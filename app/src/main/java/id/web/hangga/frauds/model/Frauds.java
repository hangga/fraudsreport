package id.web.hangga.frauds.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

import id.web.hangga.frauds.util.Prop;

public class Frauds extends Report implements Parcelable {
    public static final Parcelable.Creator<Frauds> CREATOR = new Parcelable.Creator<Frauds>() {
        @Override
        public Frauds createFromParcel(Parcel source) {
            return new Frauds(source);
        }

        @Override
        public Frauds[] newArray(int size) {
            return new Frauds[size];
        }
    };
    private int id;
    private int reportId;
    private String jenis_penipuan;
    private String jumlah_kerugian;
    private String kota_korban;

    public Frauds() {
        this.setType(Prop.TYPE_ITEM_FRAUD);
    }

    protected Frauds(Parcel in) {
        this.id = in.readInt();
        this.reportId = in.readInt();
        this.createdAt = in.readString();
        this.upStringdAt = in.readString();
        this.jenis_penipuan = in.readString();
        this.jumlah_kerugian = in.readString();
        this.kota_korban = in.readString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getReportId() {
        return reportId;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
    }

    public String getJenis_penipuan() {
        return jenis_penipuan;
    }

    public void setJenis_penipuan(String jenis_penipuan) {
        this.jenis_penipuan = jenis_penipuan;
    }

    public Double getJumlah_kerugian() {
        return jumlah_kerugian.length()>0? Double.parseDouble(jumlah_kerugian.replace(",",".")):0;
    }

    public void setJumlah_kerugian(String jumlah_kerugian) {
        this.jumlah_kerugian = jumlah_kerugian;
    }

    public String getKota_korban() {
        return kota_korban;
    }

    public void setKota_korban(String kota_korban) {
        this.kota_korban = kota_korban;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.reportId);
        dest.writeString(this.createdAt);
        dest.writeString(this.upStringdAt);
        dest.writeString(this.jenis_penipuan);
        dest.writeString(this.jumlah_kerugian);
        dest.writeString(this.kota_korban);
    }
}
