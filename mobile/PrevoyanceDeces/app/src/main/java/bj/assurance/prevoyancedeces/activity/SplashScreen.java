package bj.assurance.prevoyancedeces.activity;

import androidx.appcompat.app.AppCompatActivity;
import bj.assurance.prevoyancedeces.R;
import bj.assurance.prevoyancedeces.Utils.AccessToken;
import bj.assurance.prevoyancedeces.Utils.ApiError;
import bj.assurance.prevoyancedeces.Utils.Utils;
import bj.assurance.prevoyancedeces.model.Client;
import bj.assurance.prevoyancedeces.model.Marchand;
import bj.assurance.prevoyancedeces.model.SuperMarchand;
import bj.assurance.prevoyancedeces.model.Utilisateur;
import bj.assurance.prevoyancedeces.retrofit.RetrofitBuildForGetRessource;
import bj.assurance.prevoyancedeces.retrofit.Service.UserService;
import bj.assurance.prevoyancedeces.retrofit.TokenManager;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Switch;
import android.widget.Toast;

import com.google.gson.Gson;
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

                /*i.setClass(SplashScreen.this, Main2Activity.class);
                startActivity(i);
                finish();*/

                if (!(TokenManager.getInstance(getSharedPreferences("prefs", MODE_PRIVATE)).getToken().getAccessToken() == null)) {

                    getAuthenticateUser(TokenManager.getInstance(getSharedPreferences("prefs", MODE_PRIVATE)).getToken());

                } else {
                    i.setClass(SplashScreen.this,Connexion.class);
                    startActivity(i);
                    finish();
                }
            }
        } ,1000);

    }

    public void getAuthenticateUser(AccessToken accessToken) {
        Call<Utilisateur> call;
        UserService service = new RetrofitBuildForGetRessource(accessToken).getRetrofit().create(UserService.class);
        call = service.getauthenticateUser();
        call.enqueue(new Callback<Utilisateur>() {
            @Override
            public void onResponse(Call<Utilisateur> call, Response<Utilisateur> response) {

                Log.w(TAG, "onResponse: " + response);

                if (response.isSuccessful()) {
                    System.out.println(response.body());
                    checkUserAuthentificate(response.body());
                    finish();
                } else {
                    if (response.code() == 422) {
                        System.out.println(response.errorBody().source());
                        handleErrors(response.errorBody());
                    }
                    if (response.code() == 401) {
                        ApiError apiError = Utils.converErrors(response.errorBody());
                        Intent i = new Intent(SplashScreen.this,Connexion.class);
                        startActivity(i);
                        finish();
                    }
                }

            }

            @Override
            public void onFailure(Call<Utilisateur> call, Throwable t) {
                Log.w(TAG, "onFailure: " + t.getMessage());
                Toast.makeText(SplashScreen.this, t.getMessage(), Toast.LENGTH_LONG).show();
                System.out.println(t.getMessage());

                new KAlertDialog(SplashScreen.this, KAlertDialog.WARNING_TYPE)
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
        }

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
}
