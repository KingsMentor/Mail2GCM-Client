package xyz.belvi.mail2push.DB;

/**
 * Created by zone2 on 5/2/16.
 */
public class ChatConstant {

    //for type
    public static final int TYPE_SENDER = 0;
    public static final int TYPE_RECEIVER = 1;
    //for status
    public static final int STATUS_SENT = 0;
    public static final int STATUS_FAILED = 1;
    public static final int STATUS_PENDING = 2;
    public static final int STATUS_RECEIVED = 3;

    //keys and params

    public static final String params_EMAIL = "email";
    public static final String params_GCM_KEY = "push_key";
    public static final String params_TEXT = "message";
    public static final String params_DEVICE_ID = "device_id";

    //link


    public static final String SERVER = "http://www.email2gcm.appspot.com";
    public static final String PATH_SEND = "/contact";

    //broadcast action
    public static final String BROADCAST_ACTION_UPDATE = "xyz.belvi.mail2push.localBroadcast.chat.update";

    //network info
    public static final String RESPONSE_STRING = "RESPONSE_STRING";
    public static final String RESPONSE_CODE = "RESPONSE_CODE";

}
