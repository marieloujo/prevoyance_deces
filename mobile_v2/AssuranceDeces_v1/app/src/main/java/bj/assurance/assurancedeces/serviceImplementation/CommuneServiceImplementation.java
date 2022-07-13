package bj.assurance.assurancedeces.serviceImplementation;

import com.google.gson.JsonObject;

import bj.assurance.assurancedeces.model.customModel.AccessToken;
import bj.assurance.assurancedeces.retrofit.RetrofitBuildForGetRessource;
import bj.assurance.assurancedeces.service.CommuneService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CommuneServiceImplementation implements CommuneService {


    private Call<JsonObject> call;
    private CommuneService communeService;



    /**
     *
     * @param accessToken
     */
    public CommuneServiceImplementation(AccessToken accessToken) {
        communeService = new RetrofitBuildForGetRessource(accessToken).getRetrofit()
                .create(CommuneService.class);
    }



    /**
     *
     * @return
     */
    @Override
    public Call<JsonObject> listCommune(int page) {

        return communeService.listCommune(page);

    }



    /**
     *
     * @param idCommune
     * @return
     */
    @Override
    public Call<JsonObject> listUserOfCommune(Long idCommune) {

        call = communeService.listUserOfCommune(idCommune);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });

        return null;
    }



    /**
     *
     * @param idCommune
     * @return
     */
    @Override
    public Call<JsonObject> departementOfCommune(Long idCommune) {

        return communeService.departementOfCommune(idCommune);

    }


}
