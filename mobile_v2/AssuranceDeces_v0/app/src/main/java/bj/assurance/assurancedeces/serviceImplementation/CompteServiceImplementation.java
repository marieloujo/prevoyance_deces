package bj.assurance.assurancedeces.serviceImplementation;

import android.content.Context;

import com.google.gson.JsonObject;

import androidx.fragment.app.FragmentActivity;
import bj.assurance.assurancedeces.model.Compte;
import bj.assurance.assurancedeces.retrofit.RetrofitBuildForGetRessource;
import bj.assurance.assurancedeces.service.CompteService;
import bj.assurance.assurancedeces.utils.token.TokenManager;
import retrofit2.Call;

import static android.content.Context.MODE_PRIVATE;



public class CompteServiceImplementation implements CompteService {


    private Context context;
    private CompteService compteService;


    public CompteServiceImplementation(Context context) {

        this.context = context;

        new RetrofitBuildForGetRessource(
                TokenManager.getInstance(((FragmentActivity) context)
                        .getSharedPreferences("prefs", MODE_PRIVATE))
                        .getToken()
        );
        compteService = RetrofitBuildForGetRessource.getRetrofit()
                .create(CompteService.class);

    }

    @Override
    public Call<JsonObject> transferCommission(Compte compte, Long id) {

        return compteService.transferCommission(compte, id);

    }


}
