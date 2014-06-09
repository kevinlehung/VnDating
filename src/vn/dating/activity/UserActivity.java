package vn.dating.activity;

import vn.dating.R;
import vn.dating.task.SignoutTask;
import vn.dating.task.TaskListener;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;

public class UserActivity extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();
        Fragment fragment = null;
        switch (position) {
	        case NavigationDrawerFragment.FIND_PROFILES_POSITION:
	        	fragment = FindProfilesFragment.newInstance(position + 1);
	            break;
	        case NavigationDrawerFragment.MATCH_CRITERIA_POSITION:
	        	fragment = MatchCriteriaFragment.newInstance(position + 1);
	            break;
	        case NavigationDrawerFragment.FAVOURITE_LIST_POSITION:
	        	fragment = FavouriteListFragment.newInstance(position + 1);
	            break;
	        case NavigationDrawerFragment.NEW_MESSAGES_POSITION:
	        	fragment = NewMessagesFragment.newInstance(position + 1);
	            break;
	        case NavigationDrawerFragment.ACCOUNT_SETTING_POSITION:
	        	fragment = AccountSettingFragment.newInstance(position + 1);
	        	break;
	        case NavigationDrawerFragment.SIGNOUT_POSITION:
	        	SignoutTask signoutTask = new SignoutTask(new TaskListener<Void>() {

					@Override
					public void onPostExecute(Void t) {
						Intent i = new Intent(UserActivity.this, MainActivity.class);
			            startActivity(i);
					}

					@Override
					public void onCancelled() {
						// TODO Auto-generated method stub
						
					}
	        		
				}, this);
	        	signoutTask.execute();
	        	break;
	    }
        if (fragment != null) {
        	fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
        }
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case NavigationDrawerFragment.FIND_PROFILES_POSITION:
                mTitle = getString(R.string.title_activity_find_profiles);
                break;
            case NavigationDrawerFragment.MATCH_CRITERIA_POSITION:
                mTitle = getString(R.string.title_match_criteria);
                break;
            case NavigationDrawerFragment.FAVOURITE_LIST_POSITION:
                mTitle = getString(R.string.title_favourite_list);
                break;
            case NavigationDrawerFragment.NEW_MESSAGES_POSITION:
                mTitle = getString(R.string.title_new_messages);
                break;
            case NavigationDrawerFragment.ACCOUNT_SETTING_POSITION:
            	mTitle = getString(R.string.title_account_setting);
            case NavigationDrawerFragment.SIGNOUT_POSITION:
            	mTitle = getString(R.string.title_signout);
            	break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.find_profiles, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
		case R.id.action_settings:
			break;
		}
        return super.onOptionsItemSelected(item);
    }
}
