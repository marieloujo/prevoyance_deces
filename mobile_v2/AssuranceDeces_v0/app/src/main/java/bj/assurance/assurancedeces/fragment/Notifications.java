package bj.assurance.assurancedeces.fragment;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.labters.lottiealertdialoglibrary.ClickListener;
import com.labters.lottiealertdialoglibrary.DialogTypes;
import com.labters.lottiealertdialoglibrary.LottieAlertDialog;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import bj.assurance.assurancedeces.R;
import bj.assurance.assurancedeces.activity.MarchandActivity;
import bj.assurance.assurancedeces.model.Compte;
import bj.assurance.assurancedeces.model.Notification;
import bj.assurance.assurancedeces.model.customModel.ValidationEror;
import bj.assurance.assurancedeces.recyclerViewAdapter.NotificationAdapter;
import bj.assurance.assurancedeces.serviceImplementation.UtilisateurServiceImplementation;
import bj.assurance.assurancedeces.utils.CatchApiError;
import bj.assurance.assurancedeces.utils.CustomJsonObject;
import bj.assurance.assurancedeces.utils.GetResponseObject;
import bj.assurance.assurancedeces.utils.ShowDialog;
import bj.assurance.assurancedeces.utils.token.TokenManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


public class Notifications extends Fragment {



    private RecyclerView recyclerView;

    private ShowDialog showDialog;
    private GetResponseObject getResponseObject;




    public Notifications() {



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

                MarchandActivity.getMarchand().getId()

        );

    }




    private void findView(View view) {

        recyclerView = view.findViewById(R.id.recycler);

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

                            NotificationAdapter notificationAdapter = new NotificationAdapter(getContext(), notifications);
                            recyclerView.setAdapter(notificationAdapter);

                            //markReadNotifications(id);


                        } else {

                            System.out.println(response.code());


                            try {

                                JSONObject jObjError = new JSONObject(response.errorBody().string());

                                Type listType = new TypeToken<List<ValidationEror>>() {}.getType();

                               System.out.println(
                                        jObjError.getJSONObject("success").getString("message")
                                );


                                try {

                                    showDialog.getAlertDialog().dismiss();

                                } catch (Exception ignored) {}

                            } catch (Exception e) {
                                e.printStackTrace();

                                String string = new CatchApiError(response.code()).catchError(getContext());

                            

                        }

                        }

                    }



                    @Override
                    public void onFailure(Call<JsonArray> call, Throwable t) {


                        try {

                            showDialog.showNoInternetDialog()
                                    .setPositiveListener(new ClickListener() {
                                        @Override
                                        public void onClick(@NotNull LottieAlertDialog lottieAlertDialog) {
                                            lottieAlertDialog.dismiss();
                                            unReadNotifications(id);
                                        }
                                    })

                                    .setNegativeListener(new ClickListener() {
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



}
