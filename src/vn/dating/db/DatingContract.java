package vn.dating.db;

import android.provider.BaseColumns;

public class DatingContract  {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public DatingContract() {}

    /* Inner class that defines the table contents */
    public static abstract class UserEntry implements BaseColumns {
        public static final String TABLE_NAME = "user";
        public static final String COLUMN_USER_ID = "user_id";
        public static final String COLUMN_USER_EMAIL = "user_email";
        public static final String COLUMN_PASSWORD = "password";
    }
    
}
