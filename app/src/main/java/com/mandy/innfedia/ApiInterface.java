package com.mandy.innfedia;

import com.mandy.innfedia.ApiModel.BannerApi;
import com.mandy.innfedia.ApiModel.CategoryApi;
import com.mandy.innfedia.ApiModel.DiscountedApi;
import com.mandy.innfedia.ApiModel.ForgotPasswordApi;
import com.mandy.innfedia.ApiModel.GetAddToCart;
import com.mandy.innfedia.ApiModel.GetProductDetails;
import com.mandy.innfedia.ApiModel.GetProductList;
import com.mandy.innfedia.ApiModel.GetSubCategory;
import com.mandy.innfedia.ApiModel.LoginApi;
import com.mandy.innfedia.ApiModel.NewArivalApi;
import com.mandy.innfedia.ApiModel.ProfileApi;
import com.mandy.innfedia.ApiModel.SignUpApi;
import com.mandy.innfedia.ApiModel.VerificationOtpApi;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
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


    //sele banners
    @POST("sales_banner")
    Call<BannerApi> getBanner();


    //get the profile details
    @POST("get_profile")
    Call<ProfileApi> getProfile(
            @Header("Authorization") String authorization
    );

    // upload the profile images
    @Multipart
    @POST("update_image")
    Call<ProfileApi> getProfileImage(
            @Header("Authorization") String authorization,
            @Part MultipartBody.Part part
    );

    // update the profile data
    @POST("edit_profile")
    Call<ProfileApi> getProfileUpdate(
            @Header("Authorization") String authorization,
            @Query("email") String Email,
            @Query("name") String Name
    );

    //get the sub category
    @POST("get_cat_products")
    Call<GetSubCategory> getSubCategory(
            @Header("Authorization") String authorization,
            @Query("cat_id") String Id
    );

    //get the product list
    @POST("get_products")
    Call<GetProductList> getProductList(
            @Header("Authorization") String authorization,
            @Query("product_cat_id") String Id
    );

    //get the product details
    @POST("get_single_product")
    Call<GetProductDetails> getProductDetails(
            @Header("Authorization") String authorization,
            @Query("id") String Id
    );

    //get the related product list
    @POST("get_related_products")
    Call<GetProductList> getRelatedList(
            @Header("Authorization") String authorization,
            @Query("product_cat_id") String Id
    );

    @POST("add_to_cart")
    Call<GetAddToCart> addToCart(
            @Header("Authorization") String authorization,
            @Query("product_id") String Id
    );

    @POST("get_cart_number")
    Call<GetAddToCart> getCartNumber(
            @Header("Authorization") String authorization
    );


}
