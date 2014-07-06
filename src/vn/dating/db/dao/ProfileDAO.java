package vn.dating.db.dao;

import java.util.HashMap;
import java.util.Map;

import vn.dating.db.bean.UserProfileBean;
import vn.dating.db.dao.DatingContract.ProfileEntry;
import android.content.ContentValues;
import android.database.Cursor;

public class ProfileDAO extends BaseDAO {

	public long resetUserProfile(UserProfileBean userProfileBean) {
		this.removeUserProfile();
		return this.insertUserProfile(userProfileBean);
	}
	public long insertUserProfile(UserProfileBean userProfileBean) {
		ContentValues values = new ContentValues();
		values.put(ProfileEntry.COLUMN_ABOUT_ME, userProfileBean.getAboutMe());
		values.put(ProfileEntry.COLUMN_FULL_NAME, userProfileBean.getFullName());
		values.put(ProfileEntry.COLUMN_GENDER, userProfileBean.getGender());
		values.put(ProfileEntry.COLUMN_MARITAL_STATUS, userProfileBean.getMaritalStatus());
		
		// Insert the new row, returning the primary key value of the new row
		 long newRowId = db.insert(ProfileEntry.TABLE_NAME, null, values);
		 return newRowId;
	}
	
	public int removeUserProfile() {
		return db.delete(ProfileEntry.TABLE_NAME, null, null);
	}
	
	public Map<String, String> getProfileDetail() {
		String[] projection = { ProfileEntry._ID, 
				ProfileEntry.COLUMN_ABOUT_ME,
				ProfileEntry.COLUMN_FULL_NAME,
				ProfileEntry.COLUMN_GENDER,
				ProfileEntry.COLUMN_MARITAL_STATUS,};

		Cursor c = db.query(ProfileEntry.TABLE_NAME, // The table to query
				projection, // The columns to return
				null, // The columns for the WHERE clause
				null, // The values for the WHERE clause
				null, // don't group the rows
				null, // don't filter by row groups
				null // The sort order
				);
		if (c.getCount() > 0) {
			Map<String, String> profileDetails = new HashMap<String, String>();
			c.moveToFirst();
			String[] columnNames = c.getColumnNames();
			for (String columnName : columnNames) {
				int columnIndex = c.getColumnIndexOrThrow(columnName);
				String value = c.getString(columnIndex);
				profileDetails.put(columnName, value);
			}
			return profileDetails;
		}
		return null;
	}
}
