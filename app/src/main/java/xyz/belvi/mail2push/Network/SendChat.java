package xyz.belvi.mail2push.Network;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;


import java.net.HttpURLConnection;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import xyz.belvi.mail2push.Cache;
import xyz.belvi.mail2push.ChatUtils;
import xyz.belvi.mail2push.DB.ChatClient;
import xyz.belvi.mail2push.DB.ChatConstant;
import xyz.belvi.mail2push.DB.ChatOperations;

/**
 * Created by zone2 on 2/10/16.
 */
public class SendChat extends IntentService {


    public SendChat() {
        super("");
    }


    private static NetworkResponse sendChat(Context context, Map<Object, Object> objectsMap, int chatID) {
        String link = ChatConstant.SERVER + ChatConstant.PATH_SEND;
        Log.e("link", link);
        NetworkResponse networkResponse = NetworkCall.connect(context, link, NetworkConstant.POST, objectsMap);
        if (networkResponse.getStatusCode() == HttpURLConnection.HTTP_OK) {
            ChatOperations.updateChat(context, chatID, ChatClient.COLUMN_STATUS, String.valueOf(ChatConstant.STATUS_SENT));
        } else {
            ChatOperations.updateChat(context, chatID, ChatClient.COLUMN_STATUS, String.valueOf(ChatConstant.STATUS_FAILED));
        }
        return new NetworkResponse();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Map<Object, Object> params = new HashMap<>();
        params.put(ChatConstant.params_EMAIL, intent.getStringExtra(ChatConstant.params_EMAIL));
        params.put(ChatConstant.params_TEXT, intent.getStringExtra(ChatConstant.params_TEXT));
        params.put(ChatConstant.params_DEVICE_ID, Cache.getDeviceID(this));
        params.put(ChatConstant.params_GCM_KEY, Cache.getGcmKey(this));
        int id = ChatOperations.saveChat(this, intent.getStringExtra(ChatConstant.params_TEXT)
                , intent.getStringExtra(ChatConstant.params_EMAIL), ChatConstant.TYPE_SENDER, ChatConstant.STATUS_PENDING, Calendar.getInstance().getTimeInMillis());

        Log.e("ID",""+id);
        NetworkResponse response = sendChat(this, params, id);
        ChatUtils.buildResponseIntent(intent, response);
        // update view
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);

    }
}
