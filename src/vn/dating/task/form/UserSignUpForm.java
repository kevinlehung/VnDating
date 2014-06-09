package vn.dating.task.form;


/**
 * Form for sign_up page
 * 
 * @author hunglevn@outlook.com
 *
 */
public class UserSignUpForm {
	private String email;
	
	private String password;
	
	private String confirmPassword;
	
	private boolean acceptAgreement;
	
	private String fullName;
	
	private String aboutMe;
	
	private String maritalStatus;
	
	private String gender;
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public boolean isAcceptAgreement() {
		return acceptAgreement;
	}

	public void setAcceptAgreement(boolean acceptAgreement) {
		this.acceptAgreement = acceptAgreement;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getAboutMe() {
		return aboutMe;
	}

	public void setAboutMe(String aboutMe) {
		this.aboutMe = aboutMe;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
}
