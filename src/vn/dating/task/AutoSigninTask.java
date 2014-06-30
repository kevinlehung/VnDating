package vn.dating.task;

import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import vn.dating.activity.MainActivity;
import vn.dating.activity.UserActivity;
import vn.dating.db.DatingContract.UserEntry;
import vn.dating.db.UserDAO;
import vn.dating.manager.AccountManager;
import vn.dating.task.bean.UserDetailBean;
import vn.dating.task.form.UserSigninForm;
import vn.dating.wsclient.WebserviceConstant;
import vn.dating.wsclient.WsClientHelper;
import android.content.Intent;

public class AutoSigninTask extends BaseAsyncTask<String, Void, UserDetailBean> {
	private UserDAO userDAO;
	
	public AutoSigninTask(MainActivity mainActivity) {
		super(mainActivity);
		this.userDAO = new UserDAO(dbHelper.getWritableDatabase());
	}

	@Override
	protected UserDetailBean doInBackground(String... params) {
		UserDetailBean userDetailBean = null;
		UserSigninForm userSigninForm = buildUserSigninForm();
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
		HttpHeaders headers = new HttpHeaders();
		headers.set("Connection", "Close");
		
		HttpEntity request = new HttpEntity(userSigninForm, headers);
		UserDetailBean userDetailBean = restTemplate.postForObject(signinWsUrl, request,
				UserDetailBean.class);
		return userDetailBean;
	}

	private UserSigninForm buildUserSigninForm() {
		Map<String, String> userDetail = userDAO.getUserDetail();
		if (userDetail != null) {
			UserSigninForm userSigninForm = new UserSigninForm(userDetail.get(UserEntry.COLUMN_USER_EMAIL), userDetail.get(UserEntry.COLUMN_PASSWORD));
			return userSigninForm;
		}
		return null;
	}

	@Override
	protected void onPostExecute(UserDetailBean userDetailBean) {
		if (userDetailBean != null) {
			AccountManager.getInstance().updateCredential(userDetailBean.getUserEmail(), userDetailBean.getPassword());
		}
		((MainActivity)context).showProgress(false);
		if (userDetailBean != null) {
			Intent i = new Intent(context, UserActivity.class);
			context.startActivity(i);
		}
	}

	@Override
	protected void onCancelled() {
	}
}