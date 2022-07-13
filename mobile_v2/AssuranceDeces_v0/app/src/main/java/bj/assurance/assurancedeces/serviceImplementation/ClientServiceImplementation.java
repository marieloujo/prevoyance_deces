package bj.assurance.assurancedeces.serviceImplementation;

import com.google.gson.JsonObject;

import bj.assurance.assurancedeces.retrofit.RetrofitBuildForGetRessource;
import bj.assurance.assurancedeces.service.ClientService;
import bj.assurance.assurancedeces.utils.CatchApiError;
import bj.assurance.assurancedeces.model.customModel.AccessToken;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ClientServiceImplementation {


    private Call<JsonObject> call;
    private ClientService clientService;
    private static JsonObject jsonObject;



    /**
     *
     * @param accessToken
     */
    public ClientServiceImplementation(AccessToken accessToken) {
        clientService = new RetrofitBuildForGetRessource(accessToken).getRetrofit()
                                            .create(ClientService.class);
    }



    /**
     *
     * @return
     */
    public JsonObject listClients() {
        call = clientService.listClients();
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                if (response.isSuccessful()) {
                    jsonObject = response.body();
                } else {

                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                t.printStackTrace();
            }
        });
        return jsonObject;
    }



    /**
     *
     *
     * @param id
     * @return
     */
    public Call<JsonObject> lastContratsOfClient(Long id) {


        return clientService.lastContratsOfClient(id);

    }



    /**
     * @param id
     * @return
     */
    public Call<JsonObject> listContratOfClients(Long id) {
        call = clientService.listContratOfClients(id);
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
