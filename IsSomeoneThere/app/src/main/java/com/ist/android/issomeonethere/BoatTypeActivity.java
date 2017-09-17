package com.ist.android.issomeonethere;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class BoatTypeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boat_type);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(android.support.v7.appcompat.R.drawable.abc_ic_ab_back_material);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();
            }
        });

        final Button b_motorboat = (Button) findViewById(R.id.b_motorboat);
        b_motorboat.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(
                        BoatTypeActivity.this,
                        MapActivity.class);
                intent.putExtras(getIntent().getExtras());
                intent.putExtra("info", "It's a motor boat\n");
                startActivity(intent);
            }
        });

        final Button b_inflatable = (Button) findViewById(R.id.b_inflatable);
        b_inflatable.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(
                        BoatTypeActivity.this,
                        MapActivity.class);
                intent.putExtras(getIntent().getExtras());
                intent.putExtra("info", "It's an inflatable boat\n");
                startActivity(intent);
            }
        });
    }
}
