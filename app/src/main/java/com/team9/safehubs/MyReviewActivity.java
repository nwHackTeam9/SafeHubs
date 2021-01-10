package com.team9.safehubs;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MyReviewActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerAdapter adapter;
    List<ReviewBlock> reviewBlockList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_review);

        reviewBlockList.add(new ReviewBlock("place1", (float) 3.5, "2021/01/05 12:12", "addit.comment1"));
        reviewBlockList.add(new ReviewBlock("place2", (float) 3.0, "2021/01/02 11:16", "addit.comment2"));
        reviewBlockList.add(new ReviewBlock("place3", (float) 4.0, "2021/01/02 05:13", ""));
        reviewBlockList.add(new ReviewBlock("place4", (float) 3.2, "2021/01/01 07:11", ""));
        reviewBlockList.add(new ReviewBlock("place5", (float) 3.1, "2021/01/05 10:18", ""));
        reviewBlockList.add(new ReviewBlock("place6", (float) 3.9, "2021/01/04 22:19", "addit.comment6"));
        reviewBlockList.add(new ReviewBlock("place7", (float) 4.5, "2021/01/08 16:13", "addit.comment7"));
        reviewBlockList.add(new ReviewBlock("place8", (float) 5.0, "2021/01/09 19:19", ""));

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        adapter = new RecyclerAdapter(reviewBlockList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}