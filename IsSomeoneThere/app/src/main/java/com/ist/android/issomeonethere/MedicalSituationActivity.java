package com.ist.android.issomeonethere;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class MedicalSituationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_situation);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(android.support.v7.appcompat.R.drawable.abc_ic_ab_back_material);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();
            }
        });

        final Button b_critical = (Button) findViewById(R.id.b_critical);
        b_critical.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(
                        MedicalSituationActivity.this,
                        IsRoadAccessibleActivity.class);
                intent.putExtras(getIntent().getExtras());
                intent.putExtra("info", "Critical condition/n");
                startActivity(intent);
            }
        });

        final Button b_serious = (Button) findViewById(R.id.b_serious);
        b_serious.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(
                        MedicalSituationActivity.this,
                        IsRoadAccessibleActivity.class);
                intent.putExtras(getIntent().getExtras());
                intent.putExtra("info", "Serious condition/n");
                startActivity(intent);
            }
        });

        final Button b_stable = (Button) findViewById(R.id.b_stable);
        b_stable.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(
                        MedicalSituationActivity.this,
                        IsRoadAccessibleActivity.class);
                intent.putExtras(getIntent().getExtras());
                intent.putExtra("info", "Stable condition/n");
                startActivity(intent);
            }
        });
    }
}
