package be.programmeercursussen.parkingkortrijk.callback;

import java.util.ArrayList;

import be.programmeercursussen.parkingkortrijk.model.Sensor;

/**
 * Created by Davy on 15/01/2016.
 */
public interface AsyncSensorResponse {
    void processFinish(ArrayList<Sensor> sensorArrayList);
}
