package vng.hiepit.objects;

import java.util.ArrayList;
import java.util.List;

//Singleton pattern
public class PlacesList {
	public List<Place> places;
	public String next_page_token;

	public PlacesList() {
		places = new ArrayList<Place>();
	}
	
	public boolean canLoadMore() {
		return next_page_token != null;
	}
}
