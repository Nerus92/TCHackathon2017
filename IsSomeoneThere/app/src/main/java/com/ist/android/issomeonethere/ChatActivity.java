package com.ist.android.issomeonethere;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.ist.android.issomeonethere.data.ChatRoom;

public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(android.support.v7.appcompat.R.drawable.abc_ic_ab_back_material);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();
            }
        });
        /*
        TextView tv_chat = (TextView) findViewById(R.id.tv_chat);
        tv_chat.setText(getIntent().getExtras().getString("info"));
        */

        // you get an extra called chat_uuid
        final String chat_uuid = getIntent().getExtras().getString("chat_uuid");

        final String to_add = getIntent().getExtras().getString("info");
        if (to_add != null) {
            ChatRoom ch = ((MainApplication)getApplication()).model.getChatRoomByUid(chat_uuid);
            if (ch == null) {
                ((MainApplication)getApplication()).model.createChatRoom(chat_uuid);
                ch = ((MainApplication)getApplication()).model.getChatRoomByUid(chat_uuid);
            }
            ch.addText(to_add);
            ((MainApplication)getApplication()).model.increment();
            ((MainApplication)getApplication()).syncOverNetworks();
        }

        displayMessage(chat_uuid);

        final ImageButton b_send = (ImageButton) findViewById(R.id.send_button);
        b_send.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final EditText edit_text = (EditText) findViewById(R.id.message_input);
                Log.i("Text : ", String.valueOf(edit_text.getText()));
                String text = String.valueOf(edit_text.getText());

                edit_text.setText("");
                ChatRoom ch = ((MainApplication)getApplication()).model.getChatRoomByUid(chat_uuid);
                if (ch == null) {
                    ((MainApplication)getApplication()).model.createChatRoom(chat_uuid);
                    ch = ((MainApplication)getApplication()).model.getChatRoomByUid(chat_uuid);
                }
                ch.addText(text);

                ((MainApplication)getApplication()).model.increment();
                ((MainApplication)getApplication()).syncOverNetworks();

                displayMessage(chat_uuid);

                Context context = ((MainApplication)getApplication()).getApplicationContext();
                InputMethodManager inputManager =
                        (InputMethodManager) context.
                                getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(
                        b_send.getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });

    }

    public void displayMessage(String uuid) {
        final TextView chatText = (TextView) findViewById(R.id.textViewMessages);
        //chatText.setText("");

        ChatRoom ch = ((MainApplication)getApplication()).model.getChatRoomByUid(uuid);
        if (ch == null) {
            ((MainApplication)getApplication()).model.createChatRoom(uuid);
            ch = ((MainApplication)getApplication()).model.getChatRoomByUid(uuid);
        }

        String text = "";
        if (ch != null) {
            for (String c : ch.getTexts()) {
                text = text.concat(c + "\n");
            }

            chatText.setText(text);
        }
    }

    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(broadcastReceiverSrv, new IntentFilter("MODEL_UPDATED"));
    }

    private BroadcastReceiver broadcastReceiverSrv = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String chat_uuid = getIntent().getExtras().getString("chat_uuid");
            displayMessage(chat_uuid);
        }
    };
}
