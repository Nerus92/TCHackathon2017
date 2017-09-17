package com.ist.android.issomeonethere;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class CanYouGoThereActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_can_you_go_there);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(android.support.v7.appcompat.R.drawable.abc_ic_ab_back_material);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();
            }
        });

        final Button b_yes = (Button) findViewById(R.id.b_yes);
        b_yes.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(
                        CanYouGoThereActivity.this,
                        MapActivity.class);
                intent.putExtras(getIntent().getExtras());
                startActivity(intent);
            }
        });

        final Button b_no = (Button) findViewById(R.id.b_no);
        b_no.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = null;
                String sIssueType = getIntent().getExtras().getString("Type");
                if (sIssueType.equals("Shelter")) {
                    intent = new Intent(
                            CanYouGoThereActivity.this,
                            RescueActivity.class);
                } else {
                    intent = new Intent(
                            CanYouGoThereActivity.this,
                            IsRoadAccessibleActivity.class);
                }
                intent.putExtras(getIntent().getExtras());
                startActivity(intent);
            }
        });
    }
}
