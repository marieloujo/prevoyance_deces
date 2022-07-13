package bj.assurance.assurancedeces.utils;

import android.annotation.SuppressLint;
import android.app.MediaRouteButton;
import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import bj.assurance.assurancedeces.R;
import bj.assurance.assurancedeces.fragment.CatchError;
import bj.assurance.assurancedeces.model.Client;
import bj.assurance.assurancedeces.model.Contrat;
import bj.assurance.assurancedeces.model.pagination.OutputPaginate;
import bj.assurance.assurancedeces.recyclerViewAdapter.ListClientExpandableAdapter;
import bj.assurance.assurancedeces.serviceImplementation.ClientServiceImplementation;
import bj.assurance.assurancedeces.serviceImplementation.MarchandServiceImplementation;
import bj.assurance.assurancedeces.utils.token.TokenManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class MakeListClient {



    private Context context;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;


    private List<Client> clients = new ArrayList<>();
    private List<ParentObject> parentObjects = new ArrayList<>();


    public MakeListClient(Context context, RecyclerView recyclerView, ProgressBar progressBar) {

        this.context = context;
        this.recyclerView = recyclerView;
        this.progressBar = progressBar;
    }






    public void makeList(Long id) {

        getListClient(id);

    }









    private void getListClient(final Long id) {

        new MarchandServiceImplementation(

                TokenManager.getInstance(context.
                        getSharedPreferences("prefs", MODE_PRIVATE)).
                        getToken()

        ).listClientOfMarchand(id)

                .enqueue(new Callback<JsonObject>() {


                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {


                        if (response.isSuccessful()) {

                            try {

                                Gson gson = new Gson();
                                Type listType = new TypeToken<List<Client>>() {}.getType();


                                OutputPaginate outputPaginate = new Gson().fromJson(
                                        response.body(), OutputPaginate.class);


                                clients = gson.fromJson(

                                        gson.toJson(outputPaginate.getObjects()),

                                        listType

                                );

                                getContratofClient();

                            } catch (Exception e) {

                                e.printStackTrace();

                            }

                        } else {

                            try {

                                progressBar.setVisibility(View.INVISIBLE);

                                String error = new CatchApiError(response.code()).catchError(context);

                                if (response.code() == 404) {

                                    replaceFragment(new CatchError("Aucun client pour le moment", "", false), "Mes clients");

                                } else {

                                    if (response.code() != 401) {

                                        replaceFragment(new CatchError(error, "Reporter", true), "Mes clients");

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

                            if (new onConnexionListener(context).checkConnexion()) {

                                replaceFragment(new CatchError("Serveur en maintenance veuillez patatientez ..",
                                        "Reporter", false), "Mes clients");

                            } else

                                replaceFragment(new CatchError("Aucune connexion internet." +
                                        " Vous naviguez en mode non connecté, vérifier votre connexion internet et réessayer",
                                        "Reéssayer", true), "Mes clients");


                        } catch (Exception e) {

                            progressBar.setVisibility(View.INVISIBLE);

                            e.printStackTrace();

                        }

                    }
                });

    }





    private void getContratofClient() {



        if (clients.size() > 0) {


            getListContrat(0);

        }




    }






    private void getListContrat(final int i) {

        try {


            new ClientServiceImplementation(

                    TokenManager.getInstance(context.
                            getSharedPreferences("prefs", MODE_PRIVATE)).
                            getToken()

            ).listContratOfClients(clients.get(i).getId())

                    .enqueue(new Callback<JsonObject>() {

                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {


                            if (response.isSuccessful()) {


                                try {

                                    OutputPaginate outputPaginate = new Gson().fromJson(
                                            response.body(), OutputPaginate.class);


                                    clients.get(i).setContrats(outputPaginate.getObjects());

                                    parentObjects.add((ParentObject) clients.get(i));


                                    int j = i;
                                    if (j ++ < clients.size()) {

                                        if (j == clients.size()) {

                                            ListClientExpandableAdapter listClientExpandableAdapter = new ListClientExpandableAdapter(
                                                    context, parentObjects
                                            );
                                            recyclerView.setAdapter(listClientExpandableAdapter);

                                            progressBar.setVisibility(View.INVISIBLE);

                                        }

                                        getListContrat(j);

                                    }


                                } catch (Exception e) {

                                    progressBar.setVisibility(View.INVISIBLE);

                                    e.printStackTrace();

                                }


                            } else {

                                try {

                                    String eror = new CatchApiError(response.code()).catchError(context);

                                } catch (Exception ignored) {}

                                if (response.code() != 401) {

                                    List<Object> objectList = new ArrayList<>();

                                    if (response.code() == 404) {

                                        objectList.add("Ce client n'a aucun contrat en cours");

                                        clients.get(i).setContrats(objectList);
                                        parentObjects.add((ParentObject) clients.get(i));

                                    } else {

                                        objectList.add("Impossible de charger les données");
                                        clients.get(i).setContrats(objectList);
                                        parentObjects.add((ParentObject) clients.get(i));

                                        try {
                                            System.out.println(response.errorBody().string());
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    System.out.println(clients);


                                    int j = i;
                                    if (j ++ < clients.size()) {

                                        if (j == clients.size()) {

                                            System.out.println(clients);

                                            ListClientExpandableAdapter listClientExpandableAdapter = new ListClientExpandableAdapter(
                                                    context, parentObjects
                                            );
                                            recyclerView.setAdapter(listClientExpandableAdapter);

                                            progressBar.setVisibility(View.INVISIBLE);

                                        }

                                        getListContrat(j);

                                    }

                                }


                            }


                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {

                            try {

                                progressBar.setVisibility(View.INVISIBLE);

                                if (new onConnexionListener(context).checkConnexion()) {

                                    replaceFragment(new CatchError("Serveur en maintenance veuillez patatientez ..",
                                            "Reporter", false), "Mes clients");

                                } else

                                    replaceFragment(new CatchError("Aucune connexion internet." +
                                            " Vous naviguez en mode non connecté, vérifier votre connexion internet et réessayer",
                                            "Reéssayer", true), "Mes clients");


                            } catch (Exception e) {

                                progressBar.setVisibility(View.INVISIBLE);

                                e.printStackTrace();

                            }
                        }


                    });


        } catch (Exception e) {

            progressBar.setVisibility(View.INVISIBLE);
            e.printStackTrace();

        }

    }






    private void replaceFragment(Fragment fragment, String title) {


        FragmentManager fragmentManager = ((FragmentActivity)context).getSupportFragmentManager();


        fragmentManager.beginTransaction().replace(R.id.content_main, fragment).commit();



    }




}
