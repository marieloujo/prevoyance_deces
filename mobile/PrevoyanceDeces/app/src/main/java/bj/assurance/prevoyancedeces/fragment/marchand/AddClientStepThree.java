package bj.assurance.prevoyancedeces.fragment.marchand;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.andreabaccega.widget.FormEditText;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.FragmentManager;
import bj.assurance.prevoyancedeces.R;
import bj.assurance.prevoyancedeces.Utils.AccessToken;
import bj.assurance.prevoyancedeces.Utils.ApiError;
import bj.assurance.prevoyancedeces.Utils.Utils;
import bj.assurance.prevoyancedeces.activity.Main2Activity;
import bj.assurance.prevoyancedeces.activity.MarchandMainActivity;
import bj.assurance.prevoyancedeces.fragment.client.Marchands;
import bj.assurance.prevoyancedeces.model.Benefice;
import bj.assurance.prevoyancedeces.model.Beneficiaire;
import bj.assurance.prevoyancedeces.model.Commune;
import bj.assurance.prevoyancedeces.model.Contrat;
import bj.assurance.prevoyancedeces.model.Utilisateur;
import bj.assurance.prevoyancedeces.retrofit.RetrofitBuildForGetRessource;
import bj.assurance.prevoyancedeces.retrofit.Service.ClientService;
import bj.assurance.prevoyancedeces.retrofit.TokenManager;
import br.com.sapereaude.maskedEditText.MaskedEditText;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static androidx.constraintlayout.widget.Constraints.TAG;

public class AddClientStepThree extends Fragment {

    private Button cancel, next;
    private EditText etNom, etPrenom, etQualification, etTaux, etAdresse, etDateNaissance;
    private FormEditText etEmail;
    private Spinner etSituationMatrimoniale, etSexe, etCommune;
    private MaskedEditText etTelephone;

    public AddClientStepThree() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_addclient_stepthree, container, false);




        return view;
    }

    public void init(View view) {
        cancel = view.findViewById(R.id.annuler);
        next = view.findViewById(R.id.suivant);

        etNom = view.findViewById(R.id.etNomBeneficier);
        etPrenom = view.findViewById(R.id.etPrenomBeneficier);
        etQualification = view.findViewById(R.id.QualificationBeneficier);
        etTaux = view.findViewById(R.id.etTauxBeneficier);

        etEmail = view.findViewById(R.id.etEmailBeneficier);
        etAdresse = view.findViewById(R.id.etAdresseBeneficier);
        etSituationMatrimoniale = view.findViewById(R.id.etSituationMatrimonialeBeneficier);
        etSexe = view.findViewById(R.id.etSexeB);
        etDateNaissance = view.findViewById(R.id.etDateNaissanceB);
        etTelephone = view.findViewById(R.id.etTelephoneBeneficier);
        etCommune = view.findViewById(R.id.etCommuneBeneficier);

        makeSpinnerList();
    }

    public void setCliclLister() {
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFraglent(new ListeClients());
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Commune commune = new Commune();


                for (int i = 0; i<MarchandMainActivity.getCommunes().size(); i++) {
                    if (MarchandMainActivity.getCommunes().get(i).getNom().equals(etCommune.getSelectedItem().toString())) {
                        commune = MarchandMainActivity.getCommunes().get(i);
                    }
                }

                Benefice benefice = new Benefice(etQualification.getText().toString(), etTaux.getText().toString(),
                        new Beneficiaire(new Utilisateur(
                                etNom.getText().toString(), etPrenom.getText().toString(), etTelephone.getRawText(),
                                etEmail.getText().toString(), etSexe.getSelectedItem().toString(), etDateNaissance.getText().toString(),
                                etSituationMatrimoniale.getSelectedItem().toString(), etAdresse.getText().toString(), false,
                                false, commune)));

                List<Benefice> benefices = new ArrayList<>();
                benefices.add(benefice);

                MarchandMainActivity.getContrat().setBenefices(benefices);

                System.out.println(MarchandMainActivity.getContrat());

                senContrat(TokenManager.getInstance(getActivity().getSharedPreferences("prefs", MODE_PRIVATE)).getToken(),MarchandMainActivity.getContrat());


                //replaceFraglent(new AddClientStepFour());
            }
        });

    }

    private void replaceFraglent(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_main_marchand, fragment).commit();
    }

    private void senContrat(AccessToken accessToken, Contrat contrat) {

        Call<JsonObject> call;
        ClientService service = new RetrofitBuildForGetRessource(accessToken).getRetrofit().create(ClientService.class);
        call = service.create(contrat);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                Log.w(TAG, "onResponse: " + response);

                if (response.isSuccessful()) {
                    System.out.println(response.body());
                } else {
                    if (response.code() == 422) {
                        System.out.println(response.code()+" "+response.errorBody().source());
                    }
                    if (response.code() == 401) {
                        ApiError apiError = Utils.converErrors(response.errorBody());
                        Toast.makeText(getActivity(), response.code() +" " +apiError.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.w(TAG, "onFailure: " + t.getMessage());
                Toast.makeText(getActivity(), t.getMessage()+ t.getCause().getCause().getCause(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void makeSpinnerList() {

        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                getContext(),R.layout.item_spinner,getResources().getStringArray(R.array.situation_matrimoniale)){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };


        final ArrayAdapter<String> sexeArrayAdapter = new ArrayAdapter<String>(
                getContext(),R.layout.item_spinner,getResources().getStringArray(R.array.sexe)){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };

        String[] communess = new String[MarchandMainActivity.getCommunes().size()+1];
        communess[0] = "Commune";
        for (int i = 0; i < MarchandMainActivity.getCommunes().size(); i++) {
            communess[i+1] = MarchandMainActivity.getCommunes().get(i).getNom();
            System.out.println(communess);
        }

        final ArrayAdapter<String> communeArrayAdapter = new ArrayAdapter<String>(
                getContext(),R.layout.item_spinner,communess){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };

        spinnerArrayAdapter.setDropDownViewResource(R.layout.item_spinner);
        sexeArrayAdapter.setDropDownViewResource(R.layout.item_spinner);
        communeArrayAdapter.setDropDownViewResource(R.layout.item_spinner);

        etSituationMatrimoniale.setAdapter(spinnerArrayAdapter);
        etSexe.setAdapter(sexeArrayAdapter);
        etCommune.setAdapter(communeArrayAdapter);

        etSituationMatrimoniale.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);

                if(position > 0){
                    // Notify the selected item text
                    Toast.makeText
                            (getContext().getApplicationContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT)
                            .show();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        etSexe.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);

                if(position > 0){
                    // Notify the selected item text
                    Toast.makeText
                            (getContext().getApplicationContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT)
                            .show();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        etCommune.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);

                if(position > 0){
                    // Notify the selected item text
                    Toast.makeText
                            (getContext().getApplicationContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT)
                            .show();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }



}
