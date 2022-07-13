package bj.assurance.assurancedeces.service;

import com.google.gson.JsonObject;

import bj.assurance.assurancedeces.model.Compte;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CompteService {


    @POST("marchands/{idMarchand}/transfert")
    Call<JsonObject> transferCommission(@Body Compte compte, @Path("idMarchand") Long id);


}
