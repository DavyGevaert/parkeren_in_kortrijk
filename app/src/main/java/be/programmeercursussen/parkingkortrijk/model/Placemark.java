package be.programmeercursussen.parkingkortrijk.model;

/**
 * Created by Davy on 22/01/2016.
 */
public class Placemark {
    private String id;
    private String name;
    private String description;

    // styleUrl
    // MultiGeometry
    // Polygon
    // extrude
    // altitudeMode
    // outerBoundaryIs
    // LinearRing
    // coordinates

    public Placemark() {
        // empty constructor
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
