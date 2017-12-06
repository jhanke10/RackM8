package com.jeffhanke.rackm8;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
    private View view;
    private Boolean parking;
    private String curr_park;
    private Boolean rated;

    public static Map newInstance() {
        Map fragment = new Map();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parking = false;
        rated = false;
        curr_park = "";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_map, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        final LatLng current = new LatLng(40.111763, -88.227049);
        LatLngBounds bounds = new LatLngBounds(new LatLng(40.1115010, -88.2274984), new LatLng(40.111977, -88.226796));
        mMap.setLatLngBoundsForCameraTarget(bounds);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(current, 17));
        mMap.addMarker(new MarkerOptions().position(current).title("Current Location").snippet("This is you!"));

        final LinearLayout ll = (LinearLayout) view.findViewById(R.id.infoField);
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(final Marker marker) {
                if(!marker.getTitle().equalsIgnoreCase("Current Location")) {
                    ll.setVisibility(View.VISIBLE);

                    TextView place = (TextView) view.findViewById(R.id.place);
                    TextView rating = (TextView) view.findViewById(R.id.rating);

                    place.setText(marker.getTitle());
                    rating.setText(marker.getSnippet());

                    final Button park = (Button) view.findViewById(R.id.park);
                    final Button rate = (Button) view.findViewById(R.id.rate);
                    rate.setEnabled(false);

                    final EditText line = (EditText) view.findViewById(R.id.line);
                    line.setEnabled(false);

                    final String curr_rate = marker.getSnippet();

                    if(!curr_park.equalsIgnoreCase(marker.getTitle())) {
                        park.setText("Park");
                    } else {
                        park.setText("Leave");
                        if(!rated) {
                            rate.setEnabled(true);
                            line.setEnabled(true);
                        }
                    }

                    line.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            if(!line.getText().toString().equalsIgnoreCase(""))
                                if(Integer.parseInt(line.getText().toString()) > 5)
                                    line.setText("");
                        }

                        @Override
                        public void afterTextChanged(Editable editable) {

                        }
                    });

                    park.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Drawable icon = getResources().getDrawable(R.drawable.signal);
                            Bitmap bitmap = ((BitmapDrawable) icon).getBitmap();
                            Bitmap result = Bitmap.createBitmap(bitmap, 0, 0,bitmap.getWidth() - 1, bitmap.getHeight() - 1);
                            Paint paint = new Paint();
                            if(!parking) {
                                ColorFilter filter = new LightingColorFilter(getColorByRating(Integer.parseInt(curr_rate.substring(8, 9))), Color.BLUE);
                                paint.setColorFilter(filter);
                                Canvas canvas = new Canvas(result);
                                canvas.drawBitmap(result, 0, 0, paint);
                                marker.setIcon(BitmapDescriptorFactory.fromBitmap(result));
                                curr_park = marker.getTitle();
                                park.setText("Leave");
                                rate.setEnabled(true);
                                line.setEnabled(true);
                                parking = true;
                            } else if(curr_park.equalsIgnoreCase(marker.getTitle()) && parking) {
                                ColorFilter filter = new LightingColorFilter(Color.BLUE, getColorByRating(Integer.parseInt(curr_rate.substring(8, 9))));
                                paint.setColorFilter(filter);
                                Canvas canvas = new Canvas(result);
                                canvas.drawBitmap(result, 0, 0, paint);
                                marker.setIcon(BitmapDescriptorFactory.fromBitmap(result));
                                park.setText("Park");
                                curr_park = "";
                                parking = false;
                            }
                        }
                    });

                    rate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(!line.getText().toString().equalsIgnoreCase("")) {
                                Toast.makeText(getActivity(), "Added Rating", Toast.LENGTH_SHORT).show();
                                line.setText("");
                                rated = true;
                                line.setEnabled(false);
                                rate.setEnabled(false);
                            }
                        }
                    });

                } else {
                    ll.setVisibility(View.GONE);
                }
                return false;
            }
        });

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                ll.setVisibility(View.GONE);
            }
        });

//        InfoAdapter adapter = new InfoAdapter(getActivity());
//        mMap.setInfoWindowAdapter(adapter);

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
                double rating = point.getDouble("rating");
                double latitude = point.getDouble("latitude");
                double longitude = point.getDouble("longitude");
                LatLng points = new LatLng(latitude, longitude);

                Drawable icon = getResources().getDrawable(R.drawable.signal);
                Bitmap bitmap = ((BitmapDrawable) icon).getBitmap();
                Bitmap result = Bitmap.createBitmap(bitmap, 0, 0,bitmap.getWidth() - 1, bitmap.getHeight() - 1);

                Paint paint = new Paint();
                ColorFilter filter = new LightingColorFilter(Color.BLACK, getColorByRating(rating));
                paint.setColorFilter(filter);
                Canvas canvas = new Canvas(result);
                canvas.drawBitmap(result, 0, 0, paint);

                Marker mark = mMap.addMarker(new MarkerOptions().position(points).title(name).snippet("Rating: " + rating + "/5.0").icon(BitmapDescriptorFactory.fromBitmap(result)));
                mark.setTag(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getColorByRating(double rating) {
        if(rating <= 2.0)
            return Color.rgb(173, 0, 0);
        if(rating < 4.0)
            return Color.rgb(211, 208, 0);
        return Color.rgb(4, 137, 19);
    }

}

class InfoAdapter implements GoogleMap.InfoWindowAdapter {
    private Activity context;
    private Boolean parking;

    public InfoAdapter(Activity context) {
        this.context = context;
        this.parking = false;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(final Marker marker) {
        View view = context.getLayoutInflater().inflate(R.layout.info, null);

        TextView place = (TextView) view.findViewById(R.id.place);
        TextView rating = (TextView) view.findViewById(R.id.rating);

        place.setText(marker.getTitle());
        rating.setText(marker.getSnippet());

        final Button park = (Button) view.findViewById(R.id.park);
        Button rate = (Button) view.findViewById(R.id.rate);

        final String curr_rate = marker.getSnippet();

        park.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Drawable icon = context.getResources().getDrawable(R.drawable.signal);
                Bitmap bitmap = ((BitmapDrawable) icon).getBitmap();
                Bitmap result = Bitmap.createBitmap(bitmap, 0, 0,bitmap.getWidth() - 1, bitmap.getHeight() - 1);
                Paint paint = new Paint();
                if(!parking) {
                    ColorFilter filter = new LightingColorFilter(getColorByRating(Integer.parseInt(curr_rate.substring(9, 12))), Color.BLUE);
                    paint.setColorFilter(filter);
                    Canvas canvas = new Canvas(result);
                    canvas.drawBitmap(result, 0, 0, paint);
                    marker.setIcon(BitmapDescriptorFactory.fromBitmap(result));
                    parking = true;
                } else {
                    ColorFilter filter = new LightingColorFilter(Color.BLUE, getColorByRating(Integer.parseInt(curr_rate.substring(9, 12))));
                    paint.setColorFilter(filter);
                    Canvas canvas = new Canvas(result);
                    canvas.drawBitmap(result, 0, 0, paint);
                    marker.setIcon(BitmapDescriptorFactory.fromBitmap(result));
                    parking = false;
                }
            }
        });

        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Updated Rating", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    public int getColorByRating(double rating) {
        if(rating <= 2.0)
            return Color.rgb(173, 0, 0);
        if(rating < 4.0)
            return Color.rgb(211, 208, 0);
        return Color.rgb(4, 137, 19);
    }
}
