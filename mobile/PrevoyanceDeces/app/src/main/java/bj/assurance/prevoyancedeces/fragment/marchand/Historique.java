package bj.assurance.prevoyancedeces.fragment.marchand;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.kinda.alert.KAlertDialog;

import java.util.ArrayList;
import java.util.List;

import bj.assurance.prevoyancedeces.R;
import bj.assurance.prevoyancedeces.utils.AccessToken;
import bj.assurance.prevoyancedeces.utils.ApiError;
import bj.assurance.prevoyancedeces.utils.Utils;
import bj.assurance.prevoyancedeces.model.Compte;
import bj.assurance.prevoyancedeces.retrofit.RetrofitBuildForGetRessource;
import bj.assurance.prevoyancedeces.retrofit.Service.MarchandService;
import ir.farshid_roohi.linegraph.ChartEntity;
import ir.farshid_roohi.linegraph.LineChart;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class Historique extends Fragment {

    LinearLayout appBar, nextBar;
    LineChart lineChart;

    float[] graph1 = {113000f, 183000f, 188000f, 695000f, 324000f, 230000f, 188000f, 15000f, 126000f, 5000f, 33000f, 324000f, 230000f, 188000f, 15000f, 126000f, 5000f, 33000, 47000f, 20000f, 12000f, 124400f, 160000f};
    float[] graph2 = {0f, 245000f, 1011000f, 1000f, 0f, 0f, 47000f, 20000f, 12000f, 124400f, 160000f, 47000f, 20000f, 12000f, 124400f, 160000f, 324000f, 230000f, 188000f, 15000f, 126000f, 5000f, 33000};
    String[] legendArr = {"05/21", "05/22", "05/23", "05/24", "05/25", "05/26", "05/27", "05/28", "05/29", "05/30", "05/31", "05/24", "05/25", "05/26", "05/27", "05/28", "05/29", "05/30", "05/31", "05/24", "05/25", "05/26", "05/27", "05/28", "05/29", "05/30", "05/31"};

    private List<Compte> creditVirtules;
    private List<Compte> commisions;

    float[] commissionsList, creditVirtuelleList;

    public Historique() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_historique, container, false);

        lineChart = view.findViewById(R.id.lineChart);

        ChartEntity firstChartEntity = new  ChartEntity(Color.WHITE, graph1);
        ChartEntity secondChartEntity = new  ChartEntity(Color.YELLOW, graph2);

        ArrayList<ChartEntity> list = new  ArrayList();
        list.add(firstChartEntity);
        list.add(secondChartEntity);
        lineChart.setLegendArray(legendArr);
        lineChart.setList(list);

        return view;
    }

    public void getListCompte(AccessToken accessToken) {
        Call<List<Compte>> call;
        MarchandService service = new RetrofitBuildForGetRessource(accessToken).getRetrofit().create(MarchandService.class);
        call = service.getEvolution();
        call.enqueue(new Callback<List<Compte>>() {
            @Override
            public void onResponse(Call<List<Compte>> call, Response<List<Compte>> response) {

                Log.w(TAG, "onResponse: " + response);

                if (response.isSuccessful()) {
                    //System.out.println(response.body());
                    setValue(response.body());

                } else {
                    if (response.code() == 422) {
                        System.out.println(response.errorBody().source());
                        handleErrors(response.errorBody());
                    }
                    if (response.code() == 401) {
                        ApiError apiError = Utils.converErrors(response.errorBody());
                        Toast.makeText(getContext(), apiError.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<List<Compte>> call, Throwable t) {
                Log.w(TAG, "onFailure: " + t.getMessage());
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();

                new KAlertDialog(getContext(), KAlertDialog.WARNING_TYPE)
                        .setTitleText("Connexion impossibe au serveur")
                        .setContentText("Oups!!! quelque chose s'est mal passé vérifier votre connexion internet et réessayer")
                        .showCancelButton(true)
                        .setCancelClickListener(new KAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(KAlertDialog sDialog) {
                                sDialog.cancel();
                            }
                        })
                        .show();
            }
        });
    }

    private void handleErrors(ResponseBody response) {

        ApiError apiError = Utils.converErrors(response);

    }

    public void setValue(List<Compte> comptes) {
        for (int i = 0; i<comptes.size(); i++) {
            if (comptes.get(i).getCompte().equals("credit_virtuel")) {
                creditVirtules.add(comptes.get(i));
            } else if (comptes.get(i).getCompte().equals("commission")) {
                commisions.add(comptes.get(i));
            }
        }
    }
    





}
