package com.auxesis.maxcrowdfund.restapi;

import com.auxesis.maxcrowdfund.mvvm.ui.changePassword.changePassModel.ChangePasswordResponse;
import com.auxesis.maxcrowdfund.mvvm.ui.changePreference.changePreferenceModel.ChangePreferenceResponse;
import com.auxesis.maxcrowdfund.mvvm.ui.changePreference.changePreferenceModel.UpdatePreferenceResponse;
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
import com.auxesis.maxcrowdfund.mvvm.ui.dashborad.dashboardmodel.AccountBalanceResponse;
import com.auxesis.maxcrowdfund.mvvm.ui.dashborad.pendingmodel.PendingResponse;
import com.auxesis.maxcrowdfund.mvvm.ui.login.LoginResponse;
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

    /*for login */
    @POST("api/user/login")
    Call<LoginResponse> getLoginUser(@Header("Content-Type") String content,@Body JsonObject jsonObject);   //dynamic done

    @GET("api/portfolio")
    Call<PendingResponse> getPendingAPI(@Header("Content-Type") String content);

    @GET("api/profile")
    Call<ProfileResponse> UserProfile(@Header("Content-Type") String content,@Header("X-CSRF-Token") String xcsrf); //done

    @GET("api/active-account-details")
    Call<DashboardDetailModelResponce> DashboardDetail();

    @POST("api/get-trustly-signature/")
    Call<DashboardSignatureResponce> DashboardDetailSignature(@Header("Content-Type") String content,
                                                              @Header("X-CSRF-Token") String xcsrf,
                                                              @Query("method") String method,
                                                              @Query("uuid") String uuid,
                                                              @Query("data") JSONObject data);

    @GET("api/account-balance")
    Call<AccountBalanceResponse> getBank(@Header("Content-Type") String content,@Header("X-CSRF-TOKEN") String xcsrf);  //done for dashboard

    @GET("api/investments/listing")
    Call<MyInvestmentResponce> getMyInvestment();

    @GET("api/investments/listing/")
    Call<MyInvestmentSearchResponse> getMyInvestmentSearch(@Query("company") String company, @Query("from") String from, @Query("to") String to);

    @GET("api/investment/details")
    Call<MyInvestmentDetailResponse> getMyInvestmentDetail();

    @POST("api/change-email")
    Call<ChangeEmailResponse> changeEmail(@Header("Content-Type") String content, @Header("X-CSRF-Token") String xcsrf, @Body JsonObject jsonObject);

    /*For Bank Account */
    @GET("api/bank-accounts")
    Call<ChangeBankAccountResponse> getChangeBankAccount(@Header("Content-Type") String content, @Header("X-CSRF-TOKEN") String xcsrf);   //done

    @POST("api/change-active-bank-account")
    Call<ActiveBankAccountResponse> getActiveBankAccount(@Header("Content-Type") String content, @Header("X-CSRF-TOKEN") String xcsrf,@Body JsonObject jsonObject); //done

    @POST("api/sendotp")
    Call<SendOTPResponse> getSendOTP(@Header("Content-Type") String content,@Header("X-CSRF-TOKEN") String xcsrf,@Body JsonObject jsonObject);

    @POST("api/update-phone")
    Call<UpdatePhoneNumberResponse> getUpdatePhone(@Header("Content-Type") String content,@Header("X-CSRF-TOKEN") String xcsrf,@Body JsonObject jsonObject);

    @GET("api/get-preferences")
    Call<ChangePreferenceResponse> getChangePreferenceAPI(@Header("Content-Type") String content,@Header("X-CSRF-TOKEN") String xcsrf);

    @POST("api/change-preferences")
    Call<UpdatePreferenceResponse> getUpdatePreferenceAPI(@Header("Content-Type") String content, @Header("X-CSRF-TOKEN") String xcsrf,@Body JsonObject jsonObject);

    /* @GET("api/contact-information")
    Call<ContactInformationResponse> getContactInformation(@Header("Content-Type") String content);
    */

    @POST("api/change-password")
    Call<ChangePasswordResponse> getChangePassword(@Header("Content-Type") String content, @Header("X-CSRF-TOKEN") String xcsrf, @Body JsonObject jsonObject);  //done

    // Call<ChangePasswordResponse> getChangePassword(@Header("Content-Type") String content,@Header("X-CSRF-Token") String xcsrf, @Body JsonObject jsonObject);  //done

    @POST("api/change-avatar")
    Call<ChangeAvtarResponse> getChangeAvtar(@Header("Content-Type") String content, @Body JsonObject jsonObject);

}
