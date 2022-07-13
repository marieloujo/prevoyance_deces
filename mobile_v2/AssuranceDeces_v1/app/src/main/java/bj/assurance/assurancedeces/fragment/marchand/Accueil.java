package bj.assurance.assurancedeces.fragment.marchand;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.arthurivanets.bottomsheets.BottomSheet;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.labters.lottiealertdialoglibrary.ClickListener;
import com.labters.lottiealertdialoglibrary.LottieAlertDialog;

import org.jetbrains.annotations.NotNull;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import bj.assurance.assurancedeces.BottomSheet.ActionBottomDialogFragment;
import bj.assurance.assurancedeces.BottomSheet.ChoiceTransfertBottomSheet;
import bj.assurance.assurancedeces.BottomSheet.DepotBottomSheet;
import bj.assurance.assurancedeces.R;
import bj.assurance.assurancedeces.activity.MarchandActivity;
import bj.assurance.assurancedeces.fragment.CatchError;
import bj.assurance.assurancedeces.model.Marchand;
import bj.assurance.assurancedeces.model.Portefeuille;
import bj.assurance.assurancedeces.model.Utilisateur;
import bj.assurance.assurancedeces.model.pagination.OutputPaginate;
import bj.assurance.assurancedeces.recyclerViewAdapter.TransactionAdater;
import bj.assurance.assurancedeces.serviceImplementation.MarchandServiceImplementation;
import bj.assurance.assurancedeces.utils.CatchApiError;
import bj.assurance.assurancedeces.utils.GetResponseObject;
import bj.assurance.assurancedeces.utils.NotificationReader;
import bj.assurance.assurancedeces.utils.OnBackPressedListener;
import bj.assurance.assurancedeces.utils.ShowDialog;
import bj.assurance.assurancedeces.utils.onConnexionListener;
import bj.assurance.assurancedeces.utils.token.TokenManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


public class Accueil extends Fragment implements OnBackPressedListener {



    private TextView nomPrenomMarchand, matriculeMarchand,
            creditVirtuelleActuel, creditVirtuelRecharger;

    private ImageView transferCommission;
    private FloatingActionButton floatingActionButton;

    private MarchandServiceImplementation marchandServiceImplementation;
    private ShowDialog showDialog;
    private GetResponseObject getResponseObject;

    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    private Integer creditVirtuelle, commission;


    private Marchand marchand;
    private Utilisateur utilisateur;

