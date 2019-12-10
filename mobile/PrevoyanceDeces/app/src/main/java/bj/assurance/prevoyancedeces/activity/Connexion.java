package bj.assurance.prevoyancedeces.activity;

import androidx.appcompat.app.AppCompatActivity;
import bj.assurance.prevoyancedeces.R;
import bj.assurance.prevoyancedeces.model.pagination.OutputPaginate;
import bj.assurance.prevoyancedeces.utils.AccessToken;
import bj.assurance.prevoyancedeces.utils.ApiError;
import bj.assurance.prevoyancedeces.utils.Utils;
import bj.assurance.prevoyancedeces.model.Utilisateur;
import bj.assurance.prevoyancedeces.retrofit.RetrofitBuildForGetRessource;
import bj.assurance.prevoyancedeces.retrofit.RetrofitClientInstance;
import bj.assurance.prevoyancedeces.retrofit.Service.UserService;
import bj.assurance.prevoyancedeces.retrofit.TokenManager;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.kinda.alert.KAlertDialog;

import java.util.List;
import java.util.Map;

public class Connexion extends AppCompatActivity {

    private static final String TAG = "Connexion";

    EditText telephone;
    EditText password;
    static String users;
    TextView errorPassword, errorTelephone, error;

    UserService service;
    TokenManager tokenManager;

