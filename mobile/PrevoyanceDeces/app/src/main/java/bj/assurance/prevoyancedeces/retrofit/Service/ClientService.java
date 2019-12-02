package bj.assurance.prevoyancedeces.retrofit.Service;

import com.google.gson.JsonObject;

import bj.assurance.prevoyancedeces.model.Client;
import bj.assurance.prevoyancedeces.model.Contrat;
import bj.assurance.prevoyancedeces.model.Departement;
import bj.assurance.prevoyancedeces.model.Utilisateur;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ClientService {

    /**
     *
     * @param id
     * @return liste des contrats d'un utilisateur
     */
    @GET("clients/{idClient}/contrats")
    Call<Client> getClientbyId(@Path("idClient") Long id);

    @GET("users/marchands/commune")
    Call<Departement> getMarchandsinClient();

    @POST("contrats")
    Call<JsonObject> create(@Body Contrat contrat);

    @GET("")
    Call<Client> findbyId(@Path("idClient") Long id);

}
