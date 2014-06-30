package vn.dating.manager;


public class AccountManager {
	private String userEmail;
	private String password;
	
	private static AccountManager instance = new AccountManager();
	
	private AccountManager() {
		
	}
	
	public static AccountManager getInstance () {
		return instance;
	}
	
	public void updateCredential(String userEmail, String password) {
		this.userEmail = userEmail;
		this.password = password;
	}
	
	public String getUserEmail() {
		return userEmail;
	}
	
	public String getPassword() {
		return password;
	}
	
}
