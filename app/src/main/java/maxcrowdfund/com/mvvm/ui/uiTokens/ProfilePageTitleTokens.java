
package maxcrowdfund.com.mvvm.ui.uiTokens;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfilePageTitleTokens {

    @SerializedName("profile_page_title")
    @Expose
    private String profilePageTitle;

    public String getProfilePageTitle() {
        return profilePageTitle;
    }

    public void setProfilePageTitle(String profilePageTitle) {
        this.profilePageTitle = profilePageTitle;
    }

}
