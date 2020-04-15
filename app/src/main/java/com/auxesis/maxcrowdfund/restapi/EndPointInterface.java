package com.auxesis.maxcrowdfund.restapi;

import com.auxesis.maxcrowdfund.mvvm.ui.changeMobileNumber.changeMobileModel.SendOTPResponse;
import com.auxesis.maxcrowdfund.mvvm.ui.changeMobileNumber.changeMobileModel.UpdatePhoneNumberResponse;
import com.auxesis.maxcrowdfund.mvvm.ui.changePassword.changePassModel.ChangePasswordResponse;
import com.auxesis.maxcrowdfund.mvvm.ui.changePreference.model.ChangePreferenceResponse;
import com.auxesis.maxcrowdfund.mvvm.ui.changePreference.model.UpdatePreferenceResponse;
import com.auxesis.maxcrowdfund.custommvvm.myinvestmentmodel.MyInvestmentSearchResponse;
import com.auxesis.maxcrowdfund.mvvm.ui.changebankaccount.changebankaccountmodel.ActiveBankAccountResponse;
import com.auxesis.maxcrowdfund.mvvm.ui.changebankaccount.changebankaccountmodel.ChangeBankAccountResponse;
import com.auxesis.maxcrowdfund.mvvm.ui.changeemail.ChangeEmailResponse;
import com.auxesis.maxcrowdfund.mvvm.ui.contactinformation.contactInformationModel.ContactInfoResponse;
import com.auxesis.maxcrowdfund.mvvm.ui.dashborad.dashboarddeposit.DashboardDepositResponse;
import com.auxesis.maxcrowdfund.mvvm.ui.dashborad.dashboardmodel.AccountResponse;
import com.auxesis.maxcrowdfund.mvvm.ui.dashborad.pendingmodel.PendingResponse;
import com.auxesis.maxcrowdfund.mvvm.ui.home.oppmodel.InvestmentOppRes;
import com.auxesis.maxcrowdfund.mvvm.ui.home.homeDetail.detailmodel.FundDetailResponce;
import com.auxesis.maxcrowdfund.mvvm.ui.home.homeDetail.investmodel.CreateInvestmentResponse;
import com.auxesis.maxcrowdfund.mvvm.ui.investform.questmodel.model.SurveyFormDataInsertResponse;
import com.auxesis.maxcrowdfund.mvvm.ui.investform.questmodel.questionlistmodel.InvestAmountKeyUpResponse;
import com.auxesis.maxcrowdfund.mvvm.ui.investform.questmodel.questionlistmodel.InvestSurveyRuestionResponse;
import com.auxesis.maxcrowdfund.mvvm.ui.login.LoginResponse;
import com.auxesis.maxcrowdfund.mvvm.ui.myinvestments.myinvestmentdetail.myinvestmentdetailmodel.CancelInvestmentResponce;
import com.auxesis.maxcrowdfund.mvvm.ui.myinvestments.myinvestmentdetail.myinvestmentdetailmodel.MyInvestmentDetailResponse;
import com.auxesis.maxcrowdfund.mvvm.ui.myinvestments.model.MyInvestmentResponce;
import com.auxesis.maxcrowdfund.mvvm.ui.myprofile.model.ProfileResponse;
import com.auxesis.maxcrowdfund.mvvm.ui.investform.questmodel.InvestFormPreloadResponse;
import com.auxesis.maxcrowdfund.mvvm.ui.uploadImage.ChangeAvatarResponce;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface EndPointInterface {

    /*for login */
    @POST("api/user/login")
    Call<LoginResponse> getLoginUser(@Header("Content-Type") String content, @Body JsonObject jsonObject);   //dynamic done

    @GET("api/portfolio")
    Call<PendingResponse> getPendingAPI(@Header("Content-Type") String content);

    @GET("api/profile")
    Call<ProfileResponse> UserProfile(@Header("Content-Type") String content, @Header("X-CSRF-TOKEN") String xcsrf); //done

    /* @GET("api/active-account-details")
     Call<DashboardDetailModelResponce> DashboardDetail();

     @POST("api/get-trustly-signature/")
     Call<DashboardSignatureResponce> DashboardDetailSignature(@Header("Content-Type") String content,
                                                               @Header("X-CSRF-TOKEN") String xcsrf,
                                                               @Query("method") String method,
                                                               @Query("uuid") String uuid,
                                                               @Query("data") JSONObject data);*/
    @GET("api/account-balance")
    Call<AccountResponse> getAccountBalance(@Header("Content-Type") String content, @Header("X-CSRF-TOKEN") String xcsrf);   //done for dashboard

    @POST("api/create-deposit-entry")
    Call<DashboardDepositResponse> getDashboardDeposit(@Header("Content-Type") String content, @Header("X-CSRF-TOKEN") String xcsrf, @Body JsonObject jsonObject);  //done for dashboard

    @GET("api/investments/listing")
    Call<MyInvestmentResponce> getMyInvestment(@Header("Content-Type") String content, @Header("X-CSRF-TOKEN") String xcsrf);

    @GET("api/investments/listing/")
    Call<MyInvestmentSearchResponse> getMyInvestmentSearch(@Query("company") String company, @Query("from") String from, @Query("to") String to);

    @GET("api/investment/details")
    Call<MyInvestmentDetailResponse> getMyInvestmentDetail(@Header("Content-Type") String content, @Header("X-CSRF-TOKEN") String xcsrf, @Query("investment_id") String investment_id);

    @POST("api/create-investment")
    Call<CreateInvestmentResponse> getCreateInvestment(@Header("Content-Type") String content, @Header("X-CSRF-TOKEN") String xcsrf, @Body JsonObject jsonObject);

    @GET("api/investment-cancel")
    Call<CancelInvestmentResponce> getCancelInvestment(@Header("Content-Type") String content, @Header("X-CSRF-TOKEN") String xcsrf, @Query("investment_id") String investment_id);

    @POST("api/change-email")
    Call<ChangeEmailResponse> changeEmail(@Header("Content-Type") String content, @Header("X-CSRF-TOKEN") String xcsrf, @Body JsonObject jsonObject);

    /*For Bank Account */
    @GET("api/bank-accounts")
    Call<ChangeBankAccountResponse> getChangeBankAccount(@Header("Content-Type") String content, @Header("X-CSRF-TOKEN") String xcsrf);   //done

    @POST("api/change-active-bank-account")
    Call<ActiveBankAccountResponse> getActiveBankAccount(@Header("Content-Type") String content, @Header("X-CSRF-TOKEN") String xcsrf, @Body JsonObject jsonObject); //done

    @POST("api/sendotp")
    Call<SendOTPResponse> getSendOTP(@Header("Content-Type") String content, @Header("X-CSRF-TOKEN") String xcsrf, @Body JsonObject jsonObject);

    @POST("api/update-phone")
    Call<UpdatePhoneNumberResponse> getUpdatePhone(@Header("Content-Type") String content, @Header("X-CSRF-TOKEN") String xcsrf, @Body JsonObject jsonObject);

    @GET("api/get-preferences")
    Call<ChangePreferenceResponse> getChangePreferenceAPI(@Header("Content-Type") String content, @Header("X-CSRF-TOKEN") String xcsrf);

    @POST("api/change-preferences")
    Call<UpdatePreferenceResponse> getUpdatePreferenceAPI(@Header("Content-Type") String content, @Header("X-CSRF-TOKEN") String xcsrf, @Body JsonObject jsonObject);

    @GET("api/contact-information")
    Call<ContactInfoResponse> getContactInformation(@Header("Content-Type") String content, @Header("X-CSRF-TOKEN") String xcsrf);

    @POST("api/change-password")
    Call<ChangePasswordResponse> getChangePassword(@Header("Content-Type") String content, @Header("X-CSRF-TOKEN") String xcsrf, @Body JsonObject jsonObject);  //done

    @POST("api/change-avatar")
    Call<ChangeAvatarResponce> getChangeAvtar(@Header("Content-Type") String content, @Header("X-CSRF-TOKEN") String xcsrf, @Body JsonObject jsonObject);

    @GET("api/fundraiser/listing")
    Call<InvestmentOppRes> getInvestmentOppHome(@Query("_format") String mFormate, @Query("investment_status") String investmentStatus, @Query("page") int mPage, @Header("Content-Type") String content, @Header("X-CSRF-TOKEN") String xcsrf);  //done

    @GET("api/fundraiser/details")
    Call<FundDetailResponce> getInvestmentOppDetail(@Query("loan_id") String loan_id, @Header("Content-Type") String content, @Header("X-CSRF-TOKEN") String xcsrf);  //done

    @GET("api/user/login_status")
    Call<?> getCheckUser(@Query("_format") String mFormate);

    //For Questing
    @GET("api/invest-form-preload")
    Call<InvestFormPreloadResponse> getInvestFormPreload(@Query("loan_id") String mLoanId, @Header("Content-Type") String content, @Header("X-CSRF-TOKEN") String xcsrf);

    @GET("api/invest-amount-key-up")
    Call<InvestAmountKeyUpResponse> getInvestAmountKeyUp(@Query("loan_id") String mLoanId, @Query("invest_amount") String mInvestAmount, @Header("Content-Type") String content, @Header("X-CSRF-TOKEN") String xcsrf);

    @GET("api/invest-survey-question")
    Call<InvestSurveyRuestionResponse> getInvestSurvey(@Query("loan_id") String mLoanId, @Query("invest_amount") String mInvestAmount, @Header("Content-Type") String content, @Header("X-CSRF-TOKEN") String xcsrf);

    @POST("api/survey-form-data-insert")
    Call<SurveyFormDataInsertResponse> getSurveyFormDataInserted(@Header("Content-Type") String content, @Header("X-CSRF-TOKEN") String xcsrf, @Body JsonObject jsonObject);

}
