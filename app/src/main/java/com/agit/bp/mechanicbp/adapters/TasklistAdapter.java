package com.agit.bp.mechanicbp.adapters;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.agit.bp.mechanicbp.R;
import com.agit.bp.mechanicbp.activity.DetailWOActivity;
import com.agit.bp.mechanicbp.database.ConstantaTasklist;
import com.agit.bp.mechanicbp.database.DatabaseHelper;
import com.agit.bp.mechanicbp.models.ModelWOTakslist;
import com.agit.bp.mechanicbp.models.RequestStatusTasklist;
import com.agit.bp.mechanicbp.models.ResponseStatusTasklist;
import com.agit.bp.mechanicbp.services.ConverterJSON;
import com.agit.bp.mechanicbp.services.GoogleLocationService;
import com.agit.bp.mechanicbp.services.RESTService;
import com.agit.bp.mechanicbp.services.ServiceFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.READ_PHONE_STATE;

/**
 * Created by erwin on 6/14/16.
 */
public class TasklistAdapter extends RecyclerView.Adapter<TasklistAdapter.TasklistHolder> {

    private Activity activity;
    private Context mContext;
    private List<ModelWOTakslist> mOrderList;
    int recentStatus = 0;
    int button_sequence = 0;
    int statusID_updateButton = 0;
    String path = "";
    String statusNAME_updateButton = "";
    //ModelWOTakslist modelWOTakslist;
    DatabaseHelper db = null;
    int i = 0;
    ProgressDialog mProgressDialog;

    AlertDialog alertDialog;

    private static final int REQUEST_FINE_LOCATION = 1;
    private static final int REQUEST_READ_PHONE_STATE = 1;

    String actionTime = "";

    ConverterJSON converterJSON;

    private String orderAssignmentId;
    private String orderHeaderId;
    private String mechanicId;

    public TasklistAdapter(Activity activity, List<ModelWOTakslist> mOrderList, String orderAssignmentId, String orderHeaderId, String mechanicId) {
        this.activity = activity;
        this.mOrderList = mOrderList;
        db = new DatabaseHelper(activity);
        converterJSON = new ConverterJSON();
        this.orderAssignmentId = orderAssignmentId;
        this.mechanicId = mechanicId;
        this.orderHeaderId = orderHeaderId;
    }

