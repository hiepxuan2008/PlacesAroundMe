package vng.hiepit.objects;

import java.io.Serializable;

import vng.hiepit.placenearme.MainActivity;
import android.util.Log;

public class Place implements Serializable {
	private static final long serialVersionUID = 1L;

	String mIcon;
	String mName;
	String mPlaceId;
	String mVicinty;
	String mPhotoReference;
	String mRating;
	Location mLocation;
	Location mUserLocation;
	PlaceDetails mPlaceDetails;

	public Place(String mIcon, String mName, String mPlaceId, String mVicinty,
			String mPhotoReference, String mRating, Location mLocation) {
		super();
		this.mIcon = mIcon;
		this.mName = mName;
		this.mPlaceId = mPlaceId;
		this.mVicinty = mVicinty;
		this.mPhotoReference = mPhotoReference;
		this.mRating = mRating;
		this.mLocation = mLocation;
	}

	public String getmIcon() {
		return mIcon;
	}

	public void setmIcon(String mIcon) {
		this.mIcon = mIcon;
	}

	public String getmName() {
		return mName;
	}

	public void setmName(String mName) {
		this.mName = mName;
	}

	public String getmPlaceId() {
		return mPlaceId;
	}

	public void setmPlaceId(String mPlaceId) {
		this.mPlaceId = mPlaceId;
	}

	public String getmVicinty() {
		return mVicinty;
	}

	public void setmVicinty(String mVicinty) {
		this.mVicinty = mVicinty;
	}

	public String getmPhotoReference() {
		return mPhotoReference;
	}

	public void setmPhotoReference(String mPhotoReference) {
		this.mPhotoReference = mPhotoReference;
	}

	public String getmRating() {
		return mRating;
	}

	public void setmRating(String mRating) {
		this.mRating = mRating;
	}

	public Location getmLocation() {
		return mLocation;
	}

	public void setmLocation(Location mLocation) {
		this.mLocation = mLocation;
	}

	public String getDistance() {
		mUserLocation = MainActivity.mLocation;
		
		Log.d("Place", "mLocation: " + mLocation + ", mUserLocation: "
				+ mUserLocation);

		double Radius = 6371000.0;// metter
		double dLat = Math.toRadians(mLocation.latitude
				- mUserLocation.latitude);
		double dLon = Math.toRadians(mLocation.longitude
				- mUserLocation.longitude);
		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
				+ Math.cos(Math.toRadians(mLocation.latitude))
				* Math.cos(Math.toRadians(mUserLocation.latitude))
				* Math.sin(dLon / 2) * Math.sin(dLon / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		
		return String.format("%.2f km", Radius * c / 1000);
	}
	
	public void setPlaceDetails(PlaceDetails placeDetails) {
		this.mPlaceDetails = placeDetails;
	}
	
	public PlaceDetails getPlaceDetails() {
		return mPlaceDetails;
	}

	@Override
	public String toString() {
		return "Place {" + "icon=" + mIcon + ", name=" + mName + ", place_id="
				+ mPlaceId + ", vicinity=" + mVicinty + ", photo="
				+ mPhotoReference + ", rating=" + mRating + ", location="
				+ mLocation.latitude + "," + mLocation.longitude + "}";
	}
}
