package bj.assurance.prevoyancedeces.fragment.marchand;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.andreabaccega.widget.FormEditText;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.kinda.alert.KAlertDialog;
import com.msa.dateedittext.DateEditText;

import org.json.JSONObject;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import androidx.fragment.app.FragmentManager;
import bj.assurance.prevoyancedeces.R;
import bj.assurance.prevoyancedeces.activity.Connexion;
import bj.assurance.prevoyancedeces.activity.Main2Activity;
import bj.assurance.prevoyancedeces.activity.SplashScreen;
import bj.assurance.prevoyancedeces.model.Assurer;
import bj.assurance.prevoyancedeces.model.Beneficiaire;
import bj.assurance.prevoyancedeces.model.Departement;
import bj.assurance.prevoyancedeces.model.Marchand;
import bj.assurance.prevoyancedeces.model.Portefeuille;
import bj.assurance.prevoyancedeces.model.SuperMarchand;
import bj.assurance.prevoyancedeces.retrofit.Service.MarchandService;
import bj.assurance.prevoyancedeces.retrofit.Service.UserService;
import bj.assurance.prevoyancedeces.retrofit.TokenManager;
import bj.assurance.prevoyancedeces.utils.AccessToken;
import bj.assurance.prevoyancedeces.utils.ApiError;
import bj.assurance.prevoyancedeces.utils.ErrorResponse;
import bj.assurance.prevoyancedeces.utils.PhonePrefix;
import bj.assurance.prevoyancedeces.utils.Utils;
import bj.assurance.prevoyancedeces.activity.MarchandMainActivity;
import bj.assurance.prevoyancedeces.model.Client;
import bj.assurance.prevoyancedeces.model.Commune;
import bj.assurance.prevoyancedeces.model.Contrat;
import bj.assurance.prevoyancedeces.model.Utilisateur;
import bj.assurance.prevoyancedeces.retrofit.RetrofitBuildForGetRessource;
import bj.assurance.prevoyancedeces.retrofit.Service.ClientService;
import bj.assurance.prevoyancedeces.utils.ValidationEror;
import br.com.sapereaude.maskedEditText.MaskedEditText;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static androidx.constraintlayout.widget.Constraints.TAG;

public class AddClientStepOne extends Fragment {

    private Button cancel, next;
    private TextView tvNom, tvPrenoms, tvEmail, tvAdresse, tvSituationMatrimoniale, tvSexe, tvDateNaissance,
            tvProfession, tvEmployeur, tvCommune, tvTelephone;

    @SuppressLint("StaticFieldLeak")
    static private EditText etNom, etPrenoms, etAdresse, etProfession, etEmployeur;
    private MaskedEditText etTelephone;
    static private DateEditText etDateNaissance;
    static private FormEditText etEmail;

    @SuppressLint("StaticFieldLeak")
    static private Spinner etSituationMatrimoniale, etSexe, etCommune, phoneIdentity;
    SimpleDateFormat dtYYYY = new SimpleDateFormat("YYYY");

    boolean reset = false;
    private KAlertDialog ifClientExist;
    private Client client;
    private Utilisateur utilisateur = null;
    KAlertDialog pDialog;


    @SuppressLint("ValidFragment")
    public AddClientStepOne(boolean reset) {
        this.reset = reset;
    }

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
        beginDialog();

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
        etDateNaissance = view.findViewById(R.id.dateEditText);
        etEmployeur = view.findViewById(R.id.etEmployeur);
        etProfession = view.findViewById(R.id.etProfession);
        etTelephone = view.findViewById(R.id.etTelephoneClient);
        etCommune = view.findViewById(R.id.etCommuneClient);
        phoneIdentity = view.findViewById(R.id.phoneIdentity);

        etDateNaissance.listen();

        int minYear = new Date().getYear() - 74;
        int maxYear = new Date().getYear() - 18;

        etDateNaissance.setMinDate(new Date(minYear,12,31));
        etDateNaissance.setMaxDate(new Date(maxYear, 12,31));


        makeSpinnerList();
        if (reset) resetData(MarchandMainActivity.getContrat().getClient());

