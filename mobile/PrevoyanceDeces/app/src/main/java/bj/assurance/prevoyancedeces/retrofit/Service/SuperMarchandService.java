package bj.assurance.prevoyancedeces.retrofit.Service;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface SuperMarchandService {

    @GET("supermarchands/{idSupermarchand}/marchands")
    Call<JsonObject> getMarhands(@Path("idSupermarchand") Long id);

    @GET("supermarchands/{idSupermarchand}/getComptes/{date}")
    Call<JsonObject> getEvolutionsCommission(@Path("idSupermarchand") Long id);

}
