package bj.assurance.assurancedeces.fragment.resetPassword;


import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.goodiebag.pinview.Pinview;

import java.util.Random;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import bj.assurance.assurancedeces.R;
import bj.assurance.assurancedeces.serviceImplementation.SmsServiceImplementation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ResetPasswordStepTwo extends Fragment {



    private Pinview pinview;
    private TextView textView;

    private String code, smsText = "Chez utilisateur, le code de vérification pour compléter le processus de changement de mot de passe est: ";




    public ResetPasswordStepTwo() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_reset_password_step_two, container, false);


        init(view);

        return view;
    }







    private void init(View view) {

        findView(view);
        initValue();
        setClickListener();


    }





    private void findView(View view) {

        pinview = view.findViewById(R.id.pinview);
        textView = view.findViewById(R.id.resendCode);

    }




    private void initValue() {


        code = generateCode();

        smsText += code;

        System.out.println(code);

    }





    private void setClickListener() {


        pinview.setPinViewEventListener(new Pinview.PinViewEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void onDataEntered(Pinview pinview, boolean fromUser) {
                //Make api calls here or what not

                pinview.setTextColor(getResources().getColor(R.color.black_background));

                if (pinview.getValue().equals(code)){

                    pinview.setTextColor(getResources().getColor(R.color.colorGreen));

                    FragmentTransaction fragmentTransaction = ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.content_main, new ResetPasswordStepThree());
                    fragmentTransaction.commit();

                } else {

                    pinview.setTextColor(getResources().getColor(R.color.red_active));

                    Toast.makeText(getContext(), "Code incorect", Toast.LENGTH_LONG).show();

                }

            }
        });




        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                code = generateCode();

                System.out.println(code);

                /*sendSms("branlycaele", "jetaimebranly", "EHWLINMI ASSURANCE",
                        "22964250705",smsText + code,"5617");*/

            }
        });



    }





    private String generateCode() {


        String code = "";

        Random random = new Random();


        for (int i = 0; i < 5; i++) {


            code += String.valueOf(random.nextInt(10));

        }


        return code;

    }







    private void sendSms(String user, String password, String from, String to, String text, String api) {

        new SmsServiceImplementation()
                .sendSms(user, password, from, to, text, api)

                .enqueue(new Callback<String>() {


                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {

                        if (response.isSuccessful()) {

                            System.out.println(response.body());

                        } else {

                            System.out.println(response.code());

                        }
                    }


                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                        System.out.println(t.getMessage());

                    }
            });


    }




}
