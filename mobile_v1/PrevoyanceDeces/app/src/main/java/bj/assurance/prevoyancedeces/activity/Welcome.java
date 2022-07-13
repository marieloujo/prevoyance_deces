package bj.assurance.prevoyancedeces.activity;

import androidx.appcompat.app.AppCompatActivity;
import bj.assurance.prevoyancedeces.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Welcome extends AppCompatActivity {

    static boolean visiteur = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }

    public void connexion(View view) {
        visiteur = false;
        Intent intent = new Intent(Welcome.this, Connexion.class);
        startActivity(intent);
    }

    public void visiter(View view) {
        visiteur = true;
        Intent intent = new Intent(Welcome.this, MainVisiteurActivity.class);
        startActivity(intent);
    }

}
