package vn.dating.adapter;

import java.util.ArrayList;
import java.util.List;

import vn.dating.R;
import vn.dating.task.bean.ProfileBean;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.loopj.android.image.SmartImageView;

public class ProfileArrayAdapter extends ArrayAdapter<ProfileBean> {

	private List<ProfileBean> profileBeans = new ArrayList<ProfileBean>();
	private Context context;

	public List<ProfileBean> getProfileBeans() {
		return profileBeans;
	}

	public void setProfileBeans(List<ProfileBean> profileBeans) {
		this.profileBeans = profileBeans;
	}

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
	public int getCount() {
		return getProfileBeans().size();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;

		if (v == null) {
			LayoutInflater inflater = (LayoutInflater) getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.profile_entry, null);
		}

		ProfileBean profileBean = profileBeans.get(position);
		if (profileBean != null) {
			TextView fullNameTxt = (TextView) v.findViewById(R.id.fullNameTxt);
			fullNameTxt.setText(profileBean.getFullName());
			
			TextView aboutMeTxt = (TextView) v.findViewById(R.id.aboutMeTxt);
			aboutMeTxt.setText(profileBean.getAboutMe());
			
			SmartImageView photoImg = (SmartImageView) v.findViewById(R.id.photoImg);
			photoImg.setImageUrl(profileBean.getPhotoUrl());
		}

		return v;
	}

}
