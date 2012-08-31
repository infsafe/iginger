package com.framework.iginger.geo.model;

public class Geometry {
    private String location_type;
    private LatLng location;
    private LatLngBounds viewport;
    
    public LocationType getLocationType() {
    	return LocationType.valueOf(location_type);
    }
    
    public LatLng getLocation() {
    	return location;
    }
    
    public LatLngBounds getViewport() {
    	return viewport;
    }
}