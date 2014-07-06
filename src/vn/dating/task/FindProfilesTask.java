package vn.dating.task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import android.graphics.Bitmap;
import vn.dating.adapter.ProfileArrayAdapter;
import vn.dating.task.bean.ProfileBean;
import vn.dating.util.GraphicUtil;
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
		for (ProfileBean profileBean : profileBeans) {
			List<String> photoUrls = profileBean.getPhotoUrls();
			if (photoUrls != null) {
				List<Bitmap> photoBitmaps = new ArrayList<Bitmap>();
	
				for (String photoUrl : photoUrls) {
					Bitmap bitmap = GraphicUtil.getImageBitmap(photoUrl);
					photoBitmaps.add(bitmap);
				}
				profileBean.setPhotoBitmaps(photoBitmaps);
			}
			
		}
		return Arrays.asList(profileBeans);
    }

    protected void onPostExecute(List<ProfileBean> profileBeans) {
    	adapter.addAll(profileBeans);
    }
}