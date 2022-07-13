package bj.assurance.assurancedeces.fragment.marchand;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import bj.assurance.assurancedeces.R;
import bj.assurance.assurancedeces.activity.MarchandActivity;
import bj.assurance.assurancedeces.fragment.CatchError;
import bj.assurance.assurancedeces.model.Portefeuille;
import bj.assurance.assurancedeces.model.pagination.OutputPaginate;
import bj.assurance.assurancedeces.recyclerViewAdapter.AllTransactionAdater;
import bj.assurance.assurancedeces.serviceImplementation.MarchandServiceImplementation;
import bj.assurance.assurancedeces.utils.CatchApiError;
import bj.assurance.assurancedeces.utils.NotificationReader;
import bj.assurance.assurancedeces.utils.OnBackPressedListener;
import bj.assurance.assurancedeces.utils.onConnexionListener;
import bj.assurance.assurancedeces.utils.token.TokenManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


public class Transactions extends Fragment implements OnBackPressedListener {



    private RecyclerView recyclerView;
    private ProgressBar mainProgress, scrollProgress;
    private SwipyRefreshLayout refreshLayout;


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


        new NotificationReader(getContext()).unReadNotifications(MarchandActivity.getUtilisateur().getId());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);


        recyclerView.setLayoutManager(linearLayoutManager);

        //recyclerView.setLayoutManager(new VegaLayoutManager());

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

                if (CURRENT_PAGE + 1 <= LAST_PAGE) {

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

                            try {

                                Gson gson = new Gson();
                                Type listType = new TypeToken<List<Portefeuille>>() {}.getType();


                                OutputPaginate outputPaginate = new Gson().fromJson(
                                        response.body(), OutputPaginate.class);


                                CURRENT_PAGE = outputPaginate.getMetaPaginate().getCurrentPage();
                                LAST_PAGE = outputPaginate.getMetaPaginate().getLastPage();


                                List<Portefeuille> portefeuilleList = gson.fromJson (

                                        gson.toJson(outputPaginate.getObjects()),

                                        listType

                                );

                                portefeuilles.addAll(portefeuilleList);

                                mainProgress.setVisibility(View.INVISIBLE);


                                if (isFirstPage) {

                                    allTransactionAdater = new AllTransactionAdater(getContext(),portefeuilles);
                                    recyclerView.setAdapter(allTransactionAdater);
                                    recyclerView.setVisibility(View.VISIBLE);

                                    mainProgress.setVisibility(View.INVISIBLE);

                                } else {

                                    allTransactionAdater.notifyDataSetChanged();
                                    refreshLayout.setRefreshing(false);

                                }


                            } catch (Exception e) {

                                e.printStackTrace();

                            }


                            //System.out.println(portefeuilles);

                        } else {

                            try {

                                mainProgress.setVisibility(View.INVISIBLE);

                                String error = new CatchApiError(response.code()).catchError(getContext());

                                if (response.code() == 404) {

                                    replaceFragment(new CatchError("Aucune transaction effectuée", "", false));

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

                            }

                                /*replaceFragment(new CatchError("Aucune connexion internet." +
                                        " Vous naviguez en mode non connecté, vérifier votre connexion internet et réessayer",
                                        "Reéssayer", true));*/


                        } catch (Exception e) {

                            mainProgress.setVisibility(View.INVISIBLE);

                            e.printStackTrace();

                        }



                    }


                });

    }





    @SuppressLint("SetTextI18n")
    private void replaceFragment(Fragment fragment) {

        FragmentManager fragmentManager = ((MarchandActivity)getContext()).getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_main, fragment).commit();

    }


    @Override
    public void doBack() {
        replaceFragment(new Accueil());

        MarchandActivity.getFrameTitle().setText("Salut " + MarchandActivity.getUtilisateur().getPrenom());

    }
}
