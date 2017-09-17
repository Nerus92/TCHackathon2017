package com.ist.android.issomeonethere;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class TransportationTypeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transportation_type);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(android.support.v7.appcompat.R.drawable.abc_ic_ab_back_material);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();
            }
        });

        final Button b_boat = (Button) findViewById(R.id.b_boat);
        b_boat.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(
                        TransportationTypeActivity.this,
                        BoatTypeActivity.class);
                intent.putExtras(getIntent().getExtras());
                intent.putExtra("info", "I have a boat\n");
                startActivity(intent);
            }
        });

        final Button b_car = (Button) findViewById(R.id.b_car);
        b_car.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(
                        TransportationTypeActivity.this,
                        CarTypeActivity.class);
                intent.putExtras(getIntent().getExtras());
                intent.putExtra("info", "I have a car\n");
                startActivity(intent);
            }
        });
    }
}
