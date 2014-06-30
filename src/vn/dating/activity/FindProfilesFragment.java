package vn.dating.activity;

import vn.dating.R;
import vn.dating.adapter.ProfileArrayAdapter;
import vn.dating.task.FindProfilesTask;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * A placeholder fragment containing a simple view.
 */
public class FindProfilesFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    ProfileArrayAdapter profileAdapter;
    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static FindProfilesFragment newInstance(int sectionNumber) {
    	FindProfilesFragment fragment = new FindProfilesFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public FindProfilesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_find_profiles, container, false);
        setProfileListAdapter(rootView);
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((UserActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }
    
    private void setProfileListAdapter(View rootView) {
		// Build adapter with contact entries
    	this.profileAdapter = new ProfileArrayAdapter(getActivity(), R.id.profileEntry);
		ListView profileList = (ListView) rootView.findViewById(R.id.profileList);
		profileList.setAdapter(profileAdapter);
		
		FindProfilesTask task = new FindProfilesTask(this.profileAdapter);
		task.execute();
    }
}
