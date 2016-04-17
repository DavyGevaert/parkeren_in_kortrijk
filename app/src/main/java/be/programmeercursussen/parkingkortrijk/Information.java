package be.programmeercursussen.parkingkortrijk;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioGroup;

import java.util.ArrayList;

import be.programmeercursussen.parkingkortrijk.fragment.InfoFragment;
import be.programmeercursussen.parkingkortrijk.fragment.RouteFragment;
import be.programmeercursussen.parkingkortrijk.model.Parking;


public class Information extends Activity {

    private RadioGroup radioGroup;
    private Parking parking;
    private ArrayList<Parking> parkings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        // the Parking object only contains the data received through sax parsing with the realtime occupation (capacity, occupied, total, description)
        parking = getIntent().getParcelableExtra("parking");
        // the Arraylist<Parking> contains all the data received through DOM parsing
        parkings = getIntent().getParcelableArrayListExtra("parkings");

        ActionBar ab = getActionBar();
        ab.setTitle(parking.getParkingName());
        ab.setSubtitle("Vrij : " + parking.getStatus().getAvailableCapacity()
                                + " - Bezet : " + (parking.getStatus().getOccupied())
                                + " - Totaal : " + parking.getStatus().getTotalcapacity());

        // declare radioGroup component
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup1);

        // Checked change Listener for radioGroup
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                FragmentManager fm;
                FragmentTransaction ft;

                switch (checkedId)
                {
                    case R.id.radioRoute:
                        RouteFragment routeFragment = new RouteFragment();

                        fm = getFragmentManager();

                        ft = fm.beginTransaction();

                        ft.replace(R.id.fragment_container, routeFragment);

                        ft.commit();
                        break;
                    case R.id.radioFares:
                        InfoFragment infoFragment = new InfoFragment();

                        fm = getFragmentManager();

                        ft = fm.beginTransaction();

                        ft.replace(R.id.fragment_container, infoFragment);

                        ft.commit();
                        break;
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.information, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_previous_info:
                // close current activity Info
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
}
