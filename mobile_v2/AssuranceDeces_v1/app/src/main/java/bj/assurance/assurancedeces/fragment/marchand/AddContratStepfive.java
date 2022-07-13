package bj.assurance.assurancedeces.fragment.marchand;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.labters.lottiealertdialoglibrary.ClickListener;
import com.labters.lottiealertdialoglibrary.LottieAlertDialog;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import bj.assurance.assurancedeces.R;
import bj.assurance.assurancedeces.activity.MarchandActivity;
import bj.assurance.assurancedeces.model.Contrat;
import bj.assurance.assurancedeces.model.Marchand;
import bj.assurance.assurancedeces.serviceImplementation.MarchandServiceImplementation;
import bj.assurance.assurancedeces.serviceImplementation.SmsServiceImplementation;
import bj.assurance.assurancedeces.utils.CatchApiError;
import bj.assurance.assurancedeces.utils.OnBackPressedListener;
import bj.assurance.assurancedeces.utils.ShowDialog;
import bj.assurance.assurancedeces.utils.onConnexionListener;
import bj.assurance.assurancedeces.utils.token.TokenManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


public class AddContratStepfive extends Fragment implements OnBackPressedListener {



    private Button next, cancel;


    public AddContratStepfive() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_contrat_stepfive, container, false);



        next  = view.findViewById(R.id.suivant);
        cancel = view.findViewById(R.id.annuler);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //System.out.println(new Gson().toJson(MarchandActivity.getContrat()));

                sendContrat(MarchandActivity.getContrat());
            }
        });


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ((FragmentActivity) getContext()).getSupportFragmentManager().popBackStack();

            }
        });

        return view;
    }






    private void sendContrat(final Contrat contrat) {


        final ShowDialog showDialog = new ShowDialog(getContext());
        showDialog.showLoaddingDialog("Enregistrement ..",
                    " Veuillez patienter l'enregistrement est en cours");


        new MarchandServiceImplementation(

                TokenManager.getInstance(getActivity().
                        getSharedPreferences("prefs", MODE_PRIVATE)).
                        getToken()


        ).createContrat(contrat)

                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {


                        if (response.isSuccessful()) {

                            try {

                                showDialog.getAlertDialog().dismiss();

                            } catch (Exception e) {}

                           try {


                               System.out.println(response.body());

                               Contrat contrat1 = new Gson().fromJson(response.body().getAsJsonObject("success").getAsJsonObject("message"), Contrat.class);

                               LottieAlertDialog lottieAlertDialog = new ShowDialog(getContext()).showSuccessDialog("Contrat créee avec success", "")
                                       .setPositiveListener(new ClickListener() {
                                           @Override
                                           public void onClick(@NotNull LottieAlertDialog lottieAlertDialog) {


                                               /*sendSms("branlycaele", "jetaimebranly", "EHWLINMI ASSURANCE",
                                                       "22964250705", makeSmsText(),"5617");
*/
                                               getActivity().startActivity(
                                                       new Intent(getContext(), MarchandActivity.class)
                                                               .putExtra("marchand", new Gson().toJson(MarchandActivity.getUserable())));

                                               lottieAlertDialog.dismiss();




                                           }
                                       }).build();

                               lottieAlertDialog.setCancelable(false);
                               lottieAlertDialog.show();

                           } catch (Exception e) {

                                e.printStackTrace();

                               new ShowDialog(getContext()).showErrorDialog("Echec d'enregistrement", "L'enregistrement du contrat n'a pas pu aboutit",
                                       "Ressayer")
                                       .setPositiveListener(new ClickListener() {
                                           @Override
                                           public void onClick(@NotNull LottieAlertDialog lottieAlertDialog) {
                                               lottieAlertDialog.dismiss();
                                               sendContrat(contrat);

                                           }
                                       })
                                       .build()
                                       .show();


                           }


                        } else {

                            try {
                                showDialog.getAlertDialog().dismiss();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            if (response.code() != 401) {

                                try {
                                    new ShowDialog(getContext()).showErrorDialog("Echec d'enregistrement", "L'enregistrement du contrat n'a pas pu aboutit",
                                            "Ressayer")
                                            .setPositiveListener(new ClickListener() {
                                                @Override
                                                public void onClick(@NotNull LottieAlertDialog lottieAlertDialog) {
                                                    lottieAlertDialog.dismiss();
                                                    sendContrat(contrat);

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

                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {

                        try {

                            showDialog.getAlertDialog().dismiss();

                        } catch (Exception ignored) {}

                        System.out.println(t.getMessage());


                        try {


                            if (t.getMessage().equals("timeout")) {

                                LottieAlertDialog lottieAlertDialog = showDialog.showErrorDialog("Temps écroulé",
                                        "Les données on pris trop de temps à être envoyé vers le serveur.. Veuillez réessayer ultérieurement",
                                        "Réessayer")

                                        .setPositiveListener(new ClickListener() {
                                            @Override
                                            public void onClick(@NotNull LottieAlertDialog lottieAlertDialog) {

                                                sendContrat(contrat);
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
                                            sendContrat(contrat);
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


        return "Vous etes bien enregistre dans EHWLINMI ASSURANCE";


    }


    @Override
    public void doBack() {

        ((FragmentActivity) getActivity()).getSupportFragmentManager().popBackStack();

    }
}
