package be.programmeercursussen.parkingkortrijk.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Davy on 15/01/2016.
 */
public class Sensor implements Parcelable {
    private String code;
    private String state;
    private String parkingbay;
    private String street;
    private String latitude;
    private String longitude;

    public Sensor() {
        // empty constructor
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getParkingbay() {
        return parkingbay;
    }

    public void setParkingbay(String parkingbay) {
        this.parkingbay = parkingbay;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
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

    public Sensor(Parcel in) {
        code = in.readString();
        state = in.readString();
        parkingbay = in.readString();
        street = in.readString();
        latitude = in.readString();
        longitude = in.readString();
    }

    public static final Parcelable.Creator<Sensor> CREATOR =
            new Parcelable.Creator<Sensor>() {
                public Sensor createFromParcel(Parcel pc) {
                    return new Sensor(pc);
                }

                @Override
                public Sensor[] newArray (int size) {
                    return new Sensor[size];
                }
            };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(code);
        dest.writeString(state);
        dest.writeString(parkingbay);
        dest.writeString(street);
        dest.writeString(latitude);
        dest.writeString(longitude);
    }
}
