package bj.assurance.assurancedeces.serviceImplementation;


import bj.assurance.assurancedeces.service.SmsService;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SmsServiceImplementation implements SmsService {


    private static Retrofit retrofit;
    private static final String BASE_URL = "http://oceanicsms.com/api/http/";



    @Override
    public Call<String> sendSms(String user, String password, String from, String to, String text, String api) {

        Call<String> call;

        return getRetrofitInstance().create(SmsService.class).sendSms(
                user, password, from, to, text, api
        );

    }




    private static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }



}
