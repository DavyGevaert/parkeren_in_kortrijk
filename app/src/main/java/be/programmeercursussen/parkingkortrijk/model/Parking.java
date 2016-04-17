package be.programmeercursussen.parkingkortrijk.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.net.URI;
import java.util.ArrayList;

/**
 * Created by Davy on 13/01/2016.
 */
public class Parking implements Parcelable {
    private String parkingName;
    private Status status;

    private URI logoUrl;
    private URI url;
    private Contact contact;
    private Adress adress;
    private GeoLocation geoLocation;
    private Fare fare;
    private ArrayList<Fare> fares;
    private ArrayList<Parking> parkingArrayList;

    // constructor 1
    public Parking() {
        // empty constructor
    }

    // constructor 2
    public Parking(String parkingName) {
        this.parkingName = parkingName;
    }

    public Parking(String parkingName, URI logoUrl, URI url, Contact contact, Adress adress, GeoLocation geoLocation, Fare fare) {
        this.parkingName = parkingName;
        this.logoUrl = logoUrl;
        this.url = url;
        this.contact = contact;
        this.adress = adress;
        this.geoLocation = geoLocation;
        this.fare = fare;

        // initialiseren ArrayLists
        this.fares = new ArrayList<Fare>();
        this.parkingArrayList = new ArrayList<Parking>();
    }

    public String getParkingName() {
        return parkingName;
    }

    public void setParkingName(String parkingName) {
        this.parkingName = parkingName;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public URI getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(URI logoUrl) {
        this.logoUrl = logoUrl;
    }

    public URI getUrl() {
        return url;
    }

    public void setUrl(URI url) {
        this.url = url;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public Adress getAdress() {
        return adress;
    }

    public void setAdress(Adress adress) {
        this.adress = adress;
    }

    public GeoLocation getGeoLocation() {
        return geoLocation;
    }

    public void setGeoLocation(GeoLocation geoLocation) {
        this.geoLocation = geoLocation;
    }

    public Fare getFare() {
        return fare;
    }

    public void setFare(Fare fare) {
        this.fare = fare;
    }

    public ArrayList<Fare> getFares() {
        return fares;
    }

    public void setFares(ArrayList<Fare> fares) {
        this.fares = fares;
    }

    public ArrayList<Parking> getParkingArrayList() {
        return parkingArrayList;
    }

    public void setParkingArrayList(ArrayList<Parking> parkingArrayList) {
        this.parkingArrayList = parkingArrayList;
    }

    public Parking(Parcel in) {
        parkingName = in.readString();
        status = (Status) in.readParcelable(Status.class.getClassLoader());
        adress = (Adress) in.readParcelable(Adress.class.getClassLoader());
        contact = (Contact) in.readParcelable(Contact.class.getClassLoader());
        geoLocation = (GeoLocation) in.readParcelable(GeoLocation.class.getClassLoader());
        fare = (Fare) in.readParcelable(Fare.class.getClassLoader());
        fares = in.readArrayList(Fare.class.getClassLoader());
        parkingArrayList = in.readArrayList(Parking.class.getClassLoader());
    }

    public static final Parcelable.Creator<Parking> CREATOR =
            new Parcelable.Creator<Parking>() {
                public Parking createFromParcel(Parcel pc) {
                    return new Parking(pc);
                }

                @Override
                public Parking[] newArray (int size) {
                    return new Parking[size];
                }
            };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(parkingName);
        dest.writeParcelable(status, flags);
        dest.writeParcelable(adress, flags);
        dest.writeParcelable(contact, flags);
        dest.writeParcelable(geoLocation, flags);
        dest.writeParcelable(fare, flags);
        dest.writeList(fares);
        dest.writeList(parkingArrayList);
    }
}
