package be.programmeercursussen.parkingkortrijk.callback;

import java.util.ArrayList;

import be.programmeercursussen.parkingkortrijk.model.Parking;

/**
 * Created by Davy on 13/01/2016.
 */
public interface AsyncParkingResponse {
    void processFinish(ArrayList<Parking> parkingArrayList);
}
