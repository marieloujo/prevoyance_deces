package bj.assurance.prevoyancedeces.fragment.marchand;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import bj.assurance.prevoyancedeces.R;
import bj.assurance.prevoyancedeces.activity.MarchandMainActivity;
import bj.assurance.prevoyancedeces.adapter.NotificationAdapter;
import bj.assurance.prevoyancedeces.adapter.TransactionAdater;
import bj.assurance.prevoyancedeces.model.Client;
import bj.assurance.prevoyancedeces.model.Portefeuille;
import bj.assurance.prevoyancedeces.model.pagination.OutputPaginate;
import bj.assurance.prevoyancedeces.retrofit.RetrofitBuildForGetRessource;
import bj.assurance.prevoyancedeces.retrofit.Service.MarchandService;
import bj.assurance.prevoyancedeces.retrofit.TokenManager;
import bj.assurance.prevoyancedeces.utils.AccessToken;
import bj.assurance.prevoyancedeces.utils.ApiError;
import bj.assurance.prevoyancedeces.utils.Utils;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static androidx.constraintlayout.widget.Constraints.TAG;


public class Transactions extends Fragment {

    private RecyclerView recyclerView;
    private TransactionAdater transactionAdater;
    private List<Portefeuille> portefeuilles = new ArrayList<>();

    private ProgressBar progressBar, progressBarBottom;
    private ScrollView scrollView;

    private LinearLayout linearLayout;
    private RelativeLayout relativeLayout;

    private int FIRST_PAGE = 1;
    private int CURRENT_PAGE, LAST_PAGE;

    public Transactions() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transactions, container, false);

        init(view);
        getTransactions(
                TokenManager.getInstance(getActivity()
                        .getSharedPreferences("prefs", MODE_PRIVATE))
                        .getToken(), FIRST_PAGE
        );

        System.out.println("CURRENT_PAGE = " + CURRENT_PAGE + " LAST_PAGE = " + LAST_PAGE);

        return view;
    }

    @SuppressLint("WrongConstant")
    public void init(View view) {
        progressBar = view.findViewById(R.id.main_progress);
        progressBarBottom = view.findViewById(R.id.scroll_progress);
        recyclerView = view.findViewById(R.id.recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        scrollView = view.findViewById(R.id.main_scroll);
        linearLayout = view.findViewById(R.id.no_data_layout);
        relativeLayout = view.findViewById(R.id.no_connection);

        scrollView.getViewTreeObserver()
                .addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
                    @Override
                    public void onScrollChanged() {
                        if (scrollView.getChildAt(0).getBottom()
                                <= (scrollView.getHeight() + scrollView.getScrollY())) {
                            progressBarBottom.setVisibility(View.VISIBLE);
                            if (CURRENT_PAGE + 1 > LAST_PAGE ) {
                                progressBarBottom.setVisibility(View.INVISIBLE);
                                return;
                            } else {

                                CURRENT_PAGE +=1;

                                try {
                                    getNextTansaction(
                                            TokenManager.getInstance(getActivity()
                                                    .getSharedPreferences("prefs", MODE_PRIVATE))
                                                    .getToken());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            //scroll view is not at bottom
                        }
                    }
                });
    }


    private void getTransactions(AccessToken accessToken, int page) {

        Call<OutputPaginate> call;
        MarchandService service = new RetrofitBuildForGetRessource(accessToken).getRetrofit().create(MarchandService.class);
        call = service.getTransactions(MarchandMainActivity.getMarchand().getId(), FIRST_PAGE);
        call.enqueue(new Callback<OutputPaginate>() {
            @Override
            public void onResponse(Call<OutputPaginate> call, Response<OutputPaginate> response) {

                Log.w(TAG, "onResponse: " + response);

                if (response.isSuccessful()) {
                    System.out.println(response.body());

                    CURRENT_PAGE = response.body().getMetaPaginate().getCurrentPage();
                    LAST_PAGE = response.body().getMetaPaginate().getLastPage();

                    Gson gson = new Gson();
                    Type listType = new TypeToken<ArrayList<Portefeuille>>(){}.getType();

                    String object = gson.toJson(response.body().getObjects());

                    portefeuilles = gson.fromJson(object, listType);

                    transactionAdater = new TransactionAdater(getContext(), portefeuilles);
                    recyclerView.setAdapter(transactionAdater);

                    progressBar.setVisibility(View.INVISIBLE);


                } else {
                    if (response.code() == 422) {
                        System.out.println(response.errorBody().source());
                        handleErrors(response.errorBody());
                    }
                    if (response.code() == 401) {
                        ApiError apiError = Utils.converErrors(response.errorBody());
                        Toast.makeText(getContext(), apiError.getMessage(), Toast.LENGTH_LONG).show();
                    }

                    progressBar.setVisibility(View.INVISIBLE);
                    //relativeLayout.setVisibility(View.VISIBLE);

                }

            }

            @Override
            public void onFailure(Call<OutputPaginate> call, Throwable t) {
                Log.w(TAG, "onFailure: " + t.getMessage());
                progressBar.setVisibility(View.INVISIBLE);
                //linearLayout.setVisibility(View.VISIBLE);
                //Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void handleErrors(ResponseBody response) {

        ApiError apiError = Utils.converErrors(response);

    }

    private void getNextTansaction(AccessToken accessToken) {
        Call<OutputPaginate> call;
        MarchandService service = new RetrofitBuildForGetRessource(accessToken).getRetrofit().create(MarchandService.class);
        call = service.getTransactions(MarchandMainActivity.getMarchand().getId(), CURRENT_PAGE);
        call.enqueue(new Callback<OutputPaginate>() {
            @Override
            public void onResponse(Call<OutputPaginate> call, Response<OutputPaginate> response) {

                Log.w(TAG, "onResponse: " + response);

                if (response.isSuccessful()) {
                    System.out.println(response.body());

                    CURRENT_PAGE = response.body().getMetaPaginate().getCurrentPage();
                    LAST_PAGE = response.body().getMetaPaginate().getLastPage();

                    Gson gson = new Gson();
                    Type listType = new TypeToken<ArrayList<Portefeuille>>(){}.getType();

                    String object = gson.toJson(response.body().getObjects());

                    portefeuilles.addAll(gson.fromJson(object, listType));

                    transactionAdater.notifyDataSetChanged();

                    progressBarBottom.setVisibility(View.INVISIBLE);


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
            public void onFailure(Call<OutputPaginate> call, Throwable t) {
                Log.w(TAG, "onFailure: " + t.getMessage());
                //Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
