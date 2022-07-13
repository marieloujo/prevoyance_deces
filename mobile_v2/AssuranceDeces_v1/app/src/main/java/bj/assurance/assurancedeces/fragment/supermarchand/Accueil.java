package bj.assurance.assurancedeces.fragment.supermarchand;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.labters.lottiealertdialoglibrary.ClickListener;
import com.labters.lottiealertdialoglibrary.LottieAlertDialog;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import bj.assurance.assurancedeces.R;
import bj.assurance.assurancedeces.activity.SuperMarchandActivity;
import bj.assurance.assurancedeces.fragment.CatchError;
import bj.assurance.assurancedeces.model.Client;
import bj.assurance.assurancedeces.model.Compte;
import bj.assurance.assurancedeces.model.Marchand;
import bj.assurance.assurancedeces.model.Notification;
import bj.assurance.assurancedeces.model.SuperMarchand;
import bj.assurance.assurancedeces.model.Utilisateur;
import bj.assurance.assurancedeces.model.pagination.OutputPaginate;
import bj.assurance.assurancedeces.recyclerViewAdapter.MesMarchandAdapter;
import bj.assurance.assurancedeces.serviceImplementation.MarchandServiceImplementation;
import bj.assurance.assurancedeces.serviceImplementation.SuperMarchandServiceImplementation;
import bj.assurance.assurancedeces.serviceImplementation.UtilisateurServiceImplementation;
import bj.assurance.assurancedeces.utils.CatchApiError;
import bj.assurance.assurancedeces.utils.CustomJsonObject;
import bj.assurance.assurancedeces.utils.GetResponseObject;
import bj.assurance.assurancedeces.utils.ShowDialog;
import bj.assurance.assurancedeces.utils.onConnexionListener;
import bj.assurance.assurancedeces.utils.token.TokenManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


public class Accueil extends Fragment {



    private TextView nomPrenom, matricule, soldeCommission, voirplus;

    private RecyclerView recyclerView;
    private ProgressBar progressBar;


    private SuperMarchand superMarchand;


    private GetResponseObject getResponseObject;
    private ShowDialog showDialog;


    private List<CustumMarchand> custumMarchands = new ArrayList<>();
    private MesMarchandAdapter mesMarchandAdapter;
    private List<Marchand> marchands;





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
        progressBar = view.findViewById(R.id.main_progress);

    }




    @SuppressLint({"SetTextI18n", "WrongConstant"})
    private void initValue() {


        Utilisateur utilisateur = SuperMarchandActivity.getUtilisateur();
        superMarchand = SuperMarchandActivity.getSuperMarchand();


        nomPrenom.setText(

                utilisateur.getNom()  + " " + utilisateur.getPrenom()
        );


        matricule.setText(
                superMarchand.getMatricule());


        soldeCommission.setText(
                superMarchand.getCommission() + " points");


        getResponseObject = new GetResponseObject();
        showDialog = new ShowDialog(getContext());


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);


        mesMarchandAdapter = new MesMarchandAdapter(getContext(), custumMarchands);

        recyclerView.setAdapter(mesMarchandAdapter);


        getListMarchand(superMarchand.getId());
        unReadNotifications(utilisateur.getId());


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


                            try {

                                Gson gson = new Gson();
                                Type listType = new TypeToken<List<Marchand>>() {}.getType();


                                OutputPaginate outputPaginate = new Gson().fromJson(
                                        response.body(), OutputPaginate.class);

                                marchands = gson.fromJson(
                                        gson.toJson(outputPaginate.getObjects()), listType);
                                marchands.remove(null);


                                createCustumMarchand();
                                progressBar.setVisibility(View.INVISIBLE);

                            } catch (Exception e) {

                                e.printStackTrace();

                            }


                        } else {


                            try {

                                progressBar.setVisibility(View.INVISIBLE);

                                String error = new CatchApiError(response.code()).catchError(getContext());

                                if (response.code() == 404) {

                                    replaceFragment(new CatchError("Vous n'avez aucun marchand", "", false));

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





    private void getLisClientoftMarchand(final Marchand marchand, final int position) {

        new MarchandServiceImplementation(

                TokenManager.getInstance(getActivity().
                        getSharedPreferences("prefs", MODE_PRIVATE)).
                        getToken()

        ).listClientOfMarchand(marchand.getId())

                .enqueue(new Callback<JsonObject>() {


                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                        if (response.isSuccessful()) {


                            try {

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
                                progressBar.setVisibility(View.INVISIBLE);


                                custumMarchands.add( new CustumMarchand( marchand, clients.size()) );

                                System.out.println(custumMarchands);
                                mesMarchandAdapter.notifyDataSetChanged();

                                try {

                                    int j = position;
                                    if (j++  == marchands.size()) {

                                        progressBar.setVisibility(View.INVISIBLE);

                                    }

                                } catch (Exception e) {

                                    progressBar.setVisibility(View.INVISIBLE);

                                }


                            } catch (Exception e) {

                                e.printStackTrace();

                            }


                        } else {

                            try {

                                int j = position;
                                if (position == marchands.size()) {

                                    progressBar.setVisibility(View.INVISIBLE);

                                }


                                new CatchApiError(response.code()).catchError(getContext());

                                if (response.code() == 404) {

                                    custumMarchands.add(new CustumMarchand(marchand, 0));
                                    mesMarchandAdapter.notifyDataSetChanged();

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





    private void createCustumMarchand() {

        System.out.println("size " + marchands.size());

        for (int i = 0; i < marchands.size(); i++) {

           getLisClientoftMarchand(marchands.get(i), i);

        }

    }






    private void replaceFragment(Fragment fragment, String title) {


        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_main, fragment).commit();


        SuperMarchandActivity.getFrameTitle().setText(title);

    }









    @SuppressLint("SetTextI18n")
    private void replaceFragment(Fragment fragment) {

        FragmentManager fragmentManager = ((SuperMarchandActivity)getContext()).getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_marchand, fragment).commit();

    }





    private void unReadNotifications(final Long id) {


        new UtilisateurServiceImplementation(getActivity())

                .unReadNotifications(id,

                        TokenManager.getInstance(
                                (getActivity()).getSharedPreferences("prefs", MODE_PRIVATE)).
                                getToken()

                )

                .enqueue(new Callback<JsonArray>() {


                    @Override
                    public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {

                        if (response.isSuccessful()) {

                            try {

                                Type listType = new TypeToken<List<Notification>>() {}.getType();

                                List<Notification> notifications = new Gson().fromJson(response.body(), listType);

                                SuperMarchandActivity.getIsRead().setVisibility(View.VISIBLE);


                                System.out.println("notifications "+notifications);

                            } catch (Exception e) {

                                e.printStackTrace();

                            }


                        } else {

                            System.out.println("code" + response.code());

                            if (response.code() == 404) {

                                try {

                                    SuperMarchandActivity.getIsRead().setVisibility(View.INVISIBLE);

                                } catch (Exception e) {

                                    e.printStackTrace();

                                }

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



                    }


                });


    }







    public static class CustumMarchand  {


        private Marchand marchand;
        private Utilisateur utilisateur;
        private int clients;


        public CustumMarchand() {
        }

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



        public Utilisateur getUtilisateur() {
            return utilisateur;
        }

        public void setUtilisateur(Utilisateur utilisateur) {
            this.utilisateur = utilisateur;
        }

        @Override
        public String toString() {
            return "CustumMarchand{" +
                    "marchand=" + marchand +
                    ", utilisateur=" + utilisateur +
                    ", clients=" + clients +
                    '}';
        }


    }





}
