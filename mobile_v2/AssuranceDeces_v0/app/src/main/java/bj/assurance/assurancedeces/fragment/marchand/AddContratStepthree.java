package bj.assurance.assurancedeces.fragment.marchand;


import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.labters.lottiealertdialoglibrary.ClickListener;
import com.labters.lottiealertdialoglibrary.DialogTypes;
import com.labters.lottiealertdialoglibrary.LottieAlertDialog;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.FragmentManager;
import bj.assurance.assurancedeces.R;
import bj.assurance.assurancedeces.activity.MarchandActivity;
import bj.assurance.assurancedeces.model.Benefice;
import bj.assurance.assurancedeces.model.Beneficiaire;
import bj.assurance.assurancedeces.model.Utilisateur;
import bj.assurance.assurancedeces.model.customModel.ValidationEror;
import bj.assurance.assurancedeces.serviceImplementation.ContratServiceImplementation;
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

    private Button prev, next;

    private ShowDialog showDialog;


    private TextView tvNom, tvPrenoms, tvEmail, tvAdresse, tvSituationMatrimoniale, tvSexe, tvDateNaissance,
            tvProfession, tvEmployeur, tvCommune, tvTelephone, tvQualification;

    private EditText etNom, etPrenoms, etAdresse, etProfession, etEmployeur, etDateNaissance, etEmail;

    @SuppressLint("StaticFieldLeak")
    static private Spinner etSituationMatrimoniale, etSexe, etCommune, phoneIdentity, etQualification;
    static private MaskedEditText etTelephone;


    private boolean isValidForm = false;

    private List<Benefice> benefices = new ArrayList<>();


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

        prev = view.findViewById(R.id.annuler);
        next = view.findViewById(R.id.suivant);

    }




    private void findItemView(View view) {

        etNom = view.findViewById(R.id.etNomBeneficiaire);
        etPrenoms = view.findViewById(R.id.etPrenomBeneficiaire);
        etEmail = view.findViewById(R.id.et_email);
        etAdresse = view.findViewById(R.id.etAdresseBeneficiaire);
        etSituationMatrimoniale = view.findViewById(R.id.etSituationMatrimonialeBeneficiaire);
        etSexe = view.findViewById(R.id.etSexeBeneficiaire);
        etDateNaissance = view.findViewById(R.id.dateEditText);
        etEmployeur = view.findViewById(R.id.etEmployeur);
        etProfession = view.findViewById(R.id.etProfession);
        etTelephone = view.findViewById(R.id.etTelephoneBeneficiaire);
        etCommune = view.findViewById(R.id.etCommuneBeneficiaire);
        phoneIdentity = view.findViewById(R.id.phoneIdentity);
        etQualification = view.findViewById(R.id.etQualification);


        tvNom = view.findViewById(R.id.tvNomBeneficiaire);
        tvPrenoms = view.findViewById(R.id.tvPrenomBeneficiaire);
        tvEmail = view.findViewById(R.id.tvEmailBeneficiaire);
        tvAdresse = view.findViewById(R.id.tvAdresseDomicileBeneficiaire);
        tvSituationMatrimoniale = view.findViewById(R.id.tvSituationMatrimonialeBeneficiaire);
        tvSexe = view.findViewById(R.id.tvetSexeBeneficiaire);
        tvDateNaissance = view.findViewById(R.id.tvDateNaissanceBeneficiaire);
        tvCommune = view.findViewById(R.id.tvCommuneBeneficiaire);
        tvProfession = view.findViewById(R.id.tvProfessionBeneficiaire);
        tvEmployeur = view.findViewById(R.id.tvEmployeurBeneficiaire);
        tvTelephone = view.findViewById(R.id.tvTelephoneBeneficiaire);
        tvQualification = view.findViewById(R.id.tvQualification);

    }






    @SuppressLint("WrongConstant")
    private void initValue () {

        strings.add("");

        showDialog = new ShowDialog(getContext());

        contratFormUtils = new ContratFormUtils(getContext());


        contratFormUtils.makePhoneIdentityList(phoneIdentity);
        contratFormUtils.makeSexeSpinnerList(etSexe);
        contratFormUtils.makeCommuneList(etCommune);
        contratFormUtils.makeQualificationList(etQualification);
        contratFormUtils.makeSmSpinnerList(etSituationMatrimoniale);



    }




    private void onClickListener() {



        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


              validation(getUtilisateurFromForm());



                if (isValidForm) {
                    System.out.println(benefices);
                }

            }
        });


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





    private Utilisateur getUtilisateurFromForm() {
        
        return new Utilisateur (
                etNom.getText().toString(),
                etPrenoms.getText().toString(),
                etTelephone.getRawText(),
                etEmail.getText().toString(),
                contratFormUtils.getSelectedSexe(etSexe),
                etDateNaissance.getText().toString(),
                etSituationMatrimoniale.getSelectedItem().toString(),
                etAdresse.getText().toString(),
                false,
                false,
                contratFormUtils.getSelectedCommune(etCommune)

        );

    }




    private void makeEditError(List<ValidationEror> validationEror )  {

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
        etEmployeur.setText("");
        etProfession.setText("");
        etTelephone.setText("");

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
         tvProfession.setText("");
         tvEmployeur.setText("");
         tvTelephone.setText("");

    }




    private void replaceFragment(Fragment fragment, String title) {


        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_main, fragment).commit();


        MarchandActivity.getFrameTitle().setText(title);

    }




    private void validation(final Utilisateur utilisateur) {

        new ContratServiceImplementation(TokenManager.getInstance(getActivity()
                .getSharedPreferences("prefs", MODE_PRIVATE))
                .getToken())
                .validation(utilisateur)
                .enqueue(new Callback<JsonObject>() {

                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {


                        if (response.isSuccessful()) {

                            resetFormError( );

                            try {

                                showDialog.getAlertDialog().dismiss();

                            } catch (Exception ignored) {}


                            MarchandActivity.getContrat().getBenefices().add(new Benefice(etQualification.getSelectedItem().toString(),
                                    "", new Beneficiaire(0L, utilisateur)));

                        } else {


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

                                } catch (Exception e) {e.printStackTrace();}

                            } catch (Exception e) {
                                e.printStackTrace();

                                String string = new CatchApiError(response.code()).catchError(getContext());

                                showDialog.getAlertDialog().changeDialog(new LottieAlertDialog.Builder(getContext(), DialogTypes.TYPE_ERROR)
                                        .setTitle("Error")
                                        .setDescription(string)
                                );

                            }


                            //tvTelephone.setText("Ce numéro n'est pas attribué pour le code " + phoneIdentity.getSelectedItem().toString());
                            //tvTelephone.setVisibility(View.VISIBLE);


                        }


                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {

                        try {

                            showDialog.getAlertDialog().dismiss();

                        } catch (Exception ignored) {}


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
                });



    }



}
