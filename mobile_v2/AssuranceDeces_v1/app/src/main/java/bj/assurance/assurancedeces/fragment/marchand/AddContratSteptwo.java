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
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import bj.assurance.assurancedeces.R;
import bj.assurance.assurancedeces.activity.ContentAddBeneficier;
import bj.assurance.assurancedeces.activity.MarchandActivity;
import bj.assurance.assurancedeces.model.Assurer;
import bj.assurance.assurancedeces.model.Client;
import bj.assurance.assurancedeces.model.Marchand;
import bj.assurance.assurancedeces.model.Userable;
import bj.assurance.assurancedeces.model.Utilisateur;
import bj.assurance.assurancedeces.model.customModel.AccessToken;
import bj.assurance.assurancedeces.model.customModel.ValidationEror;
import bj.assurance.assurancedeces.serviceImplementation.ContratServiceImplementation;
import bj.assurance.assurancedeces.serviceImplementation.UtilisateurServiceImplementation;
import bj.assurance.assurancedeces.utils.CatchApiError;
import bj.assurance.assurancedeces.utils.ContratFormUtils;
import bj.assurance.assurancedeces.utils.OnBackPressedListener;
import bj.assurance.assurancedeces.utils.ShowDialog;
import bj.assurance.assurancedeces.utils.token.TokenManager;
import br.com.sapereaude.maskedEditText.MaskedEditText;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


public class AddContratSteptwo extends Fragment implements OnBackPressedListener {


    private Button cancel, next;

    private TextView tvNom, tvPrenoms, tvEmail, tvAdresse, tvSituationMatrimoniale, tvSexe, tvDateNaissance,
            tvProfession, tvEmployeur, tvCommune, tvTelephone;

    private EditText etNom, etPrenoms, etAdresse, etProfession, etEmployeur, etDateNaissance, etEmail;

    @SuppressLint("StaticFieldLeak")
    static private Spinner etSituationMatrimoniale, etSexe, etCommune, phoneIdentity;
    static private MaskedEditText etTelephone;

    private ShowDialog showDialog;

    private DatePickerFragmentDialog datePickerFragmentDialog;

    private ContratFormUtils contratFormUtils;



