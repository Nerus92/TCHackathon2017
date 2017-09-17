package com.ist.android.issomeonethere;

import android.app.Application;
import android.content.Intent;
import android.media.Image;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Button;

import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import java.net.URISyntaxException;

import static android.R.attr.data;


public class MainApplication extends Application {

    public boolean socket_connected = false;
    public boolean udark_connected = false;

    public Model model;
    {
        model = new Model();
    }

    private Emitter.Listener onConnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            socket_connected = true;
            changeServStatus();
            System.out.println("socket.io connected");

        }
    };

    private Emitter.Listener onDisconnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            socket_connected = false;
            changeServStatus();
            System.out.println("socket.io disconnected");
        }
    };

    private Emitter.Listener onConnectError = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            socket_connected = false;
            changeServStatus();
            System.out.println("socket.io connection error");
        }
    };

    private Emitter.Listener onNewContent = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            try {
                JSONObject data;
                try {
                    data = (JSONObject) args[0];
                } catch(ClassCastException e ) {
                    String t = (String) args[0];
                    data = new JSONObject(t);
                }
                long count = data.getLong("lastUpdated");
                Log.i("onNewContent", "increment is "+ count);

                if (count > model.lastUpdated) {
                    model.updateAll(data);
                    syncOverNetworks();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };


    private Socket mSocket;
    {
        try {
            mSocket = IO.socket(Constant.SERVER_URL);

            mSocket.on(Socket.EVENT_CONNECT,onConnect);
            mSocket.on(Socket.EVENT_DISCONNECT,onDisconnect);
            mSocket.on(Socket.EVENT_CONNECT_ERROR, onConnectError);
            mSocket.on(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
            mSocket.on("content", onNewContent);
            mSocket.connect();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public Socket getSocket() {
        return mSocket;
    }

    public UDarkNode mUdark;

    public UDarkNode getUDarkNode() {
        return mUdark;
    }

    public void setUdarkNode(UDarkNode udark) {
        mUdark = udark;
    }


    public void syncOverNetworks() {
        String json = null;
        try {
            json = model.toJSON();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mSocket.emit("content", json);
        if (mUdark != null) {
            mUdark.broadcastFrame(json.getBytes());
        }
    }

    public void changeServStatus() {
        Log.i("JJ", "changeSerStatus "+socket_connected);
        Intent wifichanged = new Intent("WIFI_CHANGE");
        wifichanged.putExtra("status", socket_connected);
        LocalBroadcastManager.getInstance(this.getApplicationContext()).sendBroadcast(wifichanged);
    }

    public boolean getSocketStatus()
    {
        return socket_connected;
    }


}
