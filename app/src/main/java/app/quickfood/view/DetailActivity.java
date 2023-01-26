package app.quickfood.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import app.quickfood.R;

public class DetailActivity extends AppCompatActivity implements OnMapReadyCallback
{
    private GoogleMap mMap;
    String destLat = "" , destLng ="" , startlat ="" , startlng ="";
    LatLng destPoint , startPoint;
    SharedPreferences preferences;

    // LatLng NewCastle = new LatLng(-79.6376197,43.719362);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        preferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        if (preferences.contains("StartLat")) {
            startlat = preferences.getString("StartLat", "");
        }
        if (preferences.contains("StartLng")) {
            startlng = preferences.getString("StartLng", "");
        }

        Intent intent = getIntent();
        destLat = intent.getStringExtra("DESTLAT");
        destLng = intent.getStringExtra("DESTLNG");

        destPoint = new LatLng(Double.parseDouble(destLng) , Double.parseDouble(destLat));
        Log.d("desPoint: " , destLat +"..."+destLng);

        if(startlat != null && startlng != null){
            Log.d("startPoint: " , startlat +"..."+startlng);
            startPoint = new LatLng(Double.parseDouble(startlng) , Double.parseDouble(startlat));
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.framemaps);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        //Add Marker
        mMap.addMarker(new MarkerOptions().position(startPoint).icon(BitmapDescriptorFactory.fromResource(R.drawable.starticon)));
        mMap.addMarker(new MarkerOptions().position(destPoint).icon(BitmapDescriptorFactory.fromResource(R.drawable.desticon)));

        // inside on map ready method
        // we will be displaying polygon on Google Maps.
        // on below line we will be adding polyline on Google Maps.
        mMap.addPolyline((new PolylineOptions()).add(startPoint, destPoint).
                // below line is use to specify the width of poly line.
                        width(5)
                // below line is use to add color to our poly line.
                .color(Color.BLACK)
                // below line is to make our poly line geodesic.
                .geodesic(true));
        // on below line we will be starting the drawing of polyline.
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(startPoint, 13));
    }

}