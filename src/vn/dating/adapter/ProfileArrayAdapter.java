package vn.dating.adapter;

import java.util.ArrayList;
import java.util.List;

import vn.dating.R;
import vn.dating.task.bean.ProfileBean;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ProfileArrayAdapter extends ArrayAdapter<ProfileBean> {

	private Context context;

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public ProfileArrayAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId, new ArrayList<ProfileBean>());
		setContext(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.profile_entry, null);
		}
		final ProfileBean profileBean = this.getItem(position);
		if (profileBean != null) {
			TextView fullNameTxt = (TextView) convertView.findViewById(R.id.fullNameTxt);
			fullNameTxt.setText(profileBean.getFullName());
			
			TextView aboutMeTxt = (TextView) convertView.findViewById(R.id.aboutMeTxt);
			aboutMeTxt.setText(profileBean.getAboutMe());
			final ImageView photoImg = (ImageView) convertView.findViewById(R.id.photoImg);
			if (!profileBean.getPhotoUrls().isEmpty()) {
				List<Bitmap> photoBitmaps = profileBean.getPhotoBitmaps();
				if (photoBitmaps != null) {
					photoImg.setImageBitmap(photoBitmaps.get(0));
				}
			} else {
				photoImg.setImageResource(R.drawable.anonymouse_female);
			}
		}
		return convertView;
	}
}
