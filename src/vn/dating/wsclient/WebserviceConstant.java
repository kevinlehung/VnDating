package vn.dating.wsclient;

public interface WebserviceConstant {
	//public static final String BASE_WEBSERVICE_URL = "http://Welcomes-PC:8080/ds/ws";
	//public static final String BASE_WEBSERVICE_URL = "http://192.168.1.9:8080/ds/ws";
	public static final String BASE_WEBSERVICE_URL = "http://192.168.14.21:8080/ds/ws";
	
	public interface ParamNames {
		public static final String EMAIL = "email";
		public static final String PASSWORD = "password";
		public static final String ACCEPT_AGREEMENT = "acceptAgreement";
	}
	
	public interface API {
		public static final String SIGNUP = "sign_up.ds";
		public static final String SIGNIN = "sign_in.ds";
		public static final String SEARCH_USER_PROFILE = "search_user_profile.ds";
		public static final String ADD_PHOTOS_WS = "add_photos.ds";
	}
}
