package be.programmeercursussen.parkingkortrijk.asynctask;

import android.os.AsyncTask;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import be.programmeercursussen.parkingkortrijk.callback.AsyncParkingResponse;
import be.programmeercursussen.parkingkortrijk.handler.OccupationHandler;
import be.programmeercursussen.parkingkortrijk.model.Parking;

/**
 * Created by Davy on 13/01/2016.
 */
public class RetrieveOccupationTask extends AsyncTask<Object, Integer, ArrayList<Parking>> {

    private static String TAG = "RetrieveOccupationTask";

    public AsyncParkingResponse delegate = null;   //Call back interface

    public RetrieveOccupationTask(AsyncParkingResponse asyncResponse) {
        delegate = asyncResponse;           //Assigning call back interface through constructor
    }

    protected ArrayList<Parking> doInBackground(Object... objects) {
        OccupationHandler handler = new OccupationHandler();
        try {
            String myUrl = (String) objects[0];
            URL url= new URL(myUrl);

            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            XMLReader xmlreader = saxParser.getXMLReader();
            xmlreader.setContentHandler(handler);

            InputSource is = new InputSource(url.openStream());
            xmlreader.parse(is);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return handler.getParkings();
    }

    @Override
    protected void onPostExecute(ArrayList<Parking> parkingArrayList) {
        super.onPostExecute(parkingArrayList);

        /*
        // for logging purposes
        for (int i = 0; i < parkingList.size(); i++) {

            Log.i(TAG, "naam parking : " + parkingList.get(i).getParkingName());
        }
        */

        delegate.processFinish(parkingArrayList);
    }
}
