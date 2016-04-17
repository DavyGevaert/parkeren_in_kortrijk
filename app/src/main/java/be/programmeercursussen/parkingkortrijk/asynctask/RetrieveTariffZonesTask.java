package be.programmeercursussen.parkingkortrijk.asynctask;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.util.Log;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import be.programmeercursussen.parkingkortrijk.callback.AsyncPlacemarkResponse;
import be.programmeercursussen.parkingkortrijk.handler.PlacemarkHandler;
import be.programmeercursussen.parkingkortrijk.model.Placemark;

/**
 * Created by Davy on 22/01/2016.
 */
public class RetrieveTariffZonesTask extends AsyncTask<Object, Integer, ArrayList<Placemark>> {

    private static String TAG = "RetrieveTariffZonesTask";
    private Context context;

    public AsyncPlacemarkResponse delegate = null;   //Call back interface

    public RetrieveTariffZonesTask(Context context, AsyncPlacemarkResponse asyncResponse) {
        this.context = context;
        delegate = asyncResponse;           //Assigning call back interface through constructor
    }

    protected ArrayList<Placemark> doInBackground(Object... objects) {
        AssetManager assetManager = context.getAssets();

        PlacemarkHandler handler = new PlacemarkHandler();

        InputSource inputSource = null;
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            XMLReader xmlreader = saxParser.getXMLReader();
            xmlreader.setContentHandler(handler);

            inputSource = new InputSource(context.getAssets().open("TariefzonesParko.kml"));
            xmlreader.parse(inputSource);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return handler.getPlacemarks();
    }

    @Override
    protected void onPostExecute(ArrayList<Placemark> placemarkArrayList) {
        super.onPostExecute(placemarkArrayList);

        // for logging purposes

        for (int i = 0; i < placemarkArrayList.size(); i++) {

            Log.i(TAG, "description placemark : " + placemarkArrayList.get(i).getDescription());
        }


        delegate.processFinish(placemarkArrayList);
    }
}
