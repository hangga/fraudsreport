package id.web.hangga.frauds.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Frauds implements Parcelable {
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
    private Date createdAt;
    private Date updatedAt;
    private String jenis_penipuan;
    private String jumlah_kerugian;
    private String kota_korban;

    public Frauds() {
    }

    protected Frauds(Parcel in) {
        this.id = in.readInt();
        this.reportId = in.readInt();
        long tmpCreatedAt = in.readLong();
        this.createdAt = tmpCreatedAt == -1 ? null : new Date(tmpCreatedAt);
        long tmpUpdatedAt = in.readLong();
        this.updatedAt = tmpUpdatedAt == -1 ? null : new Date(tmpUpdatedAt);
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getJenis_penipuan() {
        return jenis_penipuan;
    }

    public void setJenis_penipuan(String jenis_penipuan) {
        this.jenis_penipuan = jenis_penipuan;
    }

    public String getJumlah_kerugian() {
        return jumlah_kerugian;
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
        dest.writeLong(this.createdAt != null ? this.createdAt.getTime() : -1);
        dest.writeLong(this.updatedAt != null ? this.updatedAt.getTime() : -1);
        dest.writeString(this.jenis_penipuan);
        dest.writeString(this.jumlah_kerugian);
        dest.writeString(this.kota_korban);
    }
}
