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
import android.widget.Toast;

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
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import bj.assurance.assurancedeces.R;
import bj.assurance.assurancedeces.activity.MarchandActivity;
import bj.assurance.assurancedeces.model.Assurer;
import bj.assurance.assurancedeces.model.Client;
import bj.assurance.assurancedeces.model.Commune;
import bj.assurance.assurancedeces.model.Userable;
import bj.assurance.assurancedeces.model.Utilisateur;
import bj.assurance.assurancedeces.model.customModel.AccessToken;
import bj.assurance.assurancedeces.model.customModel.ValidationEror;
import bj.assurance.assurancedeces.serviceImplementation.ContratServiceImplementation;
import bj.assurance.assurancedeces.serviceImplementation.UtilisateurServiceImplementation;
import bj.assurance.assurancedeces.utils.CatchApiError;
import bj.assurance.assurancedeces.utils.ContratFormUtils;
import bj.assurance.assurancedeces.utils.NotificationReader;
import bj.assurance.assurancedeces.utils.OnBackPressedListener;
import bj.assurance.assurancedeces.utils.ShowDialog;
import bj.assurance.assurancedeces.utils.token.TokenManager;
import br.com.sapereaude.maskedEditText.MaskedEditText;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


public class AddContratStepone extends Fragment implements OnBackPressedListener {


    private Button cancel, next;

    private TextView tvNom, tvPrenoms, tvEmail, tvAdresse, tvSituationMatrimoniale, tvSexe, tvDateNaissance,
            tvProfession, tvEmployeur, tvCommune, tvTelephone;

    private EditText etNom, etPrenoms, etAdresse, etProfession, etEmployeur, etDateNaissance, etEmail;

    @SuppressLint("StaticFieldLeak")
    static private Spinner etSituationMatrimoniale, etSexe, etCommune, phoneIdentity;
    static private MaskedEditText etTelephone;

    TextView textView1;

    private ShowDialog showDialog;

    private DatePickerFragmentDialog datePickerFragmentDialog;

    private ContratFormUtils contratFormUtils;

    private static boolean isGetedbyPhone = false;
    private static boolean isAssure = false;







