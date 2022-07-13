package bj.assurance.assurancedeces.fragment.supermarchand;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.labters.lottiealertdialoglibrary.ClickListener;
import com.labters.lottiealertdialoglibrary.LottieAlertDialog;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import bj.assurance.assurancedeces.R;
import bj.assurance.assurancedeces.activity.SuperMarchandActivity;
import bj.assurance.assurancedeces.model.Client;
import bj.assurance.assurancedeces.model.Compte;
import bj.assurance.assurancedeces.model.Marchand;
import bj.assurance.assurancedeces.model.SuperMarchand;
import bj.assurance.assurancedeces.model.pagination.OutputPaginate;
import bj.assurance.assurancedeces.recyclerViewAdapter.MesMarchandAdapter;
import bj.assurance.assurancedeces.serviceImplementation.MarchandServiceImplementation;
import bj.assurance.assurancedeces.serviceImplementation.SuperMarchandServiceImplementation;
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



    private TextView nomPrenom, matricule, soldeCommission, voirplus;

    private RecyclerView recyclerView;

    private SuperMarchand superMarchand;


    private GetResponseObject getResponseObject;
    private ShowDialog showDialog;


    private List<CustumMarchand> custumMarchands = new ArrayList<>();

    private MesMarchandAdapter mesMarchandAdapter;


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

        View view = inflater.inflate(R.layout.fragment_accueil_supermarchand, container, false);


        init(view);


        return view;
    }





    private void init(View view) {


        findView(view);
        initValue();
        setClickListener();

    }





    private void findView(View view) {


        nomPrenom = view.findViewById(R.id.nom_prenom_super);
        matricule = view.findViewById(R.id.matricule_super_marchand);
        soldeCommission = view.findViewById(R.id.commissions_super_marchand);
        voirplus = view.findViewById(R.id.view_more);


        recyclerView = view.findViewById(R.id.recycler);


    }




    @SuppressLint({"SetTextI18n", "WrongConstant"})
    private void initValue() {


        superMarchand = SuperMarchandActivity.getSuperMarchand();


        nomPrenom.setText(

                superMarchand.getUtilisateur().getNom()
                        + " " +
                superMarchand.getUtilisateur().getPrenom()
        );


        matricule.setText(
                superMarchand.getMatricule()
        );


        getResponseObject = new GetResponseObject();
        showDialog = new ShowDialog(getContext());

        getCommission(

                superMarchand.getId()

        );


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);


        mesMarchandAdapter = new MesMarchandAdapter(

                getContext(),
                custumMarchands

        );

        recyclerView.setAdapter(mesMarchandAdapter);


        getListMarchand(

                superMarchand.getId()

        );


    }






    private void setClickListener() {


        voirplus.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                replaceFragment(

                    new MesMarchands(),

                    getContext().getResources().getString(R.string.mes_marchands)

                );

            }

        });


    }






    private void getCommission(final Long id) {


        new SuperMarchandServiceImplementation(

                TokenManager.getInstance(getActivity().
                        getSharedPreferences("prefs", MODE_PRIVATE)).
                        getToken()

        ).getCommissionofSupermarchand(id)

                .enqueue(new Callback<JsonObject>() {


                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {


                        if (response.isSuccessful()) {


                                Compte compte = new Gson().fromJson(

                                        response.body().getAsJsonObject("success").get("commission"),
                                        Compte.class
                                );


                                System.out.println(compte);

                                soldeCommission.setText(compte.getMontant() + " points");


                        }  else {


                            new CatchApiError(response.code()).catchError(getContext());

                            showDialog.showErrorDialog("Oups...",
                                    "Nous n'avons pas pu récupérer votre dernier recharge de crédit virtuelle",
                                    "Réesayer")
                                    .setPositiveListener(new ClickListener() {
                                        @Override
                                        public void onClick(@NotNull LottieAlertDialog lottieAlertDialog) {

                                            lottieAlertDialog.dismiss();
                                            getCommission(id);

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

                            showDialog.showNoInternetDialog()
                                    .setPositiveListener(new ClickListener() {
                                        @Override
                                        public void onClick(@NotNull LottieAlertDialog lottieAlertDialog) {

                                            lottieAlertDialog.dismiss();
                                            getCommission(id);

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





    private void getListMarchand(final Long id) {

        new SuperMarchandServiceImplementation(

                TokenManager.getInstance(getActivity().
                        getSharedPreferences("prefs", MODE_PRIVATE)).
                        getToken()

        ).listMarchandsOfSupermarchand(id)

                .enqueue(new Callback<JsonObject>() {


                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                        if (response.isSuccessful()) {

                            getResponseObject.setJsonObject(response.body());

                            CustomJsonObject jsonObject = getResponseObject.getResponse();

                            Gson gson = new Gson();
                            Type listType = new TypeToken<List<Marchand>>() {}.getType();


                            OutputPaginate outputPaginate = new Gson().fromJson(
                                    jsonObject.getJsonObject(), OutputPaginate.class);



                            List<Marchand>  marchands = gson.fromJson(

                                    gson.toJson(outputPaginate.getObjects()),

                                    listType

                            );
                            marchands.remove(null);



                            createCustumMarchand(marchands);


                        } else {

                            new CatchApiError(response.code()).catchError(getContext());

                        }

                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {


                        try {

                            showDialog.showNoInternetDialog()
                                    .setPositiveListener(new ClickListener() {
                                        @Override
                                        public void onClick(@NotNull LottieAlertDialog lottieAlertDialog) {

                                            lottieAlertDialog.dismiss();
                                            getCommission(id);

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





    private void getLisClientoftMarchand(final Marchand marchand) {

        new MarchandServiceImplementation(

                TokenManager.getInstance(getActivity().
                        getSharedPreferences("prefs", MODE_PRIVATE)).
                        getToken()

        ).listClientOfMarchand(marchand.getId())

                .enqueue(new Callback<JsonObject>() {


                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                        if (response.isSuccessful()) {

                            getResponseObject.setJsonObject(response.body());

                            CustomJsonObject jsonObject = getResponseObject.getResponse();

                            Gson gson = new Gson();
                            Type listType = new TypeToken<List<Client>>() {}.getType();


                            OutputPaginate outputPaginate = new Gson().fromJson(
                                    jsonObject.getJsonObject(), OutputPaginate.class);





                            List<Client>  clients = gson.fromJson(

                                    gson.toJson(outputPaginate.getObjects()),

                                    listType

                            );

                            custumMarchands.add(

                                    new CustumMarchand(
                                            marchand,
                                            clients.size()
                                    )

                            );


                            mesMarchandAdapter.notifyDataSetChanged();


                        } else {

                            new CatchApiError(response.code()).catchError(getContext());

                        }

                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {


                        try {

                            showDialog.showNoInternetDialog()
                                    .setPositiveListener(new ClickListener() {
                                        @Override
                                        public void onClick(@NotNull LottieAlertDialog lottieAlertDialog) {

                                            lottieAlertDialog.dismiss();
                                            getCommission(marchand.getId());

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





    private void createCustumMarchand(List<Marchand> marchands) {


        for (Marchand marchand : marchands) {

           getLisClientoftMarchand(marchand);
        }




    }






    private void replaceFragment(Fragment fragment, String title) {


        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_main, fragment).commit();


        SuperMarchandActivity.getFrameTitle().setText(title);

    }







    public class CustumMarchand  {


        private Marchand marchand;
        private int clients;

        public CustumMarchand(Marchand marchand, int clients) {
            this.marchand = marchand;
            this.clients = clients;
        }






        public int getClients() {
            return clients;
        }

        public void setClients(int clients) {
            this.clients = clients;
        }



        public Marchand getMarchand() {
            return marchand;
        }

        public void setMarchand(Marchand marchand) {
            this.marchand = marchand;
        }




        @Override
        public String toString() {
            return "CustumMarchand{" +
                    "marchand=" + marchand +
                    ", clients=" + clients +
                    '}';
        }



    }





}
