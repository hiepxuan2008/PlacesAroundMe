package vng.hiepit.googleplaceswebservice;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import vng.hiepit.objects.Location;
import vng.hiepit.objects.Place;
import vng.hiepit.objects.PlacesList;
import android.net.Uri;
import android.util.Log;


public class GooglePlaceSearch extends GooglePlacesAPIWebService {
	private static final String URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?";
	private final String TAG_LOG = GooglePlaceSearch.class.getName();
	
	Location mLocation;
	int mRadius;
	String mTypes;
	String mNextPageToken;
	String mRankBy;
	String mName;
	
	PlacesList mPlacesList;
	public GooglePlaceSearchType searchType;
	
	public GooglePlaceSearch(Location location) {
		super();
		this.mLocation = location;
		this.mRadius = 500;
		searchType = GooglePlaceSearchType.NEW;
		
		this.mPlacesList = new PlacesList();
		updateURI();
	}
	
	public GooglePlaceSearch(PlacesList placesList) {
		super();
		this.mNextPageToken = placesList.next_page_token;
		this.mPlacesList = placesList;
		
		searchType = GooglePlaceSearchType.UPDATE;
		updateURI();
	}
	
	public PlacesList getPlacesList() {
		return mPlacesList;
	}

	public void updateURI() {
		Uri.Builder builder = Uri.parse(URL).buildUpon();
		if (mLocation != null) {
			builder.appendQueryParameter("location", Double.toString(mLocation.latitude) + "," + Double.toString(mLocation.longitude));
			builder.appendQueryParameter("radius", Integer.toString(mRadius));
		} else if (mNextPageToken != null) {
			builder.appendQueryParameter("pagetoken", this.mNextPageToken);
		}
		builder.appendQueryParameter("key", API_KEY);
		
		this.mStrUri = builder.build().toString();
	}
	
	public void execute() {
		
	}
	
	private void processParsingJson() {
		String response = getResponse();
		//Log.v(TAG_LOG, response);
		Log.d(TAG_LOG, "ParsingJson data!");
		
		try {
			JSONObject jsonData = new JSONObject(response);
			
			JSONArray resultsArray = jsonData.getJSONArray("results");
			for (int i = 0; i < resultsArray.length(); ++i) {
				JSONObject jsonPlace = resultsArray.getJSONObject(i);
				
				String icon = jsonPlace.getString("icon");
				String name = jsonPlace.getString("name");
				String place_id = jsonPlace.getString("place_id");
				String vicinity = jsonPlace.getString("vicinity");
				
				String rating = "0";
				try {
					rating = jsonPlace.getString("rating");
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				double geoLat, geoLgn;
				{
					JSONObject jsonGeometry = jsonPlace.getJSONObject("geometry");
					JSONObject jsonLocation = jsonGeometry.getJSONObject("location");
					geoLat = jsonLocation.getDouble("lat");
					geoLgn = jsonLocation.getDouble("lng");
				}
				Location location = new Location(geoLat, geoLgn);
				
				String photo_reference = null;
				{
					try {
						JSONArray photosArray = jsonPlace.getJSONArray("photos");
						JSONObject jsonPhoto = photosArray.getJSONObject(0);
						photo_reference = jsonPhoto.getString("photo_reference");
					} catch (Exception e) {
						
					}
				}
				
				Place place = new Place(icon, name, place_id, vicinity, photo_reference, rating, location);
				this.mPlacesList.places.add(place);
			}
			
			try {
				//next_page_token
				String next_page_token = jsonData.getString("next_page_token");
				this.mPlacesList.next_page_token = next_page_token;
				
			} catch (Exception e) {
				this.mPlacesList.next_page_token = null; //End of token
			}
			
			for (Place place: mPlacesList.places) {
				Log.v(TAG_LOG, place.toString());
			}
			
		} catch (JSONException e) {
			Log.d(TAG_LOG, "error", e);
		}
	}
	
	protected class DownloadJsonData extends DownloadRowData {

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			processParsingJson();
		}
		
	}
}
