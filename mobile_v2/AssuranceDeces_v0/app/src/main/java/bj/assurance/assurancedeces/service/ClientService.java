package bj.assurance.assurancedeces.service;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


/**
 * Interface contain list of route of api can help to access table Client in database.
 *  * This routes return jsonObject over the format:
 *  *          errors: {message : "error message"}
 *  *          success: {message : "success message"}
 *  *          success: {data: data}
 *  *          success: {JsonObject}
 */
public interface ClientService {


    /**
     *
     * @return
     */
    @GET("clients")
    Call<JsonObject> listClients();



    /**
     *
     * @param id
     * @return
     */
    @GET("clients/{idClient}/lastContrats")
    Call<JsonObject> lastContratsOfClient(@Path("idClient") Long id);



    /**
     *
     * @param id
     * @return
     */
    @GET("clients/{idClient}/contrats")
    Call<JsonObject> listContratOfClients(@Path("idClient") Long id);

}
