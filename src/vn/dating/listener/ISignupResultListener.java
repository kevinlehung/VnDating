package vn.dating.listener;

import vn.dating.task.form.UserSignUpForm;

public interface ISignupResultListener {

	void onSignupSuccess(UserSignUpForm userSignUpForm);

	void onSignupFailed();

}
