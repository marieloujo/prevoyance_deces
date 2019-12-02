package bj.assurance.prevoyancedeces.fragment.client;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.kinda.alert.KAlertDialog;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import bj.assurance.prevoyancedeces.R;
import bj.assurance.prevoyancedeces.Utils.AccessToken;
import bj.assurance.prevoyancedeces.Utils.ApiError;
import bj.assurance.prevoyancedeces.Utils.Utils;
import bj.assurance.prevoyancedeces.activity.Main2Activity;
import bj.assurance.prevoyancedeces.adapter.ListeSouscriptionAdpter;
import bj.assurance.prevoyancedeces.model.Client;
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

    RelativeLayout layoutNodata, layoutConnexionLose;

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

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        System.out.println( Main2Activity.getClient().toString() );

        /*getContratsForUser(TokenManager.getInstance(getActivity().
                getSharedPreferences("prefs", MODE_PRIVATE)).
                getToken());*/


    }

    public void getContratsForUser(AccessToken accessToken) {

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
                    } else {
                        System.out.println(response.body());
                        articleAdapter = new ListeSouscriptionAdpter(getContext(), response.body().getContrats());
                        recyclerView.setAdapter(articleAdapter);

                        layoutConnexionLose.setVisibility(View.INVISIBLE);
                        layoutNodata.setVisibility(View.INVISIBLE);
                    }
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
            public void onFailure(Call<Client> call, Throwable t) {
                Log.w(TAG, "onFailure: " + t.getMessage());
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();

                layoutConnexionLose.setVisibility(View.VISIBLE);
                layoutNodata.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void handleErrors(ResponseBody response) {

        ApiError apiError = Utils.converErrors(response);
        Toast.makeText(getActivity(),apiError.getErrors() + " " + apiError.getMessage(), Toast.LENGTH_LONG).show();
        System.out.println(apiError.getErrors() + " " + apiError.getMessage());

    }

}
