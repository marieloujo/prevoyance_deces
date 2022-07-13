package bj.assurance.assurancedeces.fragment.supermarchand;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

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
import com.labters.lottiealertdialoglibrary.LottieAlertDialog;
import com.shagi.materialdatepicker.date.DatePickerFragmentDialog;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.fragment.app.FragmentActivity;
import bj.assurance.assurancedeces.R;
import bj.assurance.assurancedeces.activity.SuperMarchandActivity;
import bj.assurance.assurancedeces.model.Commune;
import bj.assurance.assurancedeces.model.Marchand;
import bj.assurance.assurancedeces.model.Userable;
import bj.assurance.assurancedeces.model.Utilisateur;
import bj.assurance.assurancedeces.model.customModel.ValidationEror;
import bj.assurance.assurancedeces.serviceImplementation.ContratServiceImplementation;
import bj.assurance.assurancedeces.serviceImplementation.SuperMarchandServiceImplementation;
import bj.assurance.assurancedeces.utils.CatchApiError;
import bj.assurance.assurancedeces.utils.ContratFormUtils;
import bj.assurance.assurancedeces.utils.ShowDialog;
import bj.assurance.assurancedeces.utils.onConnexionListener;
import bj.assurance.assurancedeces.utils.token.TokenManager;
import br.com.sapereaude.maskedEditText.MaskedEditText;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


public class AddMarchand extends Fragment {



    private Button cancel, next;


    private TextView tvNom, tvPrenoms, tvEmail, tvAdresse, tvSituationMatrimoniale,
                    tvSexe, tvDateNaissance, tvCommune, tvTelephone;


    
    private EditText etNom, etPrenoms, etAdresse, etDateNaissance, etEmail;
    private Spinner etSituationMatrimoniale, etSexe, etCommune, phoneIdentity;
    private MaskedEditText etTelephone;



    private ShowDialog showDialog;
    private DatePickerFragmentDialog datePickerFragmentDialog;
    private ContratFormUtils contratFormUtils;
    
    
    
    
    
    public AddMarchand() {
        // Required empty public constructor
    }

   
    
    
    

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    
    
    
    
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        
        
        View view = inflater.inflate(R.layout.fragment_add_marchand, container, false);



