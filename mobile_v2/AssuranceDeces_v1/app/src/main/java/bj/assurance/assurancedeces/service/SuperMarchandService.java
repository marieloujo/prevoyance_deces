package bj.assurance.assurancedeces.service;

import com.google.gson.JsonObject;

import java.util.Date;

import bj.assurance.assurancedeces.model.Marchand;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


/**
 * Interface contain list of route of api can help to access table Supermarchand in database.
 *  * This routes return jsonObject over the format:
 *  *          errors: {message : "error message"}
 *  *          success: {message : "success message"}
 *  *          success: {data: data}
 *  *          success: {JsonObject}
 */
public interface SuperMarchandService {





    @GET("supermarchands")
    Call<JsonObject> listMarchand();





    @GET("supermarchands/{idSupermarchands}/marchands")
    Call<JsonObject> listMarchandsOfSupermarchand(@Path("idSupermarchands") Long id);





    @GET("supermarchands/{idSupermarchands}/getCompte")
    Call<JsonObject> getCommissionofSupermarchand(@Path("idSupermarchands") Long id);





    @GET("supermarchands/{idSupermarchands}/getComptes")
    Call<JsonObject> getHistoriqueCommissionofSupermarchand(@Path("idSupermarchands") Long id);





    @GET("supermarchands/{idSupermarchands}/getComptes/{date}")
    Call<JsonObject> getHistoriqueCommissionofSupermarchandbyDate(@Path("idSupermarchands") Long id, @Path("date") Date date);







    @POST("marchands")
    Call<JsonObject> createMarchand(@Body Marchand marchand);






}
