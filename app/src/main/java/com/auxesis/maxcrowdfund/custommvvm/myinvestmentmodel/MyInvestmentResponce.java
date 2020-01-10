
package com.auxesis.maxcrowdfund.custommvvm.myinvestmentmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MyInvestmentResponce {

    @SerializedName("total")
    @Expose
    private Integer total;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

}
