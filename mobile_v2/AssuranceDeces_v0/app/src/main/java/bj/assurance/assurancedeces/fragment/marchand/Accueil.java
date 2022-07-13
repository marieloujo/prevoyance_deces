package bj.assurance.assurancedeces.fragment.marchand;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.arthurivanets.bottomsheets.BottomSheet;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
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
import java.util.Objects;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import bj.assurance.assurancedeces.BottomSheet.ChoiceTransfertBottomSheet;
import bj.assurance.assurancedeces.BottomSheet.DepotBottomSheet;
import bj.assurance.assurancedeces.R;
import bj.assurance.assurancedeces.activity.MarchandActivity;
import bj.assurance.assurancedeces.fragment.CatchError;
import bj.assurance.assurancedeces.model.Compte;
import bj.assurance.assurancedeces.model.Marchand;
import bj.assurance.assurancedeces.model.Notification;
import bj.assurance.assurancedeces.model.Portefeuille;
import bj.assurance.assurancedeces.model.customModel.ValidationEror;
import bj.assurance.assurancedeces.model.pagination.OutputPaginate;
import bj.assurance.assurancedeces.recyclerViewAdapter.NotificationAdapter;
import bj.assurance.assurancedeces.recyclerViewAdapter.TransactionAdater;
import bj.assurance.assurancedeces.service.MarchandService;
import bj.assurance.assurancedeces.serviceImplementation.MarchandServiceImplementation;
import bj.assurance.assurancedeces.serviceImplementation.UtilisateurServiceImplementation;
import bj.assurance.assurancedeces.utils.CatchApiError;
import bj.assurance.assurancedeces.utils.CustomJsonObject;
import bj.assurance.assurancedeces.utils.GetResponseObject;
import bj.assurance.assurancedeces.utils.ShowDialog;
import bj.assurance.assurancedeces.utils.token.TokenManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


public class Accueil extends Fragment {



    private TextView nomPrenomMarchand, matriculeMarchand,
            creditVirtuelleActuel, creditVirtuelRecharger;

    private SeekBar simpleSeekBar;
    private ImageView transferCommission;
    private FloatingActionButton floatingActionButton;

    private MarchandServiceImplementation marchandServiceImplementation;
    private ShowDialog showDialog;
    private GetResponseObject getResponseObject;

    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    private Integer creditVirtuelle, commission;


    private Marchand marchand;



