package vn.dating.db.dao;

import android.provider.BaseColumns;

public class DatingContract  {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public DatingContract() {}

    /* Inner class that defines user table contents */
    public static abstract class UserEntry implements BaseColumns {
        public static final String TABLE_NAME = "user";
        public static final String COLUMN_USER_EMAIL = "user_email";
        public static final String COLUMN_PASSWORD = "password";
    }
    
    /* Inner class that defines profile table contents */
    public static abstract class ProfileEntry implements BaseColumns {
        public static final String TABLE_NAME = "profile";
        public static final String COLUMN_PROFILE_ID = "profile_id";
        public static final String COLUMN_FULL_NAME = "full_name";
        public static final String COLUMN_GENDER = "gender";
        public static final String COLUMN_MARITAL_STATUS = "marital_status";
        public static final String COLUMN_ABOUT_ME = "about_me";
    }
}
