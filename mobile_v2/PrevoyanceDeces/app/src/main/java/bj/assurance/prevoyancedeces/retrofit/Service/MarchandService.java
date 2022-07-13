package bj.assurance.prevoyancedeces.retrofit.Service;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonObject;

import java.util.Date;
import java.util.List;

import bj.assurance.prevoyancedeces.model.Client;
import bj.assurance.prevoyancedeces.model.Commune;
import bj.assurance.prevoyancedeces.model.Compte;
import bj.assurance.prevoyancedeces.model.Contrat;
import bj.assurance.prevoyancedeces.model.Marchand;
import bj.assurance.prevoyancedeces.model.Portefeuille;
import bj.assurance.prevoyancedeces.model.Utilisateur;
import bj.assurance.prevoyancedeces.model.pagination.OutputPaginate;
import bj.assurance.prevoyancedeces.utils.ErrorResponse;
import bj.assurance.prevoyancedeces.utils.ValidationEror;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MarchandService {

    @GET("marchands/{idMarchand}/contrats")
    Call<Marchand> getMarchanbyId(@Path("idMarchand") Long id);


    @GET("users/usereable")
    Call<Marchand> findById();

    @GET("departements/{idDepartement}/communes")
    Call<List<Commune>> getCommunebyDepartement(@Path("idDepartement") Long id);


    @GET("marchands/{idMarchand}/getComptes")
    Call<JsonObject> getEvolution(@Path("idMarchand") Long id);


    @GET("marchands/{idMarchand}/getCompte")
    Call<JsonObject> getCompte(@Path("idMarchand") Long id);


    @GET("marchands/{idMarchand}/transactions")
    Call<JsonObject> getTransactionsForWeek(@Path("idMarchand") Long id, @Query("page") int page);


    @GET("marchands/{idMarchand}/clients")
    Call<JsonObject> getClients(@Path("idMarchand") Long id, @Query("page") int page);

    @GET("marchands/{idMarchand}/clients/{idClient}/contrats")
    Call<JsonObject> getConrattoClient(@Path("idMarchand") Long idMarchand, @Path("idClient") Long idClient, @Query("page") int page);


    @GET("marchands/{idMarchand}/getAllTransactions")
    Call<OutputPaginate> getTransactions(@Path("idMarchand") Long id, @Query("page") int page);

    @GET("marchands/{idMarchand}/prospects")
    Call<JsonObject> getProspects(@Path("idMarchand") Long id);

    @POST("portefeuilles")
    Call<JsonObject> depot(@Body Portefeuille portefeuille);

    @GET("contrats/{reference}/contrat")
    Call<JsonObject> findbyContrat(@Path("reference") String reference);

    @GET("marchands/{idMarchand}/transactions/{date}")
    Call<JsonObject> getTransactionbyDate(@Path("idMarchand") Long id, @Path("date") Date date);


    @POST("users/addProspects")
    Call<JsonObject> createPospect(@Body Utilisateur prospect);

    @POST("comptes")
    Call<JsonObject> tranfertCommission(@Body Compte compte);

    @POST("")
    Call<JsonObject> getMarchandbyMatricule(@Body String matricule);



    @POST("validation")
    Call<ErrorResponse> validation(@Body Utilisateur utilisateur);



}
