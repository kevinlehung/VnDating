package vn.dating.activity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import vn.dating.R;
import vn.dating.adapter.ImageItem;
import vn.dating.adapter.PhotoGridViewAdapter;
import vn.dating.task.PostPhotosTask;
import vn.dating.util.FileHelper;
import vn.dating.util.GraphicUtil;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;

public class TakePhotoActivity extends Activity {
	private int PHOTO_FROM_GALLERY = 0;
	private int PHOTO_FROM_CAMERA = 1;
	
	private String recentlyTakenPhoto;
	
	private GridView gridView;
    private PhotoGridViewAdapter photoGridViewAdapter;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_take_photo);

		ImageView takePhotoImg = (ImageView) findViewById(R.id.takePhotoImg);
		takePhotoImg.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showTakePhotoDialog();
			}
		});
		 
		gridView = (GridView) findViewById(R.id.photoGridView);
		photoGridViewAdapter = new PhotoGridViewAdapter(this, R.layout.row_photo_grid);
        gridView.setAdapter(photoGridViewAdapter);


	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.take_photo_menu, menu);
	    return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.action_accept:
	            submitPhotos();
	            return true;
	        case R.id.action_remove:
	        	removeSelectedPhoto();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	private void removeSelectedPhoto() {
		photoGridViewAdapter.removeSelectedPhotos();
		
	}

	private void submitPhotos() {
		List<File> photoFiles = new ArrayList<File>();
		int photoCount = photoGridViewAdapter.getCount();
		for (int i = 0; i < photoCount; i++) {
			ImageItem imageItem = photoGridViewAdapter.getItem(i);
			File photoFile = new File(imageItem.getImagePath());
			photoFiles.add(photoFile);
		}
		PostPhotosTask postPhotosTask = new PostPhotosTask(this, photoFiles);
		postPhotosTask.execute(new String[]{});
	}

	private void showTakePhotoDialog() {
		AlertDialog.Builder takePhotoDialog = new AlertDialog.Builder(this);
		takePhotoDialog.setTitle("Pictures Option");
		takePhotoDialog.setMessage("Select Picture Mode");

		takePhotoDialog.setPositiveButton("Gallery",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {
						dispatchTakePictureFromGalleryIntent();
					}
				});

		takePhotoDialog.setNegativeButton("Camera",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {
						dispatchTakePictureFromCameraIntent();
					}
				});
		takePhotoDialog.show();
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == PHOTO_FROM_GALLERY && resultCode == RESULT_OK) {
			Uri selectedImageUri = data.getData();
			String photoPath = getRealPathFromURI(selectedImageUri);
			previewPhoto(photoPath);
		} else if (requestCode == PHOTO_FROM_CAMERA && resultCode == RESULT_OK) {
			previewPhoto(recentlyTakenPhoto);
		}
	}
	
	public String getRealPathFromURI(Uri contentUri) {
	    String res = null;
	    String[] proj = { MediaStore.Images.Media.DATA };
	    Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
	    if(cursor.moveToFirst()){;
	       int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
	       res = cursor.getString(column_index);
	    }
	    cursor.close();
	    return res;
	}
	
	private void previewPhoto(String photoPath) {
		Bitmap bitmap = GraphicUtil.decodeSampledBitmapFromResource(photoPath, 100, 110);
	    //photoGridViewAdapter.insert(new ImageItem(bitmap, null, photoPath), 0);
		photoGridViewAdapter.add(new ImageItem(bitmap, null, photoPath));
	}
	

	/**
	 * Show camera for take photo. Saving photo to "place holder file"
	 */
	private void dispatchTakePictureFromCameraIntent() {
	    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	    // Ensure that there's a camera activity to handle the intent
	    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
	        // Create the File where the photo should go
	        File photoFile = null;
	        try {
	            photoFile = FileHelper.createImageFilePlaceHolder();
	        } catch (IOException ex) {
	            // Error occurred while creating the File
	        	throw new RuntimeException(ex);
	        }
	        // Continue only if the File was successfully created
	        if (photoFile != null) {
	            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
	            recentlyTakenPhoto = photoFile.getAbsolutePath();
	            startActivityForResult(takePictureIntent, PHOTO_FROM_CAMERA);
	        }
	    }
	}
	
	private void dispatchTakePictureFromGalleryIntent() {
		Intent i = new Intent(Intent.ACTION_GET_CONTENT, null);
		i.setType("image/*");
		i.putExtra("return-data", true);
		startActivityForResult(i, PHOTO_FROM_GALLERY);
	}

}
