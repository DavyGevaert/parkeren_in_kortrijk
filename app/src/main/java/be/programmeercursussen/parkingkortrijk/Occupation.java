package be.programmeercursussen.parkingkortrijk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import be.programmeercursussen.parkingkortrijk.adapter.ParkingListAdapter;
import be.programmeercursussen.parkingkortrijk.asynctask.RetrieveOccupationTask;
import be.programmeercursussen.parkingkortrijk.callback.AsyncParkingResponse;
import be.programmeercursussen.parkingkortrijk.constants.Constanten;
import be.programmeercursussen.parkingkortrijk.model.Parking;


public class Occupation extends Activity {

    private static String TAG = "OccupationActivity";

    private Context context;
    private ParkingListAdapter parkingListAdapter;
    private ListView listView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ArrayList<Parking> parkings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_occupation);

        // receive data from MainActivity when clicked on Occupation button
        parkings = getIntent().getParcelableArrayListExtra("parkings");

        /*
        for (Parking parking : parkings) {
            Log.i(TAG, "parkingName : " + parking.getParkingName());
        }
        */

        // set context to this Activity
        context = this;

        // create listview
        listView = (ListView) findViewById(R.id.list);

        new RetrieveOccupationTask(new AsyncParkingResponse() {
            @Override
            public void processFinish(ArrayList<Parking> parkingList) {
                // custom ParkingListAdapter hier opvullen
                parkingListAdapter = new ParkingListAdapter(context, parkingList);

                // set listview with custom adapter
                listView.setAdapter(parkingListAdapter);

                /*
                // for logging purposes
                for(Parking parking : parkingList){
                    Log.i(TAG, "naam parking : " + parking.getParkingName());
                    Log.i(TAG, "capaciteit : " + parking.getTotalcapacity());
                    Log.i(TAG, "bezet : " + parking.getOccupied());
                    Log.i(TAG, "vrij : " + parking.getAvailableCapacity());
                    Log.i(TAG, "status : " + parking.getStatus());
                }
                */
            }
        }).execute(Constanten.BEZETTING_URL);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                //get selected Parking item
                Parking parking = (Parking) adapterView.getItemAtPosition(position);

                Intent i = new Intent(Occupation.this, Information.class);
                // put parkings and parking through with intent
                // in the following activity, I will compare if the chosen parking matches with a Parking object from the arraylist parkings
                // and if it does, I will use that particular Parking object from the arraylist to fill the Information Activity with data
                i.putParcelableArrayListExtra("parkings", parkings);
                i.putExtra("parking", parking);
                startActivity(i);
            }
        });

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);

        // sets the colors used in the refresh animation
        mSwipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light);

        mSwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                // run asynctask RetrieveOccupationTask and get callback through interface AsyncResponse
                                // so we can do something with our ArrayList<Parking> parkingArrayList generated inside the asynctask
                                new RetrieveOccupationTask(new AsyncParkingResponse() {
                                    @Override
                                    public void processFinish(ArrayList<Parking> parkingArrayList) {
                                        parkingListAdapter = new ParkingListAdapter(context, parkingArrayList);
                                        listView.setAdapter(parkingListAdapter);
                                        parkingListAdapter.notifyDataSetChanged();
                                    }
                                }).execute(Constanten.BEZETTING_URL);
                                mSwipeRefreshLayout.setRefreshing(false);
                            }
                        }, 3000);
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.occupation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_previous_occupation:
                // close current activity Info
                this.finish();
                break;
            case R.id.action_quit_occupation:
                // put this activity to backstack activity
                moveTaskToBack(true);
                // close program
                android.os.Process.killProcess(android.os.Process.myPid());
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
