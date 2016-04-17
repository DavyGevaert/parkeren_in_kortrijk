package be.programmeercursussen.parkingkortrijk;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;

import be.programmeercursussen.parkingkortrijk.adapter.GridItemAdapter;
import be.programmeercursussen.parkingkortrijk.data.GridItemData;
import be.programmeercursussen.parkingkortrijk.model.Parking;
import be.programmeercursussen.parkingkortrijk.model.Sensor;


public class MainActivity extends Activity implements GridView.OnItemClickListener {

    private static final String TAG = "MainActivity";

    private GridView grid;
    private GridItemData gridItemData;
    private Resources resources;
    private String tekst;
    private ArrayList<Sensor> sensoren;
    private ArrayList<Parking> parkings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // assign variabelen
        grid = (GridView)findViewById(R.id.grid_MainMenu);
        gridItemData = new GridItemData();
        resources = getResources();

        // set adapter
        grid.setAdapter(new GridItemAdapter(this, gridItemData.showGridList(resources)));

        grid.setOnItemClickListener(this);

        // get sensor data received from SplashScreen
        sensoren = getIntent().getParcelableArrayListExtra("sensorArrayList");
        // Log.i(TAG, "inhoud arraylist : " + sensoren);

        // get parking data received from SplashScreen
        parkings = getIntent().getParcelableArrayListExtra("parkingArrayList");
        //Log.i(TAG, "inhoud arraylist : " + parkings);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_quit_main:
                // put this activity to backstack activity
                moveTaskToBack(true);
                // close program
                android.os.Process.killProcess(android.os.Process.myPid());
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        TextView naam = (TextView) view.findViewById(R.id.tvGridName);

        tekst = naam.getText().toString();

        Intent intent;

        // switch statement met een String vanaf Java versie 1.7
        // hiervoor moet je build.gradle aanpassen
        switch (tekst) {
            case "Bezetting":
                intent = new Intent(this, Occupation.class);
                intent.putParcelableArrayListExtra("parkings", parkings);
                startActivity(intent);
                break;
            case "Shop & Go":
                intent = new Intent(this, ShopGo.class);
                intent.putParcelableArrayListExtra("sensoren", sensoren);
                startActivity(intent);
                break;
            /*
            case "Straatparkeren":
                intent = new Intent(this, StraatParkeren.class);
                startActivity(intent);
                break;
            */
        }
    }
}
