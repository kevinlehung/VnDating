package vn.dating.listener;

public interface IAuthenticateResultListener {
	public void onSigninSuccess(String userName, String password);

	public void onSigninFailed();
}
