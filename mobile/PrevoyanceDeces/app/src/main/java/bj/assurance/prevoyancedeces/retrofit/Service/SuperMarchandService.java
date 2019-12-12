package bj.assurance.prevoyancedeces.retrofit.Service;

import com.google.gson.JsonObject;

import java.util.Date;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface SuperMarchandService {

    @GET("supermarchands/{idSupermarchand}/marchands")
    Call<JsonObject> getMarhands(@Path("idSupermarchand") Long id);

    @GET("supermarchands/{idSupermarchand}/getCompte/")
    Call<JsonObject> getCommission(@Path("idSupermarchand") Long id);

    @GET("supermarchands/{idSupermarchand}/getComptes/")
    Call<JsonObject> getEvolutionsCommission(@Path("idSupermarchand") Long id);

    @GET("supermarchands/{idSupermarchand}/getComptes/{date}")
    Call<JsonObject> getEvolutionsCommissionbyDate(@Path("idSupermarchand") Long id, @Path("date")Date date);



}
