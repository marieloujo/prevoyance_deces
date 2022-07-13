package bj.assurance.assurancedeces.fragment.marchand;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
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
import com.labters.lottiealertdialoglibrary.LottieAlertDialog;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import bj.assurance.assurancedeces.R;
import bj.assurance.assurancedeces.activity.MarchandActivity;
import bj.assurance.assurancedeces.activity.SuperMarchandActivity;
import bj.assurance.assurancedeces.model.Commune;
import bj.assurance.assurancedeces.model.Marchand;
import bj.assurance.assurancedeces.model.Userable;
import bj.assurance.assurancedeces.model.Utilisateur;
import bj.assurance.assurancedeces.model.customModel.ValidationEror;
import bj.assurance.assurancedeces.serviceImplementation.ContratServiceImplementation;
import bj.assurance.assurancedeces.serviceImplementation.UtilisateurServiceImplementation;
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


public class AddProspect extends Fragment {



    private EditText nom,prenom, email;
    private Spinner sexe, commune, phoneIdentity;
    private MaskedEditText telephone;

    private TextView tvTelephone, tvCommune, tvPrenoms, tvNom, tvSexe;

    private Button next, cancel;

    private ContratFormUtils contratFormUtils;
    private ShowDialog showDialog;



    public AddProspect() {



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


        return view;
    }




    private void init(View view) {

        findView(view);
        initValue();
        onClickListener();

    }


    private void findView(View view) {

        nom = view.findViewById(R.id.etNomProspect);
        prenom = view.findViewById(R.id.etPrenomProspect);
        sexe = view.findViewById(R.id.etSexeProspect);
        commune = view.findViewById(R.id.etCommuneProspect);
        email = view.findViewById(R.id.et_emailProspect);
        telephone = view.findViewById(R.id.etTelephoneProspect);
        phoneIdentity = view.findViewById(R.id.phoneIdentity);

        tvNom = view.findViewById(R.id.tvNomProspect);
        tvPrenoms = view.findViewById(R.id.tvPrenomProspect);
        tvCommune = view.findViewById(R.id.tvCommuneProspect);
        tvTelephone = view.findViewById(R.id.tvTelephoneProspect);
        tvSexe = view.findViewById(R.id.tvetSexeProspect);

        cancel = view.findViewById(R.id.annuler);
        next = view.findViewById(R.id.suivant);

    }


    private void initValue() {

        contratFormUtils = new ContratFormUtils(getContext(), MarchandActivity.getUtilisateur().getCommune().getDepartement().getId());

        showDialog = new ShowDialog(getContext());

        makeSpinnerList();

    }



    private void makeSpinnerList() {

        contratFormUtils.makeCommuneList(commune);
        contratFormUtils.makeSexeSpinnerList(sexe);
        contratFormUtils.makePhoneIdentityList(phoneIdentity);

    }




