package be.programmeercursussen.parkingkortrijk.data;

import android.content.res.Resources;
import android.graphics.BitmapFactory;

import java.util.ArrayList;

import be.programmeercursussen.parkingkortrijk.R;
import be.programmeercursussen.parkingkortrijk.model.GridItem;

/**
 * Created by Davy on 4/01/2015.
 */
public class GridItemData extends ArrayList<GridItem> {


    // empty constructor
    public GridItemData() {

    }

    public GridItemData showGridList(Resources resources) {
        // google search matte-blue-and-white-square-icon
        this.add(new GridItem(BitmapFactory.decodeResource(resources, R.drawable.information_icon), "Bezetting"));
        this.add(new GridItem(BitmapFactory.decodeResource(resources, R.drawable.shopping), "Shop & Go"));
        //this.add(new GridItem(BitmapFactory.decodeResource(resources, R.drawable.parking), "Straatparkeren"));
        return this;
    }
}
