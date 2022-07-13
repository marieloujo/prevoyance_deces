package bj.assurance.prevoyancedeces.BottomSheet;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
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
import bj.assurance.prevoyancedeces.R;
import bj.assurance.prevoyancedeces.activity.MarchandMainActivity;
import bj.assurance.prevoyancedeces.model.Compte;
import bj.assurance.prevoyancedeces.model.Contrat;
import bj.assurance.prevoyancedeces.model.Marchand;
import bj.assurance.prevoyancedeces.model.Portefeuille;
import bj.assurance.prevoyancedeces.model.pagination.OutputPaginate;
import bj.assurance.prevoyancedeces.retrofit.RetrofitBuildForGetRessource;
import bj.assurance.prevoyancedeces.retrofit.Service.MarchandService;
import bj.assurance.prevoyancedeces.retrofit.TokenManager;
import bj.assurance.prevoyancedeces.utils.AccessToken;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static androidx.constraintlayout.widget.Constraints.TAG;

public class TransferToAnotherBottomSheet extends BaseBottomSheet {

    EditText matricule, montant;
    Button button;
    TextView errorMatricule, errorMontant, solde;
    Marchand marchand;

    public TransferToAnotherBottomSheet(@NonNull Activity hostActivity) {
        this(hostActivity, new Config.Builder(hostActivity).build());
    }

    public TransferToAnotherBottomSheet(@NonNull Activity hostActivity, @NonNull BaseConfig config) {
        super(hostActivity, config);
    }

    @NonNull
    @Override
    public final View onCreateSheetContentView(@NonNull Context context) {

        View view = LayoutInflater.from(context).inflate(R.layout.form_transfert_to_marchand, this, false);

        init(view);


        return view;
    }

    private void init(View view) {
        matricule = view.findViewById(R.id.matricule_marchand);
        montant = view.findViewById(R.id.montant_paye);

        errorMatricule = view.findViewById(R.id.errorMatricule);
        errorMontant = view.findViewById(R.id.errorMontant);
        solde = view.findViewById(R.id.solde);

        button = view.findViewById(R.id.validate);

        solde.setText(MarchandMainActivity.getMarchand().getCommission());

        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verifData()) {
                    /*getMarchandbyMatricule(
                            TokenManager.getInstance(getContext().
                                    getSharedPreferences("prefs", MODE_PRIVATE)).
                                    getToken(), matricule.getText().toString()
                    );*/
                }
            }
        });
    }

    @SuppressLint("SetTextI18n")
    public boolean verifData() {

        boolean verif = true;

        if (montant.getText().toString().isEmpty()) {
            verif = false;
            errorMontant.setText("Vous devez renseigner le montant");
        } else {
            errorMontant.setText("");
            if (Float.valueOf(montant.getText().toString()) > Float.valueOf(MarchandMainActivity.getMarchand().getCommission())) {
                verif = false;
                errorMontant.setText("Vous ne pouvez pas transféret plus de la somme dont vous disposez dans votre commission");
            } else errorMontant.setText("");
        }

        if (matricule.getText().toString().isEmpty()) {
            verif = false;
            errorMatricule.setText("Vous devez renseigner le matricule");
        } else errorMatricule.setText("");

        return verif;
    }

    private void getMarchandbyMatricule(AccessToken accessToken, String matricule) {
        Call<JsonObject> call;
        MarchandService service = new RetrofitBuildForGetRessource(accessToken).getRetrofit().create(MarchandService.class);
        call = service.getMarchandbyMatricule(matricule);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                Log.w(TAG, "onResponse: " + response);

                if (response.isSuccessful()) {
                    //System.out.println(response.body());
                    String string = response.body().get("contrat_id").getAsString();
                    System.out.println(string);

                    /*sendDepot(
                            TokenManager.getInstance(getContext().
                                    getSharedPreferences("prefs", MODE_PRIVATE)).
                                    getToken(), compte );*/

                    getResponseMarchand(response.body());
                } else {
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.w(TAG, "onFailure: " + t.getMessage());
            }
        });
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
                    pDialog.dismiss();

                    new KAlertDialog(getContext(), KAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Montant déposé")
                            .setTitleText(response.body().toString())
                            .showCancelButton(true)
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

    private void getResponseMarchand(JsonObject jsonObject) {
        JsonObject error = null, sucess = null;
        String messageError = null, message = null;
        OutputPaginate outputPaginate = null;

        try {
            error = jsonObject.getAsJsonObject("errors");
            messageError = error.get("message").getAsString();
            errorMatricule.setText(messageError);
        }catch (Exception ignored) {}

        try {
            sucess = jsonObject.getAsJsonObject("success");
            //message = sucess.get("message").getAsString();

            String s1 = sucess.get("data").getAsString();
            marchand = new Gson().fromJson(s1, Marchand.class);

            System.out.println(s1);
            System.out.println(marchand);

            Compte compte = new Compte();
            compte.setCompteable(marchand);
            compte.setCompteableType("App\\Models\\Marchand");
            compte.setCompte("credit_virtuel");
            compte.setMontant(montant.getText().toString());


            sendDepot(
                    TokenManager.getInstance(getContext().
                            getSharedPreferences("prefs", MODE_PRIVATE)).
                            getToken(), compte);

        }catch (Exception e) {
            e.printStackTrace();
        }


    }
}
