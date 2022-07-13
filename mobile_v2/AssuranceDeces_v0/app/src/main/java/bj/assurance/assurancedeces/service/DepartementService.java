package bj.assurance.assurancedeces.service;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


/**
 * Interface contain list of route of api can help to access table Departement in database.
 *  * This routes return jsonObject over the format:
 *  *          errors: {message : "error message"}
 *  *          success: {message : "success message"}
 *  *          success: {data: data}
 *  *          success: {JsonObject}
 */
public interface DepartementService {


    /**
     *
     * @return
     */
    @GET("departements")
    Call<JsonObject> listDepartement();





    /**
     *
     * @param idDepartement
     * @return
     */
    @GET("departements/{idDepartement}/communes")
    Call<JsonObject> listCommunesByDepartement(@Path("idDepartement") Long idDepartement);





    /**
     *
     * @param idDepartement
     * @param idCommune
     * @return
     */
    @GET("departements/{idDepartement}/communes/{idCommune}/marchands")
    Call<JsonObject> listMarchandByCommuneOfDepartement(@Path("idDepartement") Long idDepartement,
                                                        @Path("idCommune") Long idCommune
    );




}
