package com.ist.android.issomeonethere.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by jjeannin on 9/17/17.
 */

public class ChatRoom {

    public String uid;
    public List<String> texts;

    public ChatRoom() {
        texts = new ArrayList<String>();
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public List<String> getTexts() {
        return texts;
    }

    public void setTexts(List<String> texts) {
        this.texts = texts;
    }

    public void clearTexts() {
        texts.clear();
    }

    public void addText(String t) {
        texts.add(t);
    }


}
