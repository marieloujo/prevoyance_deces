package bj.assurance.prevoyancedeces.fragment.marchand;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.andreabaccega.widget.FormEditText;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.kinda.alert.KAlertDialog;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.fragment.app.FragmentManager;
import bj.assurance.prevoyancedeces.R;
import bj.assurance.prevoyancedeces.activity.MarchandMainActivity;
import bj.assurance.prevoyancedeces.customClass.ListObject;
import bj.assurance.prevoyancedeces.model.Commune;
import bj.assurance.prevoyancedeces.model.Utilisateur;
import bj.assurance.prevoyancedeces.model.pagination.OutputPaginate;
import bj.assurance.prevoyancedeces.retrofit.RetrofitBuildForGetRessource;
import bj.assurance.prevoyancedeces.retrofit.Service.MarchandService;
import bj.assurance.prevoyancedeces.retrofit.Service.UserService;
import bj.assurance.prevoyancedeces.retrofit.TokenManager;
import bj.assurance.prevoyancedeces.utils.AccessToken;
import br.com.sapereaude.maskedEditText.MaskedEditText;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


public class AddProspect extends Fragment {

    private EditText nom,prenom;
    private Spinner sexe, commune;
    private MaskedEditText telephone;
    private FormEditText email;
    private TextView tvEmail, tvTelephone, tvCommune, tvPrenoms, tvNom;
    private Button next, cancel;

    KAlertDialog pDialog;
    private List<Commune> communes;

    ArrayAdapter<ListObject> communeArrayAdapter = null;

    List<ListObject> communess = new ArrayList<>();

