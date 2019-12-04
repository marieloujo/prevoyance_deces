package bj.assurance.prevoyancedeces.retrofit.Service;

import java.util.List;

import bj.assurance.prevoyancedeces.utils.AccessToken;
import bj.assurance.prevoyancedeces.model.Message;
import bj.assurance.prevoyancedeces.model.Utilisateur;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

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
    Call<AccessToken> login(@Field("login") String username, @Field("password") String password);

    @POST("refresh")
    @FormUrlEncoded
    Call<AccessToken> refresh(@Field("refresh_token") String refreshToken);

    /**
     *
     * @return
     */
    @GET("user")
    Call<Utilisateur> getauthenticateUser();

    @GET("")
    Call<List<Message>> getMessageofUser();

    @GET("users/notifications")
    Call<List<Message>> getNotification();



}
