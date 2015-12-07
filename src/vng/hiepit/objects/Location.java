package vng.hiepit.objects;

import java.io.Serializable;

public class Location implements Serializable{
	private static final long serialVersionUID = 1L;
	public double latitude;
	public double longitude;
	
	public Location(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	@Override
	public String toString() {
		return "Location {lat:" + latitude + ", lon:" + longitude + "}";
	}
}
