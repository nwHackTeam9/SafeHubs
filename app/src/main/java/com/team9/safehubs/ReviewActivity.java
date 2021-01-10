package com.team9.safehubs;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ReviewActivity extends AppCompatActivity {

    private float rating1 = 0, rating2 = 0, rating3 = 0, rating4 = 0, avgRating = 0;
    private String additional_comments;
    private boolean isRated1 = false, isRated2 = false, isRated3 = false, isRated4 = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        RatingBar ratingBar1 = findViewById(R.id.RatingBar1);
        RatingBar ratingBar2 = findViewById(R.id.RatingBar2);
        RatingBar ratingBar3 = findViewById(R.id.RatingBar3);
        RatingBar ratingBar4 = findViewById(R.id.RatingBar4);
        EditText additionalComments = findViewById(R.id.additionalComments);
        Button submitButton = findViewById(R.id.submitButton);

        ratingBar1.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                rating1 = v;
                isRated1 = true;
            }
        });

        ratingBar2.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                rating2 = v;
                isRated2 = true;
            }
        });

        ratingBar3.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                rating3 = v;
                isRated3 = true;
            }
        });

        ratingBar4.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                rating4 = v;
                isRated4 = true;
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isRated1) {
                    if (isRated2) {
                        if (isRated3) {
                            if (isRated4) {
                                avgRating = (rating1 + rating2 + rating3 + rating4) / 4;
                                additional_comments = additionalComments.getText().toString();

                                new AlertDialog.Builder(ReviewActivity.this)
                                        .setTitle("Review Submitted")
                                        .setMessage("Average Rating: " + String.valueOf(avgRating))
                                        .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                finish();
                                            }
                                        })
                                        .setIcon(R.drawable.ic_baseline_done_24)
                                        .show();

//                                Toast.makeText(ReviewActivity.this, "Review Submitted Successfully!!! Avg. Rating: "+String.valueOf(avgRating),
//                                        Toast.LENGTH_LONG).show();

                            } else {
                                Toast.makeText(ReviewActivity.this, "Please rate the 4th question!!!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(ReviewActivity.this, "Please rate the 3rd question!!!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(ReviewActivity.this, "Please rate the 2nd question!!!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ReviewActivity.this, "Please rate the 1st question!!!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}