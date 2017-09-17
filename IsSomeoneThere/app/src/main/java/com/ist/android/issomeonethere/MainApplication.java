package com.ist.android.issomeonethere;

import android.app.Application;
import io.socket.client.IO;
import io.socket.client.Socket;

import java.net.URISyntaxException;


public class MainApplication extends Application {
    public Model model;

    private Socket mSocket;
    {
        try {
            mSocket = IO.socket(Constant.SERVER_URL);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public Socket getSocket() {
        return mSocket;
    }


}
