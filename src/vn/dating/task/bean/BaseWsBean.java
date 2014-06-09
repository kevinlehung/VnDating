package vn.dating.task.bean;

public class BaseWsBean {
	private String processStatus;
	private String statusMessage;
	private String result;
	private String jSessionId;
	
	public String getProcessStatus() {
		return processStatus;
	}

	public void setProcessStatus(String processStatus) {
		this.processStatus = processStatus;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getjSessionId() {
		return jSessionId;
	}

	public void setjSessionId(String jSessionId) {
		this.jSessionId = jSessionId;
	}
}
