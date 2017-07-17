package com.agit.bp.mechanicbp.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;
import android.transition.TransitionInflater;
import android.util.Log;

import com.agit.bp.mechanicbp.R;
import com.agit.bp.mechanicbp.database.DatabaseHelper;
import com.agit.bp.mechanicbp.models.ModelWOHeader;
import com.agit.bp.mechanicbp.models.ResponseNewWO;
import com.agit.bp.mechanicbp.services.RESTService;
import com.agit.bp.mechanicbp.services.ServiceFactory;
import com.agit.bp.mechanicbp.services.SessionManager;
import com.agit.bp.mechanicbp.services.SyncOfflineData;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by NiyatiR on 9/26/2016.
 */
public class SplashScreen extends AppCompatActivity {

    private SessionManager sessionManager;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        setupWindowAnimations();

        sessionManager = new SessionManager(this);
        db = new DatabaseHelper(this);
        DetailWOActivity.ClassDetail = null;
        WriteSignature.ClassVOC = null;

        if (sessionManager.isLoggedIn()) {
            startService(new Intent(this, SyncOfflineData.class));

            try {
                ModelWOHeader dataWONotif = new ModelWOHeader();

                if (getIntent().getExtras() != null) {
                    //Log.e("masuk intent", "firebase");
                    String orderHeaderId = getIntent().getExtras().getString("orderHeaderId");
                    String orderStatusName = getIntent().getExtras().getString("orderStatusName");
                    String status = getIntent().getExtras().getString("status");
                    String description = getIntent().getExtras().getString("description");
                    int orderStatus = Integer.parseInt(getIntent().getExtras().getString("orderStatus"));

//                    Log.d("orderHeaderId", "" + orderHeaderId);
//                    Log.d("orderStatusName", "" + orderStatusName);
//                    Log.d("status", "" + status);
//                    Log.d("description", "" + description);
//                    Log.d("orderStatus", "" + orderStatus);

                    if (status.equals("SUCCESS")) {
                        if (db.checkIDWO(orderHeaderId) == true) {
                            if (orderStatus == 5055) {
                                db.updateStatusWOApproval(orderHeaderId, orderStatus, orderStatusName);
                            }
                            dataWONotif = db.getRecentStatusWO(orderHeaderId).get(0);
                            Intent intent = new Intent(SplashScreen.this, DetailWOActivity.class);
                            intent.putExtra("submit", dataWONotif);
                            startActivity(intent);
                            finish();
                        } else {
                            syncDataWO(orderHeaderId);
                        }
                    } else {
                        Intent orderNew = new Intent(SplashScreen.this, MainActivity.class);
                        startActivity(orderNew);
                        finish();
                    }
                } else {
                    Intent orderNew = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(orderNew);
                    finish();
                }
                // [END handle_data_extras]
            } catch (Exception e) {
                Intent orderNew = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(orderNew);
                finish();
            }
            // [END handle_data_extras]

            /////////////////////////////////////////////////
        } else {
            Intent orderNew = new Intent(SplashScreen.this, LoginActivity.class);
            startActivity(orderNew);
            finish();
        }
    }


    public void syncDataWO(String orderHeaderId) {
//        Log.e("notif new wo", "lalalala");
//        Log.e("staff", sessionManager.getUserId());
        try {
            RESTService RESTService = ServiceFactory.createRetrofitService(RESTService.class);
            RESTService.getNew(sessionManager.getUserId())
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<ResponseNewWO>() {
                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {
                            try {
                                Log.e("bad connection", e.toString());
                                //Toast.makeText(getApplication(), "Bad Internet Connection...", Toast.LENGTH_LONG).show();
                                Intent orderNew = new Intent(SplashScreen.this, MainActivity.class);
                                startActivity(orderNew);
                                finish();
                            } catch (Exception onError) {
                                Log.e("Exception onError", e.toString());
                            }
                        }

                        @Override
                        public void onNext(ResponseNewWO response) {
                            //Log.e("MASUK ONNEXT notif", response.getStatus());
                            try {
                                if (response.getStatus().equals("SUCCESS")) {
                                    int size = 0;
                                    size = response.getResult().size();
                                    if (size != 0) {
                                        List<ModelWOHeader> wo = response.getResult();
                                        for (int i = 0; i < size; i++) {
                                            if (db.checkIDWO(wo.get(i).getOrderHeaderId()) == false) {
                                                db.writeWO(wo.get(i));
                                                for (int j = 0; j < wo.get(i).getOrderItemView().size(); j++) {
                                                    db.writeTasklist(wo.get(i).getOrderItemView().get(j));
                                                }
                                            }
                                        }
                                    }
                                }
                            } catch (Throwable e) {
                                Log.e("error insert", e.toString());
                            } finally {
                                //Log.e("akhirnya", "masuk finally");
                                if (db.checkIDWO(orderHeaderId) == true) {
                                    //ModelWOHeader dataWONotif = new ModelWOHeader();
                                    //Log.e("masuk ", "true checkIDWO");
                                    //dataWONotif = db.getRecentStatusWO(orderHeaderId).get(0);
                                    Intent intent = new Intent(SplashScreen.this, DetailWOActivity.class);
                                    intent.putExtra("submit", db.getRecentStatusWO(orderHeaderId).get(0));
                                    startActivity(intent);
                                    finish();
                                } else {
                                    //Log.e("masuk checkIDWO", "false");
                                    Intent intent = new Intent(SplashScreen.this, DetailWOActivity.class);
                                    intent.putExtra("submit", db.getRecentStatusWO(orderHeaderId).get(0));
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        }
                    });

        } catch (Exception e) {
            Log.e("error run retrofit", "error close before");
            Intent intent = new Intent(SplashScreen.this, DetailWOActivity.class);
            //intent.putExtra("submit", db.getRecentStatusWO(orderHeaderId).get(0));
            startActivity(intent);
            finish();
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setupWindowAnimations() {
        Fade fade = (Fade) TransitionInflater.from(this).inflateTransition(R.transition.activity_fade);
        getWindow().setEnterTransition(fade);
    }

}
