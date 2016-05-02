package xyz.belvi.mail2push;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import xyz.belvi.mail2push.Adapter.ChatAdapter;
import xyz.belvi.mail2push.DB.ChatClient;
import xyz.belvi.mail2push.DB.ChatConstant;
import xyz.belvi.mail2push.GCM_Handler.PushRegistration;
import xyz.belvi.mail2push.Network.SendChat;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    RecyclerView recyclerView;
    ChatAdapter chatAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        new PushRegistration(this);//get push id for device;
        getSupportLoaderManager().initLoader(0, null, this).forceLoad();
        final EditText email = (EditText) findViewById(R.id.recipientAddress);
        final EditText chat = (EditText) findViewById(R.id.chatText);
        findViewById(R.id.send).setEnabled(false);
        chat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.toString().trim().isEmpty()) {
                    findViewById(R.id.send).setEnabled(false);
                } else {
                    findViewById(R.id.send).setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        findViewById(R.id.send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailString = email.getText().toString().trim();
                if (emailString.isEmpty()) {
                    sendMessage(emailString, chat);
                } else {
                    if (ChatUtils.isEmailValid(emailString)) {
                        sendMessage(emailString, chat);
                    } else {
                        showMessage("invalid recipient email address");
                    }
                }
            }
        });


    }

    private void sendMessage(String email, TextView chatView) {
        if (Cache.getGcmKey(this).trim().isEmpty()) {
            showMessage("device not ready to push message yet");
        } else {
            String message = chatView.getText().toString().trim();
            Intent intent = new Intent(this, SendChat.class);
            ChatUtils.buildSendIntent(intent, email, message);
            startService(intent);
            chatView.setText("");
        }
    }

    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(chatBroadcastReceiver, new IntentFilter(ChatConstant.BROADCAST_ACTION_UPDATE));
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(chatBroadcastReceiver);
    }

    BroadcastReceiver chatBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            getSupportLoaderManager().initLoader(0, null, MainActivity.this).forceLoad();
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, ChatClient.getChatClient(this), null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (chatAdapter == null) {
            chatAdapter = new ChatAdapter(this, data);
            recyclerView = (RecyclerView) findViewById(R.id.chat_view);
            recyclerView.setAdapter(chatAdapter);

            recyclerView.setHasFixedSize(true);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(layoutManager);
        } else {
            chatAdapter.swapCursor(data);
        }
        recyclerView.scrollToPosition(chatAdapter.getItemCount() - 1);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
