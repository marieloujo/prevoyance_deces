package bj.assurance.assurancedeces.BottomSheet;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.arthurivanets.bottomsheets.BaseBottomSheet;
import com.arthurivanets.bottomsheets.BottomSheet;
import com.arthurivanets.bottomsheets.config.BaseConfig;
import com.arthurivanets.bottomsheets.config.Config;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.labters.lottiealertdialoglibrary.ClickListener;
import com.labters.lottiealertdialoglibrary.LottieAlertDialog;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import bj.assurance.assurancedeces.R;
import bj.assurance.assurancedeces.activity.MarchandActivity;
import bj.assurance.assurancedeces.fragment.marchand.Accueil;
import bj.assurance.assurancedeces.model.Client;
import bj.assurance.assurancedeces.model.Compte;
import bj.assurance.assurancedeces.model.Marchand;
import bj.assurance.assurancedeces.model.SuperMarchand;
import bj.assurance.assurancedeces.model.Userable;
import bj.assurance.assurancedeces.model.Utilisateur;
import bj.assurance.assurancedeces.model.customModel.ValidationEror;
import bj.assurance.assurancedeces.serviceImplementation.CommuneServiceImplementation;
import bj.assurance.assurancedeces.serviceImplementation.CompteServiceImplementation;
import bj.assurance.assurancedeces.serviceImplementation.MarchandServiceImplementation;
import bj.assurance.assurancedeces.serviceImplementation.UtilisateurServiceImplementation;
import bj.assurance.assurancedeces.utils.CatchApiError;
import bj.assurance.assurancedeces.utils.ContratFormUtils;
import bj.assurance.assurancedeces.utils.CustomJsonObject;
import bj.assurance.assurancedeces.utils.GetResponseObject;
import bj.assurance.assurancedeces.utils.ShowDialog;
import bj.assurance.assurancedeces.utils.token.TokenManager;
import br.com.sapereaude.maskedEditText.MaskedEditText;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class TransfertCommissiontoMarchand extends BaseBottomSheet {


    private MaskedEditText matricule;
    private EditText montant;
    private Button button, retry;
    private TextView errorMatricule, errorMontant, solde, error;
    private Spinner phoneIdentity;


    private ContratFormUtils contratFormUtils;
    private ShowDialog showDialog;


    private ProgressBar progressBar;
    private LinearLayout contentForm, catchError;






    public TransfertCommissiontoMarchand(@NonNull Activity hostActivity) {
        super(hostActivity, new Config.Builder(hostActivity).build());
    }



    public TransfertCommissiontoMarchand(@NonNull Activity hostActivity, @NonNull BaseConfig config) {
        super(hostActivity, config);
    }




    @NonNull
    @Override
    protected View onCreateSheetContentView(@NonNull Context context) {

        View view = LayoutInflater.from(context).inflate(R.layout.form_transfert_to_marchand, this, false);


        init(view);


        return view;
    }




    private void init(View view)  {

        findView(view);
        initValue();
        onClikListener();

    }



    private void findView (View view) {

        matricule = view.findViewById(R.id.etTelephoneMarchand);
        montant = view.findViewById(R.id.montant_paye);
        phoneIdentity = view.findViewById(R.id.phoneIdentity);

        errorMatricule = view.findViewById(R.id.errorMatricule);
        errorMontant = view.findViewById(R.id.errorMontant);
        solde = view.findViewById(R.id.solde);


        progressBar = view.findViewById(R.id.main_progress);
        error = view.findViewById(R.id.error_text);
        retry = view.findViewById(R.id.retry);


        catchError = view.findViewById(R.id.catch_error);
        contentForm = view.findViewById(R.id.content_main);


        button = view.findViewById(R.id.validate);

    }



    private void initValue() {

        solde.setText(MarchandActivity.getMarchand().getCommission());

        contratFormUtils = new ContratFormUtils(getContext());
        contratFormUtils.makePhoneIdentityList(phoneIdentity);
        showDialog = new ShowDialog(getContext());


        refresh(MarchandActivity.getMarchand().getId(), "chargement");

    }



    private void onClikListener () {

        button.setOnClickListener(new OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if (verifData()) {

                    //if (contratFormUtils.verifTelephone(matricule.getRawText(), phoneIdentity)) {

                        showDialog.showLoaddingDialog("Recherche du marchand", "Veuillez patienter");

                        findMarchandByTelephone(matricule.getRawText());

                    //} else {

                        /*errorMatricule.setText("Ce numéro n'est pas attribé au code " +
                                phoneIdentity.getSelectedItem().toString());*/

                    //}
                }
            }
        });



        retry.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                refresh(MarchandActivity.getMarchand().getId(), "chargement");

            }
        });


    }




    @SuppressLint("SetTextI18n")
    public boolean verifData() {

        boolean verif = true;

        if (montant.getText().toString().isEmpty()) {

            verif = false;
            errorMontant.setText("Vous devez renseigner le montant");

        } else {

            errorMontant.setText("");

            if (Float.valueOf(montant.getText().toString()) >
                    Float.valueOf(MarchandActivity.getMarchand().getCommission())) {

                verif = false;
                errorMontant.setText("Vous ne pouvez pas transféret plus de la somme dont vous disposez dans votre commission");

            } else

                errorMontant.setText("");

        }

        if (matricule.getText().toString().isEmpty()) {

            verif = false;
            errorMatricule.setText("Vous devez renseigner le matricule");

        } else
            errorMatricule.setText("");

        return verif;
    }




    private void findMarchandByTelephone(String telephone) {

        new UtilisateurServiceImplementation(getContext())
                .findUserbyPhoneNumber(
                        telephone,
                        TokenManager.getInstance(((FragmentActivity)getContext())
                                .getSharedPreferences("prefs", MODE_PRIVATE))
                                .getToken()
                )
                .enqueue(new Callback<JsonObject>() {

                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                        if (response.isSuccessful()) {


                            Utilisateur utilisateur = new Gson().fromJson(response.body().getAsJsonObject("success")
                                    .getAsJsonObject("data"),
                                    Utilisateur.class);


                            boolean isMarchand = false;

                            for (Userable userable : utilisateur.getUserables()) {

                                if (isMarchand(userable)) {

                                    isMarchand = true;
                                    transfertConfirmation(userable);
                                    return;

                                } else {

                                    errorMatricule.setText("Ce numéro ne correspond à aucun marchand");

                                    showDialog.getAlertDialog().dismiss();

                                }

                            }


                            System.out.println(utilisateur);

                        } else {

                            showDialog.getAlertDialog().dismiss();

                            new CatchApiError(response.code());

                            try {

                                JSONObject jObjError = new JSONObject(response.errorBody().string());

                                String string = (jObjError.getJSONObject("success").getString("message"));

                                errorMatricule.setText("Aucun marchand trouvé pour ce numéro");

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }



                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        t.printStackTrace();

                        showDialog.getAlertDialog().dismiss();
                    }

                }
        );

    }




    private boolean isMarchand(Userable userable) {

        switch (userable.getUserableType()) {


            case "App\\Models\\Marchand":

                return true;

            default:

                return  false;

        }

    }




    private void transfertConfirmation(final Userable utilisateur) {


        String sexe = "";

        if (utilisateur.getUtilisateur().getSexe().equals(""))

            sexe = "Mme";

        else sexe = "Mr";


        final String finalSexe = sexe;

        showDialog.showQuestionDialog(
                    "Confirmation",
                "Voulez vous vraiment tranferer " + montant.getText().toString() +
                          "  points à " + sexe +
                          utilisateur.getUtilisateur().getNom() + " " + utilisateur.getUtilisateur().getPrenom()

        ).setPositiveListener(new ClickListener() {
                    @Override
                    public void onClick(@NotNull LottieAlertDialog lottieAlertDialog) {

                        lottieAlertDialog.dismiss();
                        showDialog.getAlertDialog().dismiss();

                        showDialog.showLoaddingDialog(

                                "Transfert de commission",
                                "Vous etes entrain de transférer "
                                        + montant.getText().toString() +
                                        " points à " +
                                        finalSexe +
                                        utilisateur.getUtilisateur().getNom() + " " +
                                        utilisateur.getUtilisateur().getPrenom()
                        );


                        Marchand marchand = new Marchand(utilisateur.getUserableId());

                        Compte compte = new Compte(montant.getText().toString(), marchand);

                        System.out.println(new Gson().toJson(compte));

                        makeTransfert(compte,
                                MarchandActivity.getMarchand().getId()

                        );

                    }
                })

                .setNegativeListener(new ClickListener() {
                    @Override
                    public void onClick(@NotNull LottieAlertDialog lottieAlertDialog) {
                        lottieAlertDialog.dismiss();
                        showDialog.getAlertDialog().dismiss();
                    }
                })

                .build()
                .show();

    }




    private void makeTransfert(final Compte compte, final Long id) {

        System.out.println(compte);

        new CompteServiceImplementation(getContext())
                .transferCommission(compte, id)
                .enqueue(new Callback<JsonObject>() {

                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {



                        if (response.isSuccessful()) {

                                showDialog.getAlertDialog().dismiss();

                                refresh(MarchandActivity.getMarchand().getId(), "refresh");

                        } else {


                            try {

                                System.out.println(response.errorBody().string());

                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            showDialog.getAlertDialog().dismiss();

                            showDialog.showErrorDialog("Le tensfert à échouer", "",
                                    "Ressayer")

                                .setPositiveListener(new ClickListener() {
                                    @Override
                                    public void onClick(@NotNull LottieAlertDialog lottieAlertDialog) {
                                        lottieAlertDialog.dismiss();
                                        makeTransfert(compte, id);
                                    }
                                })

                                .setNegativeListener(new ClickListener() {
                                    @Override
                                    public void onClick(@NotNull LottieAlertDialog lottieAlertDialog) {
                                        lottieAlertDialog.dismiss();
                                    }
                                })
                                .build().show();


                        }

                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {

                        t.printStackTrace();

                    }

                });


    }





    private void refresh(Long id, final String string) {

        reset();


        if (string.equals("chargement")) {

            progressBar.setVisibility(VISIBLE);
            catchError.setVisibility(INVISIBLE);
            contentForm.setVisibility(INVISIBLE);

        }

        new MarchandServiceImplementation(

                TokenManager.getInstance(((FragmentActivity)getContext())
                        .getSharedPreferences("prefs", MODE_PRIVATE))
                        .getToken()

        ).getCreditAndComissionOfMarchand(id)

                .enqueue(new Callback<JsonObject>() {

                    @SuppressLint("SetTextI18n")
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                        if (response.isSuccessful()) {

                            JsonObject jsonObjectData = response.body()
                                    .getAsJsonObject("success")
                                    .getAsJsonObject("data");

                            solde.setText(jsonObjectData.get("commission").getAsString());


                            replaceFragment(new Accueil());


                            switch (string) {


                                case "refresh":

                                    showDialog.showSuccessDialog("Transfert effectué avec succes",
                                            "")
                                            .setPositiveListener(new ClickListener() {
                                                @Override
                                                public void onClick(@NotNull LottieAlertDialog lottieAlertDialog) {
                                                    lottieAlertDialog.dismiss();
                                                }
                                            })
                                            .build().show();

                                    break;


                                case "chargement":

                                    progressBar.setVisibility(INVISIBLE);
                                    catchError.setVisibility(INVISIBLE);
                                    contentForm.setVisibility(VISIBLE);

                                    break;

                            }


                        } else {


                            new CatchApiError(response.code()).catchError(getContext());

                            if (string.equals("chargement")) {

                                progressBar.setVisibility(INVISIBLE);
                                catchError.setVisibility(VISIBLE);
                                contentForm.setVisibility(INVISIBLE);

                                error.setText("Le chargement de votre solde de commission à échouer." +
                                        " Veuillez réessayer ultérieurement");


                            }

                        }

                    }




                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {

                        if (string.equals("chargement")) {

                            progressBar.setVisibility(INVISIBLE);
                            catchError.setVisibility(VISIBLE);
                            contentForm.setVisibility(INVISIBLE);

                            error.setText("Auncune connexion internet, le chargement de votre solde de commission à échouer." +
                                    " Vérifier votre connexion internet et réessayer");


                        }

                    }

                });


        contentForm.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                return;
            }
        });

    }






    private void replaceFragment(Fragment fragment) {


        FragmentManager fragmentManager = ((FragmentActivity)getContext()).getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_main, fragment).commit();


    }






    private void reset() {

        montant.setText("");
        matricule.setText("");

    }



}
