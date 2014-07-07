package vn.dating.db.dao;

import java.util.HashMap;
import java.util.Map;

import vn.dating.db.dao.DatingContract.UserEntry;
import android.content.ContentValues;
import android.database.Cursor;

public class UserDAO extends BaseDAO {

	public long resetUserDetail(String email, String password) {
		removeUserDetail();
		return storeUserDetail(email, password);
	}
	
	public int removeUserDetail() {
		return db.delete(UserEntry.TABLE_NAME, null, null);
	}
	
	public long storeUserDetail(String email, String password) {
		ContentValues values = new ContentValues();
		values.put(UserEntry.COLUMN_USER_EMAIL, email);
		values.put(UserEntry.COLUMN_PASSWORD, password);

		// Insert the new row, returning the primary key value of the new row
		 long newRowId = db.insert(UserEntry.TABLE_NAME, null, values);
		 return newRowId;
	}
	
	public Map<String, String> getUserDetail() {
		String[] projection = { UserEntry._ID, 
				UserEntry.COLUMN_USER_EMAIL,
				UserEntry.COLUMN_PASSWORD, };

		Cursor c = db.query(UserEntry.TABLE_NAME, // The table to query
				projection, // The columns to return
				null, // The columns for the WHERE clause
				null, // The values for the WHERE clause
				null, // don't group the rows
				null, // don't filter by row groups
				null // The sort order
				);
		if (c.getCount() > 0) {
			c.moveToFirst();
			String userEmail = c.getString(c.getColumnIndexOrThrow(UserEntry.COLUMN_USER_EMAIL));
			String password = c.getString(c.getColumnIndexOrThrow(UserEntry.COLUMN_PASSWORD));
			
			if (userEmail != null && password != null) {
				Map<String, String> userDetails = new HashMap<String, String>();
				userDetails.put(UserEntry.COLUMN_USER_EMAIL, userEmail);
				userDetails.put(UserEntry.COLUMN_PASSWORD, password);
				return userDetails;
			}
		}
		return null;
	}
	
}
