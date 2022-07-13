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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.andreabaccega.widget.FormEditText;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.kinda.alert.KAlertDialog;
import com.msa.dateedittext.DateEditText;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.fragment.app.FragmentManager;
import bj.assurance.prevoyancedeces.R;
import bj.assurance.prevoyancedeces.activity.MarchandMainActivity;
import bj.assurance.prevoyancedeces.model.Assurer;
import bj.assurance.prevoyancedeces.model.Client;
import bj.assurance.prevoyancedeces.model.Commune;
import bj.assurance.prevoyancedeces.model.Utilisateur;
import bj.assurance.prevoyancedeces.retrofit.RetrofitBuildForGetRessource;
import bj.assurance.prevoyancedeces.retrofit.Service.ClientService;
import bj.assurance.prevoyancedeces.retrofit.Service.MarchandService;
import bj.assurance.prevoyancedeces.retrofit.Service.UserService;
import bj.assurance.prevoyancedeces.retrofit.TokenManager;
import bj.assurance.prevoyancedeces.utils.AccessToken;
import bj.assurance.prevoyancedeces.utils.ErrorResponse;
import bj.assurance.prevoyancedeces.utils.PhonePrefix;
import bj.assurance.prevoyancedeces.utils.ValidationEror;
import br.com.sapereaude.maskedEditText.MaskedEditText;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static androidx.constraintlayout.widget.Constraints.TAG;

public class AddClientStepTwo extends Fragment {

    private Button cancel, next;
    private TextView tvNom, tvPrenoms, tvEmail, tvAdresse, tvSituationMatrimoniale, tvSexe, tvDateNaissance, tvProfession, tvEmployeur, tvCommune, tvTelephone;
    private EditText etNom, etPrenoms, etAdresse, etProfession, etEmployeur;
    private DateEditText etDateNaissance;
    private FormEditText etEmail;
    private Spinner etSituationMatrimoniale, etSexe, etCommune, phoneIdentity;
    private MaskedEditText etTelephone;

    KAlertDialog pDialog;

    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat dtYYYY = new SimpleDateFormat("YYYY");

    boolean reset = false;
    private KAlertDialog ifAssurerExist;

    private Utilisateur utilisateur;
    private Assurer assurer;

    @SuppressLint("ValidFragment")
    public AddClientStepTwo(boolean reset) {
        this.reset = reset;
    }

