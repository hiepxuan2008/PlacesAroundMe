package vng.hiepit.PlaceRecyclerView;

import vng.hiepit.googleplaceswebservice.GooglePlacePhotos;
import vng.hiepit.objects.Place;
import vng.hiepit.objects.PlacesList;
import vng.hiepit.placenearme.R;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.squareup.picasso.Picasso;

@SuppressWarnings("rawtypes")
public class PlaceRecyclerViewAdapter extends RecyclerView.Adapter {
	private final int VIEW_ITEM = 1;
	private final int VIEW_PROG = 0;

	private PlacesList mPlacesList;
	private Context mContext;
	private RecyclerView mRecyclerView;
	
	
	private OnLoadMoreListener onLoadMoreListener;
	private boolean isLoading = false;
	private int visibleThreshold = 1;

	public PlaceRecyclerViewAdapter(Context context, PlacesList placesList, RecyclerView recyclerView) {
		this.mContext = context;
		this.mPlacesList = placesList;
		this.mRecyclerView = recyclerView;
		
		//Add event ScrollListener to check end of list
		mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
			@Override
			public void onScrolled(RecyclerView recyclerView,
								   int dx, int dy) {
				super.onScrolled(recyclerView, dx, dy);

				final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView
						.getLayoutManager();
				
				int totalItemCount = linearLayoutManager.getItemCount();
				int lastVisibleItem = linearLayoutManager
						.findLastVisibleItemPosition();
				
				if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
					// End has been reached
					// Do something
					Log.d("MainActivity", "Reach to end!");
					onLoadMoreListener.onLoadMore();
					isLoading = true;
				}
			}
		});
	}

	@Override
	public int getItemCount() {
		return mPlacesList == null ? 0 : mPlacesList.places.size();
	}

	@Override
	public int getItemViewType(int position) {
		return mPlacesList.places.get(position) != null ? VIEW_ITEM : VIEW_PROG;
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int i) {

		if (holder instanceof PlaceViewHolder) {
			PlaceViewHolder placeViewHolder = (PlaceViewHolder) holder;
			Place placeItem = mPlacesList.places.get(i);

			// If this place supports thumbnail photo -> Load
			if (placeItem.getmPhotoReference() != null) {
				String photoURI = new GooglePlacePhotos(
						placeItem.getmPhotoReference()).genUri();

				Picasso.with(mContext).load(photoURI)
						.error(R.drawable.placeholder)
						.placeholder(R.drawable.placeholder)
						.into(placeViewHolder.thumbnail);
			} else {
				placeViewHolder.thumbnail.setImageResource(R.drawable.placeholder);
			}
			// Title
			placeViewHolder.title.setText(placeItem.getmName());
			// Address
			placeViewHolder.address.setText(placeItem.getmVicinty());
			// Distance
			placeViewHolder.distance.setText(placeItem.getDistance());

			Log.d("PlaceRecyclerViewAdapter", "Process " + i);
		} else { // Show Progressbar
			((ProgressViewHolder) holder).progressBar.setIndeterminate(true);
		}
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup,
			int viewType) {
		RecyclerView.ViewHolder vh;
		if (viewType == VIEW_ITEM) {
			View view = LayoutInflater.from(viewGroup.getContext()).inflate(
					R.layout.place_item, viewGroup, false);
			PlaceViewHolder placeImageViewHolder = new PlaceViewHolder(view);

			return placeImageViewHolder;
		} else {
			View v = LayoutInflater.from(viewGroup.getContext()).inflate(
					R.layout.progressbar_item, viewGroup, false);

			vh = new ProgressViewHolder(v);
		}
		return vh;
	}

	public void loadNewData(PlacesList placesList) {
		mPlacesList = placesList;
		isLoading = false;
		notifyDataSetChanged();
	}

	public Place getPlace(int position) {
		return mPlacesList == null ? null : mPlacesList.places.get(position);
	}
	
	public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
		this.onLoadMoreListener = onLoadMoreListener;
	}

	public static class ProgressViewHolder extends RecyclerView.ViewHolder {
		public ProgressBar progressBar;

		public ProgressViewHolder(View v) {
			super(v);
			progressBar = (ProgressBar) v.findViewById(R.id.loadingProgressBar);
		}
	}
}
