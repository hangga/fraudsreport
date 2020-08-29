package id.web.hangga.frauds.repository.remote.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

import id.web.hangga.frauds.model.Report;
import id.web.hangga.frauds.util.Prop;

public class ReportItem {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("number")
    @Expose
    private String number;
    @SerializedName("no_telp")
    @Expose
    private boolean no_telp;
    @SerializedName("no_rek")
    @Expose
    private boolean no_rek;

    public Report toReportparcel(){
        Report report = new Report();
        report.setId(id);
        report.setCreatedAt(createdAt);
        report.setUpdatedAt(updatedAt);
        report.setNumber(number);
        report.setNo_rek(no_rek);
        report.setNo_telp(no_telp);
        report.setType(Prop.TYPE_ITEM_REPORT);
        return report;
    }
}
