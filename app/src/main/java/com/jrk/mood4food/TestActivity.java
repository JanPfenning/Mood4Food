package com.jrk.mood4food;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

        Button btnClickMe = findViewById(R.id.btnClickMe);
        btnClickMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView tvClicked = findViewById(R.id.tvClicked);
                tvClicked.setVisibility(View.VISIBLE);
            }
        });
    }
}