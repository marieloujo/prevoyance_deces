package bj.assurance.assurancedeces.serviceImplementation;

import com.google.gson.JsonObject;

import bj.assurance.assurancedeces.model.Portefeuille;
import bj.assurance.assurancedeces.model.Utilisateur;
import bj.assurance.assurancedeces.model.customModel.AccessToken;
import bj.assurance.assurancedeces.retrofit.RetrofitBuildForGetRessource;
import bj.assurance.assurancedeces.service.ContratService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ContratServiceImplementation implements ContratService {


    private Call<JsonObject> call;
    private ContratService contratService;


    /**
     *
     * @param accessToken
     */
    public ContratServiceImplementation(AccessToken accessToken) {
        contratService = new RetrofitBuildForGetRessource(accessToken).getRetrofit()
                                                                      .create(ContratService.class);
    }



    /**
     *
     * @return
     */
    @Override
    public Call<JsonObject> listContrat() {

        call = contratService.listContrat();
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
     * @param referenceContrat
     * @return
     */
    @Override
    public Call<JsonObject> findContratbyReference(String referenceContrat) {

        return contratService.findContratbyReference(referenceContrat);

    }




    @Override
    public Call<JsonObject> depot(Portefeuille portefeuille) {
        return contratService.depot(portefeuille);
    }





    @Override
    public Call<JsonObject> validation(Utilisateur utilisateur) {

        return contratService.validation(utilisateur);

    }





    @Override
    public Call<JsonObject> validationAssurer(Long id) {

        return contratService.validationAssurer(id);
    }




}
