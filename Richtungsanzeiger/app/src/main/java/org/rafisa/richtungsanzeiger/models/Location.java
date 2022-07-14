package org.rafisa.richtungsanzeiger.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Location {
    private String name;
    private double longitude;
    private double latitude;

    public Location(String name, double longitude, double latitude) {
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getName() {
        return name;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public String toJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", name);
            jsonObject.put("longitude", longitude);
            jsonObject.put("latitude", latitude);
            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }
}
