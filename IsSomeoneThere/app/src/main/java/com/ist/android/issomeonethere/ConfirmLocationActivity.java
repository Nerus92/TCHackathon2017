package com.ist.android.issomeonethere;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.esri.arcgisruntime.geometry.Envelope;
import com.esri.arcgisruntime.geometry.Multipoint;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.SpatialReferences;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.Viewpoint;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.symbology.SimpleMarkerSymbol;

public class ConfirmLocationActivity extends AppCompatActivity implements LocationListener {
    // The map itself
    private ArcGISMap map;
    private MapView mMapView;
    private GraphicsOverlay myLocationOverlay;

    // GPS Stuff
    private LocationManager locationManager;
    private String locationProvider;

    // POIs to display
    private Point myLocation;
    private SimpleMarkerSymbol myLocationMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_location);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(android.support.v7.appcompat.R.drawable.abc_ic_ab_back_material);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();
            }
        });

        // create points designs
        myLocationMarker = new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, Color.BLUE, 12); //size 12, style of circle

        // Create map
        map = new ArcGISMap(Basemap.Type.TOPOGRAPHIC, 34.056295, -117.195800, 16);

        // Create all overlays for POIs
        myLocationOverlay = new GraphicsOverlay();

        // set the map to be displayed in this view
        mMapView = (MapView) findViewById(R.id.mapView);
        mMapView.getGraphicsOverlays().add(myLocationOverlay);

        mMapView.setMap(map);


        // setup GPS
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }
        gpsConfigure();
        gpsUpdateProvider();
        gpsStart();
        Location location = locationManager.getLastKnownLocation(locationProvider);
        if(location != null) {
            onLocationChanged(location);
        }

        final Button b_confirm = (Button) findViewById(R.id.b_confirm);
        b_confirm.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(
                        ConfirmLocationActivity.this,
                        ChatActivity.class);
                intent.putExtras(getIntent().getExtras());
                startActivity(intent);
            }
        });

    }
    @Override
    protected void onPause(){
        super.onPause();
        // pause MapView
        mMapView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // resume MapView
        mMapView.resume();
    }

    public void onLocationChanged(Location newLocation)  {
        Double lat = newLocation.getLatitude();
        Double lng = newLocation.getLongitude();

        // Save the current location
        myLocation = new Point(lng, lat, SpatialReferences.getWgs84());

        // Add the new point on the map
        Graphic graphic = new Graphic(myLocation, myLocationMarker);
        myLocationOverlay.getGraphics().clear();
        myLocationOverlay.getGraphics().add(graphic);

        mMapView.setViewpoint(new Viewpoint(myLocation, 4000));
//        mMapView.setViewpointCenterAsync(myLocation, 100.0);
    }

    public void onProviderEnabled(String newProvider) {
        gpsUpdateProvider();
        gpsStart();
    }

    public void onProviderDisabled(String provider) {
        gpsUpdateProvider();
        gpsStart();
    }

    public void onStatusChanged(String provider, int status, Bundle extras) {
        gpsUpdateProvider();
        gpsStart();
    }

    private void gpsConfigure() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    }

    private void gpsUpdateProvider() {
        locationProvider = locationManager.getBestProvider(new Criteria(), true);
    }

    private void gpsStart() {
        try {
            locationManager.requestLocationUpdates(locationProvider, 0, 0, this);
        } catch (SecurityException e) {
            Log.e("LOCATION", e.toString());
        }
    }


}
