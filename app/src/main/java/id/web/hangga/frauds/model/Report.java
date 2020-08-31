package id.web.hangga.frauds.model;

import android.os.Parcel;
import android.os.Parcelable;


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
    String createdAt;
    String upStringdAt;
    private int id;
    private String number;
    private boolean no_telp = false;
    private boolean no_rek = false;
    private int type;
    private boolean isNew;

    public Report() {
    }

    protected Report(Parcel in) {
        this.id = in.readInt();
        this.createdAt = in.readString();
        this.upStringdAt = in.readString();
        this.number = in.readString();
        this.no_telp = in.readBoolean(); //in.readByte() != 0;
        this.no_rek = in.readBoolean(); //in.readByte() != 0;
        this.type = in.readInt();
    }

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpStringdAt() {
        return upStringdAt;
    }

    public void setUpdatedAt(String upStringdAt) {
        this.upStringdAt = upStringdAt;
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
        dest.writeString(this.createdAt);
        dest.writeString(this.upStringdAt);
        dest.writeString(this.number);
        dest.writeBoolean(this.no_telp);
        dest.writeBoolean(this.no_rek);
        dest.writeInt(this.type);
    }
}
