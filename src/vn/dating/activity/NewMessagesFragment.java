package vn.dating.activity;

import vn.dating.R;
import vn.dating.R.id;
import vn.dating.R.layout;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A placeholder fragment containing a simple view.
 */
public class NewMessagesFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static NewMessagesFragment newInstance(int sectionNumber) {
    	NewMessagesFragment fragment = new NewMessagesFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public NewMessagesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_find_profiles, container, false);
        TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        textView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((UserActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }
}