    KAlertDialog pDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);

        init();

    }

    public void init() {
        telephone = findViewById(R.id.etTelephone);
        password = findViewById(R.id.etPassword);

        errorPassword = findViewById(R.id.errorPassword);
        errorTelephone = findViewById(R.id.errorUsername);
        error = findViewById(R.id.error);

        tokenManager = TokenManager.getInstance(getSharedPreferences("prefs", MODE_PRIVATE));

        users = "";

        pDialog = new KAlertDialog(Connexion.this, KAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#2d8df8"));
        pDialog.setTitleText("Authentification");
        pDialog.setCancelable(false);
    }

    public void valider(View view) {
        login();
    }

    public void login() {
        if(dataisCorrect()) {

            pDialog.show();

            Call<JsonObject> call;
            service = RetrofitClientInstance.getRetrofit().create(UserService.class);
            call = service.login(telephone.getText().toString(), password.getText().toString());
            call.enqueue(new Callback<JsonObject>() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    error.setText("");
                    pDialog.dismiss();
                    Log.w(TAG, "onResponse: " + response.body());

                    if (response.isSuccessful()) {
                        pDialog.dismiss();
                        getResponseError(response.body());
                    } else {
                        catchApiEror(response);

                        Log.w(TAG, "onResponse: " + response.code());
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    Log.w(TAG, "onFailure: " + t.getMessage());

                    pDialog.dismiss();

                    new KAlertDialog(Connexion.this, KAlertDialog.WARNING_TYPE)
                            .setTitleText("Erreur interne") // Oups!!! quelque chose s'est mal passé vérifier votre connexion internet et réessayer
                            .setContentText(t.getMessage())
                            .showCancelButton(true)
                            .setCancelClickListener(new KAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(KAlertDialog sDialog) {
                                    sDialog.cancel();
                                }
                            })
                            .show();
                }
            });
        }
    }

    public void getAuthenticateUser(AccessToken accessToken) {
        Call<JsonObject> call;
        service = new RetrofitBuildForGetRessource(accessToken).getRetrofit().create(UserService.class);
        call = service.getauthenticateUser();
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                Log.w(TAG, "onResponse: " + response.body());

                if (response.isSuccessful()) {
                    System.out.println("response: " + response.body());
                    pDialog.dismiss();
                    getResponseUser(response.body());
                } else {
                    pDialog.dismiss();
                    System.out.println("response: " + response.body());
                    //catchApiErorUser(response);
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                t.printStackTrace();
                pDialog.dismiss();
                new KAlertDialog(Connexion.this, KAlertDialog.WARNING_TYPE)
                    .setTitleText("Erreur interne") // Oups!!! quelque chose s'est mal passé vérifier votre connexion internet et réessayer
                    .setContentText(t.getMessage())
                    .showCancelButton(true)
                    .setCancelClickListener(new KAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(KAlertDialog sDialog) {
                            sDialog.cancel();
                        }
                    })
                    .show();

            }
        });
    }

    public void getResponseUser(JsonObject jsonObject) {
        JsonObject error = null, sucess = null, data = null;
        String messageError = null, message = null;
        Utilisateur utilisateur = null;

        try {
            error = jsonObject.getAsJsonObject("errors");
            messageError = error.get("message").getAsString();
        }catch (Exception ignored){}

        try {
            sucess = jsonObject.getAsJsonObject("success");
        }catch (Exception e) {}

        try {
            message = sucess.get("message").getAsString();
        } catch (Exception e) {}

        try {
            data = sucess.getAsJsonObject("data");
            utilisateur = new Gson().fromJson(sucess.getAsJsonObject("data"), Utilisateur.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("error: " + error + ",\n" +
                "error_message: " + messageError + "\n" +
                "sucess: " + sucess + "\n" +
                "message_sucess " + message + "\n" +
                "data: " + data + "\n" +
                "content " + utilisateur + "\n" );


        try {
            checkUserAuthentificate(utilisateur);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void handleErrors(ResponseBody response) {

        //ApiError apiError = Utils.converErrors(response);

    }

    public boolean dataisCorrect() {

        boolean bool = true;

        if (telephone.getText().toString().isEmpty()) {
            errorTelephone.setText(getString(R.string.alert_telephone_not_write));
            bool = false;
        } else errorTelephone.setText("");


        if (password.getText().toString().isEmpty()) {
            errorPassword.setText(getString(R.string.alert_password_not_write));
            bool = false;
        } else  errorPassword.setText("");

        return bool;
    }

    public void checkUserAuthentificate(Utilisateur utilisateur) {

        Gson gson = new Gson();

        String usertypeObject = gson.toJson(utilisateur);
        Intent intent = new Intent();

        if (utilisateur.getUsereableType().equals("App\\Models\\Marchand")) {

            intent.setClass(Connexion.this, MarchandMainActivity.class);
            intent.putExtra("marchand", usertypeObject);
            startActivity(intent);

        } else if (utilisateur.getUsereableType().equals("App\\Models\\Client")) {

            intent.setClass(Connexion.this, Main2Activity.class);
            intent.putExtra("client", usertypeObject);
            startActivity(intent);

        } else if (utilisateur.getUsereableType().equals("App\\Models\\SuperMarchand")) {
            intent.setClass(Connexion.this, SuperMarchandMainActivity.class);
            intent.putExtra("supermarchand", usertypeObject);
            startActivity(intent);
        } else return;
    }

    @SuppressLint("SetTextI18n")
    public void catchApiEror(Response<JsonObject> response) {

         switch (response.code()) {
            case 400:  // Bad Request La syntaxe de la requête est erronée.
                error.setText("Une erreur s'est produite lors de l'envoie des donnée veuillez réessayer ultérieurement");
                Log.w(TAG, "400: " + response.errorBody().source());
                handleErrors(response.errorBody());
                pDialog.dismiss();
                break;

            case 401:  // Unauthorized  Une authentification est nécessaire pour accéder à la ressource
                error.setText("Vous n'êtes pas autorisé à acceder à cette resource");
                Log.w(TAG, "401: " + response.errorBody().byteStream());
                handleErrors(response.errorBody());
                pDialog.dismiss();
                break;

            case 403:  // Forbidden
                Log.w(TAG, "403: " + response.errorBody().source());
                handleErrors(response.errorBody());
                pDialog.dismiss();
                break;

            case 404: // Not Found Ressource non trouvée.
                error.setText("Vous n'etes pas autorisé à acceder à cette ressource");
                Log.w(TAG, "422: " + response.errorBody().source());
                handleErrors(response.errorBody());
                pDialog.dismiss();
                break;

            case 405: //  Method Not Allowed Méthode de requête non autorisée.
                error.setText("Une erreur s'est produite lors de l'envoie des donnée veuillez réessayer ultérieurement");
                Log.w(TAG, "405: " + response.errorBody().source());
                handleErrors(response.errorBody());
                pDialog.dismiss();
                break;

            case 406:  //  Not Acceptable La ressource demandée n'est pas disponible dans un format qui respecterait les en-têtes « Accept » de la requête.
                error.setText("Une erreur s'est produite lors de l'envoie des donnée veuillez réessayer ultérieurement");
                Log.w(TAG, "406: " + response.errorBody().source());
                handleErrors(response.errorBody());
                pDialog.dismiss();
                break;

            case 408:   //   Request Time-out Temps d’attente d’une requête du client, écoulé côté serveur. D'après les spécifications HTTP : « Le client n'a pas produit de requête dans le délai que le serveur était prêt à attendre. Le client PEUT répéter la demande sans modifications à tout moment ultérieur »8.
                error.setText("L'envoie des données à pris trop de temps.Veuillez réessayer ultérieurement");
                Log.w(TAG, "408: " + response.errorBody().source());
                handleErrors(response.errorBody());
                pDialog.dismiss();
                break;

            case 424:  //  Method failure Une méthode de la transaction a échoué.
                error.setText("Une erreur s'est produite lors de l'envoie des données, veuillez réessayer ultérieurement");
                Log.w(TAG, "424: " + response.errorBody().source());
                handleErrors(response.errorBody());
                pDialog.dismiss();
                break;

            case 429: //  Too Many Requests Le client a émis trop de requêtes dans un délai donné.
                error.setText("Vous avez lançer trop de requêtes");
                Log.w(TAG, "429: " + response.errorBody().source());
                handleErrors(response.errorBody());
                pDialog.dismiss();
                break;

            case 444:  // No Response Indique que le serveur n'a retourné aucune information vers le client et a fermé la connexion.
                error.setText("Aucune reponse retourné");
                Log.w(TAG, "444: " + response.errorBody().source());
                handleErrors(response.errorBody());
                pDialog.dismiss();
                break;

            case 500:  // Internal Server Error Erreur interne du serveur.
                error.setText("Erreur interne au niveau du seveur, veuillez réessayer ultérieurement");
                Log.w(TAG, "500: " + response.errorBody().source());
                handleErrors(response.errorBody());
                pDialog.dismiss();
                break;

                /*
            case 498:  // Client Closed Request	Le client a fermé la connexion avant de recevoir la réponse. Cette erreur se produit quand le traitement est trop long côté serveur16.
                error.setText("Une erreur s'est produite lors de l'envoie des donnée veuillez réessayer ultérieurement");
                Log.w(TAG, "422: " + response.errorBody().source());
                handleErrors(response.errorBody());
                pDialog.dismiss();
                break;*/
        }
    }

    @SuppressLint("SetTextI18n")
    public void catchApiErorUser(Response<Utilisateur> response) {
        switch (response.code()) {
            case 400:  // Bad Request La syntaxe de la requête est erronée.
                error.setText("Une erreur s'est produite lors de l'envoie des donnée veuillez réessayer ultérieurement");
                Log.w(TAG, "400: " + response.errorBody().source());
                handleErrors(response.errorBody());
                pDialog.dismiss();
                break;

            case 401:  // Unauthorized  Une authentification est nécessaire pour accéder à la ressource.
                error.setText("Vous n'etes pas autorisé à acceder à cette ressource");
                Log.w(TAG, "401: " + response.errorBody().source());
                handleErrors(response.errorBody());
                pDialog.dismiss();
                break;

            case 403:  // Forbidden
                Log.w(TAG, "403: " + response.errorBody().source());
                handleErrors(response.errorBody());
                pDialog.dismiss();
                break;

            case 404: // Not Found Ressource non trouvée.
                error.setText("La ressource demandé est indisponible");
                Log.w(TAG, "404: " + response.errorBody().source());
                handleErrors(response.errorBody());
                pDialog.dismiss();
                break;

            case 405: //  Method Not Allowed Méthode de requête non autorisée.
                error.setText("Une erreur s'est produite lors de l'envoie des donnée veuillez réessayer ultérieurement");
                Log.w(TAG, "405: " + response.errorBody().source());
                handleErrors(response.errorBody());
                pDialog.dismiss();
                break;

            case 406:  //  Not Acceptable La ressource demandée n'est pas disponible dans un format qui respecterait les en-têtes « Accept » de la requête.
                error.setText("Une erreur s'est produite lors de l'envoie des donnée veuillez réessayer ultérieurement");
                Log.w(TAG, "406: " + response.errorBody().source());
                handleErrors(response.errorBody());
                pDialog.dismiss();
                break;

            case 408:   //   Request Time-out Temps d’attente d’une requête du client, écoulé côté serveur. D'après les spécifications HTTP : « Le client n'a pas produit de requête dans le délai que le serveur était prêt à attendre. Le client PEUT répéter la demande sans modifications à tout moment ultérieur »8.
                error.setText("L'envoie des données à pris trop de temps.Veuillez réessayer ultérieurement");
                Log.w(TAG, "408: " + response.errorBody().source());
                handleErrors(response.errorBody());
                pDialog.dismiss();
                break;

            case 422:  // Unprocessable entity	WebDAV : L’entité fournie avec la requête est incompréhensible ou incomplète.
                error.setText("Nom d'utilisateur ou mot de passe incorrect");
                Log.w(TAG, "422: " + response.errorBody().source());
                handleErrors(response.errorBody());
                pDialog.dismiss();
                break;

            case 424:  //  Method failure Une méthode de la transaction a échoué.
                error.setText("Une erreur s'est produite lors de l'envoie des données, veuillez réessayer ultérieurement");
                Log.w(TAG, "424 " + response.errorBody().source());
                handleErrors(response.errorBody());
                pDialog.dismiss();
                break;

            case 429: //  Too Many Requests Le client a émis trop de requêtes dans un délai donné.
                error.setText("Vous avez lançer trop de requêtes");
                Log.w(TAG, "429: " + response.errorBody().source());
                handleErrors(response.errorBody());
                pDialog.dismiss();
                break;

            case 444:  // No Response Indique que le serveur n'a retourné aucune information vers le client et a fermé la connexion.
                error.setText("Aucune reponse retourné");
                Log.w(TAG, "444: " + response.errorBody().source());
                handleErrors(response.errorBody());
                pDialog.dismiss();
                break;

            case 500:  // Internal Server Error Erreur interne du serveur.
                error.setText("Erreur interne au niveau du seveur, veuillez réessayer ultérieurement");
                Log.w(TAG, "500: " + response.errorBody().source());
                handleErrors(response.errorBody());
                pDialog.dismiss();
                break;


            case 498:  // Client Closed Request	Le client a fermé la connexion avant de recevoir la réponse. Cette erreur se produit quand le traitement est trop long côté serveur16.
                error.setText("Session expiré");
                Log.w(TAG, "422: " + response.errorBody().source());
                handleErrors(response.errorBody());
                pDialog.dismiss();
                break;
        }
    }


    @SuppressLint("SetTextI18n")
    public void getResponseError(JsonObject jsonObject) {
        JsonObject errors = null;
        AccessToken accessToken = null;
        String error = null;

        try {
            errors = jsonObject.get("errors").getAsJsonObject();
            Log.w(TAG, "onError: " + errors);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            error = errors.get("message").getAsString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            accessToken = new Gson().fromJson(jsonObject, AccessToken.class);
            Log.w(TAG, "onToken: " + accessToken.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.w(TAG, "onTypeError: " + error);

        if (accessToken.getAccessToken() == null) {
            if (error != null) {
                Connexion.this.error.setText(error);
            }
        } else {
            Connexion.this.error.setText("");
            tokenManager.saveToken(accessToken);
            getAuthenticateUser(accessToken);
        }

    }


    public void clearText(View view) {
        error.setText("");
        errorTelephone.setText("");
        errorPassword.setText("");
    }
}
