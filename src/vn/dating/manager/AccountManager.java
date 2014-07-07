package vn.dating.manager;

import java.util.Map;

import vn.dating.db.dao.DatingContract.ProfileEntry;
import vn.dating.db.dao.DatingContract.UserEntry;
import vn.dating.db.dao.ProfileDAO;
import vn.dating.db.dao.UserDAO;
import vn.dating.manager.bean.ProfileBean;
import vn.dating.manager.bean.UserCredentialBean;

public class AccountManager {
	private static AccountManager instance = new AccountManager();
	
	private UserDAO userDAO;
	private ProfileDAO profileDAO;
	
	private UserCredentialBean userCredential;
	private AccountManager() {
		this.userDAO = new UserDAO();
		profileDAO = new ProfileDAO();
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
	
	public ProfileBean getProfile() {
		ProfileBean profile = null;
		Map<String, String> profileDetail = profileDAO.getProfileDetail();
		if (profileDetail != null) {
			profile = new ProfileBean();
			profile.setAboutMe(profileDetail.get(ProfileEntry.COLUMN_ABOUT_ME));
			profile.setFullName(profileDetail.get(ProfileEntry.COLUMN_FULL_NAME));
			profile.setGender(profileDetail.get(ProfileEntry.COLUMN_GENDER));
			profile.setMaritalStatus(profileDetail.get(ProfileEntry.COLUMN_MARITAL_STATUS));
		}
		return profile;
	}

	public void resetAll() {
		profileDAO.removeUserProfile();
		userDAO.removeUserDetail();
		this.userCredential = null;
	}
}
