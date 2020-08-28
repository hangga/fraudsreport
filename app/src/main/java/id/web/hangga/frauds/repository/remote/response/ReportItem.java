package id.web.hangga.frauds.repository.remote.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class ReportItem {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("createdAt")
    @Expose
    private Date createdAt;
    @SerializedName("updatedAt")
    @Expose
    private Date updatedAt;
    @SerializedName("number")
    @Expose
    private String number;
    @SerializedName("no_telp")
    @Expose
    private boolean no_telp;
    @SerializedName("no_rek")
    @Expose
    private boolean no_rek;
}