    private TextView viewHistorique, viewallAransaction;




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
        onClickListener(view);


    }




    private void findView(View view) {

        nomPrenomMarchand = view.findViewById(R.id.nom_prenom_marchand);
        matriculeMarchand = view.findViewById(R.id.matricule_marchand);

        creditVirtuelleActuel = view.findViewById(R.id.credit_virtuelle_actuel);
        creditVirtuelRecharger = view.findViewById(R.id.solde_commisssions);



        transferCommission = view.findViewById(R.id.add);

        floatingActionButton = view.findViewById(R.id.floatingAdd);


        recyclerView = view.findViewById(R.id.recycler);
        progressBar = view.findViewById(R.id.scroll_progress);

        viewHistorique = view.findViewById(R.id.view_historique);
        viewallAransaction = view.findViewById(R.id.view_all_transaction);

    }




    @SuppressLint({"SetTextI18n", "WrongConstant"})
    private void initValue() {


        marchand = MarchandActivity.getMarchand();
        utilisateur = MarchandActivity.getUtilisateur();



        marchandServiceImplementation = new MarchandServiceImplementation(
                TokenManager.getInstance(getActivity().
                        getSharedPreferences("prefs", MODE_PRIVATE)).
                        getToken()
        );


        nomPrenomMarchand.setText(
                utilisateur.getNom()
                        + " " +
                utilisateur.getPrenom()
        );


        matriculeMarchand.setText(marchand.getMatricule());



        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);




        showDialog = new ShowDialog(getContext());
        getResponseObject = new GetResponseObject();



        getlastTransactions(marchand.getId());


        getAuthMarchand(marchand.getId(), "refresh");

        new NotificationReader(getContext()).unReadNotifications(MarchandActivity.getUtilisateur().getId());


    }




















    @SuppressLint("ClickableViewAccessibility")
    private void onClickListener(View view) {



        viewHistorique.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {

                getContext().startActivity(new Intent(getContext(), bj.assurance.assurancedeces.activity.FragmentActivity.class)
                        .putExtra("key", "HistoriqueMarchand"));

            }
        });




        viewallAransaction.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {

                replaceFragment(new Transactions());

                MarchandActivity.getFrameTitle().setText("Transactions");


                /*Menu menu = MarchandActivity.getMenu();

                MenuInflater inflater = ((FragmentActivity)getContext()).getMenuInflater();
                inflater.inflate(R.menu.bubbletabbar_menu_marchand, menu);

                MenuItem toggleAttackDetectionMenuItem = menu.findItem(R.id.marchand_bottom_nav_accueil);
                MenuItem toggleCellTrackingMenuItem = menu.findItem(R.id.marchand_bottom_nav_transactions);

                toggleAttackDetectionMenuItem.setChecked(false);
                toggleCellTrackingMenuItem.setChecked(true);*/

                //((View) R.id.marchand_option_nav_clients).setChecked(false);

            }
        });





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
















    private void getlastTransactions(final Long id) {

        progressBar.setVisibility(View.VISIBLE);


        new MarchandServiceImplementation(

                TokenManager.getInstance(getActivity().
                        getSharedPreferences("prefs", MODE_PRIVATE)).
                        getToken()

        ).listTransactionsOfMarchandbyDate(id, new Date())
                .enqueue(new Callback<JsonObject>() {


                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {


                        if (response.isSuccessful()) {


                            try {

                                Gson gson = new Gson();
                                Type listType = new TypeToken<List<Portefeuille>>() {}.getType();

                                OutputPaginate outputPaginate = new Gson().fromJson(
                                        response.body(), OutputPaginate.class);


                                List<Portefeuille> portefeuilles = gson.fromJson(

                                        gson.toJson(outputPaginate.getObjects()),

                                        listType
                                );

                                TransactionAdater transactionAdater = new TransactionAdater(getContext(),portefeuilles);
                                recyclerView.setAdapter(transactionAdater);

                                progressBar.setVisibility(View.INVISIBLE);

                            } catch (Exception e) {

                                e.printStackTrace();

                            }


                        } else {


                            try {

                                progressBar.setVisibility(View.INVISIBLE);

                                String error = new CatchApiError(response.code()).catchError(getContext());

                                if (response.code() == 404) {

                                    replaceFragment(new CatchError("Aucune transaction effectuée", "", false));

                                } else {

                                    if (response.code() != 401) {

                                        replaceFragment(new CatchError(error, "Reporter", true));

                                    }

                                }

                            } catch (Exception e) {

                                e.printStackTrace();

                            }


                        }


                    }


                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {



                        try {

                            progressBar.setVisibility(View.INVISIBLE);

                            if (new onConnexionListener(getContext()).checkConnexion()) {

                                replaceFragment(new CatchError("Serveur en maintenance veuillez patatientez ..",
                                        "Reporter", false));

                            } else {

                                CatchError catchError = new CatchError("Aucune connexion internet." +
                                        " Vous naviguez en mode non connecté, vérifier votre connexion internet et réessayer",
                                        "Reéssayer", true);

                                replaceFragment(catchError);

                            }


                        } catch (Exception e) {

                            progressBar.setVisibility(View.INVISIBLE);

                            e.printStackTrace();

                        }


                    }

                });


    }




    private void getAuthMarchand(Long id, final String string) {


        showDialog.showLoaddingDialog("Chargement", "Veuillez patienter quelques instant.");

        marchandServiceImplementation.getCreditAndComissionOfMarchand(id)

            .enqueue(new Callback<JsonObject>() {


                @SuppressLint("SetTextI18n")
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                    if (response.isSuccessful()) {

                        try {

                            JsonObject jsonObjectData = response.body()
                                    .getAsJsonObject("success")
                                    .getAsJsonObject("data");

                            creditVirtuelle = Integer.valueOf(jsonObjectData.get("credit_virtuel").getAsString());
                            commission = Integer.valueOf(jsonObjectData.get("commission").getAsString());

                            creditVirtuelleActuel.setText(String.valueOf(creditVirtuelle) + " points");
                            creditVirtuelRecharger.setText(String.valueOf(commission) + " points");


                            showDialog.getAlertDialog().dismiss();


                            switch (string) {


                                case "depot":

                                    if (creditVirtuelle >= 1000) {


                                        try {

                                            BottomSheet bottomSheet = new DepotBottomSheet(
                                                    Objects.requireNonNull(getActivity()),
                                                    "" + creditVirtuelle
                                            );
                                            bottomSheet.show();

                                        } catch (Exception e) {

                                            e.printStackTrace();

                                        }

                                    } else {

                                        try {

                                            showDialog.showWarningDialog("Crédit virtuel insuffissant",
                                                    "Votre solde de crédit virtuel est de " + creditVirtuelle +
                                                            " Veuillez recharger votre compte pour effectuer l'opération");
                                        } catch (Exception e) {

                                            e.printStackTrace();

                                        }
                                    }

                                    break;


                                case "transfert":

                                    if (commission >= 1000) {

                                        try {


                                            BottomSheet bottomSheet = new ChoiceTransfertBottomSheet(
                                                    (Activity) Objects.requireNonNull(getContext())
                                            );
                                            bottomSheet.show();


                                        } catch (Exception e) {

                                            e.printStackTrace();

                                        }

                                    } else {

                                        try {

                                            showDialog.showWarningDialog("Commission insuffissante",
                                                    "Votre solde de commission est de " + commission +
                                                            " Veuillez recharger votre compte pour effectuer l'opération");

                                        } catch (Exception e) {

                                            e.printStackTrace();

                                        }

                                    }

                                    break;


                                case "refresh":

                                    try {

                                        creditVirtuelleActuel.setText(String.valueOf(creditVirtuelle) + " points");
                                        creditVirtuelRecharger.setText(String.valueOf(commission) + " points");

                                    } catch (Exception e) {

                                        e.printStackTrace();

                                    }


                                    break;

                            }


                        } catch (Exception e) {

                            e.printStackTrace();

                        }

                    } else {

                        try {

                            showDialog.getAlertDialog().dismiss();

                            System.out.println(response.code());

                        } catch (Exception e) {}

                    }

                }


                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {

                    try {

                        showDialog.getAlertDialog().dismiss();

                    } catch (Exception e) {}

                }


            });

    }









    @SuppressLint("SetTextI18n")
    private void replaceFragment(Fragment fragment) {

         FragmentManager fragmentManager = ((MarchandActivity)getContext()).getSupportFragmentManager();
         fragmentManager.beginTransaction().replace(R.id.content_last_transaction, fragment).addToBackStack(null).commit();

    }







    @Override
    public void doBack() {

        showDialog.showQuestionDialog("Confirmation",
                "Etes vous sûre de vouloir quitter l'application ??")

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
