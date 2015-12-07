package vng.hiepit.objects;

public class Review {
	String mAuthorName;
	String mAuthorUrl;
	String mRating;
	String mText;
	String mTime;

	public Review(String mAuthorName, String mAuthorUrl, String mRating,
			String mText, String mTime) {
		super();
		this.mAuthorName = mAuthorName;
		this.mAuthorUrl = mAuthorUrl;
		this.mRating = mRating;
		this.mTime = mTime;
		this.mText = mText;
	}
}
