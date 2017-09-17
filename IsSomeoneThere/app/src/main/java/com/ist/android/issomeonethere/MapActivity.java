package com.ist.android.issomeonethere;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.Button;
import android.view.View;
import android.graphics.drawable.BitmapDrawable;

import android.graphics.Color;
import android.widget.Toast;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import android.content.Intent;
import android.app.Activity;
import android.app.ActivityManager;
import com.esri.arcgisruntime.geometry.Envelope;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.SpatialReferences;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.symbology.PictureMarkerSymbol;

import java.util.HashMap;

import com.esri.arcgisruntime.geometry.GeometryEngine;
import com.esri.arcgisruntime.geometry.Multipoint;
import com.esri.arcgisruntime.mapping.view.DefaultMapViewOnTouchListener;

import com.esri.arcgisruntime.symbology.SimpleMarkerSymbol;

import com.esri.arcgisruntime.geometry.PointCollection;

import android.location.LocationManager;
import android.location.LocationListener;
import android.location.Location;
import android.location.Criteria;

public class MapActivity extends AppCompatActivity implements LocationListener {

    // The map itself
    private ArcGISMap map;
    private MapView mMapView;
    private GraphicsOverlay myLocationOverlay;

    // POIs to display
    private Point myLocation;
    private SimpleMarkerSymbol myLocationMarker;
    //    private PointCollection poiLocationList;
    //    private SimpleMarkerSymbol poiLocationMarker;
    private double latMin, latMax, lngMin, lngMax;
    private HashMap<String, GraphicsOverlay> poiTypeOverlay;
    private HashMap<String, PictureMarkerSymbol> poiTypeSymbols;
    private HashMap<String, PointCollection> poiTypeLocationList;

    // GPS Stuff
    private LocationManager locationManager;
    private String locationProvider;
    private boolean bFirstLocation;

    // Who am I
    private String myType;
    private String displayedType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

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

        BitmapDrawable poiProvideWaterDrawable = (BitmapDrawable) ContextCompat.getDrawable(this, R.drawable.providewater);
        BitmapDrawable poiProvideFoodDrawable = (BitmapDrawable) ContextCompat.getDrawable(this, R.drawable.providefood);
        BitmapDrawable poiProvideShelterDrawable = (BitmapDrawable) ContextCompat.getDrawable(this, R.drawable.provideshelter);
        BitmapDrawable poiProvideTransportationDrawable = (BitmapDrawable) ContextCompat.getDrawable(this, R.drawable.providetransportation);
        BitmapDrawable poiProvideMedicalDrawable = (BitmapDrawable) ContextCompat.getDrawable(this, R.drawable.providemedical);
        BitmapDrawable poiNeedWaterDrawable = (BitmapDrawable) ContextCompat.getDrawable(this, R.drawable.needwater);
        BitmapDrawable poiNeedFoodDrawable = (BitmapDrawable) ContextCompat.getDrawable(this, R.drawable.needfood);
        BitmapDrawable poiNeedShelterDrawable = (BitmapDrawable) ContextCompat.getDrawable(this, R.drawable.needshelter);
        BitmapDrawable poiNeedTransportationDrawable = (BitmapDrawable) ContextCompat.getDrawable(this, R.drawable.needtransportation);
        BitmapDrawable poiNeedMedicalDrawable = (BitmapDrawable) ContextCompat.getDrawable(this, R.drawable.needmedical);

        final PictureMarkerSymbol poiProvideWaterMarker = new PictureMarkerSymbol(poiProvideWaterDrawable);
        final PictureMarkerSymbol poiProvideFoodMarker = new PictureMarkerSymbol(poiProvideFoodDrawable);
        final PictureMarkerSymbol poiProvideShelterMarker = new PictureMarkerSymbol(poiProvideShelterDrawable);
        final PictureMarkerSymbol poiProvideTransportationMarker = new PictureMarkerSymbol(poiProvideTransportationDrawable);
        final PictureMarkerSymbol poiProvideMedicalMarker = new PictureMarkerSymbol(poiProvideMedicalDrawable);
        final PictureMarkerSymbol poiNeedWaterMarker = new PictureMarkerSymbol(poiNeedWaterDrawable);
        final PictureMarkerSymbol poiNeedFoodMarker = new PictureMarkerSymbol(poiNeedFoodDrawable);
        final PictureMarkerSymbol poiNeedShelterMarker = new PictureMarkerSymbol(poiNeedShelterDrawable);
        final PictureMarkerSymbol poiNeedTransportationMarker = new PictureMarkerSymbol(poiNeedTransportationDrawable);
        final PictureMarkerSymbol poiNeedMedicalMarker = new PictureMarkerSymbol(poiNeedMedicalDrawable);

