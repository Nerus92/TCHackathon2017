package com.ist.android.issomeonethere;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Random;

import io.underdark.Underdark;
import io.underdark.transport.Link;
import io.underdark.transport.Transport;
import io.underdark.transport.TransportKind;
import io.underdark.transport.TransportListener;

public class UDarkNode implements TransportListener {

    private boolean running;
    private Model model;
    private long nodeId;
    private Transport transport;
    private MainApplication app;

    private ArrayList<Link> links = new ArrayList<>();
    private int framesCount = 0;


    public UDarkNode(MainApplication app)
    {
        model = app.model;
        do
        {
            nodeId = new Random().nextLong();
        } while (nodeId == 0);

        if(nodeId < 0)
            nodeId = -nodeId;

        EnumSet<TransportKind> kinds = EnumSet.of(TransportKind.BLUETOOTH, TransportKind.WIFI);
        //kinds = EnumSet.of(TransportKind.WIFI);
        //kinds = EnumSet.of(TransportKind.BLUETOOTH);

        this.transport = Underdark.configureTransport(
                234235,
                nodeId,
                this,
                null,
                app.getApplicationContext(),
                kinds
        );

    }

    public void start()
    {
        if(running)
            return;

        running = true;
        transport.start();
    }

    public void stop()
    {
        if(!running)
            return;

        running = false;
        transport.stop();
    }

    public ArrayList<Link> getLinks()
    {
        return links;
    }

    public int getFramesCount()
    {
        return framesCount;
    }

    public void broadcastFrame(byte[] frameData)
    {
        if(links.isEmpty())
            return;

        ++framesCount;

        for(Link link : links)
            link.sendFrame(frameData);
    }

    @Override
    public void transportNeedsActivity(Transport transport, ActivityCallback activityCallback) {
        Log.i("UDark", "Transport need activity");
        //activityCallback.accept(activity);
    }

    @Override
    public void transportLinkConnected(Transport transport, Link link) {
        Log.i("UDark", "Link connected");
        links.add(link);
        //activity.connected_udark = true;

        try {
            broadcastFrame(model.toJSON().getBytes(StandardCharsets.UTF_8));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void transportLinkDisconnected(Transport transport, Link link) {
        Log.i("UDark", "Link disconnected");
        links.remove(link);

        if(links.isEmpty())
        {
            //activity.connected_udark = false;
            framesCount = 0;
        }
    }

    @Override
    public void transportLinkDidReceiveFrame(Transport transport, Link link, byte[] bytes) {
        Log.i("UDark", "Received frame");
        ++framesCount;

        String from_json = new String(bytes, StandardCharsets.UTF_8);
        try {
            JSONObject json = new JSONObject(from_json);
            long count = json.getLong("lastUpdated");
            Log.i("onNewContent UDark", "increment is "+ count);
            if (count > model.lastUpdated) {
                model.updateAll(json);
                //activity.syncOverNetworks();
                app.syncOverNetworks();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
