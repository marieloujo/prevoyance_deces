package bj.assurance.assurancedeces.activity;

import androidx.appcompat.app.AppCompatActivity;
import bj.assurance.assurancedeces.R;
import bj.assurance.assurancedeces.serviceImplementation.UtilisateurServiceImplementation;
import bj.assurance.assurancedeces.utils.ShowDialog;
import bj.assurance.assurancedeces.utils.token.TokenManager;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Connexion extends AppCompatActivity {




    private EditText telephone, password;
    private static TextView errorPassword, errorTelephone;
    private static TextView error;
    private UtilisateurServiceImplementation utilisateurServiceImplementation;

    private ShowDialog showDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);

        init();
    }



    public void init() {


        findbyId();
        initValue();
        checkToken();

    }



    private void findbyId() {
        telephone = findViewById(R.id.etTelephone);
        password = findViewById(R.id.etPassword);

        errorPassword = findViewById(R.id.errorPassword);
        errorTelephone = findViewById(R.id.errorUsername);
        error = findViewById(R.id.error);

    }



    private void initValue() {

        TokenManager tokenManager = TokenManager.getInstance(getSharedPreferences("prefs", MODE_PRIVATE));

        utilisateurServiceImplementation = new UtilisateurServiceImplementation(
                Connexion.this );


        showDialog = new ShowDialog(Connexion.this);


    }



    public void valider(View view) {

        if (makeEror()) {

            error.setText("");

            utilisateurServiceImplementation.login(
                    telephone.getText().toString(),
                    password.getText().toString()
            );


        }

    }



    public boolean makeEror() {

        boolean bool = true;

        if (telephone.getText().toString().isEmpty()) {

            errorTelephone.setText(getString(R.string.alert_telephone_not_write));
            bool = false;


        } else

            errorTelephone.setText("");


        if (password.getText().toString().isEmpty()) {

            errorPassword.setText(getString(R.string.alert_password_not_write));
            bool = false;

        } else

            errorPassword.setText("");

        return bool;
    }



    private void checkToken() {

        try {

            showDialog.showLoaddingDialog("Vérification", "Veuillez patienter");

        } catch (Exception e){

            e.printStackTrace();
            
        }

        if (!(TokenManager.getInstance(getSharedPreferences("prefs", MODE_PRIVATE))
                        .getToken().getAccessToken() == null)) {

            utilisateurServiceImplementation.getAuth();

            return;

        } else {

            showDialog.getAlertDialog().dismiss();

        }

    }






    public static TextView getErrorPassword() {
        return errorPassword;
    }


    public static void setErrorPassword(TextView errorPassword) {
        Connexion.errorPassword = errorPassword;
    }



    public static TextView getErrorTelephone() {
        return errorTelephone;
    }


    public static void setErrorTelephone(TextView errorTelephone) {
        Connexion.errorTelephone = errorTelephone;
    }



}
