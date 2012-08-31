package com.framework.iginger.geo.model;

import java.util.ArrayList;
import java.util.List;

public class AddressComponent {
    public String long_name;
    public String short_name;
    private List<String> types;

    public String getLong_name() {
        return long_name;
    }

    public void setLong_name(String long_name) {
        this.long_name = long_name;
    }

    public String getShort_name() {
        return short_name;
    }

    public void setShort_name(String short_name) {
        this.short_name = short_name;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    
    public List<AddressComponentType> getComponentTypes() {
    	List<AddressComponentType> componentTypeList = new ArrayList<AddressComponentType>();
    	
    	if (types != null && !types.isEmpty()) {
    		for (String type : types) {
    			componentTypeList.add(AddressComponentType.fromValue(type));
    		}
    	}
    	
    	return componentTypeList;
    }
}