    public AddClientStepTwo() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_client_step_two, container, false);

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
        if(reset) resetData(MarchandMainActivity.getContrat().getAssurer());

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

    }

    private void setClickListener() {
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((etNom.getText().toString().isEmpty() && etPrenoms.getText().toString().isEmpty() &&
                        etAdresse.getText().toString().isEmpty() && etPrenoms.getText().toString().isEmpty() &&
                        etEmployeur.getText().toString().isEmpty() && etDateNaissance.getText().toString().isEmpty() &&
                        etEmail.getText().toString().isEmpty() && etTelephone.getRawText().isEmpty())) {

                    replaceFraglent(new AddClientStepOne(true));

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
                                replaceFraglent(new AddClientStepOne(true));
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

                }

                Utilisateur utilisateur = new Utilisateur(
                        etNom.getText().toString(), etPrenoms.getText().toString(), etTelephone.getRawText(),
                        etEmail.getText().toString(), etSexe.getSelectedItem().toString(), etDateNaissance.getText().toString(),
                        etSituationMatrimoniale.getSelectedItem().toString(), etAdresse.getText().toString(), false,
                        false, commune);

                validation(utilisateur,
                        TokenManager.getInstance(getActivity().
                                getSharedPreferences("prefs", MODE_PRIVATE)).
                                getToken());

                /*if (verifeData()) {

                    System.out.println(MarchandMainActivity.getContrat().toString());

                    Commune commune = new Commune();


                    try {
                        for (int i = 0; i < MarchandMainActivity.getCommunes().size(); i++) {
                            if (MarchandMainActivity.getCommunes().get(i).getNom().equals(etCommune.getSelectedItem().toString())) {
                                commune = MarchandMainActivity.getCommunes().get(i);
                            }
                        }
                    } catch (Exception e) {

                    }

                    Assurer assurer = new Assurer(etProfession.getText().toString(), etEmployeur.getText().toString(),new Utilisateur(
                            etNom.getText().toString(), etPrenoms.getText().toString(), etTelephone.getRawText(),
                            etEmail.getText().toString(), etSexe.getSelectedItem().toString(), etDateNaissance.getText().toString(),
                            etSituationMatrimoniale.getSelectedItem().toString(), etAdresse.getText().toString(), false,
                            false, commune), false
                    );

                    assurer.setId(0L);

                    MarchandMainActivity.getContrat().setAssurer(assurer);

                    replaceFraglent(new AddClientStepThree());

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

        if (etTelephone.getRawText().isEmpty()) {
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

    }

    private void resetData(Assurer assurer) {
        try {
            etNom.setText(assurer.getUtilisateur().getNom());
            etPrenoms.setText(assurer.getUtilisateur().getPrenom());
            etAdresse.setText(assurer.getUtilisateur().getAdresse());
            etEmail.setText(assurer.getUtilisateur().getEmail());
            etProfession.setText(assurer.getProfession());

            etTelephone.setText(assurer.getUtilisateur().getTelephone());
            resetSpiner(assurer);

        } catch (Exception e) {
            e.printStackTrace();
        }

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            etDateNaissance.setHint(simpleDateFormat.format(format.parse(assurer.getUtilisateur().getDateNaissance())));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void resetSpiner(Assurer assurer) {
        for(int i= 0; i < etSexe.getAdapter().getCount(); i++) {
            if(etSexe.getAdapter().getItem(i).toString().contains(assurer.getUtilisateur().getSexe())) {
                etSexe.setSelection(i);
            }
        }

        for(int i= 0; i < etCommune.getAdapter().getCount(); i++) {
            if(etCommune.getAdapter().getItem(i).toString().contains(assurer.getUtilisateur().getCommune().getNom())) {
                etCommune.setSelection(i);
            }
        }

        for(int i= 0; i < etSituationMatrimoniale.getAdapter().getCount(); i++) {
            if(etSituationMatrimoniale.getAdapter().getItem(i).toString().contains(assurer.getUtilisateur().getSituationMatrimoniale())) {
                etSituationMatrimoniale.setSelection(i);
            }
        }
    }

    private void beginDialog() {
        ifAssurerExist = new KAlertDialog(getActivity(), KAlertDialog.CUSTOM_IMAGE_TYPE);
        ifAssurerExist.setTitleText("Vérification de l'assurer")
                .setContentText("Est-ce un ancien assurer ??")
                .setCancelText("Non")
                .setConfirmText("Oui")
                .showCancelButton(true)
                .setCancelClickListener(new KAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(KAlertDialog sDialog) {
                        ifAssurerExist.cancel();
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
                    textView1.setVisibility(View.INVISIBLE);
                    return;
                } else {
                    textView1.setVisibility(View.VISIBLE);
                    pDialog.show();
                    findClientbyId(
                            TokenManager.getInstance(getActivity().
                                    getSharedPreferences("prefs", MODE_PRIVATE)).
                                    getToken(), textView.getRawText()
                    );
                }
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }

    private void findClientbyId(AccessToken accessToken, String number) {
        Call<JsonObject> call;
        UserService service = new RetrofitBuildForGetRessource(accessToken).getRetrofit().create(UserService.class);
        call = service.findbyTelephone(number);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                Log.w(TAG, "onResponse: " + response);

                if (response.isSuccessful()) {
                    //System.out.println(response.body());
                    onNumberSuccess(response.body());

                } else {
                    pDialog.dismiss();
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.w(TAG, "onFailure: " + t.getMessage());
                pDialog.dismiss();
                //Toast.makeText(Main2Activity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
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

    private void onNumberSuccess(JsonObject jsonObject) {
        try {
            utilisateur = new Gson().fromJson(new Gson().toJson(jsonObject.getAsJsonObject("success").
                    getAsJsonObject("data")), Utilisateur.class);

            assurer = new Gson().fromJson(new Gson().toJson(utilisateur.getObject()), Assurer.class);
            assurer.setUtilisateur(utilisateur);

            System.out.println("\n client: \n "+ assurer);
        } catch (Exception e) {
            e.printStackTrace();
        }

        resetData(assurer);
        disabledInput();
        pDialog.dismiss();
        ifAssurerExist.dismiss();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Utilisateur utilisateur = new Utilisateur(
                        etNom.getText().toString(), etPrenoms.getText().toString(), etTelephone.getRawText(),
                        etEmail.getText().toString(), etSexe.getSelectedItem().toString(), etDateNaissance.getText().toString(),
                        assurer.getUtilisateur().getSituationMatrimoniale(), etAdresse.getText().toString(), assurer.getUtilisateur().isProspect(),
                        assurer.getUtilisateur().isActif(), assurer.getUtilisateur().getCommune());

                Assurer assurerdsd = new Assurer(etProfession.getText().toString(), etEmployeur.getText().toString(),utilisateur, false
                );

                assurer.setId(assurer.getId());



                MarchandMainActivity.getContrat().setAssurer(assurerdsd);

                System.out.println(MarchandMainActivity.getContrat());

                replaceFraglent(new AddClientStepThree());
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

                            Assurer assurer = new Assurer(etProfession.getText().toString(), etEmployeur.getText().toString(),utilisateur, false
                            );

                            assurer.setId(0L);

                            MarchandMainActivity.getContrat().setAssurer(assurer);

                            replaceFraglent(new AddClientStepThree());

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

                            if (phonePrefixes.get(i).getNum().equals(etTelephone.getRawText().substring(0,2))) {
                                isValidePhone = true;
                            }
                        }

                        if (!isValidePhone) {

                            tvTelephone.setText("Ce numéro n'est pas attribué pour le code postal 00225");
                            tvTelephone.setVisibility(View.VISIBLE);

                        } else {

                            Assurer assurer = new Assurer(etProfession.getText().toString(), etEmployeur.getText().toString(),utilisateur, false
                            );

                            assurer.setId(0L);

                            MarchandMainActivity.getContrat().setAssurer(assurer);

                            replaceFraglent(new AddClientStepThree());

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



}
