package com.agit.bp.mechanicbp.services;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.agit.bp.mechanicbp.R;
import com.agit.bp.mechanicbp.activity.DetailWOActivity;
import com.agit.bp.mechanicbp.activity.MainActivity;
import com.agit.bp.mechanicbp.activity.WriteSignature;
import com.agit.bp.mechanicbp.database.DatabaseHelper;
import com.agit.bp.mechanicbp.fragments.HomeFragment;
import com.agit.bp.mechanicbp.fragments.NotificationFragment;
import com.agit.bp.mechanicbp.fragments.OrderFragment;
import com.agit.bp.mechanicbp.fragments.OrderOnProcessFragment;
import com.agit.bp.mechanicbp.models.ModelWOHeader;
import com.agit.bp.mechanicbp.models.ResponseNewWO;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    ModelWOHeader dataWO = new ModelWOHeader();
    String titleNotification = "";
    boolean validNotif = false;
    DatabaseHelper db = new DatabaseHelper(this);
    Intent intent;
    PendingIntent pendingIntent;

    String App_status = "";
    String App_desc = "";
    String App_orderHeaderId = "";
    int App_orderStatus = 0;
    String App_orderStatusName = "";

    private SessionManager sessionManager;


    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // [START_EXCLUDE]
        // There are two types of messages data messages and notification messages. Data messages are handled
        // here in onMessageReceived whether the app is in the foreground or background. Data messages are the type
        // traditionally used with GCM. Notification messages are only received here in onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages containing both notification
        // and data payloads are treated as notification messages. The Firebase console always sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        // [END_EXCLUDE]
        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
//        Log.e("From : ", "" + remoteMessage.getFrom());
//        Log.e("Test", "remote message : " + remoteMessage.toString());
//        Log.e("Test", "remote message : " + remoteMessage.getNotification().toString());

//        Log.e("action ", "click action : " + remoteMessage.getNotification().getClickAction());

        //Log.e("Test Lagi", remoteMessage.)
        // Check if message contains a data payload.
