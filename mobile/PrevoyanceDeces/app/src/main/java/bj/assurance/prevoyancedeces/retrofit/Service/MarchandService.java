package bj.assurance.prevoyancedeces.retrofit.Service;

import java.util.List;

import bj.assurance.prevoyancedeces.model.Client;
import bj.assurance.prevoyancedeces.model.Marchand;
import bj.assurance.prevoyancedeces.model.Portefeuille;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface MarchandService {

    @GET("marchand/{idMarchand}/contrats")
    Call<Marchand> getMarchanbyId(@Path("idMarchand") Long id);


    @GET("")
    Call<Marchand> findById(@Path("idMarchand") Long id);

    @GET("")
    Call<List<Portefeuille>> getTransaction(@Path("idContrat") Long id);

}
