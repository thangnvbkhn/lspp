package nvt.com.adapter;

import java.util.ArrayList;
import java.util.Collection;

import nvt.com.object.Duong;
import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

public class PlacesAutoCompleteAdapter extends ArrayAdapter<Duong> implements Filterable {

	private ArrayList<Duong> items;
	private ArrayList<Duong> mData;
	private ArrayList<Duong> suggessions;

	@SuppressWarnings("unchecked")
	public PlacesAutoCompleteAdapter(Context context, int textViewResourceId,
			ArrayList<Duong> items) {
		super(context, textViewResourceId, items);
		this.mData = items;
		this.items = (ArrayList<Duong>) mData.clone();
		this.suggessions = new ArrayList<Duong>();
	}

	@Override
	public int getCount() {
		return suggessions.size();
	}

	@Override
	public void add(Duong object) {
		items.add(object);
	}
	
	@Override
	public void addAll(Collection<? extends Duong> collection) {
		items.addAll(collection);
	}
	
	@Override
	public Duong getItem(int position) {
		return suggessions.get(position);
	}		
	
	@Override
	public Filter getFilter() {
		Filter filter = new Filter() {
			@Override
			protected FilterResults performFiltering(CharSequence constraint) {
				FilterResults filterResults = new FilterResults();
				if (constraint!=null && constraint.toString().length()>0) {
					synchronized (this) {
						suggessions.clear();
						for (Duong s : items) {
							if (s.getName_khong_dau().toLowerCase().contains(constraint.toString().toLowerCase())) {
								suggessions.add(s);
							}
						}
						filterResults.values = suggessions;
						filterResults.count = suggessions.size();
						return filterResults;
					}
				} else {
					return new FilterResults();
				}
			}

			@SuppressWarnings("unchecked")
			@Override
			protected void publishResults(CharSequence constraint,FilterResults results) {
				if (results != null && results.count > 0) {
					mData = (ArrayList<Duong>) results.values;
					notifyDataSetChanged();
				} else {
					notifyDataSetInvalidated();
				}
			}
		};
		return filter;
	}
}
