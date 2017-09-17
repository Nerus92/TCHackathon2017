package com.ist.android.issomeonethere.data;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jjeannin on 9/16/17.
 */

public class RawData {

    public String sample_data = "{\"pointOfInterests\":[{\"uid\":\"pf1\",\"type\":\"Provide\",\"category\":\"Food\",\"capacity\":10,\"loc\":[37.793641,-122.398631],\"created\":1505618874327,\"lastUpdated\":1505618874328,\"chatUid\":\"pf1\"},{\"uid\":\"pf2\",\"type\":\"Provide\",\"category\":\"Food\",\"capacity\":10,\"loc\":[37.779212,-122.483826],\"created\":1505618874327,\"lastUpdated\":1505618874328,\"chatUid\":\"pf2\"},{\"uid\":\"nf1\",\"type\":\"Need\",\"category\":\"Food\",\"capacity\":10,\"loc\":[37.754243,-122.424774],\"created\":1505618874327,\"lastUpdated\":1505618874328,\"chatUid\":\"nf1\"},{\"uid\":\"nf2\",\"type\":\"Need\",\"category\":\"Food\",\"capacity\":10,\"loc\":[37.746642,-122.390976],\"created\":1505618874327,\"lastUpdated\":1505618874328,\"chatUid\":\"nf2\"},{\"uid\":\"nm1\",\"type\":\"Need\",\"category\":\"Medical\",\"capacity\":10,\"loc\":[37.761843,-122.387009],\"created\":1505618874327,\"lastUpdated\":1505618874328,\"chatUid\":\"nm1\"},{\"uid\":\"nm2\",\"type\":\"Need\",\"category\":\"Medical\",\"capacity\":10,\"loc\":[37.785546,-122.448120],\"created\":1505618874327,\"lastUpdated\":1505618874328,\"chatUid\":\"nm2\"},{\"uid\":\"pm1\",\"type\":\"Provide\",\"category\":\"Medical\",\"capacity\":10,\"loc\":[37.752530,-122.447605],\"created\":1505618874327,\"lastUpdated\":1505618874328,\"chatUid\":\"pm1\"},{\"uid\":\"pm2\",\"type\":\"Provide\",\"category\":\"Medical\",\"capacity\":10,\"loc\":[37.795224,-122.423401],\"created\":1505618874327,\"lastUpdated\":1505618874328,\"chatUid\":\"pm2\"},{\"uid\":\"nr1\",\"type\":\"Need\",\"category\":\"Transportation\",\"capacity\":10,\"loc\":[37.794682,-122.381516],\"created\":1505618874327,\"lastUpdated\":1505618874328,\"chatUid\":\"nr1\"},{\"uid\":\"nr2\",\"type\":\"Need\",\"category\":\"Transportation\",\"capacity\":10,\"loc\":[37.791562,-122.384777],\"created\":1505618874327,\"lastUpdated\":1505618874328,\"chatUid\":\"nr2\"},{\"uid\":\"pr1\",\"type\":\"Provide\",\"category\":\"Transportation\",\"capacity\":10,\"loc\":[37.774874,-122.405891],\"created\":1505618874327,\"lastUpdated\":1505618874328,\"chatUid\":\"pr1\"},{\"uid\":\"pr2\",\"type\":\"Provide\",\"category\":\"Transportation\",\"capacity\":5,\"loc\":[37.779759,-122.487259],\"created\":1505618874327,\"lastUpdated\":1505618874327,\"chatUid\":\"pr2\"},{\"uid\":\"nw1\",\"type\":\"Need\",\"category\":\"Water\",\"capacity\":10,\"loc\":[37.746782,-122.493095],\"created\":1505618874327,\"lastUpdated\":1505618874328,\"chatUid\":\"nw1\"},{\"uid\":\"nw2\",\"type\":\"Need\",\"category\":\"Water\",\"capacity\":10,\"loc\":[37.799836,-122.418251],\"created\":1505618874327,\"lastUpdated\":1505618874328,\"chatUid\":\"nw2\"},{\"uid\":\"pw1\",\"type\":\"Provide\",\"category\":\"Water\",\"capacity\":10,\"loc\":[37.797937,-122.464085],\"created\":1505618874327,\"lastUpdated\":1505618874328,\"chatUid\":\"pw1\"},{\"uid\":\"pw2\",\"type\":\"Provide\",\"category\":\"Water\",\"capacity\":5,\"loc\":[37.764833,-122.417564],\"created\":1505618874327,\"lastUpdated\":1505618874327,\"chatUid\":\"pw2\"}]}";

    public JSONObject local_data;

    public RawData() {
        try {
            local_data = new JSONObject(sample_data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public JSONObject getLocal_data() {
        return local_data;
    }

    public void setLocal_data(JSONObject local_data) {
        this.local_data = local_data;
    }

}
