package com.agit.bp.mechanicbp.services;

/**
 * Created by NiyatiR on 8/21/2016.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.agit.bp.mechanicbp.database.DatabaseHelper;
import com.agit.bp.mechanicbp.models.ModelHistoryActivity;
import com.agit.bp.mechanicbp.models.RequestStatusWO;
import com.agit.bp.mechanicbp.models.ResponseStatusWO;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class NetworkChangeReceiver extends BroadcastReceiver {

    private DatabaseHelper db;
    private ConverterJSON converter;
    private SessionManager sessionManager;

    @Override
    public void onReceive(final Context context, final Intent intent) {

        boolean isEnabled = NetworkUtil.getConnectivityStatusBoolean(context);
        db = new DatabaseHelper(context);
        converter = new ConverterJSON();
        sessionManager = new SessionManager(context);

        //while (isEnabled) {
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
                    //sendDataWO(data.getPath(), data.getJson());
                } else if (data.getType().equals("Tasklist")) {

                } else if (data.getType().equals("VOC")) {

                }

            }
        //}
    }

    private void sendDataWO(String path, String json) {

        try {

            RequestStatusWO dataRequest = new RequestStatusWO();
            dataRequest = converter.stringtoJsonWO(json);
            Log.e("getOrderHeaderId", "" + dataRequest.getOrderHeaderId());
            Log.e("getOrderStatus", "" + dataRequest.getOrderStatus());
            Log.e("getOrderStatusName", "" + dataRequest.getOrderStatusName());
            Log.e("getActionTime", "" + dataRequest.getActionTime());
            Log.e("getLatitude", "" + dataRequest.getLatitude());
            Log.e("getLongitude", "" + dataRequest.getLongitude());
            Log.e("getMechanicId", "" + dataRequest.getMechanicId());
            Log.e("getNote", "" + dataRequest.getNote());

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

                        }

                        @Override
                        public void onNext(ResponseStatusWO responseStatusWO) {

                        }
                    });

        } catch (Exception e) {

        }
    }

    private void sendDataTasklist() {
        try {

        } catch (Exception e) {

        }
    }

    private void sendDataVOC() {
        try {

        } catch (Exception e) {

        }
    }
}

//Toast.makeText(context, "status : " + isEnabled, Toast.LENGTH_LONG).show();
        /*if (isEnabled) {
            // konek internet

            Log.e("NetworkChangeReceiver", "isEnabled");
            db = new DatabaseHelper(context);
            List<ModelHistoryActivity> data = new ArrayList<>();
            data = db.getListFalseHistoryActivity();
            Log.e("size HA", ""+data.size());
            for(int i = 0; i<data.size(); i++){
                Log.e("getId", "" + data.get(i).getId());
                Log.e("getType", "" + data.get(i).getType());
                Log.e("getOrderHeaderId", "" + data.get(i).getOrderHeaderId());
                Log.e("getJson", "" + data.get(i).getJson());
                Log.e("getPath", "" + data.get(i).getPath());
                Log.e("getFlag", "" + data.get(i).getFlag());
            }
        } else {
            // tidak konek
        }*/