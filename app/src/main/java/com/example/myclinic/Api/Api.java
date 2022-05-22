package com.example.myclinic.Api;

import com.example.myclinic.Models.DoctorListResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api {

    @GET("cityList")
    Call<ResponseBody> loadCities();

    @FormUrlEncoded
    @POST("login")
    Call<ResponseBody> loginUser(
            @Field("phone") String phoneNum
    );

    @FormUrlEncoded
    @POST("doctorList")
    Call<DoctorListResponse> doctorList(
      @Query("pageNum") int page,
      @Field("cityName") String cityName
    );

    @FormUrlEncoded
    @POST("sendBookingRequest")
    Call<ResponseBody> bookDoctor(
      @Field("patientName") String patientName,
      @Field("patientPhone") String patientPhone,
      @Field("patientAge") String patientAge,
      @Field("patientAddress") String patientAddress,
      @Field("patientGender") String patientGender,
      @Field("docName") String docName,
      @Field("docId") String docId
    );
}
