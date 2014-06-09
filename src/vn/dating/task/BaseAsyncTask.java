package vn.dating.task;

import vn.dating.db.DatingDBHelper;
import android.content.Context;
import android.os.AsyncTask;

public abstract class BaseAsyncTask<Param, Progress, Result>  extends AsyncTask<Param, Progress, Result> {
	protected TaskListener<Result> taskListener;
	protected Context context;
	protected DatingDBHelper dbHelper;
	
	public BaseAsyncTask(TaskListener<Result> taskListener, Context context) {
		this.taskListener = taskListener;
		this.context = context;
		dbHelper = new DatingDBHelper(context);
	}
}
