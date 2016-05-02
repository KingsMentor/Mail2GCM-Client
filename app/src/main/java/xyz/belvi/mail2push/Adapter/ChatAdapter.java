package xyz.belvi.mail2push.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Calendar;

import xyz.belvi.mail2push.DB.ChatClient;
import xyz.belvi.mail2push.DB.ChatConstant;
import xyz.belvi.mail2push.HelperLib.CursorRecyclerViewAdapter;
import xyz.belvi.mail2push.R;
import xyz.belvi.mail2push.TimeUtils;

/**
 * Created by zone2 on 5/2/16.
 */
public class ChatAdapter extends CursorRecyclerViewAdapter<ViewHolder> {

    public ChatAdapter(Context context, Cursor cursor) {
        super(context, cursor);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Cursor cursor) {

        viewHolder.text.setText(getCursorValue(cursor, ChatClient.COLUMN_TEXT));
        viewHolder.email.setText(getCursorValue(cursor, ChatClient.COLUMN_EMAIL));
        viewHolder.status.setText(getMessageStatus(cursor));
        viewHolder.time.setText(getTimeFromTimeStamp(Long.parseLong(getCursorValue(cursor, ChatClient.COLUMN_TIME))));
    }

    @Override
    public int getItemViewType(int position) {
        if (isIncoming(position))
            return ChatConstant.TYPE_RECEIVER;
        return ChatConstant.TYPE_SENDER;
    }

    private boolean isIncoming(int position) {
        if (null != getCursor() && getCursor().moveToPosition(position)) {
            return getCursor().getInt(getCursor().getColumnIndex(ChatClient.COLUMN_TYPE)) == ChatConstant.TYPE_RECEIVER;
        }
        return false;

    }

    private String getTimeFromTimeStamp(long timeInMillis) {
        return TimeUtils.toReadableTime(timeInMillis, true);
    }

    public String getCursorValue(Cursor cursor, String colName) {
        return cursor.getString(cursor.getColumnIndex(colName));
    }

    public String getMessageStatus(Cursor cursor) {
        int status = cursor.getInt(cursor.getColumnIndex(ChatClient.COLUMN_STATUS));
        switch (status) {
            case ChatConstant.STATUS_FAILED:
                return "failed";
            case ChatConstant.STATUS_PENDING:
                return "sending";
            case ChatConstant.STATUS_SENT:
                return "sent";
            default:
                return "";
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = null;
        if (viewType == ChatConstant.TYPE_RECEIVER) {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.incoming, parent, false);
        } else {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.outgoing, parent, false);
        }
        ViewHolder vh = new ViewHolder(itemView);
        return vh;
    }
}
