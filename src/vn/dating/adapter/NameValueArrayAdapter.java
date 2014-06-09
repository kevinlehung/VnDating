package vn.dating.adapter;

import java.util.List;

import android.content.Context;
import android.widget.ArrayAdapter;

public class NameValueArrayAdapter extends ArrayAdapter<NameValueItem> {
	public NameValueArrayAdapter(Context context, int textViewResourceId, List<NameValueItem> values) {
		super(context, textViewResourceId, values);
	}
}
