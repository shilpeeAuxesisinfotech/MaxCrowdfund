package maxcrowdfund.com.restapi;

import maxcrowdfund.com.mvvm.ui.BankTransferDetail.model.BankTransferDetailMailResponse;
import maxcrowdfund.com.mvvm.ui.BankTransferDetail.model.BankTransferDetailResponse;
import maxcrowdfund.com.mvvm.ui.changeMobileNumber.changeMobileModel.SendOTPResponse;
import maxcrowdfund.com.mvvm.ui.changeMobileNumber.changeMobileModel.UpdatePhoneNumberResponse;
import maxcrowdfund.com.mvvm.ui.changePassword.changePassModel.ChangePasswordResponse;
import maxcrowdfund.com.mvvm.ui.changePreference.model.ChangePreferenceResponse;
import maxcrowdfund.com.mvvm.ui.changePreference.model.UpdatePreferenceResponse;
import maxcrowdfund.com.mvvm.ui.changebankaccount.changebankaccountmodel.ActiveBankAccountResponse;
import maxcrowdfund.com.mvvm.ui.changebankaccount.changebankaccountmodel.ChangeBankAccountResponse;
import maxcrowdfund.com.mvvm.ui.changeemail.ChangeEmailResponse;
import maxcrowdfund.com.mvvm.ui.contactinformation.contactInformationModel.ContactInfoResponse;
import maxcrowdfund.com.mvvm.ui.dashborad.dashboarddeposit.DashboardDepositResponse;
import maxcrowdfund.com.mvvm.ui.dashborad.model.AccountBalanceResponse;
import maxcrowdfund.com.mvvm.ui.home.homeDetail.funddetail.FundraiserDetailResponse;
import maxcrowdfund.com.mvvm.ui.home.homeDetail.investmodel.CreateInvestmentResponse;
import maxcrowdfund.com.mvvm.ui.home.oppmodel.InvestmentOppRes;
import maxcrowdfund.com.mvvm.ui.investform.questmodel.famodel.TfaValidateResponse;
import maxcrowdfund.com.mvvm.ui.investform.questmodel.model.SurveyFormDataInsertResponse;
import maxcrowdfund.com.mvvm.ui.investform.questmodel.question.InvestSurveyQuestionResponse;
import maxcrowdfund.com.mvvm.ui.investform.questmodel.questionlistmodel.InvestAmountKeyUpResponse;
import maxcrowdfund.com.mvvm.ui.login.LoginResponse;
import maxcrowdfund.com.mvvm.ui.myinvestments.myinvestmentdetail.myinvestmentdetailmodel.CancelInvestmentResponce;
import maxcrowdfund.com.mvvm.ui.myinvestments.myinvestmentdetail.myinvestmentdetailmodel.MyInvestmentDetailResponse;
import maxcrowdfund.com.mvvm.ui.myinvestments.model.MyInvestmentResponce;
import maxcrowdfund.com.mvvm.ui.investform.questmodel.InvestFormPreloadResponse;
import maxcrowdfund.com.mvvm.ui.myprofile.model.ProfileResponse;
import maxcrowdfund.com.mvvm.ui.uploadImage.ChangeAvatarResponce;
import com.google.gson.JsonObject;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface EndPointInterface {
    @POST("api/user/login")
    Call<LoginResponse> getLoginUser(@Query("_format") String mFormate,@Header("Content-Type") String content, @Body JsonObject jsonObject);

    @GET("api/profile")
    Call<ProfileResponse> UserProfile(@Header("Content-Type") String content, @Header("X-CSRF-TOKEN") String xcsrf);

    @GET("api/account-balance")
    Call<AccountBalanceResponse> getAccountBalance(@Header("Content-Type") String content, @Header("X-CSRF-TOKEN") String xcsrf);

    @POST("api/create-deposit-entry")
    Call<DashboardDepositResponse> getDashboardDeposit(@Header("Content-Type") String content, @Header("X-CSRF-TOKEN") String xcsrf, @Body JsonObject jsonObject);

    @GET("api/investments/listing")
    Call<MyInvestmentResponce> getMyInvestment(@Header("Content-Type") String content, @Header("X-CSRF-TOKEN") String xcsrf);

    @GET("api/investment/details")
    Call<MyInvestmentDetailResponse> getMyInvestmentDetail(@Header("Content-Type") String content, @Header("X-CSRF-TOKEN") String xcsrf, @Query("investment_id") String investment_id);

    @POST("api/create-investment")
    Call<CreateInvestmentResponse> getCreateInvestment(@Header("Content-Type") String content, @Header("X-CSRF-TOKEN") String xcsrf, @Body JsonObject jsonObject);

    @GET("api/investment-cancel")
    Call<CancelInvestmentResponce> getCancelInvestment(@Header("Content-Type") String content, @Header("X-CSRF-TOKEN") String xcsrf, @Query("investment_id") String investment_id);

    @POST("api/change-email")
    Call<ChangeEmailResponse> changeEmail(@Header("Content-Type") String content, @Header("X-CSRF-TOKEN") String xcsrf, @Body JsonObject jsonObject);

    @GET("api/bank-accounts")
    Call<ChangeBankAccountResponse> getChangeBankAccount(@Header("Content-Type") String content, @Header("X-CSRF-TOKEN") String xcsrf);

    @POST("api/change-active-bank-account")
    Call<ActiveBankAccountResponse> getActiveBankAccount(@Header("Content-Type") String content, @Header("X-CSRF-TOKEN") String xcsrf, @Body JsonObject jsonObject);

    @POST("api/sendotp")
    Call<SendOTPResponse> getSendOTP(@Header("Content-Type") String content, @Header("X-CSRF-TOKEN") String xcsrf, @Body JsonObject jsonObject);

    @POST("api/sendotp")
    Call<SendOTPResponse> getTwoFASendOTP(@Header("Content-Type") String content, @Header("X-CSRF-TOKEN") String xcsrf, @Body JsonObject jsonObject);

    @POST("api/update-phone")
    Call<UpdatePhoneNumberResponse> getUpdatePhone(@Header("Content-Type") String content, @Header("X-CSRF-TOKEN") String xcsrf, @Body JsonObject jsonObject);

    @GET("api/get-preferences")
    Call<ChangePreferenceResponse> getChangePreferenceAPI(@Header("Content-Type") String content, @Header("X-CSRF-TOKEN") String xcsrf);

    @POST("api/change-preferences")
    Call<UpdatePreferenceResponse> getUpdatePreferenceAPI(@Header("Content-Type") String content, @Header("X-CSRF-TOKEN") String xcsrf, @Body JsonObject jsonObject);

    @GET("api/contact-information")
    Call<ContactInfoResponse> getContactInformation(@Header("Content-Type") String content, @Header("X-CSRF-TOKEN") String xcsrf);

    @POST("api/change-password")
    Call<ChangePasswordResponse> getChangePassword(@Header("Content-Type") String content, @Header("X-CSRF-TOKEN") String xcsrf, @Body JsonObject jsonObject);

    @POST("api/change-avatar")
    Call<ChangeAvatarResponce> getChangeAvtar(@Header("Content-Type") String content, @Header("X-CSRF-TOKEN") String xcsrf, @Body JsonObject jsonObject);

    @GET("api/fundraiser/listing")
    Call<InvestmentOppRes> getInvestmentOppHome(@Query("_format") String mFormate, @Query("investment_status") String investmentStatus, @Query("page") int mPage, @Header("Content-Type") String content, @Header("X-CSRF-TOKEN") String xcsrf);

    @GET("api/fundraiser/details")
    Call<FundraiserDetailResponse> getInvestmentOppDetail(@Query("loan_id") String loan_id, @Header("Content-Type") String content, @Header("X-CSRF-TOKEN") String xcsrf);

    @GET("api/user/login_status")
    Call<?> getCheckUser(@Query("_format") String mFormate);

    @GET("api/invest-form-preload")
    Call<InvestFormPreloadResponse> getInvestFormPreload(@Query("loan_id") String mLoanId, @Header("Content-Type") String content, @Header("X-CSRF-TOKEN") String xcsrf);

    @GET("api/invest-amount-key-up")
    Call<InvestAmountKeyUpResponse> getInvestAmountKeyUp(@Query("loan_id") String mLoanId, @Query("invest_amount") String mInvestAmount, @Header("Content-Type") String content, @Header("X-CSRF-TOKEN") String xcsrf);

    @GET("api/invest-survey-question")
    Call<InvestSurveyQuestionResponse> getInvestSurvey(@Query("loan_id") String mLoanId, @Query("invest_amount") String mInvestAmount, @Header("Content-Type") String content, @Header("X-CSRF-TOKEN") String xcsrf);

    @POST("api/survey-form-data-insert")
    Call<SurveyFormDataInsertResponse> getSurveyFormDataInserted(@Header("Content-Type") String content, @Header("X-CSRF-TOKEN") String xcsrf, @Body JsonObject jsonObject);

    @GET("api/tfa-validate")
    Call<TfaValidateResponse> getTFAValidated(@Header("Content-Type") String content, @Header("X-CSRF-TOKEN") String xcsrf, @Query("tfa_type") String mTfaType, @Query("code") String mCode, @Query("code_submit") String mCodeSubmit);

    @GET("api/bank-transfer-detail")
    Call<BankTransferDetailResponse> getBankTransferDetail(@Header("Content-Type") String content, @Header("X-CSRF-TOKEN") String xcsrf);

    @GET("api/bank-transfer-detail-mail")
    Call<BankTransferDetailMailResponse> getBankTransferDetailMail(@Header("Content-Type") String content, @Header("X-CSRF-TOKEN") String xcsrf);
}
