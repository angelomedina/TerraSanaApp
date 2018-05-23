package com.example.katty.terrasana;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Locale;

public class MapsActivity extends FragmentActivity implements
        OnMapReadyCallback, GoogleMap.OnMarkerClickListener,
        GoogleMap.OnInfoWindowClickListener,
        GoogleMap.OnMarkerDragListener {

    private GoogleMap mMap;
    private static final int LOCATION_REQUEST_CODE = 101;
    private Marker markerPais;
    public  static final  String EXTRA_LATITUD="";
    public  static final  String EXTRA_LONGITUD="";

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
        if (marker.equals(markerPais)) {

            /*
            Intent intent = new Intent(this, InfoPedidoActivity.class);
            intent.putExtra(EXTRA_LATITUD, marker.getPosition().latitude);
            intent.putExtra(EXTRA_LONGITUD, marker.getPosition().longitude);

            startActivity(intent);
            */
        }
        return false;

    }

    @Override
    public void onInfoWindowClick(Marker marker) {


    }

    @Override
    public void onMarkerDragStart(Marker marker) {
        if (marker.equals(markerPais)) {
            //Toast.makeText(this, "START", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onMarkerDrag(Marker marker) {
        if (marker.equals(markerPais)) {
            String newTitle = String.format(Locale.getDefault(),
                    getString(R.string.marker_detail_latlng),
                    marker.getPosition().latitude,
                    marker.getPosition().longitude);

            setTitle(newTitle);
            //Toast.makeText(getApplicationContext(), newTitle, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        if (marker.equals(markerPais)) {
            //Toast.makeText(this, "END", Toast.LENGTH_SHORT).show();
        }
    }
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

        mMap.setMyLocationEnabled(true);





        // Markers
        LatLng entrega = new LatLng(10.3238, -84.4271);
        markerPais = googleMap.addMarker(new MarkerOptions()
                .position(entrega)
                .title("Entrega")
                .draggable(true)
        );

        ListaCompra.txtUbicacion.setText("Mi ubicacion: "+entrega.toString());

        // Cámara
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(entrega));

        // Eventos
        googleMap.setOnMarkerClickListener(this);


        // Eventos
        googleMap.setOnInfoWindowClickListener(this);

        // Eventos
        googleMap.setOnMarkerDragListener(this);

        UiSettings mapSettings;
        mapSettings = mMap.getUiSettings();
        mapSettings.setZoomControlsEnabled(true); //Set Zoom Controls
    }

}
