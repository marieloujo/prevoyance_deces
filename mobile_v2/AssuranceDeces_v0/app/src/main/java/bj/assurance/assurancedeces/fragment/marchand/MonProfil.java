package bj.assurance.assurancedeces.fragment.marchand;


import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import bj.assurance.assurancedeces.R;
import bj.assurance.assurancedeces.activity.MarchandActivity;
import bj.assurance.assurancedeces.model.Contrat;
import bj.assurance.assurancedeces.model.Marchand;
import bj.assurance.assurancedeces.model.Utilisateur;
import bj.assurance.assurancedeces.model.pagination.OutputPaginate;
import bj.assurance.assurancedeces.recyclerViewAdapter.ListContratAdapter;
import bj.assurance.assurancedeces.recyclerViewAdapter.ListProspetsAdapter;
import bj.assurance.assurancedeces.service.MarchandService;
import bj.assurance.assurancedeces.serviceImplementation.MarchandServiceImplementation;
import bj.assurance.assurancedeces.utils.ShowDialog;
import bj.assurance.assurancedeces.utils.token.TokenManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


public class MonProfil extends Fragment {



    private TextView nomPrenom, adresse, email;
    private Button openDiscussions;


    private RecyclerView recyclerView, recyclerViewContrat;
    private ProgressBar progressBar;

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
        recyclerViewContrat = view.findViewById(R.id.recyclerContratEnAttente);

    }



    @SuppressLint({"SetTextI18n", "WrongConstant"})
    private void initValue() {


        nomPrenom.setText(

                MarchandActivity.getMarchand().getUtilisateur().getPrenom()
                        + " " +
                MarchandActivity.getMarchand().getUtilisateur().getPrenom()

        );


        adresse.setText(

                MarchandActivity.getMarchand().getUtilisateur().getAdresse()

        );


        email.setText(

                MarchandActivity.getMarchand().getUtilisateur().getEmail()

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







    private void getListProspect(Long id, int page) {


        marchandService.listProspectOfMarchand(id, page)
            .enqueue(new Callback<JsonObject>() {

                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                    if (response.isSuccessful()) {

                        OutputPaginate outputPaginate = new Gson().fromJson(response.body(), OutputPaginate.class);

                        Type listType = new TypeToken<List<Utilisateur>>() {}.getType();
                        List<Utilisateur> utilisateurs = new Gson().fromJson (

                                new Gson().toJson(outputPaginate.getObjects()),

                                listType

                        );

                        ListProspetsAdapter listProspetsAdapter = new ListProspetsAdapter( getContext(), utilisateurs);
                        recyclerView.setAdapter(listProspetsAdapter);


                    } else {

                        System.out.println(response.code());

                    }

                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {

                    System.out.println(t.getMessage());

                }

            });

    }





    private void getListContratEnAttente(Long id, int page) {


        marchandService.listContratEnAttente(id, page)
                .enqueue(new Callback<JsonObject>() {

                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                        if (response.isSuccessful()) {

                            OutputPaginate outputPaginate = new Gson().fromJson(response.body(), OutputPaginate.class);

                            Type listType = new TypeToken<List<Contrat>>() {}.getType();
                            List<Contrat> contrats = new Gson().fromJson (

                                    new Gson().toJson(outputPaginate.getObjects()),

                                    listType

                            );

                            ListContratAdapter listContratAdapter = new ListContratAdapter(getContext(), contrats);
                            recyclerViewContrat.setAdapter(listContratAdapter);


                        } else {

                            System.out.println(response.code());

                        }

                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {

                        System.out.println(t.getMessage());

                    }

                });

    }






}
