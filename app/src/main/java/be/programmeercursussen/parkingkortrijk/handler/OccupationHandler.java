package be.programmeercursussen.parkingkortrijk.handler;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

import be.programmeercursussen.parkingkortrijk.model.Parking;
import be.programmeercursussen.parkingkortrijk.model.Status;

/**
 * Created by Davy on 13/01/2016.
 */
public class OccupationHandler extends DefaultHandler {

    private static String TAG = "ParkingHandler";

    // This is the list which shall be populated while parsing in the XML
    public ArrayList<Parking> parkingList = new ArrayList<Parking>();

    // construct Parking object on global scope
    private Parking parking;

    // getter method for parkinglist
    public ArrayList<Parking> getParkings() {
        return parkingList;
    }

    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        // if this is start of 'parking' element then prepare a new Parking instance
        if ("parking".equals(qName)) {
            parking = new Parking();

            // set all required attributes in any XML element here itself
            if (attributes != null && attributes.getLength() == 4) {
                Status status = new Status();

                status.setTotalcapacity(Integer.valueOf(attributes.getValue("capaciteit")));
                status.setOccupied(Integer.valueOf(attributes.getValue("bezet")));
                status.setAvailableCapacity(Integer.valueOf(attributes.getValue("vrij")));
                status.setDescription(attributes.getValue("status"));

                parking.setStatus(status);

                /*
                // for logging purposes
                Log.i(TAG, "capaciteit : " + parking.getTotalcapacity());
                Log.i(TAG, "bezet : " + parking.getOccupied());
                Log.i(TAG, "vrij : " + parking.getAvailableCapacity());
                Log.i(TAG, "status : " + parking.getStatus());
                */
            }
        }
    }

    public void endElement(String uri, String localName, String qName) throws SAXException {
        // Parking instance has been constructed on global level and with data added in startElement, so push in parkingList
        if ("parking".equals(qName)) {
            this.parkingList.add(parking);
        }
    }

    /**
     * This will be called everytime parser encounter a value node
     */
    public void characters(char ch[], int start, int length) throws SAXException {
        String value = new String(ch, start, length).trim();
        if(value.length() == 0) return; // ignore white space

        // set value for parkingName of Parking object
        parking.setParkingName(value);
    }
}
