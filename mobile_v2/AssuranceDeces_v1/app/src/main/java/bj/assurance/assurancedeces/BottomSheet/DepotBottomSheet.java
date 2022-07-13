package bj.assurance.assurancedeces.BottomSheet;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.arthurivanets.bottomsheets.BaseBottomSheet;
import com.arthurivanets.bottomsheets.config.BaseConfig;
import com.arthurivanets.bottomsheets.config.Config;
import com.google.gson.JsonObject;
import com.labters.lottiealertdialoglibrary.ClickListener;
import com.labters.lottiealertdialoglibrary.LottieAlertDialog;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import bj.assurance.assurancedeces.R;
import bj.assurance.assurancedeces.activity.MarchandActivity;
import bj.assurance.assurancedeces.fragment.marchand.Accueil;
import bj.assurance.assurancedeces.model.Contrat;
import bj.assurance.assurancedeces.model.Message;
import bj.assurance.assurancedeces.model.Portefeuille;
import bj.assurance.assurancedeces.model.Utilisateur;
import bj.assurance.assurancedeces.serviceImplementation.ContratServiceImplementation;
import bj.assurance.assurancedeces.serviceImplementation.MarchandServiceImplementation;
import bj.assurance.assurancedeces.serviceImplementation.SmsServiceImplementation;
import bj.assurance.assurancedeces.serviceImplementation.UtilisateurServiceImplementation;
import bj.assurance.assurancedeces.utils.CatchApiError;
import bj.assurance.assurancedeces.utils.CustomJsonObject;
import bj.assurance.assurancedeces.utils.GetResponseObject;
import bj.assurance.assurancedeces.utils.NotificationReader;
import bj.assurance.assurancedeces.utils.ShowDialog;
import bj.assurance.assurancedeces.utils.token.TokenManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class DepotBottomSheet extends BaseBottomSheet {



    private EditText reference, montant;
    private Button button;
    private TextView errorReference, errorMontant, soldeCreditVirtuel;
    private ContratServiceImplementation contratServiceImplementation;
    private ShowDialog showDialog;
    private GetResponseObject getResponseObject;
    private Portefeuille portefeuille;
    private Integer paiement;
    private Integer montantDepot;


    public DepotBottomSheet(@NonNull Activity hostActivity, String creditVirtuel) {
        this(hostActivity, new Config.Builder(hostActivity).build());

        soldeCreditVirtuel.setText(creditVirtuel);

    }





    public DepotBottomSheet(@NonNull Activity hostActivity, @NonNull BaseConfig config) {
        super(hostActivity, config);
    }





    @NonNull
    @Override
    protected View onCreateSheetContentView(@NonNull Context context) {


        View view = LayoutInflater.from(context).inflate(R.layout.form_add_transaction, this, false);

        init(view);

        return view;
    }



    private void init(View view) {

        findView(view);
        initValue();
        onClickListener();

    }





    private void findView(View view) {

        reference = view.findViewById(R.id.reference_contat);
        montant = view.findViewById(R.id.montant_paye);

        errorMontant = view.findViewById(R.id.errorMontant);
        errorReference = view.findViewById(R.id.errorReference);
        soldeCreditVirtuel = view.findViewById(R.id.soldeCreditVirtuel);

        button = view.findViewById(R.id.validate);

    }





    private void initValue() {

        contratServiceImplementation = new ContratServiceImplementation(
                TokenManager.getInstance(getContext().getSharedPreferences("prefs", MODE_PRIVATE)).getToken());

        showDialog = new ShowDialog(getContext());

        getResponseObject = new GetResponseObject();

    }




    private void onClickListener() {

        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {


                //sendSms("branlycaele", "jetaimebranly", "Prevoyance", "22967266867", makeSmsText(),"5617");

                if (makeEditErorr()) {

                    showDialog.showLoaddingDialog("Recherche du contrat", "Veuillez patienter");

                    findContratbyReference(reference.getText().toString());

                }

            }
        });

    }





    @SuppressLint("SetTextI18n")
    private boolean makeEditErorr() {

        boolean isValide = true;



        if (reference.getText().toString().isEmpty()) {


            isValide = false;

            errorReference.setText("Vous devez renseigner la réference du contrat");
            errorReference.setVisibility(VISIBLE);

        }  else {

            errorReference.setVisibility(INVISIBLE);
        }

        if (montant.getText().toString().isEmpty()) {

            isValide = false;

            errorMontant.setText("Vous devez renseigner le montant");
            errorMontant.setVisibility(VISIBLE);

        } else {

            errorMontant.setVisibility(INVISIBLE);

            if (Integer.valueOf(montant.getText().toString()) > Integer.valueOf(soldeCreditVirtuel.getText().toString()))  {

                isValide = false;

                errorMontant.setText("Votre crédit est insuffisant pour effectuer cette transaction");
                errorMontant.setVisibility(VISIBLE);

            } else {

                if (Integer.valueOf(montant.getText().toString()) < 1000) {

                    isValide = false;

                    errorMontant.setText("Le souscripteur ne peut pas déposer moins de 1000 fcfa");
                    errorMontant.setVisibility(VISIBLE);

                } else if (Integer.valueOf(montant.getText().toString()) > 12000) {

                    isValide = false;

                    errorMontant.setText("Le souscripteur ne peut pas déposer plus de 12000 fcfa");
                    errorMontant.setVisibility(VISIBLE);

                } else {

                    if ((Integer.valueOf(montant.getText().toString()) % 1000) != 0) {

                        isValide = false;

                        errorMontant.setText("Ce montant ne peut être déposer");
                        errorMontant.setVisibility(VISIBLE);

                    } else {

                        errorMontant.setVisibility(INVISIBLE);

                    }
                }

            }
        }


        return isValide;

    }




    private void findContratbyReference(final String reference)  {


        contratServiceImplementation.findContratbyReference(reference).enqueue(new Callback<JsonObject>() {

            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {


                if (response.isSuccessful()) {

                    System.out.println(response.body());

                    paiement = Integer.valueOf(response.body().get("portefeuille").getAsString());
                    montantDepot = Integer.valueOf(montant.getText().toString());

                    showDialog.getAlertDialog().dismiss();

                    if ((12000 - paiement) == 0) {

                        errorMontant.setText("Le souscripteur à cloturer son paiement pour ce contrat");
                        errorMontant.setVisibility(VISIBLE);

                        showDialog.getAlertDialog().dismiss();

                    } else if ((12000 - paiement) < montantDepot) {

                        errorMontant.setText("Il vous reste à payer au souscripteur " + (12000 - paiement) + ". Il ne peut pas dépasser cette somme pour le dépot");
                        errorMontant.setVisibility(VISIBLE);

                        showDialog.getAlertDialog().dismiss();

                    } else

                        {

                        portefeuille = new Portefeuille(montant.getText().toString(),
                                new Contrat(Long.valueOf(response.body().get("contrat_id").getAsString()),
                                        reference),
                                MarchandActivity.getMarchand());


                        depot(portefeuille);

                    }



                } else {

                    System.out.println(response.code());
                    showDialog.getAlertDialog().dismiss();

                    new CatchApiError(response.code()).catchError(getContext());

                    if (response.code() == 404) {

                        errorReference.setText("Aucun contrat ne correspond à cette réference");

                        errorReference.setVisibility(VISIBLE);

                        return;
                    }

                    errorReference.setVisibility(INVISIBLE);

                    showDialog.showErrorDialog("Oups...",
                            "Nous n'avons pas pu récupérer votre dernier recharge de crédit virtuelle",
                            "Réesayer")
                            .setPositiveListener(new ClickListener() {
                                @Override
                                public void onClick(@NotNull LottieAlertDialog lottieAlertDialog) {
                                    lottieAlertDialog.dismiss();
                                    findContratbyReference(reference);
                                }
                            })

                            .setNegativeListener(new ClickListener() {
                                @Override
                                public void onClick(@NotNull LottieAlertDialog lottieAlertDialog) {
                                    lottieAlertDialog.dismiss();
                                }
                            })

                            .setNegativeText("Annuler")
                            .setNegativeButtonColor(Color.parseColor("#e57373"))
                            .setNegativeTextColor(Color.parseColor("#ffffffff"))

                            .build()
                            .show();

                }


            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

                try {

                    showDialog.getAlertDialog().dismiss();

                    showDialog.showNoInternetDialog()
                            .setPositiveListener(new ClickListener() {
                                @Override
                                public void onClick(@NotNull LottieAlertDialog lottieAlertDialog) {
                                    lottieAlertDialog.dismiss();
                                    findContratbyReference(reference);
                                }
                            })

                            .setNegativeListener(new ClickListener() {
                                @Override
                                public void onClick(@NotNull LottieAlertDialog lottieAlertDialog) {
                                    lottieAlertDialog.dismiss();
                                }
                            })

                            .setNegativeText("Annuler")
                            .setNegativeButtonColor(Color.parseColor("#e57373"))
                            .setNegativeTextColor(Color.parseColor("#ffffffff"))

                            .build()
                            .show();

                } catch (Exception ignored) {}

            }

        });

    }




    private void depot(final Portefeuille portefeuille) {

        showDialog.showLoaddingDialog("Dépot de la prime", "Veuillez patienter");


        contratServiceImplementation.depot(portefeuille).enqueue(new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                if (response.isSuccessful()) {

                    System.out.println(response.body());

                    getResponseObject.setJsonObject(response.body());
                    CustomJsonObject customJsonObject = getResponseObject.getResponse();

                    System.out.println(customJsonObject);

                    if (customJsonObject.getType().equals("success_message")) {

                        refresh(MarchandActivity.getMarchand().getId());

                        /*sendSms("branlycaele", "jetaimebranly", "EHWLINMI ASSURANCE",
                                "22964250705", makeSmsText(),"5617");*/

                    }

                } else {

                    System.out.println(response.code());
                    showDialog.getAlertDialog().dismiss();

                    new CatchApiError(response.code());

                    showDialog.showErrorDialog("Oups...",
                            "Le paiment à échouer veuillez réessayer ultérieurement",
                            "Réesayer")
                            .setPositiveListener(new ClickListener() {
                                @Override
                                public void onClick(@NotNull LottieAlertDialog lottieAlertDialog) {
                                    lottieAlertDialog.dismiss();
                                    depot(portefeuille);
                                }
                            })

                            .setNegativeListener(new ClickListener() {
                                @Override
                                public void onClick(@NotNull LottieAlertDialog lottieAlertDialog) {
                                    lottieAlertDialog.dismiss();
                                }
                            })

                            .setNegativeText("Annuler")
                            .setNegativeButtonColor(Color.parseColor("#e57373"))
                            .setNegativeTextColor(Color.parseColor("#ffffffff"))

                            .build()
                            .show();

                }


            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

                try {

                    showDialog.getAlertDialog().dismiss();

                    showDialog.showNoInternetDialog()
                            .setPositiveListener(new ClickListener() {
                                @Override
                                public void onClick(@NotNull LottieAlertDialog lottieAlertDialog) {
                                    lottieAlertDialog.dismiss();
                                    depot(portefeuille);
                                }
                            })

                            .setNegativeListener(new ClickListener() {
                                @Override
                                public void onClick(@NotNull LottieAlertDialog lottieAlertDialog) {
                                    lottieAlertDialog.dismiss();
                                }
                            })

                            .setNegativeText("Annuler")
                            .setNegativeButtonColor(Color.parseColor("#e57373"))
                            .setNegativeTextColor(Color.parseColor("#ffffffff"))

                            .build()
                            .show();

                } catch (Exception ignored) {}

            }


        });

    }





    private void refresh(final Long id) {

        new MarchandServiceImplementation(

                TokenManager.getInstance(((FragmentActivity)getContext())
                        .getSharedPreferences("prefs", MODE_PRIVATE))
                        .getToken()

        ).getCreditAndComissionOfMarchand(id)

                .enqueue(new Callback<JsonObject>() {

                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                        if (response.isSuccessful()) {

                            try {

                                resetForm();
                                final JsonObject jsonObjectData = response.body()
                                        .getAsJsonObject("success")
                                        .getAsJsonObject("data");

                                soldeCreditVirtuel.setText(jsonObjectData.get("credit_virtuel").getAsString());


                                replaceFragment(new Accueil(), "Salut " + MarchandActivity.getUtilisateur().getPrenom());


                                new NotificationReader(getContext()).createNotification(new Message(
                                        "Vous avez effectué un dépot de " + portefeuille.getMontant() +
                                                " pour le contrat n° " + portefeuille.getContrat().getNumero() +
                                                " .Votre crédit virtuel passe à " + soldeCreditVirtuel.getText().toString() +
                                                " points et votre commission est de " + jsonObjectData.get("commission").getAsString() +
                                                " points",

                                        null), MarchandActivity.getUtilisateur().getId());

                                showDialog.getAlertDialog().dismiss();
                                showDialog.showSuccessDialog("Depôt effectué",
                                        "")
                                        .setPositiveListener(new ClickListener() {
                                            @Override
                                            public void onClick(@NotNull LottieAlertDialog lottieAlertDialog) {
                                                lottieAlertDialog.dismiss();
                                                resetForm();
                                            }
                                        })
                                        .build()
                                        .show();

                            } catch (Exception e) {

                                e.printStackTrace();
                            }

                        } else {

                            new CatchApiError(response.code()).catchError(getContext());

                        }

                    }




                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {

                        t.printStackTrace();

                    }

                });

    }




    private void resetForm() {

        reference.setText("");
        montant.setText("");

    }



    private void replaceFragment(Fragment fragment, String title) {


        FragmentManager fragmentManager = ((FragmentActivity)getContext()).getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_main, fragment).commit();


        MarchandActivity.getFrameTitle().setText(title);

    }








    private void sendSms(String user, String password, String from, String to, String text, String api) {

       new SmsServiceImplementation()
            .sendSms(user, password, from, to, text, api)

                .enqueue(new Callback<String>() {


                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {

                        if (response.isSuccessful()) {

                            System.out.println(response.body());

                        } else {

                            System.out.println(response.code());

                        }


                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                        System.out.println(t.getMessage());

                    }
                });


    }





    private String makeSmsText() {

        return "Vous venez d'éffectuer un paiement de " + portefeuille.getMontant() +
                " fcfa pour le contrat " + portefeuille.getContrat().getNumero() +
                ". Il vous reste à payer " + String.valueOf((12000 -paiement) - montantDepot) + " fcfa";

    }


}
