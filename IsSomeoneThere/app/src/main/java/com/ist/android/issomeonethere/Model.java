package com.ist.android.issomeonethere;

import com.ist.android.issomeonethere.data.Obsolete;
import com.ist.android.issomeonethere.data.POI;
import com.ist.android.issomeonethere.data.RawData;
import com.ist.android.issomeonethere.data.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jjeannin on 9/16/17.
 */

public class Model {

    private RawData rawData;

    public List<POI> POIs;
    public List<User> users;
    public Obsolete obsolete;

    public Model() {

        POIs = new ArrayList<POI>();
        users = new ArrayList<User>();
        obsolete = new Obsolete();

        System.out.println("Init model");
        rawData = new RawData();

        updateObsolete();
        updatePOIs();
        updateUsers();
    }

    private void updatePOIs() {
        // Remove all POI
        POIs.clear();

        // Read from raw data
        try {
            JSONArray rawPOIs =  rawData.local_data.getJSONArray("pointOfInterests");
            for (int i = 0; i < rawPOIs.length(); i++) {
                JSONObject json_poi = rawPOIs.getJSONObject(i);

                POI java_poi = new POI();
                java_poi.setUuid(json_poi.getString("uid"));
                java_poi.setType(json_poi.getString("type"));
                java_poi.setCategory(json_poi.getString("category"));
                java_poi.setCapacity(json_poi.getInt("capacity"));

                JSONArray latlong = json_poi.getJSONArray("loc");
                java_poi.setLat(latlong.getDouble(0));
                java_poi.setLng(latlong.getDouble(1));

                java_poi.setCreated(json_poi.getString("created"));
                java_poi.setLastUpdated(json_poi.getString("lastUpdated"));

                java_poi.setChatId(json_poi.getString("chatUid"));

                // add if not obsolete
                POIs.add(java_poi);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void updateUsers() {
        // Remove all POI
        users.clear();

        // Read from raw data
        try {
            JSONArray rawUsers =  rawData.local_data.getJSONArray("users");
            for (int i = 0; i < rawUsers.length(); i++) {
                JSONObject json_user = rawUsers.getJSONObject(i);

                User java_user = new User();

                java_user.setUid(json_user.getString("uid"));
                java_user.setUsername(json_user.getString("username"));

                ArrayList<String> types = new ArrayList<String>();
                JSONArray json_types = json_user.getJSONArray("type");
                for (int j=0; j < json_types.length(); j++) {
                    types.add(json_types.getString(j));
                }
                java_user.setTypes(types);

                JSONArray latlong = json_user.getJSONArray("loc");
                java_user.setLat(latlong.getDouble(0));
                java_user.setLng(latlong.getDouble(1));

                java_user.setCreated(json_user.getString("created"));
                java_user.setLastUpdated(json_user.getString("lastUpdated"));

                users.add(java_user);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private void updateObsolete() {
        obsolete.clear();
        try {
            JSONArray rawObsolete =  rawData.local_data.getJSONArray("obsolete");
            for (int i = 0; i < rawObsolete.length(); i++) {
                JSONArray obsoleteData = rawObsolete.getJSONArray(i);
                obsolete.add(obsoleteData.getString(0));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

}
