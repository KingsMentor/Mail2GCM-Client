package xyz.belvi.mail2push.GCM_Handler;

import com.google.android.gms.iid.InstanceIDListenerService;

/**
 * Created by BELVI-PC on 1/7/2016.
 */
public class GCMInstanceIDListenerService extends InstanceIDListenerService {

    @Override
    public void onTokenRefresh() {
        new PushRegistration(getBaseContext());
    }
}
