package bj.assurance.prevoyancedeces.BottomSheet;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.arthurivanets.bottomsheets.BaseBottomSheet;
import com.arthurivanets.bottomsheets.config.BaseConfig;
import com.arthurivanets.bottomsheets.config.Config;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.kinda.alert.KAlertDialog;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import bj.assurance.prevoyancedeces.R;
import bj.assurance.prevoyancedeces.activity.Main2Activity;
import bj.assurance.prevoyancedeces.activity.MarchandMainActivity;
import bj.assurance.prevoyancedeces.model.Compte;
import bj.assurance.prevoyancedeces.model.Contrat;
import bj.assurance.prevoyancedeces.model.Marchand;
import bj.assurance.prevoyancedeces.model.Portefeuille;
import bj.assurance.prevoyancedeces.model.Utilisateur;
import bj.assurance.prevoyancedeces.model.pagination.OutputPaginate;
import bj.assurance.prevoyancedeces.retrofit.RetrofitBuildForGetRessource;
import bj.assurance.prevoyancedeces.retrofit.Service.MarchandService;
import bj.assurance.prevoyancedeces.retrofit.Service.UserService;
import bj.assurance.prevoyancedeces.retrofit.TokenManager;
import bj.assurance.prevoyancedeces.utils.AccessToken;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static androidx.constraintlayout.widget.Constraints.TAG;

public class TransfertCommissionBottomSheet extends BaseBottomSheet {

    EditText montant;
    Button button;
    TextView textView, solde;
    boolean isMe;

    public TransfertCommissionBottomSheet(@NonNull Activity hostActivity, boolean bool) {
        this(hostActivity, new Config.Builder(hostActivity).build());
        this.isMe = bool;
    }

    public TransfertCommissionBottomSheet(@NonNull Activity hostActivity, @NonNull BaseConfig config) {
        super(hostActivity, config);
    }


    @NonNull
    @Override
    public final View onCreateSheetContentView(@NonNull Context context) {


        View view = LayoutInflater.from(context).inflate(R.layout.form_transfert_to_my_compte, this, false);

        initForMyCompte(view);


        return view;
    }


    @SuppressLint("SetTextI18n")
    private void initForMyCompte(View view) {
        solde = view.findViewById(R.id.solde);
        montant = view.findViewById(R.id.montant_transferer);
        textView = view.findViewById(R.id.errorMontant);
        button = view.findViewById(R.id.validate);

        System.out.println(MarchandMainActivity.getMarchand().getCommission());

        solde.setText(MarchandMainActivity.getMarchand().getCommission() + " coins");

        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verifData()) {

                    Marchand marchand = new Marchand(MarchandMainActivity.getMarchand().getId());

                    Compte compte = new Compte();
                    compte.setCompteable(marchand);
                    compte.setMontant(montant.getText().toString());

                    System.out.println("\n \n Compte: \n " + new Gson().toJson(compte) + "\n");

                    sendDepot(
                            TokenManager.getInstance(getContext().
                                    getSharedPreferences("prefs", MODE_PRIVATE)).
                                    getToken(), compte
                    );
                }
            }
        });
    }


    @SuppressLint("SetTextI18n")
    public boolean verifData() {

        boolean verif = true;

        if (montant.getText().toString().isEmpty()) {
            verif = false;
            textView.setText("Vous devez renseigner le montant");
        } else {
            textView.setText("");
            if (Float.valueOf(montant.getText().toString()) > Float.valueOf(MarchandMainActivity.getMarchand().getCommission())) {
                verif = false;
                textView.setText("Vous ne pouvez pas transféret plus de la somme dont vous disposez dans votre commission");
            } else textView.setText("");

        }

        return verif;
    }

    private void sendDepot(AccessToken accessToken, Compte compte) {

        KAlertDialog pDialog = new KAlertDialog(getContext(), KAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#2d8df8"));
        pDialog.setTitleText("Enregistrement");
        pDialog.setCancelable(false);
        pDialog.show();

        Call<JsonObject> call;
        MarchandService service = new RetrofitBuildForGetRessource(accessToken).getRetrofit().create(MarchandService.class);
        call = service.tranfertCommission(compte);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                Log.w(TAG, "onResponse: " + response);

                if (response.isSuccessful()) {
                    System.out.println(response.body());

                    getAuthenticateUser(
                            TokenManager.getInstance(getContext().
                                    getSharedPreferences("prefs", MODE_PRIVATE)).
                                    getToken()
                    );

                    pDialog.changeAlertType(KAlertDialog.SUCCESS_TYPE);
                    pDialog.setTitleText("Transfert effectué avec succes")
                            .setConfirmText("Ok")
                            .showCancelButton(false)
                            .setConfirmClickListener(new KAlertDialog.OnSweetClickListener() {
                                @RequiresApi(api = Build.VERSION_CODES.N)
                                @Override
                                public void onClick(KAlertDialog kAlertDialog) {
                                    pDialog.dismiss();
                                }
                            })
                            .show();
                } else {
                    pDialog.dismiss();
                    Log.w(TAG, "onFailure: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                pDialog.dismiss();
                Log.w(TAG, "onFailure: " + t.getMessage());
                //Toast.makeText(getActivity(), t.getMessage()+ t.getCause().getCause().getCause(), Toast.LENGTH_LONG).show();

                new KAlertDialog(getContext(), KAlertDialog.WARNING_TYPE)
                        .setTitleText("Echec")
                        .setContentText(t.getMessage())
                        .setCancelText("Ok")
                        .showCancelButton(true)
                        .show();
            }
        });
    }

    public void getAuthenticateUser(AccessToken accessToken) {

        Call<JsonObject> call;
        UserService service = new RetrofitBuildForGetRessource(accessToken).getRetrofit().create(UserService.class);
        call = service.getauthenticateUser();
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                Log.w(TAG, "onResponse: " + response.body());

                if (response.isSuccessful()) {
                    getResponseUser(response.body());
                    //checkUserAuthentificate(response.body());
                } else {
                }
            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.w(TAG, "onFailure: " + t.getMessage());
                System.out.println(t.getMessage());

            }
        });
    }

    public void getResponseUser(JsonObject jsonObject) {
        JsonObject error = null, sucess = null;
        String messageError = null, message = null;
        Utilisateur utilisateur = null;

        try {
            error = jsonObject.getAsJsonObject("errors");
            messageError = error.get("message").getAsString();
        }catch (Exception ignored){}

        try {
            sucess = jsonObject.getAsJsonObject("success");
        }catch (Exception ignored) {}

        try {
            message = sucess.get("message").getAsString();
        } catch (Exception ignored) {}

        try {
            utilisateur = new Gson().fromJson(sucess.getAsJsonObject("data"), Utilisateur.class);

            Marchand marchand = new Gson().fromJson(new Gson().toJson(
                    utilisateur.getObject()), Marchand.class);

            MarchandMainActivity.setUtilisateur(utilisateur);
            MarchandMainActivity.setMarchand(marchand);

            solde.setText(marchand.getCommission());


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

