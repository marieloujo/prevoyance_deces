package bj.assurance.assurancedeces.fragment.marchand;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.labters.lottiealertdialoglibrary.ClickListener;
import com.labters.lottiealertdialoglibrary.LottieAlertDialog;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;
import com.stone.vega.library.VegaLayoutManager;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import bj.assurance.assurancedeces.R;
import bj.assurance.assurancedeces.activity.MarchandActivity;
import bj.assurance.assurancedeces.model.Portefeuille;
import bj.assurance.assurancedeces.model.pagination.OutputPaginate;
import bj.assurance.assurancedeces.recyclerViewAdapter.AllTransactionAdater;
import bj.assurance.assurancedeces.serviceImplementation.MarchandServiceImplementation;
import bj.assurance.assurancedeces.utils.CatchApiError;
import bj.assurance.assurancedeces.utils.CustomJsonObject;
import bj.assurance.assurancedeces.utils.GetResponseObject;
import bj.assurance.assurancedeces.utils.ShowDialog;
import bj.assurance.assurancedeces.utils.token.TokenManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;




public class Transactions extends Fragment {



    private RecyclerView recyclerView;
    private ProgressBar mainProgress, scrollProgress;
    private SwipyRefreshLayout refreshLayout;


    private GetResponseObject getResponseObject;
    private ShowDialog showDialog;
    private AllTransactionAdater allTransactionAdater;


    private List<Portefeuille> portefeuilles = new ArrayList<>();



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


        return view;
    }




    private void init(View view) {


        findView(view);
        initValue();
        onClickListener();

    }



    private void findView (View view) {


        recyclerView = view.findViewById(R.id.recycler);


        mainProgress = view.findViewById(R.id.main_progress);
        scrollProgress = view.findViewById(R.id.scroll_progress);

        refreshLayout = view.findViewById(R.id.main_scroll);

    }




    @SuppressLint("WrongConstant")
    private void initValue() {

        recyclerView.setLayoutManager(new VegaLayoutManager());


        getResponseObject = new GetResponseObject();
        showDialog = new ShowDialog(getContext());


        getAllTransaction(
                MarchandActivity.getMarchand().getId(),
                1, true
        );

    }





    private void onClickListener() {

        refreshLayout.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {

                System.out.println(CURRENT_PAGE + " " + LAST_PAGE );

                if (CURRENT_PAGE + 1 < LAST_PAGE) {

                    getAllTransaction(
                            MarchandActivity.getMarchand().getId(),
                            CURRENT_PAGE + 1,
                            false
                    );

                } else if (CURRENT_PAGE  == LAST_PAGE) {

                    refreshLayout.setRefreshing(false);

                }

            }
        });

    }





    private void getAllTransaction(final Long id, final int page, final boolean isFirstPage) {


        new MarchandServiceImplementation(


                TokenManager.getInstance(getActivity().
                        getSharedPreferences("prefs", MODE_PRIVATE)).
                        getToken()


        ).allListTransactionsOfMarchand(id, page)

                .enqueue(new Callback<JsonObject>() {



                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {


                        if (response.isSuccessful()) {

                            getResponseObject.setJsonObject(response.body());

                            CustomJsonObject jsonObject = getResponseObject.getResponse();


                            Gson gson = new Gson();
                            Type listType = new TypeToken<List<Portefeuille>>() {}.getType();


                            OutputPaginate outputPaginate = new Gson().fromJson(
                                    jsonObject.getJsonObject(), OutputPaginate.class);


                            CURRENT_PAGE = outputPaginate.getMetaPaginate().getCurrentPage();
                            LAST_PAGE = outputPaginate.getMetaPaginate().getLastPage();


                            List<Portefeuille> portefeuilleList = gson.fromJson (

                                    gson.toJson(outputPaginate.getObjects()),

                                    listType

                            );

                            portefeuilles.addAll(portefeuilleList);


                            if (isFirstPage) {

                                allTransactionAdater = new AllTransactionAdater(getContext(),portefeuilles);
                                recyclerView.setAdapter(allTransactionAdater);

                                mainProgress.setVisibility(View.INVISIBLE);

                            } else {

                                allTransactionAdater.notifyDataSetChanged();
                                refreshLayout.setRefreshing(false);

                            }



                            //System.out.println(portefeuilles);

                        } else {


                            String eror = new CatchApiError(response.code()).catchError(getContext());

                        }


                    }




                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {


                        try {

                            showDialog.showNoInternetDialog()

                                    .setPositiveListener( new ClickListener() {
                                        @Override
                                        public void onClick(@NotNull LottieAlertDialog lottieAlertDialog) {

                                            lottieAlertDialog.dismiss();

                                            getAllTransaction(id, page, isFirstPage);
                                        }
                                    })

                                    .setNegativeListener( new ClickListener() {
                                        @Override
                                        public void onClick(@NotNull LottieAlertDialog lottieAlertDialog) {
                                            lottieAlertDialog.dismiss();
                                        }
                                    })

                                    .setNegativeText("Annuler")
                                    .setNegativeButtonColor(Color.parseColor("#e57373"))
                                    .setNegativeTextColor(Color.parseColor("#ffffffff"))

                                    .build()
                                    .show();

                        } catch (Exception ignored) {}


                    }


                });

    }








}
