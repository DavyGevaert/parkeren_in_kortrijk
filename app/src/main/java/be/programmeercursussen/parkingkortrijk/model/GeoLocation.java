package be.programmeercursussen.parkingkortrijk.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Davy on 17/01/2015.
 */
public class GeoLocation implements Parcelable {
    private String latitude;
    private String longitude;

    public GeoLocation() {
        // empty constructor
    }

    public GeoLocation(String longitude, String latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    // constructor voor Parcel
    public GeoLocation(Parcel in) {
        latitude = in.readString();
        longitude = in.readString();
    }

    public static final Creator<GeoLocation> CREATOR =
            new Creator<GeoLocation>() {
                public GeoLocation createFromParcel(Parcel pc) {
                    return new GeoLocation(pc);
                }

                @Override
                public GeoLocation[] newArray (int size) {
                    return new GeoLocation[size];
                }
            };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(latitude);
        dest.writeString(longitude);
    }
}
