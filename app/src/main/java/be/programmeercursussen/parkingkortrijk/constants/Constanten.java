package be.programmeercursussen.parkingkortrijk.constants;

/**
 * Created by Davy on 16/01/2016.
 */
public interface Constanten {
    // I approach ParkoInfo.xml by static retrieving with assets folder as it seems this data isn't updated dynamically
    // public static final String INFO_URL = "http://www.parkodata.be/OpenData/ParkoInfo.xml";

    public static final String BEZETTING_URL = "http://193.190.76.149:81/ParkoParkings/counters.php";
    public static final String SHOP_GO_URL = "http://www.parkodata.be/OpenData/ShopAndGoOccupation.php";

    public static final String TARIEFZONES_URL = "http://www.parkodata.be/OpenData/TariefzonesParko.kmz";
    public static final String PARKEERAUTOMATEN_URL = "http://www.parkodata.be/OpenData/ParkeerautomatenParko.kml";
}
