package bj.assurance.prevoyancedeces.fragment.client;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kinda.alert.KAlertDialog;

import java.util.ArrayList;
import java.util.List;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import bj.assurance.prevoyancedeces.R;
import bj.assurance.prevoyancedeces.Utils.AccessToken;
import bj.assurance.prevoyancedeces.Utils.ApiError;
import bj.assurance.prevoyancedeces.Utils.Utils;
import bj.assurance.prevoyancedeces.activity.Connexion;
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

    private ListeSouscriptionAdpter articleAdapter;
    private RecyclerView recyclerView;
    private KAlertDialog pDialog;

    private RelativeLayout  layoutConnexionLose;
    private LinearLayout layoutNodata;

    private TextView portefeuilActuel, portefeulTotal, readMore;
    private ProgressBar progressBar;

    private CardView cardView, cardView1;



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

        return view;
    }

    @SuppressLint("WrongConstant")
    public void init(View view) {
        layoutConnexionLose = view.findViewById(R.id.no_connection);
        layoutNodata = view.findViewById(R.id.layout_nothing);
        recyclerView = view.findViewById(R.id.recycler);
        portefeuilActuel = view.findViewById(R.id.totalPortefeuilClient);
        portefeulTotal = view.findViewById(R.id.totalMaxPortefeuilClient);
        progressBar = view.findViewById(R.id.my_progressBar);
        readMore = view.findViewById(R.id.seeMore);
        cardView = view.findViewById(R.id.gestion_immobilier);
        cardView1 = view.findViewById(R.id.organistion_funerail);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        pDialog = new KAlertDialog(getActivity(), KAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#2d8df8"));
        pDialog.setTitleText("Veuillez patienter");
        pDialog.setCancelable(false);
        pDialog.show();

        //System.out.println( Main2Activity.getClient().toString() );

        //generateContrat();

        getContratsForUser(TokenManager.getInstance(getActivity().
                getSharedPreferences("prefs", MODE_PRIVATE)).
                getToken());

        try {
            displayData();
        } catch (Exception ignored) {}

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

    private void displayData() {

        Integer portefeuil = 0, paiement = 0;

        for (int i = 0; i < Main2Activity.getClient().getContrats().size(); i++) {
            for (int j = 0; i < Main2Activity.getClient().getContrats().get(i).getTransactions().size(); j++)
                portefeuil += Integer.valueOf(Main2Activity.getClient().getContrats().get(i).getTransactions().get(j).getMontant());
        }

        paiement = 12 * Main2Activity.getClient().getContrats().size();

        int status = (portefeuil / paiement) * 100;

        portefeuilActuel.setText(portefeuil);
        portefeulTotal.setText(paiement);

        progressBar.setProgress(status);

    }

    private void getContratsForUser(AccessToken accessToken) {

        try {
            Call<Client> call;
            ClientService service = new RetrofitBuildForGetRessource(accessToken).getRetrofit().create(ClientService.class);
            call = service.getClientbyId(Main2Activity.getClient().getId());
            call.enqueue(new Callback<Client>() {
                @Override
                public void onResponse(Call<Client> call, Response<Client> response) {

                    Log.w(TAG, "onResponse: " + response);

                    if (response.isSuccessful()) {
                        if (response.body().getContrats().isEmpty()) {
                            System.out.println(response.body());
                            layoutConnexionLose.setVisibility(View.INVISIBLE);
                            layoutNodata.setVisibility(View.VISIBLE);
                            pDialog.dismiss();
                        } else {
                            System.out.println(response.body());
                            articleAdapter = new ListeSouscriptionAdpter(getContext(), response.body().getContrats());
                            recyclerView.setAdapter(articleAdapter);

                            layoutConnexionLose.setVisibility(View.INVISIBLE);
                            layoutNodata.setVisibility(View.INVISIBLE);
                            pDialog.dismiss();
                        }
                    } else {

                        if (response.code() == 422) {
                            System.out.println(response.errorBody().source());
                            handleErrors(response.errorBody());
                            pDialog.dismiss();
                        }

                        if (response.code() == 401) {
                            ApiError apiError = Utils.converErrors(response.errorBody());
                            Toast.makeText(getContext(), apiError.getMessage(), Toast.LENGTH_LONG).show();
                            pDialog.dismiss();
                        }
                    }

                }

                @Override
                public void onFailure(Call<Client> call, Throwable t) {
                    Log.w(TAG, "onFailure: " + t.getMessage());
                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();

                    layoutConnexionLose.setVisibility(View.VISIBLE);
                    layoutNodata.setVisibility(View.INVISIBLE);

                    pDialog.dismiss();
                }
            });
        } catch (Exception e) {
            pDialog.dismiss();
        }
    }

    private void handleErrors(ResponseBody response) {

        ApiError apiError = Utils.converErrors(response);
        Toast.makeText(getActivity(),apiError.getErrors() + " " + apiError.getMessage(), Toast.LENGTH_LONG).show();
        System.out.println(apiError.getErrors() + " " + apiError.getMessage());

    }

    private void generateContrat() {
        List<Contrat> contrats = new ArrayList<>();

        if (contrats.isEmpty()) {
            layoutConnexionLose.setVisibility(View.INVISIBLE);
            layoutNodata.setVisibility(View.VISIBLE);

            articleAdapter = new ListeSouscriptionAdpter(getContext(), contrats);
            recyclerView.setAdapter(articleAdapter);
        } else {

        }
    }

    private void replaceFragment(Fragment fragment, String titre){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_main, fragment).commit();

        Main2Activity.getTextTitle().setText(titre);

    }

}
