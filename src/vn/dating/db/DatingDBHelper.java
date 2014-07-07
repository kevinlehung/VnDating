package vn.dating.db;

import vn.dating.app.VnDatingApp;
import vn.dating.db.dao.DatingContract.ProfileEntry;
import vn.dating.db.dao.DatingContract.UserEntry;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatingDBHelper  extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Dating.db";
    
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    
    private static final String SQL_CREATE_USER =
        "CREATE TABLE " + UserEntry.TABLE_NAME + " (" +
        UserEntry._ID + " INTEGER PRIMARY KEY," +
        UserEntry.COLUMN_USER_EMAIL + TEXT_TYPE + COMMA_SEP +
        UserEntry.COLUMN_PASSWORD + TEXT_TYPE + " )";

    private static final String SQL_CREATE_PROFILE =
            "CREATE TABLE " + ProfileEntry.TABLE_NAME + " (" +
            ProfileEntry._ID + " INTEGER PRIMARY KEY," +
            ProfileEntry.COLUMN_ABOUT_ME + TEXT_TYPE + COMMA_SEP +
            ProfileEntry.COLUMN_FULL_NAME + TEXT_TYPE + COMMA_SEP +
            ProfileEntry.COLUMN_GENDER + TEXT_TYPE + COMMA_SEP +
            ProfileEntry.COLUMN_MARITAL_STATUS + TEXT_TYPE + " )";
    
    private static final String SQL_DROP_USER =
        "DROP TABLE IF EXISTS " + UserEntry.TABLE_NAME;
    
    private static final String SQL_DROP_PROFILE =
            "DROP TABLE IF EXISTS " + ProfileEntry.TABLE_NAME;
    
    private static DatingDBHelper instance = null;
    
    public static DatingDBHelper getInstance() {
    	if (instance == null) {
    		Context context = VnDatingApp.getAppContext();
    		instance = new DatingDBHelper(context);
    	}
    	return instance;
    }
    
    private DatingDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_USER);
        db.execSQL(SQL_CREATE_PROFILE);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        //db.execSQL(SQL_DELETE_USER);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}