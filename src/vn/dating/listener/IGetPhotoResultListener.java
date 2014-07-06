package vn.dating.listener;

import java.util.List;

import android.graphics.Bitmap;

public interface IGetPhotoResultListener {
	public void onSuccessGetPhotos(List<Bitmap> photoBitmaps);
}
