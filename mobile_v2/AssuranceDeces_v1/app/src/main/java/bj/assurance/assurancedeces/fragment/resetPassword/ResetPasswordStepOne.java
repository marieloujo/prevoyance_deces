package bj.assurance.assurancedeces.fragment.resetPassword;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.labters.lottiealertdialoglibrary.ClickListener;
import com.labters.lottiealertdialoglibrary.LottieAlertDialog;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import bj.assurance.assurancedeces.R;
import bj.assurance.assurancedeces.activity.Connexion;
import bj.assurance.assurancedeces.fragment.CatchError;
import bj.assurance.assurancedeces.model.Userable;
import bj.assurance.assurancedeces.model.Utilisateur;
import bj.assurance.assurancedeces.model.customModel.ValidationEror;
import bj.assurance.assurancedeces.serviceImplementation.UtilisateurServiceImplementation;
import bj.assurance.assurancedeces.utils.CatchApiError;
import bj.assurance.assurancedeces.utils.ShowDialog;
import bj.assurance.assurancedeces.utils.onConnexionListener;
import bj.assurance.assurancedeces.utils.token.TokenManager;
import br.com.sapereaude.maskedEditText.MaskedEditText;
import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


public class ResetPasswordStepOne extends Fragment {



    private EditText identifiant;
    private MaskedEditText telephone;
    private TextView textErrorIdentifiant, textErrorTelelephone;



    private LinearLayout linearLayout, button;
    private CircularProgressButton circularProgressButton;



    public ResetPasswordStepOne() {
        // Required empty public constructor
    }





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_reset_password_step_one, container, false);

        init(view);

        return view;
    }







    private void init(View view) {

        findView(view);
        initValue();
        setClicklistenr();

    }






    private void findView(View view) {

        identifiant = view.findViewById(R.id.editTextIdentifiant);
        telephone = view.findViewById(R.id.etTelephoneUser);

        textErrorIdentifiant = view.findViewById(R.id.textErrorIdentifiant);
        textErrorTelelephone = view.findViewById(R.id.textErrorTelephone);


        linearLayout = view.findViewById(R.id.validateSearchAccount);
        button = view.findViewById(R.id.bottom_next);

        circularProgressButton = view.findViewById(R.id.btn_loaader);

    }





    private void initValue() {



    }






    private void setClicklistenr() {


        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //circularProgressButton.startAnimation();

                button.setVisibility(View.VISIBLE);
                circularProgressButton.setVisibility(View.INVISIBLE);
                circularProgressButton.dispose();


                FragmentTransaction fragmentTransaction = ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.content_main, new ResetPasswordStepTwo());
                fragmentTransaction.commit();

                /*if (validation()) {

                    Utilisateur utilisateur = new Utilisateur();
                    utilisateur.setTelephone(telephone.getRawText());

                    Userable userable = new Userable();
                    userable.setUtilisateur(utilisateur);
                    userable.setLogin(identifiant.getText().toString());

                    findCompte(userable);

                    System.out.println(new Gson().toJson(userable));





                }*/

            }
        });

    }








    @SuppressLint("SetTextI18n")
    private boolean validation() {


        boolean validate = true;


        textErrorTelelephone.setText("");
        textErrorIdentifiant.setText("");


        if (identifiant.getText().toString().equals("")) {

            textErrorIdentifiant.setText("Veuillez renseigner votre identifiant");
            validate = false;

        } else {

            textErrorIdentifiant.setText("");

        }

        if (telephone.getRawText().toString().equals("")) {

            System.out.println(telephone.getRawText());

            textErrorTelelephone.setText("Veuillez renseigner votre télephone");
            validate = false;

        }


        return validate;


    }










    private void findCompte(final Userable userable) {

        button.setVisibility(View.INVISIBLE);
        circularProgressButton.setVisibility(View.VISIBLE);
        circularProgressButton.startAnimation();


        new UtilisateurServiceImplementation(getContext()).findCompte(userable,

                TokenManager.getInstance(getActivity().
                        getSharedPreferences("prefs", MODE_PRIVATE)).
                        getToken()

            ).enqueue(new Callback<JsonObject>() {

            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {


                if (response.isSuccessful()) {

                    try {

                        Type listType = new TypeToken<List<Userable>>() {}.getType();
                        List<Userable> userables = new Gson().fromJson(response.body().getAsJsonArray("data"), listType);

                        textErrorTelelephone.setText("");
                        textErrorIdentifiant.setText("");


                        button.setVisibility(View.VISIBLE);
                        circularProgressButton.setVisibility(View.INVISIBLE);
                        circularProgressButton.dispose();


                        FragmentTransaction fragmentTransaction = ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.content_main, new ResetPasswordStepTwo());
                        fragmentTransaction.commit();


                    } catch (Exception e) {

                        e.printStackTrace();

                        textErrorTelelephone.setText("Un truc s'est mal passé, vérifier les informations et reéssayer.");

                        button.setVisibility(View.VISIBLE);
                        circularProgressButton.setVisibility(View.INVISIBLE);
                        circularProgressButton.dispose();


                    }


                } else {


                    new CatchApiError(response.code()).catchError(getContext());


                    if (response.code() != 401) {

                        try {

                            String jObjError = new JSONObject(
                                    response.errorBody().string()).getJSONObject("errors").getString("message");


                            ValidationEror validationEror = new Gson().fromJson(
                                    jObjError, ValidationEror.class);


                            System.out.println("errors " + validationEror + " \n " + jObjError);


                           if (validationEror.getErrorLogin() == null || validationEror.getErrorPassword() == null) {

                               textErrorTelelephone.setText("Aucun compte trouvé");

                           } else {

                               textErrorTelelephone.setText("");
                               textErrorIdentifiant.setText("");
                           }

                            button.setVisibility(View.VISIBLE);
                            circularProgressButton.setVisibility(View.INVISIBLE);
                            circularProgressButton.dispose();


                        } catch (Exception e) {

                            e.printStackTrace();

                            button.setVisibility(View.VISIBLE);
                            circularProgressButton.setVisibility(View.INVISIBLE);
                            circularProgressButton.dispose();

                        }

                    }

                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {


                try {

                    if (t.getMessage().equals("timeout")) {

                        findCompte(userable);

                    } else {

                        button.setVisibility(View.VISIBLE);
                        circularProgressButton.setVisibility(View.INVISIBLE);
                        circularProgressButton.dispose();

                        if (new onConnexionListener(getContext()).checkConnexion()) {

                            new ShowDialog(getContext()).showWarningDialog("Echec de connexion",
                                    "Imposible d'établir une connexion avec le seuveur. Veuillez réessayer ultérieurement");

                        } else

                            new ShowDialog(getContext()).showNoInternetDialog().setPositiveListener(new ClickListener() {
                                @Override
                                public void onClick(@NotNull LottieAlertDialog lottieAlertDialog) {
                                    lottieAlertDialog.dismiss();
                                    findCompte(userable);
                                }
                            })
                                    .build()
                                    .show();

                    }


                } catch (Exception e) {

                    e.printStackTrace();

                    button.setVisibility(View.VISIBLE);
                    circularProgressButton.setVisibility(View.INVISIBLE);
                    circularProgressButton.dispose();

                }



            }
        });



    }







}
