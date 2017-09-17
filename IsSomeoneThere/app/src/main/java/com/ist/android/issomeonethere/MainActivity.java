package com.ist.android.issomeonethere;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Socket mSocket;
    public UDarkNode mUdark;
    private MainApplication app;
    public Boolean connected_io = false;
    public Boolean connected_udark = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        app = (MainApplication) getApplication();

        app.setUdarkNode(new UDarkNode(app));
        mUdark = app.getUDarkNode();
        mUdark.start();

        ImageView wifi = (ImageView) findViewById(R.id.imageWifi);
        if (app.getSocketStatus()) {
            wifi.setImageDrawable(getResources().getDrawable(R.drawable.wifi));
        }
        else {
            wifi.setImageDrawable(getResources().getDrawable(R.drawable.no_wifi));
        }

        ImageView udark = (ImageView) findViewById(R.id.imageMesh);
        if (mUdark.connected) {
            udark.setImageDrawable(getResources().getDrawable(R.drawable.mesh_on));
        }
        else {
            udark.setImageDrawable(getResources().getDrawable(R.drawable.mesh_off));
        }

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


   protected void onResume() {
       super.onResume();
       LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(broadcastReceiverSrv, new IntentFilter("WIFI_CHANGE"));
       LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(broadcastReceiverUdark, new IntentFilter("UDARK_CHANGE"));
   }

    @Override
    protected void onStart()
    {
        super.onStart();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private BroadcastReceiver broadcastReceiverSrv = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            boolean cnx = intent.getExtras().getBoolean("status");
            ImageView wifi = (ImageView) findViewById(R.id.imageWifi);
            if (cnx) {
                wifi.setImageDrawable(getResources().getDrawable(R.drawable.wifi));
            }
            else {
                wifi.setImageDrawable(getResources().getDrawable(R.drawable.no_wifi));
            }
        }
    };

    private BroadcastReceiver broadcastReceiverUdark = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            boolean cnx = intent.getExtras().getBoolean("status");
            ImageView mesh = (ImageView) findViewById(R.id.imageMesh);
            if (cnx) {
                mesh.setImageDrawable(getResources().getDrawable(R.drawable.mesh_on));
            }
            else {
                mesh.setImageDrawable(getResources().getDrawable(R.drawable.mesh_off));
            }
        }
    };

}
