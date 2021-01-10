package com.team9.safehubs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity
        implements OnMapReadyCallback {

    private GoogleMap map;
    private FirebaseDatabase database;
    private Button btnWriteReview;
    private Button btnReadReviews;
    private Place selectedPlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnWriteReview = (Button) findViewById(R.id.btnWriteReview);
        btnReadReviews = (Button) findViewById(R.id.btnReadReviews);

        btnWriteReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent reviewActivity = new Intent(MainActivity.this, ReviewActivity.class);
                reviewActivity.putExtra("place", selectedPlace); //Optional parameters
                startActivity(reviewActivity);
            }
        });

        // Write a message to the database
        database = FirebaseDatabase.getInstance();
        RequestQueue queue = Volley.newRequestQueue(this);
        Context context = this.getApplicationContext();
        String key = context.getString(R.string.api_key);

        /**
         * Initialize Places. For simplicity, the API key is hard-coded. In a production
         * environment we recommend using a secure mechanism to manage API keys.
         */
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(),
                    context.getString(R.string.api_key));
        }

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
                                                .title(place.getName()));
                                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,
                                                10f));

                                        // Set the selected place to be the place (used to pass intents)
                                        selectedPlace = place;

                                        // Add to database
                                        DatabaseReference placeRef = database
                                                .getReference("places/" + place.getId());

                                        // Check if value exists already or is null
                                        placeRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                Object data = dataSnapshot.getValue();

                                                if (data == null) {
                                                    placeRef.child("name").setValue(place.getName());
                                                    placeRef.child("lat").setValue(lat);
                                                    placeRef.child("lng").setValue(lng);
                                                } else {
                                                    Log.d("changeevent", data.toString());
                                                }
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {
                                                Log.e("onDataChange", "The read failed: " + databaseError.getCode());
                                            }
                                        });

                                        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                                            @Override
                                            public boolean onMarkerClick(Marker marker) {
                                                Intent reviewActivity = new Intent(MainActivity.this, ReviewActivity.class);
                                                reviewActivity.putExtra("place", selectedPlace); //Optional parameters
                                                startActivity(reviewActivity);
                                                return false;
                                            }
                                        });
                                        btnWriteReview.setEnabled(true);
                                        btnReadReviews.setEnabled(true);
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
        LatLng vancouver = new LatLng(49.2827, -123.1207);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(vancouver, 10f));
    }
}