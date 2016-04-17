package be.programmeercursussen.parkingkortrijk.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Davy on 20/01/2015.
 */
public class Fare implements Parcelable {
    private String fareName;
    private String amount;
    private String exchange;
    private String time;
    private String timeLimit;
    private String userType1;
    private String userType2;
    private String vehicle;
    private String fee;

    public Fare() {
        // empty constructor
    }

    public String getFareName() {
        return fareName;
    }

    public void setFareName(String fareName) {
        this.fareName = fareName;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(String timeLimit) {
        this.timeLimit = timeLimit;
    }

    public String getUserType1() {
        return userType1;
    }

    public void setUserType1(String userType1) {
        this.userType1 = userType1;
    }

    public String getUserType2() {
        return userType2;
    }

    public void setUserType2(String userType2) {
        this.userType2 = userType2;
    }

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    // constructor voor Parcel
    public Fare(Parcel in) {
        fareName = in.readString();
        amount = in.readString();
        exchange = in.readString();
        time = in.readString();
        timeLimit = in.readString();
        userType1 = in.readString();
        userType2 = in.readString();
        vehicle = in.readString();
        fee = in.readString();
    }

    public static final Creator<Fare> CREATOR =
            new Creator<Fare>() {
                public Fare createFromParcel(Parcel pc) {
                    return new Fare(pc);
                }

                @Override
                public Fare[] newArray (int size) {
                    return new Fare[size];
                }
            };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(fareName);
        dest.writeString(amount);
        dest.writeString(exchange);
        dest.writeString(time);
        dest.writeString(timeLimit);
        dest.writeString(userType1);
        dest.writeString(userType2);
        dest.writeString(vehicle);
        dest.writeString(fee);
    }

    // voor debug logging
    @Override
    public String toString() {
        return fareName;
    }


}
