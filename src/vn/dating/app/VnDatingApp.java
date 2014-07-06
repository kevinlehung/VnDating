package vn.dating.app;

import android.app.Application;
import android.content.Context;

public class VnDatingApp extends Application{

    private static Context context;

    public void onCreate(){
        super.onCreate();
        VnDatingApp.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return VnDatingApp.context;
    }
}