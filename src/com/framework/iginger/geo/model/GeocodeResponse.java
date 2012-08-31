package com.framework.iginger.geo.model;

import java.util.List;

public class GeocodeResponse {

	private String status;
    public List<GeocodeResult> results;

    public Status getStatus() {
    	return Status.valueOf(status);
	}
    
    public void setStatus(Status status) {
    	this.status = status.name();
    }
    
    public List<GeocodeResult> getResults() {
    	return results;
    }
    
    public void setResults(List<GeocodeResult> results) {
    	this.results = results;
    }
}