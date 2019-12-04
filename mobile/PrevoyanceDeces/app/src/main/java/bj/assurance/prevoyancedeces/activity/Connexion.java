package bj.assurance.prevoyancedeces.activity;

import androidx.appcompat.app.AppCompatActivity;
import bj.assurance.prevoyancedeces.R;
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

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kinda.alert.KAlertDialog;

import java.util.List;
import java.util.Map;

public class Connexion extends AppCompatActivity {

    private static final String TAG = "Connexion";

    EditText telephone;
    EditText password;
    static String users;
    TextView errorPassword, errorTelephone;

    UserService service;
    TokenManager tokenManager;

    KAlertDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);

        telephone = findViewById(R.id.etTelephone);
        password = findViewById(R.id.etPassword);

        errorPassword = findViewById(R.id.errorPassword);
        errorTelephone = findViewById(R.id.errorUsername);

        tokenManager = TokenManager.getInstance(getSharedPreferences("prefs", MODE_PRIVATE));

        users = "";

    }

    public void valider(View view) {

        login();

    }

    public void login() {
        if(dataisCorrect()) {

            pDialog = new KAlertDialog(Connexion.this, KAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#2d8df8"));
            pDialog.setTitleText("Authentification");
            pDialog.setCancelable(false);
            pDialog.show();

            Call<AccessToken> call;
            service = RetrofitClientInstance.getRetrofit().create(UserService.class);
            call = service.login(telephone.getText().toString(), password.getText().toString());
            call.enqueue(new Callback<AccessToken>() {
                @Override
                public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {

                    Log.w(TAG, "onResponse: " + response);

                    if (response.isSuccessful()) {
                        tokenManager.saveToken(response.body());
                        getAuthenticateUser(response.body());
                    } else {
                        if (response.code() == 422) {
                            System.out.println(response.errorBody().source());
                            handleErrors(response.errorBody());
                            pDialog.dismiss();
                        }
                        if (response.code() == 401) {
                            ApiError apiError = Utils.converErrors(response.errorBody());
                            Toast.makeText(Connexion.this, apiError.getMessage() + 401, Toast.LENGTH_LONG).show();
                            pDialog.dismiss();
                        }
                    }

                }

                @Override
                public void onFailure(Call<AccessToken> call, Throwable t) {
                    Log.w(TAG, "onFailure: " + t.getMessage());
                    Toast.makeText(Connexion.this, t.getMessage() + "onfailure" , Toast.LENGTH_LONG).show();

                    pDialog.dismiss();

                    new KAlertDialog(Connexion.this, KAlertDialog.WARNING_TYPE)
                            .setTitleText("Connexion impossibe au serveur")
                            .setContentText("Oups!!! quelque chose s'est mal passé vérifier votre connexion internet et réessayer")
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
        Call<Utilisateur> call;
        service = new RetrofitBuildForGetRessource(accessToken).getRetrofit().create(UserService.class);
        call = service.getauthenticateUser();
        call.enqueue(new Callback<Utilisateur>() {
            @Override
            public void onResponse(Call<Utilisateur> call, Response<Utilisateur> response) {

                Log.w(TAG, "onResponse: " + response);

                if (response.isSuccessful()) {
                    Utilisateur utilisateur = response.body();
                    System.out.println(response.body());
                    pDialog.dismiss();
                    checkUserAuthentificate(response.body());
                } else {
                    if (response.code() == 422) {
                        System.out.println(response.errorBody().source());
                        handleErrors(response.errorBody());
                        pDialog.dismiss();
                    }
                    if (response.code() == 401) {
                        ApiError apiError = Utils.converErrors(response.errorBody());
                        Toast.makeText(Connexion.this, apiError.getMessage(), Toast.LENGTH_LONG).show();
                        pDialog.dismiss();
                    }

                    pDialog.dismiss();

                    new KAlertDialog(Connexion.this, KAlertDialog.WARNING_TYPE)
                            .setTitleText("Connexion impossibe au serveur")
                            .setContentText("Oups!!! quelque chose s'est mal passé vérifier votre connexion internet et réessayer")
                            .showCancelButton(true)
                            .setCancelClickListener(new KAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(KAlertDialog sDialog) {
                                    sDialog.cancel();
                                }
                            })
                            .show();
                }

            }

            @Override
            public void onFailure(Call<Utilisateur> call, Throwable t) {
                Log.w(TAG, "onFailure: " + t.getMessage());
                Toast.makeText(Connexion.this, t.getMessage(), Toast.LENGTH_LONG).show();

                pDialog.dismiss();

                new KAlertDialog(Connexion.this, KAlertDialog.WARNING_TYPE)
                        .setTitleText("Connexion impossibe au serveur")
                        .setContentText("Oups!!! quelque chose s'est mal passé vérifier votre connexion internet et réessayer")
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

    private void handleErrors(ResponseBody response) {

        ApiError apiError = Utils.converErrors(response);

        for (Map.Entry<String, List<String>> error : apiError.getErrors().entrySet()) {
            if (error.getKey().equals("username")) {
                errorTelephone.setError(error.getValue().get(0));
            }
            if (error.getKey().equals("password")) {
                errorPassword.setError(error.getValue().get(0));
            }
        }

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

    public void sendForm() {

    }

    public void makeError(){

    }

}
