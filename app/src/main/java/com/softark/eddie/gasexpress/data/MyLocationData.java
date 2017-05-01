package com.softark.eddie.gasexpress.data;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.softark.eddie.gasexpress.Constants;
import com.softark.eddie.gasexpress.Singleton.RequestSingleton;
import com.softark.eddie.gasexpress.adapters.LocationAdapter;
import com.softark.eddie.gasexpress.helpers.GEPreference;
import com.softark.eddie.gasexpress.models.Location;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Eddie on 4/30/2017.
 */

public class MyLocationData {

    private Context context;
    private RequestSingleton singleton;

    public MyLocationData(Context context) {
        this.context = context;
        singleton = new RequestSingleton(context);
    }

    public void getLocation(final RecyclerView recyclerView) {
        final ArrayList<Location> locations = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.GET_MY_LOCATIONS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                Location location = new Location();
                                location.setLng(object.getDouble("lng"));
                                location.setLat(object.getDouble("lat"));
                                location.setAddress(object.getString("address"));
                                location.setType(object.getInt("type"));
                                location.setId(object.getString("location_id"));
                                locations.add(location);
                            }
                            LocationAdapter adapter = new LocationAdapter(context, locations);
                            recyclerView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user", "12345");
                return params;
            }
        };
        singleton.addToRequestQueue(stringRequest);
    }

    public void addLocation(final Location location) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.ADD_LOCATION,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("lng", String.valueOf(location.getLng()));
                params.put("lat", String.valueOf(location.getLat()));
                params.put("address", location.getAddress());
                params.put("type", String.valueOf(location.getType()));
                params.put("user", "12345");
                return params;
            }
        };
        singleton.addToRequestQueue(stringRequest);
    }

    public void disableLocation(final String id) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.DISABLE_LOCATION,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("location", id);
                GEPreference preference = new GEPreference(context);
                params.put("user", preference.getUser().get(GEPreference.USER_ID));
                return params;
            }
        };
        singleton.addToRequestQueue(stringRequest);
    }


}
