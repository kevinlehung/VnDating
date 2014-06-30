package vn.dating.task;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import vn.dating.activity.TakePhotoActivity;
import vn.dating.activity.UserActivity;
import vn.dating.task.bean.PhotoWsBean;
import vn.dating.util.GraphicUtil;
import vn.dating.wsclient.WebserviceConstant;
import vn.dating.wsclient.WsClientHelper;
import android.content.Intent;

public class PostPhotosTask extends BaseAsyncTask<String, Void, List<PhotoWsBean>> {
	private List<File> photoFiles;
	
	public PostPhotosTask(TakePhotoActivity takePhotoActivity, List<File> photoFiles) {
		super(takePhotoActivity);
		this.photoFiles = photoFiles;
	}
	
	@Override
	protected List<PhotoWsBean> doInBackground(String... params) {
		RestTemplate restTemplate = WsClientHelper.buildRestTemplate();
		List<PhotoWsBean> photoWsBeans = callPostPhotoWs(photoFiles,
				restTemplate);
		return photoWsBeans;
    }

	private List<PhotoWsBean> callPostPhotoWs(List<File> photoFiles,
			RestTemplate restTemplate) {
		List<PhotoWsBean> result = new ArrayList<PhotoWsBean>();
		String addPhotosWsUrl = WsClientHelper
				.buildWsUrl(WebserviceConstant.API.ADD_PHOTOS_WS);
		
		List<File> tempPhotos = new ArrayList<File>();
		try {
			Map<String, String> headers = new HashMap<String, String>();
			
			MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
			for (File photoFile : photoFiles) {
				File scaledPhoto = GraphicUtil.scalePhoto(photoFile, 440, 400);
				params.add("files", new FileSystemResource(scaledPhoto));
				tempPhotos.add(scaledPhoto);
			}
			HttpEntity<MultiValueMap<String,String>> requestEntity = WsClientHelper.buildRequestObjWithAuth(params, headers);
			
			List<PhotoWsBean> photoWsBeans = restTemplate.postForObject(addPhotosWsUrl,
					requestEntity, List.class);
		} finally {
			for (File tempPhoto : tempPhotos) {
				tempPhoto.delete();	
			}
			
		}
		return result;
	}

	@Override
	protected void onPostExecute(List<PhotoWsBean> photoWsBeans) {
		Intent intend = new Intent(context, UserActivity.class);
		context.startActivity(intend);
	}

}