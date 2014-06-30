package vn.dating.task;

import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

import vn.dating.activity.LoginActivity;
import vn.dating.activity.UserActivity;
import vn.dating.db.UserDAO;
import vn.dating.manager.AccountManager;
import vn.dating.task.bean.UserDetailBean;
import vn.dating.task.form.UserSigninForm;
import vn.dating.wsclient.WebserviceConstant;
import vn.dating.wsclient.WsClientHelper;
import android.content.Intent;

public class SigninTask extends BaseAsyncTask<String, Void, UserDetailBean>  {
	private UserDAO userDAO;
	public SigninTask(LoginActivity loginActivity) {
		super(loginActivity);
		userDAO = new UserDAO(this.dbHelper.getWritableDatabase());
	}

	@Override
	protected UserDetailBean doInBackground(String... params) {
		UserSigninForm userSigninForm = buildUserSigninForm(params);
		
		RestTemplate restTemplate = WsClientHelper.buildRestTemplate();
		UserDetailBean userDetailBean = callSigninWs(userSigninForm,
				restTemplate);
		if (WsClientHelper.isSuccessWsBean(userDetailBean)) {
			userDAO.resetUserDetail(userSigninForm.getEmail(), userSigninForm.getPassword());
			AccountManager.getInstance().updateCredential(userDetailBean.getUserEmail(), userDetailBean.getPassword());
		} else {
			return null;
		}
		return userDetailBean;
	}

	
	private UserDetailBean callSigninWs(
			UserSigninForm userSigninForm,
			RestTemplate restTemplate) {
		String signinWsUrl = WsClientHelper.buildWsUrl(WebserviceConstant.API.SIGNIN);
		HttpEntity request = WsClientHelper.buildRequestObj(userSigninForm);
		UserDetailBean userDetailBean = restTemplate.postForObject(signinWsUrl, request,
				UserDetailBean.class);
		return userDetailBean;
	}

	private UserSigninForm buildUserSigninForm(String params[]) {
		UserSigninForm userSigninForm = new UserSigninForm(params[0], params[1]);
		return userSigninForm;
	}
	
	@Override
	protected void onPostExecute(final UserDetailBean userDetailBean) {
		((LoginActivity)context).showProgress(false);

		if (userDetailBean != null) {
			Intent i = new Intent(context, UserActivity.class);
			context.startActivity(i);
		}
	}

	@Override
	protected void onCancelled() {
		((LoginActivity)context).showProgress(false);
	}
}
