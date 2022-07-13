package bj.assurance.assurancedeces.utils;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.FragmentActivity;
import bj.assurance.assurancedeces.R;
import bj.assurance.assurancedeces.activity.ClientActivity;
import bj.assurance.assurancedeces.activity.MarchandActivity;
import bj.assurance.assurancedeces.activity.SuperMarchandActivity;
import bj.assurance.assurancedeces.model.Message;
import bj.assurance.assurancedeces.model.Notification;
import bj.assurance.assurancedeces.serviceImplementation.UtilisateurServiceImplementation;
import bj.assurance.assurancedeces.utils.token.TokenManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class NotificationReader {




    private Context context;






    public NotificationReader(Context context) {
        this.context = context;
    }






    @RequiresApi(api = Build.VERSION_CODES.O)
    public void addNotification(String desciption) {


        notificationDialog(desciption);

    }







    @RequiresApi(api = Build.VERSION_CODES.O)
    private void notificationDialog(String description) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFICATION_CHANNEL_ID = "AssuranceDeces";


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            @SuppressLint("WrongConstant") NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "My Notifications", NotificationManager.IMPORTANCE_MAX);
            // Configure the notification channel.
            notificationChannel.setDescription(description);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationChannel.enableVibration(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }


        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID);
        notificationBuilder.setAutoCancel(true)
                .setDefaults(android.app.Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.logo_transparent)
                .setTicker("AssuranceDeces")
                .setPriority(android.app.Notification.PRIORITY_MAX)
                //.setContentTitle(title)
                .setContentText(description)
                .setContentInfo("Information");
        notificationManager.notify(1, notificationBuilder.build());
    }








    public void unReadNotifications(Long id) {


        new UtilisateurServiceImplementation(context)

                .unReadNotifications(id,

                        TokenManager.getInstance(
                                ((FragmentActivity) context).getSharedPreferences("prefs", MODE_PRIVATE)).
                                getToken()

                )

                .enqueue(new Callback<JsonArray>() {


                    @Override
                    public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {

                        if (response.isSuccessful()) {

                            try {

                                Type listType = new TypeToken<List<Notification>>() {}.getType();

                                List<Notification> notifications = new Gson().fromJson(response.body(), listType);



                                for (int i = 0; i < notifications.size(); i++) {

                                    addNotification(notifications.get(i).getData());

                                }



                                try {
                                    MarchandActivity.getIsRead().setVisibility(View.VISIBLE);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }


                                try {
                                    ClientActivity.getIsRead().setVisibility(View.VISIBLE);
                                } catch (Exception e){
                                    e.printStackTrace();
                                }


                                try {
                                    SuperMarchandActivity.getIsRead().setVisibility(View.VISIBLE);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }


                                System.out.println("notifications "+notifications);

                            } catch (Exception e) {

                                e.printStackTrace();

                            }


                        } else {

                            System.out.println("code" + response.code());

                            if (response.code() == 404) {

                                //isRead.setVisibility(View.INVISIBLE);

                            } else {


                                try {

                                    JSONObject jObjError = new JSONObject(response.errorBody().string());


                                    try {

                                        //showDialog.getAlertDialog().dismiss();

                                    } catch (Exception ignored) {
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();

                                    String string = new CatchApiError(response.code()).catchError(context);


                                    System.out.println("stringno" + string);

                                }
                            }

                        }

                    }



                    @Override
                    public void onFailure(Call<JsonArray> call, Throwable t) {




                    }


                });


    }






    public void createNotification(Message message, Long id) {


        new UtilisateurServiceImplementation(context).createNotification(

                message, id,

                TokenManager.getInstance(
                        ((FragmentActivity)context).getSharedPreferences("prefs", MODE_PRIVATE)).
                        getToken()

        ).enqueue(new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                System.out.println(response.body() + " " + response.code());

                if (response.isSuccessful()) {

                    unReadNotifications(MarchandActivity.getUtilisateur().getId());

                }


            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

                System.out.println(t.getMessage());

            }


        });



    }





}
