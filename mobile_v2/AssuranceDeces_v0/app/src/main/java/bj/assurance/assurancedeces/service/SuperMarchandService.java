package bj.assurance.assurancedeces.service;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
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


    /**
     *
     * @return
     */
    @GET("supermarchands")
    Call<JsonObject> listMarchand();


    /**
     *
     * @param id
     * @return
     */
    @GET("supermarchands/{idSupermarchands}/marchands")
    Call<JsonObject> listMarchandsOfSupermarchand(@Path("idSupermarchands") Long id);


    /**
     *
     * @param id
     * @return
     */
    @GET("supermarchands/{idSupermarchands}/getCompte")
    Call<JsonObject> getCommissionofSupermarchand(@Path("idSupermarchands") Long id);


    /**
     *
     * @param id
     * @return
     */
    @GET("supermarchands/{idSupermarchands}/getComptes")
    Call<JsonObject> getHistoriqueCommissionofSupermarchand(@Path("idSupermarchands") Long id);


    /**
     *
     * @param id
     * @param date
     * @return
     */
    @GET("supermarchands/{idSupermarchands}/getComptes/{date}")
    Call<JsonObject> getHistoriqueCommissionofSupermarchandbyDate(@Path("idSupermarchands") Long id, @Path("date") String date);


}
