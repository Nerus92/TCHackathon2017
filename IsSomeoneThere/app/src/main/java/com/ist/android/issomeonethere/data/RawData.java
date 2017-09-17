package com.ist.android.issomeonethere.data;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jjeannin on 9/16/17.
 */

public class RawData {

    public String sample_data = "{\"pointOfInterests\":[{\"uid\":\"uid1\",\"type\":\"fire\",\"category\":\"issue\",\"capacity\":10,\"loc\":[37.773724,-122.390976],\"created\":\"xxx\",\"lastUpdated\":\"yyy\",\"chatUid\":\"chatUID_1\"},{\"uid\":\"uid2\",\"type\":\"shelter\",\"category\":\"ressource\",\"capacity\":5,\"loc\":[37.776844,-122.39151],\"created\":\"xxx\",\"lastUpdated\":\"yyy\",\"chatUid\":\"chatUID_2\"}],\"users\":[{\"uid\":\"user1\",\"type\":[\"doctor\",\"fireman\"],\"loc\":[37.776844,-122.39111],\"created\":\"xxx\",\"lastUpdated\":\"yyy\",\"username\":\"Toto\"}],\"obsolete\":[[\"old1\",\"aaa\"],[\"old2\",\"bbb\"]]}";

    public JSONObject local_data;

    public RawData() {
        try {
            local_data = new JSONObject(sample_data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



}
