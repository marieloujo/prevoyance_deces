package bj.assurance.assurancedeces.fragment.supermarchand;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.labters.lottiealertdialoglibrary.ClickListener;
import com.labters.lottiealertdialoglibrary.LottieAlertDialog;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import bj.assurance.assurancedeces.R;
import bj.assurance.assurancedeces.activity.SuperMarchandActivity;
import bj.assurance.assurancedeces.fragment.CatchError;
import bj.assurance.assurancedeces.model.Marchand;
import bj.assurance.assurancedeces.model.pagination.OutputPaginate;
import bj.assurance.assurancedeces.recyclerViewAdapter.MesMarchandAdapter;
import bj.assurance.assurancedeces.serviceImplementation.SuperMarchandServiceImplementation;
import bj.assurance.assurancedeces.utils.CatchApiError;
import bj.assurance.assurancedeces.utils.CustomJsonObject;
import bj.assurance.assurancedeces.utils.GetResponseObject;
import bj.assurance.assurancedeces.utils.ShowDialog;
import bj.assurance.assurancedeces.utils.onConnexionListener;
import bj.assurance.assurancedeces.utils.token.TokenManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


public class MesMarchands extends Fragment {



    private RecyclerView recyclerView;
    private GetResponseObject getResponseObject;
    private ShowDialog showDialog;

    private ProgressBar progressBar ;


    public MesMarchands() {




    }






    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_mes_marchands, container, false);


        init(view);


        return view;
    }




    private void init(View view) {

        findView(view);
        initValue();

    }





    private void findView(View view) {

        recyclerView = view.findViewById(R.id.recycler);
        progressBar = view.findViewById(R.id.main_progress);

    }




    @SuppressLint("WrongConstant")
    private void initValue() {

        getResponseObject = new GetResponseObject();
        showDialog = new ShowDialog(getContext());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        getListMarchand(SuperMarchandActivity.getSuperMarchand().getId());

    }






    private void getListMarchand(final Long id) {

        progressBar.setVisibility(View.VISIBLE);

        new SuperMarchandServiceImplementation(

                TokenManager.getInstance(getActivity().
                        getSharedPreferences("prefs", MODE_PRIVATE)).
                        getToken()

        ).listMarchandsOfSupermarchand(id)

            .enqueue(new Callback<JsonObject>() {


                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                            if (response.isSuccessful()) {

                                try {

                                    Gson gson = new Gson();
                                    Type listType = new TypeToken<List<Marchand>>() {}.getType();


                                    OutputPaginate outputPaginate = new Gson().fromJson(
                                            response.body(), OutputPaginate.class);


                                    List<Marchand>  marchands = gson.fromJson(

                                            gson.toJson(outputPaginate.getObjects()),
                                            listType

                                    );
                                    marchands.remove(null);

                                    MesMarchandAdapter mesMarchandAdapter = new MesMarchandAdapter(getContext(), createCustumMarchand(marchands));
                                    recyclerView.setAdapter(mesMarchandAdapter);

                                    progressBar.setVisibility(View.INVISIBLE);

                                } catch (Exception e) {

                                    e.printStackTrace();

                                }

                            } else {


                                try {

                                    progressBar.setVisibility(View.INVISIBLE);

                                    String error = new CatchApiError(response.code()).catchError(getContext());

                                    if (response.code() == 404) {

                                        replaceFragment(new CatchError("Vous n'avez aucun marchand", "", false),
                                                "Mes marchands");

                                    } else {

                                        if (response.code() != 401) {

                                            replaceFragment(new CatchError(error, "Reporter", true), "Mes marchands");

                                        }

                                    }

                                } catch (Exception e) {

                                    e.printStackTrace();

                                }


                                try {

                                    progressBar.setVisibility(View.INVISIBLE);

                                    new CatchApiError(response.code()).catchError(getContext());

                                    System.out.println("marchands "+ response.code());

                                } catch (Exception e) {

                                    e.printStackTrace();

                                }

                            }

                        }



                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {

                            try {

                                progressBar.setVisibility(View.INVISIBLE);

                                if (new onConnexionListener(getContext()).checkConnexion()) {

                                    replaceFragment(new CatchError("Serveur en maintenance veuillez patatientez ..",
                                            "Reporter", false), "Mes marchands");

                                } else {

                                    CatchError catchError = new CatchError("Aucune connexion internet." +
                                            " Vous naviguez en mode non connecté, vérifier votre connexion internet et réessayer",
                                            "Reéssayer", true);

                                    replaceFragment(catchError, "Mes marchands");

                                }


                            } catch (Exception e) {

                                progressBar.setVisibility(View.INVISIBLE);

                                e.printStackTrace();

                            }


                        }


                    });
        }








    private List<Accueil.CustumMarchand> createCustumMarchand(List<Marchand> marchands) {

        List<Accueil.CustumMarchand> custumMarchands = new ArrayList<>();

        for (Marchand marchand : marchands) {

            custumMarchands.add(

                    new Accueil.CustumMarchand(
                            marchand,
                            -1
                    )

            );

        }

        return custumMarchands;
    }







    private void replaceFragment(Fragment fragment, String title) {


        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_main, fragment).commit();


        SuperMarchandActivity.getFrameTitle().setText(title);

    }








}
