package id.web.hangga.frauds.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Report implements Parcelable {
    public static final Parcelable.Creator<Report> CREATOR = new Parcelable.Creator<Report>() {
        @Override
        public Report createFromParcel(Parcel source) {
            return new Report(source);
        }

        @Override
        public Report[] newArray(int size) {
            return new Report[size];
        }
    };
    private int id;
    private Date createdAt;
    private Date updatedAt;
    private String number;
    private boolean no_telp;
    private boolean no_rek;

    public Report() {
    }

    protected Report(Parcel in) {
        this.id = in.readInt();
        long tmpCreatedAt = in.readLong();
        this.createdAt = tmpCreatedAt == -1 ? null : new Date(tmpCreatedAt);
        long tmpUpdatedAt = in.readLong();
        this.updatedAt = tmpUpdatedAt == -1 ? null : new Date(tmpUpdatedAt);
        this.number = in.readString();
        this.no_telp = in.readByte() != 0;
        this.no_rek = in.readByte() != 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public boolean isNo_telp() {
        return no_telp;
    }

    public void setNo_telp(boolean no_telp) {
        this.no_telp = no_telp;
    }

    public boolean isNo_rek() {
        return no_rek;
    }

    public void setNo_rek(boolean no_rek) {
        this.no_rek = no_rek;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeLong(this.createdAt != null ? this.createdAt.getTime() : -1);
        dest.writeLong(this.updatedAt != null ? this.updatedAt.getTime() : -1);
        dest.writeString(this.number);
        dest.writeByte(this.no_telp ? (byte) 1 : (byte) 0);
        dest.writeByte(this.no_rek ? (byte) 1 : (byte) 0);
    }
}
