package be.programmeercursussen.parkingkortrijk.model;

import android.graphics.Bitmap;

/**
 * Created by Davy on 4/01/2015.
 */
public class GridItem {         // no parcelable needed here, we don't transfer GridItem data from activity A to activity B
    // private variabelen
    private Bitmap image;
    private String name;

    // constructor
    public GridItem(Bitmap image, String name) {
        this.image = image;
        this.name = name;
    }

    public GridItem(String naam) {
        this.name = naam;
    }

    // getter en setters
    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
