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

        users = "";

    }

    public void valider(View view) {

        if(dataisCorrect()) {

            if (telephone.getRawText().equals("00000000")) {
                Intent intent = new Intent(Connexion.this, Main2Activity.class);
                startActivity(intent);
            } else if (telephone.getRawText().equals("11111111")) {
                Intent intent = new Intent(Connexion.this, MarchandMainActivity.class);
                startActivity(intent);
            } else if (telephone.getRawText().equals("22222222")) {
                users = "super marchants";
                Intent intent = new Intent(Connexion.this, MarchandMainActivity.class);
                startActivity(intent);
            } else {
                errorTelephone.setText("Numéro de téléphone non reconnu");
                return;
            }

        } else return;
    }

    public boolean dataisCorrect() {

        boolean bool = true;

        if (telephone.getRawText().isEmpty()) {
            errorTelephone.setText(getString(R.string.alert_telephone_not_write));
            bool = false;
        } else errorTelephone.setText("");


        if (password.getText().toString().isEmpty()) {
            errorPassword.setText(getString(R.string.alert_password_not_write));
            bool = false;
        } else  errorPassword.setText("");

        return bool;
    }

    public void sendForm() {

    }

    public void makeError() {

    }

}
