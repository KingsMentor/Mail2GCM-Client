package xyz.belvi.mail2push.DB;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Created by zone2 on 5/1/16.
 */
public class ChatProvider extends ContentProvider {
    @Override
    public boolean onCreate() {
        ChatClient.chatDB = new ChatDB(getContext(), ChatClient.DB_NAME, null, ChatClient.DB_VERSION);
        return false;
    }


    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        if (checkColumns(projection)) {
            SQLiteQueryBuilder sqLiteQueryBuilder = new SQLiteQueryBuilder();
            sqLiteQueryBuilder.setTables(ChatClient.TABLE_NAME);

            SQLiteDatabase sqLiteDatabase = ChatClient.chatDB.getWritableDatabase();
            Cursor cursor = sqLiteQueryBuilder.query(sqLiteDatabase, projection, selection, selectionArgs, null, null, sortOrder);
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
            return cursor;
        }
        return null;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long rowAffected = ChatClient.chatDB.getWritableDatabase().insert(ChatClient.TABLE_NAME, null, values);
        uri = ContentUris.withAppendedId(uri, rowAffected);
        getContext().getContentResolver().notifyChange(uri, null);
        return uri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int rowAffected = ChatClient.chatDB.getWritableDatabase().delete(ChatClient.TABLE_NAME, selection, selectionArgs);
        uri = ContentUris.withAppendedId(uri, rowAffected);
        getContext().getContentResolver().notifyChange(uri, null);
        return rowAffected;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int rowAffected = ChatClient.chatDB.getWritableDatabase().update(ChatClient.TABLE_NAME, values, selection, selectionArgs);
        uri = ContentUris.withAppendedId(uri, rowAffected);
        getContext().getContentResolver().notifyChange(uri, null);
        return rowAffected;
    }

    private boolean checkColumns(String[] projections) {
        if (projections != null) {
            String available[] = {ChatClient.COLUMN_TIME, ChatClient.COLUMN_STATUS,
                    ChatClient.COLUMN_TYPE, ChatClient.COLUMN_TEXT, ChatClient.COLUMN_EMAIL
            };
            HashSet<String> requestedColumn = new HashSet<String>(Arrays.asList(projections));
            HashSet<String> availableColumn = new HashSet<String>(Arrays.asList(available));
            return availableColumn.containsAll(requestedColumn);
        }
        return true;
    }

}