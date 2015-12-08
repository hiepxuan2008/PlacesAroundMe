package vng.hiepit.PhotoLoader;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import vng.hiepit.storage.Storage;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

//Author: Mai Thanh Hiep
//Created at: 08/12/2015

public class RequestCreator {
	final String TAG_LOG = RequestCreator.class.getName();
	String mUrl;
	String mCacheFileName;
	ImageView mTarget;
	Integer mPlaceHolder; // Resource place holder
	Context mContext;

	public RequestCreator(Context context, String cacheFileName, String url) {
		this.mUrl = url;
		this.mCacheFileName = cacheFileName;
		this.mContext = context;
	}

	public void into(ImageView target) {
		this.mTarget = target;

		// Set placeholder
		if (mPlaceHolder != null)
			this.mTarget.setImageResource(mPlaceHolder);

		// Load from cache
		if (!loadFromCache()) {
			new DownloadBitmap().execute(); // If not found in cache -> Download
											// from internet
		}
	}

	public RequestCreator placeholder(int placeHolder) {
		this.mPlaceHolder = placeHolder;
		return this;
	}

	private boolean loadFromCache() {
		Bitmap bmp = Storage.with(mContext).loadBitmap(mCacheFileName);
		if (bmp != null) {
			mTarget.setImageBitmap(bmp);
			Log.d(TAG_LOG, "Load bitmap from cache (Intenal Storage)!");
			return true;
		}
		return false;
	}

	private class DownloadBitmap extends AsyncTask<String, Void, Bitmap> {

		@Override
		protected Bitmap doInBackground(String... params) {
			URL url;
			HttpURLConnection urlConnection = null;
			InputStream is = null;
			Bitmap bmp = null;
			Log.d(TAG_LOG, "Downloading Bitmap from " + mUrl);

			try {
				url = new URL(mUrl);
				urlConnection = (HttpURLConnection) url.openConnection();
				urlConnection.setRequestMethod("GET");
				urlConnection.connect();
				is = urlConnection.getInputStream();
				bmp = BitmapFactory.decodeStream(is);

			} catch (Exception e) {
				Log.e(TAG_LOG, "Failed to download bitmap", e);
			} finally {
				if (urlConnection != null) {
					urlConnection.disconnect();
				}
				if (is != null)
					try {
						is.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
			}
			return bmp;
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			super.onPostExecute(result);
			Storage.with(mContext).saveBitmap(result, mCacheFileName); // Store
			mTarget.setImageBitmap(result);
		}
	}
}
