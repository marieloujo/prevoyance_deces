package bj.assurance.assurancedeces.fragment.marchand;


import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.labters.lottiealertdialoglibrary.ClickListener;
import com.labters.lottiealertdialoglibrary.DialogTypes;
import com.labters.lottiealertdialoglibrary.LottieAlertDialog;
import com.shagi.materialdatepicker.date.DatePickerFragmentDialog;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import bj.assurance.assurancedeces.R;
import bj.assurance.assurancedeces.activity.FragmentActivity;
import bj.assurance.assurancedeces.activity.MarchandActivity;
import bj.assurance.assurancedeces.model.Benefice;
import bj.assurance.assurancedeces.model.Beneficiaire;
import bj.assurance.assurancedeces.model.Client;
import bj.assurance.assurancedeces.model.Userable;
import bj.assurance.assurancedeces.model.Utilisateur;
import bj.assurance.assurancedeces.model.customModel.AccessToken;
import bj.assurance.assurancedeces.model.customModel.ValidationEror;
import bj.assurance.assurancedeces.serviceImplementation.ContratServiceImplementation;
import bj.assurance.assurancedeces.serviceImplementation.UtilisateurServiceImplementation;
import bj.assurance.assurancedeces.utils.CatchApiError;
import bj.assurance.assurancedeces.utils.ContratFormUtils;
import bj.assurance.assurancedeces.utils.ShowDialog;
import bj.assurance.assurancedeces.utils.token.TokenManager;
import br.com.sapereaude.maskedEditText.MaskedEditText;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


public class AddContratStepthree extends Fragment {


    private List<String> strings = new ArrayList<>();


    private ShowDialog showDialog;


    private TextView tvNom, tvPrenoms, tvEmail, tvAdresse, tvSituationMatrimoniale, tvSexe, tvDateNaissance,
            tvCommune, tvTelephone, tvQualification, tvTaux;

    private EditText etNom, etPrenoms, etAdresse, etDateNaissance, etEmail , etTaux ;

    @SuppressLint("StaticFieldLeak")
    private Spinner etSituationMatrimoniale, etSexe, etCommune, phoneIdentity, etQualification;
    private MaskedEditText etTelephone;

    private DatePickerFragmentDialog datePickerFragmentDialog;


    private Beneficiaire beneficiaire1 = new Beneficiaire();
    private Utilisateur utilisateur = new Utilisateur();
    private boolean isGetedbyPhone = false;


    private ContratFormUtils contratFormUtils;



