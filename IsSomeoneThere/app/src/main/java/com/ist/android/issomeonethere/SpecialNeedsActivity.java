package com.ist.android.issomeonethere;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

public class SpecialNeedsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special_needs);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(android.support.v7.appcompat.R.drawable.abc_ic_ab_back_material);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();
            }
        });

        final CheckBox cb_elderly = (CheckBox) findViewById(R.id.cb_elderly);
        final CheckBox cb_disabled = (CheckBox) findViewById(R.id.cb_disabled);
        final CheckBox cb_toddler = (CheckBox) findViewById(R.id.cb_toddler);

        final Button b_confirm = (Button) findViewById(R.id.b_confirm);
        b_confirm.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(
                        SpecialNeedsActivity.this,
                        IsRoadAccessibleActivity.class);
                intent.putExtras(getIntent().getExtras());
                String toConcat = "";
                if (cb_elderly.isChecked())
                    toConcat.concat("We have at least one elderly/n");
                if (cb_disabled.isChecked())
                    toConcat.concat("We have at least one disabled person/n");
                if (cb_toddler.isChecked())
                    toConcat.concat("We have at least one toddler/n");
                intent.putExtra("info", getIntent().getExtras().getString("info").concat(toConcat));
                startActivity(intent);
            }
        });
    }
}
