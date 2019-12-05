package bj.assurance.prevoyancedeces.retrofit.Service;

import com.google.gson.JsonObject;

import java.util.List;

import bj.assurance.prevoyancedeces.model.Commune;
import bj.assurance.prevoyancedeces.model.Compte;
import bj.assurance.prevoyancedeces.model.Contrat;
import bj.assurance.prevoyancedeces.model.Marchand;
import bj.assurance.prevoyancedeces.model.Portefeuille;
import bj.assurance.prevoyancedeces.model.pagination.OutputPaginate;
import retrofit2.Call;
import retrofit2.http.GET;
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
    Call<List<Compte>> getEvolution();


    @GET("marchands/{idMarchand}/getCompte")
    Call<JsonObject> getCompte(@Path("idMarchand") Long id);


    @GET("marchands/{idMarchand}/transactions")
    Call<OutputPaginate> getTransactionsForWeek(@Path("idMarchand") Long id, @Query("page") int page);


    @GET("marchands/{idMarchand}/clients")
    Call<OutputPaginate> getClients(@Path("idMarchand") Long id, @Query("page") int page);


    @GET("marchands/{idMarchand}/clients/{idClient}/contrats")
    Call<OutputPaginate> getConrattoClient(@Path("idMarchand") Long idMarchand, @Path("idClient") Long idClient, @Query("page") int page);


    @GET("marchands/{idMarchand}/getAllTransactions")
    Call<OutputPaginate> getTransactions(@Path("idMarchand") Long id, @Query("page") int page);

}
