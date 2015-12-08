package vng.hiepit.PhotoLoader;

import android.content.Context;

// Author: Mai Thanh Hiep
// Created at: 08/12/2015

public class PhotoLoader {
	private Context mContext;

	private PhotoLoader(Context context) {
		this.mContext = context;
	}

	public static PhotoLoader with(Context context) {
		return new PhotoLoader(context);
	}

	public RequestCreator load(String cacheFileName, String url) {
		return new RequestCreator(mContext, cacheFileName, url);
	}
}
