package vn.dating.db.dao;

import vn.dating.app.VnDatingApp;
import vn.dating.db.DatingDBHelper;
import android.database.sqlite.SQLiteDatabase;

public class BaseDAO {
	protected SQLiteDatabase db;
	
	public BaseDAO() {
		DatingDBHelper dbHelper = new DatingDBHelper(VnDatingApp.getAppContext());
		dbHelper.getWritableDatabase();
		
	}
}
