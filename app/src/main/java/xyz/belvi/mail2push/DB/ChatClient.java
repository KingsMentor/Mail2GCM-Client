package xyz.belvi.mail2push.DB;

import android.content.Context;
import android.net.Uri;

import xyz.belvi.mail2push.R;

/**
 * Created by zone2 on 5/1/16.
 */
public class ChatClient {
    public static final String DB_NAME = "CHAT_DB";
    public static final int DB_VERSION = 1;
    public static final String TABLE_NAME = "CHAT_TABLE";
    public static final String _ID = "_id";// id for cursor adapter
    public static final String COLUMN_TEXT = "CHAT_TEXT";//text of chat or ref to content on server
    public static final String COLUMN_EMAIL = "CHAT_EMAIL";//recipient email
    public static final String COLUMN_TIME = "CHAT_TIME";//time chat arrived or was sent
    public static final String COLUMN_STATUS = "CHAT_STATUS";//sent or failed
    public static final String COLUMN_TYPE = "CHAT_TYPE";//sender or receiver

    public static ChatDB chatDB;


    public static Uri getChatClient(Context context) {
        String providerName = "content://" + context.getString(R.string.chat_provider) + "/" + TABLE_NAME;
        return Uri.parse(providerName);
    }
}
