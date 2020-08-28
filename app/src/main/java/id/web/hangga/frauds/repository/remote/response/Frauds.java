package id.web.hangga.frauds.repository.remote.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Frauds {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("reportId")
    @Expose
    private int reportId;
    @SerializedName("createdAt")
    @Expose
    private Date createdAt;
    @SerializedName("updatedAt")
    @Expose
    private Date updatedAt;
    @SerializedName("jenis_penipuan")
    @Expose
    private String jenis_penipuan;
    @SerializedName("jumlah_kerugian")
    @Expose
    private String jumlah_kerugian;
    @SerializedName("kota_korban")
    @Expose
    private String kota_korban;
}