    public AddContratStepone() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_contrat_stepone, container, false);


        init(view);


        return view;
    }




    private void init(View view) {

        findView(view);
        onClickListener();
        initValue();

    }




    private void initValue() {


        contratFormUtils = new ContratFormUtils(getContext(), MarchandActivity.getUtilisateur().getCommune().getDepartement().getId());
        showDialog = new ShowDialog(getContext());


        makeSpinnerList();
        makeDatePicker();


        if (MarchandActivity.getContrat().getClient() != null) {

            checkValue();


        } else {

            beginDialog();


        }


    }







    private void checkValue() {


        if (isGetedbyPhone) {

            final Utilisateur utilisateur = MarchandActivity.getContrat().getClient().getUserable().getUtilisateur();
            contratFormUtils.resetSpiner(utilisateur, etSexe, etCommune, etSituationMatrimoniale);


            if (MarchandActivity.getContrat().getClient().getId() != 0) {

                disabledInput(true, utilisateur);

                if (MarchandActivity.getContrat().getClient().getEmployeur() == null)
                    etEmployeur.setEnabled(true);


                if (MarchandActivity.getContrat().getClient().getProfession() == null)
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

                        olderUsersetnextClickListener(utilisateur, MarchandActivity.getContrat().getClient());

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

                System.out.println(new Gson().toJson(getUserFromForm()));

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

                                Client client = new Client(etProfession.getText().toString(),
                                        etEmployeur.getText().toString(), utilisateur
                                );

                                client.setId(0L);

                                MarchandActivity.getContrat().setClient(client);

                                System.out.println(new Gson().toJson(client));

                                replaceFragment(new AddContratSteptwo());

                            } else  {

                                tvTelephone.setText("Ce numéro n'est pas attribué pour le code " + phoneIdentity.getSelectedItem().toString());
                                tvTelephone.setVisibility(View.VISIBLE);

                            }


                        } else {

                            try {

                                showDialog.getAlertDialog().dismiss();

                            } catch (Exception e) {}


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







    private void replaceFragment(Fragment fragment) {

        FragmentManager fragmentManager = getFragmentManager();


        fragmentManager.beginTransaction().replace(R.id.content_main, fragment).addToBackStack("AddContratStepone").commit();

    }















    private void beginDialog() {


        showDialog.showQuestionDialog("Vérification du souscripteur",
                "Est-ce un ancien souscripteur ??")

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
        textView1 = alertLayout.findViewById(R.id.tvError);


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

                        textView1.setVisibility(View.INVISIBLE);


                        showDialog.showLoaddingDialog("", "Recherche");

                        System.out.println("Recherche" );

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

        final Client client = getUserableType(utilisateur);

        resetData(utilisateur);
        this.isGetedbyPhone = true;


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                olderUsersetnextClickListener(utilisateur, client);

            }
        });


        showDialog.getAlertDialog().dismiss();


    }







    private void olderUsersetnextClickListener(Utilisateur utilisateur, Client client) {


        Client client1 = new Client();

        Utilisateur utilisateur1 = getUserFromForm();
        utilisateur1.setId(utilisateur.getId());
        utilisateur1.setTelephone((phoneIdentity.getSelectedItem().toString()).substring(1)
                + etTelephone.getRawText());



        if (client != null) {

            Userable userable = new Userable();
            userable.setUtilisateur(utilisateur1);
            userable.setObject(null);

            client1 = new Client(client.getId(), etProfession.getText().toString(), etEmployeur.getText().toString(), userable);

        } else {

            client1 = new Client(etProfession.getText().toString(), etEmployeur.getText().toString(), utilisateur1);
            client1.setId(0L);
            client1.getUserable().getUtilisateur().setId(utilisateur.getId());


        }

        System.out.println("client1" + new Gson().toJson(client1));
        MarchandActivity.getContrat().setClient(client1);

        replaceFragment(new AddContratSteptwo());

    }







    private Client getUserableType(Utilisateur utilisateur) {

        Client client = null;
        Assurer assurer = null;


        for (int i = 0; i < utilisateur.getUserables().size(); i++) {


            Userable userable = utilisateur.getUserables().get(i);

            switch (userable.getUserableType()) {

                case "App\\Models\\Client":

                    client = new Gson().fromJson(new Gson().toJson(userable.getObject()), Client.class);

                    break;

                case "App\\Models\\Assurer":

                    assurer = new Gson().fromJson(new Gson().toJson(userable.getObject()), Assurer.class);

                    break;

                default:

            }

        }

        if (client != null && assurer != null) this.isAssure = true;

        return client;

    }






    private void resetData(Utilisateur utilisateur) {

        etNom.setText(utilisateur.getNom());
        etPrenoms.setText(utilisateur.getPrenom());
        etAdresse.setText(utilisateur.getAdresse());
        etEmail.setText(utilisateur.getEmail());


        if (getUserableType(utilisateur) != null) {

            Client client = getUserableType(utilisateur);
            etProfession.setText(client.getProfession());
            etEmployeur.setText(client.getEmployeur());

            disabledInput(true, utilisateur);

        } else {

            disabledInput(false, utilisateur);

        }


        etDateNaissance.setText(utilisateur.getDateNaissance());

        etTelephone.setText(utilisateur.getTelephone().substring(3));

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


            MarchandActivity.getContrat().setClient(null);
            ((FragmentActivity) getActivity()).finish();


        } else {

            showDialog.showQuestionDialog("Confirmaion", "Voulez vous vraiment annuler ??" +
                    " vous perdriez toutes les données entrées.")

                .setPositiveListener(new ClickListener() {
                    @Override
                    public void onClick(@NotNull LottieAlertDialog lottieAlertDialog) {
                        lottieAlertDialog.dismiss();
                        MarchandActivity.getContrat().setClient(null);
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


    public static boolean isAssure() {
        return isAssure;
    }




    @Override
    public void doBack() {

        confirmationtoCancel();

    }



}
