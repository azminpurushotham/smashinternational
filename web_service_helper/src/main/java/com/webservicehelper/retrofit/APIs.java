package com.webservicehelper.retrofit;


import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by appzoc on 20/1/16.
 */
public interface APIs {

    public static final String BASE_URL = "http://smash.codinol.in/";
    ///MODEL////

    @GET("demo/api/users/registration_data")
    Call<JsonObject> getRegistrationDataOne();

//    @FormUrlEncoded
//    @POST("/api/smash/api/profile/login/")
//    Call<JsonObject> postLogin(@Field("username") String username,
//                               @Field("password") String password,
//                               @Field("token") String token);

    ////    @FormUrlEncoded
    @POST("/api/smash/api/profile/login/")
    Call<JsonObject> postLogin(@Query("username") String username,
                               @Query("password") String password,
                               @Query("token") String token);

    @GET("mobile/termsAndConditions")
    Call<JSONObject> getTermsAndConditions();


    @GET("mobile/termsAndConditions")
    Call<JsonElement> getTest();


    @FormUrlEncoded
    @POST("mobile/termsAndConditions")
    Call<JSONObject> postFbForgotPassword(@Field("user_id") String user_id);


    @FormUrlEncoded
    @POST("mobile/termsAndConditions")
    Call<JSONObject> postFbLogin(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("mobile/termsAndConditions")
    Call<JsonObject> postLogout(@Field("user_id") String user_id,
                                @Field("tocken") String tocken);


    @FormUrlEncoded
    @POST("mobile/termsAndConditions")
    Call<JSONObject> postRegistration(@Field("user_id") String user_id);


    @FormUrlEncoded
    @POST("mobile/termsAndConditions")
    Call<JSONObject> postLogin(@Field("user_id") String user_id);


    @GET("mobile/aboutUs")
    Call<JSONObject> getAboutUs();


    @GET("mobile/aboutUs/{path}")
    Call<JsonObject> getPropertyListing(@Path("path") String path);

    @GET("/api/smash/api/work/work_list/{user_id}/{token}")
    Call<JsonObject> getScheduledWorks(@Path("user_id") String user_id,
                                       @Path("token") String token);

    @GET("api/work/work_list/{path}")
    Call<JsonObject> getScheduledWorks();

    @GET("api/smash/api/work/work_details/{id}/{user_id}/{token}")
    Call<JsonObject> getScheduledWorksDetail(@Path("id") String id, @Path("user_id") String user_id, @Path("token") String token);

    @FormUrlEncoded
    @POST("api/smash/api/lead/add_lead/")
    Call<JsonObject> postNewLead(@Field("user_id") String user_id, @Field("token") String token, @Field("status") String status,
                                 @Field("customer_name") String customer_name, @Field("branch_name") String branch_name,
                                 @Field("telephone_number") String telephone_number, @Field("email") String email,
                                 @Field("sms_no") String sms_no, @Field("address") String address,
                                 @Field("pending_amount") String pending_amount, @Field("collection_amount") String collection_amount,
                                 @Field("currency") String currency, @Field("latitude") String latitude,
                                 @Field("longtitude") String longitude, @Field("bill_id") String bill_id);

    @FormUrlEncoded
    @POST("api/smash/api/work/work_update/")
    Call<JsonObject> updateWorkStatus(@Field("user_id") String user_id, @Field("token") String token, @Field("branch_id") String branch_id,
                                      @Field("email") String email, @Field("sms_no") String sms_no,
                                      @Field("branch_name") String branch_name, @Field("address") String address,
                                      @Field("telephone_number") String telephone_number, @Field("status") String status,
                                      @Field("collection_amount") String collection_amount,
                                      @Field("reason") String reason, @Field("bill_id") String bill_id);
}