        tvNom = view.findViewById(R.id.tvNomClient);
        tvPrenoms = view.findViewById(R.id.tvPrenomClient);
        tvEmail = view.findViewById(R.id.tvEmailClient);
        tvAdresse = view.findViewById(R.id.tvAdresseDomicileClient);
        tvSituationMatrimoniale = view.findViewById(R.id.tvSituationMatrimonialeClient);
        tvSexe = view.findViewById(R.id.tvetSexeClient);
        tvDateNaissance = view.findViewById(R.id.tvDateNaissanceClient);
        tvCommune = view.findViewById(R.id.tvCommuneClient);
        tvProfession = view.findViewById(R.id.tvProfessionClient);
        tvEmployeur = view.findViewById(R.id.tvEmployeurClient);
        tvTelephone = view.findViewById(R.id.tvTelephoneClient);

        pDialog = new KAlertDialog(getContext(), KAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#2d8df8"));
        pDialog.setTitleText("Rechercher");
        pDialog.setCancelable(false);

        //autoCompleCommune();

    }

    private void setClickListener() {
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((etNom.getText().toString().isEmpty() && etPrenoms.getText().toString().isEmpty() &&
                        etAdresse.getText().toString().isEmpty() && etPrenoms.getText().toString().isEmpty() &&
                        etEmployeur.getText().toString().isEmpty() && etDateNaissance.getText().toString().isEmpty() &&
                        etEmail.getText().toString().isEmpty() && etTelephone.getRawText().toString().isEmpty())) {

                    replaceFraglent(new ListeClients());

                } else {
                    new KAlertDialog(getContext(), KAlertDialog.WARNING_TYPE)
                        .setTitleText("Confirmation")
                        .setContentText("Voulez vous vraiment annuler ?? toutes les données entrées seront effacées.")
                        .setConfirmText("Oui")
                        .setCancelText("Non")
                        .showCancelButton(true)
                        .setCancelClickListener(new KAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(KAlertDialog sDialog) {
                                sDialog.cancel();
                            }
                        })
                        .setConfirmClickListener(new KAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(KAlertDialog sDialog) {
                                sDialog.dismiss();
                                replaceFraglent(new ListeClients());
                            }
                        })
                        .show();
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                @SuppressLint("SimpleDateFormat")
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");


                Commune commune = new Commune();

