package bj.assurance.assurancedeces.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import bj.assurance.assurancedeces.model.Message;
import bj.assurance.assurancedeces.model.Userable;
import bj.assurance.assurancedeces.model.Utilisateur;
import bj.assurance.assurancedeces.model.customModel.AccessToken;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


/**
 * Interface contain list of route of api can help to access table User in database.
 * This routes return jsonObject over the format:
 *          errors: {message : "error message"}
 *          success: {message : "success message"}
 *          success: {data: data}
 *          success: {JsonObject}
 *
 */
public interface UtilisateurService {


    /**
     * route of api for get list of all user exist in database.
     * @return
     */
    @GET("users")
    Call<JsonObject> listUser();



    /**
     * route of api for get auth user.
     * @return
     */
    @GET("user")
    Call<JsonObject> getAuthentificateUser();


    /**
     * route of api for get user by your phone number.
     * @param phoneNumber
     * @return
     */
    @GET("users/{phoneNumber}/telephone")
    Call<JsonObject> findUserbyPhoneNumber(@Path("phoneNumber") String phoneNumber);


    /**
     * route of api for get user by your name.
     * @param nom
     * @return
     */
    @GET("users/{nom}/nom")
    Call<JsonObject> findUserbyName(@Path("nom") String nom);



    /**
     * route of api get list converstion of user by your id.
     * @param id
     * @return
     */
    @GET("users/{idUser}/conversations")
    Call<JsonObject> listConversationsOfUser(@Path("idUser") Long id);



    /**
     * route of api get list notifications of user by your id.
     * @param id
     * @return
     */
    @GET("users/{idUser}/notifications")
    Call<JsonObject> listNotificationsOfUser(@Path("idUser") Long id);



    /**
     * route of api to authentificate user by your username and your password.
     * @param username
     * @param password
     * @return
     */
    @POST("login")
    @FormUrlEncoded
    Call<JsonObject> login(@Field("login") String username, @Field("password") String password);



    /**
     * route of api to log out user.
     * @return
     */
    @POST("logout")
    Call<JsonObject> logout();



    Call<AccessToken> refresh(String s);




    @POST("users/addProspects")
    Call<JsonObject> createPospect(@Body Utilisateur prospect);



    /*Route::get('/{user}/notifications','Api\UserController@notifications');
    Route::get('/{user}/markReadNotifications','Api\UserController@markReadNotifications');
    Route::get('/{user}/unReadNotifications','Api\UserController@unReadNotifications');
    Route::get('/{user}/getNotifications','Api\UserController@getNotifications');
    Route::post('/{user}/notifications','Api\UserController@sendNotifications');*/




    @GET("users/{idUser}/notifications")
    Call<JsonObject> listNotification(@Path("idUser") Long id);



    @GET("users/{idUser}/markReadNotifications")
    Call<JsonObject> markReadNotifications(@Path("idUser") Long id);



    @GET("users/{idUser}/unReadNotifications")
    Call<JsonArray> unReadNotifications(@Path("idUser") Long id);




    @GET("users/{idUser}/getNotifications")
    Call<JsonArray> getNotifications(@Path("idUser") Long id);




    @POST("users/{idUser}/notifications")
    Call<JsonObject> createNotification(@Body Message message, @Path("idUser") Long id);






    @POST("users/checkUser")
    Call<JsonObject> findCompte(@Body Userable userable);




}
