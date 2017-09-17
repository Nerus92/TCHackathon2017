package jonas.perso.test_mapquest;

import android.app.Activity;
import android.location.LocationManager;
import android.os.Bundle;
import android.content.Context;

import com.mapquest.mapping.MapQuestAccountManager;
import com.mapquest.mapping.maps.OnMapReadyCallback;
import com.mapquest.mapping.maps.MapboxMap;
import com.mapquest.mapping.maps.MapView;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import jonas.perso.test_mapquest.GPSTracker;
import 	android.util.Log;

public class MainActivity extends Activity {
    private MapboxMap mMapboxMap;
    private MapView mMapView;
    private static final String TAG = "MyActivity";
    GPSTracker gps = new GPSTracker(this);
    protected Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MapQuestAccountManager.start(getApplicationContext());
        setContentView(R.layout.activity_main);

        mMapView = (MapView) findViewById(R.id.mapquestMapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {
                mMapboxMap = mapboxMap;
                try {
                    mMapboxMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(locationManager.getLastKnownLocation("gps").getLatitude(), locationManager.getLastKnownLocation("gps").getLongitude()), 11));
                } catch(SecurityException e){
                       Log.e(TAG, e.toString());
                }
            }
        });
    }

    @Override
    public void onResume()
    { super.onResume(); mMapView.onResume(); }

    @Override
    public void onPause()
    { super.onPause(); mMapView.onPause(); }

    @Override
    protected void onDestroy()
    { super.onDestroy(); mMapView.onDestroy(); }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    { super.onSaveInstanceState(outState); mMapView.onSaveInstanceState(outState); }
}