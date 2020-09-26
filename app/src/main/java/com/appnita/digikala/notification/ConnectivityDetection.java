package com.appnita.digikala.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class ConnectivityDetection extends BroadcastReceiver {
    private static final String CHANNEL_ID = "channel_id";

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "sdasdasdasd", Toast.LENGTH_SHORT).show();
    }


}