                try {
                    for (int i = 0; i < MarchandMainActivity.getCommunes().size(); i++) {
                        if (MarchandMainActivity.getCommunes().get(i).getNom().equals(etCommune.getSelectedItem().toString())) {
                            commune = MarchandMainActivity.getCommunes().get(i);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                String sexe = etSexe.getSelectedItem().toString();
                String validSexe = null;

                if (!(sexe.equals("Sexe"))) {
                    validSexe = sexe;
                }

                Utilisateur utilisateur  = new Utilisateur(
                        etNom.getText().toString(), etPrenoms.getText().toString(), etTelephone.getRawText().toString(),
                        etEmail.getText().toString(), validSexe, etDateNaissance.getText().toString(),
                        etSituationMatrimoniale.getSelectedItem().toString(), etAdresse.getText().toString(), false,
                        false, commune);


                validation(utilisateur,TokenManager.getInstance(getActivity().
                                getSharedPreferences("prefs", MODE_PRIVATE)).
                                getToken()
                        );


                /*if (verifeData()) {


                    findClientbyNumber(
                            TokenManager.getInstance(getActivity().
                                    getSharedPreferences("prefs", MODE_PRIVATE)).
                                    getToken(), etTelephone.getRawText(), false
                    );



                    /*System.out.println(MarchandMainActivity.getContrat().toString());

                    Commune commune = new Commune();

                    try {
                        for (int i = 0; i < MarchandMainActivity.getCommunes().size(); i++) {
                            if (MarchandMainActivity.getCommunes().get(i).getNom().equals(etCommune.getSelectedItem().toString())) {
                                commune = MarchandMainActivity.getCommunes().get(i);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    Client client = new Client(etProfession.getText().toString(), etEmployeur.getText().toString(), new Utilisateur(
                            etNom.getText().toString(), etPrenoms.getText().toString(), etTelephone.getRawText(),
                            etEmail.getText().toString(), etSexe.getSelectedItem().toString(), etDateNaissance.getText().toString(),
                            etSituationMatrimoniale.getSelectedItem().toString(), etAdresse.getText().toString(), false,
                            false, commune)
                    );

                    client.setId(0L);

                    MarchandMainActivity.getContrat().setClient(client);

                    replaceFraglent(new AddClientStepTwo());/

                }*/
            }
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

        if (etDateNaissance.getText().toString().equals("")) {
            isValid = false;
            tvDateNaissance.setVisibility(View.VISIBLE);
        } else tvDateNaissance.setVisibility(View.INVISIBLE);

       /* if (etCommune.getSelectedItem().toString().equals("Commune")) {
            isValid = false;
            tvCommune.setVisibility(View.VISIBLE);
        } else tvCommune.setVisibility(View.INVISIBLE);*/

        if (etProfession.getText().toString().isEmpty()) {
            isValid = false;
            tvProfession.setVisibility(View.VISIBLE);
        } else tvProfession.setVisibility(View.INVISIBLE);

        if (etEmployeur.getText().toString().isEmpty()) {
            isValid = false;
            tvEmployeur.setVisibility(View.VISIBLE);
        } else tvEmployeur.setVisibility(View.INVISIBLE);

        if (etTelephone.getRawText().toString().isEmpty()) {
            isValid = false;
            tvTelephone.setVisibility(View.VISIBLE);
        } else tvTelephone.setVisibility(View.INVISIBLE);


        boolean allValid = etEmail.testValidity() ;

        return isValid && allValid;

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

        String[] communess = new String[MarchandMainActivity.getCommunes().size()+2];
        communess[0] = "Commune";
        communess[MarchandMainActivity.getCommunes().size() +1] = "Autre";
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

        etCommune.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);

                if(position + 1 == parent.getCount()){
                    // Notify the selected item text
                    openDepartementDialog();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    private void resetData(Client client) {
        etNom.setText(client.getUtilisateur().getNom());
        etPrenoms.setText(client.getUtilisateur().getPrenom());
        etAdresse.setText(client.getUtilisateur().getAdresse());
        etEmail.setText(client.getUtilisateur().getEmail());
        etProfession.setText(client.getProfession());
        etEmployeur.setText(client.getEmployeur());

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


        try {
            etDateNaissance.setHint(simpleDateFormat.format(format.parse(client.getUtilisateur().getDateNaissance())));
        } catch (Exception e) {
            e.printStackTrace();
        }

        etTelephone.setText(client.getUtilisateur().getTelephone());
        resetSpiner(client);
    }

    private void resetSpiner(Client client) {
        for(int i= 0; i < etSexe.getAdapter().getCount(); i++)
        {
            if(etSexe.getAdapter().getItem(i).toString().contains(client.getUtilisateur().getSexe()))
            {
                etSexe.setSelection(i);
            }
        }

        for(int i= 0; i < etCommune.getAdapter().getCount(); i++)
        {
            if(etCommune.getAdapter().getItem(i).toString().contains(client.getUtilisateur().getCommune().getNom()))
            {
                etCommune.setSelection(i);
            }
        }

        for(int i= 0; i < etSituationMatrimoniale.getAdapter().getCount(); i++)
        {
            if(etSituationMatrimoniale.getAdapter().getItem(i).toString().contains(client.getUtilisateur().getSituationMatrimoniale()))
            {
                etSituationMatrimoniale.setSelection(i);
            }
        }
    }

    private void beginDialog() {
        ifClientExist = new KAlertDialog(getActivity(), KAlertDialog.CUSTOM_IMAGE_TYPE);
        ifClientExist.setTitleText("Vérification du souscripteur")
            .setContentText("Est-ce un ancien souscripteur ??")
            .setCancelText("Non")
            .setConfirmText("Oui")
            .showCancelButton(true)
            .setCancelClickListener(new KAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(KAlertDialog sDialog) {
                    ifClientExist.cancel();
                }
            })
            .setConfirmClickListener(new KAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(KAlertDialog sDialog) {
                    openDialog();
                }
            })
            .show();
    }

    private void openDialog() {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.find_by_telephone_number, null);

        MaskedEditText textView = alertLayout.findViewById(R.id.etTelephone);
        TextView textView1 = alertLayout.findViewById(R.id.tvError);

        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("Recherche par télephone");
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

