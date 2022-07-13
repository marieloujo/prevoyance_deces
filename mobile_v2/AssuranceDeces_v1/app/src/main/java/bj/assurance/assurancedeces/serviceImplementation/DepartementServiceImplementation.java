package bj.assurance.assurancedeces.serviceImplementation;

import com.google.gson.JsonObject;

import bj.assurance.assurancedeces.model.customModel.AccessToken;
import bj.assurance.assurancedeces.retrofit.RetrofitBuildForGetRessource;
import bj.assurance.assurancedeces.service.DepartementService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DepartementServiceImplementation  implements DepartementService {


    private Call<JsonObject> call;
    private DepartementService departementService;


    /**
     * Constructor
     * @param accessToken
     */
    public DepartementServiceImplementation(AccessToken accessToken) {
        departementService = new RetrofitBuildForGetRessource(accessToken).getRetrofit().
                                                create(DepartementService.class);
    }



    /**
     *
     * @return
     */
    @Override
    public Call<JsonObject> listDepartement() {

        return departementService.listDepartement();

    }



    /**
     *
     * @param idDepartement
     * @return
     */
    @Override
    public Call<JsonObject> listCommunesByDepartement(Long idDepartement) {

        return departementService.listCommunesByDepartement(idDepartement);

    }



    /**
     *
     * @param idDepartement
     * @param idCommune
     * @return
     */
    @Override
    public Call<JsonObject> listMarchandByCommuneOfDepartement(Long idDepartement, Long idCommune) {

        return departementService.listMarchandByCommuneOfDepartement(idDepartement, idCommune);

    }




}
