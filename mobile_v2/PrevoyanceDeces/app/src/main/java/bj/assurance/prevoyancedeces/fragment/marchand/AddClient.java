package bj.assurance.prevoyancedeces.fragment.marchand;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.kinda.alert.KAlertDialog;

import androidx.fragment.app.FragmentManager;
import bj.assurance.prevoyancedeces.R;
import bj.assurance.prevoyancedeces.activity.Connexion;
import bj.assurance.prevoyancedeces.activity.MarchandMainActivity;
import bj.assurance.prevoyancedeces.fragment.client.Marchands;
import bj.assurance.prevoyancedeces.model.Contrat;
import bj.assurance.prevoyancedeces.retrofit.RetrofitBuildForGetRessource;
import bj.assurance.prevoyancedeces.retrofit.Service.ClientService;
import bj.assurance.prevoyancedeces.retrofit.TokenManager;
import bj.assurance.prevoyancedeces.utils.AccessToken;
import bj.assurance.prevoyancedeces.utils.ApiError;
import bj.assurance.prevoyancedeces.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static androidx.constraintlayout.widget.Constraints.TAG;

public class AddClient extends Fragment {

    private Button cancel, next;

    public AddClient() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_client, container, false);

        init(view);
        onClickListener();

        return view;
    }

    public void init(View view) {
        cancel = view.findViewById(R.id.annuler);
        next = view.findViewById(R.id.suivant);
    }

    private void onClickListener() {
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new ListeClients());
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println(new Gson().toJson(MarchandMainActivity.getContrat()));

                senContrat(TokenManager.getInstance(getActivity().getSharedPreferences("prefs", MODE_PRIVATE)).getToken(),
                        MarchandMainActivity.getContrat());

            }
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_main_marchand, fragment).commit();
    }

    private void senContrat(AccessToken accessToken, Contrat contrat) {

        KAlertDialog pDialog = new KAlertDialog(getContext(), KAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#2d8df8"));
        pDialog.setTitleText("Enregistrement");
        pDialog.setCancelable(false);

        Call<JsonObject> call;
        ClientService service = new RetrofitBuildForGetRessource(accessToken).getRetrofit().create(ClientService.class);
        call = service.create(contrat);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                Log.w(TAG, "onResponse: " + response);

                if (response.isSuccessful()) {
                    System.out.println(response.body());
                    pDialog.dismiss();

                    new KAlertDialog(getContext(), KAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Contrat créer")
                            .setCancelText("Ok")
                            .showCancelButton(true)
                            .setCancelClickListener(new KAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(KAlertDialog kAlertDialog) {
                                    FragmentManager fragmentManager = getFragmentManager();
                                    fragmentManager.beginTransaction().replace(R.id.content_main_marchand, new ListeClients()).commit();

                                    MarchandMainActivity.getTitleFrame().setText(getContext().getResources().getString(R.string.mes_clients));
                                }
                            })
                            .show();
                } else {
                   pDialog.dismiss();
                    new KAlertDialog(getContext(), KAlertDialog.ERROR_TYPE)
                            .setTitleText("Echec")
                            .setContentText("Enregistrement du contrat à échouée")
                            .setCancelText("Ok")
                            .showCancelButton(true)
                            .show();
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                pDialog.dismiss();
                Log.w(TAG, "onFailure: " + t.getMessage());
                //Toast.makeText(getActivity(), t.getMessage()+ t.getCause().getCause().getCause(), Toast.LENGTH_LONG).show();

                new KAlertDialog(getContext(), KAlertDialog.ERROR_TYPE)
                        .setTitleText("Echec")
                        .setContentText("Enregistrement du contrat à échouée")
                        .setCancelText("Ok")
                        .showCancelButton(true)
                        .show();
            }
        });
    }

}
