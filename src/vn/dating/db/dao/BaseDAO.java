package vn.dating.db.dao;

import vn.dating.db.DatingDBHelper;
import android.database.sqlite.SQLiteDatabase;

public class BaseDAO {
	protected SQLiteDatabase db;
	
	public BaseDAO() {
		DatingDBHelper dbHelper = DatingDBHelper.getInstance();
		db = dbHelper.getWritableDatabase();
		
	}
}
