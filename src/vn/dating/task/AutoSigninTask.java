package vn.dating.task;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import vn.dating.db.DatingContract.UserEntry;
import vn.dating.db.UserDAO;
import vn.dating.task.bean.UserDetailBean;
import vn.dating.task.form.UserSigninForm;
import vn.dating.wsclient.WebserviceConstant;
import vn.dating.wsclient.WsClientHelper;
import android.content.Context;

public class AutoSigninTask extends BaseAsyncTask<String, Void, UserDetailBean> {
	private UserDAO userDAO;
	
	public AutoSigninTask(TaskListener<UserDetailBean> taskListener,
			Context context) {
		super(taskListener, context);
		
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
		taskListener.onPostExecute(userDetailBean);
	}

	@Override
	protected void onCancelled() {
		taskListener.onCancelled();
	}
}