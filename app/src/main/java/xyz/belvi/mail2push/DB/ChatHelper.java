package xyz.belvi.mail2push.DB;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by zone2 on 5/1/16.
 */
public class ChatHelper {

    private  String sql = "CREATE TABLE " +
            ChatClient.TABLE_NAME + " ( " +
            ChatClient._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ChatClient.COLUMN_TEXT + " TEXT , " +
            ChatClient.COLUMN_TYPE + " INTEGER , " +
            ChatClient.COLUMN_TIME + " INTEGER , " +
            ChatClient.COLUMN_STATUS + " VARCHAR(50) , " +
            ChatClient.COLUMN_EMAIL + " VARCHAR(50)  " +
            " ); ";

    public void onCreate(SQLiteDatabase db) {

        db.execSQL(sql);
    }
}
