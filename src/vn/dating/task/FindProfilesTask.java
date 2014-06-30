package vn.dating.task;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
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
		super(null);
		this.adapter = adapter;
	}
    protected List<ProfileBean> doInBackground(String... urls) {
    	Map<String, String> headers = new HashMap<String, String>();
		MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
		
		HttpEntity<MultiValueMap<String,String>> requestEntity = WsClientHelper.buildRequestObjWithAuth(params, headers);
		
		String searchUserProfileWsUrl = WsClientHelper.buildWsUrl(WebserviceConstant.API.SEARCH_USER_PROFILE);
		RestTemplate template = WsClientHelper.buildRestTemplate();
		ProfileBean[] profileBeans  = template.postForObject(
				searchUserProfileWsUrl, requestEntity,
				ProfileBean[].class, new Object[]{});
        
		return Arrays.asList(profileBeans);
    }

    protected void onPostExecute(List<ProfileBean> profileBeans) {
    	adapter.setProfileBeans(profileBeans);
		adapter.notifyDataSetChanged();
    }
}