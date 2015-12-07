package vng.hiepit.objects;

import java.util.List;

public class PlaceDetails {
	String mLocalPhoneNumber;
	String mInternationPhoneNumber;
	String mFormattedAddress;
	
	List<Review> mReviewsList;
	List<Photo> mPhotosList;
	
	
	
	public String getmLocalPhoneNumber() {
		return mLocalPhoneNumber;
	}
	public void setmLocalPhoneNumber(String mLocalPhoneNumber) {
		this.mLocalPhoneNumber = mLocalPhoneNumber;
	}
	public String getmInternationPhoneNumber() {
		return mInternationPhoneNumber;
	}
	public void setmInternationPhoneNumber(String mInternationPhoneNumber) {
		this.mInternationPhoneNumber = mInternationPhoneNumber;
	}
	public String getmFormattedAddress() {
		return mFormattedAddress;
	}
	public void setmFormattedAddress(String mFormattedAddress) {
		this.mFormattedAddress = mFormattedAddress;
	}
	public List<Review> getmReviewsList() {
		return mReviewsList;
	}
	public void setmReviewsList(List<Review> mReviewsList) {
		this.mReviewsList = mReviewsList;
	}
	public List<Photo> getmPhotosList() {
		return mPhotosList;
	}
	public void setmPhotosList(List<Photo> mPhotosList) {
		this.mPhotosList = mPhotosList;
	}
}
