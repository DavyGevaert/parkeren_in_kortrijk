package be.programmeercursussen.parkingkortrijk.asynctask;

import android.os.AsyncTask;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import be.programmeercursussen.parkingkortrijk.callback.AsyncSensorResponse;
import be.programmeercursussen.parkingkortrijk.handler.SensorHandler;
import be.programmeercursussen.parkingkortrijk.model.Sensor;

/**
 * Created by Davy on 15/01/2016.
 */
public class RetrieveSensorTask extends AsyncTask<Object, Integer, ArrayList<Sensor>> {

    private static String TAG = "RetrieveSensorTask";

    public AsyncSensorResponse delegate = null;   //Call back interface

    public RetrieveSensorTask(AsyncSensorResponse asyncResponse) {
        delegate = asyncResponse;           //Assigning call back interface through constructor
    }

    protected ArrayList<Sensor> doInBackground(Object... objects) {
        SensorHandler handler = new SensorHandler();
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
        return handler.getSensors();
    }

    @Override
    protected void onPostExecute(ArrayList<Sensor> sensorArrayList) {
        super.onPostExecute(sensorArrayList);

        /*
        // for logging purposes
        for (int i = 0; i < sensorArrayList.size(); i++) {

            Log.i(TAG, "code sensor : " + sensorArrayList.get(i).getCode() + ", straat : " + sensorArrayList.get(i).getStreet());
        }
        */

        delegate.processFinish(sensorArrayList);
    }
}
