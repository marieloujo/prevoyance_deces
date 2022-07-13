package bj.assurance.assurancedeces.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import bj.assurance.assurancedeces.R;
import bj.assurance.assurancedeces.serviceImplementation.UtilisateurServiceImplementation;
import bj.assurance.assurancedeces.utils.token.TokenManager;



public class SplashScreen extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

       // TokenManager.getInstance(getSharedPreferences("prefs", MODE_PRIVATE)).deleteToken();

        run();

    }



    private void run() {

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent i = new Intent();

                i.setClass(SplashScreen.this,Connexion.class);
                startActivity(i);
                finish();
            }
        } ,1000);

    }








}
