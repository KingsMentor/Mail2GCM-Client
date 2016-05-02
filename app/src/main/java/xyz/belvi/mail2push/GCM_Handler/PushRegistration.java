package xyz.belvi.mail2push.GCM_Handler;

import android.content.Context;
import android.os.AsyncTask;


import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

import xyz.belvi.mail2push.Cache;
import xyz.belvi.mail2push.R;


/**
 * Created by CrowdStar on 2/16/2015.
 */
public class PushRegistration {
    private Context mContext;



    public PushRegistration(Context context) {
        mContext = context;
        registerInBackground(context);
    }


    private void registerInBackground(final Context context) {
        new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {

                InstanceID instanceID = InstanceID.getInstance(mContext);
                try {
                    String regId = instanceID.getToken(mContext.getString(R.string.gcm_SenderId),
                            GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
                    return regId;
                } catch (IOException e) {
                    return "";
                }


            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                Cache.saveGcmKey(mContext, o.toString());
            }
        }.execute();
    }

}
