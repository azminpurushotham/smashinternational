package com.webservicehelper.retrofit;


import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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


    @POST("/api/smash/api/profile/changepwd/")
    Call<JsonObject> postUpdatePassword(@Query("user_id") String user_id,
                                        @Query("new_password") String new_password,
                                        @Query("old_password") String old_password,
                                        @Query("token") String token);

    @Multipart
    @POST("/api/smash/api/profile/pic/")
    Call<JsonObject> postUpdateImage(@Part("user_id") String user_id,
                                     @Part MultipartBody.Part file,
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
                                @Field("token") String token);


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

    @GET("/api/smash/api/work/work_list/{user_id}/{token}/{cal_date}")
    Call<JsonObject> getScheduledWorksDateVice(@Path("user_id") String user_id,
                                               @Path("token") String token,
                                               @Path("cal_date") String cal_date);


    @GET("api/smash/api/shop/shop_list/{user_id}/{token}")
    Call<JsonObject> getShopList(@Path("user_id") String user_id,
                                 @Path("token") String token);


    @GET("/api/smash/api/work/complete_list/{user_id}/{token}")
    Call<JsonObject> getCompletedWorks(@Path("user_id") String user_id,
                                       @Path("token") String token);

    @GET("api/smash/api/search/search_list")
    Call<JsonObject> getSearchScheduledWorks(@Query("user_id") String user_id,
                                             @Query("token") String token,
                                             @Query("worktype") String worktype,
                                             @Query("query") String query);


    @GET("api/smash/api/search/search_list")
    Call<JsonObject> getSearchCompletedWorks(@Query("user_id") String user_id,
                                             @Query("token") String token,
                                             @Query("worktype") String worktype,
                                             @Query("query") String query);


    @GET("api/smash/api/search/search_list")
    Call<JsonObject> getSearchCompletedWorksDateVice(@Query("user_id") String user_id,
                                             @Query("token") String token,
                                             @Query("worktype") String worktype,
                                             @Query("query") String query,
                                             @Query("cal_date") String cal_date);


    @GET("api/work/work_list/{path}")
    Call<JsonObject> getScheduledWorks();

    @GET("api/smash/api/work/work_details/{id}/{user_id}/{token}")
    Call<JsonObject> getScheduledWorksDetail(@Path("id") String id, @Path("user_id") String user_id, @Path("token") String token);

    @GET("api/smash/api/shop/shop_details/{id}/{user_id}/{token}")
    Call<JsonObject> getShopDetail(@Path("id") String id,
                                   @Path("user_id") String user_id,
                                   @Path("token") String token);

    @POST("api/smash/api/lead/add_lead/")
    Call<JsonObject> postNewLead(@Query("user_id") String user_id,
                                 @Query("customer_name") String customer_name,
                                 @Query("email") String email,
                                 @Query("sms_no") String sms_no,
                                 @Query("address_1") String address1,
                                 @Query("address_2") String address2,
                                 @Query("telephone_number") String telephone_number,
                                 @Query("pending_amount") String pending_amount,
                                 @Query("currency") String currency,
                                 @Query("collection_amount") String collection_amount,
                                 @Query("bill_id") String bill_id,
                                 @Query("token") String token,
                                 @Query("status") String status,
                                 @Query("lat") String lat,
                                 @Query("lon") String lon);

    @POST("api/smash/api/work/work_update/")
    Call<JsonObject> updateWorkStatus(@Query("user_id") String user_id,
                                      @Query("token") String token,
                                      @Query("customer_id") String customer_id,
                                      @Query("customer_name") String branch_name,
                                      @Query("address_1") String address1,
                                      @Query("address_2") String address2,
                                      @Query("email") String email,
                                      @Query("sms_no") String sms_no,
                                      @Query("telephone_number") String telephone_number,
                                      @Query("status") String status,
                                      @Query("collection_amount") String collection_amount,
                                      @Query("pending_amount") String pending_amount,
                                      @Query("reason") String reason,
                                      @Query("invoice") String invoice,
                                      @Query("bill_id") String bill_id);

    @POST("api/smash/api/customer/customer_location/")
    Call<JsonObject> updateShopLocation(@Query("user_id") String user_id,
                                        @Query("token") String token,
                                        @Query("customer_id") String customer_id,
                                        @Query("address1") String address1,
                                        @Query("address2") String address2,
                                        @Query("latitude") String latitude,
                                        @Query("longitude") String longitude);
}
