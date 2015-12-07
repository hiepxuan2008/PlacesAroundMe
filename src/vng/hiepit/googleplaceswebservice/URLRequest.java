package vng.hiepit.googleplaceswebservice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.os.AsyncTask;
import android.util.Log;

public class URLRequest {
	private final String TAG_LOG = URLRequest.class.getName();
	protected String mStrUri;
	private String mStrResponse;

	public String getResponse() {
		return mStrResponse;
	}

	protected class DownloadRowData extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			HttpURLConnection urlConnection = null;
			BufferedReader reader = null;
			Log.d(TAG_LOG, mStrUri);

			try {
				URL url = new URL(mStrUri);

				urlConnection = (HttpURLConnection) url.openConnection();
				urlConnection.setRequestMethod("GET");
				urlConnection.connect();

				InputStream is = urlConnection.getInputStream();
				if (is == null)
					return null;

				StringBuffer buffer = new StringBuffer();
				reader = new BufferedReader(new InputStreamReader(is));

				String line;
				while ((line = reader.readLine()) != null) {
					buffer.append(line + "\n");
				}

				return buffer.toString();

			} catch (IOException e) {
				Log.d(TAG_LOG, "Error", e);
			} finally {
				if (urlConnection != null) {
					urlConnection.disconnect();
				}
				if (reader != null)
					try {
						reader.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}

			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			mStrResponse = result;
		}
	}
}
