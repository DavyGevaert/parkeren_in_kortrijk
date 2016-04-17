package be.programmeercursussen.parkingkortrijk;

import android.app.ActionBar;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import be.programmeercursussen.parkingkortrijk.asynctask.RetrieveSensorTask;
import be.programmeercursussen.parkingkortrijk.callback.AsyncSensorResponse;
import be.programmeercursussen.parkingkortrijk.constants.Constanten;
import be.programmeercursussen.parkingkortrijk.model.Sensor;

public class ShopGo extends FragmentActivity implements LocationSource, GoogleMap.OnMarkerClickListener {

    private static final String TAG = "ShopGoActivity";

    private SupportMapFragment supportMapFragment;
    private GoogleMap googleMap;

    private double latitude, longitude;
    private ArrayList<Sensor> sensorArrayList;

    private double kortrijk_latitude = 50.819478;
    private double kortrijk_longitude = 3.257726;
    private LatLng latLng_Kortrijk_City;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_go);

        // set context to this Activity
        context = this;

        // set title screen
        ActionBar ab = getActionBar();
        ab.setTitle("Shop & Go");
        ab.setSubtitle("Overzicht 30 minuten gratis parkeren");

        // Needs to call MapsInitializer before doing any CameraUpdateFactory calls
        try {
            MapsInitializer.initialize(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Getting reference to the SupportMapFragment of activity_main.xml
        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_shopgo);

        // Getting GoogleMap object from the supportMapFragment
        googleMap = supportMapFragment.getMap();

        // Android will use the fused location provider instead of the original Location Provider, and the battery usage should be low when in combination used with the GPS from the device
        // you can check if your application uses low/high battery usage by going to Settings on your Android smartphone and select Location
        googleMap.setLocationSource(this);

        // disable MyLocation Layer of Google Map
        googleMap.setMyLocationEnabled(false);

        // Setting Zoom Controls
        googleMap.getUiSettings().setZoomControlsEnabled(true);

        // OnMarkerClickListener Override method : public boolean onMarkerClick(Marker marker)
        googleMap.setOnMarkerClickListener(this);

        // get sensordata received from intent MainActivity
        sensorArrayList = getIntent().getExtras().getParcelableArrayList("sensoren");

        // local latlng object creation with latitude and longitude of Kortrijk City center
        // http://www.latlong.net
        // we use this LatLng object to center the GoogleMap on Kortrijk City
        latLng_Kortrijk_City = new LatLng(kortrijk_latitude, kortrijk_longitude);

        // Move the camera instantly to latlng with a zoom value.
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng_Kortrijk_City, 12));

        // Construct a CameraPosition to center the map
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLng_Kortrijk_City).build();      // Sets the center of the map

        // Zoom in, animating the camera.
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(12), 1, null);   // 1 = durationMilliseconds

        /*
        ------------------------------------------------------------------------------------------------------------------------------------------------------------------------
         */

        // loop through data sensorArrayList
        for (Sensor s : sensorArrayList) {
            // Log.i(TAG, "latitude van " + s.getStreet() + " : " + s.getLatitude());
            // Log.i(TAG, "longitude van " + s.getStreet() + " : " + s.getLongitude());

            // initiate doubles latitude and longitude
            latitude = 0.0;
            longitude = 0.0;

            // data from sensors sometimes delivers empty codes with no latitude en longitude
            if (s.getLatitude().isEmpty() || s.getLongitude().isEmpty()) {
                // we do nothing here
            } else {      // if we do find values, we create a Double from the String variables getLatitude and getLongitude from object Sensor

                try {
                    // use NumberFormat when current default locale happens to use a comma as a decimal seperator
                    NumberFormat format = NumberFormat.getInstance();
                    Number numberLatitude = format.parse(s.getLatitude());
                    Number numberLongitude = format.parse(s.getLongitude());

                    latitude = numberLatitude.doubleValue();
                    longitude = numberLongitude.doubleValue();

                    // create LatLng object from current sensor
                    LatLng latLng_sensor = new LatLng(latitude, longitude);

                    // switch statement
                    // create a green marker if the sensor is free
                    // create a red marker if the sensor is occupied
                    BitmapDescriptor bitmapMarker = null;
                    switch (s.getState()) {
                        case "Free":
                            s.setState("Vrij");
                            bitmapMarker = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN);

                            // toevoegen marker op googleMap
                            googleMap.addMarker(new MarkerOptions()
                                    .position(latLng_sensor)        // latlng object current sensor
                                    .title(s.getStreet())
                                    .snippet(s.getState())          // snippet: create a subtitle
                                    .icon(bitmapMarker));           // icon: create a green or red marker
                            break;
                        case "Occupied":
                            s.setState("Bezet");
                            bitmapMarker = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED);

                            // toevoegen marker op googleMap
                            googleMap.addMarker(new MarkerOptions()
                                    .position(latLng_sensor)        // latlng object current sensor
                                    .title(s.getStreet())
                                    .snippet(s.getState())          // snippet: create a subtitle
                                    .icon(bitmapMarker));           // icon: create a green or red marker
                            break;
                        case "Unknown":
                            // do nothing as I don't know this parking spot is free or occupied,
                            // I don't really even know if it exists as there is no additional data in the parsed xml file
                            // I don't want to deceive the end user who uses this application
                            break;
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        /*
        former code used to time getting data

        // Long currentTime = System.currentTimeMillis();

        Long pastTime = System.currentTimeMillis() - currentTime;

        Toast.makeText(getApplicationContext(), "duur task : " + pastTime, Toast.LENGTH_LONG).show();
        // 908, 943, 922 milliseconds ==> approx nearly 1 second for sax parsing
        */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.shop_go, menu);
        return true;
    }

    public static class ProgressDialogFragment extends DialogFragment
    {
        @Override
        public void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            setCancelable(false);
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState)
        {
            ProgressDialog dialog = new ProgressDialog(getActivity(), getTheme());
            dialog.setTitle("Een ogenblik geduld ...");
            dialog.setMessage("De kaart wordt herladen.");
            dialog.setIndeterminate(true);
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            return dialog;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_refresh_shopgo:

                final DialogFragment progressDialogFragment = new ProgressDialogFragment();
                progressDialogFragment.show(getFragmentManager(), "Progress Dialog");

                // clear all markers, overlays, and polylines from the map
                googleMap.clear();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        new RetrieveSensorTask(new AsyncSensorResponse() {
                            @Override
                            public void processFinish(ArrayList<Sensor> sensorArrayList) {
                                for (Sensor s : sensorArrayList) {
                                    Log.i(TAG, "latitude van " + s.getStreet() + " : " + s.getLatitude());
                                    Log.i(TAG, "longitude van " + s.getStreet() + " : " + s.getLongitude());

                                    // create local variables with datatype Double
                                    double latitude = 0.0;
                                    double longitude = 0.0;

                                    // data from sensors sometimes delivers empty codes with no latitude en longitude
                                    if (s.getLatitude().isEmpty() || s.getLongitude().isEmpty()) {
                                        // we do nothing here
                                    } else {      // if we do find values, we create a Double from the String variables getLatitude and getLongitude from object Sensor

                                        try {
                                            // use NumberFormat when current default locale happens to use a comma as a decimal seperator
                                            NumberFormat format = NumberFormat.getInstance();
                                            Number numberLatitude = format.parse(s.getLatitude());
                                            Number numberLongitude = format.parse(s.getLongitude());

                                            latitude = numberLatitude.doubleValue();
                                            longitude = numberLongitude.doubleValue();

                                            // create LatLng object from current sensor
                                            LatLng latLng_sensor = new LatLng(latitude, longitude);

                                            // switch statement
                                            // create a green marker if the sensor is free
                                            // create a red marker if the sensor is occupied
                                            BitmapDescriptor bitmapMarker = null;
                                            switch (s.getState()) {
                                                case "Free":
                                                    s.setState("Vrij");
                                                    bitmapMarker = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN);

                                                    // toevoegen marker op googleMap
                                                    googleMap.addMarker(new MarkerOptions()
                                                            .position(latLng_sensor)        // latlng object current sensor
                                                            .title(s.getStreet())
                                                            .snippet(s.getState())          // snippet: create a subtitle
                                                            .icon(bitmapMarker));           // icon: create a green or red marker
                                                    break;
                                                case "Occupied":
                                                    s.setState("Bezet");
                                                    bitmapMarker = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED);

                                                    // toevoegen marker op googleMap
                                                    googleMap.addMarker(new MarkerOptions()
                                                            .position(latLng_sensor)        // latlng object current sensor
                                                            .title(s.getStreet())
                                                            .snippet(s.getState())          // snippet: create a subtitle
                                                            .icon(bitmapMarker));           // icon: create a green or red marker
                                                    break;
                                                case "Unknown":
                                                    // do nothing as I don't know this parking spot is free or occupied,
                                                    // I don't really even know if it exists as there is no additional data in the parsed xml file
                                                    // I don't want to deceive the end user who uses this application
                                                    break;
                                            }
                                        }
                                        catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        // no need to update or move camera, if user device would want to stay on current map position, let him be ...
                                    }
                                }
                            }
                        }).execute(Constanten.SHOP_GO_URL);
                        progressDialogFragment.dismiss();
                    }
                }, 3000);
                break;
            case R.id.action_previous_shopgo:
                // close current activity ShopGo
                this.finish();
                break;
            case R.id.action_quit_info:
                // put this activity to backstack activity
                moveTaskToBack(true);
                // close program
                android.os.Process.killProcess(android.os.Process.myPid());
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    // override method activate and deactivate for LocationSource => usage low battery
    // we don't do anything with it, we just need the low battery setting instead of the default high setting
    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {

    }

    @Override
    public void deactivate() {

    }

    // override method onMarkerClick (this Activity implements GoogleMap.OnMarkerClickListener)
    @Override
    public boolean onMarkerClick(Marker marker) {

        /*

        if (marker.getTitle().equals(s.getStraat())) {

            dialog = new Dialog(context);

            dialog.setContentView(R.layout.dialog_shop_go);
            dialog.setTitle("Parkeren Shop & Go");

            // initialiseren textviews dialog_shop_go.xml
            locatie = (TextView) dialog.findViewById(R.id.tv_dialog_shop_go_locatie);
            status = (TextView) dialog.findViewById(R.id.tv_dialog_shop_go_status);

            // initialiseren imageButtons dialog_shop_go.xml
            parkeerButton = (ImageButton) dialog.findViewById(R.id.im_btn_parkeren);
            afsluitButton = (ImageButton) dialog.findViewById(R.id.im_btn_afsluiten);

            locatie.setText(marker.getTitle());

            if (marker.getSnippet().equals("Free")){
                status.setText("Status : Vrij");
            }
            else if (marker.getSnippet().equals("Occupied") || marker.getSnippet().equals("Unknown"))
            {
                status.setText("Status : Bezet");
                // indien status parkeerplaats shop & go bezet is, disable dan de knop parkeerButton
                parkeerButton.setEnabled(false);
            }

            Log.i(TAG, "status gettext : " + status.getText());



            dialog.show();

            parkeerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // VERSTUREN VAN SMS OP ACHTERGROND

                    //String phoneNo = textPhoneNo.getText().toString();
                    //String sms = textSMS.getText().toString();

                        /*try {
                            SmsManager smsManager = SmsManager.getDefault();
                            smsManager.sendTextMessage(phoneNo, null, sms, null, null);
                            Toast.makeText(getApplicationContext(), "SMS Sent!",
                                    Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(),
                                    "SMS faild, please try again later!",
                                    Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }

                    // onderstaande code enkel om de functie te tonen aan Parko
                    // bovenstaande code gebruiken maar dan moet ik eerst weten welke SMS tekst er effectief moet staan

                    try {

                        Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                        sendIntent.putExtra("sms_body", "default content");
                        sendIntent.setType("vnd.android-dir/mms-sms");
                        startActivity(sendIntent);

                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(),
                                "SMS failed, please try again later!",
                                Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                }
            });

            afsluitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            // for loop sensoren afbreken omdat s.getStraat() meerdere keren voorkomt
            // zo kan dialoogvenster perfect worden afgesloten
            break;
        }
    }

    */
    return false;
    }
}
