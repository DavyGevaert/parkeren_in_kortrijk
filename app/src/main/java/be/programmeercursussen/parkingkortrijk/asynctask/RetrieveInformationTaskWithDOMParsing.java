package be.programmeercursussen.parkingkortrijk.asynctask;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import be.programmeercursussen.parkingkortrijk.callback.AsyncParkingResponse;
import be.programmeercursussen.parkingkortrijk.model.Adress;
import be.programmeercursussen.parkingkortrijk.model.Contact;
import be.programmeercursussen.parkingkortrijk.model.Fare;
import be.programmeercursussen.parkingkortrijk.model.GeoLocation;
import be.programmeercursussen.parkingkortrijk.model.Parking;

/**
 * Created by Davy on 16/01/2016.
 */
public class RetrieveInformationTaskWithDOMParsing extends AsyncTask<Object, Integer, ArrayList<Parking>> {

    private static String TAG = "DownloadWebPageTask";

    private Parking parking;
    private Contact contact;
    private Adress adress;
    private GeoLocation geoLocation;
    private Fare fare;
    private ArrayList<Fare> fareList = new ArrayList<Fare>();

    private ArrayList<Parking> parkingArrayList;

    public AsyncParkingResponse delegate = null;   //Call back interface

    public RetrieveInformationTaskWithDOMParsing(AsyncParkingResponse asyncResponse) {
        delegate = asyncResponse;           //Assigning call back interface through constructor
    }

