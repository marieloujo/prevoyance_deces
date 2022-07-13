package bj.assurance.assurancedeces.serviceImplementation;

import android.content.Context;
import android.content.Intent;

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
import bj.assurance.assurancedeces.activity.ClientActivity;
import bj.assurance.assurancedeces.activity.Connexion;
import bj.assurance.assurancedeces.activity.MarchandActivity;
import bj.assurance.assurancedeces.activity.SuperMarchandActivity;
import bj.assurance.assurancedeces.model.Client;
import bj.assurance.assurancedeces.model.Marchand;
import bj.assurance.assurancedeces.model.Message;
import bj.assurance.assurancedeces.model.Notification;
import bj.assurance.assurancedeces.model.SuperMarchand;
import bj.assurance.assurancedeces.model.Userable;
import bj.assurance.assurancedeces.model.Utilisateur;
import bj.assurance.assurancedeces.model.customModel.ValidationEror;
import bj.assurance.assurancedeces.retrofit.RetrofitBuildForGetRessource;
import bj.assurance.assurancedeces.retrofit.RetrofitClientInstance;
import bj.assurance.assurancedeces.service.UtilisateurService;
import bj.assurance.assurancedeces.utils.CatchApiError;
import bj.assurance.assurancedeces.utils.CustomJsonObject;
import bj.assurance.assurancedeces.utils.GetResponseObject;
import bj.assurance.assurancedeces.utils.ShowDialog;
import bj.assurance.assurancedeces.model.customModel.AccessToken;
import bj.assurance.assurancedeces.utils.token.TokenManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

import static android.content.Context.MODE_PRIVATE;


public class UtilisateurServiceImplementation{



    private static final String TAG = "Connexion";

    private Call<JsonObject> call;
    private UtilisateurService utilisateurService;
    private CustomJsonObject customJsonObject = null;
    private TokenManager tokenManager;
    private Context context;
    private GetResponseObject getResponseObject = new GetResponseObject();
    private ShowDialog showDialog;





    public UtilisateurServiceImplementation(Context context) {

        this.context = context;

        tokenManager = TokenManager.getInstance(context.getSharedPreferences("prefs", MODE_PRIVATE));

        showDialog = new ShowDialog(context);

    }




