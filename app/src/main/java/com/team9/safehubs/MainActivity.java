package com.team9.safehubs;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity
        implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap map;
    Marker marker;
    double lat, lng;
    private FirebaseDatabase database;
    DatabaseReference mDatabase;
    Button buttonWriteReview;
    Button buttonMyReview;
    String placeName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Write a message to the database
        database = FirebaseDatabase.getInstance();
        RequestQueue queue = Volley.newRequestQueue(this);
        Context context = this.getApplicationContext();
        String key = context.getString(R.string.api_key);
        buttonWriteReview = findViewById(R.id.btnWriteReview);
        buttonMyReview = findViewById(R.id.btnReview);

        mDatabase = FirebaseDatabase.getInstance().getReference("review");//Dont pass any path if you want root of the tree


        buttonWriteReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("PLACE NAME: " + placeName);
                Intent reviewActivity = new Intent(MainActivity.this, ReviewActivity.class);
                reviewActivity.putExtra("place_name", placeName); //Optional parameters
                startActivity(reviewActivity);
            }
        });

        buttonMyReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myReviewActivity = new Intent(MainActivity.this, MyReviewActivity.class);
                startActivity(myReviewActivity);
            }
        });
        /**
         * Initialize Places. For simplicity, the API key is hard-coded. In a production
         * environment we recommend using a secure mechanism to manage API keys.
         */
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(),
                    context.getString(R.string.api_key));
        }

//        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
//            @Override
//            public void onMapClick(LatLng latLng) {
//                if(marker == null) {
//                    marker = map.addMarker(new MarkerOptions().draggable(true).position(latLng));
//                } else {
//                    marker.setPosition(latLng);
//                }
//            }
//        });
        // Initialize the AutocompleteSupportFragment.
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {

                String url = "https://maps.googleapis.com/maps/api/place/details/json?" +
                        "key=" + key + "&place_id=" + place.getId();

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                        (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    JSONObject location = response.getJSONObject("result")
                                            .getJSONObject("geometry").getJSONObject("location");

                                    Double lat = location.getDouble("lat");
                                    Double lng = location.getDouble("lng");
                                    LatLng latLng = new LatLng(lat, lng);

                                    if (latLng != null) {
                                        map.addMarker(new MarkerOptions()
                                                .position(latLng)
                                                .draggable(true)
                                                .title(place.getName()));
                                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,
                                                10f));
                                        // Add to database
                                        DatabaseReference placeRef = database
                                                .getReference("places/" + place.getId());
                                        placeRef.child("name").setValue(place.getName());
                                        placeRef.child("lat").setValue(lat);
                                        placeRef.child("lng").setValue(lng);
                                        placeRef.get().getResult().getValue();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // TODO: Handle error

                            }
                        }
                      );
                queue.add(jsonObjectRequest);
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i("TAG", "An error occurred: " + status);
            }
        });

        // Get the SupportMapFragment and request notification
        // when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapView);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        LatLng sydney = new LatLng(-33.852, 151.211);
        map.addMarker(new MarkerOptions()
                .position(sydney)
                .draggable(true)
                .title(getAddress(sydney.latitude, sydney.longitude)));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 10f));
        map.getUiSettings().setZoomControlsEnabled(true);

        googleMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {

            }

            @Override
            public void onMarkerDrag(Marker marker) {
                LatLng latLng = marker.getPosition();
                lat = latLng.latitude;
                lng = latLng.longitude;
//                marker.setTitle(getAddress(lat,lng));
            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                LatLng latLng = marker.getPosition();
                lat = latLng.latitude;
                lng = latLng.longitude;

                Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
                try {
                    android.location.Address address = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1).get(0);
                    marker.setTitle(getAddress(lat, lng));
                    placeName = getAddress(lat, lng);    //address.getLocality();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                System.out.println("PLACE NAME: " + placeName);
                Intent reviewActivity = new Intent(MainActivity.this, ReviewActivity.class);
                reviewActivity.putExtra("place_name", placeName); //Optional parameters
                startActivity(reviewActivity);
                return false;
            }
        });
    }

    public String getAddress(double lat, double lng) {
        Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
        String add = "";
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            Address obj = addresses.get(0);
            add = obj.getAddressLine(0);
            add = add + "\n" + obj.getCountryName();
            add = add + "\n" + obj.getCountryCode();
            add = add + "\n" + obj.getAdminArea();
            add = add + "\n" + obj.getPostalCode();
            add = add + "\n" + obj.getSubAdminArea();
            add = add + "\n" + obj.getLocality();
            add = add + "\n" + obj.getSubThoroughfare();

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return add;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        System.out.println("PLACE NAME: " + placeName);
        Intent reviewActivity = new Intent(MainActivity.this, ReviewActivity.class);
        reviewActivity.putExtra("place_name", placeName); //Optional parameters
        startActivity(reviewActivity);

        return false;
    }
}