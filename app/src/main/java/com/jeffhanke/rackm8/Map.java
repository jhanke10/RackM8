package com.jeffhanke.rackm8;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class Map extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;

    public static Map newInstance() {
        Map fragment = new Map();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_map, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng current = new LatLng(40.111763, -88.227049);
        LatLngBounds bounds = new LatLngBounds(new LatLng(40.1115010, -88.2274984), new LatLng(40.111977, -88.226796));
        mMap.setLatLngBoundsForCameraTarget(bounds);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(current, 17));
        mMap.addMarker(new MarkerOptions().position(current).title("Current Location").snippet("Hi! What is up"));

        InputStream is = null;
        try {
            is = getResources().openRawResource(R.raw.coordinates);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String myJson = new String(buffer, "UTF-8");
            JSONObject obj = new JSONObject(myJson);
            JSONArray data = obj.getJSONArray("features");
            for(int i = 0; i < data.length(); i++) {
                JSONObject point = data.getJSONObject(i);
                String name = point.getString("name");
                String rating = point.getString("rating");
                double latitude = point.getDouble("latitude");
                double longitude = point.getDouble("longitude");
                LatLng points = new LatLng(latitude, longitude);
                Marker mark = mMap.addMarker(new MarkerOptions().position(points).title(name).snippet(rating).icon(BitmapDescriptorFactory.fromResource(R.drawable.signal)));
                mark.setTag(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
