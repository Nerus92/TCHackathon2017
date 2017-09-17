package com.ist.android.issomeonethere;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
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
    public Model model;

    public Boolean connected_io = false;
    public Boolean connected_udark = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        model = new Model();
        mUdark = new UDarkNode(this);

        MainApplication app = (MainApplication) getApplication();
        mSocket = app.getSocket();
        mSocket.on(Socket.EVENT_CONNECT,onConnect);
        mSocket.on(Socket.EVENT_DISCONNECT,onDisconnect);
        mSocket.on(Socket.EVENT_CONNECT_ERROR, onConnectError);
        mSocket.on(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
        mSocket.on("content", onNewContent);
        mSocket.connect();

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

    @Override
    protected void onStart()
    {
        super.onStart();
        mUdark.start();
    }

    @Override
    protected void onStop()
    {
        super.onStop();

        if(mUdark != null)
            mUdark.stop();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        mSocket.disconnect();

        mSocket.off(Socket.EVENT_CONNECT, onConnect);
        mSocket.off(Socket.EVENT_DISCONNECT, onDisconnect);
        mSocket.off(Socket.EVENT_CONNECT_ERROR, onConnectError);
        mSocket.off(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
        mSocket.off("content", onNewContent);
    }

    private Emitter.Listener onConnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    connected_io = true;
                    System.out.println("socket.io connected");
                }
            });
        }
    };

    private Emitter.Listener onDisconnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    connected_io = false;
                    System.out.println("socket.io disconnected");
                }
            });
        }
    };

    private Emitter.Listener onConnectError = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    connected_io = false;
                    System.out.println("socket.io connection error");
                }
            });
        }
    };

    private Emitter.Listener onNewContent = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    try {
                        long count = data.getLong("lastUpdated");
                        Log.i("onNewContent", "increment is "+ count);

//                        final TextView text = (TextView) findViewById(R.id.mText);
//                        text.setText(Long.toString(count));

                        if (count > model.lastUpdated) {
                            model.updateAll(data);
                            syncOverNetworks();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };

    public void syncOverNetworks() {
        String json = null;
        try {
            json = model.toJSON();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mSocket.emit("content", json);
        mUdark.broadcastFrame(json.getBytes());
    }


}
