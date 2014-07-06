package vn.dating.task;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.client.RestTemplate;

import vn.dating.listener.IGetPhotoResultListener;
import vn.dating.util.GraphicUtil;
import vn.dating.wsclient.WsClientHelper;
import android.content.Context;
import android.graphics.Bitmap;

public class GetPhotoTask extends BaseAsyncTask<String, Integer, List<Bitmap>> {
	private IGetPhotoResultListener listener;
	
	public GetPhotoTask(Context context, IGetPhotoResultListener listener) {
		super(context);
		this.listener = listener;
	}
	
	@Override
	protected List<Bitmap> doInBackground(String... photoUrls) {
		List<Bitmap> photoBitmaps = new ArrayList<Bitmap>();

		for (String photoUrl : photoUrls) {
			Bitmap bitmap = GraphicUtil.getImageBitmap(photoUrl);
			photoBitmaps.add(bitmap);
		}
		return photoBitmaps;
	}

	
	
	@Override
	protected void onPostExecute(List<Bitmap> photoBitmaps) {
		if (listener != null) {
			listener.onSuccessGetPhotos(photoBitmaps);
		}
	}
}