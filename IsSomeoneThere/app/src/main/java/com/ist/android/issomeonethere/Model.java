package com.ist.android.issomeonethere;

import com.ist.android.issomeonethere.data.Obsolete;
import com.ist.android.issomeonethere.data.POI;
import com.ist.android.issomeonethere.data.RawData;
import com.ist.android.issomeonethere.data.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.EventObject;
import java.util.Iterator;
import java.util.List;


public class Model {

    private RawData rawData;

    public long lastUpdated;
    public List<POI> POIs;
    public List<User> users;
    public Obsolete obsolete;

    public Model() {

        POIs = new ArrayList<POI>();
        users = new ArrayList<User>();
        obsolete = new Obsolete();
        lastUpdated = 0; // System.currentTimeMillis();

        System.out.println("Init model");
        rawData = new RawData();

        updateObsolete();
        updatePOIs();
        updateUsers();
    }

    public void updateAll(JSONObject data) {

        synchronized (this) {
            rawData.setLocal_data(data);

            updateObsolete();
            updatePOIs();
            updateUsers();
            try {
                lastUpdated = data.getLong("lastUpdated");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
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

                java_poi.setCreated(json_poi.getLong("created"));
                java_poi.setLastUpdated(json_poi.getLong("lastUpdated"));

                java_poi.setChatId(json_poi.getString("chatUid"));

                // add if not obsolete
                POIs.add(java_poi);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void increment() {
        lastUpdated++;
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

                java_user.setCreated(json_user.getLong("created"));
                java_user.setLastUpdated(json_user.getLong("lastUpdated"));

                users.add(java_user);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void updateObsolete() {
        obsolete.clear();
        try {
            JSONObject rawObsolete =  rawData.local_data.getJSONObject("obsolete");

            Iterator<String> it = rawObsolete.keys();
            while (it.hasNext()) {
                obsolete.add(it.next());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public String toJSON() throws JSONException {

        synchronized (this) {

            JSONObject json_model = new JSONObject();

            // ------ Obso
            JSONObject json_obsolete = new JSONObject();
            for(String obso : obsolete.getOldies()) {
                json_obsolete.put(obso, "toAddLater");
            }

            json_model.put("obsolete", json_obsolete);

            // ------ POI
            JSONArray json_pois = new JSONArray();
            for(POI poi : POIs) {
                JSONObject json_poi = new JSONObject();
                json_poi.put("uid", poi.getUuid());
                json_poi.put("type", poi.getType());
                json_poi.put("category", poi.getCategory());
                json_poi.put("capacity", poi.getCapacity());
                JSONArray json_poi_loc = new JSONArray();
                json_poi_loc.put(poi.getLat());
                json_poi_loc.put(poi.getLng());
                json_poi.put("loc", json_poi_loc);
                json_poi.put("created", poi.getCreated());
                json_poi.put("lastUpdated", poi.getLastUpdated());
                json_poi.put("chatUid", poi.getChatId());

                json_pois.put(json_poi);
            }

            json_model.put("pointOfInterests", json_pois);

            // --------- Users
            JSONArray json_users = new JSONArray();
            for(User user : users) {
                JSONObject json_user = new JSONObject();
                json_user.put("uid", user.getUid());

                JSONArray type = new JSONArray();
                for(String t : user.getTypes()) {
                    type.put(t);
                }
                json_user.put("type", type);

                JSONArray json_user_loc = new JSONArray();
                json_user_loc.put(user.getLat());
                json_user_loc.put(user.getLng());
                json_user.put("loc", json_user_loc);

                json_user.put("created", user.getCreated());
                json_user.put("lastUpdated", user.getLastUpdated());

                json_user.put("username", user.getUsername());

                json_users.put(json_user);
            }

            json_model.put("users", json_users);

            json_model.put("lastUpdated", lastUpdated);

            return json_model.toString();
        }
    }

}
