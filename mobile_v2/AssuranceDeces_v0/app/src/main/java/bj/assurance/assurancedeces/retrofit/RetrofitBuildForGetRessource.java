package bj.assurance.assurancedeces.retrofit;

import java.io.IOException;

import bj.assurance.assurancedeces.model.customModel.AccessToken;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitBuildForGetRessource {

    // https://prevoyancedeces.000webhostapp.com

    private static final String BASE_URL = "http://192.168.43.74:8000/api/";
    private static AccessToken token;

    private final static OkHttpClient client = buildClient();
    private final static Retrofit retrofit = buildRetrofit(client);



    public RetrofitBuildForGetRessource(AccessToken token) {
        this.token = token;
    }



    private static OkHttpClient buildClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();

                        Request.Builder builder = request.newBuilder()
                                .addHeader("Accept", "application/json")
                                .addHeader("Content-Type", "application/json")
                                .addHeader("Authorization", "Bearer " + token.getAccessToken());

                        request = builder.build();

                        return chain.proceed(request);

                    }
                });

        return builder.build();

    }



    private static Retrofit buildRetrofit(OkHttpClient client) {

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }



    public static <T> T createService(Class<T> service){
        return retrofit.create(service);
    }



    public static Retrofit getRetrofit() {
        return retrofit;
    }

}
