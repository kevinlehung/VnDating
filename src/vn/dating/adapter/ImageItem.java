package vn.dating.adapter;

import android.graphics.Bitmap;

/**
 * @author javatechig {@link http://javatechig.com}
 * 
 */
public class ImageItem {
	private Bitmap image;
	private String title;
	private String imagePath;
	private boolean isSelected;
	
	public ImageItem(Bitmap image, String title, String imagePath) {
		this.image = image;
		this.title = title;
		this.imagePath = imagePath;
	}

	public Bitmap getImage() {
		return image;
	}

	public void setImage(Bitmap image) {
		this.image = image;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	
}