//        if (remoteMessage.getData().size() > 0) {
//            //Log.e("Message data payload: ", "" + remoteMessage.getData());
//        }
//
//        // Check if message contains a notification payload.
//        if (remoteMessage.getNotification() != null) {
//            //Log.e("Notification Body: ", "" + remoteMessage.getNotification().getBody());
//        }

        RemoteMessage.Notification notification = remoteMessage.getNotification();
        //Log.e("judulnya notif : ", "" + notification.getTitle());
        Map<String, String> data = remoteMessage.getData();
        //Log.e("isi firebasenya itu : ", "" + data.get("result"));
        Log.e("masuk firebase notif", "masukkkkkkkkkkkk");

        titleNotification = notification.getTitle().toString();
        dataWO = null;
        try {
            App_status = data.get("status");
            App_desc = data.get("description");
            App_orderHeaderId = data.get("orderHeaderId");
            App_orderStatus = Integer.parseInt(data.get("orderStatus"));
            App_orderStatusName = data.get("orderStatusName");

            Log.e("App_status", "" + App_status);
            Log.e("App_desc", "" + App_desc);
            Log.e("App_orderHeaderId", "" + App_orderHeaderId);
            Log.e("App_orderStatus", "" + App_orderStatus);
            Log.e("App_orderStatusName", "" + App_orderStatusName);

            if (titleNotification.equals("Approval Admin")) {
                Log.e("Approval Admin", "Approval Admin");

                if (App_status.equals("SUCCESS")) {
                    try {
                        db.updateStatusWOApproval(App_orderHeaderId, App_orderStatus, App_orderStatusName);
                        setNotif();
                        dataWO = db.getRecentStatusWO(App_orderHeaderId).get(0);
                    } catch (Exception e) {
                        dataWO = null;
                    }finally {
                        sendNotification(notification, data);
                    }
                }

            } else if (titleNotification.equals("New Work Order Assignment") || titleNotification.equals("Reassignment")) {
                sessionManager = new SessionManager(this);
                RESTService RESTService = ServiceFactory.createRetrofitService(RESTService.class);
                RESTService.getNew(sessionManager.getUserId())
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        //.onErrorReturn(error -> "Empty result")
                        .subscribe(new Observer<ResponseNewWO>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                try{
                                    //Toast.makeText(getApplication(), "Bad Internet Connection...", Toast.LENGTH_SHORT).show();
                                    dataWO = null;
                                    sendNotification(notification, data);
                                } catch (Exception onError) {
                                    Log.e("Exception onError", e.toString());
                                }
                            }

                            @Override
                            public void onNext(ResponseNewWO response) {
                                try {
                                    if (response.getStatus().equals("SUCCESS")) {
                                        int size = 0;
                                        size = response.getResult().size();
                                        if (size != 0) {
                                            List<ModelWOHeader> wo = response.getResult();
                                            for (int i = 0; i < size; i++) {
                                                if (db.checkIDWO(wo.get(i).getOrderHeaderId()) == false) {
                                                    Log.e("isi size WO", "" + wo.size());
                                                    Log.e("isi size tasklist", "" + wo.get(i).getOrderItemView().size());
                                                    db.writeWO(wo.get(i));
                                                    for (int j = 0; j < wo.get(i).getOrderItemView().size(); j++) {
                                                        db.writeTasklist(wo.get(i).getOrderItemView().get(j));
                                                    }
                                                }
                                            }
                                            setNotif();
                                        }
                                    }
                                } catch (Throwable e) {
                                    Log.e("error insert", e.toString());
                                    //Toast.makeText(getApplication(), "Failed to insert data : " + throwable, Toast.LENGTH_SHORT).show();
                                } finally {
                                    dataWO = db.getRecentStatusWO(App_orderHeaderId).get(0);
                                    sendNotification(notification, data);
                                }
                            }
                        });

            }

        } catch (Exception e) {
            dataWO = null;
            sendNotification(notification, data);
        }
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }


    private void setNotif() {
        Log.e("masuk message", "setNotif");

        if (MainActivity.ClassHome != null) {
            Log.e("isi classmain", "main");
            //OrderFragment.orderFragment.load();
            HomeFragment.homeFragment.refreshCount();
        }

        if (MainActivity.ClassNew != null) {
            Log.e("isi classmain", "main");
            OrderFragment.orderFragment.load();
            //MainActivity.ClassNew.setBadgeCount();
        }
        if (MainActivity.ClassOnProcess != null) {
            Log.e("isi classmain", "onprocess");
            OrderOnProcessFragment.orderOnProcessFragment.load();
            //MainActivity.ClassOnProcess.setBadgeCount();
        }

        if (MainActivity.ClassNotif != null) {
            Log.e("isi classmain", "ClassNotif");
            NotificationFragment.notificationFragment.load();
            //MainActivity.ClassNotif.setBadgeCount();
        }

        if (DetailWOActivity.ClassDetail != null) {
            Log.e("isi classmain", "ClassDetail");
            DetailWOActivity.ClassDetail.setRecentStatus();
            DetailWOActivity.ClassDetail.setBadgeCount();
        }
        if (WriteSignature.ClassVOC != null) {
            Log.e("isi classmain", "ClassVOC");
            WriteSignature.ClassVOC.setBadgeCount();
        }
    }
    /**
     * Create and show a custom notification containing the received FCM message.
     *
     * @param notification FCM notification payload received.
     * @param data         FCM data payload received.
     */
    private void sendNotification(RemoteMessage.Notification notification, Map<String, String> data) {

        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_notif);

        if (dataWO != null) {
            if (titleNotification.equals("New Work Order Assignment")) {
                Log.e("intent New", "intent New");
                intent = new Intent(this, DetailWOActivity.class);
                intent.putExtra("submit", dataWO);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
                //pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
            } else if (titleNotification.equals("Approval Admin")) {
                Log.e("intent approve", "intent approve");
                intent = new Intent(this, DetailWOActivity.class);
                intent.putExtra("submit", dataWO);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
            }
        } else {
            Log.e("intent else", "intent else");
            intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setContentTitle(notification.getTitle())
                .setContentText(notification.getBody())
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentIntent(pendingIntent)
                .setContentInfo(notification.getTitle())
                //.setLargeIcon(icon)
                .setColor(Color.parseColor("#009788"))
                .setSmallIcon(R.drawable.ic_notif);

        try {
            String picture_url = data.get("picture_url");
            if (picture_url != null && !"".equals(picture_url)) {
                URL url = new URL(picture_url);
                Bitmap bigPicture = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                notificationBuilder.setStyle(
                        new NotificationCompat.BigPictureStyle().bigPicture(bigPicture).setSummaryText(notification.getBody())
                );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        notificationBuilder.setDefaults(Notification.DEFAULT_VIBRATE);
        notificationBuilder.setLights(Color.RED, 1000, 300);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());
    }

    //        if (titleNotification.equals("Approval Admin")) {
//            db.updateCountNotif(0, App_orderHeaderId);
//        }
    // [END receive_message]

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */
    private void sendNotification(String messageBody) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("FCM Message")
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

}