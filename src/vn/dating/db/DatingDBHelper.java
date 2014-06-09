package vn.dating.db;

import vn.dating.db.DatingContract.UserEntry;
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

    private static final String SQL_DELETE_USER =
        "DROP TABLE IF EXISTS " + UserEntry.TABLE_NAME;
    public DatingDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_USER);
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