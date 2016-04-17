package be.programmeercursussen.parkingkortrijk.handler;

import android.sax.Element;
import android.sax.EndElementListener;
import android.sax.EndTextElementListener;
import android.sax.RootElement;
import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

import be.programmeercursussen.parkingkortrijk.model.Placemark;

/**
 * Created by Davy on 22/01/2016.
 */
public class PlacemarkHandler extends DefaultHandler {

    private static String TAG = "PlacemarkHandler";
    private int count = 0;
    private boolean isDescription;
    private StringBuilder sbDescription;

    // This is the list which shall be populated while parsing in the XML
    public ArrayList<Placemark> placemarkList = new ArrayList<Placemark>();

    // construct Placemark object on global scope
    private Placemark placemark;

    // getter method for placemarkArrayList
    public ArrayList<Placemark> getPlacemarks() {
        return placemarkList;
    }

    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        // if this is start of 'Placemark' element then prepare a new Parking instance
        if ("Placemark".equals(qName)) {
            //Log.i(TAG, "qName : " + qName);
            placemark = new Placemark();
            count++;
            Log.i(TAG, "aantal placemarks : " + count);

            // set all required attributes in any XML element here itself
            if (attributes != null && attributes.getLength() == 1) {
                placemark.setId(attributes.getValue("id"));
            }
        }

        if (qName.equals("description")) {
            sbDescription = new StringBuilder();
            isDescription = true;
        }

        /*
        // onderstaande juist ???
        RootElement document = new RootElement("Document");
        Element placemarkElement = document.getChild("Placemark");
        Element name = placemarkElement.getChild("name");
        */
    }

    public void endElement(String uri, String localName, String qName) throws SAXException {
        // Sensor instance has been constructed on global level and with data added in startElement, so push in parkingList
        if ("Placemark".equals(qName)) {
            this.placemarkList.add(placemark);
        }

        if (qName.equals("description")) {
            placemark.setDescription(sbDescription.toString());
            isDescription = false;
        }
    }

    /**
     * This will be called everytime parser encounter a value node
     */
    public void characters(char ch[], int start, int length) throws SAXException {
        //String value = new String(ch, start, length).trim();
        //if(value.length() == 0) return; // ignore white space

        if (isDescription) {
            if (sbDescription != null) {
                for (int i = start; i < start+length; i++) {
                    sbDescription.append(ch[i]);
                }
            }
        }
    }
}
