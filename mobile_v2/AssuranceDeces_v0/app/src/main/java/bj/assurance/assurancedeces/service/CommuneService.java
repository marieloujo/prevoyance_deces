package bj.assurance.assurancedeces.service;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


/**
 * Interface contain list of route of api can help to access table Commune in database.
 *  * This routes return jsonObject over the format:
 *  *          errors: {message : "error message"}
 *  *          success: {message : "success message"}
 *  *          success: {data: data}
 *  *          success: {JsonObject}
 */
public interface CommuneService {


    /**
     *
     * @return
     */
    @GET("communes")
    Call<JsonObject> listCommune();


    /**
     *
     * @param idCommune
     * @return
     */
    @GET("communes/{idCommune}/users")
    Call<JsonObject> listUserOfCommune(@Path("idCommune") Long idCommune);


    /**
     *
     * @param idCommune
     * @return
     */
    @GET("communes/{idCommune}/departement")
    Call<JsonObject> departementOfCommune(@Path("idCommune") Long idCommune);


}
