package bj.assurance.assurancedeces.service;

import java.util.List;

import bj.assurance.assurancedeces.model.customModel.PhoneList;
import retrofit2.Call;
import retrofit2.http.GET;

public interface PhoneService {


    @GET("users/validatePhone")
    Call<List<PhoneList>> getPhoneNumberListPrefix();


}
