package com.ist.android.issomeonethere;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.travijuu.numberpicker.library.NumberPicker;

public class RescueActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rescue);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(android.support.v7.appcompat.R.drawable.abc_ic_ab_back_material);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();
            }
        });

        final NumberPicker np_adults = (NumberPicker) findViewById(R.id.np_adults);
        np_adults.setMax(10);
        np_adults.setMin(0);
        np_adults.setUnit(1);
        np_adults.setValue(1);

        final NumberPicker np_children = (NumberPicker) findViewById(R.id.np_children);
        np_children.setMax(10);
        np_children.setMin(0);
        np_children.setUnit(1);
        np_children.setValue(0);

        final NumberPicker np_pets = (NumberPicker) findViewById(R.id.np_pets);
        np_pets.setMax(10);
        np_pets.setMin(0);
        np_pets.setUnit(1);
        np_pets.setValue(0);

        final Button b_confirm = (Button) findViewById(R.id.b_confirm);
        b_confirm.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(
                        RescueActivity.this,
                        SpecialNeedsActivity.class);
                intent.putExtras(getIntent().getExtras());
                intent.putExtra("info", String.format("We have %s adults, %s children and %s pets\n",
                        np_adults.getValue(),
                        np_children.getValue(),
                        np_pets.getValue()));
                startActivity(intent);
            }
        });

    }
}
