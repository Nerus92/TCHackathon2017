package com.ist.android.issomeonethere;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class WhyNoRoadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_why_no_road);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(android.support.v7.appcompat.R.drawable.abc_ic_ab_back_material);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();
            }
        });

        final Button b_blocked = (Button) findViewById(R.id.b_blocked);
        b_blocked.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(
                        WhyNoRoadActivity.this,
                        ConfirmLocationActivity.class);
                intent.putExtras(getIntent().getExtras());
                intent.putExtra("info", getIntent().getExtras().getString("info").concat("Because it's blocked\n"));
                startActivity(intent);
            }
        });

        final Button b_flooded = (Button) findViewById(R.id.b_flooded);
        b_flooded.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(
                        WhyNoRoadActivity.this,
                        ConfirmLocationActivity.class);
                intent.putExtras(getIntent().getExtras());
                intent.putExtra("info", getIntent().getExtras().getString("info").concat("Because of flooding\n"));
                startActivity(intent);
            }
        });

        final Button b_other = (Button) findViewById(R.id.b_other);
        b_other.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(
                        WhyNoRoadActivity.this,
                        ConfirmLocationActivity.class);
                intent.putExtras(getIntent().getExtras());
                intent.putExtra("info", getIntent().getExtras().getString("info").concat("For other reasons\n"));
                startActivity(intent);
            }
        });
    }
}
