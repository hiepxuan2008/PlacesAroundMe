package vng.hiepit.PlaceRecyclerView;

import vng.hiepit.placenearme.R;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class PlaceViewHolder extends RecyclerView.ViewHolder{
	ImageView thumbnail;
	TextView title;
	TextView address;
	TextView distance;
	
	public PlaceViewHolder(View itemView) {
		super(itemView);
		this.thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
		this.title = (TextView) itemView.findViewById(R.id.title);
		this.address = (TextView) itemView.findViewById(R.id.address);
		this.distance = (TextView) itemView.findViewById(R.id.distance);
	}
}