    public Accueil() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_accueil_marchand, container, false);

        init(view);

        return view;
    }




    private void init(View view) {


        findView(view);
        initValue();
        onClickListener();


    }




    private void findView(View view) {

        nomPrenomMarchand = view.findViewById(R.id.nom_prenom_marchand);
        matriculeMarchand = view.findViewById(R.id.matricule_marchand);

        creditVirtuelleActuel = view.findViewById(R.id.credit_virtuelle_actuel);
        creditVirtuelRecharger = view.findViewById(R.id.solde_commisssions);

        //simpleSeekBar = view.findViewById(R.id.simpleSeekBar);
        transferCommission = view.findViewById(R.id.add);

        floatingActionButton = view.findViewById(R.id.floatingAdd);


        recyclerView = view.findViewById(R.id.recycler);
        progressBar = view.findViewById(R.id.scroll_progress);

    }




    @SuppressLint({"SetTextI18n", "WrongConstant"})
    private void initValue() {


        marchand = MarchandActivity.getMarchand();



        marchandServiceImplementation = new MarchandServiceImplementation(
                TokenManager.getInstance(getActivity().
                        getSharedPreferences("prefs", MODE_PRIVATE)).
                        getToken()
        );


        nomPrenomMarchand.setText(
                marchand.getUtilisateur().getNom()
                        + " " +
                marchand.getUtilisateur().getPrenom()
        );


        matriculeMarchand.setText(marchand.getMatricule());



        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);




        showDialog = new ShowDialog(getContext());
        getResponseObject = new GetResponseObject();



        getlastTransactions(marchand.getId());


        getAuthMarchand(marchand.getId(), "refresh");

        unReadNotifications(marchand.getId());


    }





    @SuppressLint("ClickableViewAccessibility")
    private void onClickListener() {

        /*simpleSeekBar.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });*/


        transferCommission.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {


                getAuthMarchand(

                        marchand.getId(),
                        "transfert"

                );

            }
        });


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {


                getAuthMarchand(

                        marchand.getId(),
                        "depot"

                );

            }
        });

    }




    /*private void getCreditVirtuelleofMarchand() {

        marchandServiceImplementation.creditVirtuelleOfMarchand (

                marchand.getId()

        ).enqueue(new Callback<JsonObject>() {


            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                if (response.isSuccessful()) {

                    getResponseObject.setJsonObject(response.body());
                    CustomJsonObject customJsonObject = getResponseObject.getResponse();

                    if (customJsonObject.getType().equals("success_data")) {

                        Compte compte = new Gson().fromJson (
                                customJsonObject.getJsonObject().getAsJsonObject("data"),
                                Compte.class
                        );




                        Float pourcentage = (
                                Float.valueOf(MarchandActivity.getMarchand().getCreditVirtuel())
                                        /
                                Float.valueOf(compte.getMontant())
                        ) * 100 ;

                    }

                } else {

                    new CatchApiError(response.code()).catchError(getContext());

                    showDialog.showErrorDialog("Oups...",
                            "Nous n'avons pas pu récupérer votre dernier recharge de crédit virtuelle",
                            "Réesayer")
                            .setPositiveListener(new ClickListener() {
                                @Override
                                public void onClick(@NotNull LottieAlertDialog lottieAlertDialog) {
                                    lottieAlertDialog.dismiss();
                                    getCreditVirtuelleofMarchand();
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

                noInternet();

            }



        });

    }*/




    private void getlastTransactions(final Long id) {


        new MarchandServiceImplementation(

                TokenManager.getInstance(getActivity().
                        getSharedPreferences("prefs", MODE_PRIVATE)).
                        getToken()

        ).listTransactionsOfMarchandbyDate(id, new Date())
                .enqueue(new Callback<JsonObject>() {


                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {


                        if (response.isSuccessful()) {


                            getResponseObject.setJsonObject(response.body());

                            CustomJsonObject jsonObject = getResponseObject.getResponse();

                            Gson gson = new Gson();
                            Type listType = new TypeToken<List<Portefeuille>>() {}.getType();


                            OutputPaginate outputPaginate = new Gson().fromJson(
                                    jsonObject.getJsonObject(), OutputPaginate.class);


                            List<Portefeuille> portefeuilles = gson.fromJson(

                                    gson.toJson(outputPaginate.getObjects()),

                                    listType

                            );

                            TransactionAdater transactionAdater = new TransactionAdater(getContext(),portefeuilles);
                            recyclerView.setAdapter(transactionAdater);

                            progressBar.setVisibility(View.INVISIBLE);


                        } else {


                            JSONObject jObjError = null;
                            try {

                                jObjError = new JSONObject(response.errorBody().string());

                            } catch (JSONException e) {

                                e.printStackTrace();

                            } catch (IOException e) {

                                e.printStackTrace();

                            }


                            try {

                                //System.out.println("jObjError" + jObjError/*.get("errors")/*.getString("message")*/);

                                replaceFragment(new CatchError(
                                        jObjError.getJSONObject("errors").getString("message"))
                                );

                                progressBar.setVisibility(View.INVISIBLE);

                            } catch (Exception e) {
                                e.printStackTrace();

                                try {

                                    replaceFragment(new CatchError(
                                                    jObjError.getJSONObject("success").getString("message"))
                                    );

                                    progressBar.setVisibility(View.INVISIBLE);

                                } catch (Exception e1) {

                                    e1.printStackTrace();

                                    String error = new CatchApiError(response.code()).catchError(getContext());

                                    replaceFragment(new CatchError(error));

                                    progressBar.setVisibility(View.INVISIBLE);

                                }


                            }


                        }


                    }


                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {

                        noInternet();

                    }

                });


    }




    private void getAuthMarchand(Long id, final String string) {


        showDialog.showLoaddingDialog("Chargement", "Veuillez patienter quelques instant");

        marchandServiceImplementation.getCreditAndComissionOfMarchand(id)

            .enqueue(new Callback<JsonObject>() {

                @SuppressLint("SetTextI18n")
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                    if (response.isSuccessful()) {

                        JsonObject jsonObjectData = response.body()
                                .getAsJsonObject("success")
                                .getAsJsonObject("data");

                        creditVirtuelle = Integer.valueOf(jsonObjectData.get("credit_virtuel").getAsString());
                        commission = Integer.valueOf(jsonObjectData.get("commission").getAsString());

                        creditVirtuelleActuel.setText(String.valueOf(creditVirtuelle) + " points");
                        creditVirtuelRecharger.setText(String.valueOf(commission) + " points");


                        System.out.println("creditVirtuelle " + creditVirtuelle + " commission " + commission);


                        showDialog.getAlertDialog().dismiss();


                        switch (string) {


                            case "depot":

                                if (creditVirtuelle >= 1000) {

                                    BottomSheet bottomSheet = new DepotBottomSheet(
                                            Objects.requireNonNull(getActivity()),
                                            "" + creditVirtuelle
                                    );
                                    bottomSheet.show();

                                } else {

                                    showDialog.showWarningDialog("Crédit virtuel insuffissant",
                                            "Votre solde de crédit virtuel est de " + creditVirtuelle +
                                                    " Veuillez recharger votre compte pour effectuer l'opération");

                                }

                                break;


                            case "transfert":

                                if (commission >= 1000) {

                                    BottomSheet bottomSheet = new ChoiceTransfertBottomSheet(
                                            (Activity) Objects.requireNonNull(getContext())
                                    );
                                    bottomSheet.show();


                                } else {

                                    showDialog.showWarningDialog("Commission insuffissante",
                                            "Votre solde de commission est de " + commission +
                                                    " Veuillez recharger votre compte pour effectuer l'opération");

                                }

                                break;


                            case "refresh":

                                creditVirtuelleActuel.setText(String.valueOf(creditVirtuelle) + " points");
                                creditVirtuelRecharger.setText(String.valueOf(commission) + " points");


                                break;

                        }


                    }

                }




                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {

                    noInternet();

                }

            });

    }




    private void unReadNotifications(Long id) {


        new UtilisateurServiceImplementation(getContext())

                .unReadNotifications(id,

                        TokenManager.getInstance(
                                ((FragmentActivity)getContext()).getSharedPreferences("prefs", MODE_PRIVATE)).
                                getToken()

                )

                .enqueue(new Callback<JsonArray>() {


                    @Override
                    public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {

                        if (response.isSuccessful()) {

                            Type listType = new TypeToken<List<Notification>>() {}.getType();

                            List<Notification> notifications = new Gson().fromJson(response.body(), listType);

                            MarchandActivity.getIsRead().setVisibility(View.VISIBLE);


                            System.out.println("notifications "+notifications);


                        } else {

                            System.out.println("code" + response.code());

                            if (response.code() == 404) {

                                MarchandActivity.getIsRead().setVisibility(View.INVISIBLE);

                            } else {


                                try {

                                    JSONObject jObjError = new JSONObject(response.errorBody().string());


                                    try {

                                        //showDialog.getAlertDialog().dismiss();

                                    } catch (Exception ignored) {
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();

                                    String string = new CatchApiError(response.code()).catchError(getContext());


                                    System.out.println("stringno" + string);

                                }
                            }

                        }

                    }



                    @Override
                    public void onFailure(Call<JsonArray> call, Throwable t) {


                        try {

                            /*showDialog.showNoInternetDialog()
                                    .setPositiveListener(new ClickListener() {
                                        @Override
                                        public void onClick(@NotNull LottieAlertDialog lottieAlertDialog) {
                                            lottieAlertDialog.dismiss();
                                            unReadNotifications(id);
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
                                    .show();*/

                        } catch (Exception ignored) {}


                    }


                });


    }







    @SuppressLint("SetTextI18n")
    private void replaceFragment(Fragment fragment) {


        FragmentManager fragmentManager = ((FragmentActivity)getContext()).getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_last_transaction, fragment).commit();

    }




    private void noInternet() {

        try {

            LottieAlertDialog lottieAlertDialog = showDialog.showNoInternetDialog()

                    .setPositiveListener( new ClickListener() {
                        @Override
                        public void onClick(@NotNull LottieAlertDialog lottieAlertDialog) {

                            lottieAlertDialog.dismiss();

                            getlastTransactions(marchand.getId());
                            //getAuthMarchand(marchand.getId());



                        }
                    })

                    .setNegativeListener( new ClickListener() {
                        @Override
                        public void onClick(@NotNull LottieAlertDialog lottieAlertDialog) {
                            lottieAlertDialog.dismiss();
                        }
                    })

                    .setNegativeText("Annuler")
                    .setNegativeButtonColor(Color.parseColor("#e57373"))
                    .setNegativeTextColor(Color.parseColor("#ffffffff"))

                    .build();

            lottieAlertDialog.setCancelable(false);
            lottieAlertDialog.show();


        } catch (Exception ignored) {}

    }





}
