package xyz.belvi.mail2push.DB;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import java.util.Date;

/**
 * Created by zone2 on 5/1/16.
 */
public class ChatOperations {

    public static int saveChat(Context context, String text, String sender, int type, int status, long time) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ChatClient.COLUMN_EMAIL, sender);
        contentValues.put(ChatClient.COLUMN_TEXT, text);
        contentValues.put(ChatClient.COLUMN_TYPE, type);
        contentValues.put(ChatClient.COLUMN_STATUS, status);
        contentValues.put(ChatClient.COLUMN_TIME, time);
        Uri uri = context.getContentResolver().insert(ChatClient.getChatClient(context), contentValues);
        return Integer.parseInt(uri.getLastPathSegment());

    }

    public static void updateChat(Context context, int id, String... keyValue) {
        int index = 0;
        String key = "";
        String value = "";
        ContentValues contentValues = new ContentValues();
        for (String kv : keyValue) {
            if ((index % 2) == 0) {
                key = kv;
            } else {
                value = kv;
                Log.e("params key", "" + key);
                Log.e("params value", "" + value);
                contentValues.put(key, value);
            }
            index++;
        }


        context.getContentResolver().update(ChatClient.getChatClient(context), contentValues, ChatClient._ID + " = ?", new String[]{String.valueOf(id)});
    }
}
