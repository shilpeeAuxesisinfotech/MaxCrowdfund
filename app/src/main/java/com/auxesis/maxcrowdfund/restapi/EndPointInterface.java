package com.auxesis.maxcrowdfund.restapi;

import com.auxesis.maxcrowdfund.custommvvm.changeemail.ChangeEmailResponse;
import com.auxesis.maxcrowdfund.custommvvm.myinvestmentmodel.MyInvestmentSearchResponse;
import com.auxesis.maxcrowdfund.custommvvm.myinvestmentmodel.myinvestmentdetail.MyInvestmentDetailResponse;
import com.auxesis.maxcrowdfund.custommvvm.profile.profileModel.ProfileResponse;
import com.auxesis.maxcrowdfund.custommvvm.dashboardDetail.DashboardDetailModel.DashboardDetailModelResponce;
import com.auxesis.maxcrowdfund.custommvvm.dashboardDetail.DashboardDetailModel.DashboardSignatureResponce;
import com.auxesis.maxcrowdfund.custommvvm.myinvestmentmodel.MyInvestmentResponce;
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


    //https://test.maxcrowdfund.com/api/investments/listing/?company=abc&from=2234567&to=2234567

    @GET("api/investments/listing/")
    Call<MyInvestmentSearchResponse> getMyInvestmentSearch(@Query("company") String company,
                                                           @Query("from") String from,
                                                           @Query("to") String to);

    @GET("api/investment/details")
    Call<MyInvestmentDetailResponse> getMyInvestmentDetail();


    @POST("api/change-email")
    Call<ChangeEmailResponse> changeEmail(@Header("Content-Type") String content,
                                          @Header("X-CSRF-Token") String xcsrf,
                                          @Body JsonObject jsonObject);


   /* @GET("api/account-balance")
    Call<AccountBalanceResponse> getAccountBalance(@Header("Content-Type") String content);

    @GET("api/portfolio")
    Call<PortfolioResponse> getPortfolio(@Header("Content-Type") String content);

*/


    /*For fragment*/

    //public final static String GER_ACCOUNT_BALANCE = BASE_URL + "api/account-balance";


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
