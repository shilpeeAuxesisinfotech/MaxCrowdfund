
package maxcrowdfund.com.mvvm.ui.uiTokens;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UiLoginTokens {

    @SerializedName("user_login_description")
    @Expose
    private String userLoginDescription;
    @SerializedName("user_login_email_placeholder")
    @Expose
    private String userLoginEmailPlaceholder;
    @SerializedName("user_login_password_placehplder")
    @Expose
    private String userLoginPasswordPlacehplder;
    @SerializedName("user_login_btn_login")
    @Expose
    private String userLoginBtnLogin;

    public String getUserLoginDescription() {
        return userLoginDescription;
    }

    public void setUserLoginDescription(String userLoginDescription) {
        this.userLoginDescription = userLoginDescription;
    }

    public String getUserLoginEmailPlaceholder() {
        return userLoginEmailPlaceholder;
    }

    public void setUserLoginEmailPlaceholder(String userLoginEmailPlaceholder) {
        this.userLoginEmailPlaceholder = userLoginEmailPlaceholder;
    }

    public String getUserLoginPasswordPlacehplder() {
        return userLoginPasswordPlacehplder;
    }

    public void setUserLoginPasswordPlacehplder(String userLoginPasswordPlacehplder) {
        this.userLoginPasswordPlacehplder = userLoginPasswordPlacehplder;
    }

    public String getUserLoginBtnLogin() {
        return userLoginBtnLogin;
    }

    public void setUserLoginBtnLogin(String userLoginBtnLogin) {
        this.userLoginBtnLogin = userLoginBtnLogin;
    }

}