    @Override
    protected ArrayList<Parking> doInBackground(Object... objects) {
        //String url = (String) objects[0];
        Context context = (Context) objects[0];

        parkingArrayList = new ArrayList<Parking>();
        //Log.i(TAG, "response : " + url);

        DocumentBuilderFactory factory;
        DocumentBuilder builder;
        Document document;
        try {
            factory = DocumentBuilderFactory.newInstance();
            builder = factory.newDocumentBuilder();

            /*
            String xml = GET(url);
            InputStream is = new ByteArrayInputStream(xml.getBytes("UTF-8"));
            */

            // create assets folder inside Android Studio project: http://stackoverflow.com/a/27673773/3877060
            // context is passed through on execute asynctask
            InputStream is = context.getAssets().open("ParkoInfo.xml");

            document = builder.parse(is);
            Log.i(TAG, "document : " + document);

            // normalize text representation
            document.getDocumentElement().normalize ();

            Log.i(TAG, "Root element of the doc is " + document.getDocumentElement().getNodeName());

            NodeList nodeListOffstreetParking = document.getElementsByTagName("OffstreetParking");
            //Log.i(TAG, "nodelist offstreetparking : " + nodeListOffstreetParking);

            // int totalOffstreetParking = nodeListOffstreetParking.getLength();
            // Log.i(TAG, "Total number of OffstreetParking : " + totalOffstreetParking);

            for (int i = 0; i < nodeListOffstreetParking.getLength(); i++) {
                // retrieve node of each item
                Node node = nodeListOffstreetParking.item(i);
                //Log.i(TAG, "node : " + node.getTextContent());

                // loggen naam van node
                //Log.i(TAG, "Current Element :" + node.getNodeName());

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) node;
                    //Element currentNode = (Element) nodeListOffstreetParking.item(i);
                    //Log.i(TAG, "currentnode : " + currentNode.getTextContent());

                    //Log.i(TAG, "Name : " + eElement.getElementsByTagName("Name").item(0).getTextContent());

                    String name = eElement.getElementsByTagName("Name").item(0).getTextContent();

                    // create local variables to assign them to constructor of object Adress
                    String straatnaam = eElement.getElementsByTagName("StreetName").item(0).getTextContent();
                    long postcode = Long.parseLong(eElement.getElementsByTagName("PostalCode").item(0).getTextContent());
                    String gemeente = eElement.getElementsByTagName("Location").item(0).getTextContent();

                    // create local variables to assign them to constructor of object Contact
                    String eerstenaam = eElement.getElementsByTagName("FirstName").item(0).getTextContent();
                    String tweedenaam = eElement.getElementsByTagName("LastName").item(0).getTextContent();
                    String positie = eElement.getElementsByTagName("Position").item(0).getTextContent();
                    String telefoon = eElement.getElementsByTagName("Phone").item(0).getTextContent();
                    String fax = eElement.getElementsByTagName("Fax").item(0).getTextContent();
                    String emailadres = eElement.getElementsByTagName("Email").item(0).getTextContent();

                    // create local variables to assign them to constructor of object GeoLocation
                    String longitude = eElement.getElementsByTagName("Longitude").item(0).getTextContent();
                    String latitude = eElement.getElementsByTagName("Latitude").item(0).getTextContent();

                    // create new object Parking as loop starts
                    parking = new Parking();

                    // create new object Adress with constructor
                    adress = new Adress(straatnaam, postcode, gemeente);

                    // create new object Contact with constructor
                    contact = new Contact(eerstenaam, tweedenaam, positie, telefoon, fax, emailadres);

                    // create new object GeoLocation with constructor
                    geoLocation = new GeoLocation(longitude, latitude);

                    parking.setParkingName(name);
                    parking.setAdress(adress);
                    parking.setContact(contact);
                    parking.setGeoLocation(geoLocation);

                    NodeList tariff = ((Element) node).getElementsByTagName("Tariff");

                    int totalTariff = tariff.getLength();
                    Log.i(TAG, "Total number of Tariff tags : " + totalTariff);

                    fareList = new ArrayList<Fare>();

                    for (int j = 0; j < tariff.getLength(); j++) {

                        if (node.getNodeType() == Node.ELEMENT_NODE) {


                            Element element = (Element) node;

                            // create object Fare each time the loops repeats
                            fare = new Fare();
                            //Log.i(TAG, "element : " + element.getTextContent());
                            //Log.i(TAG, "fare item j : " + element.getElementsByTagName("Tariff").item(j).getTextContent());

                            /*
                            Log.i(TAG, "Name : " + element.getElementsByTagName("Tariff").item(j).getChildNodes().item(1).getTextContent());
                            Log.i(TAG, "Amount : " + element.getElementsByTagName("Tariff").item(j).getChildNodes().item(3).getTextContent());
                            Log.i(TAG, "Currency : " + element.getElementsByTagName("Tariff").item(j).getChildNodes().item(5).getTextContent());
                            Log.i(TAG, "Duration : " + element.getElementsByTagName("Tariff").item(j).getChildNodes().item(7).getTextContent());
                            Log.i(TAG, "DurationLimit : " + element.getElementsByTagName("Tariff").item(j).getChildNodes().item(9).getTextContent());
                            Log.i(TAG, "UserType : " + element.getElementsByTagName("Tariff").item(j).getChildNodes().item(11).getTextContent());
                            Log.i(TAG, "UserType : " + element.getElementsByTagName("Tariff").item(j).getChildNodes().item(13).getTextContent());
                            Log.i(TAG, "VehicleType : " + element.getElementsByTagName("Tariff").item(j).getChildNodes().item(15).getTextContent());
                            Log.i(TAG, "FeeDescription : " + element.getElementsByTagName("Tariff").item(j).getChildNodes().item(17).getTextContent());
                            */

                            fare.setFareName(element.getElementsByTagName("Tariff").item(j).getChildNodes().item(1).getTextContent());
                            fare.setAmount(element.getElementsByTagName("Tariff").item(j).getChildNodes().item(3).getTextContent());
                            fare.setExchange(element.getElementsByTagName("Tariff").item(j).getChildNodes().item(5).getTextContent());
                            fare.setTime(element.getElementsByTagName("Tariff").item(j).getChildNodes().item(7).getTextContent());
                            fare.setTimeLimit(element.getElementsByTagName("Tariff").item(j).getChildNodes().item(9).getTextContent());
                            fare.setUserType1(element.getElementsByTagName("Tariff").item(j).getChildNodes().item(11).getTextContent());
                            fare.setUserType2(element.getElementsByTagName("Tariff").item(j).getChildNodes().item(13).getTextContent());
                            fare.setVehicle(element.getElementsByTagName("Tariff").item(j).getChildNodes().item(15).getTextContent());
                            fare.setFee(element.getElementsByTagName("Tariff").item(j).getChildNodes().item(17).getTextContent());

                            // add object fare to ArrayList fareList
                            fareList.add(fare);
                        }
                    }
                    // set fareList for object parking
                    parking.setFares(fareList);
                    Log.i(TAG, "fareList van " + parking.getParkingName() + " : " + parking.getFares().toString());
                }
                parkingArrayList.add(parking);
            }
        }
        catch(Exception e){
            Log.i(TAG, "Exception : " + e.getMessage());
        }
        return parkingArrayList;
    }

    @Override
    protected void onPostExecute(ArrayList<Parking> parkingArrayList) {
        super.onPostExecute(parkingArrayList);

        /*
        // for logging purposes
        for (int i = 0; i < parkingList.size(); i++) {
            Log.i(TAG, "naam parking : " + parkingList.get(i).getParkingName());
        }
        */

        delegate.processFinish(parkingArrayList);
    }

    // this method is only to use when to retrieve an xml-file online
    private String GET(String url){
        InputStream inputStream = null;
        String result = "";
        try {

            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // make GET request to the given URL
            HttpResponse httpResponse = httpclient.execute(new HttpGet(url));

            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";
        } catch (Exception e) {
            Log.d("InputStream", e.toString());
        }
        return result;
    }

    // this method is only to use when to retrieve an xml-file online
    private String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;
    }
}