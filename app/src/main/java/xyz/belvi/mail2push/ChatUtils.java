package xyz.belvi.mail2push;

import android.content.Intent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import xyz.belvi.mail2push.DB.ChatClient;
import xyz.belvi.mail2push.DB.ChatConstant;
import xyz.belvi.mail2push.Network.NetworkResponse;

/**
 * Created by zone2 on 5/2/16.
 */
public class ChatUtils {

    public static Intent buildSendIntent(Intent serviceIntent, String email, String text) {
        serviceIntent.putExtra(ChatConstant.params_EMAIL, email);
        serviceIntent.putExtra(ChatConstant.params_TEXT, text);
        return serviceIntent;
    }

    public static Intent buildResponseIntent(Intent serviceIntent, NetworkResponse response) {
        serviceIntent.setAction(ChatConstant.BROADCAST_ACTION_UPDATE);
        serviceIntent.putExtra(ChatConstant.RESPONSE_CODE, response.getStatusCode());
        serviceIntent.putExtra(ChatConstant.RESPONSE_STRING, response.getResponseString());
        return serviceIntent;
    }

    static Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);


    public static boolean isEmailValid(String email) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.find() && !email.trim().isEmpty();
    }
}
