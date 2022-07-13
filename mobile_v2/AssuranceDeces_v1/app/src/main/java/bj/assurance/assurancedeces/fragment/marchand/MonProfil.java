package bj.assurance.assurancedeces.fragment.marchand;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import bj.assurance.assurancedeces.R;
import bj.assurance.assurancedeces.activity.MarchandActivity;
import bj.assurance.assurancedeces.model.Contrat;
import bj.assurance.assurancedeces.model.Utilisateur;
import bj.assurance.assurancedeces.model.customModel.Expandable;
import bj.assurance.assurancedeces.model.pagination.OutputPaginate;
import bj.assurance.assurancedeces.recyclerViewAdapter.ListContratAdapter;
import bj.assurance.assurancedeces.recyclerViewAdapter.ListProspetsAdapter;
import bj.assurance.assurancedeces.recyclerViewAdapter.ListWaitingContratAdapter;
import bj.assurance.assurancedeces.serviceImplementation.MarchandServiceImplementation;
import bj.assurance.assurancedeces.utils.NotificationReader;
import bj.assurance.assurancedeces.utils.OnBackPressedListener;
import bj.assurance.assurancedeces.utils.ShowDialog;
import bj.assurance.assurancedeces.utils.onConnexionListener;
import bj.assurance.assurancedeces.utils.token.TokenManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


public class MonProfil extends Fragment implements OnBackPressedListener {



    private TextView nomPrenom, adresse, email;
    private Button openDiscussions;


    private RecyclerView recyclerView, recyclerViewContrat;
    private ProgressBar progressBar, progressBarContrat;

    private MarchandServiceImplementation marchandService;
    private ShowDialog showDialog;


    private int FIRST_PAGE_PROSPECTS = 1, LAST_PAGE_PROSPECTS, CURRENT_PAGE_PROSPECTS;
    private int FIRST_PAGE_CONTRATS = 1, LAST_PAGE_CONTRATS, CURRENT_PAGE_CONTRATS;