    public AddContratStepthree() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_contrat_stepthree, container, false);


        init(view);


        return view;
    }



    private void init(View view) {

        findView(view);
        findItemView(view);
        initValue();
        onClickListener();

    }



    private void findView(View view) {



    }




    private void findItemView(View view) {

        etNom = view.findViewById(R.id.etNomBeneficiaire);
        etPrenoms = view.findViewById(R.id.etPrenomBeneficiaire);
        etEmail = view.findViewById(R.id.et_email);
        etAdresse = view.findViewById(R.id.etAdresseBeneficiaire);
        etSituationMatrimoniale = view.findViewById(R.id.etSituationMatrimonialeBeneficiaire);
        etSexe = view.findViewById(R.id.etSexeBeneficiaire);
        etDateNaissance = view.findViewById(R.id.dateEditText);
        etTelephone = view.findViewById(R.id.etTelephoneBeneficiaire);
        etCommune = view.findViewById(R.id.etCommuneBeneficiaire);
        phoneIdentity = view.findViewById(R.id.phoneIdentity);
        etQualification = view.findViewById(R.id.etQualification);
        etTaux = view.findViewById(R.id.etTaux);



        tvNom = view.findViewById(R.id.tvNomBeneficiaire);
        tvPrenoms = view.findViewById(R.id.tvPrenomBeneficiaire);
        tvEmail = view.findViewById(R.id.tvEmailBeneficiaire);
        tvAdresse = view.findViewById(R.id.tvAdresseDomicileBeneficiaire);
        tvSituationMatrimoniale = view.findViewById(R.id.tvSituationMatrimonialeBeneficiaire);
        tvSexe = view.findViewById(R.id.tvetSexeBeneficiaire);
        tvDateNaissance = view.findViewById(R.id.tvDateNaissanceBeneficiaire);
        tvCommune = view.findViewById(R.id.tvCommuneBeneficiaire);
        tvTelephone = view.findViewById(R.id.tvTelephoneBeneficiaire);
        tvQualification = view.findViewById(R.id.tvQualification);
        tvTaux = view.findViewById(R.id.tvTaux);

    }






    @SuppressLint("WrongConstant")
    private void initValue () {

        strings.add("");

        showDialog = new ShowDialog(getContext());

        contratFormUtils = new ContratFormUtils(getContext(), MarchandActivity.getUtilisateur().getCommune().getDepartement().getId());


        contratFormUtils.makePhoneIdentityList(phoneIdentity);
        contratFormUtils.makeSexeSpinnerList(etSexe);
        contratFormUtils.makeCommuneList(etCommune);
        contratFormUtils.makeQualificationList(etQualification);
        contratFormUtils.makeSmSpinnerList(etSituationMatrimoniale);


        beginDialog();
        makeDatePicker();

    }




    private void onClickListener() {


        etDateNaissance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                assert getFragmentManager() != null;
                datePickerFragmentDialog.show(getFragmentManager(), "tag");

            }
        });



    }





    private void makeDatePicker() {


        DatePickerFragmentDialog.OnDateSetListener onDateSetListener = new DatePickerFragmentDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePickerFragmentDialog view, int year, int monthOfYear, int dayOfMonth) {

                Calendar calendar = Calendar.getInstance();
                calendar.set(year, monthOfYear, dayOfMonth);

                SimpleDateFormat simpleDateFormat = new  SimpleDateFormat("yyyy-MM-dd", Locale.FRENCH);
                simpleDateFormat.format(calendar.getTime());

                etDateNaissance.setText(simpleDateFormat.format(calendar.getTime()));

                datePickerFragmentDialog.dismiss();

            }
        };

        datePickerFragmentDialog = DatePickerFragmentDialog.newInstance(onDateSetListener, 1999,11, 4);


        int minYear = (new Date().getYear() - 74) + 1900 ;
        int maxYear = (new Date().getYear() - 18) + 1900 ;


        datePickerFragmentDialog.setYearRange(minYear, maxYear);

    }





    private void confirmDialog() {



        showDialog.showQuestionDialog("Ajout d'un beneficier",
                "Voulez-vous ajouter un nouveau béneficier ? "
        ).setPositiveListener(new ClickListener()
        {
            @Override
            public void onClick(@NotNull LottieAlertDialog lottieAlertDialog) {

                lottieAlertDialog.dismiss();

                strings.add("yhgvgh");

            }
        })

        .setNegativeListener(new ClickListener()
        {
            @Override
            public void onClick(@NotNull LottieAlertDialog lottieAlertDialog) {



            }
        })
        .build()
        .show();



    }





    public Utilisateur getUtilisateurFromForm() {

        String sm, email, adresse;

        if (etSituationMatrimoniale.getSelectedItem().toString().equals("Situation matrimoniale")) {

            sm = "";

        } else {

            sm = etSituationMatrimoniale.getSelectedItem().toString();

        }

        if (etEmail.getText().toString().equals("")) {

            email = null;

        } else email = etEmail.getText().toString();


        if (etAdresse.getText().toString().equals("")) {

            adresse = null;

        } else adresse = etAdresse.getText().toString();

        
        return new Utilisateur(
                etNom.getText().toString(),
                etPrenoms.getText().toString(),
                etTelephone.getRawText(),
                email,
                contratFormUtils.getSelectedSexe(etSexe),
                etDateNaissance.getText().toString(),
                sm,
                adresse,
                false,
                contratFormUtils.getSelectedCommune(etCommune)

        );

    }




    public void makeEditError(List<ValidationEror> validationEror )  {

        for (ValidationEror validationEror1 : validationEror) {

            try {

                  tvNom.setText(validationEror1.getErrorNom().get(0));
                  tvNom.setVisibility(View.VISIBLE);

            } catch (Exception ignored) {

                  tvNom.setText("");
                  tvNom.setVisibility(View.INVISIBLE);

            }

            try {

                tvPrenoms.setText(validationEror1.getErrorPrenom().get(0));
                tvPrenoms.setVisibility(View.VISIBLE);

            } catch (Exception ignored) {

                tvPrenoms.setText("");
                tvPrenoms.setVisibility(View.INVISIBLE);

            }


            try {

                  tvTelephone.setText(validationEror1.getErrorTelephone().get(0));
                  tvTelephone.setVisibility(View.VISIBLE);

            } catch (Exception ignored) {

                  tvTelephone.setText("");
                  tvTelephone.setVisibility(View.INVISIBLE);

            }


            try {

                  tvEmail.setText(validationEror1.getErrorEmail().get(0));
                  tvEmail.setVisibility(View.VISIBLE);

            } catch (Exception ignored) {

                  tvEmail.setText("");
                  tvEmail.setVisibility(View.INVISIBLE);

            }


            try {

                  tvSexe.setText(validationEror1.getErrorsexe().get(0));
                  tvSexe.setVisibility(View.VISIBLE);

            } catch (Exception ignored) {

                  tvSexe.setText("");
                  tvSexe.setVisibility(View.INVISIBLE);

            }


            try {

                  tvDateNaissance.setText(validationEror1.getErrorDateNaissance().get(0));
                  tvDateNaissance.setVisibility(View.VISIBLE);

            } catch (Exception ignored) {

                  tvDateNaissance.setText("");
                  tvDateNaissance.setVisibility(View.INVISIBLE);

            }


            try {

                  tvCommune.setText(validationEror1.getErrorcommune().get(0));
                  tvCommune.setVisibility(View.VISIBLE);

            } catch (Exception ignored) {

                  tvCommune.setText("");
                  tvCommune.setVisibility(View.INVISIBLE);

            }


            try {

                  tvAdresse.setText(validationEror1.getErrorAdresse().get(0));
                  tvAdresse.setVisibility(View.VISIBLE);

            } catch (Exception ignored) {

                  tvAdresse.setText("");
                  tvAdresse.setVisibility(View.INVISIBLE);

            }

        }

    }




   public String getQualification() {

        if (etQualification.getSelectedItem().toString().equals("Qualification") ||
                etQualification.getSelectedItem().toString().equals("Autre")) {

            return  "";

        } else {

            return etQualification.getSelectedItem().toString();

        }

   }







    public void resetFormError() {

         tvNom.setText("");
         tvPrenoms.setText("");
         tvEmail.setText("");
         tvAdresse.setText("");
         tvSituationMatrimoniale.setText("");
         tvSexe.setText("");
         tvDateNaissance.setText("");
         tvCommune.setText("");
         tvTelephone.setText("");

    }




    private void beginDialog() {


        showDialog.showQuestionDialog("Vérification du béneficier",
                "Est-ce un ancien utilisateur ??")

                .setPositiveListener(new ClickListener() {
                    @Override
                    public void onClick(@NotNull LottieAlertDialog lottieAlertDialog) {

                        openDialog();
                        lottieAlertDialog.dismiss();

                    }
                })

                .setNegativeListener(new ClickListener() {
                    @Override
                    public void onClick(@NotNull LottieAlertDialog lottieAlertDialog) {

                        lottieAlertDialog.dismiss();

                    }
                })

                .build()
                .show();

    }






    private void openDialog() {

        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.find_by_telephone_number, null);

        final MaskedEditText textView = alertLayout.findViewById(R.id.etTelephone);
        final TextView textView1 = alertLayout.findViewById(R.id.tvError);


        final Spinner spinner = alertLayout.findViewById(R.id.phoneIdentity);
        contratFormUtils.makePhoneIdentityList(spinner);



        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("Recherche par télephone");
        alert.setView(alertLayout);
        alert.setCancelable(false);

        alert.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });



        alert.setPositiveButton("Recherche", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (textView.getRawText().isEmpty()) {


                    textView1.setVisibility(View.VISIBLE);


                } else {

                    //if (new ContratFormUtils(getContext()).verifTelephone(textView.getRawText(), spinner)) {

                    textView1.setVisibility(View.INVISIBLE);


                    showDialog.showLoaddingDialog("", "Recherche");

                    System.out.println("Recherche" + (spinner.getSelectedItem().toString()).substring(1) );


                    findClientbyNumber(
                            TokenManager.getInstance(getActivity().
                                    getSharedPreferences("prefs", MODE_PRIVATE)).
                                    getToken(),
                            (spinner.getSelectedItem().toString()).substring(1) + textView.getRawText()
                    );

                    /*} else {

                        textView1.setVisibility(View.VISIBLE);

                    }*/

                }
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }





    private void findClientbyNumber (AccessToken accessToken, String number) {


        System.out.println("begin");

        new UtilisateurServiceImplementation(getContext())

                .findUserbyPhoneNumber(number, accessToken)
                .enqueue(new Callback<JsonObject>() {


                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                        if (response.isSuccessful()) {

                            getPhoneresponse(response);

                        } else {


                            System.out.println("error");

                            String string = new CatchApiError(response.code()).catchError(getContext());


                            try {

                                JSONObject jObjError = new JSONObject(response.errorBody().string());

                                System.out.println(jObjError);

                                try {

                                    showDialog.getAlertDialog().dismiss();

                                } catch (Exception ignored) {}

                            } catch (Exception e) {
                                e.printStackTrace();

                                showDialog.getAlertDialog().changeDialog(new LottieAlertDialog.Builder(getContext(), DialogTypes.TYPE_ERROR)
                                        .setTitle("Error")
                                        .setDescription(string)
                                );

                            }


                        }

                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {


                        t.printStackTrace();
                        System.out.println("failure");
                        System.out.println(t.getMessage());


                    }


                });

    }





    private void getPhoneresponse(Response<JsonObject> response) {

        utilisateur = new Gson().fromJson(new Gson().toJson(response.body().getAsJsonObject("success").
                getAsJsonObject("data")), Utilisateur.class);

        beneficiaire1 = getUserableType(utilisateur);
        isGetedbyPhone = true;

        System.out.println("beneficiaire1 " + beneficiaire1);

        resetData(utilisateur);

        showDialog.getAlertDialog().dismiss();

    }





    private Beneficiaire getUserableType(Utilisateur utilisateur) {

        Beneficiaire beneficiaire = null;


        for (Userable userable : utilisateur.getUserables()) {

            switch (userable.getUserableType()) {

                case "App\\Models\\Beneficiaire":

                    beneficiaire = new Gson().fromJson(new Gson().toJson(userable.getObject()), Beneficiaire.class);

                    break;

                default:

            }

        }

        return beneficiaire;

    }



    private void resetData(Utilisateur utilisateur) {

        etNom.setText(utilisateur.getNom());
        etPrenoms.setText(utilisateur.getPrenom());
        etAdresse.setText(utilisateur.getAdresse());
        etEmail.setText(utilisateur.getEmail());


        if (getUserableType(utilisateur) != null) {

            Beneficiaire beneficiaire = getUserableType(utilisateur);

            disabledInput(true, utilisateur);

        } else {

            disabledInput(false, utilisateur);

        }


        etDateNaissance.setText(utilisateur.getDateNaissance());

        etTelephone.setText(utilisateur.getTelephone());

        contratFormUtils.resetSpiner(utilisateur, etSexe, etCommune, etSituationMatrimoniale);

    }




    private void disabledInput(boolean b, Utilisateur utilisateur) {

        if (utilisateur.getNom() != null)
            etNom.setEnabled(   false);


        if ( utilisateur.getPrenom() != null)
            etPrenoms.setEnabled(false);


        if (utilisateur.getEmail() != null)
            etEmail.setEnabled(false);


        if (utilisateur.getAdresse() != null)
            etAdresse.setEnabled(false);


        if (utilisateur.getTelephone() != null)
            etTelephone.setEnabled(false);


        if (utilisateur.getDateNaissance() != null)
            etDateNaissance.setEnabled(false);



        if (utilisateur.getSexe() != null)
            etSexe.setEnabled(false);
        etSexe.setClickable(false);


        if (utilisateur.getSituationMatrimoniale() !=  null)
            etSituationMatrimoniale.setEnabled(false);
        etSituationMatrimoniale.setClickable(false);


        if (utilisateur.getCommune() != null)
            etCommune.setEnabled(false);
        etCommune.setFocusable(false);


    }










    public Beneficiaire getBeneficiaire1() {
        return beneficiaire1;
    }

    public void setBeneficiaire1(Beneficiaire beneficiaire1) {
        this.beneficiaire1 = beneficiaire1;
    }


    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public  boolean isIsGetedbyPhone() {
        return isGetedbyPhone;
    }

    public  void setIsGetedbyPhone(boolean isGetedbyPhone) {
        this.isGetedbyPhone = isGetedbyPhone;
    }




    public Spinner getPhoneIdentity() {
        return phoneIdentity;
    }

    public MaskedEditText getEtTelephone() {
        return etTelephone;
    }



    public void makeTauxError(String text) {

        tvTaux.setText(text);
        tvTaux.setVisibility(View.VISIBLE);

    }



    public void resetTauxError() {

        tvTaux.setText("");
        tvTaux.setVisibility(View.INVISIBLE);

        tvQualification.setText("");
        tvQualification.setVisibility(View.INVISIBLE);

    }



    public TextView getTvTaux() {
        return tvTaux;
    }

    public EditText getEtTaux() {
        return etTaux;
    }



    public void makeQualifError() {

        tvQualification.setText("La qualification est obligatoire");
        tvQualification.setVisibility(View.VISIBLE);

    }





}
