package com.telecom.mongo.entity;

import com.telecom.mongo.base.BaseEntityForMongo;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "t_merchant")
public class Merchant extends BaseEntityForMongo {

	private static final long serialVersionUID = -8551125289368360861L;

	@Field("name")
	private String name;
	@Field("address")
	private String address;
	@Field("location")
	private Location location;
	@Field("distance")
	private Double distance;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

}
