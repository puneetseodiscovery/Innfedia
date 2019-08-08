package com.mandy.innfedia;

import com.mandy.innfedia.ApiModel.BannerApi;
import com.mandy.innfedia.ApiModel.CategoryApi;
import com.mandy.innfedia.ApiModel.DiscountedApi;
import com.mandy.innfedia.ApiModel.ForgotPasswordApi;
import com.mandy.innfedia.ApiModel.LoginApi;
import com.mandy.innfedia.ApiModel.NewArivalApi;
import com.mandy.innfedia.ApiModel.SignUpApi;
import com.mandy.innfedia.ApiModel.VerificationOtpApi;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

    // Sign up Api
    @POST("register")
    Call<SignUpApi> signupApi(
            @Query("name") String Name,
            @Query("phone") String phone,
            @Query("password") String password
    );

    // sign up otp verification api
    @POST("user_verify")
    Call<VerificationOtpApi> userVerification(
            @Query("otp") String otp,
            @Query("phone") String phone,
            @Query("id") String id
    );

    //Login Api
    @POST("login")
    Call<LoginApi> loginApi(
            @Query("phone") String phone,
            @Query("password") String password
    );

    //get the otp for forgot password
    @POST("forget_password")
    Call<ForgotPasswordApi> getOtp(
            @Query("phone") String phone
    );

    @POST("resend_otp")
    Call<ForgotPasswordApi> sendOtp(
            @Query("otp") String otp,
            @Query("phone") String phone
    );

    //create the new password
    @POST("change_password")
    Call<ForgotPasswordApi> resetPassword(
            @Query("password") String password,
            @Query("phone") String phone
    );


    //get category
    @POST("categories")
    Call<CategoryApi> getCategory();

    //get the new Arrival product
    @POST("new_arrivals")
    Call<NewArivalApi> newArrivalsApi();

    //get the Discounted product
    @POST("discounted_products")
    Call<DiscountedApi> getDiscountedapi();


    @POST("sales_banner")
    Call<BannerApi> getBanner();


}
