package com.agit.bp.mechanicbp.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.agit.bp.mechanicbp.database.DatabaseHelper;
import com.agit.bp.mechanicbp.models.ModelHistoryActivity;
import com.agit.bp.mechanicbp.models.RequestStatusTasklist;
import com.agit.bp.mechanicbp.models.RequestStatusVOC;
import com.agit.bp.mechanicbp.models.RequestStatusWO;
import com.agit.bp.mechanicbp.models.ResponseStatusTasklist;
import com.agit.bp.mechanicbp.models.ResponseStatusWO;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by NiyatiR on 9/13/2016.
 */
public class SyncOfflineData extends Service {

    private DatabaseHelper db;
    private ConverterJSON converter;
    private SessionManager sessionManager;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //performe the deskred task
                //boolean isEnabled = NetworkUtil.getConnectivityStatusBoolean(SyncOfflineData.this);
                Log.e("loop", "loop pertama");

                if (checkConnection()) {
                    db = new DatabaseHelper(SyncOfflineData.this);
                    converter = new ConverterJSON();
                    sessionManager = new SessionManager(SyncOfflineData.this);

                    //delete all true HA
                    int resultdeleteHA = db.deleteTrueHistoryActivity();
                    Log.e("result delete HA", "" + resultdeleteHA);

                    int countHA = db.getCountHistoryActivity();
                    Log.e("size HA", "" + countHA);
                    //if (countHA > 0) {
                    ModelHistoryActivity data = new ModelHistoryActivity();
                    data = db.getFalseHistoryActivity();
                    if (data != null) {
                        Log.e("getId", "" + data.getId());
                        Log.e("getType", "" + data.getType());
                        Log.e("getOrderHeaderId", "" + data.getOrderHeaderId());
                        Log.e("getJson", "" + data.getJson());
                        Log.e("getPath", "" + data.getPath());
                        Log.e("getFlag", "" + data.getFlag());

                        if (data.getType().equals("WO")) {
                            Log.e("masuk type WO", "WO");
                            sendDataWO(data.getId(), data.getOrderHeaderId(), data.getPath(), data.getJson());
                        } else if (data.getType().equals("Tasklist")) {
                            sendDataTasklist(data.getId(), data.getOrderHeaderId(), data.getPath(), data.getJson());
                        } else if (data.getType().equals("VOC")) {
                            sendDataVOC(data.getId(), data.getOrderHeaderId(), data.getPath(), data.getJson());
                        }

                    } else {
                        Log.e("data = null", "data HA");
                    }
                    //}
                }
                handler.postDelayed(this, 60000);
            }
        }, 60000);

        // If we get killed, after returning from here, restart
        return Service.START_STICKY;
    }

    private void sendDataWO(int idHA, String WO_ID, String path, String json) {
        try {
            RequestStatusWO dataRequest = new RequestStatusWO();
            dataRequest = converter.stringtoJsonWO(json);
//            Log.e("getorderAssigmentId", "" + dataRequest.getOrderAssignmentId());
//            Log.e("getOrderHeaderId", "" + dataRequest.getOrderHeaderId());
//            Log.e("getOrderStatus", "" + dataRequest.getOrderStatus());
//            Log.e("getOrderStatusName", "" + dataRequest.getOrderStatusName());
//            Log.e("getActionTime", "" + dataRequest.getActionTime());
//            Log.e("getLatitude", "" + dataRequest.getLatitude());
//            Log.e("getLongitude", "" + dataRequest.getLongitude());
//            Log.e("getMechanicId", "" + dataRequest.getMechanicId());
//            Log.e("getNote", "" + dataRequest.getNote());

            Log.e("isi json", ""+json);

            RESTService RESTService = ServiceFactory.createRetrofitService(RESTService.class);
            RESTService.sentrequest(path, dataRequest)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<ResponseStatusWO>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            try{
                                Log.e("onError SyncofflineData", e.toString());
                            } catch (Exception onError) {
                                Log.e("Exception onError", e.toString());
                            }
                        }

                        @Override
                        public void onNext(ResponseStatusWO dataresponse) {
                            Log.e("isi response HA", ""+dataresponse.getStatus());
                            Log.e("isi response HA", ""+dataresponse.getDescription());
                            if (dataresponse.getStatus().equals("SUCCESS") || dataresponse.getStatus().equals("STATUS_UPDATED")) {
                                Log.e("masuk HA SUCCESS", "SUCCESS");
                                long result = db.updateHistoryActivity(idHA, WO_ID, 1);
                                Log.e("result update HA", "" + result);

                                if(dataresponse.getOrderStatus()== 5069){
                                    long checkdeleteWO = db.deleteWO(WO_ID);
                                }

                            }
                        }
                    });

        } catch (Exception e) {

        }
    }

    private void sendDataTasklist(int idHA, String WO_ID, String path, String json) {
        try {
            RequestStatusTasklist dataRequest = new RequestStatusTasklist();
            dataRequest = converter.stringtoJsonTasklist(json);

            RESTService RESTService = ServiceFactory.createRetrofitService(RESTService.class);
            RESTService.sentrequestTasklist(path, dataRequest)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<ResponseStatusTasklist>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                            try{
                                Log.e("onError SyncofflineData", e.toString());
                            } catch (Exception onError) {
                                Log.e("Exception onError", e.toString());
                            }
                        }

                        @Override
                        public void onNext(ResponseStatusTasklist dataresponse) {
                            Log.e("isi response HA", ""+dataresponse.getStatus());
                            Log.e("isi response HA", ""+dataresponse.getDescription());
                            if (dataresponse.getStatus().equals("SUCCESS") || dataresponse.getStatus().equals("STATUS_UPDATED")) {
                                Log.e("masuk HA SUCCESS", "SUCCESS");
                                long result = db.updateHistoryActivity(idHA, WO_ID, 1);
                                Log.e("result update HA", "" + result);
                            }
                        }
                    });

        } catch (Exception e) {

        }
    }

    private void sendDataVOC(int idHA, String WO_ID, String path, String json) {
        try {
            Log.e("isi json", ""+json);

            RequestStatusVOC dataRequest = new RequestStatusVOC();
            dataRequest = converter.stringtoJsonVOC(json);

            RESTService RESTService = ServiceFactory.createRetrofitService(RESTService.class);
            RESTService.sentrequestFeedback(path, dataRequest)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<ResponseStatusWO>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            try{
                                Log.e("onError SyncofflineData", e.toString());
                            } catch (Exception onError) {
                                Log.e("Exception onError", e.toString());
                            }
                        }

                        @Override
                        public void onNext(ResponseStatusWO dataresponse) {
                            Log.e("isi response HA", ""+dataresponse.getStatus());
                            Log.e("isi response HA", ""+dataresponse.getDescription());
                            if (dataresponse.getStatus().equals("SUCCESS") || dataresponse.getStatus().equals("STATUS_UPDATED")) {
                                Log.e("masuk HA SUCCESS", "SUCCESS");
                                long result = db.updateHistoryActivity(idHA, WO_ID, 1);
                                Log.e("result update HA", "" + result);
                            }
                        }
                    });

        } catch (Exception e) {

        }
    }

    private boolean checkConnection() {
        ConnectivityManager conMgr = (ConnectivityManager) this
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo i = conMgr.getActiveNetworkInfo();
        if (i == null)
            return false;
        if (!i.isConnected())
            return false;
        if (!i.isAvailable())
            return false;
        return true;
    }

}
