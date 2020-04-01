
package com.auxesis.maxcrowdfund.mvvm.ui.contactinformation.contactInformationModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ContactInfoResponse {

    @SerializedName("contact_information")
    @Expose
    private ContactInformation contactInformation;

    public ContactInformation getContactInformation() {
        return contactInformation;
    }

    public void setContactInformation(ContactInformation contactInformation) {
        this.contactInformation = contactInformation;
    }

}
