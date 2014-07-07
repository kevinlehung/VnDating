package vn.dating.task;

import vn.dating.activity.MainActivity;
import vn.dating.activity.UserActivity;
import vn.dating.listener.IAuthenticateResultListener;
import vn.dating.manager.AccountManager;
import android.content.Intent;

public class SignoutTask extends BaseAsyncTask<String, Void, Void> {
	private IAuthenticateResultListener authenticateResultListener;
	public SignoutTask(UserActivity userActivity, IAuthenticateResultListener authenticateResultListener) {
		super(userActivity);
		this.authenticateResultListener = authenticateResultListener;
	}

	@Override
	protected Void doInBackground(String... params) {
		AccountManager.getInstance().resetAll();
		return null;
	}
	
	@Override
	protected void onPostExecute(final Void v) {
		Intent i = new Intent(context, MainActivity.class);
		context.startActivity(i);
	}

}