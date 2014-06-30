package vn.dating.adapter;

import java.util.ArrayList;

import vn.dating.R;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
/**
 * 
 * @author javatechig {@link http://javatechig.com}
 * 
 */
public class PhotoGridViewAdapter extends ArrayAdapter<ImageItem> {
    private Context context;
    private int layoutResourceId;
    
    public PhotoGridViewAdapter(Context context, int layoutResourceId) {
        super(context, layoutResourceId, new ArrayList<ImageItem>());
        this.layoutResourceId = layoutResourceId;
        this.context = context;
    }
 
    
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            view = inflater.inflate(layoutResourceId, parent, false);
            final ViewHolder holder = new ViewHolder();
            holder.image = (ImageView) view.findViewById(R.id.image);
            holder.selectImage = (CheckBox) view.findViewById(R.id.selectImage);
            holder.selectImage.setOnCheckedChangeListener(new OnPhotoCheckedChangeListener(holder.selectImage));
            view.setTag(holder);
        }
        
       	renderImageItem(position, view);
        return view;
    }


	private void renderImageItem(final int position, View row) {
		ViewHolder holder = (ViewHolder) row.getTag();
 
        ImageItem item = (ImageItem)this.getItem(position);
        holder.selectImage.setTag(item);
        holder.selectImage.setChecked(item.isSelected());
        holder.image.setImageBitmap(item.getImage());
	}
 
    static class ViewHolder {
        ImageView image;
        CheckBox selectImage;
    }
    public class OnPhotoCheckedChangeListener implements OnCheckedChangeListener { 
    	private CheckBox checkBox;
    	public OnPhotoCheckedChangeListener(CheckBox checkBox) {
			this.checkBox = checkBox;
		}
			
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			ImageItem imageItem = (ImageItem)checkBox.getTag();
			imageItem.setSelected(isChecked);
			
		}
    }
	public void removeSelectedPhotos() {
		int itemCount = this.getCount();
		for (int i = itemCount - 1; i >=0 ; i--) {
			ImageItem imageItem = this.getItem(i);
			if (imageItem.isSelected()) {
				this.remove(imageItem);
			}
		}
	}
    
    
}