package com.ist.android.issomeonethere;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class IsRoadAccessibleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_is_road_accessible);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(android.support.v7.appcompat.R.drawable.abc_ic_ab_back_material);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();
            }
        });

        final String type = getIntent().getExtras().getString("Type");
        final String category = getIntent().getExtras().getString("Category");

        if (type.equals("Need")) {
            toolbar.setBackgroundColor(getResources().getColor(R.color.appRed));
            toolbar.setTitle("I need " + category + "...");
        } else {
            toolbar.setBackgroundColor(getResources().getColor(R.color.appBlue));
            toolbar.setTitle("I can provide " + category + "...");
        }

        final Button b_yes = (Button) findViewById(R.id.b_yes);
        b_yes.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(
                        IsRoadAccessibleActivity.this,
                        ConfirmLocationActivity.class);
                intent.putExtras(getIntent().getExtras());
                if (getIntent().getExtras().getString("info") != null)
                    intent.putExtra("info", getIntent().getExtras().getString("info").concat("Road is accessible\n"));
                else
                    intent.putExtra("info", "Road is accessible\n");
                startActivity(intent);
            }
        });

        final Button b_no = (Button) findViewById(R.id.b_no);
        b_no.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(
                        IsRoadAccessibleActivity.this,
                        WhyNoRoadActivity.class);
                intent.putExtras(getIntent().getExtras());
                if (getIntent().getExtras().getString("info") != null)
                    intent.putExtra("info", getIntent().getExtras().getString("info").concat("Road NOT accessible\n"));
                else
                    intent.putExtra("info", "Road NOT accessible\n");
                startActivity(intent);
            }
        });
    }
}
