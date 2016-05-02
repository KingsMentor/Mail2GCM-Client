package xyz.belvi.mail2push;

import android.content.Context;
import android.provider.Settings;

/**
 * Created by zone2 on 5/1/16.
 */
public class Cache {

    public static void saveGcmKey(Context context, String key) {
        context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE).edit().putString(context.getPackageName() + ".key", key).commit();
    }

    public static String getGcmKey(Context context) {
        return context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE).getString(context.getPackageName() + ".key", "");
    }


    static public String getDeviceID(Context context) {
        return Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }
}
