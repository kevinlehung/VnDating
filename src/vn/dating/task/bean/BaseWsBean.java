package vn.dating.task.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseWsBean {
	public static final int DEFAULT_SUCCESS_CODE = 1;
	public static final int DEFAULT_FAILED_CODE = 1;
	
	private int resultCode;

	public int getResultCode() {
		return resultCode;
	}

	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}
}
