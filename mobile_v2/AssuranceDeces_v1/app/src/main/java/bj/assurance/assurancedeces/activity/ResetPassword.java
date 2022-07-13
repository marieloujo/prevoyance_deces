package bj.assurance.assurancedeces.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import bj.assurance.assurancedeces.R;
import bj.assurance.assurancedeces.fragment.marchand.Accueil;
import bj.assurance.assurancedeces.fragment.resetPassword.ResetPasswordStepOne;
import bj.assurance.assurancedeces.fragment.resetPassword.ResetPasswordStepThree;

import android.os.Bundle;
import android.view.View;

public class ResetPassword extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);



        ((View) findViewById(R.id.backFrame)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.content_main, new ResetPasswordStepOne());
        fragmentTransaction.commit();


    }
}