        poiTypeSymbols = new HashMap<String, PictureMarkerSymbol>();
        poiTypeSymbols.put("ProvideWater", poiProvideWaterMarker);
        poiTypeSymbols.put("ProvideFood", poiProvideFoodMarker);
        poiTypeSymbols.put("ProvideShelter", poiProvideShelterMarker);
        poiTypeSymbols.put("ProvideTransportation", poiProvideTransportationMarker);
        poiTypeSymbols.put("ProvideMedical", poiProvideMedicalMarker);
        poiTypeSymbols.put("NeedWater", poiNeedWaterMarker);
        poiTypeSymbols.put("NeedFood", poiNeedFoodMarker);
        poiTypeSymbols.put("NeedShelter", poiNeedShelterMarker);
        poiTypeSymbols.put("NeedTransportation", poiNeedTransportationMarker);
        poiTypeSymbols.put("NeedMedical", poiNeedMedicalMarker);
        for (PictureMarkerSymbol s: poiTypeSymbols.values()) {
            s.setHeight(40);
            s.setWidth(34);
            s.setOffsetY(20);
        }

        // create empty POI lists
        poiTypeLocationList = new HashMap<String, PointCollection>();
        poiTypeLocationList.put("ProvideWater", new PointCollection(SpatialReferences.getWgs84()));
        poiTypeLocationList.put("ProvideFood", new PointCollection(SpatialReferences.getWgs84()));
        poiTypeLocationList.put("ProvideShelter", new PointCollection(SpatialReferences.getWgs84()));
        poiTypeLocationList.put("ProvideTransportation", new PointCollection(SpatialReferences.getWgs84()));
        poiTypeLocationList.put("ProvideMedical", new PointCollection(SpatialReferences.getWgs84()));
        poiTypeLocationList.put("NeedWater", new PointCollection(SpatialReferences.getWgs84()));
        poiTypeLocationList.put("NeedFood", new PointCollection(SpatialReferences.getWgs84()));
        poiTypeLocationList.put("NeedShelter", new PointCollection(SpatialReferences.getWgs84()));
        poiTypeLocationList.put("NeedTransportation", new PointCollection(SpatialReferences.getWgs84()));
        poiTypeLocationList.put("NeedMedical", new PointCollection(SpatialReferences.getWgs84()));

        // Create map
        map = new ArcGISMap(Basemap.Type.TOPOGRAPHIC, 34.056295, -117.195800, 16);

        // Create all overlays for POIs
        myLocationOverlay = new GraphicsOverlay();
        poiTypeOverlay = new HashMap<String, GraphicsOverlay>();
        poiTypeOverlay.put("ProvideWater", new GraphicsOverlay());
        poiTypeOverlay.put("ProvideFood", new GraphicsOverlay());
        poiTypeOverlay.put("ProvideShelter", new GraphicsOverlay());
        poiTypeOverlay.put("ProvideTransportation", new GraphicsOverlay());
        poiTypeOverlay.put("ProvideMedical", new GraphicsOverlay());
        poiTypeOverlay.put("NeedWater", new GraphicsOverlay());
        poiTypeOverlay.put("NeedFood", new GraphicsOverlay());
        poiTypeOverlay.put("NeedShelter", new GraphicsOverlay());
        poiTypeOverlay.put("NeedTransportation", new GraphicsOverlay());
        poiTypeOverlay.put("NeedMedical", new GraphicsOverlay());

        // Initial map window
        latMax = -1000;
        lngMax = -1000;
        latMin = 1000;
        lngMin = 1000;

        // set the map to be displayed in this view
        mMapView = (MapView) findViewById(R.id.mapView);

