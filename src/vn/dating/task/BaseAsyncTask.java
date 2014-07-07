package vn.dating.task;

import android.content.Context;
import android.os.AsyncTask;

public abstract class BaseAsyncTask<Param, Progress, Result>  extends AsyncTask<Param, Progress, Result> {
	protected Context context;
	
	public BaseAsyncTask(Context context) {
		this.context = context;
	}
}
