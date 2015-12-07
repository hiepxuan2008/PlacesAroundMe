package vng.hiepit.placenearme;

import vng.hiepit.PlaceRecyclerView.PlaceDetailsViewHolder;
import vng.hiepit.googleplaceswebservice.GooglePlaceDetails;
import vng.hiepit.googleplaceswebservice.GooglePlacePhotos;
import vng.hiepit.objects.Place;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

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
		detailsHolder.tvName.setText(place.getmName());
		detailsHolder.tvVicinity.setText(place.getmVicinty());
		detailsHolder.tvDistance.setText(place.getDistance());
		String photoURI = new GooglePlacePhotos(place.getmPhotoReference())
				.genUri();

		Picasso.with(PlaceDetailsActivity.this).load(photoURI)
				.error(R.drawable.placeholder)
				.placeholder(R.drawable.placeholder)
				.into(detailsHolder.mainPhoto);

		// Update details information
		if (place.getPlaceDetails() != null) {
			detailsHolder.tvVicinity.setText(place.getPlaceDetails()
					.getmFormattedAddress());
			detailsHolder.tvPhoneNumber.setText("Phone: " + place.getPlaceDetails()
					.getmLocalPhoneNumber());
			detailsHolder.tvInterPhoneNumber.setText("International phone: " + place.getPlaceDetails()
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
