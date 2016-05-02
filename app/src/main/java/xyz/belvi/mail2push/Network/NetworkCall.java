package xyz.belvi.mail2push.Network;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;


/**
 * Created by zone2 on 1/15/16.
 */
public class NetworkCall {

    static int counter = 0;

    public static NetworkResponse connect(Context context, String link, String action, Map<Object, Object> params) {


        NetworkResponse networkResponse = new NetworkResponse();
        URL obj = null;
        Log.e("link", link);
        try {
            obj = new URL(link);
            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();

            //add reuqest header
            conn.setRequestMethod(action.trim());
            conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            if (action.trim().equalsIgnoreCase("post")) {
                StringBuilder postData = new StringBuilder();
                if (params != null) {
                    for (Map.Entry<Object, Object> param : params.entrySet()) {
                        if (postData.length() != 0) postData.append('&');
                        postData.append(URLEncoder.encode(param.getKey().toString(), "UTF-8"));
                        postData.append('=');
                        postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
                    }
                }

                Log.e("postdata", postData.toString());

                // Send post request
                conn.setDoOutput(true);
                DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
                wr.writeBytes(postData.toString());
                wr.flush();
                wr.close();
            }

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();


            Log.e("connector response", response.toString());
            networkResponse.setStatusCode(getServerResponseCode(response.toString()));
            networkResponse.setResponseString(response.toString());

//            counter = 0;
            return networkResponse;

        } catch (MalformedURLException e) {
            e.printStackTrace();
//            Log.e("connector  malformed", e.getMessage());
        } catch (ProtocolException e) {
            e.printStackTrace();
//            Log.e("connector protocol", e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
//            Log.e("connector  io", e.getLocalizedMessage());
        }

        //print result

        networkResponse.setStatusCode(HttpURLConnection.HTTP_INTERNAL_ERROR);
        networkResponse.setResponseString("internal Error");
        return networkResponse;

    }

    private static int getServerResponseCode(String response) {
        return Integer.parseInt(response);
    }
}


