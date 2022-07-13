package bj.assurance.assurancedeces.serviceImplementation;

import com.google.gson.JsonObject;

import java.util.Date;

import bj.assurance.assurancedeces.model.Compte;
import bj.assurance.assurancedeces.model.Contrat;
import bj.assurance.assurancedeces.model.customModel.AccessToken;
import bj.assurance.assurancedeces.retrofit.RetrofitBuildForGetRessource;
import bj.assurance.assurancedeces.service.MarchandService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MarchandServiceImplementation implements MarchandService {


    private Call<JsonObject> call;
    private MarchandService marchandService;





    public MarchandServiceImplementation(AccessToken accessToken) {
        marchandService = new RetrofitBuildForGetRessource(accessToken).getRetrofit()
                            .create(MarchandService.class);
    }




    @Override
    public Call<JsonObject> listMarchand() {

        call = marchandService.listMarchand();
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });

        return null;
    }






    @Override
    public Call<JsonObject> listContratEnAttente(Long id, int page) {

        return marchandService.listContratEnAttente(id, page);

    }





    @Override
    public Call<JsonObject> listClientOfMarchand(Long id) {

        return marchandService.listClientOfMarchand(id);

    }





    @Override
    public Call<JsonObject> listContratofClientOfMarchand(Long idMarchand, Long idClient) {


        call = marchandService.listContratofClientOfMarchand(idMarchand, idClient);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });


        return null;
    }




    @Override
    public Call<JsonObject> listProspectOfMarchand(Long id, int page) {


        return marchandService.listProspectOfMarchand(id, page);

    }







    @Override
    public Call<JsonObject> listTransactionsOfMarchand(Long id) {

        return marchandService.listTransactionsOfMarchand(id);

    }




    @Override
    public Call<JsonObject> listTransactionsOfMarchandbyDate(Long id, Date date) {

        return marchandService.listTransactionsOfMarchandbyDate(id, date);

    }






    @Override
    public Call<JsonObject> allListTransactionsOfMarchand(Long id, int page) {


        return marchandService.allListTransactionsOfMarchand(id, page);
       
    }






    @Override
    public Call<JsonObject> creditVirtuelleOfMarchand(Long id) {

        return marchandService.creditVirtuelleOfMarchand(id);

    }





    @Override
    public Call<JsonObject> historiqueCompteOfMarchand(Long id) {


        call = marchandService.historiqueCompteOfMarchand(id);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });


        return null;
    }





    @Override
    public Call<JsonObject> historiqueCompteOfMarchandByDate(Long id, Date date) {


        return marchandService.historiqueCompteOfMarchandByDate(id, date);


    }







    @Override
    public Call<JsonObject> getCreditAndComissionOfMarchand(Long id) {

        return marchandService.getCreditAndComissionOfMarchand(id);

    }






    @Override
    public Call<JsonObject> transfert(Compte compte) {

        return marchandService.transfert(compte);

    }






    @Override
    public Call<JsonObject> createContrat(Contrat contrat) {
        return marchandService.createContrat(contrat);
    }






}
