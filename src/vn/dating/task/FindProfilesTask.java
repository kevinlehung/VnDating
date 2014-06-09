package vn.dating.task;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import vn.dating.adapter.ProfileArrayAdapter;
import vn.dating.task.bean.ProfileBean;
import vn.dating.wsclient.WebserviceConstant;
import vn.dating.wsclient.WsClientHelper;

public class FindProfilesTask extends BaseAsyncTask<String, Void, List<ProfileBean>> {
	ProfileArrayAdapter adapter;
	
	public FindProfilesTask(ProfileArrayAdapter adapter) {
		super(null, null);
		this.adapter = adapter;
	}
    protected List<ProfileBean> doInBackground(String... urls) {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		
		params.set("email", "uther@ymail.com");
		params.set("password", "helloworld");
		
		HttpEntity<MultiValueMap<String,String>> requestEntity = WsClientHelper.buildRequestObj(params);
		
		String searchUserProfileWsUrl = WsClientHelper.buildWsUrl(WebserviceConstant.API.SEARCH_USER_PROFILE);
		RestTemplate template = WsClientHelper.buildRestTemplate();
		ProfileBean profileBean = template.postForObject(
				searchUserProfileWsUrl, requestEntity,
				ProfileBean.class, new Object[]{});
        
		List<ProfileBean> profileBeans = new ArrayList<ProfileBean>();
		profileBeans.add(profileBean);
		return profileBeans;
    }

    protected void onPostExecute(List<ProfileBean> profileBeans) {
    	adapter.setProfileBeans(profileBeans);
		adapter.notifyDataSetChanged();
    }
}