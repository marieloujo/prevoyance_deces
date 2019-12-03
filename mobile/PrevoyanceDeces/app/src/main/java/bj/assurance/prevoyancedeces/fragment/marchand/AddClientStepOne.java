package bj.assurance.prevoyancedeces.fragment.marchand;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.andreabaccega.widget.FormEditText;
import com.google.common.annotations.VisibleForTesting;
import com.google.gson.JsonObject;
import com.tsongkha.spinnerdatepicker.DatePickerDialog;
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.fragment.app.FragmentManager;
import bj.assurance.prevoyancedeces.R;
import bj.assurance.prevoyancedeces.Utils.AccessToken;
import bj.assurance.prevoyancedeces.Utils.ApiError;
import bj.assurance.prevoyancedeces.Utils.Utils;
import bj.assurance.prevoyancedeces.activity.Main2Activity;
import bj.assurance.prevoyancedeces.activity.MarchandMainActivity;
import bj.assurance.prevoyancedeces.activity.Test;
import bj.assurance.prevoyancedeces.model.Assurer;
import bj.assurance.prevoyancedeces.model.Benefice;
import bj.assurance.prevoyancedeces.model.Beneficiaire;
import bj.assurance.prevoyancedeces.model.Client;
import bj.assurance.prevoyancedeces.model.Commune;
import bj.assurance.prevoyancedeces.model.Contrat;
import bj.assurance.prevoyancedeces.model.Departement;
import bj.assurance.prevoyancedeces.model.Marchand;
import bj.assurance.prevoyancedeces.model.SuperMarchand;
import bj.assurance.prevoyancedeces.model.Utilisateur;
import bj.assurance.prevoyancedeces.retrofit.RetrofitBuildForGetRessource;
import bj.assurance.prevoyancedeces.retrofit.Service.ClientService;
import bj.assurance.prevoyancedeces.retrofit.Service.MarchandService;
import bj.assurance.prevoyancedeces.retrofit.TokenManager;
import br.com.sapereaude.maskedEditText.MaskedEditText;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.GregorianCalendar;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;
import static androidx.constraintlayout.widget.Constraints.TAG;

public class AddClientStepOne extends Fragment implements DatePickerDialog.OnDateSetListener , DatePickerDialog.OnCancelListener {

    private Button cancel, next;
    private TextView tvNom, tvPrenoms, tvEmail, tvAdresse, tvSituationMatrimoniale, tvSexe, tvDateNaissance;
    private EditText etNom, etPrenoms, etAdresse, etDateNaissance, etProfession, etEmployeur;
    private FormEditText etEmail;
    private Spinner etSituationMatrimoniale, etSexe, etCommune;
    private MaskedEditText etTelephone;
    SimpleDateFormat dtYYYY = new SimpleDateFormat("YYYY");



