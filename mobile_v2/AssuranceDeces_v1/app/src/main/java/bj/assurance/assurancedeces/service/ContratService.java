package bj.assurance.assurancedeces.service;

import com.google.gson.JsonObject;

import bj.assurance.assurancedeces.model.Portefeuille;
import bj.assurance.assurancedeces.model.Utilisateur;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


/**
 * Interface contain list of route of api can help to access table Contrat in database.
 *  * This routes return jsonObject over the format:
 *  *          errors: {message : "error message"}
 *  *          success: {message : "success message"}
 *  *          success: {data: data}
 *  *          success: {JsonObject}
 */
public interface ContratService {


    /**
     *
     * @return
     */
    @GET("contrats")
    Call<JsonObject> listContrat();



    /**
     *
     * @param referenceContrat
     * @return
     */
    @GET("contrats/{referenceContrat}/contrat")
    Call<JsonObject> findContratbyReference(@Path("referenceContrat") String referenceContrat);



    @POST("portefeuilles")
    Call<JsonObject> depot(@Body Portefeuille portefeuille);




    @POST("validation")
    Call<JsonObject> validation(@Body Utilisateur utilisateur);



    @GET("validationAssurer/{idAssurer}")
    Call<JsonObject> validationAssurer(@Path("idAssurer") Long id);

}
