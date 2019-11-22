package bj.assurance.prevoyancedeces.retrofit.Service;

import bj.assurance.prevoyancedeces.model.Client;
import bj.assurance.prevoyancedeces.model.Utilisateur;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ClientService {

    @GET("/clients/1")
    Call<Client> getClientbyId();

}
