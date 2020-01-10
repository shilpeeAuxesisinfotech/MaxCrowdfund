
package com.auxesis.maxcrowdfund.custommvvm.myinvestmentmodel;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyInvestmentSearchResponse {

    @SerializedName("total")
    @Expose
    private Integer total;
    @SerializedName("data")
    @Expose
    private List<SearchData> data = null;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<SearchData> getData() {
        return data;
    }

    public void setData(List<SearchData> data) {
        this.data = data;
    }

}
