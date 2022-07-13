package bj.assurance.assurancedeces.fragment.marchand;


import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
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

import androidx.fragment.app.FragmentManager;
import bj.assurance.assurancedeces.R;
import bj.assurance.assurancedeces.activity.MarchandActivity;
import bj.assurance.assurancedeces.model.Assurer;
import bj.assurance.assurancedeces.model.Client;
import bj.assurance.assurancedeces.model.Marchand;
import bj.assurance.assurancedeces.model.Utilisateur;
import bj.assurance.assurancedeces.model.customModel.AccessToken;
import bj.assurance.assurancedeces.model.customModel.ValidationEror;
import bj.assurance.assurancedeces.serviceImplementation.ClientServiceImplementation;
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


public class AddContratStepone extends Fragment {


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

    private boolean isPreview = false;






    @SuppressLint("ValidFragment")
    public AddContratStepone(boolean isPreview) {
        this.isPreview = isPreview;
    }

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
        initValue();
        onClickListener();

    }




    private void initValue() {

        contratFormUtils = new ContratFormUtils(getContext());

        makeSpinnerList();

        showDialog = new ShowDialog(getContext());


        makeDatePicker();


        beginDialog();


        if (isPreview) {

            resetData(MarchandActivity.getContrat().getClient().getUtilisateur());
            disabledInput(true);

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


                            if (contratFormUtils.verifTelephone(utilisateur.getTelephone(), phoneIdentity)) {

                                Client client = new Client(etProfession.getText().toString(),
                                        etEmployeur.getText().toString(), utilisateur.
                                );

                                client.setId(0L);

                                MarchandActivity.getContrat().setClient(client);

                                System.out.println(MarchandActivity.getContrat());

                                replaceFragment(new AddContratSteptwo());

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




    private void replaceFragment(Fragment fragment) {


        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.contentFragment, fragment).commit();


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
        new ContratFormUtils(getContext()).makePhoneIdentityList(spinner);



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

                        findClientbyNumber(
                                TokenManager.getInstance(getActivity().
                                        getSharedPreferences("prefs", MODE_PRIVATE)).
                                        getToken(), textView.getRawText()
                        );

                   /* } else {

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

                        System.out.println(response.body());
                        System.out.println("success");

                        Utilisateur utilisateur = new Gson().fromJson(new Gson().toJson(response.body().getAsJsonObject("success").
                                getAsJsonObject("data")), Utilisateur.class);


                        final Client client = new Gson().fromJson(new Gson().toJson(utilisateur.getUserables().get(0)), Client.class);

                        resetData(utilisateur);

                        //utilisateur.setObject(null);
                        client.setUtilisateur(utilisateur);

                        next.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                MarchandActivity.getContrat().setClient(client);

                                System.out.println(MarchandActivity.getContrat());

                                replaceFragment(new AddContratSteptwo());

                            }
                        });


                            showDialog.getAlertDialog().dismiss();




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





    private void resetData(Utilisateur utilisateur) {

        etNom.setText(utilisateur.getNom());
        etPrenoms.setText(utilisateur.getPrenom());
        etAdresse.setText(utilisateur.getAdresse());
        etEmail.setText(utilisateur.getEmail());


        if (utilisateur.getUserableType().equals("App\\Models\\Client")) {

            Client client = new Gson().fromJson(new Gson().toJson(utilisateur.getObject()), Client.class);
            etProfession.setText(client.getProfession());
            etEmployeur.setText(client.getEmployeur());

            disabledInput(true);

        } else if (utilisateur.getUserableType().equals("App\\Models\\Assurer")) {

            Assurer assurer = new Gson().fromJson(new Gson().toJson(utilisateur.getObject()), Assurer.class);
            etProfession.setText(assurer.getProfession());

            disabledInput(false);

        } else {

            disabledInput(false);

        }









        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


        try {
            etDateNaissance.setText(simpleDateFormat.format(format.parse(utilisateur.getDateNaissance())));
        } catch (Exception e) {
            e.printStackTrace();
        }

        etTelephone.setText(utilisateur.getTelephone());
        resetSpiner(utilisateur);
    }




    private void resetSpiner(Utilisateur utilisateur) {


        for(int i= 0; i < etSexe.getAdapter().getCount(); i++)
        {
            if(etSexe.getAdapter().getItem(i).toString().contains(utilisateur.getSexe()))
            {
                etSexe.setSelection(i);

            }
        }


        for(int i= 0; i < etCommune.getAdapter().getCount(); i++)
        {
            if(etCommune.getAdapter().getItem(i).toString().contains(utilisateur.getCommune().getNom()))
            {
                etCommune.setSelection(i);

            } else {

                

            }
        }

        for(int i= 0; i < etSituationMatrimoniale.getAdapter().getCount(); i++)
        {



            if(etSituationMatrimoniale.getAdapter().getItem(i).toString().contains(utilisateur.getSituationMatrimoniale()))
            {
                etSituationMatrimoniale.setSelection(i);
            }
        }
    }





    private void disabledInput(boolean b) {

        etNom.setEnabled(   false);
        etPrenoms.setEnabled(false);
        etEmail.setEnabled(false);
        etAdresse.setEnabled(false);
        etTelephone.setEnabled(false);
        etDateNaissance.setEnabled(false);

        etSexe.setEnabled(false);
        etSexe.setClickable(false);

        etSituationMatrimoniale.setEnabled(false);
        etSituationMatrimoniale.setClickable(false);

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


            getActivity().startActivity(new Intent(getContext(), MarchandActivity.class));


        } else {

            showDialog.showQuestionDialog("Confirmaion", "Voulez vous vraiment annuler ??" +
                    " vous perdriez toutes les données entrées.")

                .setPositiveListener(new ClickListener() {
                    @Override
                    public void onClick(@NotNull LottieAlertDialog lottieAlertDialog) {
                        lottieAlertDialog.dismiss();
                        getActivity().startActivity(new Intent(getContext(), MarchandActivity.class));

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
