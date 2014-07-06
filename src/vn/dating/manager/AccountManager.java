package vn.dating.manager;

import java.util.HashMap;
import java.util.Map;

import vn.dating.db.dao.DatingContract.UserEntry;
import vn.dating.db.dao.UserDAO;
import vn.dating.manager.bean.UserCredentialBean;

public class AccountManager {
	private static AccountManager instance = new AccountManager();
	
	private UserDAO userDAO;
	
	private UserCredentialBean userCredential;
	private AccountManager() {
		this.userDAO = new UserDAO();
	}
	
	public static AccountManager getInstance () {
		return instance;
	}
	
	/**
	 * 
	 * @return
	 */
	public UserCredentialBean getUserCredential() {
		if (userCredential == null) {
			Map<String, String> userDetail = userDAO.getUserDetail();
			if (userDetail != null) {
				userCredential = new UserCredentialBean();
				userCredential.setUserName(userDetail.get(UserEntry.COLUMN_USER_EMAIL));
				userCredential.setPassword(userDetail.get(UserEntry.COLUMN_PASSWORD));
			}
		} 
		return userCredential;
	}
	
	
}
