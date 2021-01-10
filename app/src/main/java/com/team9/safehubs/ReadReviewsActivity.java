package com.team9.safehubs;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class ReadReviewsActivity extends AppCompatActivity {

    private TextView txtSummary;
    private RecyclerView recyclerView;
    private RecyclerAdapter adapter;
    private List<ReviewBlock> reviewBlockList = new ArrayList<>();
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_reviews);
        txtSummary = findViewById(R.id.txtSummary);

        database = FirebaseDatabase.getInstance();

        Intent intent = getIntent();
        String placeID = intent.getStringExtra("place_id");
        String placeName = intent.getStringExtra("place_name");
        DatabaseReference placeRef = database
                .getReference("places/" + placeID);

        placeRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                PlaceReviews placeReviews = dataSnapshot.getValue(PlaceReviews.class);
                String name = placeReviews.getName();
                Double avgRating = placeReviews.getAvg_rating();
                Long numReviews = placeReviews.getNum_reviews();

                for (Integer i = 0; i < numReviews; ++i) {
                    Double rating = Double.parseDouble(dataSnapshot.child("reviews/" + i.toString() + "/rating")
                            .getValue().toString());
                    String text = dataSnapshot.child("reviews/" + i.toString() + "/text").getValue().toString();

                    reviewBlockList.add((new ReviewBlock(rating, text)));
                }

                txtSummary.setText(name + ": " + avgRating.toString() + " (" + numReviews.toString() + " reviews)");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("onDataChange", "The read failed: " + databaseError.getCode());
            }
        });

//        reviewBlockList.add(new ReviewBlock("place2", (float) 3.0, "addit.comment2"));
//        reviewBlockList.add(new ReviewBlock("place3", (float) 4.0, ""));
//        reviewBlockList.add(new ReviewBlock("place4", (float) 3.2, ""));
//        reviewBlockList.add(new ReviewBlock("place5", (float) 3.1, ""));
//        reviewBlockList.add(new ReviewBlock("place6", (float) 3.9, "addit.comment6"));
//        reviewBlockList.add(new ReviewBlock("place7", (float) 4.5, "addit.comment7"));
//        reviewBlockList.add(new ReviewBlock("place8", (float) 5.0, ""));

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        adapter = new RecyclerAdapter(reviewBlockList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}