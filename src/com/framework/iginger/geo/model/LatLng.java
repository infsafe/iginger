package com.framework.iginger.geo.model;


public class LatLng {
	public double lat;
    public double lng;

    /**
     * @return Returns a string of the form "lat,lng" for this LatLng.
     */
    public String toUrlValue() {
        return lat + "," + lng;
    }
    
    public Double getLat() {
    	return lat;
    }
    
    public Double getLng() {
    	return lng;
    }
}