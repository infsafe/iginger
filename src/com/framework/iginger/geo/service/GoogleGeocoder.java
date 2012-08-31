package com.framework.iginger.geo.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.location.Address;
import android.util.Log;

import com.framework.iginger.geo.model.GeocodeResult;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

public class GoogleGeocoder implements GeocodeService {
    private static final String GEOCODE_REQUEST_URL = "http://maps.google.com/maps/api/geocode/json";

    private List<GeocodeResult> doGeocodeRequest(String url) {
        HttpClient client = new DefaultHttpClient();
        // ʵ��HTTP����
        HttpPost request = new HttpPost(url);
        try {
            Log.e("brady", request.getURI().toString());
            // ִ������
            HttpResponse response = client.execute(request);
            if (response != null) {
                if (response.getStatusLine().getStatusCode() == 200) {
                    String string = EntityUtils.toString(response.getEntity());
                    Type listType = new TypeToken<List<GeocodeResult>>() {
                    }.getType();
                    Log.e("brady", string);
                    // In this test code i just shove the JSON here
                    // as string.
                    Gson gson = new Gson();
                    try {
                        JSONObject jObject = new JSONObject(string);
                        List<GeocodeResult> geocodeResults = gson.fromJson(jObject.getString("results"), listType);
                        return geocodeResults;
                    } catch (JsonSyntaxException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.chinateck.wonong.land.geo.service.GeocodeService#getFromLocationName
     * (java.lang.String)
     */
    @Override
    public List<Address> getFromLocationName(String locationName) {
        List<Address> addresses = new ArrayList<Address>();
        Map<String, String> map = new HashMap<String, String>();
        map.put("address", locationName);
        map.put("language", "zh-cn");
        map.put("sensor", "false");
        String url = UrlUtil.buildUrlByStringMapAndBaseUrl(GEOCODE_REQUEST_URL, map);
        List<GeocodeResult> geocodeResults = doGeocodeRequest(url);
        if (geocodeResults == null) {
            return null;
        }

        for (GeocodeResult result : geocodeResults) {
            Address address = convertGeocodeResultToAddress(result);
            addresses.add(address);
        }

        return addresses;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.chinateck.wonong.land.geo.service.GeocodeService#getFromLocation(
     * double, double)
     */
    @Override
    public Address getFromLocation(double lat, double lng) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("latlng", lat + "," + lng);
        map.put("language", "zh-cn");
        map.put("sensor", "false");
        String url = UrlUtil.buildUrlByStringMapAndBaseUrl(GEOCODE_REQUEST_URL, map);
        List<GeocodeResult> geocodeResults = doGeocodeRequest(url);
        if (geocodeResults == null) {
            return null;
        }

        return convertGeocodeResultToAddress(geocodeResults.get(0));
    }

    /**
     * @param geocodeResult
     * @return
     */
    private Address convertGeocodeResultToAddress(GeocodeResult result) {
        if (result == null) {
            return null;
        }

        Address address = new Address(Locale.CHINA);
        int size = result.address_components.size();
        size = size > 5 ? 5 : size;
        for (int i = 0; i < size; i++) {
            Log.e("brady", "i = " + i + "size = " + size
                    + result.address_components.get(i).long_name);
            address.setAddressLine(size - i, result.address_components.get(i).long_name);
        }

        Log.e("brady", "latlgn=" + result.geometry.getLocation().lat + ","
                + result.geometry.getLocation().lng);
        address.setLatitude(result.geometry.getLocation().lat);
        address.setLongitude(result.geometry.getLocation().lng);
        return address;
    }
}