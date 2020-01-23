package com.auxesis.maxcrowdfund.restapi;

import com.auxesis.maxcrowdfund.mvvm.ui.changePassword.changePassModel.ChangePasswordResponse;
import com.auxesis.maxcrowdfund.mvvm.ui.changePreference.changePreferenceModel.ChangePreferenceResponse;
import com.auxesis.maxcrowdfund.mvvm.ui.changeemail.ChangeEmailResponse;
import com.auxesis.maxcrowdfund.custommvvm.myinvestmentmodel.MyInvestmentSearchResponse;
import com.auxesis.maxcrowdfund.custommvvm.myinvestmentmodel.myinvestmentdetail.MyInvestmentDetailResponse;
import com.auxesis.maxcrowdfund.custommvvm.profile.profileModel.ProfileResponse;
import com.auxesis.maxcrowdfund.custommvvm.dashboardDetail.DashboardDetailModel.DashboardDetailModelResponce;
import com.auxesis.maxcrowdfund.custommvvm.dashboardDetail.DashboardDetailModel.DashboardSignatureResponce;
import com.auxesis.maxcrowdfund.custommvvm.myinvestmentmodel.MyInvestmentResponce;
import com.auxesis.maxcrowdfund.mvvm.ui.changeMobileNumber.changeMobileModel.SendOTPResponse;
import com.auxesis.maxcrowdfund.mvvm.ui.changeMobileNumber.changeMobileModel.UpdatePhoneNumberResponse;
import com.auxesis.maxcrowdfund.mvvm.ui.changebankaccount.changebankaccountmodel.ActiveBankAccountResponse;
import com.auxesis.maxcrowdfund.mvvm.ui.changebankaccount.changebankaccountmodel.ChangeBankAccountResponse;
import com.auxesis.maxcrowdfund.mvvm.ui.contactinformation.contactInformationModel.ContactInformationResponse;
import com.auxesis.maxcrowdfund.mvvm.ui.home.homemodel.InvestmentOppResponse;
import com.auxesis.maxcrowdfund.mvvm.ui.myprofile.ChangeAvtarResponse;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface EndPointInterface {

    @GET("api/profile")
    Call<ProfileResponse> UserProfile();

    @GET("api/active-account-details")
    Call<DashboardDetailModelResponce> DashboardDetail();

    @POST("api/get-trustly-signature/")
    Call<DashboardSignatureResponce> DashboardDetailSignature(@Header("Content-Type") String content,
                                                              @Header("X-CSRF-Token") String xcsrf,
                                                              @Query("method") String method,
                                                              @Query("uuid") String uuid,
                                                              @Query("data") JSONObject data);

    @GET("api/investments/listing")
    Call<MyInvestmentResponce> getMyInvestment();

    @GET("api/investments/listing/")
    Call<MyInvestmentSearchResponse> getMyInvestmentSearch(@Query("company") String company,
                                                           @Query("from") String from,
                                                           @Query("to") String to);

    @GET("api/fundraiser/listing")
    Call<InvestmentOppResponse> getMyInvestmentOpp(@Query("_format") String _format,
                                                   @Query("investment_status") String investment_status,
                                                   @Query("page") int page);


    @GET("api/investment/details")
    Call<MyInvestmentDetailResponse> getMyInvestmentDetail();


    @POST("api/change-email")
    Call<ChangeEmailResponse> changeEmail(@Header("Content-Type") String content,
                                          @Header("X-CSRF-Token") String xcsrf,
                                          @Body JsonObject jsonObject);

    @GET("api/bank-accounts")
    Call<ChangeBankAccountResponse> getChangeBankAccount();

    @POST("api/change-active-bank-account")
    Call<ActiveBankAccountResponse> getActiveBankAccount(@Header("Content-Type") String content,
                                                         @Body JsonObject jsonObject);

    @POST("api/sendotp")
    Call<SendOTPResponse> getSendOTP(@Header("Content-Type") String content,
                                     @Body JsonObject jsonObject);

    @POST("api/update-phone")
    Call<UpdatePhoneNumberResponse> getUpdatePhone(@Header("Content-Type") String content,
                                                   @Body JsonObject jsonObject);

    @GET("api/get-preferences")
    Call<ChangePreferenceResponse> getChangePreferenceAPI(@Header("Content-Type") String content);

    @GET("api/contact-information")
    Call<ContactInformationResponse> getContactInformation(@Header("Content-Type") String content);


    @POST("api/change-password")
    Call<ChangePasswordResponse> getChangePassword(@Header("Content-Type") String content, @Body JsonObject jsonObject);

    @POST("api/change-avatar")
    Call<ChangeAvtarResponse> getChangeAvtar(@Header("Content-Type") String content, @Body JsonObject jsonObject);





/*
  @GET("api/get-trustly-signature/?method={method}&uuid={uuid}&data={data}")
    Call<DashboardSignatureResponce> DashboardDetailSignature(@Path("method") String method,@Path("uuid") String uuid,@Path("data") String data);

*/

    //  https://test.maxcrowdfund.com/api/get-trustly-signature/?method=sdaasd&uuid=asdasd&data=sdasdasd


   /* @POST("/api/user/login")
    Call<LoginResponse> Login(@Header("Content-Type") String content,
                              @Body JsonObject jsonObject);

    @POST("/api/user/signup")
    Call<SignUpResponse> SignUp(@Header("Content-Type") String content,
                                @Header("Accept") String accept,
                                @Body JsonObject jsonObject);

    @POST("/api/user/logout")
    Call<CreateTripResponse> Logout(@Header("Authorization") String auth);

    @POST("/api/user/send/request")
    Call<CreateTripResponse> CreateTrip(@Header("Content-Type") String content,
                                        @Header("Authorization") String auth,
                                        @Body JsonObject jsonObject);

    @POST("/api/user/update/profile")
    Call<GetProfileResponse> UpdateProfile(@Header("Content-Type") String content,
                                           @Header("Authorization") String auth,
                                           @Body JsonObject jsonObject);
    @POST("/api/user/cancel/request")
    Call<CancelTripResponse> CancelTrip(@Header("Content-Type") String content,
                                        @Header("Authorization") String auth,
                                        @Body JsonObject jsonObject);

    @POST("/api/user/rate/provider")
    Call<CancelTripResponse> RateTrip(@Header("Content-Type") String content,
                                      @Header("Authorization") String auth,
                                      @Body JsonObject jsonObject);

    @GET("/api/user/request/check")
    Call<List<TripData>> TripsCheck(@Header("Authorization") String auth);

    @GET("/api/user/trips")
    Call<AllTripResponse> AllTrips(@Header("Authorization") String auth,
                                   @Query("page") String page);

    @GET("/api/user/trip/details")
    Call<TripDetailsResponse> TripDetails(@Header("Authorization") String auth,
                                          @Query("request_id") String request_id);

    @GET("/api/user/help")
    Call<HelpResponse> Help(@Header("Authorization") String auth);

    @GET("/api/user/details")
    Call<GetProfileResponse> GetProfile(@Header("Authorization") String auth);

    @GET("/api/user/services")
    Call<VehicleResponse> Services(@Header("Authorization") String content);

    @GET("/api/user/location")
    Call<FavouriteLocationResponse> Location(@Header("Authorization") String content);

    @POST("/api/user/location")
    Call<UpdateTokenResponse> AddFavourite(@Header("Authorization") String auth,
                                           @Header("ContentType") String content,
                                           @Body JsonObject jsonObject);

    @POST("/api/user/estimated/fare")
    Call<FareResponse> EstimatedFare(@Header("Authorization") String auth,
                                     @Header("ContentType") String content,
                                     @Body JsonObject jsonObject);

    @POST("/api/user/update/usertoken")
    Call<UpdateTokenResponse> updateToken(@Header("Authorization") String auth,
                                          @Header("ContentType") String content,
                                          @Body JsonObject jsonObject);

    @GET("/api/user/trip/{id}/invoice")
    Call<UpdateTokenResponse> sendInvoice(@Header("Authorization") String auth,
                                          @Path("id") String id);

    @GET("/api/user/available/service")
    Call<AvailableServices> availableService(@Header("Authorization") String auth,
                                             @Query("latitude") double latitude,
                                             @Query("longitude") double longitude);

    @GET("/api/user/loadtypes")
    Call<List<LoadTypeResponse>> loadTypes(@Header("Authorization") String auth);
*/
}
