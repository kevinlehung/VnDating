package vn.dating.listener;

import vn.dating.db.dao.UserDAO;
import android.content.Context;

public class AuthenticateResultListener implements IAuthenticateResultListener {
	private UserDAO userDAO;
	
	public AuthenticateResultListener(Context context) {
		userDAO = new UserDAO();	
	}
	
	@Override
	public void onSigninSuccess(String userName, String password) {
		userDAO.resetUserDetail(userName, password);
	}

	@Override
	public void onSigninFailed() {
	}

}
