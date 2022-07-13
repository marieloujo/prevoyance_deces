package bj.assurance.assurancedeces.service;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SmsService {

    /*
    http://oceanicsms.com/api/http/sendmsg.php?user=branlycaele&password=branlycaele&from=Prevoyance&to=22967266867&text=Salut!!&api=5617
     */

    @GET("sendmsg.php")
    Call<String> sendSms(@Query("user") String user,@Query("password") String password,
                        @Query("from") String from, @Query("to") String to, @Query("text") String text,
                        @Query("api") String api);



}
