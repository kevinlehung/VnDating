package vn.dating.wsclient;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.support.Base64;
import org.springframework.web.client.RestTemplate;

import vn.dating.manager.AccountManager;
import vn.dating.task.bean.BaseWsBean;

public class WsClientHelper {
	public static HttpHeaders buildBasicHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		return headers;
	}

	public static void addAuthHeaders(HttpHeaders headers, String userName,
			String password) {
		String auth = userName + ":" + password;
		byte[] encodedAuthorisation = Base64.encodeBytesToBytes(auth.getBytes());
		headers.add("Authorization", "Basic "
				+ new String(encodedAuthorisation));
	}
	
	public static MultiValueMap<String, String> buildRequestParams(String[] paramNames, String[] paramValues) {
		if (paramNames.length != paramValues.length) {
			throw new RuntimeException(String.format("Length of paramNames [length = %s] and paramValues [length=%s] does not matches.", 
					paramNames.length, paramValues.length));
		}
		
		MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<String, String>();
		for (int i = 0; i < paramNames.length; i++) {
			requestParams.set(paramNames[i], paramValues[i]);
		}
		return requestParams;
	}
	
	public static RestTemplate buildRestTemplate() {
		
		RestTemplate restTemplate = new RestTemplate();

		HttpMessageConverter<?> formHttpMessageConverter = new FormHttpMessageConverter();
		HttpMessageConverter<?> stringHttpMessageConverter = new StringHttpMessageConverter();
		HttpMessageConverter<?> jacksonHttpMessageConverter = new MappingJackson2HttpMessageConverter();
		restTemplate.getMessageConverters().add(formHttpMessageConverter);
		restTemplate.getMessageConverters().add(stringHttpMessageConverter);
		restTemplate.getMessageConverters().add(jacksonHttpMessageConverter);
		
		return restTemplate;
	}
	
	public static String buildWsUrl(String api) {
		return WebserviceConstant.BASE_WEBSERVICE_URL + "/" + api;
	}
	
	public static HttpEntity buildRequestObj(Object entity) {
		return buildRequestObj(entity, new HashMap<String, String>());
	}
	
	public static HttpEntity buildRequestObj(Object entity, Map<String, String> headers) {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setAll(headers);
		httpHeaders.set("Connection", "Close");
		HttpEntity request = new HttpEntity(entity, httpHeaders);
		return request;
	}
	
	public static HttpEntity buildRequestObjWithAuth(Object entity, Map<String, String> headers) {
		setAuthToHeader(headers);
		return buildRequestObj(entity,  headers);
	}
	public static void setAuthToHeader(Map<String, String> headers) {
		setAuthToHeader(headers, AccountManager.getInstance().getUserEmail(), AccountManager.getInstance().getPassword());
	}
	
	public static void setAuthToHeader(Map<String, String> headers, String userEmail, String password) {
		String auth = userEmail + ":" + password;
		String encodedAuthorisation = Base64.encodeBytes(auth.getBytes());
		headers.put("Authorization", "Basic " + encodedAuthorisation);
	}
	
	public static boolean isSuccessWsBean(BaseWsBean wsBean) {
		return wsBean != null && "SUCCESSED".equalsIgnoreCase(wsBean.getProcessStatus());
	}
}
