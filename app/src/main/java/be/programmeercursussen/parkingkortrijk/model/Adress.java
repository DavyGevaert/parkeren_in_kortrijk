package be.programmeercursussen.parkingkortrijk.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Davy on 17/01/2015.
 */
public class Adress implements Parcelable {
    private String streetName;
    private long postalCode;
    private String city;

    public Adress() {
        // empty constructor
    }

    public Adress(String streetName, long postalCode, String city) {
        this.streetName = streetName;
        this.postalCode = postalCode;
        this.city = city;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public long getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(long postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    // constructor voor Parcel
    public Adress(Parcel in) {
        streetName = in.readString();
        postalCode = in.readLong();
        city = in.readString();
    }

    public static final Creator<Adress> CREATOR =
            new Creator<Adress>() {
                public Adress createFromParcel(Parcel pc) {
                    return new Adress(pc);
                }

                @Override
                public Adress[] newArray (int size) {
                    return new Adress[size];
                }
            };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(streetName);
        dest.writeLong(postalCode);
        dest.writeString(city);
    }
}
