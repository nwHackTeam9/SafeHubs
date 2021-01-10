package com.team9.safehubs;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ReviewActivity extends MainActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            setContentView(R.layout.activity_main);

            final Button button = findViewById(R.id.btnProfile);
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // Code here executes on main thread after user presses butto
                    setContentView(R.layout.activity_main);
                }
            });
        }
}
