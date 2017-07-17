package com.agit.bp.mechanicbp.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by NiyatiR on 10/16/2016.
 */
public class ReceiverStartService extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equalsIgnoreCase(Intent.ACTION_BOOT_COMPLETED)) {
            Intent myIntent1 = new Intent(context, MyFirebaseInstanceIDService.class);
            context.startService(myIntent1);

            Intent myIntent2 = new Intent(context, MyFirebaseMessagingService.class);
            context.startService(myIntent2);

            Intent myIntent3 = new Intent(context, SyncOfflineData.class);
            context.startService(myIntent3);
        }
//        Intent i = new Intent(context, MyAutoStartActivity.class);
//        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(i);

    }
}
