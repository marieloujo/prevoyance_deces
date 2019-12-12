package bj.assurance.prevoyancedeces;

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
import android.widget.Toast;

import com.arthurivanets.bottomsheets.BaseBottomSheet;
import com.arthurivanets.bottomsheets.config.BaseConfig;
import com.arthurivanets.bottomsheets.config.Config;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.kinda.alert.KAlertDialog;

import java.lang.reflect.Type;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import bj.assurance.prevoyancedeces.activity.Main2Activity;
import bj.assurance.prevoyancedeces.activity.MarchandMainActivity;
import bj.assurance.prevoyancedeces.adapter.TransactionAdater;
import bj.assurance.prevoyancedeces.fragment.marchand.ListeClients;
import bj.assurance.prevoyancedeces.model.Contrat;
import bj.assurance.prevoyancedeces.model.Marchand;
import bj.assurance.prevoyancedeces.model.Portefeuille;
import bj.assurance.prevoyancedeces.model.pagination.OutputPaginate;
import bj.assurance.prevoyancedeces.retrofit.RetrofitBuildForGetRessource;
import bj.assurance.prevoyancedeces.retrofit.Service.ClientService;
import bj.assurance.prevoyancedeces.retrofit.Service.MarchandService;
import bj.assurance.prevoyancedeces.retrofit.TokenManager;
import bj.assurance.prevoyancedeces.utils.AccessToken;
import bj.assurance.prevoyancedeces.utils.ApiError;
import bj.assurance.prevoyancedeces.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static androidx.constraintlayout.widget.Constraints.TAG;

public class SimpleCustomBottomSheet extends BaseBottomSheet {

    EditText reference, montant;
    Button button;
    Contrat contrat;
    TextView textView;

    public SimpleCustomBottomSheet(@NonNull Activity hostActivity) {
        this(hostActivity, new Config.Builder(hostActivity).build());
    }

    public SimpleCustomBottomSheet(@NonNull Activity hostActivity, @NonNull BaseConfig config) {
        super(hostActivity, config);
    }

    @NonNull
    @Override
    public final View onCreateSheetContentView(@NonNull Context context) {

        View view = LayoutInflater.from(context).inflate(R.layout.form_add_transaction, this, false);

        reference = view.findViewById(R.id.reference_contat);
        montant = view.findViewById(R.id.montant_paye);
        button = view.findViewById(R.id.validate);
        textView = view.findViewById(R.id.error);

        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (verifData()) {
                    getContratbyReference(
                        TokenManager.getInstance(getContext().
                            getSharedPreferences("prefs", MODE_PRIVATE)).
                            getToken(), reference.getText().toString()
                    );
                }
            }
        });


        return view;
    }

    @SuppressLint("SetTextI18n")
    public boolean verifData() {

        boolean verif = true;

        if (reference.getText().toString().isEmpty()) {
            textView.setText("Veuillez renseigner la reférence");
            verif = false;
        } else if ( montant.getText().toString().isEmpty())  {
            textView.setText("Veuillez renseigner et le montant");
            verif = false;
        }
        return verif;
    }

    private void getContratbyReference(AccessToken accessToken, String reference) {
        Call<JsonObject> call;
        MarchandService service = new RetrofitBuildForGetRessource(accessToken).getRetrofit().create(MarchandService.class);
        call = service.findbyContrat(reference);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                Log.w(TAG, "onResponse: " + response);

                if (response.isSuccessful()) {
                    //System.out.println(response.body());
                    String string = response.body().get("contrat_id").getAsString();
                    System.out.println(string);
                    Portefeuille portefeuille = new Portefeuille(montant.getText().toString(), new Contrat(
                            Long.valueOf(string)
                    ), MarchandMainActivity.getMarchand());

                    sendDepot(
                            TokenManager.getInstance(getContext().
                                    getSharedPreferences("prefs", MODE_PRIVATE)).
                                    getToken(), portefeuille );

                    //getResponseTransaction(response.body());
                } else {
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.w(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    private void sendDepot(AccessToken accessToken, Portefeuille portefeuille) {

        KAlertDialog pDialog = new KAlertDialog(getContext(), KAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#2d8df8"));
        pDialog.setTitleText("Enregistrement");
        pDialog.setCancelable(false);
        pDialog.show();

        Call<JsonObject> call;
        MarchandService service = new RetrofitBuildForGetRessource(accessToken).getRetrofit().create(MarchandService.class);
        call = service.depot(portefeuille);
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

    private void getResponseTransaction(JsonObject jsonObject) {
        JsonObject error = null, sucess = null;
        String messageError = null, message = null;
        OutputPaginate outputPaginate = null;
        contrat = null;

        try {
            error = jsonObject.getAsJsonObject("errors");
            messageError = error.get("message").getAsString();
            textView.setText(messageError);
        }catch (Exception ignored) {}

        try {
            sucess = jsonObject.getAsJsonObject("success");
            //message = sucess.get("message").getAsString();

            String s1 = sucess.get("data").getAsString();
            contrat = new Gson().fromJson(s1, Contrat.class);

            System.out.println(s1);
            System.out.println(contrat);

            Portefeuille portefeuille = new Portefeuille(montant.getText().toString(), contrat, MarchandMainActivity.getMarchand());

            /*sendDepot(
                    TokenManager.getInstance(getContext().
                            getSharedPreferences("prefs", MODE_PRIVATE)).
                            getToken(), portefeuille);*/

        }catch (Exception e) {
            e.printStackTrace();
        }


    }
}
