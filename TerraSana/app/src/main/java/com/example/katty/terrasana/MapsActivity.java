package com.example.katty.terrasana;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.Task;

import java.util.Locale;
import java.util.logging.Logger;

public class MapsActivity extends FragmentActivity implements
        OnMapReadyCallback, GoogleMap.OnMarkerClickListener,
        GoogleMap.OnInfoWindowClickListener,
        GoogleMap.OnMarkerDragListener {

    private GoogleMap mMap;
    private static final int LOCATION_REQUEST_CODE = 101;
    private Marker markerPais;
    public  static final  String EXTRA_LATITUD="";
    public  static final  String EXTRA_LONGITUD="";
    private Marker markerArgentina;
    private Marker markerEcuador;
    private static final LatLng PERTH = new LatLng(-31.952854, 115.857342);
    private static final LatLng SYDNEY = new LatLng(-33.87365, 151.20689);
    private static final LatLng BRISBANE = new LatLng(-27.47093, 153.0235);
    private Marker mPerth;
    private Marker mSydney;
    private Marker mBrisbane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    protected void requestPermission(String permissionType,int requestCode) {
        ActivityCompat.requestPermissions(this, new String[]{permissionType}, requestCode );
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case LOCATION_REQUEST_CODE: {

                if (grantResults.length == 0
                        || grantResults[0] !=
                        PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this,
                            "Unable to show location - permission required",
                            Toast.LENGTH_LONG).show();
                } else {

                    SupportMapFragment mapFragment =
                            (SupportMapFragment) getSupportFragmentManager()
                                    .findFragmentById(R.id.map);
                    mapFragment.getMapAsync(this);
                }
            }
        }

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Integer clickCount = (Integer) marker.getTag();
        // Check if a click count was set, then display the click count.
        if (clickCount != null) {
            clickCount = clickCount + 1;
            marker.setTag(clickCount);
            Toast.makeText(this,
                    marker.getTitle() +
                            " has been clicked " + clickCount + " times.",
                    Toast.LENGTH_SHORT).show();
        }

        if (marker.equals(markerPais)) {
            Intent intent = new Intent(this, MarkerDetailActivity.class);
            intent.putExtra(EXTRA_LATITUD, marker.getPosition().latitude);
            intent.putExtra(EXTRA_LONGITUD, marker.getPosition().longitude);

            startActivity(intent);
        }
        return false;

    }

    @Override
    public void onInfoWindowClick(Marker marker) {


    }

    @Override
    public void onMarkerDragStart(Marker marker) {
        if (marker.equals(markerEcuador)) {
            Toast.makeText(this, "START", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onMarkerDrag(Marker marker) {
        if (marker.equals(markerEcuador)) {
            String newTitle = String.format(Locale.getDefault(),
                    getString(R.string.marker_detail_latlng),
                    marker.getPosition().latitude,
                    marker.getPosition().longitude);

            setTitle(newTitle);
            Toast.makeText(getApplicationContext(), newTitle, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        if (marker.equals(markerEcuador)) {
            Toast.makeText(this, "END", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (mMap != null) {
            int permission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

            if (permission == PackageManager.PERMISSION_GRANTED) {
                mMap.setMyLocationEnabled(true);
            } else {
                requestPermission(Manifest.permission.ACCESS_FINE_LOCATION, LOCATION_REQUEST_CODE);
            }
        }

        // Add some markers to the map, and add a data object to each marker.
        mPerth = mMap.addMarker(new MarkerOptions()
                .position(PERTH)
                .title("Perth"));
        mPerth.setTag(0);

        mSydney = mMap.addMarker(new MarkerOptions()
                .position(SYDNEY)
                .title("Sydney"));
        mSydney.setTag(0);

        mBrisbane = mMap.addMarker(new MarkerOptions()
                .position(BRISBANE)
                .title("Brisbane"));
        mBrisbane.setTag(0);


        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        // Markers
        LatLng colombia = new LatLng(4.6,-74.08);
        markerPais = googleMap.addMarker(new MarkerOptions()
                .position(colombia)
                .title("Colombia")
        );

        // Cámara
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(colombia));

        // Eventos
        googleMap.setOnMarkerClickListener(this);

        //Eventos sobre la Info Window

        LatLng argentina = new LatLng(-34.6, -58.4);
        markerArgentina = googleMap.addMarker(
                new MarkerOptions()
                        .position(argentina)
                        .title("Argentina")
        );

        // Cámara
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(argentina));

        // Eventos
        googleMap.setOnInfoWindowClickListener(this);

        LatLng ecuador = new LatLng(-0.217, -78.51);
        markerEcuador = googleMap.addMarker(new MarkerOptions()
                .position(ecuador)
                .title("Ecuador")
                .draggable(true)
        );

        // Cámara
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(ecuador));

        // Eventos
        googleMap.setOnMarkerDragListener(this);

        UiSettings mapSettings;
        mapSettings = mMap.getUiSettings();
        mapSettings.setZoomControlsEnabled(true); //Set Zoom Controls

        /*mapSettings.setAllGesturesEnabled(true);    //Set All Gestures
        mapSettings.setScrollGesturesEnabled(true); //Set Scroll
        mapSettings.setTiltGesturesEnabled(true);   //Set Tilt
        mapSettings.setRotateGesturesEnabled(true); //Set Rotate
        mMap.setBuildingsEnabled(true); //3D view
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE); //Map type*/

        LatLng MELBOURNE = new LatLng(-37.813, 144.962);
        Marker melbourne = mMap.addMarker(new MarkerOptions()
                .position(MELBOURNE)
                .alpha(0.7f)
                .anchor(0.5f,0.5f)
                .rotation(90.0f)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(MELBOURNE));

        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(10, 10))
                .title("Marker z1")
                .zIndex(1.0f));
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(10, 11))
                .title("Marker z1")
                .draggable(true)
                .zIndex(1.2f)
                .snippet("AndroidSRC Map Demo")).setIcon(
                BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_AZURE)
        );
        PolygonOptions rectOptions = new PolygonOptions()
                .add(new LatLng(37.35, -122.0),
                        new LatLng(37.45, -122.0),
                        new LatLng(37.45, -122.2),
                        new LatLng(37.35, -122.2),
                        new LatLng(37.35, -122.0));

        // Get back the mutable Polygon
        Polygon polygon = mMap.addPolygon(rectOptions);
        LatLng MOUNTAIN_VIEW = new LatLng(37.4, -122.1);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(MOUNTAIN_VIEW, 9));

        LatLng PERTH = new LatLng(-31.90, 115.86);
        //LatLng MELBOURNE = new LatLng(-37.81319, 144.96298);
        Polyline line = mMap.addPolyline(new PolylineOptions()
                .add(PERTH, MELBOURNE)
                .width(25)
                .color(Color.BLUE)
                .geodesic(false));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(PERTH, 9));
//Add an overlay to the map
        LatLng newWark = new LatLng(40.714086, -74.228697);
        mMap.addMarker(new MarkerOptions()
                .position(newWark)
                .title("Marker in New Wark"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(newWark));
        GroundOverlayOptions newarkMap = new GroundOverlayOptions()
                .image(BitmapDescriptorFactory.fromResource(
                        R.drawable.common_full_open_on_phone))
                .position(newWark, 8600f, 6500f);
// Add an overlay to the map, retaining a handle to the GroundOverlay object.
        GroundOverlay imageOverlay = mMap.addGroundOverlay(newarkMap);
//imageOverlay.remove(); // Remove overlay map


    }

}