        alert.setPositiveButton("Recherche", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (textView.getRawText().isEmpty()) {
                    textView1.setVisibility(View.VISIBLE);
                    return;
                } else {
                    textView1.setVisibility(View.INVISIBLE);
                    pDialog.show();
                    findClientbyNumber(
                        TokenManager.getInstance(getActivity().
                            getSharedPreferences("prefs", MODE_PRIVATE)).
                            getToken(), textView.getRawText(), true
                    );
                }
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
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
                Toast.makeText(getContext(), String.valueOf(MarchandMainActivity.getDepartements().get(position).getId()),
                        Toast.LENGTH_LONG).show();
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

    private void findClientbyNumber(AccessToken accessToken, String number, boolean isSearch) {
        Call<JsonObject> call;
        UserService service = new RetrofitBuildForGetRessource(accessToken).getRetrofit().create(UserService.class);
        call = service.findbyTelephone(number);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                Log.w(TAG, "onResponse: " + response);

                if (response.isSuccessful()) {
                    System.out.println(response.body());

                    onNumberSuccess(response.body(), isSearch);

                } else {
                    pDialog.dismiss();
                    ifClientExist.dismiss();
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.w(TAG, "onFailure: " + t.getMessage());
                pDialog.dismiss();
                ifClientExist.dismiss();
                //Toast.makeText(Main2Activity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void onNumberSuccess(JsonObject jsonObject, boolean isSearch) {
        try {

            utilisateur = new Gson().fromJson(new Gson().toJson(jsonObject.getAsJsonObject("success").
                    getAsJsonObject("data")), Utilisateur.class);

            client = new Gson().fromJson(new Gson().toJson(utilisateur.getObject()), Client.class);
            client.setUtilisateur(utilisateur);

            System.out.println("\n client: \n "+ client);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (isSearch) {
            resetData(client);
            disabledInput();
            pDialog.dismiss();
            ifClientExist.dismiss();

            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                    MarchandMainActivity.getContrat().setClient(new Client(client.getId()));

                    System.out.println(MarchandMainActivity.getContrat());

                    replaceFraglent(new AddClientStepTwo());
                }
            });
        } else {
            openDialogUserExist();
        }
    }

    private void disabledInput() {
        etNom.setEnabled(   false);
        etPrenoms.setEnabled(false);
        etEmail.setEnabled(false);
        etAdresse.setEnabled(false);
        etTelephone.setEnabled(false);
        etProfession.setEnabled(false);
        etEmployeur.setEnabled(false);
        etDateNaissance.setEnabled(false);

        etSexe.setEnabled(false);
        etSexe.setClickable(false);

        etSituationMatrimoniale.setEnabled(false);
        etSituationMatrimoniale.setClickable(false);

        etCommune.setEnabled(false);
        etCommune.setFocusable(false);
    }



    private void openDialogUserExist() {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.content_existe_user, null);


        TextView nom, prenom, sexe, datenaissance, telephone, email, adresse, situationMatrimonial, profession, employeur;

        nom = alertLayout.findViewById(R.id.nom_user);
        prenom = alertLayout.findViewById(R.id.prenom_user);
        sexe = alertLayout.findViewById(R.id.sexe_user);
        datenaissance = alertLayout.findViewById(R.id.naissance_user);
        telephone = alertLayout.findViewById(R.id.telephone_user);
        email = alertLayout.findViewById(R.id.email_user);
        adresse = alertLayout.findViewById(R.id.adresse_user);
        situationMatrimonial = alertLayout.findViewById(R.id.sm__user);
        profession = alertLayout.findViewById(R.id.profession_user);
        employeur = alertLayout.findViewById(R.id.employeur_user);


        nom.setText(utilisateur.getNom());
        prenom.setText(utilisateur.getPrenom());
        sexe.setText(utilisateur.getSexe());
        datenaissance.setText(utilisateur.getDateNaissance());
        telephone.setText(utilisateur.getTelephone());
        email.setText(utilisateur.getEmail());
        adresse.setText(utilisateur.getAdresse());
        situationMatrimonial.setText(utilisateur.getSituationMatrimoniale());

