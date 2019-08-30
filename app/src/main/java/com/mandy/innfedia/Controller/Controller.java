package com.mandy.innfedia.Controller;

import android.media.session.MediaSession;

import com.google.gson.internal.LinkedHashTreeMap;
import com.mandy.innfedia.AddressActivity.GetAddressApi;
import com.mandy.innfedia.ProductDetils.GetAddToCart;
import com.mandy.innfedia.GetMeesageApi;
import com.mandy.innfedia.MyCart.ExploreMore.GetExploreMoreData;
import com.mandy.innfedia.MyCart.GetCartDataApi;
import com.mandy.innfedia.MyCart.topitems.GetTopDataApi;
import com.mandy.innfedia.MyProfile.ProfileApi;
import com.mandy.innfedia.Payment.PaymentProductApi;
import com.mandy.innfedia.ProductDetils.GetProductDetailsApi;
import com.mandy.innfedia.Retrofit.ApiInterface;
import com.mandy.innfedia.Retrofit.ServiceGenerator;
import com.mandy.innfedia.TermsAndCondition.TermsConditionApi;
import com.mandy.innfedia.home2.GetSubCategoryApi;
import com.mandy.innfedia.productList.GetProductList;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Controller {
    public getTermsandCondition getTermsandCondition; // used for get the terms and condition
    public BuyItemsList buyItemsList; // get the payment product list and payment time
    public IncreseItemQuantity increseItemQuantity; //incres or decrese the product quantity
    public DeleteItems deleteItems;// delete item from cart and payment activity
    public MyCartList myCartList;//get the my cart item list
    public TopItemsList topItemsList;//get the top listed items
    public ExploreMore exploreMore;// get the explore more items list
    public AddToCart addToCart;//add the item into cart
    public GetCheckSome getCheckSome;//get the checksome code form apis
    public EditProfile editProfile;//edit the profile details
    public EditProfileImage editProfileImage;// edit the prfile image
    public GetProfile getProfile;//get the profile data
    public GetAddressList getAddressList;// get the address list
    public ADDAddress addAddress;//add address
    public GetSubCateogry getSubCateogry;//get the subcategory;
    public GetProductDetails getProductDetails;//get the product details
    public GetRelatedItems getRelatedItems;//get the related items
    public GetItemsList getItemsList;//get items list

    /*+++++++++++++++++++++++api for get the terms and condition++++++++++++++++++*/
    public Controller(getTermsandCondition getTermsand) {
        getTermsandCondition = getTermsand;
    }

    public void setGetTermsandCondition(String Authrization) {
        ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
        Call<TermsConditionApi> call = apiInterface.getTerms(Authrization);
        call.enqueue(new Callback<TermsConditionApi>() {
            @Override
            public void onResponse(Call<TermsConditionApi> call, Response<TermsConditionApi> response) {
                if (response.isSuccessful()) {
                    Response<TermsConditionApi> response1 = response;
                    getTermsandCondition.onSucess(response1);
                }
            }

            @Override
            public void onFailure(Call<TermsConditionApi> call, Throwable t) {
                getTermsandCondition.error(t.getMessage());
            }
        });
    }

    //get the terms and condition
    public interface getTermsandCondition {
        void onSucess(Response<TermsConditionApi> response);

        void error(String error);
    }

    /*++++++++++++++++++++++++++++END+++++++++++++++++++++++++++++++++++++++++++++++++*/

    /*++++++++++++++++++++++++++++++++Api for buy+++++++++++++++++++++++++++++++++*/

    public Controller(BuyItemsList buyItems, IncreseItemQuantity increseItem, DeleteItems deleteItems1, GetCheckSome checkSome) {
        buyItemsList = buyItems;
        increseItemQuantity = increseItem;
        deleteItems = deleteItems1;
        getCheckSome = checkSome;
    }

    public void getBuyItemsList(String authorization, String id) {
        ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
        Call<PaymentProductApi> call = apiInterface.getBuyItemsList(authorization, id);
        call.enqueue(new Callback<PaymentProductApi>() {
            @Override
            public void onResponse(Call<PaymentProductApi> call, Response<PaymentProductApi> response) {
                if (response.isSuccessful()) {
                    buyItemsList.onSucess(response);
                }
            }

            @Override
            public void onFailure(Call<PaymentProductApi> call, Throwable t) {
                buyItemsList.error(t.getMessage());
            }
        });
    }

    public interface BuyItemsList {
        void onSucess(Response<PaymentProductApi> response);

        void error(String error);
    }
    /*++++++++++++++++++++++++++++++++END+++++++++++++++++++++++++++++++++*/

    /*++++++++++++++++++++++++++++++++Paytm checksome file get  API+++++++++++++++++++++++++++++++++*/
    public void setGetCheckSome(String token, String mid, String orderId, String custId, String industrytype, String channerlId, String amount, String website, String callbackurl) {
        ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
        Call<ResponseBody> call = apiInterface.getCheckSomeCode(token, mid, orderId, custId, industrytype, channerlId, amount, website, callbackurl);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    getCheckSome.getCheck(response);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                getCheckSome.gotError(t.getMessage());
            }
        });
    }

    public interface GetCheckSome {
        void getCheck(Response<ResponseBody> respose);

        void gotError(String error);
    }
    /*++++++++++++++++++++++++++++++++END+++++++++++++++++++++++++++++++++*/

    /*++++++++++++++++++++++++++++++++increse item quantity API+++++++++++++++++++++++++++++++++*/
    public void setIncreseItemQuantity(String authorization, String id, String number) {
        ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
        Call<GetMeesageApi> call = apiInterface.addQuantinty(authorization, id, number);
        call.enqueue(new Callback<GetMeesageApi>() {
            @Override
            public void onResponse(Call<GetMeesageApi> call, Response<GetMeesageApi> response) {
                if (response.isSuccessful()) {
                    increseItemQuantity.onSucessAdd(response);
                }
            }

            @Override
            public void onFailure(Call<GetMeesageApi> call, Throwable t) {
                increseItemQuantity.Adderror(t.getMessage());
            }
        });
    }

    public interface IncreseItemQuantity {
        void onSucessAdd(Response<GetMeesageApi> response);

        void Adderror(String error);
    }
    /*++++++++++++++++++++++++++++++++END+++++++++++++++++++++++++++++++++*/

    /*++++++++++++++++++++++Delete item from cart api++++++++++++++++++++++*/
    public void setDeleteItems(String token, String id) {
        ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
        Call<GetMeesageApi> call = apiInterface.deleteProduct(token, id);
        call.enqueue(new Callback<GetMeesageApi>() {
            @Override
            public void onResponse(Call<GetMeesageApi> call, Response<GetMeesageApi> response) {
                if (response.isSuccessful()) {
                    deleteItems.onSuccessDelete(response);
                }
            }

            @Override
            public void onFailure(Call<GetMeesageApi> call, Throwable t) {
                deleteItems.errorDelete(t.getMessage());
            }
        });
    }

    public interface DeleteItems {
        void onSuccessDelete(Response<GetMeesageApi> response);

        void errorDelete(String error);
    }
    /*+++++++++++++++++++++++END++++++++++++++++++++++++*/

    /*++++++++++++++++++++++++++Get my Cart item List Api++++++++++++++++++++++++++++++++*/

    public Controller(MyCartList myCart, DeleteItems deleteItems1, IncreseItemQuantity itemQuantity, TopItemsList topItemsList1, ExploreMore exploreMore1, AddToCart addToCart1) {
        myCartList = myCart;
        deleteItems = deleteItems1;
        increseItemQuantity = itemQuantity;
        topItemsList = topItemsList1;
        exploreMore = exploreMore1;
        addToCart = addToCart1;
    }

    public void setMyCartList(String token) {
        ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
        Call<GetCartDataApi> call = apiInterface.getCartData(token);
        call.enqueue(new Callback<GetCartDataApi>() {
            @Override
            public void onResponse(Call<GetCartDataApi> call, Response<GetCartDataApi> response) {
                if (response.isSuccessful()) {
                    myCartList.onSuccess(response);
                }
            }

            @Override
            public void onFailure(Call<GetCartDataApi> call, Throwable t) {
                myCartList.error(t.getMessage());
            }
        });
    }

    public interface MyCartList {
        void onSuccess(Response<GetCartDataApi> response);

        void error(String error);
    }
    /*++++++++++++++++++++++++++++++END+++++++++++++++++++++++++++++++++++*/

    /*++++++++++++++++++++++++Get top items list API+++++++++++++++++++++++++++++++*/

    public void setTopItemsList(String token) {
        ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
        Call<GetTopDataApi> call = apiInterface.getTopData(token);
        call.enqueue(new Callback<GetTopDataApi>() {
            @Override
            public void onResponse(Call<GetTopDataApi> call, Response<GetTopDataApi> response) {
                if (response.isSuccessful()) {
                    topItemsList.onSuccessTop(response);
                }
            }

            @Override
            public void onFailure(Call<GetTopDataApi> call, Throwable t) {
                topItemsList.onErrorTop(t.getMessage());
            }
        });
    }

    public interface TopItemsList {
        void onSuccessTop(Response<GetTopDataApi> response);

        void onErrorTop(String error);
    }
    /*+++++++++++++++++++++++++++END++++++++++++++++++++++++++++=*/



    /*++++++++++++++++++++++++Get Explore more items list API+++++++++++++++++++++++++++++++*/

    public void setExploreMore(String token) {
        ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
        Call<GetExploreMoreData> call = apiInterface.getMorePrduct(token);
        call.enqueue(new Callback<GetExploreMoreData>() {
            @Override
            public void onResponse(Call<GetExploreMoreData> call, Response<GetExploreMoreData> response) {
                if (response.isSuccessful()) {
                    exploreMore.onSuccessExplore(response);
                }
            }

            @Override
            public void onFailure(Call<GetExploreMoreData> call, Throwable t) {
                exploreMore.onErrorExplore(t.getMessage());
            }
        });
    }

    public interface ExploreMore {
        void onSuccessExplore(Response<GetExploreMoreData> response);

        void onErrorExplore(String error);
    }
    /*+++++++++++++++++++++++++++END++++++++++++++++++++++++++++=*/

    /*++++++++++++++++++++++++Add into cart api+++++++++++++++++++++++++*/

    public void setAddToCart(String token, String id) {
        final ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
        Call<GetAddToCart> call = apiInterface.addToCart(token, id);
        call.enqueue(new Callback<GetAddToCart>() {
            @Override
            public void onResponse(Call<GetAddToCart> call, Response<GetAddToCart> response) {
                if (response.isSuccessful()) {
                    addToCart.addtocart(response);
                }
            }

            @Override
            public void onFailure(Call<GetAddToCart> call, Throwable t) {
                addToCart.errorCart(t.getMessage());
            }
        });
    }

    public interface AddToCart {
        void addtocart(Response<GetAddToCart> response);

        void errorCart(String error);
    }
    /*++++++++++++++++++++++++++++++END+++++++++++++++++++++++++++++++++*/

    /*++++++++++++++++++++++++EDIT PROFILE+++++++++++++++++++++++*/

    public Controller(EditProfile editProfile1, EditProfileImage editProfileImage1, GetProfile getProfile1) {
        editProfile = editProfile1;
        editProfileImage = editProfileImage1;
        getProfile = getProfile1;
    }

    public void setEditProfile(String token, String name, String email) {
        ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
        Call<ProfileApi> call = apiInterface.getProfileUpdate(token, name, email);
        call.enqueue(new Callback<ProfileApi>() {
            @Override
            public void onResponse(Call<ProfileApi> call, Response<ProfileApi> response) {
                if (response.isSuccessful()) {
                    editProfile.onSucess(response);
                }
            }

            @Override
            public void onFailure(Call<ProfileApi> call, Throwable t) {
                editProfile.error(t.getMessage());
            }
        });
    }

    public interface EditProfile {
        void onSucess(Response<ProfileApi> response);

        void error(String error);
    }
    /*++++++++++++++++++++++++++++END++++++++++++++++++++++++++++++++*/

    /*+++++++++++++++++++++++++++Edit Profile Image++++++++++++++++++++++*/

    public void setEditProfileImage(String token, MultipartBody.Part part) {
        ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
        Call<ProfileApi> call = apiInterface.getProfileImage(token, part);
        call.enqueue(new Callback<ProfileApi>() {
            @Override
            public void onResponse(Call<ProfileApi> call, Response<ProfileApi> response) {
                if (response.isSuccessful()) {
                    editProfileImage.onSucessImage(response);
                }
            }

            @Override
            public void onFailure(Call<ProfileApi> call, Throwable t) {
                editProfileImage.errorImage(t.getMessage());
            }
        });
    }

    public interface EditProfileImage {
        void onSucessImage(Response<ProfileApi> response);

        void errorImage(String error);
    }
    /*+++++++++++++++++++END+++++++++++++++++++++++++++++++++*/

    /*+++++++++++++++++++++++GET THE PROFILE DATA+++++++++++++++++++*/

    public void setGetProfile(String token) {
        ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
        Call<ProfileApi> call = apiInterface.getProfile(token);
        call.enqueue(new Callback<ProfileApi>() {
            @Override
            public void onResponse(Call<ProfileApi> call, Response<ProfileApi> response) {
                if (response.isSuccessful()) {
                    getProfile.onSuccesGetProfile(response);
                }
            }

            @Override
            public void onFailure(Call<ProfileApi> call, Throwable t) {
                getProfile.errorProfile(t.getMessage());
            }
        });
    }

    public interface GetProfile {
        void onSuccesGetProfile(Response<ProfileApi> response);

        void errorProfile(String error);
    }
    /*+++++++++++++++++++++END++++++++++++++++++++++++++*/

    /*++++++++++++++++++++++++++++get address list+++++++++++++++++*/

    public Controller(GetAddressList getAddressList1) {
        getAddressList = getAddressList1;
    }

    public void setGetAddressList(String token) {
        ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
        Call<GetAddressApi> call = apiInterface.getAddressList(token);
        call.enqueue(new Callback<GetAddressApi>() {
            @Override
            public void onResponse(Call<GetAddressApi> call, Response<GetAddressApi> response) {
                if (response.isSuccessful()) {
                    getAddressList.onSucess(response);
                }
            }

            @Override
            public void onFailure(Call<GetAddressApi> call, Throwable t) {
                getAddressList.error(t.getMessage());
            }
        });
    }

    public interface GetAddressList {
        void onSucess(Response<GetAddressApi> response);

        void error(String error);
    }
    /*+++++++++++++++++++++++++END+++++++++++++++++++++++++*/

    /*+++++++++++++++++++ADD ADDRESS INTO API+++++++++++++++++++*/

    public Controller(ADDAddress addAddress1) {
        addAddress = addAddress1;
    }

    public void setAddAddress(String token, String name, String mobile, String postcode, String town, String state, String flat, String near) {
        ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
        Call<GetMeesageApi> call = apiInterface.addAddress(token, name, mobile, postcode, town, state, flat, near);
        call.enqueue(new Callback<GetMeesageApi>() {
            @Override
            public void onResponse(Call<GetMeesageApi> call, Response<GetMeesageApi> response) {
                if (response.isSuccessful()) {
                    addAddress.onSucess(response);
                }
            }

            @Override
            public void onFailure(Call<GetMeesageApi> call, Throwable t) {
                addAddress.error(t.getMessage());
            }
        });

    }

    public interface ADDAddress {
        void onSucess(Response<GetMeesageApi> response);

        void error(String error);
    }
    /*++++++++++++++++++++END+++++++++++++++++++++*/

    /*########################## See All sub Category###########################*/

    public Controller(GetSubCateogry getSubCateogry1) {
        getSubCateogry = getSubCateogry1;
    }

    public void setGetSubCateogry(String token, String id) {
        ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
        Call<GetSubCategoryApi> call = apiInterface.getSubCategory(token, id);
        call.enqueue(new Callback<GetSubCategoryApi>() {
            @Override
            public void onResponse(Call<GetSubCategoryApi> call, Response<GetSubCategoryApi> response) {
                if (response.isSuccessful()) {
                    getSubCateogry.onSucess(response);
                }
            }

            @Override
            public void onFailure(Call<GetSubCategoryApi> call, Throwable t) {
                getSubCateogry.error(t.getMessage());
            }
        });
    }

    public interface GetSubCateogry {
        void onSucess(Response<GetSubCategoryApi> response);

        void error(String error);
    }
    /*++++++++++++++++++++++END++++++++++++++++++++++++*/

    /*+++++++++++++++++++Get Product Details+++++++++++++++++++++++++++*/

    public Controller(GetProductDetails getProductDetails1, GetRelatedItems getRelatedItems1, AddToCart addToCart1) {
        getProductDetails = getProductDetails1;
        getRelatedItems = getRelatedItems1;
        addToCart = addToCart1;
    }

    public void setGetProductDetails(String token, String id) {
        ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
        Call<GetProductDetailsApi> call = apiInterface.getProductDetails(token, id);
        call.enqueue(new Callback<GetProductDetailsApi>() {
            @Override
            public void onResponse(Call<GetProductDetailsApi> call, Response<GetProductDetailsApi> response) {
                if (response.isSuccessful()) {
                    getProductDetails.onSucess(response);
                }
            }

            @Override
            public void onFailure(Call<GetProductDetailsApi> call, Throwable t) {
                getProductDetails.error(t.getMessage());
            }
        });
    }

    public interface GetProductDetails {
        void onSucess(Response<GetProductDetailsApi> response);

        void error(String error);
    }
    /*++++++++++++++++++++++++END++++++++++++++++++++++++++++++*/

    /*++++++++++++++++++++++++++++++ Get the Related items ++++++++++++++++++++++ */
    public void setGetRelatedItems(String token, String catId) {
        ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
        Call<GetProductList> call = apiInterface.getRelatedList(token, catId);
        call.enqueue(new Callback<GetProductList>() {
            @Override
            public void onResponse(Call<GetProductList> call, Response<GetProductList> response) {
                if (response.isSuccessful()) {
                    getRelatedItems.onSuccessRelated(response);
                }
            }

            @Override
            public void onFailure(Call<GetProductList> call, Throwable t) {
                getRelatedItems.errorRelated(t.getMessage());
            }
        });
    }

    public interface GetRelatedItems {
        void onSuccessRelated(Response<GetProductList> response);

        void errorRelated(String error);
    }
    /*++++++++++++++++++++ END +++++++++++++++++++++*/

    /*++++++++++++++++++++++++++++Get the product list+++++++++++++++++++++++*/

    public Controller(GetItemsList getItemsList1) {
        getItemsList = getItemsList1;
    }

    public void setGetItemsList(String token, String id) {
        ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
        Call<GetProductList> call = apiInterface.getProductList(token, id);
        call.enqueue(new Callback<GetProductList>() {
            @Override
            public void onResponse(Call<GetProductList> call, Response<GetProductList> response) {
                if (response.isSuccessful()) {
                    getItemsList.onSuccess(response);
                }
            }

            @Override
            public void onFailure(Call<GetProductList> call, Throwable t) {
                getItemsList.error(t.getMessage());
            }
        });
    }

    public interface GetItemsList {
        void onSuccess(Response<GetProductList> response);

        void error(String error);
    }
    /*+++++++++++++++++++END+++++++++++++++++++++*/
}
