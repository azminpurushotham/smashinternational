package com.webservicehelper.retrofit;


import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by appzoc on 20/1/16.
 */
public interface APIs {


    public static final String BASE_URL = "http://smash.codinol.in/smash/";

    ///MODEL////

    @GET("demo/api/users/registration_data")
    Call<JsonObject> getRegistrationDataOne();

    @Headers("Content-Type: application/json")
    @POST("mobile/sendOTP/")
    Call<JsonObject> postOtp();

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

    @GET("api/work/work_list/{path}")
    Call<JsonObject> getScheduledWorks();
}
