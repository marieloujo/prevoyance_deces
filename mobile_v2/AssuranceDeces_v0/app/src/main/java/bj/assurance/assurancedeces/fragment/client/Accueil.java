package bj.assurance.assurancedeces.fragment.client;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.labters.lottiealertdialoglibrary.DialogTypes;
import com.labters.lottiealertdialoglibrary.LottieAlertDialog;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import bj.assurance.assurancedeces.R;
import bj.assurance.assurancedeces.activity.ClientActivity;
import bj.assurance.assurancedeces.model.Client;
import bj.assurance.assurancedeces.model.Contrat;
import bj.assurance.assurancedeces.model.customModel.ValidationEror;
import bj.assurance.assurancedeces.model.pagination.OutputPaginate;
import bj.assurance.assurancedeces.recyclerViewAdapter.ListContratAdapter;
import bj.assurance.assurancedeces.serviceImplementation.ClientServiceImplementation;
import bj.assurance.assurancedeces.utils.CatchApiError;
import bj.assurance.assurancedeces.utils.CustomJsonObject;
import bj.assurance.assurancedeces.utils.GetResponseObject;
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

        int status = (portefeuil / paiement) * 100;

        totalPortefeuilClient.setText(String.valueOf(portefeuil) + " fcfa");
        totalMaxPortefeuilClient.setText(String.valueOf(paiement) +" fcfa");

        progressBar.setProgress(status);

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



                            GetResponseObject getResponseObject = new GetResponseObject();
                            getResponseObject.setJsonObject(response.body());

                            CustomJsonObject jsonObject = getResponseObject.getResponse();

                            Gson gson = new Gson();
                            Type listType = new TypeToken<List<Contrat>>() {}.getType();


                            OutputPaginate outputPaginate = new Gson().fromJson(
                                    jsonObject.getJsonObject(), OutputPaginate.class);





                            List<Contrat>  contrats = gson.fromJson(

                                    gson.toJson(outputPaginate.getObjects()),

                                    listType

                            );

                            displayData(contrats);

                            ListContratAdapter listContratAdapter = new ListContratAdapter(getContext(), contrats);
                            recyclerView.setAdapter(listContratAdapter);

                            mainProgress.setVisibility(View.INVISIBLE);

                        } else {

                            mainProgress.setVisibility(View.INVISIBLE);

                            new CatchApiError(response.code()).catchError(getContext());

                            try {

                                JSONObject jObjError = new JSONObject(response.errorBody().string());

                                System.out.println(jObjError);


                                try {

                                    //showDialog.getAlertDialog().dismiss();

                                } catch (Exception ignored) {}

                            } catch (Exception e) {
                                e.printStackTrace();

                                String string = new CatchApiError(response.code()).catchError(getContext());

                                /*showDialog.getAlertDialog().changeDialog(new LottieAlertDialog.Builder(getContext(), DialogTypes.TYPE_ERROR)
                                        .setTitle("Error")
                                        .setDescription(string)
                                );*/
                            }

                        }

                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {


                        mainProgress.setVisibility(View.INVISIBLE);

                    }
                });


    }





}
