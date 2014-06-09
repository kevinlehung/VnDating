package vn.dating.task;

import vn.dating.db.UserDAO;
import android.content.Context;

public class SignoutTask extends BaseAsyncTask<String, Void, Void> {
	private UserDAO userDAO;
	
	public SignoutTask(TaskListener<Void> taskListener,
			Context context) {
		super(taskListener, context);
		userDAO = new UserDAO(this.dbHelper.getWritableDatabase());
	}

	@Override
	protected Void doInBackground(String... params) {
			userDAO.removeUserDetail();
			return null;
	}
	
	@Override
	protected void onPostExecute(final Void v) {
		taskListener.onPostExecute(v);
	}

	@Override
	protected void onCancelled() {
		taskListener.onCancelled();
	}
}