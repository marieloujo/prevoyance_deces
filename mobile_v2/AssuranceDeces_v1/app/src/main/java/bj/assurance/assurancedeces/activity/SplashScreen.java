package bj.assurance.assurancedeces.activity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import bj.assurance.assurancedeces.R;
import bj.assurance.assurancedeces.model.Commune;
import bj.assurance.assurancedeces.model.Departement;
import bj.assurance.assurancedeces.model.SuperMarchand;
import bj.assurance.assurancedeces.model.Userable;
import bj.assurance.assurancedeces.model.Utilisateur;
import bj.assurance.assurancedeces.utils.json.ReadExternalJson;
import bj.assurance.assurancedeces.utils.token.TokenManager;

public class SplashScreen extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

         //TokenManager.getInstance(getSharedPreferences("prefs", MODE_PRIVATE)).deleteToken();



        run();


    }




    private void run() {

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent i = new Intent();

                i.setClass(SplashScreen.this, ResetPassword.class);
                startActivity(i);
                finish();
            }
        } ,1000);

    }









}
