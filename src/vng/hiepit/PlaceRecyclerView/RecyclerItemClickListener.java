package vng.hiepit.PlaceRecyclerView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class RecyclerItemClickListener implements
		RecyclerView.OnItemTouchListener {
	public static interface OnItemClickListener {
		public void onItemClick(View view, int position);
	}

	private OnItemClickListener mListener;
	private GestureDetector mGestureDetector;

	public RecyclerItemClickListener(Context context,
			final RecyclerView recycleView, OnItemClickListener listener) {
		mListener = listener;
		mGestureDetector = new GestureDetector(context,
				new GestureDetector.SimpleOnGestureListener() {

					@Override
					public boolean onSingleTapUp(MotionEvent e) {
						return true;
					}

					@Override
					public void onLongPress(MotionEvent e) {
						View childView = recycleView.findChildViewUnder(
								e.getX(), e.getY());
						if (childView != null && mListener != null) {
							mListener.onItemClick(childView, recycleView
									.getChildAdapterPosition(childView));
						}
					}

				});
	}

	@Override
	public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
		View childView = view.findChildViewUnder(e.getX(), e.getY());
		if (childView != null && mListener != null
				&& mGestureDetector.onTouchEvent(e)) {
			mListener.onItemClick(childView,
					view.getChildAdapterPosition(childView));
			return true;
		}
		return false;
	}

	@Override
	public void onTouchEvent(RecyclerView view, MotionEvent e) {
	}

	@Override
	public void onRequestDisallowInterceptTouchEvent(boolean arg0) {
	}

}
