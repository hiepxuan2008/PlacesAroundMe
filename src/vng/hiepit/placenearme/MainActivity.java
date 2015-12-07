package vng.hiepit.placenearme;

import vng.hiepit.PlaceRecyclerView.OnLoadMoreListener;
import vng.hiepit.PlaceRecyclerView.PlaceRecyclerViewAdapter;
import vng.hiepit.PlaceRecyclerView.RecyclerItemClickListener;
import vng.hiepit.googleplaceswebservice.GooglePlaceSearch;
import vng.hiepit.gps.GPSTracker;
import vng.hiepit.objects.Location;
import vng.hiepit.objects.PlacesList;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends Activity {

	private GPSTracker mGPSTracker;
	private RecyclerView mRecyclerView;
	private LinearLayoutManager mLayoutManager;
	private PlaceRecyclerViewAdapter mPlaceRecyclerViewAdapter;
	static public PlacesList mPlacesList;
	static public Location mLocation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Active the toolbar
		// activateToolbar();

		mRecyclerView = (RecyclerView) findViewById(R.id.list_places);

		mLayoutManager = new LinearLayoutManager(this);
		mRecyclerView.setLayoutManager(mLayoutManager);

		mPlaceRecyclerViewAdapter = new PlaceRecyclerViewAdapter(
				MainActivity.this, new PlacesList(), mRecyclerView);
		mRecyclerView.setAdapter(mPlaceRecyclerViewAdapter);

		mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(
				this, mRecyclerView,
				new RecyclerItemClickListener.OnItemClickListener() {
					@Override
					public void onItemClick(View view, int position) {
						Intent intent = new Intent(MainActivity.this,
								PlaceDetailsActivity.class);
						intent.putExtra("PLACE_INDEX", position);
						startActivity(intent);
					}
				}));

		mPlaceRecyclerViewAdapter
				.setOnLoadMoreListener(new OnLoadMoreListener() {

					@Override
					public void onLoadMore() {
						if (mPlacesList.canLoadMore()) {
							ProgressBar progress = (ProgressBar) findViewById(R.id.loadingProgressBar);
							progress.setVisibility(View.VISIBLE);
							new ProcessGooglePlaceSearch(mPlacesList).execute();
						}
					}
				});

		mGPSTracker = new GPSTracker(MainActivity.this);
		if (mGPSTracker.canGetLocation()) {
			double latitude = mGPSTracker.getLatitude();
			double longitude = mGPSTracker.getLongitude();
			mLocation = new Location(latitude, longitude);
			Log.d(MainActivity.class.getName(), "latitude: " + latitude
					+ "\nlongitude: " + longitude);

			Toast.makeText(
					getBaseContext(),
					(String) "My location is: \nLatitude: " + latitude
							+ "\nLongitude: " + longitude, Toast.LENGTH_SHORT)
					.show();

			// Start
			new ProcessGooglePlaceSearch(new Location(latitude, longitude))
					.execute();
		} else {
			mGPSTracker.showSettingsAlert();
		}
	}

	public class ProcessGooglePlaceSearch extends GooglePlaceSearch {
		public ProcessGooglePlaceSearch(Location location) {
			super(location);
		}

		public ProcessGooglePlaceSearch(PlacesList placesList) {
			super(placesList);
		}

		public void execute() {
			new ProcessJSonData().execute();
		}

		public class ProcessJSonData extends DownloadJsonData {

			@Override
			protected void onPostExecute(String result) {
				super.onPostExecute(result);
				mPlacesList = getPlacesList();
				mPlaceRecyclerViewAdapter.loadNewData(mPlacesList);

				ProgressBar progress = (ProgressBar) findViewById(R.id.loadingProgressBar);
				progress.setVisibility(View.GONE);
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
