package vn.dating.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class GraphicUtil {
	public static int calculateInSampleSize(BitmapFactory.Options bmpOptions,
			int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = bmpOptions.outHeight;
		final int width = bmpOptions.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			final int halfHeight = height / 2;
			final int halfWidth = width / 2;

			// Calculate the largest inSampleSize value that is a power of 2 and
			// keeps both
			// height and width larger than the requested height and width.
			while ((halfHeight / inSampleSize) > reqHeight
					&& (halfWidth / inSampleSize) > reqWidth) {
				inSampleSize *= 2;
			}
		}

		return inSampleSize;
	}

	public static Bitmap decodeSampledBitmapFromResource(String photoPath, int reqWidth, int reqHeight) {

		// First decode with inJustDecodeBounds=true to check dimensions
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(photoPath, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, reqWidth,
				reqHeight);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(photoPath, options);
	}
	
	public static File scalePhoto(File orignalPhotoFile, int width, int height) {
		File scaledPhotoFile = null;
		OutputStream scaledPhotoFileOut = null;
        try {
        	scaledPhotoFile = FileHelper.createImageFilePlaceHolder();
            scaledPhotoFileOut = new FileOutputStream(scaledPhotoFile);
            Bitmap bitmap = GraphicUtil.decodeSampledBitmapFromResource(orignalPhotoFile.getAbsolutePath(), width, height);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, scaledPhotoFileOut);
        } catch (IOException ex) {
            // Error occurred while creating the File
        	throw new RuntimeException(ex);
        } finally {
        	IOUtils.closeQuietly(scaledPhotoFileOut);
        }
        
        return scaledPhotoFile;
	}
	
	public static Bitmap getImageBitmap(String url) {
        Bitmap bm = null;
        InputStream is = null;
        BufferedInputStream bis = null;
        try {
            URL aURL = new URL(url);
            URLConnection conn = aURL.openConnection();
            conn.connect();
            is = conn.getInputStream();
            bis = new BufferedInputStream(is);
            bm = BitmapFactory.decodeStream(bis);
       } catch (IOException e) {
           throw new RuntimeException(e);
       } finally {
    	   IOUtils.closeQuietly(bis);
    	   IOUtils.closeQuietly(is);
       }
       return bm;
    }
}