    public AddClientStepOne() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_addclient_stepone, container, false);

        init(view);
        setClickListener();


        return view;
    }

    public void init(View view) {
        cancel = view.findViewById(R.id.annuler);
        next = view.findViewById(R.id.suivant);

        etNom = view.findViewById(R.id.etNomClient);
        etPrenoms = view.findViewById(R.id.etPrenomClient);
        etEmail = view.findViewById(R.id.et_email);
        etAdresse = view.findViewById(R.id.etAdresseClient);
        etSituationMatrimoniale = view.findViewById(R.id.etSituationMatrimonialeClient);
        etSexe = view.findViewById(R.id.etSexeClient);
        etDateNaissance = view.findViewById(R.id.etDateNaissanceClient);
        etEmployeur = view.findViewById(R.id.etEmployeur);
        etProfession = view.findViewById(R.id.etProfession);
        etTelephone = view.findViewById(R.id.etTelephoneClient);
        etCommune = view.findViewById(R.id.etCommuneClient);


        tvNom = view.findViewById(R.id.tvNomClient);
        tvPrenoms = view.findViewById(R.id.tvPrenomClient);
        tvEmail = view.findViewById(R.id.tvEmailClient);
        tvAdresse = view.findViewById(R.id.tvAdresseClient);
        tvSituationMatrimoniale = view.findViewById(R.id.tvSituationMatrimonialeClient);
        tvSexe = view.findViewById(R.id.tvetSexeClient);
        tvDateNaissance = view.findViewById(R.id.tvDateNaissanceClient);

        makeSpinnerList();
        //autoCompleCommune();

    }

    /*public void autoCompleCommune() {
        String communeName[] = {};
        for (int i = 0; i < communes.size(); i++) {
            communeName[i] = communes.get(i).getNom();
        }

        System.out.println(communeName);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.support_simple_spinner_dropdown_item, communeName);
        etCommune.setAdapter(adapter);

    }*/

    private void setClickListener() {
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFraglent(new ListeClients());
            }
        });

        etDateNaissance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDate(Integer.valueOf(dtYYYY.format(new Date())) - 20, 0, 1, R.style.DatePickerSpinner);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                @SuppressLint("SimpleDateFormat")
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

                // if (verifeData()) {

                    /*
                    Commune commune = new Commune();


                    for (int i = 0; i<MarchandMainActivity.getCommunes().size(); i++) {
                        if (MarchandMainActivity.getCommunes().get(i).getNom().equals(etCommune.getSelectedItem().toString())) {
                            commune = MarchandMainActivity.getCommunes().get(i);
                        }
                    }

                    Client client = new Client(etProfession.getText().toString(), etEmployeur.getText().toString(),new Utilisateur(
                            etNom.getText().toString(), etPrenoms.getText().toString(), etTelephone.getRawText(),
                            etEmail.getText().toString(), etSexe.getSelectedItem().toString(), etDateNaissance.getText().toString(),
                            etSituationMatrimoniale.getSelectedItem().toString(), etAdresse.getText().toString(), false,
                            false, commune)
                    );

                    MarchandMainActivity.getContrat().setClient(client);
                    replaceFraglent(new AddClientStepTwo());*/

                    Commune commune = new Commune("Abomey-calavi", new Departement("Atlantique"));

                    Utilisateur utilisateur = new Utilisateur("FLOUKOUNBE", "Aziz", "00 00 00 00", "aziz@gmail.com", "masculin",
                            simpleDateFormat.format(new Date()), "celibaire sans enfant", "calavi", false, false,  commune);

                    Utilisateur utilisateur1 = new Utilisateur("FLOUKOUNBE", "Aziz", "00 00 00 01", "aziz1@gmail.com", "masculin",
                            simpleDateFormat.format(new Date()), "celibaire sans enfant", "calavi", false, false,  commune);

                    Utilisateur utilisateur2 = new Utilisateur("FLOUKOUNBE", "Aziz", "00 00 00 02", "aziz2@gmail.com", "masculin",
                            simpleDateFormat.format(new Date()), "celibaire sans enfant", "calavi", false, false,  commune);

                    Utilisateur utilisateur3 = new Utilisateur("FLOUKOUNBE", "Aziz", "00 00 00 02", "aziz2@gmail.com", "masculin",
                            simpleDateFormat.format(new Date()), "celibaire sans enfant", "calavi", false, false,  commune);


                    Client client = new Client("electicien", "VISOUSSI carine", utilisateur);

                    Marchand marchand = new Marchand("GYY176767878", "435678", "35467", new SuperMarchand(), utilisateur1);

                    Benefice benefice = new Benefice("epouse", "30", new Beneficiaire(utilisateur3));

                    List<Benefice> benefices = new ArrayList<>();
                    benefices.add(benefice);

                    Assurer assurer = new Assurer("Vitrie", utilisateur2, false);

                    Contrat contrat = new Contrat("1 000 000", "1 000", "1", simpleDateFormat.format(new Date()),
                            simpleDateFormat.format(new Date()), simpleDateFormat.format(new Date()), simpleDateFormat.format(new Date()), client,
                            marchand, benefices, assurer);


                System.out.println(contrat.toString());

                    senContrat(TokenManager.getInstance(getActivity().getSharedPreferences("prefs", MODE_PRIVATE)).getToken(),
                            contrat);

                    //System.out.println(contrat.toString());

                }
                //replaceFraglent(new AddClientStepTwo());
            //}
        });
    }

    private void replaceFraglent(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_main_marchand, fragment).commit();

    }

    private boolean verifeData() {

        boolean isValid = true;

        if (etNom.getText().toString().isEmpty()) {
            tvNom.setVisibility(View.VISIBLE);

            isValid = false;

        } else {
            tvNom.setVisibility(View.INVISIBLE);
        }

        if (etPrenoms.getText().toString().isEmpty()) {

            tvPrenoms.setVisibility(View.VISIBLE);
            isValid = false;

        } else tvPrenoms.setVisibility(View.INVISIBLE);

        if (etEmail.getText().toString().isEmpty()) {
            isValid = false;
            tvEmail.setVisibility(View.VISIBLE);
        } else tvEmail.setVisibility(View.INVISIBLE);

        if (etAdresse.getText().toString().isEmpty()) {
            isValid = false;
            tvAdresse.setVisibility(View.VISIBLE);
        } else tvAdresse.setVisibility(View.INVISIBLE);

        if (etSituationMatrimoniale.getSelectedItem().toString().equals("Situation matrimoniale")) {
            isValid = false;
            tvSituationMatrimoniale.setVisibility(View.VISIBLE);
        } else tvSituationMatrimoniale.setVisibility(View.INVISIBLE);

        if (etSexe.getSelectedItem().toString().equals("Sexe")) {
            isValid = false;
            tvSexe.setVisibility(View.VISIBLE);
        } else tvSexe.setVisibility(View.INVISIBLE);

        boolean allValid = etEmail.testValidity() ;

        return isValid && allValid;

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


    @Override
    public void onDateSet(com.tsongkha.spinnerdatepicker.DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        Calendar calendar = new GregorianCalendar(year, monthOfYear, dayOfMonth);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MM yyyy", Locale.FRANCE);

        etDateNaissance.setText(simpleDateFormat.format(calendar.getTime()));
    }

    @androidx.annotation.VisibleForTesting
    void showDate(int year, int monthOfYear, int dayOfMonth, int spinnerTheme) {

        String newDate = dtYYYY.format(new Date()).split(" ")[0];

        int minYear = Integer.valueOf(newDate) - 74;
        int maxYear = Integer.valueOf(newDate) - 18;

        System.out.println(newDate);
        //System.out.println(maxYear + " " + minYear);

        new SpinnerDatePickerDialogBuilder()
                .context(getActivity())
                .spinnerTheme(spinnerTheme)
                .defaultDate(year, monthOfYear, dayOfMonth)
                .maxDate(maxYear, 12, 31)
                .minDate(minYear, 12, 31)
                .build()
                .show();
    }

    @Override
    public void onCancel(DialogInterface dialog) {
    }


    private void handleErrors(ResponseBody response) {

        ApiError apiError = Utils.converErrors(response);

    }

}