        Client client;
        Marchand marchand;
        Assurer assurer;
        Beneficiaire beneficiaire;
        SuperMarchand superMarchand;




        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("Vérification des données");
        alert.setView(alertLayout);
        alert.setCancelable(false);
        alert.setNegativeButton("Non", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tvTelephone.setText("Ce numéro de télephone est dèja utilisé");
                tvTelephone.setVisibility(View.VISIBLE);
            }
        });

        alert.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {



            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }




    private void validation(Utilisateur utilisateur, AccessToken accessToken) {

        Call<ErrorResponse> call;

        MarchandService marchandService = new RetrofitBuildForGetRessource(accessToken).getRetrofit().create(MarchandService.class);


        call = marchandService.validation(utilisateur);

        call.enqueue(new Callback<ErrorResponse>() {


            @Override
            public void onResponse(Call<ErrorResponse> call, Response<ErrorResponse> response) {


                if (response.isSuccessful()) {

                    String phone = phoneIdentity.getSelectedItem().toString();
                    String telephone = etTelephone.getRawText().toString();
                    List<PhonePrefix> phonePrefixes = new ArrayList<>();

                    if (phone.equals("00229")) {

                        for (int i = 0; i < MarchandMainActivity.getPhoneLists().size(); i ++) {

                            if (MarchandMainActivity.getPhoneLists().get(i).getCode().equals("+229")) {

                                phonePrefixes = MarchandMainActivity.getPhoneLists().get(i).getPhonePrefix();

                            }
                        }

                        boolean isValidePhone = false;


                        for (int i = 0; i < phonePrefixes.size(); i++) {

                            if (phonePrefixes.get(i).getNum().equals(etTelephone.getRawText().substring(0,2))) {
                                isValidePhone = true;
                            }
                        }

                        if (!isValidePhone) {

                            tvTelephone.setText("Ce numéro n'est pas attribué pour le code postal 00229");
                            tvTelephone.setVisibility(View.VISIBLE);

                        } else {

                            Client client = new Client(etProfession.getText().toString(), etEmployeur.getText().toString(), utilisateur
                            );

                            client.setId(0L);

                            MarchandMainActivity.getContrat().setClient(client);

                            replaceFraglent(new AddClientStepTwo());

                        }

                    }


                    if (phone.equals("00225")) {

                        for (int i = 0; i < MarchandMainActivity.getPhoneLists().size(); i ++) {

                            if (MarchandMainActivity.getPhoneLists().get(i).getCode().equals("+225")) {

                                phonePrefixes = MarchandMainActivity.getPhoneLists().get(i).getPhonePrefix();

                            }
                        }

                        boolean isValidePhone = false;

                        for (int i = 0; i < phonePrefixes.size(); i++) {

                            Toast.makeText(getContext(), etTelephone.getRawText().substring(0,1), Toast.LENGTH_LONG).show();

                            if (phonePrefixes.get(i).getNum().equals(etTelephone.getRawText().toString().substring(0,2))) {
                                isValidePhone = true;
                            }
                        }

                        if (!isValidePhone) {

                            tvTelephone.setText("Ce numéro n'est pas attribué pour le code postal 00225");
                            tvTelephone.setVisibility(View.VISIBLE);

                        } else {

                            Client client = new Client(etProfession.getText().toString(), etEmployeur.getText().toString(), utilisateur
                            );

                            client.setId(0L);

                            MarchandMainActivity.getContrat().setClient(client);

                            replaceFraglent(new AddClientStepTwo());

                        }

                    }


                } else {

                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        System.out.println(jObjError.getJSONObject("errors").getString("message"));

                        Type listType = new TypeToken<List<ValidationEror>>() {}.getType();

                        List<ValidationEror> validationEror = new Gson().fromJson(jObjError.getJSONObject("errors").getString("message"), listType);

                        makeEditError(validationEror);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            }

            @Override
            public void onFailure(Call<ErrorResponse> call, Throwable t) {
                t.printStackTrace();
            }

        });

    }



    private void makeEditError(List<ValidationEror> validationEror)  {

        for (ValidationEror validationEror1 : validationEror) {

            try {
                tvNom.setText(validationEror1.getErrorNom().get(0));
                tvNom.setVisibility(View.VISIBLE);
            } catch (Exception ignored) {}

            try {
                tvPrenoms.setText(validationEror1.getErrorPrenom().get(0));
                tvPrenoms.setVisibility(View.VISIBLE);
            } catch (Exception ignored) {}


            try {
                tvTelephone.setText(validationEror1.getErrorTelephone().get(0));
                tvTelephone.setVisibility(View.VISIBLE);
            } catch (Exception ignored) {}


            try {
                tvEmail.setText(validationEror1.getErrorEmail().get(0));
                tvEmail.setVisibility(View.VISIBLE);
            } catch (Exception ignored) {}


            try {
                tvSexe.setText(validationEror1.getErrorsexe().get(0));
                tvSexe.setVisibility(View.VISIBLE);
            } catch (Exception ignored) {}


            try {
                tvDateNaissance.setText(validationEror1.getErrorDateNaissance().get(0));
                tvDateNaissance.setVisibility(View.VISIBLE);
            } catch (Exception ignored) {}


            try {
                tvCommune.setText(validationEror1.getErrorcommune().get(0));
                tvCommune.setVisibility(View.VISIBLE);
            } catch (Exception ignored) {}
        }

    }



}
