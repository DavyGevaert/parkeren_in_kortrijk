package be.programmeercursussen.parkingkortrijk.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import be.programmeercursussen.parkingkortrijk.R;
import be.programmeercursussen.parkingkortrijk.model.Parking;

/**
 * Created by Davy on 20/12/2015.
 */
public class ParkingListAdapter extends ArrayAdapter<Parking> {

    private final ArrayList<Parking> parkings;
    private Context context;

    // View lookup cache
    private static class ViewHolder {
        TextView parking_name;
        TextView free_capacity;
        TextView status_description;
    }

    public ParkingListAdapter(Context context, ArrayList<Parking> parkings) {
        super(context, 0, parkings);
        this.context = context;
        this.parkings = parkings;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        if (convertView == null) {
            // inflate the layout
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_parking, parent, false);

            // set up viewholder
            viewHolder = new ViewHolder();
            viewHolder.parking_name = (TextView) convertView.findViewById(R.id.tv_parking_name);
            viewHolder.free_capacity = (TextView) convertView.findViewById(R.id.tv_free_capacity);
            viewHolder.status_description = (TextView) convertView.findViewById(R.id.tv_status_description);

            // store the holder with the view
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Get the data item for this position
        Parking parking = getItem(position);

        // Populate the data into the template view using the data object
        viewHolder.parking_name.setText(parking.getParkingName());

        viewHolder.free_capacity.setText("Vrij : " + parking.getStatus().getAvailableCapacity() +
                " - Bezet : " + parking.getStatus().getOccupied() + " - Totaal : " + parking.getStatus().getTotalcapacity());

        viewHolder.status_description.setText(parking.getStatus().getDescription());

        if (parking.getStatus().getDescription().equals("OPEN")){
            viewHolder.status_description.setTextColor(context.getResources().getColor(R.color.green));
        } else {
            viewHolder.status_description.setTextColor(context.getResources().getColor(R.color.red));
        }

        return convertView;
    }
}
