package id.web.hangga.frauds.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Sumary extends Report implements Parcelable {
    public static final Parcelable.Creator<Sumary> CREATOR = new Parcelable.Creator<Sumary>() {
        @Override
        public Sumary createFromParcel(Parcel source) {
            return new Sumary(source);
        }

        @Override
        public Sumary[] newArray(int size) {
            return new Sumary[size];
        }
    };
    private int totalRek;
    private int totalTelp;
    private Double totalRugi;
    private Double newRugi;

    public Sumary() {
        this.setType(Report.TYPE_SUMMARY);
    }

    protected Sumary(Parcel in) {
        this.totalRek = in.readInt();
        this.totalTelp = in.readInt();
        this.totalRugi = (Double) in.readValue(Double.class.getClassLoader());
        this.newRugi = (Double) in.readValue(Double.class.getClassLoader());
    }

    public int getTotalRek() {
        return totalRek;
    }

    public void setTotalRek(int totalRek) {
        this.totalRek = totalRek;
    }

    public int getTotalTelp() {
        return totalTelp;
    }

    public void setTotalTelp(int totalTelp) {
        this.totalTelp = totalTelp;
    }

    public Double getTotalRugi() {
        return totalRugi;
    }

    public void setTotalRugi(Double totalRugi) {
        this.totalRugi = totalRugi;
    }

    public Double getNewRugi() {
        return newRugi;
    }

    public void setNewRugi(Double newRugi) {
        this.newRugi = newRugi;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.totalRek);
        dest.writeInt(this.totalTelp);
        dest.writeValue(this.totalRugi);
        dest.writeValue(this.newRugi);
    }
}
