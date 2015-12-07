package vng.hiepit.googleplaceswebservice;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import vng.hiepit.objects.Photo;
import vng.hiepit.objects.Place;
import vng.hiepit.objects.PlaceDetails;
import vng.hiepit.objects.Review;
import android.net.Uri;
import android.util.Log;

public class GooglePlaceDetails extends GooglePlacesAPIWebService {
	private static final String URL = "https://maps.googleapis.com/maps/api/place/details/json?";
	private final String TAG_LOG = GooglePlaceDetails.class.getName();

	Place mPlace;

	public GooglePlaceDetails(Place place) {
		this.mPlace = place;
		updateURI();
	}

	private void procesJsonData() {
		String response = getResponse();
		Log.d(TAG_LOG, "ProcessJsonData");
		if (response != null) {
			Log.d(TAG_LOG, response);
		} else {
			Log.d(TAG_LOG, "No response");
		}
		
		try {
			PlaceDetails placeDetails = new PlaceDetails();
			
			JSONObject jsonData = new JSONObject(response);
			JSONObject resultObject = jsonData.getJSONObject("result");
			
			String formatted_address = null;
			if (resultObject.has("formatted_address"))
				formatted_address = resultObject.getString("formatted_address");
			
			String formatted_phone_number = null;
			if (resultObject.has("formatted_phone_number"))
				formatted_phone_number = resultObject.getString("formatted_phone_number");
		
			String international_phone_number = null;
			if (resultObject.has("international_phone_number"))
				international_phone_number = resultObject.getString("international_phone_number");
			
			placeDetails.setmFormattedAddress(formatted_address);
			placeDetails.setmLocalPhoneNumber(formatted_phone_number);
			placeDetails.setmInternationPhoneNumber(international_phone_number);
			
			//Parse Photos
			try {
				JSONArray photosArray = resultObject.getJSONArray("photos");
				List<Photo> photosList = new ArrayList<Photo>();
				for (int i = 0; i < photosArray.length(); ++i) {
					JSONObject photoObject = photosArray.getJSONObject(i);
					String photoReference = photoObject
							.getString("photo_reference");
					int width = photoObject.getInt("width");
					int height = photoObject.getInt("height");
					Photo photo = new Photo(photoReference, width, height);
					photosList.add(photo);
				}
				placeDetails.setmPhotosList(photosList);
			} catch (Exception e) {
				Log.d(TAG_LOG, "No photos");
			}
			
			//Parse Reviews
			try {
				JSONArray reviewsArray = resultObject.getJSONArray("reviews");
				List<Review> reviewsList = new ArrayList<Review>();
				for (int i = 0; i < reviewsArray.length(); ++i) {
					JSONObject reviewObject = reviewsArray.getJSONObject(i);
					String author_name = reviewObject.getString("author_name");
					String author_url = null;
					try {
						author_url = reviewObject.getString("author_url");
					} catch (Exception e) {
						Log.d(TAG_LOG, "No author_url", e);
					}
					String rating = reviewObject.getString("rating");
					String text = reviewObject.getString("text");
					String time = reviewObject.getString("time");
					Review review = new Review(author_name, author_url, rating,
							text, time);
					reviewsList.add(review);
				}
				placeDetails.setmReviewsList(reviewsList);
			} catch (Exception e) {
				Log.d(TAG_LOG, "No reviews");
			}
			mPlace.setPlaceDetails(placeDetails);
			
		} catch (Exception e) {
			Log.e(TAG_LOG, "error", e);
		}
		Log.d(TAG_LOG, "Finish processJsonData successfully!");
	}

	public void updateURI() {
		Uri.Builder builder = Uri.parse(URL).buildUpon();
		builder.appendQueryParameter("placeid", this.mPlace.getmPlaceId());
		builder.appendQueryParameter("key", API_KEY);

		this.mStrUri = builder.build().toString();
	}

	protected class DownloadJsonData extends DownloadRowData {

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			procesJsonData();
		}
	}
}
