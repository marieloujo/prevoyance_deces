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
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
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
import bj.assurance.assurancedeces.model.SuperMarchand;
import bj.assurance.assurancedeces.model.Userable;
import bj.assurance.assurancedeces.model.Utilisateur;
import bj.assurance.assurancedeces.model.customModel.AccessToken;
import bj.assurance.assurancedeces.model.customModel.ValidationEror;
import bj.assurance.assurancedeces.retrofit.RetrofitBuildForGetRessource;
import bj.assurance.assurancedeces.retrofit.RetrofitClientInstance;
import bj.assurance.assurancedeces.service.UtilisateurService;
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




    public Call<JsonObject> findUserbyName(String nom, AccessToken accessToken) {

        utilisateurService = new RetrofitBuildForGetRessource(accessToken).getRetrofit()
                .create(UtilisateurService.class);

        return utilisateurService.findUserbyName(nom);

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







    public Call<JsonObject> findCompte(Userable userable, AccessToken accessToken) {

        utilisateurService = new RetrofitBuildForGetRessource(accessToken).getRetrofit()
                .create(UtilisateurService.class);


        return utilisateurService.findCompte(userable);


    }





    public void login(final String username, final String password) {

        showDialog.showLoaddingDialog("Authentification", "Veuillez patienter");

        UtilisateurService utilisateurService1 = RetrofitClientInstance.createService(UtilisateurService.class);
        Call<JsonObject> call = utilisateurService1.login(username, password);

        call.enqueue(new Callback<JsonObject>() {


            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                loginResponsIsSuccesfull(response);

            }



            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

                try {
                    showDialog.getAlertDialog().dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {

                    if (new onConnexionListener(context).checkConnexion()) {


                        showDialog.showWarningDialog("Echec de connexion",
                                "Imposible d'établir une connexion avec le seuveur. Veuillez réessayer ultérieurement");


                    } else {

                        showDialog.showNoInternetDialog().setPositiveListener(new ClickListener() {
                            @Override
                            public void onClick(@NotNull LottieAlertDialog lottieAlertDialog) {
                                lottieAlertDialog.dismiss();
                                login(username,password);
                            }
                        })
                                .build()
                                .show();

                    }

                } catch (Exception e) {

                    e.printStackTrace();

                }
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

                    ((FragmentActivity)context).finish();

                } else {

                    new CatchApiError(response.code()).catchError(context);

                    try {

                        showDialog.getAlertDialog().dismiss();

                    } catch (Exception e ) {}


                    try {

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

                    } catch (Exception e) {

                        e.printStackTrace();

                    }


                }


            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {


                try {

                    showDialog.getAlertDialog().dismiss();

                } catch (Exception ignored) {}


                try {

                    if (new onConnexionListener(context).checkConnexion()) {


                        showDialog.showWarningDialog("Echec de connexion",
                                "Imposible d'établir une connexion avec le seuveur. Veuillez réessayer ultérieurement");


                    } else {

                        showDialog.showNoInternetDialog().setPositiveListener(new ClickListener() {
                            @Override
                            public void onClick(@NotNull LottieAlertDialog lottieAlertDialog) {
                                lottieAlertDialog.dismiss();
                                logout(accessToken);
                            }
                        })
                                .build()
                                .show();

                    }

                } catch (Exception e) {

                    e.printStackTrace();

                }


            }
        });

    }





    public Call<AccessToken> refresh(String s) {
        return null;
    }




    public Call<JsonObject> createPospect(Utilisateur utilisateur) {

        new RetrofitBuildForGetRessource(
                TokenManager.getInstance(
                        ((FragmentActivity) context).getSharedPreferences("prefs", MODE_PRIVATE)).
                        getToken()
        );
        utilisateurService = RetrofitBuildForGetRessource.getRetrofit()
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








    public Call<JsonObject> createNotification(Message message, Long id, AccessToken accessToken) {


        utilisateurService = new  RetrofitBuildForGetRessource(accessToken).getRetrofit()
                .create(UtilisateurService.class);


        return utilisateurService.createNotification(message, id);

    }















    private void loginResponsIsSuccesfull(Response<JsonObject> response) {


        if (response.isSuccessful()) {

           try {

               AccessToken accessToken = new Gson().fromJson(response.body(), AccessToken.class);
               tokenManager.saveToken(accessToken);

               getAuth();

           } catch (Exception e) {

               e.printStackTrace();

           }

        } else {

            try {

                showDialog.getAlertDialog().dismiss();

            } catch (Exception e) {
                e.printStackTrace();
            }

            new CatchApiError(response.code()).catchError(context);


            try {

                String jObjError = new JSONObject(
                        response.errorBody().string()).getJSONObject("errors").getString("message");


                ValidationEror validationEror = new Gson().fromJson(
                        jObjError, ValidationEror.class);


                System.out.println("errors " + validationEror + " \n " + jObjError);


                try {

                    Connexion.getErrorTelephone().setText(validationEror.getErrorLogin().get(0));

                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    Connexion.getErrorPassword().setText(validationEror.getErrorPassword().get(0));
                } catch (Exception e) {
                    e.printStackTrace();
                }


                try {

                    showDialog.getAlertDialog().dismiss();

                } catch (Exception ignored) {}

            } catch (Exception e) {

                e.printStackTrace();

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

                    try {

                        showDialog.getAlertDialog().dismiss();

                    } catch (Exception e) {

                        e.printStackTrace();

                    }

                    try {

                        Type listType = new TypeToken<List<Userable>>() {}.getType();
                        List<Userable> userable = new Gson().fromJson(response.body().getAsJsonArray("data"), listType);

                        loginRedirect(userable.get(0));

                    } catch (Exception e) {

                        e.printStackTrace();

                    }


                } else {

                    new CatchApiError(response.code()).catchError(context);

                    if (response.code() != 401) {

                        JSONObject jObjError = null;

                        try {

                            jObjError = new JSONObject(response.errorBody().string());

                        } catch (JSONException e) {

                            e.printStackTrace();

                        } catch (IOException e) {

                            e.printStackTrace();

                        }

                        String string = null;
                        try {
                            string = jObjError.getJSONObject("errors").getString("message");
                            System.out.println(string);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        try {

                            showDialog.getAlertDialog().changeDialog(new LottieAlertDialog.Builder(context,
                                    DialogTypes.TYPE_ERROR)
                                    .setTitle("Error")
                                    .setDescription(string)
                            );

                        } catch (Exception e) {

                            e.printStackTrace();

                        }

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


                try {

                    System.out.println("onFailure");

                    if (new onConnexionListener(context).checkConnexion()) {


                        LottieAlertDialog lottieAlertDialog = showDialog.showErrorDialog("Echec de connexion",
                                "Imposible d'établir une connexion avec le seuveur. Veuillez réessayer ultérieurement",
                                "Reéssayer")
                                .setPositiveListener(new ClickListener() {
                                    @Override
                                    public void onClick(@NotNull LottieAlertDialog lottieAlertDialog) {
                                        getAuth();
                                        lottieAlertDialog.dismiss();
                                    }
                                }).build();

                        lottieAlertDialog.setCancelable(false);
                        lottieAlertDialog.show();


                    } else {

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

                } catch (Exception e) {

                    e.printStackTrace();

                }

            }


        });
    }





    private void loginRedirect(Userable userable) {

        Intent intent = new Intent();

        Object object = getResponseObject.checkAuthype(userable);

        if (object.getClass().equals(Marchand.class)) {

            intent.setClass(context, MarchandActivity.class);
            intent.putExtra("marchand", new Gson().toJson(userable));


            context.startActivity(intent);
            ((FragmentActivity)context).finish();


        } else {


            if (object.getClass().equals(SuperMarchand.class)) {


                intent.setClass(context, SuperMarchandActivity.class);
                intent.putExtra("superMarchand", new Gson().toJson(userable));


                context.startActivity(intent);
                ((FragmentActivity)context).finish();



            } else if (object.getClass().equals(Client.class)) {

                if (userable.isActif()) {

                    intent.setClass(context, ClientActivity.class);
                    intent.putExtra("client", new Gson().toJson(userable));


                    context.startActivity(intent);
                    ((FragmentActivity)context).finish();

                    return;

                } else {


                    try {

                        showDialog.showWarningDialog("Impossible de se connecter",
                                "Votre compte n'est pas encore actif");

                    } catch (Exception e) {

                        e.printStackTrace();

                    }


                }




            }


        }
    }






}
