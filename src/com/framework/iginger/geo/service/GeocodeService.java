package com.framework.iginger.geo.service;

import java.util.List;

import android.location.Address;

import com.framework.iginger.geo.model.GeocodeResponse;
import com.framework.iginger.geo.model.GeocodeResult;

/**
 * Interface for a Geocoding service.
 * @author ccashwell
 */
public interface GeocodeService {
	/**
	 * Generally, only one {@link GeocodeResult} is returned for address lookups, 
	 * though the geocoder may return several results when queries are ambiguous.
	 * 
	 * @param address the input address
	 * @return {@link GeocodeResponse} object with results.
	 */
    List<Address> getFromLocationName(String address);
	
	/**
	 * @param lat the latitude
	 * @param lng the longitude
	 * @return {@link GeocodeResponse} object with results.
	 */
    Address getFromLocation(double lat, double Lng);
}