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
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.kinda.alert.KAlertDialog;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import bj.assurance.prevoyancedeces.R;
import bj.assurance.prevoyancedeces.activity.MarchandMainActivity;
import bj.assurance.prevoyancedeces.adapter.TransactionAdater;
import bj.assurance.prevoyancedeces.model.Portefeuille;
import bj.assurance.prevoyancedeces.model.pagination.OutputPaginate;
import bj.assurance.prevoyancedeces.retrofit.TokenManager;
import bj.assurance.prevoyancedeces.utils.AccessToken;
import bj.assurance.prevoyancedeces.utils.ApiError;
import bj.assurance.prevoyancedeces.utils.Utils;
import bj.assurance.prevoyancedeces.model.Compte;
import bj.assurance.prevoyancedeces.retrofit.RetrofitBuildForGetRessource;
import bj.assurance.prevoyancedeces.retrofit.Service.MarchandService;
import im.dacer.androidcharts.LineView;
import ir.farshid_roohi.linegraph.ChartEntity;
import ir.farshid_roohi.linegraph.LineChart;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static androidx.constraintlayout.widget.Constraints.TAG;

public class Historique extends Fragment {

    int randomint = 9;

    LineView lineView;


    private List<Float> creditVirtules = new ArrayList<>();
    private List<Float> commisions = new ArrayList<>();

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

        init(view);
        getListCompte(
            TokenManager.getInstance(getActivity().
                getSharedPreferences("prefs", MODE_PRIVATE)).
                getToken()
        );

        return view;
    }

    public void init(View view) {

        lineView = (LineView)view.findViewById(R.id.line_view);
        lineView.setDrawDotLine(false); //optional
        lineView.setShowPopup(LineView.SHOW_POPUPS_MAXMIN_ONLY); //optional
        lineView.setColorArray(new int[]{Color.parseColor("#424345"),Color.parseColor("#2dad58")});

        ArrayList<String> test = new ArrayList<String>();
        for (int i = 0; i < randomint; i++) {
            test.add(String.valueOf(i + 1));
        }
        lineView.setBottomTextList(test);
    }

    private void getListCompte(AccessToken accessToken) {
        Call<JsonObject> call;
        MarchandService service = new RetrofitBuildForGetRessource(accessToken).getRetrofit().create(MarchandService.class);
        call = service.getEvolution(MarchandMainActivity.getMarchand().getId());
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                Log.w(TAG, "onResponse: " + response);

                if (response.isSuccessful()) {
                    System.out.println(response.body());
                    getResponseTransaction(response.body());
                    //setValue(response.body());

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
            public void onFailure(Call<JsonObject> call, Throwable t) {
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
                creditVirtules.add(Float.valueOf(comptes.get(i).getMontant()));
            } else if (comptes.get(i).getCompte().equals("commission")) {
                commisions.add(Float.valueOf(comptes.get(i).getMontant()));
            }
        }

        ArrayList<ArrayList<Float>> graphes = new ArrayList<>();
        graphes.add((ArrayList<Float>) commisions);
        graphes.add((ArrayList<Float>) creditVirtules);

        lineView.setFloatDataList(graphes);

    }

    private void getResponseTransaction(JsonObject jsonObject) {
        JsonObject error = null, sucess = null;
        String messageError = null, message = null;
        OutputPaginate outputPaginate = null;
        List<Compte> comptes = null;

        try {
            error = jsonObject.getAsJsonObject("errors");
            messageError = error.get("message").getAsString();
        }catch (Exception ignored) {}

        try {
            sucess = jsonObject.getAsJsonObject("success");
            message = sucess.get("message").getAsString();
        } catch (Exception ignored) {}

        try {
            outputPaginate = new Gson().fromJson(jsonObject, OutputPaginate.class);
        }catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Compte>>() {}.getType();

            assert outputPaginate != null;
            comptes = gson.fromJson(gson.toJson(outputPaginate.getObjects()), listType);

            setValue(comptes);

            System.out.println("comptes: " + comptes.toString() + "\n" +
                    "commissions: " + commisions.toString() + "\n" +
                    "credit_virtuelle" + creditVirtules + "\n");

        }catch (Exception e) {
            e.printStackTrace();
        }

    }





}
