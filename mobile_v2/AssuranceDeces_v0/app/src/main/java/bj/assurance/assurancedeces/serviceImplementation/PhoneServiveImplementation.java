package bj.assurance.assurancedeces.serviceImplementation;


import com.google.gson.JsonObject;

import java.util.List;

import bj.assurance.assurancedeces.model.customModel.AccessToken;
import bj.assurance.assurancedeces.model.customModel.PhoneList;
import bj.assurance.assurancedeces.retrofit.RetrofitBuildForGetRessource;
import bj.assurance.assurancedeces.service.PhoneService;
import retrofit2.Call;



public class PhoneServiveImplementation implements PhoneService {



    private Call<JsonObject> call;
    private PhoneService phoneService;
    private static JsonObject jsonObject;


    public PhoneServiveImplementation(AccessToken accessToken) {

        this.phoneService = new RetrofitBuildForGetRessource(accessToken).getRetrofit()
                .create(PhoneService.class);

    }

    @Override
    public Call<List<PhoneList>> getPhoneNumberListPrefix() {

        return phoneService.getPhoneNumberListPrefix();

    }


}
