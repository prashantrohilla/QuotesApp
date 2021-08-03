package com.social.quotesapp.Api;

import com.social.quotesapp.Response.DeleteResponse;
import com.social.quotesapp.Response.GetPoetryResponse;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.Call;
import retrofit2.http.POST;

public interface ApiInterface {
    @GET("getpoetry.php")
    Call<GetPoetryResponse> getpoetry();              // we have to create same java class according to api response
    // getpoetry() is the name of function by which we call it.
    // we have defined request type, end point, response type of call and function name.

    @FormUrlEncoded           // we are using form data in postman
    @POST("deletepoetry.php")
        // end point is our file name
    Call<DeleteResponse> deletepoetry(@Field("poetry_id") String poetry_id); // deletepoetry() is the name of function by which we call it.
    // we have defined request type, end point, response type of call and function name.

    @FormUrlEncoded        // we are using form data in postman @FormUrlEncoded
    @POST("insertpoetry.php")
    Call<DeleteResponse> addpoetry(@Field("poetry") String poetry_data,  // request parameters
                                   @Field("poet_name") String poet_name);// using delete reponse for insert poetry because it has same parameters

    @FormUrlEncoded
    @POST("updatepoetry.php")
    Call<DeleteResponse> updatepoetry(@Field("poetry") String poetry_data,
                                      @Field("id") String id);

}
