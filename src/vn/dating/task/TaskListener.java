package vn.dating.task;

public interface TaskListener<T> {
	public void onPostExecute(T t);
	public void onCancelled();
}
