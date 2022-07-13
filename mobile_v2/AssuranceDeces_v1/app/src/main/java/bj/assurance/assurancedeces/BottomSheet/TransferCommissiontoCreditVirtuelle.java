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
import android.widget.TextView;

import com.arthurivanets.bottomsheets.BaseBottomSheet;
import com.arthurivanets.bottomsheets.config.BaseConfig;
import com.arthurivanets.bottomsheets.config.Config;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.labters.lottiealertdialoglibrary.ClickListener;
import com.labters.lottiealertdialoglibrary.LottieAlertDialog;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import bj.assurance.assurancedeces.R;
import bj.assurance.assurancedeces.activity.MarchandActivity;
import bj.assurance.assurancedeces.fragment.marchand.Accueil;
import bj.assurance.assurancedeces.model.Compte;
import bj.assurance.assurancedeces.model.Marchand;
import bj.assurance.assurancedeces.model.Message;
import bj.assurance.assurancedeces.serviceImplementation.CompteServiceImplementation;
import bj.assurance.assurancedeces.serviceImplementation.MarchandServiceImplementation;
import bj.assurance.assurancedeces.utils.CatchApiError;
import bj.assurance.assurancedeces.utils.NotificationReader;
import bj.assurance.assurancedeces.utils.ShowDialog;
import bj.assurance.assurancedeces.utils.token.TokenManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


public class TransferCommissiontoCreditVirtuelle extends BaseBottomSheet {



    private TextView solde, errorMontant, error;
    private EditText montantTransferer;
    private Button validate, retry;

    private ProgressBar progressBar;
    private ShowDialog showDialog;
    private LinearLayout contentForm, catchError;
    private Compte compte;


    public TransferCommissiontoCreditVirtuelle(@NonNull Activity hostActivity) {
        this(hostActivity, new Config.Builder(hostActivity).build());
    }




    public TransferCommissiontoCreditVirtuelle(@NonNull Activity hostActivity, @NonNull BaseConfig config) {
        super(hostActivity, config);
    }




    @NonNull
    @Override
    protected View onCreateSheetContentView(@NonNull Context context) {


        View view = LayoutInflater.from(context).inflate(R.layout.form_transfert_to_my_compte, this, false);


        init(view);


        return view;
    }



    private void init(View view) {


        findView(view);
        initValue();
        onClickListener();

    }



    private void findView(View view) {

        solde = view.findViewById(R.id.solde);
        montantTransferer = view.findViewById(R.id.montant_transferer);
        validate = view.findViewById(R.id.validate);
        errorMontant = view.findViewById(R.id.errorMontant);

        progressBar = view.findViewById(R.id.main_progress);
        error = view.findViewById(R.id.error_text);
        retry = view.findViewById(R.id.retry);


        catchError = view.findViewById(R.id.catch_error);
        contentForm = view.findViewById(R.id.content_main);


    }



    @SuppressLint("SetTextI18n")
    private void initValue() {

        solde.setText(MarchandActivity.getMarchand().getCommission() + " points");

        showDialog = new ShowDialog(getContext());

        refresh(MarchandActivity.getMarchand().getId(), "chargement");

    }



    private void onClickListener() {

        validate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verifData()) {

                    Marchand marchand = new Marchand(MarchandActivity.getMarchand().getId());

                    compte = new Compte(montantTransferer.getText().toString(), marchand);

                    System.out.println("\n \n Compte: \n " + new Gson().toJson(compte) + "\n");

                    showDialog.showLoaddingDialog(

                            "Transfert de commission",
                            "Vous etes entrain de transférer " +
                                    montantTransferer.getText().toString() +
                                    " points sur votre crédit virtuelle"
                    );

                    makeTransfert(compte, MarchandActivity.getMarchand().getId());

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

        if (montantTransferer.getText().toString().isEmpty()) {

            verif = false;
            errorMontant.setText("Vous devez renseigner le montant");

        } else {

            errorMontant.setText("");

            if (Float.valueOf(montantTransferer.getText().toString()) >
                    Float.valueOf(solde.getText().toString())) {

                verif = false;

                errorMontant.setText("Vous ne pouvez pas transféret plus de la somme" +
                        " dont vous disposez dans votre commission");


            } else errorMontant.setText("");

        }

        return verif;
    }





    private void makeTransfert(final Compte compte, final Long id) {

        new CompteServiceImplementation(getContext())
                .transferCommission(compte, id)
                .enqueue(new Callback<JsonObject>() {

                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                        if (response.isSuccessful()) {

                            System.out.println(response.body());

                            showDialog.getAlertDialog().dismiss();

                            refresh(MarchandActivity.getMarchand().getId(), "refresh");


                        } else {

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

                    }

                });


    }







    private void replaceFragment(Fragment fragment) {

        FragmentManager fragmentManager = ((FragmentActivity)getContext()).getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_main, fragment).commit();
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


                                    new NotificationReader(getContext()).createNotification(new Message(
                                            "Vous venez de transferer " + compte.getMontant() +
                                                    " points de votre commission sur votre crédit virtuel. Solde actuel: \n" +
                                                    " commission " + solde.getText().toString() + " points , \n crédit virtuel " +
                                                    jsonObjectData.get("credit_virtuel").getAsString() + " points.",

                                            null), MarchandActivity.getUtilisateur().getId());


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





    private void reset() {

        montantTransferer.setText("");

    }


}
