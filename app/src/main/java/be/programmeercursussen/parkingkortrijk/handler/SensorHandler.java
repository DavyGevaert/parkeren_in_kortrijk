package be.programmeercursussen.parkingkortrijk.handler;

import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

import be.programmeercursussen.parkingkortrijk.model.Sensor;

/**
 * Created by Davy on 15/01/2016.
 */
public class SensorHandler extends DefaultHandler {

    private static String TAG = "SensorHandler";

    // This is the list which shall be populated while parsing in the XML
    public ArrayList<Sensor> sensorList = new ArrayList<Sensor>();

    // construct Sensor object on global scope
    private Sensor sensor;

    // getter method for sensorList
    public ArrayList<Sensor> getSensors() {
        return sensorList;
    }

    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        // if this is start of 'sensor' element then prepare a new Parking instance
        if ("Sensor".equals(qName)) {
            sensor = new Sensor();

            // set all required attributes in any XML element here itself
            if (attributes != null && attributes.getLength() == 5) {

                sensor.setState(attributes.getValue("State"));
                sensor.setParkingbay(attributes.getValue("Parkingbay"));
                sensor.setStreet(attributes.getValue("Street"));
                sensor.setLatitude(attributes.getValue("Lat"));
                sensor.setLongitude(attributes.getValue("Long"));

                /*
                // for logging purposes
                Log.i(TAG, "state : " + sensor.getState());
                Log.i(TAG, "parkingbay : " + sensor.getParkingbay());
                Log.i(TAG, "street : " + sensor.getStreet());
                Log.i(TAG, "latitude : " + sensor.getLatitude());
                Log.i(TAG, "longitude : " + sensor.getLatitude());
                */
            }
        }
    }

    public void endElement(String uri, String localName, String qName) throws SAXException {
        // Sensor instance has been constructed on global level and with data added in startElement, so push in parkingList
        if ("Sensor".equals(qName)) {
            this.sensorList.add(sensor);
        }
    }

    /**
     * This will be called everytime parser encounter a value node
     */
    public void characters(char ch[], int start, int length) throws SAXException {
        String value = new String(ch, start, length).trim();
        if(value.length() == 0) return; // ignore white space

        // set value for code of Sensor object
        sensor.setCode(value);
    }
}
