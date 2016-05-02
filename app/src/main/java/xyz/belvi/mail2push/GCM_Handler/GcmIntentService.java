package xyz.belvi.mail2push.GCM_Handler;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;


import com.google.android.gms.gcm.GcmListenerService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.List;

import xyz.belvi.mail2push.DB.ChatConstant;
import xyz.belvi.mail2push.DB.ChatOperations;


/**
 * Created by CrowdStar on 2/16/2015.
 */
public class GcmIntentService extends GcmListenerService {

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     * Used to name the worker thread, important only for debugging.
     */


    @Override
    public void onMessageReceived(String from, Bundle data) {
        super.onMessageReceived(from, data);
        String text = data.getString("message");
        String sender = data.getString("sender");
        ChatOperations.saveChat(this, text, sender, ChatConstant.TYPE_RECEIVER, ChatConstant.STATUS_RECEIVED, Calendar.getInstance().getTimeInMillis());
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(ChatConstant.BROADCAST_ACTION_UPDATE));


    }
}