    public MonProfil() {
        // Required empty public constructor
    }





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_mon_profil, container, false);


        init(view);


        return view;
    }




    private void init(View view) {

        findView(view);
        initValue();

    }




    private void findView(View view) {

        nomPrenom = view.findViewById(R.id.nom_prenom_marchand);
        adresse = view.findViewById(R.id.adresse_marchand);
        email = view.findViewById(R.id.adresse_mail_marchand);


        openDiscussions = view.findViewById(R.id.mes_messages);
        recyclerView = view.findViewById(R.id.recycler);
        recyclerViewContrat = view.findViewById(R.id.recyclerContrat);



        progressBar = view.findViewById(R.id.progress_prospect);
        progressBarContrat = view.findViewById(R.id.progress_contrat);

    }



    @SuppressLint({"SetTextI18n", "WrongConstant"})
    private void initValue() {


        new NotificationReader(getContext()).unReadNotifications(MarchandActivity.getUtilisateur().getId());


        nomPrenom.setText(

                MarchandActivity.getUtilisateur().getNom()
                        + " " +
                MarchandActivity.getUtilisateur().getPrenom()

        );


        adresse.setText(

                MarchandActivity.getUtilisateur().getAdresse()

        );


        email.setText(

                MarchandActivity.getUtilisateur().getEmail()

        );


        showDialog = new ShowDialog(getContext());
        marchandService = new MarchandServiceImplementation(

                TokenManager.getInstance(
                        ((FragmentActivity)getContext()).getSharedPreferences("prefs", MODE_PRIVATE)).
                        getToken()

        );


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);



        LinearLayoutManager linearLayoutManagerContrat = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewContrat.setLayoutManager(linearLayoutManagerContrat);




        getListProspect(MarchandActivity.getMarchand().getId(), FIRST_PAGE_PROSPECTS);

        getListContratEnAttente(MarchandActivity.getMarchand().getId(), FIRST_PAGE_CONTRATS);


    }







    private void getListProspect(final Long id, final int page) {

        progressBar.setVisibility(View.VISIBLE);

        marchandService.listProspectOfMarchand(id, page)
            .enqueue(new Callback<JsonObject>() {

                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                    if (response.isSuccessful()) {

                        try {

                            OutputPaginate outputPaginate = new Gson().fromJson(response.body(), OutputPaginate.class);

                            List<ParentObject> parentObjects = new ArrayList<>();
                            parentObjects.add((ParentObject) new Expandable("Mes prospects", outputPaginate.getObjects()));

                            ListProspetsAdapter listProspetsAdapter = new ListProspetsAdapter( getContext(), parentObjects);
                            recyclerView.setAdapter(listProspetsAdapter);

                            progressBar.setVisibility(View.INVISIBLE);

                        } catch (Exception e) {

                            progressBar.setVisibility(View.INVISIBLE);

                            e.printStackTrace();


                        }


                    } else {

                        progressBar.setVisibility(View.INVISIBLE);

                        System.out.println(response.code());

                        try {
                            System.out.println(response.code() + " " + response.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        progressBarContrat.setVisibility(View.INVISIBLE);

                        if (response.code() == 404) {

                            makeListProspect("Vous n'avez aucun prospect .");

                        } else {

                            makeListProspect("Impossible de récupérer les données.");

                        }


                    }

                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {

                    progressBar.setVisibility(View.INVISIBLE);

                    System.out.println(t.getMessage());

                    if (t.getMessage().equals("timeout")) {

                        getListProspect(id, page);

                    } else {

                        if (new onConnexionListener(getContext()).checkConnexion()) {

                            makeListProspect("Echec de connexion au serveur veuillez réessayer ultérieurement.");

                        } else {

                            makeListProspect("Connexion perdue, impossible de charger les données. Veuillez réessayer ultérieurement.");

                        }

                    }


                }

            });

    }





    private void getListContratEnAttente(final Long id, final int page) {

        progressBar.setVisibility(View.VISIBLE);

        marchandService.listContratEnAttente(id, page)
                .enqueue(new Callback<JsonObject>() {

                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                        if (response.isSuccessful()) {

                            try {

                                OutputPaginate outputPaginate = new Gson().fromJson(response.body(), OutputPaginate.class);


                                System.out.println(outputPaginate.getObjects());

                                List<ParentObject> parentObjects = new ArrayList<>();
                                parentObjects.add((ParentObject) new Expandable("Contrats en attente", outputPaginate.getObjects()));

                                ListWaitingContratAdapter listProspetsAdapter = new ListWaitingContratAdapter( getContext(), parentObjects);
                                recyclerViewContrat.setAdapter(listProspetsAdapter);

                                progressBarContrat.setVisibility(View.INVISIBLE);

                            } catch (Exception e) {

                                e.printStackTrace();

                                progressBarContrat.setVisibility(View.INVISIBLE);

                            }


                        } else {

                            try {
                                System.out.println(response.code() + " " + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            progressBarContrat.setVisibility(View.INVISIBLE);

                            if (response.code() == 404) {

                               makeListWaitingContrat("Vous n'avez aucun contrat en attente");

                            } else {

                               makeListWaitingContrat("Impossible de récupérer les données");

                            }

                        }

                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {

                        System.out.println(t.getMessage());

                        progressBarContrat.setVisibility(View.INVISIBLE);

                        if (t.getMessage().equals("timeout")) {

                            getListContratEnAttente(id, page);

                        } else {

                            if (new onConnexionListener(getContext()).checkConnexion()) {

                                makeListWaitingContrat("Echec de connexion au serveur veuillez réessayer ultérieurement");

                            } else {

                                makeListWaitingContrat("Connexion perdue, impossible de charger les données. Veuillez réessayer ultérieurement");

                            }

                        }


                    }

                });

    }




    @Override
    public void doBack() {

        replaceFragment(new Accueil(), "Salut " + MarchandActivity.getUtilisateur().getPrenom());

    }








    private void replaceFragment(Fragment fragment, String title) {

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_main, fragment).commit();

        MarchandActivity.getFrameTitle().setText(title);

    }




    //timeout
    private void makeListWaitingContrat(String string) {

        List<Object> objects = new ArrayList<>();
        objects.add(string);


        List<ParentObject> parentObjects = new ArrayList<>();
        parentObjects.add((ParentObject) new Expandable("Contrats en attente", objects));

        ListWaitingContratAdapter listProspetsAdapter = new ListWaitingContratAdapter( getContext(), parentObjects);
        recyclerViewContrat.setAdapter(listProspetsAdapter);


    }







    private void makeListProspect(String string) {

        List<Object> objects = new ArrayList<>();
        objects.add(string);


        List<ParentObject> parentObjects = new ArrayList<>();
        parentObjects.add((ParentObject) new Expandable("Mes prospects", objects));

        ListProspetsAdapter listProspetsAdapter = new ListProspetsAdapter( getContext(), parentObjects);
        recyclerView.setAdapter(listProspetsAdapter);


    }





}