    public AddProspect() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_prospect, container, false);

        init(view);

        setClickListener();

        return view;
    }

    private void init(View view) {
        nom = view.findViewById(R.id.etNomProspect);
        prenom = view.findViewById(R.id.etPrenomProspect);
        sexe = view.findViewById(R.id.etSexeProspect);
        commune = view.findViewById(R.id.etCommuneProspect);
        email = view.findViewById(R.id.et_emailProspect);
        telephone = view.findViewById(R.id.etTelephoneProspect);

        tvNom = view.findViewById(R.id.tvNomProspect);
        tvPrenoms = view.findViewById(R.id.tvPrenomProspect);
        tvEmail = view.findViewById(R.id.tvEmailProspect);
        tvCommune = view.findViewById(R.id.tvCommuneProspect);
        tvTelephone = view.findViewById(R.id.tvTelephoneProspect);

        cancel = view.findViewById(R.id.annuler);
        next = view.findViewById(R.id.suivant);

        makeSpinnerList();
    }

    private void makeSpinnerList() {

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

        communess.add(new ListObject("Commune"));
        for (int i = 0; i < MarchandMainActivity.getCommunes().size(); i++) {
            communess.add(new ListObject(MarchandMainActivity.getCommunes().get(i).getNom()));
            System.out.println(communess);
        }
        communess.add(new ListObject("Autre"));

        communeArrayAdapter = new ArrayAdapter<ListObject>(
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

        sexeArrayAdapter.setDropDownViewResource(R.layout.item_spinner);
        communeArrayAdapter.setDropDownViewResource(R.layout.item_spinner);

        sexe.setAdapter(sexeArrayAdapter);
        commune.setAdapter(communeArrayAdapter);

        commune.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ListObject selectedItemText = (ListObject) parent.getItemAtPosition(position);

                if(position > 0){

                    if (selectedItemText.getLibelle().equals("Autre"))
                        openDepartementDialog();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    private void openDepartementDialog() {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.content_list_departement, null);

        ListView listView = alertLayout.findViewById(R.id.list_departement);

        String[] strings = new  String[MarchandMainActivity.getDepartements().size()];

        for (int i = 0; i < MarchandMainActivity.getDepartements().size(); i ++) {
            strings[i] = MarchandMainActivity.getDepartements().get(i).getNom();
        }
        System.out.println("departement " + strings);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(Objects.requireNonNull(getActivity()),
                android.R.layout.simple_list_item_1,
                strings);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*Toast.makeText(getContext(), String.valueOf(MarchandMainActivity.
                    getDepartements().get(position).getNom()),
                        Toast.LENGTH_LONG).show();*/

                getCommuneOfDepartement (
                        TokenManager.getInstance(getActivity().
                                getSharedPreferences("prefs", MODE_PRIVATE)).getToken(),
                        MarchandMainActivity.
                                getDepartements().get(position).getId()
                );

            }
        });

        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("Selectionner un département");
        // this is set the view from XML inside AlertDialog
        alert.setView(alertLayout);
        // disallow cancel of AlertDialog on click of back button and outside touch
        alert.setCancelable(false);

        alert.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Toast.makeText(getContext(), "Cancel clicked", Toast.LENGTH_SHORT).show();
            }
        });

        AlertDialog dialog = alert.create();
        dialog.show();
    }

    private void openCommuneDialog() {

        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("Selectionner une commune");

        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.content_list_departement, null);

        ListView listView = alertLayout.findViewById(R.id.list_departement);

        List<ListObject> strings = new ArrayList<>();

        for (int i = 0; i < communes.size(); i ++) {
            strings.add(new ListObject(communes.get(i).getNom()));
        }
        System.out.println("communes " + strings);

        ArrayAdapter<ListObject> arrayAdapter = new ArrayAdapter<ListObject>(Objects.requireNonNull(getActivity()),
                android.R.layout.simple_list_item_1,
                strings);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                communess.remove(communess.size()-1);

                communess.add(communess.size(), new ListObject(communes.get(position).getNom()));
                communeArrayAdapter.notifyDataSetChanged();

                communess.add(communess.size(), new ListObject("Autre"));

                for(int i= 0; i < commune.getAdapter().getCount(); i++) {
                    if(commune.getAdapter().getItem(i).toString().contains(communes.get(position).getNom())) {
                        commune.setSelection(i);
                    }
                }

            }
        });


        // this is set the view from XML inside AlertDialog
        alert.setView(alertLayout);
        // disallow cancel of AlertDialog on click of back button and outside touch
        alert.setCancelable(false);

        alert.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Toast.makeText(getContext(), "Cancel clicked", Toast.LENGTH_SHORT).show();
            }
        });

        AlertDialog dialog = alert.create();
        dialog.show();
    }

    private void setClickListener() {
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verifData()) {
                    pDialog = new KAlertDialog(getContext(), KAlertDialog.PROGRESS_TYPE);
                    pDialog.getProgressHelper().setBarColor(Color.parseColor("#2d8df8"));
                    pDialog.setTitleText("Enregistrement du prospect");
                    pDialog.setCancelable(false);
                    pDialog.show();

                    Utilisateur utilisateur = new Utilisateur();
                    utilisateur.setNom(nom.getText().toString());
                    utilisateur.setPrenom(prenom.getText().toString());
                    utilisateur.setEmail(email.getText().toString());
                    utilisateur.setTelephone(telephone.getRawText());
                    utilisateur.setSexe(sexe.getSelectedItem().toString());
                    utilisateur.setMarchand(MarchandMainActivity.getMarchand());

                    Commune commune = new Commune();

                    try {
                        for (int i = 0; i < MarchandMainActivity.getCommunes().size(); i++) {
                            if (MarchandMainActivity.getCommunes().get(i).getNom().equals(AddProspect.this.commune.getSelectedItem().toString())) {
                                commune = MarchandMainActivity.getCommunes().get(i);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    utilisateur.setCommune(commune);

                    registerProspect(utilisateur,
                            TokenManager.getInstance(
                                    getContext().getSharedPreferences("prefs", MODE_PRIVATE)).getToken());

                }
            }
        });
    }

    private boolean verifData() {

        boolean isValid = true;

        if (nom.getText().toString().isEmpty()) {
            tvNom.setVisibility(View.VISIBLE);

            isValid = false;

        } else {
            tvNom.setVisibility(View.INVISIBLE);
        }

        if (prenom.getText().toString().isEmpty()) {

            tvPrenoms.setVisibility(View.VISIBLE);
            isValid = false;

        } else tvPrenoms.setVisibility(View.INVISIBLE);

        if (email.getText().toString().isEmpty() || telephone.getRawText().isEmpty()) {
            isValid = false;

            if (email.getText().toString().isEmpty())
                tvEmail.setVisibility(View.VISIBLE);
            else tvTelephone.setVisibility(View.INVISIBLE);

        } else tvEmail.setVisibility(View.INVISIBLE);

        if (commune.getSelectedItem().toString().equals("Commune")) {
            isValid = false;
            tvCommune.setVisibility(View.VISIBLE);
        } else tvCommune.setVisibility(View.INVISIBLE);

        return isValid;
    }

    private void registerProspect(Utilisateur utilisateur, AccessToken accessToken) {
        Call<JsonObject> call;
        MarchandService service = new RetrofitBuildForGetRessource(accessToken).getRetrofit().create(MarchandService.class);
        call = service.createPospect(utilisateur);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    System.out.println(response.body());

                    String message = response.body().getAsJsonObject("success").get("message").getAsString();

                    if (message.equals("Prospect enregistré")) {

                        pDialog.changeAlertType(KAlertDialog.SUCCESS_TYPE);
                        pDialog.setTitleText("Enregistrement réussi")
                                .setContentText(message)
                                .setConfirmText("OK")
                                .setConfirmClickListener(new KAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(KAlertDialog kAlertDialog) {

                                        pDialog.dismiss();
                                        replaceFragment(new Accueil(),
                                                "Salut "+ MarchandMainActivity.getUtilisateur().getPrenom());
                                    }
                                }) ;

                    } else {
                        pDialog.changeAlertType(KAlertDialog.ERROR_TYPE);
                        pDialog.setTitleText("Echec d'enregistrement")
                                .setContentText(message)
                                .setConfirmText("OK")
                                .setConfirmClickListener(new KAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(KAlertDialog kAlertDialog) {
                                        pDialog.dismiss();
                                    }
                                });
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }

    public void replaceFragment(Fragment fragment, String titre){
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_main_marchand, fragment).commit();

        MarchandMainActivity.getTitleFrame().setText(titre);
    }


    private void getCommuneOfDepartement(AccessToken accessToken, Long id) {
        Call<JsonObject> call;
        UserService userService = new RetrofitBuildForGetRessource(accessToken).getRetrofit().create(UserService.class);
        call = userService.getCommuneOfDepartement(id);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    System.out.println(response.body());

                    getResponseCommunes(response.body());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }

    private void getResponseCommunes(JsonObject jsonObject) {
        JsonObject error = null, sucess = null;
        String messageError = null, message = null;
        OutputPaginate outputPaginate = null;

        try {
            error = jsonObject.getAsJsonObject("errors");
            messageError = error.get("message").getAsString();
        }catch (Exception ignored) {}

        try {
            sucess = jsonObject.getAsJsonObject("success");
            message = sucess.get("message").getAsString();
        } catch (Exception ignored) {}

        try {
            outputPaginate = new Gson().fromJson(jsonObject, OutputPaginate.class);
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Commune>>() {}.getType();

            assert outputPaginate != null;
            communes = gson.fromJson(gson.toJson(outputPaginate.getObjects()), listType);

            openCommuneDialog();

        }catch (Exception e) {
            e.printStackTrace();
        }

    }


}
