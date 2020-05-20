
package maxcrowdfund.com.mvvm.ui.dashborad.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data__ {

    @SerializedName("net_return")
    @Expose
    private NetReturn_ netReturn;
    @SerializedName("average_return")
    @Expose
    private AverageReturn averageReturn;
    @SerializedName("incidental_payments")
    @Expose
    private IncidentalPayments incidentalPayments;
    @SerializedName("fees")
    @Expose
    private Fees_ fees;
    @SerializedName("reserved")
    @Expose
    private Reserved__ reserved;
    @SerializedName("written_off")
    @Expose
    private WrittenOff_ writtenOff;
    @SerializedName("last_net_return")
    @Expose
    private LastNetReturn lastNetReturn;

    public NetReturn_ getNetReturn() {
        return netReturn;
    }

    public void setNetReturn(NetReturn_ netReturn) {
        this.netReturn = netReturn;
    }

    public AverageReturn getAverageReturn() {
        return averageReturn;
    }

    public void setAverageReturn(AverageReturn averageReturn) {
        this.averageReturn = averageReturn;
    }

    public IncidentalPayments getIncidentalPayments() {
        return incidentalPayments;
    }

    public void setIncidentalPayments(IncidentalPayments incidentalPayments) {
        this.incidentalPayments = incidentalPayments;
    }

    public Fees_ getFees() {
        return fees;
    }

    public void setFees(Fees_ fees) {
        this.fees = fees;
    }

    public Reserved__ getReserved() {
        return reserved;
    }

    public void setReserved(Reserved__ reserved) {
        this.reserved = reserved;
    }

    public WrittenOff_ getWrittenOff() {
        return writtenOff;
    }

    public void setWrittenOff(WrittenOff_ writtenOff) {
        this.writtenOff = writtenOff;
    }

    public LastNetReturn getLastNetReturn() {
        return lastNetReturn;
    }

    public void setLastNetReturn(LastNetReturn lastNetReturn) {
        this.lastNetReturn = lastNetReturn;
    }

}
