package bj.assurance.assurancedeces.retrofit;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;




public class RetrofitClientInstance {


    private static final String BASE_URL = "http://192.168.8.100:8000/api/";


    private final static OkHttpClient client = buildClient();
    private final static Retrofit retrofit = buildRetrofit(client);



    private static OkHttpClient buildClient(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();

                        Request.Builder builder = request.newBuilder()
                                .addHeader("Accept", "application/json")
                                .addHeader("Content-Type", "application/json");

                        request = builder.build();

                        return chain.proceed(request);

                    }
                });

        return builder.build();
    }




    private static Retrofit buildRetrofit(OkHttpClient client){
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