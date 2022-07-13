package bj.assurance.assurancedeces.service;

import com.google.gson.JsonObject;

import java.util.Date;

import bj.assurance.assurancedeces.model.Compte;
import bj.assurance.assurancedeces.model.Contrat;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


/**
 * Interface contain list of route of api can help to access table Marchand in database.
 *  * This routes return jsonObject over the format:
 *  *          errors: {message : "error message"}
 *  *          success: {message : "success message"}
 *  *          success: {data: data}
 *  *          success: {JsonObject}
 */
public interface MarchandService {


    /**
     *
     * @return
     */
    @GET("marchands")
    Call<JsonObject> listMarchand();



    @GET("marchands/{idMarchand}/getWaitingContrats")
    Call<JsonObject> listContratEnAttente(@Path("idMarchand") Long id, @Query("page") int page);



    /**
     *
     * @param id
     * @return
     */
    @GET("marchands/{idMarchand}/clients")
    Call<JsonObject> listClientOfMarchand(
            @Path("idMarchand") Long id
    );


    /**
     *
     * @param idMarchand
     * @param idClient
     * @return
     */
    @GET("marchands/{idMarchand}/clients/{idClient}/contrats")
    Call<JsonObject> listContratofClientOfMarchand(@Path("idMarchand") Long idMarchand,
                                                   @Path("idClient") Long idClient
    );


    /**
     *
     * @param id
     * @return
     */
    @GET("marchands/{idMarchand}/prospects")
    Call<JsonObject> listProspectOfMarchand(@Path("idMarchand") Long id, @Query("page") int page);


    /**
     *
     * @param id
     * @return
     */
    @GET("marchands/{idMarchand}/transactions")
    Call<JsonObject> listTransactionsOfMarchand(@Path("idMarchand") Long id);


    /**
     *
     * @param id
     * @param date
     * @return
     */
    @GET("marchands/{idMarchand}/transactions/{date}")
    Call<JsonObject> listTransactionsOfMarchandbyDate(@Path("idMarchand") Long id,
                                                      @Path("date") Date date
    );


    /**
     *
     * @param id
     * @return
     */
    @GET("marchands/{idMarchand}/getAllTransactions")
    Call<JsonObject> allListTransactionsOfMarchand(@Path("idMarchand") Long id,
                                                   @Query("page") int page
    );




    @GET("marchands/{idMarchand}/getCompte")
    Call<JsonObject> creditVirtuelleOfMarchand(@Path("idMarchand") Long id);





    @GET("marchands/{idMarchand}/getComptes")
    Call<JsonObject> historiqueCompteOfMarchand(@Path("idMarchand") Long id);



    @GET("marchands/{idMarchand}/getComptes/{date}")
    Call<JsonObject> historiqueCompteOfMarchandByDate(@Path("idMarchand") Long id,
                                                      @Path("date") Date date
    );



    @GET("marchands/{idMarchand}/getMarchand")
    Call<JsonObject> getCreditAndComissionOfMarchand(@Path("idMarchand") Long id);



    @POST("comptes")
    Call<JsonObject> transfert(@Body Compte compte);


    @POST("contrats")
    Call<JsonObject> createContrat(@Body Contrat contrat);

}
