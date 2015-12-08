package vng.hiepit.storage;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class Storage {
	private final String TAG_LOG = Storage.class.getName();

	private Context mContext;

	public Storage(Context context) {
		this.mContext = context;
	}

	static public Storage with(Context context) {
		return new Storage(context);
	}

	public void saveBitmap(Bitmap bmp, String fileName) {
		FileOutputStream fos = null;
		try {
			fos = this.mContext.openFileOutput(fileName, Context.MODE_PRIVATE);
			bmp.compress(Bitmap.CompressFormat.PNG, 100, fos); // bmp is your
																// Bitmap
																// instance
			// PNG is a lossless format, the compression factor (100) is ignored
			Log.d(TAG_LOG, this.mContext.getFilesDir().toString());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fos != null)
					fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public Bitmap loadBitmap(String fileName) {
		Bitmap bmp = null;
		InputStream is = null;
		try {
			is = this.mContext.openFileInput(fileName);
			bmp = BitmapFactory.decodeStream(is);

		} catch (Exception e) {
			Log.d(TAG_LOG, fileName + " not found!");
		} finally {
			try {
				if (is != null)
					is.close();
			} catch (IOException e) {
				Log.d(TAG_LOG, "", e);
			}
		}
		return bmp;
	}
}