        init(view);

        
        return view;
    }











    private void init(View view) {

        findView(view);
        onClickListener();
        initValue();

    }




    private void initValue() {


        contratFormUtils = new ContratFormUtils(getContext(), SuperMarchandActivity.getUtilisateur().getCommune().getDepartement().getId());
        showDialog = new ShowDialog(getContext());


        makeSpinnerList();
        makeDatePicker();


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






    private void findView(View view) {

        cancel = view.findViewById(R.id.annuler);
        next = view.findViewById(R.id.suivant);

        etNom = view.findViewById(R.id.etNomMarchand);
        etPrenoms = view.findViewById(R.id.etPrenomMarchand);
        etEmail = view.findViewById(R.id.et_email);
        etAdresse = view.findViewById(R.id.etAdresseMarchand);
        etSituationMatrimoniale = view.findViewById(R.id.etSituationMatrimonialeMarchand);
        etSexe = view.findViewById(R.id.etSexeMarchand);
        etDateNaissance = view.findViewById(R.id.dateEditText);
        etTelephone = view.findViewById(R.id.etTelephoneMarchand);
        etCommune = view.findViewById(R.id.etCommuneMarchand);
        phoneIdentity = view.findViewById(R.id.phoneIdentity);


        tvNom = view.findViewById(R.id.tvNomMarchand);
        tvPrenoms = view.findViewById(R.id.tvPrenomMarchand);
        tvEmail = view.findViewById(R.id.tvEmailMarchand);
        tvAdresse = view.findViewById(R.id.tvAdresseDomicileMarchand);
        tvSituationMatrimoniale = view.findViewById(R.id.tvSituationMatrimonialeMarchand);
        tvSexe = view.findViewById(R.id.tvetSexeMarchand);
        tvDateNaissance = view.findViewById(R.id.tvDateNaissanceMarchand);
        tvCommune = view.findViewById(R.id.tvCommuneMarchand);
        tvTelephone = view.findViewById(R.id.tvTelephoneMarchand);

    }





    private void onClickListener () {

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                assert getFragmentManager() != null;

                confirmationtoCancel();
            }
        });


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showDialog.showLoaddingDialog("Vérification des données", "Veuillez patientez");

                validation(getUserFromForm());

            }
        });


        etDateNaissance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                assert getFragmentManager() != null;
                datePickerFragmentDialog.show(getFragmentManager(), "tag");

            }
        });

    }




    private void makeSpinnerList() {

        contratFormUtils.makeCommuneList(etCommune);
        contratFormUtils.makePhoneIdentityList(phoneIdentity);
        contratFormUtils.makeSexeSpinnerList(etSexe);
        contratFormUtils.makeSmSpinnerList(etSituationMatrimoniale);

    }











    private void validation(final Utilisateur utilisateur) {

        new ContratServiceImplementation(TokenManager.getInstance(getActivity()
                .getSharedPreferences("prefs", MODE_PRIVATE))
                .getToken())
                .validation(utilisateur)
                .enqueue(new Callback<JsonObject>() {

                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {


                        if (response.isSuccessful()) {

                            resetFormError();

                            try {

                                showDialog.getAlertDialog().dismiss();

                            } catch (Exception ignored) {}


                            if (contratFormUtils.verifTelephone(etTelephone.getRawText(), phoneIdentity)) {

                                utilisateur.setTelephone((phoneIdentity.getSelectedItem().toString()).substring(1)
                                        + etTelephone.getRawText());

                                Userable userable = new Userable();
                                userable.setUtilisateur(utilisateur);

                                Marchand marchand = new Marchand(
                                        "0", "O",
                                        SuperMarchandActivity.getSuperMarchand(),
                                        userable
                                );

                                createMarchand(marchand);


                            } else  {

                                tvTelephone.setText("Ce numéro n'est pas attribué pour le code " + phoneIdentity.getSelectedItem().toString());
                                tvTelephone.setVisibility(View.VISIBLE);

                            }


                        } else {

                            try {

                                showDialog.getAlertDialog().dismiss();

                            } catch (Exception e) {}

                            new CatchApiError(response.code()).catchError(getContext());


                            if (response.code() != 401) {

                                try {

                                    JSONObject jObjError = new JSONObject(response.errorBody().string());

                                    Type listType = new TypeToken<List<ValidationEror>>() {}.getType();

                                    List<ValidationEror> validationEror = new Gson().fromJson (
                                            jObjError.getJSONObject("errors").getString("message"),
                                            listType
                                    );

                                    makeEditError(validationEror);

                                    try {

                                        showDialog.getAlertDialog().dismiss();

                                    } catch (Exception ignored) {}

                                } catch (Exception e) {
                                    e.printStackTrace();

                                    String string = new CatchApiError(response.code()).catchError(getContext());

                                    showDialog.showErrorDialog("Error", string, "D'accord");

                                }


                            }

                        }


                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {

                        try {

                            showDialog.getAlertDialog().dismiss();

                        } catch (Exception ignored) {}


                        try {

                            if (new onConnexionListener(getContext()).checkConnexion()) {

                                showDialog.showWarningDialog("Echec de connexion",
                                        "Imposible d'établir une connexion avec le seuveur. Veuillez réessayer ultérieurement");


                            } else {

                                showDialog.showNoInternetDialog().setPositiveListener(new ClickListener() {
                                    @Override
                                    public void onClick(@NotNull LottieAlertDialog lottieAlertDialog) {
                                        lottieAlertDialog.dismiss();
                                        validation(utilisateur);
                                    }
                                })
                                        .build()
                                        .show();
                            }

                        } catch (Exception e) {

                            e.printStackTrace();

                        }

                    }
                });



    }







    private void createMarchand(final Marchand marchand) {

        showDialog.showLoaddingDialog("Enregistrement en cours", "Veuillez patientez");

        new SuperMarchandServiceImplementation(

                TokenManager.getInstance(getActivity()
                        .getSharedPreferences("prefs", MODE_PRIVATE))
                        .getToken()

        ).createMarchand(marchand)

            .enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {


                    Gson gson = new Gson();


                    if (response.isSuccessful()) {

                        try {

                            System.out.println(response.body());

                            assert response.body() != null;

                            Type listType = new TypeToken<List<Marchand>>() {}.getType();
                            List<Marchand> marchand1 = gson.fromJson(
                                    response.body().getAsJsonArray("data"), listType);

                            try {

                                showDialog.getAlertDialog().dismiss();

                            } catch (Exception e) {}

                            LottieAlertDialog lottieAlertDialog = showDialog.showSuccessDialog("", "Marchand crée avec succes")
                                .setPositiveListener(new ClickListener() {
                                    @Override
                                    public void onClick(@NotNull LottieAlertDialog lottieAlertDialog) {
                                        resetForm();
                                        lottieAlertDialog.dismiss();
                                    }
                                })
                                .build();
                            lottieAlertDialog.setCancelable(false);
                            lottieAlertDialog.show();


                        } catch (Exception e) {

                            e.printStackTrace();

                            showDialog.getAlertDialog().dismiss();
                            createMarchandFail();

                        }

                    } else {

                        if (response.code() != 401) {

                            try {

                                JSONObject jObjError = new JSONObject(response.errorBody().string());
                                showDialog.getAlertDialog().dismiss();

                                createMarchandFail();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        } else new CatchApiError(response.code()).catchError(getContext());

                    }


                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {


                    try {

                        showDialog.getAlertDialog().dismiss();

                    } catch (Exception ignored) {}


                    try {

                        if (new onConnexionListener(getContext()).checkConnexion()) {


                            showDialog.showWarningDialog("Echec de connexion",
                                    "Imposible d'établir une connexion avec le seuveur. Veuillez réessayer ultérieurement");


                        } else {

                            showDialog.showNoInternetDialog().setPositiveListener(new ClickListener() {
                                @Override
                                public void onClick(@NotNull LottieAlertDialog lottieAlertDialog) {
                                    lottieAlertDialog.dismiss();
                                    createMarchand(marchand);
                                }
                            })
                                    .build()
                                    .show();

                        }

                    } catch (Exception e) {

                        e.printStackTrace();

                    }


                }
            });



    }








    private void createMarchandFail() {

        LottieAlertDialog lottieAlertDialog =
                showDialog.showErrorDialog("Enregistrement échoué",
                        "L'enregistrement du marchand à échouer",
                        "Reéssayer")
                        .setNegativeText("Annuler")
                        .setNegativeButtonColor(Color.parseColor("#f44242"))
                        .setNegativeTextColor(Color.parseColor("#ffeaea"))

                        .setNegativeListener(new ClickListener() {
                            @Override
                            public void onClick(@NotNull LottieAlertDialog lottieAlertDialog) {

                                resetForm();
                                lottieAlertDialog.dismiss();

                            }
                        }).build();

        lottieAlertDialog.setCancelable(false);
        lottieAlertDialog.show();

    }







    private void makeEditError(List<ValidationEror> validationEror)  {

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







    private void resetForm() {

        etNom.setText("");
        etPrenoms.setText("");
        etEmail.setText("");
        etAdresse.setText("");
        etDateNaissance.setText("");
        etTelephone.setText("");



        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setSexe("Sexe");
        utilisateur.setCommune(new Commune("Commune"));
        utilisateur.setSituationMatrimoniale("Situation matrimoniale");


        contratFormUtils.resetSpiner(utilisateur, etSexe, etCommune, etSituationMatrimoniale);


    }





    private void resetFormError() {

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





    private Utilisateur getUserFromForm() {


        String sm, email, adresse;

        if (etSituationMatrimoniale.getSelectedItem().toString().equals("Situation matrimoniale")) {

            sm = null;

        } else
            sm = etSituationMatrimoniale.getSelectedItem().toString();



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









    private void confirmationtoCancel() {

        if (etNom.getText().toString().isEmpty() && etPrenoms.getText().toString().isEmpty() &&
                etAdresse.getText().toString().isEmpty()  &&
                etDateNaissance.getText().toString().isEmpty() && etEmail.getText().toString().isEmpty() &&
                etTelephone.getRawText().isEmpty()) {


            ((FragmentActivity) getActivity()).finish();


        } else {

            showDialog.showQuestionDialog("Confirmaion", "Voulez vous vraiment annuler ??")

                    .setPositiveListener(new ClickListener() {
                        @Override
                        public void onClick(@NotNull LottieAlertDialog lottieAlertDialog) {
                            lottieAlertDialog.dismiss();

                            ((FragmentActivity) getActivity()).finish();

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

    }




}
