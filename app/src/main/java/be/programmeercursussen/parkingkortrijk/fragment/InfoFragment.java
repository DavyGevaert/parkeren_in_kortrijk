package be.programmeercursussen.parkingkortrijk.fragment;



import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import be.programmeercursussen.parkingkortrijk.R;
import be.programmeercursussen.parkingkortrijk.model.Fare;
import be.programmeercursussen.parkingkortrijk.model.Parking;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class InfoFragment extends Fragment {

    private static String TAG = "InfoFragment";

    private Parking parking;
    private ArrayList<Parking> parkings;
    private ArrayList<Fare> fares;
    private TextView fee;

    public InfoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_info, container, false);

        // get the Parking object send by the Intent from the OccupationActivity
        // this only contains the data received through sax parsing with the realtime occupation (capacity, occupied, total, description)
        parking = getActivity().getIntent().getParcelableExtra("parking");

        // get the Arraylist<Parking> send by the Intent from the OccupationActivity
        // this contains all the data received through DOM parsing
        parkings = getActivity().getIntent().getParcelableArrayListExtra("parkings");

        // get textview fee
        fee = (TextView) view.findViewById(R.id.tvFee);

        for (int i = 0; i < parkings.size(); i++) {
            if (parkings.get(i).getParkingName().equals(parking.getParkingName())) {
                // create StringBuilder object
                StringBuilder sb = new StringBuilder();

                // create an arrayList of fares from within the arrayList parkings
                fares = parkings.get(i).getFares();
                Log.i(TAG, "content arrayList fares : " + fares);

                String newLine = "\n";
                sb.append(newLine);

                // for loop
                for (Fare f : fares) {

                    // assing f.getFee to local String fee
                    String fee = f.getFee();
                    Log.i(TAG, "fee : " + fee);

                    // create a regexpression that finds the first minus sign
                    Matcher matcher = Pattern.compile("- ").matcher(fee);
                    if(matcher.find()){
                        // get part of String fee
                        fee = fee.substring(matcher.end()).trim();

                        // create new String newFee that represents our fee
                        String newFee = fee;

                        matcher = Pattern.compile("- ").matcher(newFee);

                        if (matcher.find()){
                            // shorten our text with 2 ( - 2) to remove the second minus sign in the data
                            newFee = newFee.substring(0, matcher.end() - 2);
                            sb.append(newFee);
                            Log.i(TAG, "start : " + newFee);
                        }
                    }
                    sb.append(newLine);
                    sb.append(newLine);
                }
                fee.setText(sb.toString());
            }
        }

        return view;
    }
}
