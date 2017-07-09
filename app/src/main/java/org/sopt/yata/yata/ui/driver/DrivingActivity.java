package org.sopt.yata.yata.ui.driver;

import android.app.FragmentManager;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

import org.sopt.yata.yata.R;
import org.sopt.yata.yata.application.YaTaActivity;

/**
 * Created by taehyung on 2017-07-08.
 */

public class DrivingActivity extends YaTaActivity implements OnMapReadyCallback {
    GoogleMap gMap;

    Double slat;
    Double slon;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driving);




        FragmentManager fm = getFragmentManager();
        MapFragment mf = (MapFragment) fm.findFragmentById(R.id.search_map);
        mf.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;

        if(slat == null && slon == null){
            slat = 37.574515;
            slon = 126.976930;
        }

        LatLng location = new LatLng(slat,slon);
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15));
        gMap.animateCamera(CameraUpdateFactory.zoomTo(15));

    }
}
