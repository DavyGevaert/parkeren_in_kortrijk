package be.programmeercursussen.parkingkortrijk.callback;

import java.util.ArrayList;

import be.programmeercursussen.parkingkortrijk.model.Placemark;

/**
 * Created by Davy on 22/01/2016.
 */
public interface AsyncPlacemarkResponse {
    void processFinish(ArrayList<Placemark> placemarkArrayList);
}
