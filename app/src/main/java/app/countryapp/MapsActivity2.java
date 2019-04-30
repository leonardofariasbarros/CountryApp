package app.countryapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import app.countryapp.Model.Paises;

public class MapsActivity2 extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private List<Paises> paisesLista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps2);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        paisesLista = new ArrayList<Paises>();

        Intent i = getIntent();
        paisesLista = (List<Paises>) i.getSerializableExtra("paises");

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

        String latlng = null;

        LatLng paislatlng = null;

        LatLng brasil = new LatLng(-10.0,-55.0);

        //mMap.addMarker(new MarkerOptions().position(sydney).title("sidney"));
        for (int j = 0; j < paisesLista.size() - 1; j++) {
            latlng = paisesLista.get(j).getLatlng();
            paislatlng = new LatLng(Double.parseDouble(latlng.substring(latlng.indexOf('[')+1,latlng.indexOf(','))), Double.parseDouble(latlng.substring(latlng.indexOf(',')+1,latlng.indexOf(']'))));

            mMap.addMarker(new MarkerOptions().position(paislatlng).title(paisesLista.get(j).toString()));
        }

        mMap.moveCamera(CameraUpdateFactory.newLatLng(brasil));

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                return false;
            }
        });

    }



}
