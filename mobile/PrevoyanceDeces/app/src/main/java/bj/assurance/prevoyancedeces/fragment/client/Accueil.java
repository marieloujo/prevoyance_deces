package bj.assurance.prevoyancedeces.fragment.client;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.kinda.alert.KAlertDialog;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import bj.assurance.prevoyancedeces.R;
import bj.assurance.prevoyancedeces.model.pagination.OutputPaginate;
import bj.assurance.prevoyancedeces.utils.AccessToken;
import bj.assurance.prevoyancedeces.utils.ApiError;
import bj.assurance.prevoyancedeces.utils.Utils;
import bj.assurance.prevoyancedeces.activity.Main2Activity;
import bj.assurance.prevoyancedeces.adapter.ListeSouscriptionAdpter;
import bj.assurance.prevoyancedeces.fragment.Boutique;
import bj.assurance.prevoyancedeces.model.Client;
import bj.assurance.prevoyancedeces.model.Contrat;
import bj.assurance.prevoyancedeces.retrofit.RetrofitBuildForGetRessource;
import bj.assurance.prevoyancedeces.retrofit.Service.ClientService;
import bj.assurance.prevoyancedeces.retrofit.TokenManager;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class Accueil extends Fragment {

    private static final String TAG = "Accueil";

    private ListeSouscriptionAdpter listeSouscriptionAdpter;
    private RecyclerView recyclerView;

    private RelativeLayout  layoutConnexionLose, contentError;
    private LinearLayout layoutNodata;

    private TextView portefeuilActuel, portefeulTotal, readMore;
    private ProgressBar progressBar, progressBarMain;

    private CardView cardView, cardView1;

    private ScrollView contentMain;

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
        View view = inflater.inflate(R.layout.fragment_accueil, container, false);

        init(view);
        setClickListener();

        return view;
    }

    @SuppressLint({"WrongConstant", "SetTextI18n"})
    public void init(View view) {
        layoutConnexionLose = view.findViewById(R.id.no_connection);
        layoutNodata = view.findViewById(R.id.layout_nothing);
        recyclerView = view.findViewById(R.id.recycler);
        portefeuilActuel = view.findViewById(R.id.totalPortefeuilClient);
        portefeulTotal = view.findViewById(R.id.totalMaxPortefeuilClient);
        progressBar = view.findViewById(R.id.my_progressBar);
        progressBarMain = view.findViewById(R.id.main_progress);
        readMore = view.findViewById(R.id.seeMore);
        cardView = view.findViewById(R.id.gestion_immobilier);
        cardView1 = view.findViewById(R.id.organistion_funerail);
        contentMain = view.findViewById(R.id.content_main);
        contentError = view.findViewById(R.id.content_error);
        TextView textView = contentError.findViewById(R.id.error_text);
        textView.setText("Aucun contrat");
        ((Button) contentError.findViewById(R.id.retry)).setVisibility(View.INVISIBLE);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        contentMain.setVisibility(View.VISIBLE);

        //generateContrat();

        getContratsForUser(TokenManager.getInstance(getActivity().
                getSharedPreferences("prefs", MODE_PRIVATE)).
                getToken());
    }

    private void setClickListener() {
        readMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new MonProfile(), getResources().getString(R.string.mon_profil));
            }
        });

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new Boutique(), getResources().getString(R.string.boutique_virtuelle));
            }
        });

        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new Boutique(), getResources().getString(R.string.boutique_virtuelle));
            }
        });
    }

    private void displayData(List<Contrat> contrats) {

        Integer portefeuil = 0, paiement = 0;

        for (int i = 0; i < contrats.size(); i++) {
            for (int j = 0; j < contrats.get(i).getTransactions().size(); j++)
                portefeuil += Integer.valueOf(contrats.get(i).getTransactions().get(j).getMontant());
        }

        paiement = 12000 * contrats.size();

        int status = (portefeuil / paiement) * 100;

        portefeuilActuel.setText(String.valueOf(portefeuil));
        portefeulTotal.setText(String.valueOf(paiement));

        progressBar.setProgress(status);

    }

    private void getContratsForUser(AccessToken accessToken) {

        try {
            Call<JsonObject> call;
            ClientService service = new RetrofitBuildForGetRessource(accessToken).getRetrofit().create(ClientService.class);
            call = service.getContrat(Main2Activity.getClient().getId());
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                    Log.w(TAG, "onResponse: " + response);

                    if (response.isSuccessful()) {
                        System.out.println(response.body());
                        getResponseContrat(response.body());
                        progressBarMain.setVisibility(View.INVISIBLE);
                    } else {
                        progressBarMain.setVisibility(View.INVISIBLE);
                        contentError.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    Log.w(TAG, "onFailure: " + t.getMessage());

                    progressBarMain.setVisibility(View.INVISIBLE);
                   /* layoutConnexionLose.setVisibility(View.INVISIBLE);
                    layoutNodata.setVisibility(View.INVISIBLE);
                    contentError.setVisibility(View.VISIBLE);
                    contentMain.setVisibility(View.INVISIBLE);*/
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            progressBarMain.setVisibility(View.INVISIBLE);
           /* layoutConnexionLose.setVisibility(View.INVISIBLE);
            layoutNodata.setVisibility(View.INVISIBLE);
            //contentError.setVisibility(View.VISIBLE);
            contentMain.setVisibility(View.VISIBLE);*/
        }
    }

    private void getResponseContrat(JsonObject jsonObject) {
        JsonObject error = null, sucess = null;
        String messageError = null, message = null;
        OutputPaginate outputPaginate = null;
        String string = null, s = null;
        List<Contrat> contrats = null;

        TextView errorText = contentError.findViewById(R.id.error_text);
        TextView nodata = layoutNodata.findViewById(R.id.no_data);

        try {
            error = jsonObject.getAsJsonObject("errors");
            messageError = error.get("message").getAsString();
            errorText.setText(messageError);
            /*layoutConnexionLose.setVisibility(View.INVISIBLE);
            layoutNodata.setVisibility(View.INVISIBLE);
            contentError.setVisibility(View.VISIBLE);*/
            //contentMain.setVisibility(View.INVISIBLE);
        }catch (Exception ignored) {}

        try {
            sucess = jsonObject.getAsJsonObject("success");
            message = sucess.get("message").getAsString();
            nodata.setText(message);
            /*layoutConnexionLose.setVisibility(View.INVISIBLE);
            layoutNodata.setVisibility(View.VISIBLE);
            contentError.setVisibility(View.INVISIBLE);*/
            //contentMain.setVisibility(View.INVISIBLE);
        } catch (Exception ignored) {}

        try {
            outputPaginate = new Gson().fromJson(jsonObject, OutputPaginate.class);
        } catch (Exception ignored) {
        }

        try {
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Contrat>>() {}.getType();

            assert outputPaginate != null;
            string = gson.toJson(outputPaginate.getObjects());
            contrats = gson.fromJson(string, listType);
        }catch (Exception e) {
            e.printStackTrace();
        }

        try {
            displayData(contrats);
            listeSouscriptionAdpter = new ListeSouscriptionAdpter(getContext(), contrats);
            recyclerView.setAdapter(listeSouscriptionAdpter);
           /* layoutConnexionLose.setVisibility(View.INVISIBLE);
            layoutNodata.setVisibility(View.INVISIBLE);
            contentError.setVisibility(View.INVISIBLE);
            contentMain.setVisibility(View.VISIBLE);*/
        } catch (Exception ignored) {
        }
    }

    private void handleErrors(ResponseBody response) {

        ApiError apiError = Utils.converErrors(response);
        Toast.makeText(getActivity(),apiError.getErrors() + " " + apiError.getMessage(), Toast.LENGTH_LONG).show();
        System.out.println(apiError.getErrors() + " " + apiError.getMessage());

    }

    private void replaceFragment(Fragment fragment, String titre){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_main, fragment).commit();

        Main2Activity.getTextTitle().setText(titre);

    }

}
