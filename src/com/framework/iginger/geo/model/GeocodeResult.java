package com.framework.iginger.geo.model;

import java.io.Serializable;
import java.util.List;

public class GeocodeResult implements Serializable {
    public List<String> types;
    public String formatted_address;
    public List<AddressComponent> address_components;
    public Geometry geometry;
}