package vng.hiepit.googleplaceswebservice;

import android.net.Uri;

public class GooglePlacePhotos extends GooglePlacesAPIWebService{
	public static final String URL = "https://maps.googleapis.com/maps/api/place/photo";
	String mPhotoReference;
	int maxWidth = 400;
	int maxHeight;

	public GooglePlacePhotos(String mPhotoReference) {
		this.mPhotoReference = mPhotoReference;
	}

	public int getMaxWidth() {
		return maxWidth;
	}

	public GooglePlacePhotos setMaxWidth(int maxWidth) {
		this.maxWidth = maxWidth;
		return this;
	}

	public int getMaxHeight() {
		return maxHeight;
	}

	public GooglePlacePhotos setMaxHeight(int maxHeight) {
		this.maxHeight = maxHeight;
		return this;
	}

	public String getmPhotoReference() {
		return mPhotoReference;
	}

	public void setmPhotoReference(String mPhotoReference) {
		this.mPhotoReference = mPhotoReference;
	}

	public String genUri() {
		Uri.Builder builder = Uri.parse(URL).buildUpon()
				.appendQueryParameter("photoreference", this.mPhotoReference)
				.appendQueryParameter("key", API_KEY);
		
		if (this.maxHeight > 0) {
			builder.appendQueryParameter("maxheight", Integer.toString(this.maxHeight));
		}
		if (this.maxWidth > 0) {
			builder.appendQueryParameter("maxwidth", Integer.toString(this.maxWidth));
		}

		return builder.build().toString();
	}
}
