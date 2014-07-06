package vn.dating.task;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import vn.dating.activity.RegisterActivity;
import vn.dating.activity.TakePhotoActivity;
import vn.dating.listener.ISignupResultListener;
import vn.dating.task.bean.UserDetailBean;
import vn.dating.task.form.UserSignUpForm;
import vn.dating.wsclient.WebserviceConstant;
import vn.dating.wsclient.WsClientHelper;
import android.content.Intent;

public class SignupTask extends BaseAsyncTask<String, Void, UserDetailBean> {
	private UserSignUpForm userSignUpForm;
	private ISignupResultListener signupResultListener;
	
	public SignupTask(RegisterActivity registerActivity, UserSignUpForm userSignUpForm, ISignupResultListener signupResultListener) {
		super(registerActivity);
		this.userSignUpForm = userSignUpForm;
		this.signupResultListener = signupResultListener;
	}
	
	@Override
	protected UserDetailBean doInBackground(String... params) {
		RestTemplate restTemplate = WsClientHelper.buildRestTemplate();
		UserDetailBean userDetailBean = callSignupWs(userSignUpForm,
				restTemplate);
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
			/**
			 * Auto signin
			 */
			signupResultListener.onSignupSuccess(userSignUpForm);
		} else {
			signupResultListener.onSignupFailed();
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
			/**
			 * FIXME: display error dialog box
			 */
			((RegisterActivity)context).setMailError("Invalid email address");
		}
	}

	@Override
	protected void onCancelled() {
		((RegisterActivity)context).showProgress(false);
	}
}
