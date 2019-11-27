package bj.assurance.prevoyancedeces.activity;

import androidx.appcompat.app.AppCompatActivity;
import bj.assurance.prevoyancedeces.R;
import br.com.sapereaude.maskedEditText.MaskedEditText;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Connexion extends AppCompatActivity {

    MaskedEditText telephone;
    EditText password;
    static String users;
    TextView errorPassword, errorTelephone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);

        telephone = findViewById(R.id.etTelephone);
        password = findViewById(R.id.etPassword);

        errorPassword = findViewById(R.id.errorPassword);
        errorTelephone = findViewById(R.id.errorUsername);

    }

    public void valider(View view) {

        if(dataisCorrect()) {

            if (telephone.getRawText().equals("00000000")) {
                users = "client";
            } else if (telephone.getRawText().equals("11111111")) {
                users = "marchands";
            } else if (telephone.getRawText().equals("22222222")) {
                users = "super marchants";
            } else {
                errorTelephone.setText("Numéro de téléphone non reconnu");
                return;
            }

            Intent intent = new Intent(Connexion.this, Main2Activity.class);
            startActivity(intent);
        } else return;
    }

    public boolean dataisCorrect() {

        boolean bool = true;

        if (telephone.getRawText().isEmpty()) {
            errorTelephone.setText("Veillez saisir votre numéro de télephone");
            bool = false;
        } else errorTelephone.setText("");


        if (password.getText().toString().isEmpty()) {
            bool = false;
        } else  errorPassword.setText("");

        return bool;
    }

    public void sendForm() {

    }

    public void makeError() {

    }


}
