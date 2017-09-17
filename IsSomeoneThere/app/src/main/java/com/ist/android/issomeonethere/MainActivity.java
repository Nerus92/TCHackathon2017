package com.ist.android.issomeonethere;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button b_need_help = (Button) findViewById(R.id.b_need_help);
        b_need_help.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(
                        MainActivity.this,
                        HelpCategoriesActivity.class);
                intent.putExtra("Type", "Need");
                startActivity(intent);
            }
        });

        final Button b_provide_help = (Button) findViewById(R.id.b_provide_help);
        b_provide_help.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(
                        MainActivity.this,
                        HelpCategoriesActivity.class);
                intent.putExtra("Type", "Provide");
                startActivity(intent);
            }
        });

        final Button b_map = (Button) findViewById(R.id.b_map);
        b_map.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(
                        MainActivity.this,
                        MapActivity.class);
                intent.putExtra("Type", "Map");
                startActivity(intent);
            }
        });
    }

}
