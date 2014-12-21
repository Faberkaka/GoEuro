package com.goeuro.bean;

import java.math.BigInteger;

public class TripDetails {

	private BigInteger _id;
	private String key;
	private String name;
	private String fullName;
	private String iata_airport_code;
	private String type;
	private String country;
	private GeoPosition geo_position;
	private Long locationId;
	private Boolean inEurope;
	private String countryCode;
	private Boolean coreCountry;
	private String distance;
	
	public BigInteger get_id() {
		return _id;
	}
	public void set_id(BigInteger _id) {
		this._id = _id;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getIata_airport_code() {
		return iata_airport_code;
	}
	public void setIata_airport_code(String iata_airport_code) {
		this.iata_airport_code = iata_airport_code;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public GeoPosition getGeo_position() {
		return geo_position;
	}
	public void setGeo_position(GeoPosition geo_position) {
		this.geo_position = geo_position;
	}
	public Long getLocationId() {
		return locationId;
	}
	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
	public Boolean getInEurope() {
		return inEurope;
	}
	public void setInEurope(Boolean inEurope) {
		this.inEurope = inEurope;
	}
	public Boolean getCoreCountry() {
		return coreCountry;
	}
	public void setCoreCountry(Boolean coreCountry) {
		this.coreCountry = coreCountry;
	}

}
