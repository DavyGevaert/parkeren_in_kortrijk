package be.programmeercursussen.parkingkortrijk.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Davy on 14/01/2016.
 */
public class Status implements Parcelable {
    private String description;
    private int totalcapacity;
    private int occupied;
    private int availableCapacity;

    public Status() {
        // empty constructor
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTotalcapacity() {
        return totalcapacity;
    }

    public void setTotalcapacity(int totalcapacity) {
        this.totalcapacity = totalcapacity;
    }

    public int getOccupied() {
        return occupied;
    }

    public void setOccupied(int occupied) {
        this.occupied = occupied;
    }

    public int getAvailableCapacity() {
        return availableCapacity;
    }

    public void setAvailableCapacity(int availableCapacity) {
        this.availableCapacity = availableCapacity;
    }

    public Status(Parcel in) {
        description = in.readString();
        totalcapacity = in.readInt();
        occupied = in.readInt();
        availableCapacity = in.readInt();
    }

    public static final Parcelable.Creator<Status> CREATOR =
            new Parcelable.Creator<Status>() {
                public Status createFromParcel(Parcel pc) {
                    return new Status(pc);
                }

                @Override
                public Status[] newArray (int size) {
                    return new Status[size];
                }
            };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(description);
        dest.writeInt(totalcapacity);
        dest.writeInt(occupied);
        dest.writeInt(availableCapacity);
    }
}
