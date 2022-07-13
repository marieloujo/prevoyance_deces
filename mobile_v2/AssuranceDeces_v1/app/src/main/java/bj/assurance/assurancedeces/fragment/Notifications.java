package bj.assurance.assurancedeces.fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import bj.assurance.assurancedeces.R;
import bj.assurance.assurancedeces.activity.ClientActivity;
import bj.assurance.assurancedeces.activity.MarchandActivity;
import bj.assurance.assurancedeces.activity.SuperMarchandActivity;
import bj.assurance.assurancedeces.model.Notification;
import bj.assurance.assurancedeces.recyclerViewAdapter.NotificationAdapter;
import bj.assurance.assurancedeces.serviceImplementation.UtilisateurServiceImplementation;
import bj.assurance.assurancedeces.utils.CatchApiError;
import bj.assurance.assurancedeces.utils.ShowDialog;
import bj.assurance.assurancedeces.utils.onConnexionListener;
import bj.assurance.assurancedeces.utils.token.TokenManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


@SuppressLint("ValidFragment")
public class Notifications extends Fragment {



    private RecyclerView recyclerView;
    private ProgressBar progressBar;


    private ShowDialog showDialog;
    private Long id;



    @SuppressLint("ValidFragment")
    public Notifications(Long id) {

        this.id = id;

    }







    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_notifications, container, false);


        init(view);


        return view;
    }




    private void init(View view) {


        findView(view);
        initValue();


        unReadNotifications(

                id

        );

    }




    private void findView(View view) {

        recyclerView = view.findViewById(R.id.recycler);
        progressBar = view.findViewById(R.id.main_progress);

    }



    @SuppressLint("WrongConstant")
    private void initValue() {


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);


        showDialog = new ShowDialog(getContext());


    }





    private void unReadNotifications(final Long id) {


        new UtilisateurServiceImplementation(getContext())

            .unReadNotifications(id,

                TokenManager.getInstance(
                        ((FragmentActivity)getContext()).getSharedPreferences("prefs", MODE_PRIVATE)).
                        getToken()

            )

                .enqueue(new Callback<JsonArray>() {


                    @Override
                    public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {

                        if (response.isSuccessful()) {

                            Type listType = new TypeToken<List<Notification>>() {}.getType();
                            List<Notification> notifications = new Gson().fromJson(response.body(), listType);

                            progressBar.setVisibility(View.INVISIBLE);

                            if (notifications == null) {

                                replaceFragment(new NoDataFound(R.mipmap.notification_off, "Aucune nouvelle notification"));

                            } else  {

                                NotificationAdapter notificationAdapter = new NotificationAdapter(getContext(), notifications);
                                recyclerView.setAdapter(notificationAdapter);

                                markReadNotifications(id);

                            }


                            try {

                                SuperMarchandActivity.getIsRead().setVisibility(View.INVISIBLE);
                            } catch (Exception ignored) {}


                            try {
                                MarchandActivity.getIsRead().setVisibility(View.INVISIBLE);
                            } catch (Exception ignored) {}


                            try {
                                ClientActivity.getIsRead().setVisibility(View.INVISIBLE);

                            } catch (Exception ignored) {}


                        } else {



                            try {

                                progressBar.setVisibility(View.INVISIBLE);

                                String error = new CatchApiError(response.code()).catchError(getContext());

                                if (response.code() == 404) {

                                    replaceFragment(new NoDataFound(R.mipmap.notification_off, "Aucune nouvelle notification"));

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
                    public void onFailure(Call<JsonArray> call, Throwable t) {

                        progressBar.setVisibility(View.INVISIBLE);


                        try {

                            progressBar.setVisibility(View.INVISIBLE);

                            if (new onConnexionListener(getContext()).checkConnexion()) {

                                replaceFragment(new CatchError("Serveur en maintenance veuillez patatientez ..",
                                        "Reporter", false));

                            } else

                                replaceFragment(new CatchError("Aucune connexion internet." +
                                        " Vous naviguez en mode non connecté, vérifier votre connexion internet et réessayer",
                                        "Reéssayer", true));


                        } catch (Exception e) {

                            progressBar.setVisibility(View.INVISIBLE);

                            e.printStackTrace();

                        }

                    }


                });


    }




    private void markReadNotifications(Long id) {


        new UtilisateurServiceImplementation(getContext()).markReadNotifications(id,

                TokenManager.getInstance(
                        ((FragmentActivity)getContext()).getSharedPreferences("prefs", MODE_PRIVATE)).
                        getToken()

            ).enqueue(new Callback<JsonObject>() {


            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                if (response.isSuccessful()) {


                    System.out.println(response.body());

                } else {

                    System.out.println(response.code());

                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

                System.out.println(t.getMessage());

            }


        });


    }





    @SuppressLint("SetTextI18n")
    private void replaceFragment(Fragment fragment) {

        FragmentManager fragmentManager = ((FragmentActivity) getContext()).getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_main, fragment).commit();

    }



}
