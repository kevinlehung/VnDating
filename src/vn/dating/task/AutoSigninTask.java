package vn.dating.task;

import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import vn.dating.activity.MainActivity;
import vn.dating.activity.UserActivity;
import vn.dating.db.dao.UserDAO;
import vn.dating.db.dao.DatingContract.UserEntry;
import vn.dating.listener.IAuthenticateResultListener;
import vn.dating.task.bean.UserDetailBean;
import vn.dating.task.form.UserSigninForm;
import vn.dating.wsclient.WebserviceConstant;
import vn.dating.wsclient.WsClientHelper;
import android.content.Intent;

public class AutoSigninTask extends BaseAsyncTask<String, Void, UserDetailBean> {
	private UserDAO userDAO;
	private Map<String, String> localUserDetail;
	
	private IAuthenticateResultListener authenticateResultListener;
	
	public AutoSigninTask(MainActivity mainActivity, IAuthenticateResultListener authenticateResultListener) {
		super(mainActivity);
		this.userDAO = new UserDAO();
		this.authenticateResultListener = authenticateResultListener;
	}

	@Override
	protected UserDetailBean doInBackground(String... params) {
		UserDetailBean userDetailBean = null;
		localUserDetail = userDAO.getUserDetail();
		UserSigninForm userSigninForm = buildUserSigninForm(localUserDetail);
		if (userSigninForm != null) {
			RestTemplate restTemplate = WsClientHelper.buildRestTemplate();
			
			userDetailBean = callSigninWs(userSigninForm,
					restTemplate);
	        
		}
		
		return userDetailBean;
    }

	private UserDetailBean callSigninWs(
			UserSigninForm userSigninForm,
			RestTemplate restTemplate) {
		String signinWsUrl = WsClientHelper.buildWsUrl(WebserviceConstant.API.SIGNIN);
		MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
		HttpEntity request = WsClientHelper.buildRequestObj(userSigninForm);
		
		UserDetailBean userDetailBean = restTemplate.postForObject(signinWsUrl, request,
				UserDetailBean.class);
		return userDetailBean;
	}

	private UserSigninForm buildUserSigninForm(Map<String, String> localUserDetail) {
		if (localUserDetail != null) {
			UserSigninForm userSigninForm = new UserSigninForm(localUserDetail.get(UserEntry.COLUMN_USER_EMAIL), localUserDetail.get(UserEntry.COLUMN_PASSWORD));
			return userSigninForm;
		}
		return null;
	}

	@Override
	protected void onPostExecute(UserDetailBean userDetailBean) {
		((MainActivity)context).showProgress(false);
		if (userDetailBean != null) {
			Intent i = new Intent(context, UserActivity.class);
			context.startActivity(i);
		}
	}
}