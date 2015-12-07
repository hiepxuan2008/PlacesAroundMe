package vng.hiepit.gps;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

public class GPSTracker extends Service implements LocationListener {
	private final String TAG = GPSTracker.class.getName();
	private final Context context;
	boolean isGPSEnabled = false;
	boolean isNetworkEnabled = false;
	boolean canGetLocation = false;
	Location location;

	double latitude;
	double longitude;

	private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10
																	// metters
	private static final long MIN_TIME_BETWEEN_UPDATES = 1000 * 60; // update
																	// location
																	// per 1
																	// minutes

	protected LocationManager locationManager;
	private String providerInfo;

	public GPSTracker(Context context) {
		this.context = context;
		getLocation();
	}

	public void getLocation() {
		try {
			locationManager = (LocationManager) context
					.getSystemService(Context.LOCATION_SERVICE);
			isGPSEnabled = locationManager
					.isProviderEnabled(LocationManager.GPS_PROVIDER); // get
																		// location
																		// from
																		// GPS
			isNetworkEnabled = locationManager
					.isProviderEnabled(LocationManager.NETWORK_PROVIDER); // get
																			// location
																			// from
																			// Internet
																			// info

			if (!isGPSEnabled && !isNetworkEnabled) {
				this.canGetLocation = false;
			} else {
				this.canGetLocation = true;

				if (isGPSEnabled) {
					Log.d(TAG, "Application use GPS Service");
					providerInfo = LocationManager.GPS_PROVIDER;
				} else if (isNetworkEnabled) {
					Log.d(TAG,
							"Application use Network State to get GPS coordinates");
					providerInfo = LocationManager.NETWORK_PROVIDER;
				}

				// Application can use GPS or Network Provider
				if (!providerInfo.isEmpty()) {
					locationManager.requestLocationUpdates(providerInfo,
							MIN_TIME_BETWEEN_UPDATES,
							MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

					if (locationManager != null) {
						location = locationManager
								.getLastKnownLocation(providerInfo);
						if (location != null) {
							latitude = location.getLatitude();
							longitude = location.getLongitude();
						}
					}
				}
			}
		} catch (Exception e) {
			Log.e(TAG, "Impossible to connect to LocationManager", e);
		}
	}

	public void stopUsingGPS() {
		if (locationManager != null) {
			locationManager.removeUpdates(this);
		}
	}

	public double getLatitude() {
		return this.latitude;
	}

	public double getLongitude() {
		return this.longitude;
	}

	public boolean canGetLocation() {
		return this.canGetLocation;
	}

	public void showSettingsAlert() {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

		alertDialog.setTitle("GPS Settings");
		alertDialog
				.setMessage("GPS is not enabled. Do you want to go to settings menu?");
		alertDialog.setPositiveButton("Settings",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent(
								Settings.ACTION_LOCATION_SOURCE_SETTINGS);
						context.startActivity(intent);
					}
				});

		alertDialog.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
		alertDialog.show();

		// LocationSettingsRequest.Builder builder = new LocationSettingsRequest
		// Update later
	}

	@Override
	public void onLocationChanged(Location location) {
		this.latitude = location.getLatitude();
		this.longitude = location.getLongitude();
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
}