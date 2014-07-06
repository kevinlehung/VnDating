package vn.dating.listener;

import vn.dating.db.DatingDBHelper;
import vn.dating.db.bean.UserProfileBean;
import vn.dating.db.dao.ProfileDAO;
import vn.dating.db.dao.UserDAO;
import vn.dating.task.form.UserSignUpForm;
import android.content.Context;

public class SignupResultListener implements ISignupResultListener {
	private UserDAO userDAO;
	private ProfileDAO profileDAO;
	
	public SignupResultListener(Context context) {
		userDAO = new UserDAO();
		profileDAO = new ProfileDAO();
	}
	
	@Override
	public void onSignupSuccess(UserSignUpForm userSignUpForm) {
		userDAO.resetUserDetail(userSignUpForm.getEmail(), userSignUpForm.getPassword());
		UserProfileBean userProfileBean = buildUserProfileBean(userSignUpForm);
		profileDAO.resetUserProfile(userProfileBean );
	}

	private UserProfileBean buildUserProfileBean(UserSignUpForm userSignUpForm) {
		UserProfileBean userProfileBean = new UserProfileBean();
		userProfileBean.setAboutMe(userSignUpForm.getAboutMe());
		userProfileBean.setFullName(userSignUpForm.getFullName());
		userProfileBean.setGender(userSignUpForm.getGender());
		userProfileBean.setMaritalStatus(userSignUpForm.getMaritalStatus());
		return userProfileBean;
	}

	@Override
	public void onSignupFailed() {
		// TODO Auto-generated method stub
		
	}

}