        // Configure onTouch for the map
        mMapView.setOnTouchListener(new DefaultMapViewOnTouchListener(this, mMapView) {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                // get the point that was clicked and convert it to a point in map coordinates
                Point clickPoint = mMapView.screenToLocation(new android.graphics.Point(Math.round(e.getX()), Math.round(e.getY())));
                int tolerance = 20;
                double mapTolerance = tolerance * mMapView.getUnitsPerDensityIndependentPixel();
                // create objects required to do a selection with a query
                Envelope envelope = new Envelope(clickPoint.getX() - mapTolerance, clickPoint.getY() - mapTolerance, clickPoint.getX() + mapTolerance, clickPoint.getY() + mapTolerance, map.getSpatialReference());
                envelope = (Envelope) GeometryEngine.project(envelope, SpatialReferences.getWgs84());
                searchPOI(envelope);
                return super.onSingleTapConfirmed(e);
            }
        });

        // Configure my type
        try {
            String type = getIntent().getExtras().getString("Type");
            String category = getIntent().getExtras().getString("Category");
            updateMyType(type, category);
        } catch(Exception e) {
            updateMyType("Need", "Food");
        }

        mMapView.setMap(map);

        // For debug only
        final Button button = (Button) findViewById(R.id.clickButton);
        button.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v) {
                drawPOI(37.774929, -122.419416, "NeedFood");
                drawPOI(37.734929, -122.429416, "ProvideFood");
                drawPOI(37.714929, -122.439416, "NeedFood");
                drawPOI(37.784929, -122.419416, "ProvideFood");
            }
        });


        drawPOI(37.774929, -122.419416, "NeedFood");
        drawPOI(37.734929, -122.429416, "ProvideFood");
        drawPOI(37.714929, -122.439416, "NeedFood");
        drawPOI(37.784929, -122.419416, "ProvideFood");

        // setup GPS
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }
        bFirstLocation = true;
        gpsConfigure();
        gpsUpdateProvider();
        gpsStart();
        Location location = locationManager.getLastKnownLocation(locationProvider);
        if(location != null) {
            onLocationChanged(location);
            updateMapView();
        }
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

        // Make sure the view covers the new point
        if(lat>latMax) latMax = lat;
        if(lat<latMin) latMin = lat;
        if(lng>lngMax) lngMax = lng;
        if(lng<lngMin) lngMin = lng;
        if(bFirstLocation) {
            bFirstLocation = false;
            updateMapView();
        }

        // Save the current location
        myLocation = new Point(lng, lat, SpatialReferences.getWgs84());
//        mMapView.setViewpointCenterAsync(myLocation);

        // Add the new point on the map
        Graphic graphic = new Graphic(myLocation, myLocationMarker);
        myLocationOverlay.getGraphics().clear();
        myLocationOverlay.getGraphics().add(graphic);

        //TODO: call Jerome method to update my position and type

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

    private void drawPOI(Double lat, Double lng, String type) {
        // Save this point to the list
        Point poi = new Point(lng, lat, SpatialReferences.getWgs84());
        poiTypeLocationList.get(type).add(poi);

        // Add the icon on the list
        Graphic graphic = new Graphic(poi, poiTypeSymbols.get(type));
        poiTypeOverlay.get(type).getGraphics().add(graphic);

        if(type.equals(displayedType)) {
            // Update envelope boundaries
            if (lat > latMax) latMax = lat;
            if (lat < latMin) latMin = lat;
            if (lng > lngMax) lngMax = lng;
            if (lng < lngMin) lngMin = lng;
            // reload the map
            updateMapView();
        }
    }

    private void searchPOI(Envelope envelope) {
        Log.i("ENVELOPE", envelope.toString());
        int counter = 0;

        //TODO: Use Jerome POI class instead

        Point selectedPoint = new Point(0,0);
        Multipoint multipoint = new Multipoint(poiTypeLocationList.get(displayedType));
        for (Point pt : multipoint.getPoints()) {
//                    Log.i("Point", String.format("Point x=%f,y=%f",  pt.getX(), pt.getY()));
            if (pt.getX() < envelope.getXMax() && pt.getX() > envelope.getXMin() &&
                    pt.getY() < envelope.getYMax() && pt.getY() > envelope.getYMin()) {
                counter++;
                selectedPoint = pt;
            }
        }

        if (counter == 0) {
            Log.i("ENVELOPE", "empty");
        } else if(counter == 1) {
            Toast.makeText(getApplicationContext(), selectedPoint.toString() + " selected", Toast.LENGTH_SHORT).show();
            goToChat("abcde");
        } else {
            Toast.makeText(getApplicationContext(), counter + " features selected, select only 1", Toast.LENGTH_SHORT).show();
        }
    }

    private void goToChat(String chat_guid) {
        Intent myIntent = new Intent(MapActivity.this, ChatActivity.class);
        myIntent.putExtra("guid", chat_guid);
        MapActivity.this.startActivity(myIntent);
    }

    private void updateMapView() {
        Envelope envelope = new Envelope(lngMin, latMin, lngMax, latMax, SpatialReferences.getWgs84());
        Log.i("ENVELOPE", envelope.toString());
        mMapView.setViewpointGeometryAsync(envelope, getResources().getDimension(R.dimen.viewpoint_padding));
    }

    private void updateMyType(String type, String category) {
        myType = type+category;
        displayedType = (type.equals("Provide")?"Need":"Provide")+category;
        Log.i("TYPE", String.format("I am %s, I see %s", myType, displayedType));
        mMapView.getGraphicsOverlays().clear();
        mMapView.getGraphicsOverlays().add(myLocationOverlay);
        mMapView.getGraphicsOverlays().add(poiTypeOverlay.get(displayedType));
        updateMapView();
    }

}