    public Call<JsonObject> listUser() {

        call = utilisateurService.listUser();
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });

        return null;
    }




    public Call<JsonObject> getAuthentificateUser(AccessToken accessToken) {

        utilisateurService = new RetrofitBuildForGetRessource(accessToken).getRetrofit()
                .create(UtilisateurService.class);

        call = utilisateurService.getAuthentificateUser();


        return call;
    }




    public Call<JsonObject> findUserbyPhoneNumber(String phoneNumber, AccessToken accessToken) {

        utilisateurService = new RetrofitBuildForGetRessource(accessToken).getRetrofit()
                .create(UtilisateurService.class);


        return utilisateurService.findUserbyPhoneNumber(phoneNumber);

    }




    public Call<JsonObject> findUserbyName(String nom) {


        call = utilisateurService.findUserbyName(nom);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });


        return null;
    }




    public Call<JsonObject> listConversationsOfUser(Long id) {


        call = utilisateurService.listConversationsOfUser(id);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });


        return null;
    }




    public Call<JsonArray> listNotificationsOfUser(Long id, AccessToken accessToken) {

        utilisateurService = new RetrofitBuildForGetRessource(accessToken).getRetrofit()
                .create(UtilisateurService.class);

        return utilisateurService.getNotifications(id);

    }




    public void login(final String username, final String password) {

        showDialog.showLoaddingDialog("Authentification", "Veuillez patienter");

        UtilisateurService utilisateurService1 = RetrofitClientInstance.createService(UtilisateurService.class);
        Call<JsonObject> call = utilisateurService1.login(username, password);

        call.enqueue(new Callback<JsonObject>() {


            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                System.out.println(response.body() + " " + response.code());
                loginResponsIsSuccesfull(response);

            }



            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

                showDialog.getAlertDialog().dismiss();

                LottieAlertDialog lottieAlertDialog =
                        showDialog.showNoInternetDialog().setPositiveListener(new ClickListener() {
                            @Override
                            public void onClick(@NotNull LottieAlertDialog lottieAlertDialog) {
                                lottieAlertDialog.dismiss();
                                login(username, password);
                            }
                        })
                        .build();

                lottieAlertDialog.setCancelable(false);
                lottieAlertDialog.show();


                t.printStackTrace();
            }


        });
    }






    public void logout(final AccessToken accessToken) {


        showDialog.showLoaddingDialog("Déconnexion", "Veuillez patienter");


        utilisateurService = new RetrofitBuildForGetRessource(accessToken).getRetrofit()
                .create(UtilisateurService.class);

        call = utilisateurService.logout();
        call.enqueue(new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                if (response.isSuccessful()) {

                    showDialog.getAlertDialog().dismiss();
                    tokenManager.deleteToken();

                    Intent intent = new Intent(context, Connexion.class);
                    context.startActivity(intent);

                } else {

                    new CatchApiError(response.code()).catchError(context);

                    showDialog.getAlertDialog().dismiss();

                    showDialog.showErrorDialog("Oups...",
                            "Une erreur est intervenue lors de la déconnexion," +
                            " veuillez réessayer", "Reesayer"
                    )

                    .setPositiveListener(new ClickListener() {
                        @Override
                        public void onClick(@NotNull LottieAlertDialog lottieAlertDialog) {
                            lottieAlertDialog.dismiss();
                            logout(accessToken);
                        }
                    })
                    .build()
                    .show();


                }


            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {



            }
        });

    }





    public Call<AccessToken> refresh(String s) {
        return null;
    }




    public Call<JsonObject> createPospect(Utilisateur utilisateur) {

        utilisateurService = new RetrofitBuildForGetRessource(
                TokenManager.getInstance(
                        ((FragmentActivity)context).getSharedPreferences("prefs", MODE_PRIVATE)).
                        getToken()
        ).getRetrofit()
                .create(UtilisateurService.class);

        return  utilisateurService.createPospect(utilisateur);

    }





   public Call<JsonArray> unReadNotifications(Long id, AccessToken accessToken) {

       utilisateurService = new RetrofitBuildForGetRessource(accessToken).getRetrofit()
               .create(UtilisateurService.class);

        return utilisateurService.unReadNotifications(id);

   }





   public Call<JsonObject> markReadNotifications(Long id, AccessToken accessToken) {

        utilisateurService = new RetrofitBuildForGetRessource(accessToken).getRetrofit().create(UtilisateurService.class);


        return utilisateurService.markReadNotifications(id);

   }



    /*@GET("users/{idUser}/markReadNotifications")
    Call<JsonObject> markReadNotifications(@Path("idUser") Long id);



    @GET("users/{idUser}/unReadNotifications")
    Call<JsonObject> unReadNotifications(@Path("idUser") Long id);




    @GET("users/{idUser}/getNotifications")
    Call<JsonObject> getNotifications(@Path("idUser") Long id);*/




    /*@POST("users/{idUser}/notifications")
    Call<JsonObject> createNotification(@Path("idUser") Long id);*/


    public Call<JsonObject> createNotification(Message message, AccessToken accessToken) {


        utilisateurService = new  RetrofitBuildForGetRessource(accessToken).getRetrofit()
                .create(UtilisateurService.class);


        return utilisateurService.createNotification(message);

    }















    private void loginResponsIsSuccesfull(Response<JsonObject> response) {


        if (response.isSuccessful()) {

            getResponseObject.setJsonObject(response.body());
            CustomJsonObject customJsonObject = getResponseObject.getResponse();

            if (customJsonObject.getType().equals("success_data")) {

                AccessToken accessToken = new Gson().fromJson(customJsonObject.getJsonObject(), AccessToken.class);
                tokenManager.saveToken(accessToken);

                System.out.println(accessToken.getAccessToken());

                getAuth();

            }

        } else {

            showDialog.getAlertDialog().dismiss();

            new CatchApiError(response.code()).catchError(context);


            try {

                JSONObject jObjError = new JSONObject(response.errorBody().string());


                String string = jObjError.getJSONObject("errors").getString("message");



                if (string.equals("Nom d'utilisateur incorrect")) {

                    Connexion.getErrorTelephone().setText(string);

                } else if (string.equals("Mot de passe incorrect")) {

                    Connexion.getErrorPassword().setText(string);

                }

                try {

                    showDialog.getAlertDialog().dismiss();

                } catch (Exception ignored) {}

            } catch (Exception e) {

            }

        }


    }



    public void getAuth() {

        getAuthentificateUser(
                tokenManager.getToken()
        ).enqueue(new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                if (response.isSuccessful()) {

                    getResponseObject.setJsonObject(response.body());
                    customJsonObject = getResponseObject.getResponse();

                    if (customJsonObject.getType().equals("success_data")) {

                        Userable userable = new Gson().fromJson(customJsonObject
                                .getJsonObject().getAsJsonObject("data"), Userable.class);

                        System.out.println(userable);

                        try {

                            showDialog.getAlertDialog().dismiss();

                        } catch (Exception e) {

                            e.printStackTrace();

                        }

                        loginRedirect(userable);
                    }

                } else {

                    String string = new CatchApiError(response.code()).catchError(context);

                    try {
                        showDialog.getAlertDialog().changeDialog(new LottieAlertDialog.Builder(context, DialogTypes.TYPE_ERROR)
                                .setTitle("Error")
                                .setDescription(string)
                        );
                    } catch (Exception e) {

                        e.printStackTrace();

                    }
                }


            }



            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

                try {

                    showDialog.getAlertDialog().dismiss();

                } catch (Exception e) {
                    e.printStackTrace();
                }

                LottieAlertDialog lottieAlertDialog =
                        showDialog.showNoInternetDialog().setPositiveListener(new ClickListener() {
                    @Override
                    public void onClick(@NotNull LottieAlertDialog lottieAlertDialog) {
                        lottieAlertDialog.dismiss();
                        getAuth();
                    }
                })
                .build();

                lottieAlertDialog.setCancelable(false);
                lottieAlertDialog.show();

            }


        });
    }



    private void loginRedirect(Userable userable) {

        Intent intent = new Intent();

        Object object = getResponseObject.checkAuthype(userable);

        if (object.getClass().equals(Marchand.class)) {

            intent.setClass(context, MarchandActivity.class);
            intent.putExtra("marchand", new Gson().toJson(object));


            context.startActivity(intent);
            ((FragmentActivity)context).finish();


        } else {


            if (object.getClass().equals(SuperMarchand.class)) {


                intent.setClass(context, SuperMarchandActivity.class);
                intent.putExtra("superMarchand", new Gson().toJson(object));


                context.startActivity(intent);
                ((FragmentActivity)context).finish();



            } else if (object.getClass().equals(Client.class)) {


                intent.setClass(context, ClientActivity.class);
                intent.putExtra("client", new Gson().toJson(object));


                context.startActivity(intent);
                ((FragmentActivity)context).finish();



            }


        }
    }






}
