package bj.assurance.assurancedeces.fragment.resetPassword;



import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.scottyab.showhidepasswordedittext.ShowHidePasswordEditText;

import bj.assurance.assurancedeces.R;
import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;


public class ResetPasswordStepThree extends Fragment {


    private ShowHidePasswordEditText passwordEditText, conformPasswordEditText;
    private TextView textErrorPassword, textErrorConformPassword;

    private View view, button;
    private CircularProgressButton circularProgressButton;


    public ResetPasswordStepThree() {
        // Required empty public constructor
    }





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_reset_password_step_three, container, false);


        init(view);


        return view;
    }







    private void init(View view) {

        findView(view);
        initValue();
        onClickListener();

    }




    private void findView(View view) {

        passwordEditText = view.findViewById(R.id.editTextPassword);
        conformPasswordEditText = view.findViewById(R.id.editTextPasswordConfirm);

        textErrorPassword = view.findViewById(R.id.textErrorIdentifiant);
        textErrorConformPassword = view.findViewById(R.id.textErrorPasswordConfirm);


        button = view.findViewById(R.id.bottom_next);

        circularProgressButton = view.findViewById(R.id.btn_loaader);


        this.view = view.findViewById(R.id.validate);

    }




    private void initValue() {



    }





    private void onClickListener() {


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (validateForm()) {

                    if (passwordEditText.getText().toString().equals(conformPasswordEditText.getText().toString())) {

                        button.setVisibility(View.INVISIBLE);
                        circularProgressButton.setVisibility(View.VISIBLE);
                        circularProgressButton.startAnimation();

                    } else {

                        textErrorConformPassword.setText("Mot de passe non conforme");

                    }

                }

            }
        });


    }






    @SuppressLint("SetTextI18n")
    private boolean validateForm() {

        boolean validate = true;


        if (passwordEditText.getText().toString().equals("")) {

            textErrorPassword.setText("Veuillez renseigner le nouveau mot de passe");
            validate = false;

        } else {

            textErrorPassword.setText("");

        }


        if (conformPasswordEditText.getText().toString().equals("")) {

            textErrorConformPassword.setText("Veuillez renseigner le nouveau mot de passe");
            validate = false;

        } else {

            textErrorConformPassword.setText("");

        }


        return validate;

    }



}
