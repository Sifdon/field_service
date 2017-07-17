package com.agit.bp.mechanicbp.services;

import android.util.Log;

import com.agit.bp.mechanicbp.database.ConstantaWO;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;


/**
 * Created by NiyatiR on 8/13/2016.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = MyFirebaseInstanceIDService.class.getSimpleName();

    //private SessionManager sessionManager;

    @Override
    public void onTokenRefresh() {
        //Log.e("tai", "tai kucing");
        //sessionManager = new SessionManager(getApplicationContext(), false);

        //Getting registration token
        String refreshedTokenFCM = FirebaseInstanceId.getInstance().getToken();
        sendRegistrationToServer(refreshedTokenFCM);

        //Log.e("tai", "tai kucing");
        if (ConstantaWO.IS_DEBUG) {
            //Displaying token on logcat
            Log.e("token", "Refreshed token : " + refreshedTokenFCM);
        }

    }

    private void sendRegistrationToServer(String tokenFCM) {
        //You can implement this method to store the tokenFCM on your server
        //Not required for current project

        /*String myToken = sessionManager.getToken();
        int userId = sessionManager.getUserId();

        if (sessionManager.isLoggedIn()) {
            try {
                //QWaterClient.getClient().updateTokenFCM(userId, tokenFCM, myToken);
            } catch (Throwable throwable) {

            }
        }*/

    }
}