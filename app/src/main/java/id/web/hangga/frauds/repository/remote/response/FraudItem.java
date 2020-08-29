package id.web.hangga.frauds.repository.remote.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

import id.web.hangga.frauds.model.Frauds;
import id.web.hangga.frauds.model.Report;
import id.web.hangga.frauds.util.Prop;

public class FraudItem {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("reportId")
    @Expose
    private int reportId;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("jenis_penipuan")
    @Expose
    private String jenis_penipuan;
    @SerializedName("jumlah_kerugian")
    @Expose
    private String jumlah_kerugian;
    @SerializedName("kota_korban")
    @Expose
    private String kota_korban;

    public id.web.hangga.frauds.model.Frauds toFraudsParcel(){
        id.web.hangga.frauds.model.Frauds frauds = new id.web.hangga.frauds.model.Frauds();
        frauds.setId(id);
        frauds.setReportId(reportId);
        frauds.setCreatedAt(createdAt);
        frauds.setUpdatedAt(updatedAt);
        frauds.setJenis_penipuan(jenis_penipuan);
        frauds.setJumlah_kerugian(jumlah_kerugian);
        frauds.setKota_korban(kota_korban);
        frauds.setType(Prop.TYPE_ITEM_FRAUD);
        return frauds;
    }
}
