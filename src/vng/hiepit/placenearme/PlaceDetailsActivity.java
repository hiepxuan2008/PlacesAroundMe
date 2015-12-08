package vng.hiepit.placenearme;

import vng.hiepit.PhotoLoader.PhotoLoader;
import vng.hiepit.PlaceRecyclerView.PlaceDetailsViewHolder;
import vng.hiepit.googleplaceswebservice.GooglePlaceDetails;
import vng.hiepit.googleplaceswebservice.GooglePlacePhotos;
import vng.hiepit.objects.Place;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class PlaceDetailsActivity extends Activity {

	Place place;
	PlaceDetailsViewHolder detailsHolder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_place_details);

		Intent intent = getIntent();
		int position = (int) intent.getIntExtra("PLACE_INDEX", -1);
		if (position == -1)
			return;

		detailsHolder = new PlaceDetailsViewHolder();
		detailsHolder.mainPhoto = (ImageView) findViewById(R.id.place_image);
		detailsHolder.tvName = (TextView) findViewById(R.id.place_name);
		detailsHolder.tvVicinity = (TextView) findViewById(R.id.place_address);
		detailsHolder.tvPhoneNumber = (TextView) findViewById(R.id.place_phonenumber);
		detailsHolder.tvInterPhoneNumber = (TextView) findViewById(R.id.place_inter_phonenumber);
		detailsHolder.tvDistance = (TextView) findViewById(R.id.place_distance);

		place = MainActivity.mPlacesList.places.get(position);
		if (place == null)
			return;

		// Update temperate before update detail
		updatePlaceDetails(place, detailsHolder);
		// Get details place information if haven't get yet
		if (place.getPlaceDetails() == null)
			new ProcessPlaceDetails(place).execute();
	}

	public class ProcessPlaceDetails extends GooglePlaceDetails {
		public ProcessPlaceDetails(Place place) {
			super(place);
			// TODO Auto-generated constructor stub
		}

		public void execute() {
			new ProcessJsonData().execute();
		}

		protected class ProcessJsonData extends DownloadJsonData {

			@Override
			protected void onPostExecute(String result) {
				super.onPostExecute(result);
				updatePlaceDetails(place, detailsHolder);
			}
		}
	}

	public void updatePlaceDetails(Place place,
			PlaceDetailsViewHolder detailsHolder) {
		// Name
		detailsHolder.tvName.setText(place.getmName());
		// Short Address Temperary
		detailsHolder.tvVicinity.setText(place.getmVicinty());
		// Distance
		detailsHolder.tvDistance.setText(place.getDistance());

		// Show photo
		if (place.getmPhotoReference() != null) {
			int maxWidth = 400;
			String photoURI = new GooglePlacePhotos(place.getmPhotoReference())
					.setMaxWidth(maxWidth).genUri();
			String cacheFileName = place.getmPlaceId() + "_" + maxWidth;

			Log.d("PlaceDetailsActivity", photoURI);
			PhotoLoader.with(PlaceDetailsActivity.this)
					.load(cacheFileName, photoURI)
					.placeholder(R.drawable.placeholder)
					.into(detailsHolder.mainPhoto);
		}

		// Update more details information
		if (place.getPlaceDetails() != null) {
			// Full address
			if (place.getPlaceDetails().getmFormattedAddress() != null)
				detailsHolder.tvVicinity.setText(place.getPlaceDetails()
						.getmFormattedAddress());

			// Phone number
			if (place.getPlaceDetails().getmLocalPhoneNumber() != null)
				detailsHolder.tvPhoneNumber.setText("Phone: "
						+ place.getPlaceDetails().getmLocalPhoneNumber());

			// International phone number
			if (place.getPlaceDetails().getmInternationPhoneNumber() != null)
				detailsHolder.tvInterPhoneNumber
						.setText("International phone: "
								+ place.getPlaceDetails()
										.getmInternationPhoneNumber());
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_place_details, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
