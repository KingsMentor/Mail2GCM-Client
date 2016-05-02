package xyz.belvi.mail2push.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import xyz.belvi.mail2push.R;

/**
 * Created by zone2 on 5/2/16.
 */
public class ViewHolder extends RecyclerView.ViewHolder {
    public TextView text, time, email, status;

    public ViewHolder(View itemView) {
        super(itemView);
        text = (TextView) itemView.findViewById(R.id.message);
        time = (TextView) itemView.findViewById(R.id.time);
        email = (TextView) itemView.findViewById(R.id.email);
        status = (TextView) itemView.findViewById(R.id.status);
    }
}
