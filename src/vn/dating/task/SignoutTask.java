package vn.dating.task;

import vn.dating.activity.MainActivity;
import vn.dating.activity.UserActivity;
import vn.dating.db.UserDAO;
import android.content.Context;
import android.content.Intent;

public class SignoutTask extends BaseAsyncTask<String, Void, Void> {
	private UserDAO userDAO;
	private UserActivity userActivity;
	
	public SignoutTask(UserActivity userActivity) {
		super(userActivity);
		userDAO = new UserDAO(this.dbHelper.getWritableDatabase());
	}

	@Override
	protected Void doInBackground(String... params) {
			userDAO.removeUserDetail();
			return null;
	}
	
	@Override
	protected void onPostExecute(final Void v) {
		Intent i = new Intent(userActivity, MainActivity.class);
		userActivity.startActivity(i);
	}

}