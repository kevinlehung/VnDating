package vn.dating.task;

import java.util.HashMap;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import vn.dating.db.UserDAO;
import vn.dating.task.bean.UserDetailBean;
import vn.dating.task.form.UserSigninForm;
import vn.dating.wsclient.WebserviceConstant;
import vn.dating.wsclient.WsClientHelper;
import android.content.Context;

public class SigninTask extends BaseAsyncTask<String, Void, UserDetailBean> {
	private UserDAO userDAO;
	
	public SigninTask(TaskListener<UserDetailBean> taskListener,
			Context context) {
		super(taskListener, context);
		userDAO = new UserDAO(this.dbHelper.getWritableDatabase());
	}

	@Override
	protected UserDetailBean doInBackground(String... params) {
		UserSigninForm userSigninForm = buildUserSigninForm(params);
		
		RestTemplate restTemplate = WsClientHelper.buildRestTemplate();
		UserDetailBean userDetailBean = callSigninWs(userSigninForm,
				restTemplate);
		if (userDetailBean != null && "SUCCESSED".equalsIgnoreCase(userDetailBean.getProcessStatus())) {
			userDAO.resetUserDetail(userSigninForm.getEmail(), userSigninForm.getPassword());
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
		taskListener.onPostExecute(userDetailBean);
	}

	@Override
	protected void onCancelled() {
		taskListener.onCancelled();
	}
}
