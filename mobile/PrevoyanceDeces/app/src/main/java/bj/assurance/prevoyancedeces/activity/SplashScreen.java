package bj.assurance.prevoyancedeces.activity;

import androidx.appcompat.app.AppCompatActivity;
import bj.assurance.prevoyancedeces.R;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends AppCompatActivity {

    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashScreen.this, Welcome.class);
                startActivity(i);
                finish();
            }
        } ,1000);

    }
}
