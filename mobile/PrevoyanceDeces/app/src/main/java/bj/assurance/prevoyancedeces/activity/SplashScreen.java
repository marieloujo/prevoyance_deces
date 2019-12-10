package bj.assurance.prevoyancedeces.activity;

import androidx.appcompat.app.AppCompatActivity;
import bj.assurance.prevoyancedeces.R;
import bj.assurance.prevoyancedeces.utils.AccessToken;
import bj.assurance.prevoyancedeces.utils.ApiError;
import bj.assurance.prevoyancedeces.utils.Utils;
import bj.assurance.prevoyancedeces.model.Utilisateur;
import bj.assurance.prevoyancedeces.retrofit.RetrofitBuildForGetRessource;
import bj.assurance.prevoyancedeces.retrofit.Service.UserService;
import bj.assurance.prevoyancedeces.retrofit.TokenManager;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.kinda.alert.KAlertDialog;

import java.util.List;
import java.util.Map;

public class SplashScreen extends AppCompatActivity {

    Handler handler;
    private static final String TAG = "SplashScreen";

    Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        //TokenManager.getInstance(getSharedPreferences("prefs", MODE_PRIVATE)).deleteToken();

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent i = new Intent();

                /*i.setClass(SplashScreen.this, MarchandMainActivity.class);
                startActivity(i);
                finish();*/

