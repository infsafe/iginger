package com.framework.iginger.geo.model;


public class LatLngBounds {
	private static final long serialVersionUID = 1455168149631351220L;
	
	public LatLng southwest;
    public LatLng northeast;

    /**
     * @return Returns a string of the form "lat_southwest,lng_southwest,lat_northeast,lng_northeast"
     */
    public String toUrlValue() {
        return southwest.toUrlValue() + "," + northeast.toUrlValue();
    }
}