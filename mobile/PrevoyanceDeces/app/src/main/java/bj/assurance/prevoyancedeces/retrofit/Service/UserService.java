package bj.assurance.prevoyancedeces.retrofit.Service;

import com.google.gson.JsonObject;

import java.util.List;

import bj.assurance.prevoyancedeces.model.Client;
import bj.assurance.prevoyancedeces.utils.AccessToken;
import bj.assurance.prevoyancedeces.model.Message;
import bj.assurance.prevoyancedeces.model.Utilisateur;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserService {

    @POST("register")
    @FormUrlEncoded
    Call<AccessToken> register(@Field("name") String name, @Field("email") String email, @Field("password") String password);

    /**
     *
     * @param username
     * @param password
     * @return
     */
    @POST("login")
    @FormUrlEncoded
    Call<JsonObject> login(@Field("login") String username, @Field("password") String password);

    @POST("refresh")
    @FormUrlEncoded
    Call<AccessToken> refresh(@Field("refresh_token") String refreshToken);

    /**
     *
     * @return
     */
    @GET("user")
    Call<JsonObject> getauthenticateUser();



    @GET("users/{idUser}/conversations")
    Call<JsonObject> getMessageofUser(@Path("idUser") Long id);


    @GET("users/{idUser}/notifications")
    Call<JsonObject> getNotification(@Path("idUser") Long id);




    @GET("users/{number}/telephone")
    Call<JsonObject> findbyTelephone(@Path("number") String id);

    @GET("users/{user}")
    Call<Utilisateur> findbyName_();

    @GET("departements")
    Call<JsonObject> getDepartement();


}
