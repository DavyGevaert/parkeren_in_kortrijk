package be.programmeercursussen.parkingkortrijk.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import be.programmeercursussen.parkingkortrijk.R;
import be.programmeercursussen.parkingkortrijk.model.GridItem;

/**
 * Created by Davy on 4/01/2015.
 */
public class GridItemAdapter extends BaseAdapter {
    private Context context;
    private List<GridItem> list_griditem;

    public GridItemAdapter(Context context, List<GridItem> list_griditem) {
        this.context = context;
        this.list_griditem = list_griditem;
    }

    @Override
    public int getCount() {
        return list_griditem.size();
    }

    @Override
    public Object getItem(int position) {
        return list_griditem.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GridItem entry = list_griditem.get(position);

        if(convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.grid_item, null);
        }

        ImageView ivGridImageIcon = (ImageView)convertView.findViewById(R.id.ivGridImage);
        ivGridImageIcon.setImageBitmap(entry.getImage());

        TextView tvGridName = (TextView)convertView.findViewById(R.id.tvGridName);
        tvGridName.setText(entry.getName());

        return convertView;
    }
}
