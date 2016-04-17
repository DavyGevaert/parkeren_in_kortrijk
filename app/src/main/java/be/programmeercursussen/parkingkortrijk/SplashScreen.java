package be.programmeercursussen.parkingkortrijk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import be.programmeercursussen.parkingkortrijk.asynctask.RetrieveInformationTaskWithDOMParsing;
import be.programmeercursussen.parkingkortrijk.asynctask.RetrieveSensorTask;
import be.programmeercursussen.parkingkortrijk.callback.AsyncParkingResponse;
import be.programmeercursussen.parkingkortrijk.callback.AsyncSensorResponse;
import be.programmeercursussen.parkingkortrijk.constants.Constanten;
import be.programmeercursussen.parkingkortrijk.model.Parking;
import be.programmeercursussen.parkingkortrijk.model.Sensor;


public class SplashScreen extends Activity {

    private static final String TAG = "SplashScreenActivity";

    private Intent intent;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // create intent to specify which Activity it should load, namely MainActivity
        intent = new Intent(SplashScreen.this, MainActivity.class);
        // refer context to this Activity, I need this for the asynctask RetrieveInformationTaskWithDOMParsing
        // because I'd had to program write InputStream is = context.getAssets().open("ParkoInfo.xml");
        context = this;

        /*
        This task has quite some big data in it's online xml, so I put this in a SplashScreen with handler,
        to avoid some time that it needs to load in the next activity.
        The end user will just think the program is loading
         */

        new RetrieveSensorTask(new AsyncSensorResponse() {
            @Override
            public void processFinish(ArrayList<Sensor> sensorArrayList) {
                // Create an intent to pass through the results which are Parcelable from Activity A to Activity B
                for (Sensor s : sensorArrayList) {
                    Log.i(TAG, s.getLatitude());
                    Log.i(TAG, s.getLongitude());
                }

                intent.putParcelableArrayListExtra("sensorArrayList", sensorArrayList);
            }
        }).execute(Constanten.SHOP_GO_URL);

        /*
        The following task has a complex nested xml setup to parse
        sax parsing gives me trouble so I'd choose DOM parsing for this xml-file.

        Plus I don't acquire this xml by online download, I put it as a static resource in the assets folder
        as it seems the data in the xml-file never changes
        */

        new RetrieveInformationTaskWithDOMParsing(new AsyncParkingResponse() {
            @Override
            public void processFinish(ArrayList<Parking> parkingArrayList) {
                // Create an intent to pass through the results which are Parcelable from Activity A to Activity B
                intent.putParcelableArrayListExtra("parkingArrayList", parkingArrayList);

                /*
                for (Parking p : parkingArrayList) {
                    Log.i(TAG, "latitude parkingplaats : " + p.getGeoLocation().getLatitude());
                    Log.i(TAG, "longitude parkingplaats : " + p.getGeoLocation().getLongitude());
                }*/

                // only do startActivity with intent if this is the last asynctask that is performed within the handler
                startActivity(intent);
            }
        }).execute(context);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.splash_screen, menu);
        return true;
    }

    // The method onOptionsItemSelected is never used because of the theme setting in AndroidManifest.xml for this startup Activity
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
