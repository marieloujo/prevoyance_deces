package bj.assurance.assurancedeces.serviceImplementation;

import com.google.gson.JsonObject;

import bj.assurance.assurancedeces.retrofit.RetrofitBuildForGetRessource;
import bj.assurance.assurancedeces.service.SuperMarchandService;
import bj.assurance.assurancedeces.model.customModel.AccessToken;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class SuperMarchandServiceImplementation implements SuperMarchandService {


    private Call<JsonObject> call;
    private SuperMarchandService superMarchandService;



    /**
     * Constructor
     * @param accessToken
     */
    public SuperMarchandServiceImplementation(AccessToken accessToken) {

        superMarchandService = new RetrofitBuildForGetRessource(accessToken).getRetrofit()
                                        .create(SuperMarchandService.class);
    }




    /**
     *
     * @return
     */
    @Override
    public Call<JsonObject> listMarchand() {


        return superMarchandService.listMarchand();

    }



    /**
     *
     * @param id
     * @return
     */
    @Override
    public Call<JsonObject> listMarchandsOfSupermarchand(Long id) {


        return superMarchandService.listMarchandsOfSupermarchand(id);

    }




    @Override
    public Call<JsonObject> getCommissionofSupermarchand(Long id) {


        return superMarchandService.getCommissionofSupermarchand(id);


    }



    /**
     *
     * @param id
     * @return
     */
    @Override
    public Call<JsonObject> getHistoriqueCommissionofSupermarchand(Long id) {


        call = superMarchandService.getHistoriqueCommissionofSupermarchand(id);
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
     * @param id
     * @param date
     * @return
     */
    @Override
    public Call<JsonObject> getHistoriqueCommissionofSupermarchandbyDate(Long id, String date) {



        call = superMarchandService.getHistoriqueCommissionofSupermarchandbyDate(id, date);
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



}
