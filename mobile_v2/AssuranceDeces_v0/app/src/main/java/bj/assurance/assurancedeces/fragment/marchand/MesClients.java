package bj.assurance.assurancedeces.fragment.marchand;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
import bj.assurance.assurancedeces.activity.ContenteAddBeneficier;
import bj.assurance.assurancedeces.activity.FragmentActivity;
import bj.assurance.assurancedeces.activity.MarchandActivity;
import bj.assurance.assurancedeces.model.Client;
import bj.assurance.assurancedeces.model.Contrat;
import bj.assurance.assurancedeces.model.pagination.OutputPaginate;
import bj.assurance.assurancedeces.recyclerViewAdapter.ListeClientAdapter;
import bj.assurance.assurancedeces.serviceImplementation.ClientServiceImplementation;
import bj.assurance.assurancedeces.serviceImplementation.MarchandServiceImplementation;
import bj.assurance.assurancedeces.utils.CatchApiError;
import bj.assurance.assurancedeces.utils.CustomJsonObject;
import bj.assurance.assurancedeces.utils.GetResponseObject;
import bj.assurance.assurancedeces.utils.ShowDialog;
import bj.assurance.assurancedeces.utils.token.TokenManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


public class MesClients extends Fragment {


    private FloatingActionButton floatingActionButton;

    private RecyclerView recyclerView;


    private ShowDialog showDialog;
    private GetResponseObject getResponseObject;


    private List<Client> newClients = new ArrayList<>();
    private List<Client> clients = new ArrayList<>();
    private List<Client> lastclients = new ArrayList<>();


    private ListeClientAdapter listeClientAdapter;


    public MesClients() {

    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_mes_clients, container, false);

        init(view);

        return view;
    }



    private void init(View view) {


        findView(view);
        initValue();
        onClickListener();


    }



    private void findView(View view) {

        floatingActionButton = view.findViewById(R.id.floatingAdd);
        recyclerView = view.findViewById(R.id.recycler);

    }



    @SuppressLint("WrongConstant")
    private void initValue() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);



        listeClientAdapter = new ListeClientAdapter (
                lastclients, getContext()
        );
        recyclerView.setAdapter(listeClientAdapter);



        showDialog = new ShowDialog(getContext());
        getResponseObject = new GetResponseObject();


        getListClient(

                MarchandActivity.getMarchand().getId()

        );

    }



    private void onClickListener() {

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getContext().startActivity(
                        new Intent(getContext(), FragmentActivity.class).putExtra("key", "enregistrement")
                );

            }
        });

    }



    private void replaceFragment(Fragment fragment, String title) {

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_main, fragment).commit();

        MarchandActivity.getFrameTitle().setText(title);

    }




    private void getListClient(final Long id) {

        new MarchandServiceImplementation(

                TokenManager.getInstance(getActivity().
                        getSharedPreferences("prefs", MODE_PRIVATE)).
                        getToken()

        ).listClientOfMarchand(id)

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





                            clients = gson.fromJson(

                                    gson.toJson(outputPaginate.getObjects()),

                                    listType

                            );

                            for (Client client : clients) {

                                if (client != null)

                                    newClients.add(client);

                            }





                            getContratofClient();


                            //progressBar.setVisibility(View.INVISIBLE);

                        } else {

                            String eror = new CatchApiError(response.code()).catchError(getContext());

                        }


                    }



                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {


                        try {

                            showDialog.showNoInternetDialog()

                                    .setPositiveListener( new ClickListener() {
                                        @Override
                                        public void onClick(@NotNull LottieAlertDialog lottieAlertDialog) {

                                            lottieAlertDialog.dismiss();

                                            getListClient(id);
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

                                    .build()
                                    .show();

                        } catch (Exception ignored) {}


                    }
                });



    }



    private void getListContrat(final Client client) {

        try {


            new ClientServiceImplementation(

                    TokenManager.getInstance(getActivity().
                            getSharedPreferences("prefs", MODE_PRIVATE)).
                            getToken()

            ).listContratOfClients(client.getId())

                    .enqueue(new Callback<JsonObject>() {

                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {


                            if (response.isSuccessful()) {


                                getResponseObject.setJsonObject(response.body());

                                CustomJsonObject jsonObject = getResponseObject.getResponse();

                                Gson gson = new Gson();
                                Type listType = new TypeToken<List<Contrat>>() {}.getType();


                                OutputPaginate outputPaginate = new Gson().fromJson(
                                        jsonObject.getJsonObject(), OutputPaginate.class);


                                List<Contrat> contrats = gson.fromJson(

                                        gson.toJson(outputPaginate.getObjects()),

                                        listType

                                );

                                client.setContrats(contrats);

                                lastclients.add(client);

                                listeClientAdapter.notifyDataSetChanged();


                                // System.out.println(newClients);



                            } else {

                                String eror = new CatchApiError(response.code()).catchError(getContext());

                            }


                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {

                            try {

                                showDialog.showNoInternetDialog()

                                        .setPositiveListener( new ClickListener() {
                                            @Override
                                            public void onClick(@NotNull LottieAlertDialog lottieAlertDialog) {

                                                lottieAlertDialog.dismiss();

                                                getListClient(client.getId());
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

                                        .build()
                                        .show();

                            } catch (Exception ignored) {}

                        }


                    });


        } catch (Exception e) {

            e.printStackTrace();

        }

    }



    private void getContratofClient () {

        for (Client client : newClients) {

            getListContrat(client);

        }

    }

}