    public AddContratSteptwo() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_contrat_steptwo, container, false);

        init(view);

        return view;
    }



    private void init(View view) {

        findView(view);
        initValue();
        onClickListener();

    }



    private void initValue() {

        contratFormUtils = new ContratFormUtils(getContext(), MarchandActivity.getUtilisateur().getCommune().getDepartement().getId());
        showDialog = new ShowDialog(getContext());


        makeSpinnerList();
        makeDatePicker();


            beginDialog();


    }








    private void checkValue() {


        final Utilisateur utilisateur = MarchandActivity.getContrat().getAssurer().getUserable().getUtilisateur();

        utilisateur.setUserables(new ArrayList<Userable>());
        utilisateur.getUserables().add(MarchandActivity.getContrat().getAssurer().getUserable());


        resetData(MarchandActivity.getContrat().getAssurer().getUserable().getUtilisateur());


System.out.println(MarchandActivity.getContrat().getAssurer().getUserable().getUtilisateur());


        if (utilisateur.getId() != 0) {

            contratFormUtils.resetSpiner(utilisateur, etSexe, etCommune, etSituationMatrimoniale);


            if (MarchandActivity.getContrat().getAssurer().getId() != 0) {

                disabledInput(true, utilisateur);

                if (MarchandActivity.getContrat().getAssurer().getEmployeur() == null)
                    etEmployeur.setEnabled(true);


                if (MarchandActivity.getContrat().getAssurer().getProfession() == null)
                    etEmployeur.setEnabled(true);

            } else {

                disabledInput(false, utilisateur);

            }


            next.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("NewApi")
                @Override
                public void onClick(View view) {

                    if (utilisateur.getId() == 0) {

                        validation(getUserFromForm());

                    } else {

                        olderUsersetnextClickListener(utilisateur, MarchandActivity.getContrat().getAssurer());

                    }


                }
            });



        }


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

        etNom = view.findViewById(R.id.etNomAssure);
        etPrenoms = view.findViewById(R.id.etPrenomAssure);
        etEmail = view.findViewById(R.id.et_email);
        etAdresse = view.findViewById(R.id.etAdresseAssure);
        etSituationMatrimoniale = view.findViewById(R.id.etSituationMatrimonialeAssure);
        etSexe = view.findViewById(R.id.etSexeAssure);
        etDateNaissance = view.findViewById(R.id.dateEditText);
        etEmployeur = view.findViewById(R.id.etEmployeur);
        etProfession = view.findViewById(R.id.etProfession);
        etTelephone = view.findViewById(R.id.etTelephoneAssure);
        etCommune = view.findViewById(R.id.etCommuneAssure);
        phoneIdentity = view.findViewById(R.id.phoneIdentity);


        tvNom = view.findViewById(R.id.tvNomAssure);
        tvPrenoms = view.findViewById(R.id.tvPrenomAssure);
        tvEmail = view.findViewById(R.id.tvEmailAssure);
        tvAdresse = view.findViewById(R.id.tvAdresseDomicileAssure);
        tvSituationMatrimoniale = view.findViewById(R.id.tvSituationMatrimonialeAssure);
        tvSexe = view.findViewById(R.id.tvetSexeAssure);
        tvDateNaissance = view.findViewById(R.id.tvDateNaissanceAssure);
        tvCommune = view.findViewById(R.id.tvCommuneAssure);
        tvProfession = view.findViewById(R.id.tvProfessionAssure);
        tvEmployeur = view.findViewById(R.id.tvEmployeurAssure);
        tvTelephone = view.findViewById(R.id.tvTelephoneAssure);

    }



    private void onClickListener () {

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                assert getFragmentManager() != null;
                ((FragmentActivity) getContext()).getSupportFragmentManager().popBackStack();

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


                            if (contratFormUtils.verifTelephone(utilisateur.getTelephone(), phoneIdentity)) {

                                utilisateur.setTelephone((phoneIdentity.getSelectedItem().toString()).substring(1)
                                        + etTelephone.getRawText());

                                Assurer assure = new Assurer(etProfession.getText().toString(),
                                        etEmployeur.getText().toString(), utilisateur, false
                                );

                                assure.setId(0L);

                                System.out.println(assure);

                                MarchandActivity.getContrat().setAssurer(assure);
                                getContext().startActivity(new Intent(getContext(), ContentAddBeneficier.class));


                            } else  {

                                tvTelephone.setText("Ce numéro n'est pas attribué pour le code " + phoneIdentity.getSelectedItem().toString());
                                tvTelephone.setVisibility(View.VISIBLE);

                            }


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

                                } catch (Exception ignored) {}

                            } catch (Exception e) {
                                e.printStackTrace();

                                String string = new CatchApiError(response.code()).catchError(getContext());

                                showDialog.getAlertDialog().changeDialog(new LottieAlertDialog.Builder(getContext(), DialogTypes.TYPE_ERROR)
                                        .setTitle("Error")
                                        .setDescription(string)
                                );

                            }

                        }


                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {

                        System.out.println("failure valid");

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



    private Utilisateur getUserFromForm() {


        String sm, email;

        if (etSituationMatrimoniale.getSelectedItem().toString().equals("Situation matrimoniale")) {

            sm = null;

        } else
            sm = etSituationMatrimoniale.getSelectedItem().toString();



        if (etEmail.getText().toString().equals("")) {

            email = null;

        } else email = etEmail.getText().toString();



        return new Utilisateur(
                etNom.getText().toString(),
                etPrenoms.getText().toString(),
                etTelephone.getRawText(),
                email,
                contratFormUtils.getSelectedSexe(etSexe),
                etDateNaissance.getText().toString(),
                sm,
                etAdresse.getText().toString(),
                false,
                contratFormUtils.getSelectedCommune(etCommune)

        );

    }



    private void replaceFragment(Fragment fragment) {

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_main, fragment).commit();

    }









    private void beginDialog() {


        showDialog.showQuestionDialog("Vérification de l'assurer",
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
                        assureDialog();

                    }
                })

                .build()
                .show();

    }



    private void assureDialog() {

        showDialog.showQuestionDialog("Vérification de l'assurer",
                "Est-ce le souscripteur enrégistrer précedemment ??")

                .setPositiveListener(new ClickListener() {
                    @Override
                    public void onClick(@NotNull LottieAlertDialog lottieAlertDialog) {

                        resetDataAssurer(MarchandActivity.getContrat().getClient().getUserable().getUtilisateur());

                        next.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Assurer assurer = new Gson().fromJson(
                                        new Gson().toJson(MarchandActivity.getContrat().getClient()), Assurer.class
                                );

                                MarchandActivity.getContrat().setAssurer(assurer);

                                if (!AddContratStepone.isAssure())
                                    MarchandActivity.getContrat().setAssureIsSouscripteur(true);

                                getContext().startActivity(new Intent(getContext(), ContentAddBeneficier.class));

                            }
                        });



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


                    showDialog.showLoaddingDialog("", "Recherche");

                    findClientbyNumber(
                            TokenManager.getInstance(getActivity().
                                    getSharedPreferences("prefs", MODE_PRIVATE)).
                                    getToken(),
                            (spinner.getSelectedItem().toString()).substring(1) + textView.getRawText()
                    );


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

        final Utilisateur utilisateur = new Gson().fromJson(new Gson().toJson(response.body().getAsJsonObject("success").
                getAsJsonObject("data")), Utilisateur.class);

        final Assurer assurer = getUserableType(utilisateur);

        resetData(utilisateur);


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            olderUsersetnextClickListener(utilisateur, assurer);



                //replaceFragment(new AddContratStepthree());

            }
        });


        showDialog.getAlertDialog().dismiss();


    }







    private void olderUsersetnextClickListener(Utilisateur utilisateur, Assurer assurer) {

        Assurer assurer1 = new Assurer();

        Utilisateur utilisateur1 = getUserFromForm();
        utilisateur1.setId(utilisateur.getId());

        utilisateur1.setTelephone((phoneIdentity.getSelectedItem().toString()).substring(1)
                + etTelephone.getRawText());



        if (assurer != null) {

            validationAsurer(utilisateur);


        } else {

            assurer1 = new Assurer( etProfession.getText().toString(), etEmployeur.getText().toString(), utilisateur1);
            assurer1.setId(0L);
            assurer1.getUserable().getUtilisateur().setId(utilisateur.getId());



            MarchandActivity.getContrat().setAssurer(assurer1);

            getContext().startActivity(new Intent(getContext(), ContentAddBeneficier.class));

        }

    }













    private Assurer getUserableType(Utilisateur utilisateur) {

        Assurer assurer = null;


        for (Userable userable : utilisateur.getUserables()) {

            switch (userable.getUserableType()) {

                case "App\\Models\\Assurer":

                    assurer = new Gson().fromJson(new Gson().toJson(userable.getObject()), Assurer.class);

                    break;

                default:

            }

        }

        return assurer;

    }





    private void resetDataAssurer(Utilisateur utilisateur) {

        etNom.setText(utilisateur.getNom());
        etPrenoms.setText(utilisateur.getPrenom());
        etAdresse.setText(utilisateur.getAdresse());
        etEmail.setText(utilisateur.getEmail());



        etProfession.setText(MarchandActivity.getContrat().getClient().getProfession());
        etEmployeur.setText(MarchandActivity.getContrat().getClient().getEmployeur());

        disabledInput(true, utilisateur);



        etDateNaissance.setText(utilisateur.getDateNaissance());

        etTelephone.setText(utilisateur.getTelephone());

        contratFormUtils.resetSpiner(utilisateur, etSexe, etCommune, etSituationMatrimoniale);
    }





    private void resetData(Utilisateur utilisateur) {

        etNom.setText(utilisateur.getNom());
        etPrenoms.setText(utilisateur.getPrenom());
        etAdresse.setText(utilisateur.getAdresse());
        etEmail.setText(utilisateur.getEmail());




        try {

            if (getUserableType(utilisateur) != null) {

                Assurer assurer = getUserableType(utilisateur);
                etProfession.setText(assurer.getProfession());
                etEmployeur.setText(assurer.getEmployeur());

                disabledInput(true, utilisateur);

            } else {

                disabledInput(false, utilisateur);

            }

        } catch (Exception e) {

            e.printStackTrace();

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


        if (b) {

            etProfession.setEnabled(false);
            etEmployeur.setEnabled(false);

        } else {

            etProfession.setEnabled(true);
            etEmployeur.setEnabled(true);

        }


    }




    private void confirmationtoCancel() {

        if (etNom.getText().toString().isEmpty() && etPrenoms.getText().toString().isEmpty() &&
                etAdresse.getText().toString().isEmpty() && etPrenoms.getText().toString().isEmpty() &&
                etEmployeur.getText().toString().isEmpty() && etDateNaissance.getText().toString().isEmpty() &&
                etEmail.getText().toString().isEmpty() && etTelephone.getRawText().toString().isEmpty()) {


            ((FragmentActivity) getActivity()).getSupportFragmentManager().popBackStack();


        } else {

            showDialog.showQuestionDialog("Confirmaion", "Voulez vous vraiment effectuer cette action ??" +
                    " Vous perdriez toutes les données entrées.")

                    .setPositiveListener(new ClickListener() {
                        @Override
                        public void onClick(@NotNull LottieAlertDialog lottieAlertDialog) {

                            lottieAlertDialog.dismiss();
                            ((FragmentActivity) getActivity()).getSupportFragmentManager().popBackStack();

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









    private void validationAsurer(final Utilisateur utilisateur) {

        final Assurer assurer = getUserableType(utilisateur);

        new ContratServiceImplementation(

            TokenManager.getInstance(getActivity().
                    getSharedPreferences("prefs", MODE_PRIVATE)).
                    getToken()

        ).validationAssurer(assurer.getId())

            .enqueue(new Callback<JsonObject>() {


                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {


                    if (response.isSuccessful()) {

                        boolean isValid = response.body().getAsJsonObject("success").get("message").getAsBoolean();

                        System.out.println(isValid);


                        if (!isValid) {

                            Utilisateur utilisateur1 = getUserFromForm();
                            utilisateur1.setId(utilisateur.getId());

                            utilisateur1.setTelephone((phoneIdentity.getSelectedItem().toString()).substring(1)
                                    + etTelephone.getRawText());

                            Userable userable = utilisateur.getUserables().get(0);
                            userable.setUtilisateur(utilisateur1);
                            userable.setObject(null);

                            Assurer assurer1 = new Assurer(assurer.getId(), etProfession.getText().toString(), etEmployeur.getText().toString(), userable);


                            MarchandActivity.getContrat().setAssurer(assurer1);

                            System.out.println(assurer1);

                            //getContext().startActivity(new Intent(getContext(), ContentAddBeneficier.class));

                        } else {

                            new ShowDialog(getContext()).showWarningDialog("", "L'assurer ne peut être assuré plus de trois fois.");

                        }


                    } else {



                    }


                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {

                }


            });


    }


    @Override
    public void doBack() {

        confirmationtoCancel();

    }
}
