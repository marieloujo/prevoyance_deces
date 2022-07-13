package bj.assurance.assurancedeces.fragment.client;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import bj.assurance.assurancedeces.R;
import bj.assurance.assurancedeces.activity.ClientActivity;
import bj.assurance.assurancedeces.activity.MarchandActivity;
import bj.assurance.assurancedeces.fragment.CatchError;
import bj.assurance.assurancedeces.model.Contrat;
import bj.assurance.assurancedeces.model.pagination.OutputPaginate;
import bj.assurance.assurancedeces.recyclerViewAdapter.ListContratAdapter;
import bj.assurance.assurancedeces.serviceImplementation.ClientServiceImplementation;
import bj.assurance.assurancedeces.utils.CatchApiError;
import bj.assurance.assurancedeces.utils.CustomJsonObject;
import bj.assurance.assurancedeces.utils.GetResponseObject;
import bj.assurance.assurancedeces.utils.onConnexionListener;
import bj.assurance.assurancedeces.utils.token.TokenManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


public class Accueil extends Fragment {



    private TextView totalPortefeuilClient, totalMaxPortefeuilClient;

    private CardView gestionImmobilier, organistionFunerail;

    private RecyclerView recyclerView;
    private ProgressBar progressBar, mainProgress;




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

        View view = inflater.inflate(R.layout.fragment_accueil_client, container, false);


        init(view);


        return view;
    }







    private void init(View view) {

        findView(view);
        initValue();
        clickListener();


    }




    private void findView(View view) {

        totalMaxPortefeuilClient = view.findViewById(R.id.totalMaxPortefeuilClient);
        totalPortefeuilClient = view.findViewById(R.id.totalPortefeuilClient);


        gestionImmobilier = view.findViewById(R.id.gestion_immobilier);
        organistionFunerail = view.findViewById(R.id.organistion_funerail);

        progressBar = view.findViewById(R.id.my_progressBar);
        mainProgress = view.findViewById(R.id.main_progress);

        recyclerView = view.findViewById(R.id.recycler);

    }





    @SuppressLint("WrongConstant")
    private void initValue() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);


        getListLastContratforClients(
                ClientActivity.getClient().getId()
        );
    }





    private void clickListener() {


        gestionImmobilier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        organistionFunerail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }





    @SuppressLint("SetTextI18n")
    private void displayData(List<Contrat> contrats) {

        Integer portefeuil = 0, paiement = 0;

        for (int i = 0; i < contrats.size(); i++) {
            for (int j = 0; j < contrats.get(i).getTransactions().size(); j++)
                portefeuil += Integer.valueOf(contrats.get(i).getTransactions().get(j).getMontant());
        }

        paiement = 12000 * contrats.size();

        double status =  (((double) portefeuil) / ((double) paiement)) * 100 ;

        System.out.println(status + " " + portefeuil + " " + paiement);

        totalPortefeuilClient.setText(String.valueOf(portefeuil) + " fcfa");
        totalMaxPortefeuilClient.setText(String.valueOf(paiement) +" fcfa");

        progressBar.setProgress((int) status);

    }





    private void getListLastContratforClients(Long id) {

        new ClientServiceImplementation(

                TokenManager.getInstance(getActivity().
                        getSharedPreferences("prefs", MODE_PRIVATE)).
                        getToken()

        ).lastContratsOfClient(id)

                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {


                        if (response.isSuccessful()) {

                            Gson gson = new Gson();
                            Type listType = new TypeToken<List<Contrat>>() {}.getType();


                            OutputPaginate outputPaginate = new Gson().fromJson(
                                    response.body(), OutputPaginate.class);


                            List<Contrat>  contrats = gson.fromJson(

                                    gson.toJson(outputPaginate.getObjects()),

                                    listType

                            );


                            System.out.println(contrats.get(0).getTransactions());

                            displayData(contrats);

                            ListContratAdapter listContratAdapter = new ListContratAdapter(getContext(), contrats);
                            recyclerView.setAdapter(listContratAdapter);

                            mainProgress.setVisibility(View.INVISIBLE);

                        } else {

                            try {

                                mainProgress.setVisibility(View.INVISIBLE);

                                String error = new CatchApiError(response.code()).catchError(getContext());

                                if (response.code() == 404) {

                                    replaceFragment(new CatchError("Vous n'avez aucun contrat actif", "", false));

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

                            mainProgress.setVisibility(View.INVISIBLE);

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

                            mainProgress.setVisibility(View.INVISIBLE);

                            e.printStackTrace();

                        }

                    }
                });


    }





    private void replaceFragment(Fragment fragment) {

        FragmentManager fragmentManager = ((ClientActivity)getContext()).getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.listContrat, fragment).commit();

    }



}
