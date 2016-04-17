package be.programmeercursussen.parkingkortrijk;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import java.util.ArrayList;

import be.programmeercursussen.parkingkortrijk.asynctask.RetrieveTariffZonesTask;
import be.programmeercursussen.parkingkortrijk.callback.AsyncPlacemarkResponse;
import be.programmeercursussen.parkingkortrijk.model.Placemark;


public class StraatParkeren extends Activity {

    private WebView webView;
    private String htmlData;
    private String clean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_straat_parkeren);

        webView = (WebView) findViewById(R.id.activity_straatparkeren_webview);

        new RetrieveTariffZonesTask(this, new AsyncPlacemarkResponse() {
            @Override
            public void processFinish(ArrayList<Placemark> placemarkArrayList) {
                for (int i = 0; i < placemarkArrayList.size(); i++) {
                    if (i == 1) {
                        htmlData = "<link rel=\"stylesheet\" type=\"text/css\" href=\"reset.css\" />" + placemarkArrayList.get(i).getDescription();

                        // remove style (css) from tag 'table' with library Jsoup (add dependency in file build.gradle)
                        Whitelist whitelist = Whitelist.simpleText();
                        whitelist.addTags("table");     // add additional tags here as necessary followed by a comma
                        clean = Jsoup.clean(htmlData, whitelist);

                        // file:///android_asset/ , this should call our own css file
                        webView.loadDataWithBaseURL("file:///android_asset/", clean, "text/html", "utf-8", null);
                    }
                }
            }
        }).execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.straat_parkeren, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
