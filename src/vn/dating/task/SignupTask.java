package vn.dating.task;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import vn.dating.activity.RegisterActivity;
import vn.dating.activity.TakePhotoActivity;
import vn.dating.db.UserDAO;
import vn.dating.manager.AccountManager;
import vn.dating.task.bean.UserDetailBean;
import vn.dating.task.form.UserSignUpForm;
import vn.dating.wsclient.WebserviceConstant;
import vn.dating.wsclient.WsClientHelper;
import android.content.Intent;

public class SignupTask extends BaseAsyncTask<String, Void, UserDetailBean> {
	private UserDAO userDAO;
	private UserSignUpForm userSignUpForm;
	
	public SignupTask(RegisterActivity registerActivity, UserSignUpForm userSignUpForm) {
		super(registerActivity);
		this.userSignUpForm = userSignUpForm;
		userDAO = new UserDAO(this.dbHelper.getWritableDatabase());
	}
	
	@Override
	protected UserDetailBean doInBackground(String... params) {
		RestTemplate restTemplate = WsClientHelper.buildRestTemplate();
		UserDetailBean userDetailBean = callSignupWs(userSignUpForm,
				restTemplate);
		userDAO.resetUserDetail(userDetailBean.getUserEmail(), userDetailBean.getPassword());
		return userDetailBean;
    }

	private UserDetailBean callSignupWs(
			UserSignUpForm userSignUpForm,
			RestTemplate restTemplate) {
		String signupWsUrl = WsClientHelper.buildWsUrl(WebserviceConstant.API.SIGNUP);
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Connection", "Close");
		
		HttpEntity request = new HttpEntity(userSignUpForm, headers);
		UserDetailBean userDetailBean = restTemplate.postForObject(signupWsUrl, request,
				UserDetailBean.class);
		
		if (WsClientHelper.isSuccessWsBean(userDetailBean)) {
			userDAO.resetUserDetail(userSignUpForm.getEmail(), userSignUpForm.getPassword());
			AccountManager.getInstance().updateCredential(userSignUpForm.getEmail(), userSignUpForm.getPassword());
		} else {
			return null;
		}
		return userDetailBean;
	}

	@Override
	protected void onPostExecute(UserDetailBean userDetailBean) {
		((RegisterActivity)context).showProgress(false);

		if (WsClientHelper.isSuccessWsBean(userDetailBean)) {
			Intent i = new Intent(context, TakePhotoActivity.class);
			context.startActivity(i);
		} else {
			((RegisterActivity)context).setMailError(userDetailBean.getStatusMessage());
		}
	}

	@Override
	protected void onCancelled() {
		((RegisterActivity)context).showProgress(false);
	}
}