                if (!(TokenManager.getInstance(getSharedPreferences("prefs", MODE_PRIVATE)).getToken().getAccessToken() == null)) {

                    getAuthenticateUser(TokenManager.getInstance(getSharedPreferences("prefs", MODE_PRIVATE)).getToken());
                    return;
                } else {
                    i.setClass(SplashScreen.this,Welcome.class);
                    startActivity(i);
                    finish();
                }
            }
        } ,1000);

    }

    public void getAuthenticateUser(AccessToken accessToken) {

        Call<JsonObject> call;
        UserService service = new RetrofitBuildForGetRessource(accessToken).getRetrofit().create(UserService.class);
        call = service.getauthenticateUser();
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                Log.w(TAG, "onResponse: " + response.body());

                if (response.isSuccessful()) {
                    getResponseUser(response.body());
                    //checkUserAuthentificate(response.body());
                    finish();
                } else {
                    catchApiErorUser(response);
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.w(TAG, "onFailure: " + t.getMessage());
                System.out.println(t.getMessage());

                showDialog("Erreur de serveur", "Une erreur s'est produite, veuillez réessayer");

            }
        });
    }

    public void getResponseUser(JsonObject jsonObject) {
        JsonObject error = null, sucess = null;
        String messageError = null, message = null;
        Utilisateur utilisateur = null;

        try {
            error = jsonObject.getAsJsonObject("errors");
            messageError = error.get("message").getAsString();
        }catch (Exception ignored){}

        try {
            sucess = jsonObject.getAsJsonObject("success");
        }catch (Exception ignored) {}

        try {
            message = sucess.get("message").getAsString();
        } catch (Exception ignored) {}

        try {
            utilisateur = new Gson().fromJson(sucess.getAsJsonObject("data"), Utilisateur.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("error: " + error + ",\n" +
                "error_message: " + messageError + "\n" +
                "sucess: " + sucess + "\n" +
                "message_sucess " + message + "\n" +
                "data: " + utilisateur);

        try {
            checkUserAuthentificate(utilisateur);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @SuppressLint("SetTextI18n")
    public void catchApiErorUser(Response<JsonObject> response) {
        switch (response.code()) {
            case 400:  // Bad Request La syntaxe de la requête est erronée.
                showDialog("Récupération des données échoués", "Veuillez réessayer");
                Log.w(TAG, "400: " + response.errorBody().source());
                handleErrors(response.errorBody());

                break;

            case 401:  // Unauthorized  Une authentification est nécessaire pour accéder à la ressource.
                Intent i = new Intent(SplashScreen.this,Welcome.class);
                startActivity(i);
                finish();
                break;

            case 403:  // Forbidden
                showDialog("Interdit", "Le serveur ne peut exécuter votre requête");
                Log.w(TAG, "403: " + response.errorBody().source());
                handleErrors(response.errorBody());
                break;

            case 404: // Not Found Ressource non trouvée.
                showDialog("", "La ressource demandé est indisponible");
                Log.w(TAG, "404: " + response.errorBody().source());
                handleErrors(response.errorBody());
                break;

            case 405: //  Method Not Allowed Méthode de requête non autorisée.
                showDialog("Erreur", "Une erreur s'est produite, veuillez réessayer");
                Log.w(TAG, "405: " + response.errorBody().source());
                handleErrors(response.errorBody());
                break;

            case 406:  //  Not Acceptable La ressource demandée n'est pas disponible dans un format qui respecterait les en-têtes « Accept » de la requête.
                Log.w(TAG, "406: " + response.errorBody().source());
                handleErrors(response.errorBody());
                break;

            case 408:   //   Request Time-out Temps d’attente d’une requête du client, écoulé côté serveur. D'après les spécifications HTTP : « Le client n'a pas produit de requête dans le délai que le serveur était prêt à attendre. Le client PEUT répéter la demande sans modifications à tout moment ultérieur »8.
                showDialog("Temps écoulé", "La récupération des données à mis trop de temps");
                Log.w(TAG, "408: " + response.errorBody().source());
                handleErrors(response.errorBody());
                break;

            case 424:  //  Method failure Une méthode de la transaction a échoué.
                showDialog("Erreur de serveur", "Une erreur s'est produite, veuillez réessayer");
                Log.w(TAG, "424 " + response.errorBody().source());
                handleErrors(response.errorBody());
                break;

            case 429: //  Too Many Requests Le client a émis trop de requêtes dans un délai donné.
                showDialog("Trp de requête", "Vous avez lançer trop de requêtes");
                Log.w(TAG, "429: " + response.errorBody().source());
                handleErrors(response.errorBody());
                break;

            case 444:  // No Response Indique que le serveur n'a retourné aucune information vers le client et a fermé la connexion.
                showDialog("Une de serveur", "Une erreur s'est produite, veuillez réessayer");
                Log.w(TAG, "444: " + response.errorBody().source());
                handleErrors(response.errorBody());
                break;

            case 500:  // Internal Server Error Erreur interne du serveur.
                showDialog("Une de serveur", "Une erreur s'est produite, veuillez réessayer");
                Log.w(TAG, "500: " + response.errorBody().source());
                handleErrors(response.errorBody());
                break;


            case 498:  // Client Closed Request	Le client a fermé la connexion avant de recevoir la réponse. Cette erreur se produit quand le traitement est trop long côté serveur16.
                Log.w(TAG, "422: " + response.errorBody().source());
                handleErrors(response.errorBody());
                break;
        }
    }

    private void handleErrors(ResponseBody response) {

        ApiError apiError = Utils.converErrors(response);
    }

    public void checkUserAuthentificate(Utilisateur utilisateur) {

        String usertypeObject = gson.toJson(utilisateur);
        Intent intent = new Intent();

        if (utilisateur.getUsereableType().equals("App\\Models\\Marchand")) {

            intent.setClass(SplashScreen.this, MarchandMainActivity.class);
            intent.putExtra("marchand", usertypeObject);
            startActivity(intent);

        } else if (utilisateur.getUsereableType().equals("App\\Models\\Client")) {

            intent.setClass(SplashScreen.this, Main2Activity.class);
            intent.putExtra("client", usertypeObject);
            startActivity(intent);

        } else if (utilisateur.getUsereableType().equals("App\\Models\\SuperMarchand")) {
            intent.setClass(SplashScreen.this, SuperMarchandMainActivity.class);
            intent.putExtra("supermarchand", usertypeObject);
            startActivity(intent);
        } else return;
    }

    public void showDialog(String title, String message) {
        new KAlertDialog(SplashScreen.this, KAlertDialog.WARNING_TYPE)
                .setTitleText(title)
                .setContentText(message)
                .setConfirmText("Réessayer")
                .showCancelButton(true)
                .setCancelClickListener(new KAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(KAlertDialog sDialog) {
                        sDialog.cancel();
                    }
                })
                .setConfirmClickListener(new KAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(KAlertDialog sDialog) {
                        sDialog.dismiss();
                        getAuthenticateUser(TokenManager.getInstance(getSharedPreferences("prefs", MODE_PRIVATE)).getToken());

                    }
                })
                .show();
    }
}