    private void onClickListener() {

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            getContext().startActivity(new Intent(getContext(), MarchandActivity.class));

            }
        });


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               validation(getUtilisateurFromForm());

            }
        });

    }












    private void validation(final Utilisateur utilisateur) {

        showDialog.showLoaddingDialog("Vérification des données", "Veuillez patientez");

        utilisateur.setDateNaissance(String.valueOf(new Date()));

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


                            if (contratFormUtils.verifTelephone(telephone.getRawText(), phoneIdentity)) {

                                utilisateur.setTelephone((phoneIdentity.getSelectedItem().toString()).substring(1)
                                        + telephone.getRawText());
                                utilisateur.setDateNaissance(null);
                                utilisateur.setMarchand(new Marchand(MarchandActivity.getMarchand().getId()));

                                createProspect(utilisateur);


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

                                    System.out.println(validationEror);

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

                tvSexe.setText(validationEror1.getErrorPrenom().get(0));
                tvSexe.setVisibility(View.VISIBLE);

            } catch (Exception ignored) {

                tvSexe.setText("");
                tvSexe.setVisibility(View.INVISIBLE);

            }



            try {

                tvTelephone.setText(validationEror1.getErrorsexe().get(0));
                tvTelephone.setVisibility(View.VISIBLE);

            } catch (Exception ignored) {

                tvTelephone.setText("");
                tvTelephone.setVisibility(View.INVISIBLE);

            }


            try {

                tvCommune.setText(validationEror1.getErrorcommune().get(0));
                tvCommune.setVisibility(View.VISIBLE);

            } catch (Exception ignored) {

                tvCommune.setText("");
                tvCommune.setVisibility(View.INVISIBLE);

            }


        }

    }






    private void resetFormError() {

        tvNom.setText("");
        tvPrenoms.setText("");
        tvCommune.setText("");
        tvTelephone.setText("");
        tvSexe.setText("");

    }









    private Utilisateur getUtilisateurFromForm() {

        String mail;

        if (email.getText().toString().equals("")) {

            mail = null;

        } else mail = email.getText().toString();



        return new
                Utilisateur(
                nom.getText().toString(),
                prenom.getText().toString(),
                telephone.getRawText(),
                mail,
                contratFormUtils.getSelectedSexe(sexe),
                null,
                null,
                null,
                true,
                contratFormUtils.getSelectedCommune(commune)
            );
    }







    private void createProspect(final Utilisateur utilisateur) {

        showDialog.showLoaddingDialog("Enregistrement du prospect", "Veuillez patientez");

        new UtilisateurServiceImplementation(getContext())
            .createPospect(utilisateur)
                .enqueue(new Callback<JsonObject>() {

                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                        if (response.isSuccessful()) {

                            try {

                                try {
                                    showDialog.getAlertDialog().dismiss();
                                } catch (Exception e) {}


                                if (response.body().getAsJsonObject("success")
                                        .get("message").getAsString().equals("Prospect enregistré")) {

                                    resetForm();
                                    showDialog.showSuccessDialog("Enregistrement réussi",
                                            "Le prospect à été bien enregistré")

                                            .setPositiveListener(new ClickListener() {

                                                @Override
                                                public void onClick(@NotNull LottieAlertDialog lottieAlertDialog) {
                                                    lottieAlertDialog.dismiss();
                                                    resetForm();
                                                }
                                            }).build().show();

                                } else {

                                    LottieAlertDialog lottieAlertDialog =
                                            showDialog.showErrorDialog("Enregistrement échoué",
                                                    "L'enregistrement du prospect à échouer",
                                                    "Reéssayer")
                                                    .setNegativeText("Annuler")
                                                    .setNegativeButtonColor(Color.parseColor("#f44242"))
                                                    .setNegativeTextColor(Color.parseColor("#ffeaea"))
                                                    .setPositiveListener(new ClickListener() {
                                                        @Override
                                                        public void onClick(@NotNull LottieAlertDialog lottieAlertDialog) {

                                                            createProspect(utilisateur);
                                                            lottieAlertDialog.dismiss();

                                                        }
                                                    })

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



                            } catch (Exception e) {

                                e.printStackTrace();

                            }

                        } else {

                            if (response.code() != 401) {

                                try {

                                    JSONObject jObjError = new JSONObject(response.errorBody().string());

                                    System.out.println(jObjError);

                                    showDialog.getAlertDialog().dismiss();

                                    LottieAlertDialog lottieAlertDialog =
                                            showDialog.showErrorDialog("Enregistrement échoué",
                                                    "L'enregistrement du prospect à échouer",
                                                    "Reéssayer")
                                                    .setNegativeText("Annuler")
                                                    .setNegativeButtonColor(Color.parseColor("#f44242"))
                                                    .setNegativeTextColor(Color.parseColor("#ffeaea"))
                                                    .setPositiveListener(new ClickListener() {
                                                        @Override
                                                        public void onClick(@NotNull LottieAlertDialog lottieAlertDialog) {

                                                            createProspect(utilisateur);
                                                            lottieAlertDialog.dismiss();

                                                        }
                                                    })

                                                    .setNegativeListener(new ClickListener() {
                                                        @Override
                                                        public void onClick(@NotNull LottieAlertDialog lottieAlertDialog) {

                                                            resetForm();
                                                            lottieAlertDialog.dismiss();

                                                        }
                                                    }).build();

                                    lottieAlertDialog.setCancelable(false);
                                    lottieAlertDialog.show();



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

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        try {


                            if (t.getMessage().equals("timeout")) {

                                LottieAlertDialog lottieAlertDialog = showDialog.showErrorDialog("Temps écroulé",
                                        "Les données on pris trop de temps à être envoyé vers le serveur.. Veuillez réessayer ultérieurement",
                                        "Réessayer")

                                        .setPositiveListener(new ClickListener() {
                                            @Override
                                            public void onClick(@NotNull LottieAlertDialog lottieAlertDialog) {

                                                createProspect(utilisateur);
                                                lottieAlertDialog.dismiss();

                                            }
                                        }).build();
                                lottieAlertDialog.setCancelable(false);
                                lottieAlertDialog.show();

                            } else {

                                if (new onConnexionListener(getContext()).checkConnexion()) {

                                    showDialog.showWarningDialog("Echec de connexion",
                                            "Imposible d'établir une connexion avec le seuveur. Veuillez réessayer ultérieurement");

                                } else {

                                    showDialog.showNoInternetDialog().setPositiveListener(new ClickListener() {
                                        @Override
                                        public void onClick(@NotNull LottieAlertDialog lottieAlertDialog) {
                                            lottieAlertDialog.dismiss();
                                            createProspect(utilisateur);
                                        }
                                    })
                                            .build()
                                            .show();
                                }
                            }

                        } catch (Exception e) {

                            e.printStackTrace();

                        }


                    }


                });


    }






    private void resetForm() {

        nom.setText("");
        prenom.setText("");
        telephone.setText("");
        email.setText("");


        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setSexe("Sexe");
        utilisateur.setCommune(new Commune("Commune"));
        utilisateur.setSituationMatrimoniale("Situation matrimoniale");


        contratFormUtils.resetSpiner(utilisateur, sexe, commune, null);


    }









    private void confirmationtoCancel() {

        if (nom.getText().toString().isEmpty() && prenom.getText().toString().isEmpty() &&
                email.getText().toString().isEmpty() && telephone.getRawText().isEmpty()) {


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
