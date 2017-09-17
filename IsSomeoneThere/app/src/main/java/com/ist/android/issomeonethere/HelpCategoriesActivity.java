package com.ist.android.issomeonethere;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class HelpCategoriesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_need_help);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(android.support.v7.appcompat.R.drawable.abc_ic_ab_back_material);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();
            }
        });

        final Button b_medical = (Button) findViewById(R.id.b_medical);
        b_medical.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (getIntent().getExtras().getString("Type").equals("need")) {
                    Intent intent = new Intent(
                            HelpCategoriesActivity.this,
                            MedicalSituationActivity.class);
                    intent.putExtras(getIntent().getExtras());
                    intent.putExtra("Category", "medical");
                    startActivity(intent);
                } else {

                }
            }
        });

        final Button b_rescue = (Button) findViewById(R.id.b_rescue);
        b_rescue.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (getIntent().getExtras().getString("Type").equals("need")) {
                    Intent intent = new Intent(
                            HelpCategoriesActivity.this,
                            RescueActivity.class);
                    intent.putExtras(getIntent().getExtras());
                    intent.putExtra("Category", "transportation");
                    startActivity(intent);
                }
            }
        });

        final Button b_shelter = (Button) findViewById(R.id.b_shelter);
        b_shelter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (getIntent().getExtras().getString("Type").equals("need")) {
                    Intent intent = new Intent(
                            HelpCategoriesActivity.this,
                            CanYouMoveActivity.class);
                    intent.putExtras(getIntent().getExtras());
                    intent.putExtra("Category", "shelter");
                    startActivity(intent);
                }
            }
        });

        final Button b_food = (Button) findViewById(R.id.b_food);
        b_food.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (getIntent().getExtras().getString("Type").equals("need")) {
                    Intent intent = new Intent(
                            HelpCategoriesActivity.this,
                            CanYouGoThereActivity.class);
                    intent.putExtras(getIntent().getExtras());
                    intent.putExtra("Category", "food");
                    startActivity(intent);
                }
            }
        });

        final Button b_water = (Button) findViewById(R.id.b_water);
        b_water.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (getIntent().getExtras().getString("Type").equals("need")) {
                    Intent intent = new Intent(
                            HelpCategoriesActivity.this,
                            CanYouGoThereActivity.class);
                    intent.putExtras(getIntent().getExtras());
                    intent.putExtra("Category", "water");
                    startActivity(intent);
                }
            }
        });
    }
}
