package vn.dating.task.bean;

import java.util.List;

import android.graphics.Bitmap;

public class ProfileBean extends BaseWsBean {
	private String fullName;
	private List<String> photoUrls;
	
	private String aboutMe;
	private String userEmail;
	private String password;

	public ProfileBean() {
		
	}
	
	public ProfileBean(String fullName, List<String> photoUrls, String aboutMe) {
		this.fullName = fullName;
		this.aboutMe = aboutMe;
		this.photoUrls = photoUrls;
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

	

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<String> getPhotoUrls() {
		return photoUrls;
	}

	public void setPhotoUrls(List<String> photoUrls) {
		this.photoUrls = photoUrls;
	}

}
