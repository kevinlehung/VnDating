package vn.dating.task;

import java.util.HashMap;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import vn.dating.db.UserDAO;
import vn.dating.task.bean.UserDetailBean;
import vn.dating.task.form.UserSignUpForm;
import vn.dating.wsclient.WebserviceConstant;
import vn.dating.wsclient.WsClientHelper;
import android.content.Context;

public class SignupTask extends BaseAsyncTask<String, Void, UserDetailBean> {
	private UserDAO userDAO;
	
	private UserSignUpForm userSignUpForm;
	
	public SignupTask(TaskListener<UserDetailBean> taskListener, Context context, UserSignUpForm userSignUpForm) {
		super(taskListener, context);
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
		return userDetailBean;
	}

	@Override
	protected void onPostExecute(UserDetailBean userDetailBean) {
		taskListener.onPostExecute(userDetailBean);
	}

	@Override
	protected void onCancelled() {
		taskListener.onCancelled();
	}
}