    @Override
    public TasklistHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_detail_tasklist, parent, false);
        return new TasklistHolder(view);
    }

    @Override
    public void onBindViewHolder(TasklistHolder holder, int position) {
        ModelWOTakslist modelWOTakslist = getItem(position);
        if (modelWOTakslist == null) {
            return;
        }
        holder.loadTaskList(modelWOTakslist, position);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemCount() {
        return mOrderList.size();
    }

    public ModelWOTakslist getItem(int position) {
        i = position;
        if (mOrderList.size() > 0) {
            return mOrderList.get(position);
        }
        return null;
    }

    public class TasklistHolder extends RecyclerView.ViewHolder {

        private TextView tv_tasklistid;
        private TextView tv_statuscomplete;
        private TextView tv_completeat;
        private TextView tv_workinghours;
        private Button btn_complete;

        public TasklistHolder(View v) {
            super(v);
            tv_tasklistid = (TextView) v.findViewById(R.id.tv_tasklistid);
            tv_statuscomplete = (TextView) v.findViewById(R.id.tv_statuscomplete);
            tv_completeat = (TextView) v.findViewById(R.id.tv_completeat);
            //tv_workinghours = (TextView) v.findViewById(R.id.tv_workinghours);
            btn_complete = (Button) v.findViewById(R.id.btn_completetasklist);

        }

        private void syncDataTasklist(Double latitude, Double longitude, int position) {

            statusID_updateButton = ConstantaTasklist.CONSTANT_STATUS[4].getStatus_id();
            statusNAME_updateButton = ConstantaTasklist.CONSTANT_STATUS[4].getStatus_name();
            path = ConstantaTasklist.CONSTANT_STATUS[4].getStatus_path();

            Log.e("isi button status TL", "" + button_sequence);
            // gini caranya
            RequestStatusTasklist dataRequest = new RequestStatusTasklist();
            dataRequest.setOrderAssignmentId(orderAssignmentId);
            dataRequest.setMechanicId(mechanicId);

            dataRequest.setOrderItemId(mOrderList.get(position).getOrderiItemId());
            dataRequest.setLatitude(latitude);
            dataRequest.setLongitude(longitude);
            Date date = new Date();

            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Log.e("isi TL formatter", "" + formatter.format(date).toString());
            //SimpleDateFormat formatter = new SimpleDateFormat("MMM d, yyyy HH:mm:ss");
            dataRequest.setActionTime(formatter.format(date).toString());
            actionTime = formatter.format(date).toString();

            RESTService RESTService = ServiceFactory.createRetrofitService(RESTService.class);
            RESTService.sentrequestTasklist(path, dataRequest).subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<ResponseStatusTasklist>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                            try {
//                                Toast.makeText(activity, "Bad Internet Connection. Sync data later...", Toast.LENGTH_LONG).show();

                                Toast.makeText(mContext, "Failed to Sync Data. Try again Later!", Toast.LENGTH_LONG).show();


//                                Log.e("Start sinkron HA", "HA");
//                                ModelHistoryActivity dataHA = new ModelHistoryActivity();
//                                dataHA.setType("Tasklist");
//                                Log.e("setType", "" + dataHA.getType());
//                                dataHA.setOrderHeaderId(mOrderList.get(position).getOrderHeaderId());
//                                Log.e("setOrderHeaderId", "" + dataHA.getOrderHeaderId());
//                                dataHA.setJson(converterJSON.jsontoStringTasklist(dataRequest));
//                                Log.e("setJson", "" + dataHA.getJson());
//                                dataHA.setPath(path);
//                                Log.e("setPath", "" + dataHA.getPath());
//                                dataHA.setFlag(0);
//                                Log.e("setFlag", "" + dataHA.getFlag());
//                                Log.e("end HA", "**********************************");
//
//                                long checkresult = db.writeHistoryActivity(dataHA);
//                                Log.e("isi insert HA", "" + checkresult);
//
//                                // belum diganti
//                                Log.e("isi ID Tasklist", "" + mOrderList.get(position).getOrderiItemId());
//                                Log.e("isi status id", "" + statusID_updateButton);
//                                Log.e("isi status name", "" + statusNAME_updateButton);
//                                db.updateStatusTasklist(orderHeaderId, mOrderList.get(position).getOrderiItemId(), statusID_updateButton, statusNAME_updateButton, actionTime);
//                                if (activity instanceof DetailWOActivity) {
//                                    ((DetailWOActivity) activity).loadTasklist();
//                                    ((DetailWOActivity) activity).setButtonStatus();
//                                    //}
//                                }
                                hideProgressDialog();
                            } catch (Exception onError) {
                                Log.e("Exception onError", e.toString());
                                hideProgressDialog();
                            }
                        }

                        @Override
                        public void onNext(ResponseStatusTasklist dataresponse) {
                            Log.e("isi JSON tasklist", "" + dataresponse.getStatus());
                            if (dataresponse.getStatus().equals("SUCCESS") || dataresponse.getStatus().equals("STATUS_UPDATED")) {

                                Log.e("masuk sukses TAsync", "masuk sukses TAsync");
                                db.updateStatusTasklist(orderHeaderId, mOrderList.get(position).getOrderiItemId(), dataresponse.getOrderStatus(), dataresponse.getOrderStatusName(), dataresponse.getActionTime() /*actionTime*/);
                                if (activity instanceof DetailWOActivity) {
                                    ((DetailWOActivity) activity).swipeDetailWO();
//                                    ((DetailWOActivity) activity).loadTasklist();
//                                    ((DetailWOActivity) activity).setButtonStatus();
                                }

                                if(dataresponse.getStatus().equals("STATUS_UPDATED")){
                                    Toast.makeText(mContext, "This tasklist has been completed by another mechanic!", Toast.LENGTH_LONG).show();
                                }

                            } else {
                                Toast.makeText(mContext, "Failed to Sync Data. Try again Later!", Toast.LENGTH_LONG).show();

//                                Log.e("Start sinkron HA", "HA");
//                                ModelHistoryActivity dataHA = new ModelHistoryActivity();
//                                dataHA.setType("Tasklist");
//                                Log.e("setType", "" + dataHA.getType());
//                                dataHA.setOrderHeaderId(mOrderList.get(position).getOrderHeaderId());
//                                Log.e("setOrderHeaderId", "" + dataHA.getOrderHeaderId());
//                                dataHA.setJson(converterJSON.jsontoStringTasklist(dataRequest));
//                                Log.e("setJson", "" + dataHA.getJson());
//                                dataHA.setPath(path);
//                                Log.e("setPath", "" + dataHA.getPath());
//                                dataHA.setFlag(0);
//                                Log.e("setFlag", "" + dataHA.getFlag());
//                                Log.e("end HA", "**********************************");
//
//                                long checkresult = db.writeHistoryActivity(dataHA);
//                                Log.e("isi insert HA", "" + checkresult);
//
//                                // belum diganti
//                                Log.e("isi ID Tasklist", "" + mOrderList.get(position).getOrderiItemId());
//                                Log.e("isi status id", "" + statusID_updateButton);
//                                Log.e("isi status name", "" + statusNAME_updateButton);
//                                db.updateStatusTasklist(orderAssignmentId, mOrderList.get(position).getOrderiItemId(), statusID_updateButton, statusNAME_updateButton, actionTime);
//                                if (activity instanceof DetailWOActivity) {
//                                    ((DetailWOActivity) activity).loadTasklist();
//                                    ((DetailWOActivity) activity).setButtonStatus();
//                                }
                            }
                            hideProgressDialog();
                        }
                    });
        }

        private String getDurationString(int seconds) {

            int hours = seconds / 3600;
            int minutes = (seconds % 3600) / 60;
            seconds = seconds % 60;

            return twoDigitString(hours) + " : " + twoDigitString(minutes) + " : " + twoDigitString(seconds);
        }

        private String twoDigitString(int number) {

            if (number == 0) {
                return "00";
            }

            if (number / 10 == 0) {
                return "0" + number;
            }

            return String.valueOf(number);
        }

        private void syncLocation(int position) {
            showProgressDialog();
            try {
                Log.e("masuk syncLocation", "syncLocation TL");
                GoogleLocationService googleLocationService = new GoogleLocationService(activity);
                googleLocationService
                        .getLocation()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<Location>() {
                            @Override
                            public void onCompleted() {
                            }

                            @Override
                            public void onError(Throwable e) {
                                syncDataTasklist(0.0, 0.0, position);
                            }

                            @Override
                            public void onNext(Location location) {
                                syncDataTasklist(location.getLatitude(), location.getLongitude(), position);
                            }
                        });
            } catch (Exception e) {

            }
        }

        private void showProgressDialog() {
            try {
                mProgressDialog = new ProgressDialog(activity);
                mProgressDialog.setMessage("Loading Progress");
                mProgressDialog.setIndeterminate(true);
                mProgressDialog.show();
            } catch (Exception e) {
                //Log.e("error progress dialog", "" + e.toString());
            }
        }

        private void hideProgressDialog() {
            if (/*mProgressDialog != null && */mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
        }

        public void loadTaskList(ModelWOTakslist modelWOTakslist, int position) {
            tv_tasklistid.setText(modelWOTakslist.getTaskList());
            tv_statuscomplete.setText(": " + modelWOTakslist.getOrderItemStatusName());
            Log.e("isi status", "" + modelWOTakslist.getOrderItemStatus());

            if (modelWOTakslist.getOrderItemStatus() == 7004) {
                tv_completeat.setText(": " + modelWOTakslist.getActionDate());
            } else {
                tv_completeat.setText(": —");
            }
            //tv_workinghours.setText(modelWOTakslist.getWorkinghours());

            btn_complete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Log.e("button click adapter", "click adapter");

                    if (mayRequestLocation() == true && mayRequestImei() == true) {

                        if (!isLocationEnabled(activity)) {
                            //Toast.makeText(LoginActivity.this, "Enable permission first!", Toast.LENGTH_SHORT).show();
                            showLocationWarning("Your location access is off. Enable now?");
                            return;
                        }
                    }

//                    if (recentStatus == 5064) {
                    syncLocation(position);
                    // di sini adapternya mau manggil method di class main
                }
            });
            recentStatus = modelWOTakslist.getOrderItemStatus();
            if (recentStatus == 7005) {
                doButton(1);
            } else if (recentStatus == 5064) {
                doButton(2);
            } else if (recentStatus == 5065) {
                doButton(3);
            } else if (recentStatus == 7004) {
                doButton(4);
            } else {
                doButton(1);
            }
        }

        private void doButton(int sequence) {
            Log.e("isi assignment", ""+orderAssignmentId);
            Log.e("isi mechanicid", ""+mechanicId);

            button_sequence = sequence;
            btn_complete.setText(ConstantaTasklist.CONSTANT_STATUS[sequence].getStatus_buttonname());
            if (ConstantaTasklist.CONSTANT_STATUS[sequence].getStatus_buttoncolour().equals("#00796B")) {
                btn_complete.setBackgroundResource(R.drawable.style_button_tasklist_true);
            } else if (ConstantaTasklist.CONSTANT_STATUS[sequence].getStatus_buttoncolour().equals("#fd9f01")) {
                btn_complete.setBackgroundResource(R.drawable.style_button_tasklist_finish);
            } else {
                btn_complete.setBackgroundResource(R.drawable.style_button_tasklist_false);
            }
            btn_complete.setEnabled(ConstantaTasklist.CONSTANT_STATUS[sequence].getStatus_buttonclick());
        }
    }

    private void showLocationWarning(String title) {
        //membuat alert dialog

        try {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
            // set title
            alertDialogBuilder.setTitle("Warning");
            // set dialog message
            alertDialogBuilder
                    .setMessage(title)
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                            Intent viewIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            activity.startActivity(viewIntent);
                        }
                    })
                    .setNegativeButton("Not Now", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {
                            dialog.dismiss();
                        }
                    });
            // create alert dialog
            alertDialog = alertDialogBuilder.create();
            // show it
            alertDialog.show();

        } catch (Exception e) {
            Log.e("error dialog", e.toString());
        }
    }

    public static boolean isLocationEnabled(Context context) {
        int locationMode = 0;
        String locationProviders;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);

            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
            }
            return locationMode != Settings.Secure.LOCATION_MODE_OFF;

        } else {
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }
    }

    private boolean mayRequestLocation() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (activity.checkSelfPermission(ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //permission_location = true;
            return true;
        }
        if (activity.shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION)) {
            Toast.makeText(activity, "Active your permission first!", Toast.LENGTH_SHORT).show();
        } else {
            activity.requestPermissions(new String[]{ACCESS_FINE_LOCATION}, REQUEST_FINE_LOCATION);
        }
        return false;
    }

    private boolean mayRequestImei() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (activity.checkSelfPermission(READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            //permission_readphonestate = true;
            return true;
        }
        if (activity.shouldShowRequestPermissionRationale(READ_PHONE_STATE)) {
            Toast.makeText(activity, "Active your permission first!", Toast.LENGTH_SHORT).show();
        } else {
            activity.requestPermissions(new String[]{READ_PHONE_STATE}, REQUEST_READ_PHONE_STATE);
        }
        return false;
    }
}