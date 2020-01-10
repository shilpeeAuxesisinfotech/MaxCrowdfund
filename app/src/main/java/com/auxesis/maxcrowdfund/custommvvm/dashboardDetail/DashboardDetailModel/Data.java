
package com.auxesis.maxcrowdfund.custommvvm.dashboardDetail.DashboardDetailModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("logged_in_user_id")
    @Expose
    private Integer loggedInUserId;
    @SerializedName("active_account_type")
    @Expose
    private String activeAccountType;
    @SerializedName("active_account_id")
    @Expose
    private Integer activeAccountId;
    @SerializedName("country_code")
    @Expose
    private String countryCode;
    @SerializedName("address_line1")
    @Expose
    private String addressLine1;
    @SerializedName("address_line2")
    @Expose
    private String addressLine2;
    @SerializedName("postal_code")
    @Expose
    private Integer postalCode;
    @SerializedName("locality")
    @Expose
    private String locality;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("mobile_phone")
    @Expose
    private String mobilePhone;

    public Integer getLoggedInUserId() {
        return loggedInUserId;
    }

    public void setLoggedInUserId(Integer loggedInUserId) {
        this.loggedInUserId = loggedInUserId;
    }

    public String getActiveAccountType() {
        return activeAccountType;
    }

    public void setActiveAccountType(String activeAccountType) {
        this.activeAccountType = activeAccountType;
    }

    public Integer getActiveAccountId() {
        return activeAccountId;
    }

    public void setActiveAccountId(Integer activeAccountId) {
        this.activeAccountId = activeAccountId;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public Integer getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(Integer postalCode) {
        this.postalCode = postalCode;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

}
