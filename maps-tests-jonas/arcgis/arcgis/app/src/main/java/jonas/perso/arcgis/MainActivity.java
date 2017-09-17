package jonas.perso.arcgis;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.esri.arcgisruntime.mapping.Viewpoint;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import android.location.LocationManager;
import android.location.LocationListener;
import android.location.Location;
import android.location.Criteria;
import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;

public class MainActivity extends AppCompatActivity implements LocationListener {

    private MapView mMapView;
    private LocationManager locationManager;
    private String locationProvider;
    private ArcGISMap map;
    protected Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get location provider
        Criteria criteria = new Criteria();
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setSpeedRequired(false);
        criteria.setHorizontalAccuracy(Criteria.ACCURACY_HIGH);
        criteria.setVerticalAccuracy(Criteria.ACCURACY_COARSE);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationProvider = locationManager.getBestProvider(criteria, true);


        try {
            locationManager.requestLocationUpdates("gps", 0, 0, this);
        } catch(SecurityException e) {
            Log.e("LOCATION", e.toString());

        }

        mMapView = (MapView) findViewById(R.id.mapView);
        map = new ArcGISMap(Basemap.Type.TOPOGRAPHIC, 37.7737221, -122.3909741, 16);
        mMapView.setMap(map);
    }

    @Override
    protected void onPause(){
        mMapView.pause();
        super.onPause();
    }

    @Override
    protected void onResume(){
        super.onResume();
        mMapView.resume();
    }

    public void onLocationChanged(Location newLocation)  {
        map.setInitialViewpoint(new Viewpoint(newLocation.getLatitude(), newLocation.getLongitude(), 16));
    }

    public void onProviderEnabled(String newProvider) {

    }

    public void onProviderDisabled(String provider) {

    }

    public void onStatusChanged(String provider, int status, Bundle extras) {

    }
}
