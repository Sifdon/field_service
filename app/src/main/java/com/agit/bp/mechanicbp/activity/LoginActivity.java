package com.agit.bp.mechanicbp.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.InputType;
import android.text.TextUtils;
import android.transition.Slide;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.agit.bp.mechanicbp.R;
import com.agit.bp.mechanicbp.database.DatabaseHelper;
import com.agit.bp.mechanicbp.models.ModelWOHeader;
import com.agit.bp.mechanicbp.models.RequestUserMechanic;
import com.agit.bp.mechanicbp.models.ResponseNewWO;
import com.agit.bp.mechanicbp.models.ResponseUserMechanic;
import com.agit.bp.mechanicbp.services.RESTService;
import com.agit.bp.mechanicbp.services.ServiceFactory;
import com.agit.bp.mechanicbp.services.SessionManager;
import com.agit.bp.mechanicbp.services.SyncOfflineData;
import com.google.firebase.iid.FirebaseInstanceId;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.READ_PHONE_STATE;

//import com.google.firebase.crash.FirebaseCrash;

/**
 * Created by user on 7/26/2016.
 */

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();

    private DatabaseHelper db;
    private EditText editTextUsername;
    private EditText editTextPassword;
    private Button btnSignIn;
    ProgressDialog mProgressDialog;
    private SessionManager sessionManager;

    private static final int REQUEST_FINE_LOCATION = 1;
    private static final int REQUEST_READ_PHONE_STATE = 1;

    CheckBox showpassword;

    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionManager = new SessionManager(this);
        setContentView(R.layout.activity_login);
        setupWindowAnimations();

        db = new DatabaseHelper(this);

        editTextUsername = (EditText) findViewById(R.id.email);
        editTextPassword = (EditText) findViewById(R.id.password);
        showpassword = (CheckBox) findViewById(R.id.showpassword);
        btnSignIn = (Button) findViewById(R.id.email_sign_in_button);
        //editTextUsername.setText("mekanikbp1");
        //

        showpassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if(checked){
                    editTextPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }else{
                    editTextPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT );
                }
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mayRequestLocation() == true && mayRequestImei() == true) {

                    if (!isLocationEnabled(getApplicationContext())) {
                        //Toast.makeText(LoginActivity.this, "Enable permission first!", Toast.LENGTH_SHORT).show();
                        showLocationWarning("Your location access is off. Enable now?");
                        return;
                    }
                    showProgressDialog();
                    signIn();
                    hideProgressDialog();
                } else {
                    //showWarningSingle("Enable permission first!");
                    Toast.makeText(LoginActivity.this, "Enable permission first!", Toast.LENGTH_SHORT).show();
                }
                //syncDataWO();
            }
        });
    }

    @Override
    public void onBackPressed() {
        // sebelum tombol back di klik
        // melakukan sesuatu di siniiiiiiiiiiiii

        super.onBackPressed();
    }

    public static String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setupWindowAnimations() {
        Slide slide = (Slide) TransitionInflater.from(this).inflateTransition(R.transition.activity_slide);
        getWindow().setExitTransition(slide);
    }

    private void signIn() {
        String username = editTextUsername.getText().toString();
        String password = editTextPassword.getText().toString();
        if (username.equals("")) {
            hideProgressDialog();
            //Toast.makeText(LoginActivity.this, "username tidak boleh kosong", Toast.LENGTH_SHORT).show();
            showWarningSingle("Username may not null!");
            return;
        }
        if (password.equals("")) {
            hideProgressDialog();
            //Toast.makeText(LoginActivity.this, "password tidak boleh kosong", Toast.LENGTH_SHORT).show();
            showWarningSingle("Password may not null!");
            return;
        }
        checklogin(username, password);
    }

    private void checklogin(String username, String password) {
        ResponseUserMechanic user = db.login(username, password);
        if (user != null) {
//            Log.e("user tidak null", "user tidak null");
//            Log.e("staffid :", "" + user.getStaffId());
//            Log.e("staffname :", "" + user.getStaffName());
            sessionManager.login(true, user.getStaffId(), user.getStaffName());
//            Log.e("isi session staffid", sessionManager.getUserId());
            startService(new Intent(this, SyncOfflineData.class));
            syncDataWO();
        } else {
//            Log.e("user null", "user null");
            syncDataUser(username, password);
        }
    }

    private void syncDataUser(String username, String password) {
        //Log.e("masuk syncDataUser", "syncDataUser");

        int permissionCheckRPS = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
        if (permissionCheckRPS != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_READ_PHONE_STATE);

        } else {
            //TODO
        }


        int permissionCheckGL = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionCheckGL != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_FINE_LOCATION);

        } else {
            //TODO
        }

        //get imei device
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String imei_deviceID = tm.getDeviceId();
        Log.e("imei : ", "" + imei_deviceID);
        SharedPreferences preferences;
        String existingToken = "";

        try {
            preferences = PreferenceManager.getDefaultSharedPreferences(this);
            existingToken = FirebaseInstanceId.getInstance().getToken();
            //Log.e("token before", existingToken);
            if (existingToken == null) {
                existingToken = preferences.getString(getString(R.string.firebase_token_id), "");
            }
            //Log.e("token after", existingToken);
        } catch (Exception e) {

        }

        try {
            RequestUserMechanic datarequest = new RequestUserMechanic();
            datarequest.setUsername(username);
            datarequest.setPassword(md5(password));
            datarequest.setApiKey(existingToken);
            Log.e("existingToken", ""+existingToken);
            datarequest.setImei(imei_deviceID);

            RESTService RESTService = ServiceFactory.createRetrofitService(RESTService.class);
            RESTService.login(datarequest).subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<ResponseUserMechanic>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            try {
                                Log.e("server error", e.toString());
                                //Toast.makeText(getApplication(), "Bad Internet Connection...", Toast.LENGTH_SHORT).show();
                                hideProgressDialog();
                            } catch (Exception onError) {
                                Log.e("Exception onError", e.toString());
                            }
                        }

                        @Override
                        public void onNext(ResponseUserMechanic dataresponse) {

                            Log.e("berhasil onnext login", dataresponse.getStatusToken());
                            Log.e("berhasil onnext token", dataresponse.getToken());
                            //Log.e("berhasil staffid", dataresponse.getStaffId());

                            if (dataresponse.getStatusToken().equals("AUTH_OK") || dataresponse.getStatusToken().equals("USER_ALREADY_LOGIN")) {
                                //isi dulu data user untuk insert tabel user
                                ResponseUserMechanic datanewuser = new ResponseUserMechanic();
                                datanewuser.setStaffId(dataresponse.getStaffId());
                                datanewuser.setStaffName(dataresponse.getStaffName());
                                datanewuser.setPhone(dataresponse.getPhone());
                                datanewuser.setPlantcode(dataresponse.getPlantcode());
                                datanewuser.setUsername(username);
                                datanewuser.setPassword(password);
                                datanewuser.setImei(imei_deviceID);
                                datanewuser.setToken(dataresponse.getToken());
                                datanewuser.setStatusToken(dataresponse.getStatusToken());
                                datanewuser.setDateTime(dataresponse.getDateTime());

                                db.writeUserMechanic(datanewuser);
                                checklogin(username, password);
                            } else if (dataresponse.getStatusToken().equals("IMEI_INVALID")) {
                                showWarningSingle("IMEI device is not match!");
                                hideProgressDialog();
                            } else if (dataresponse.getStatusToken().equals("USERNAME_INVALID")) {
                                showWarningSingle("Invalid username!");
                                hideProgressDialog();
                                //Toast.makeText(LoginActivity.this, "Invalid username or password!", Toast.LENGTH_LONG).show();
                            } else if (dataresponse.getStatusToken().equals("PASSWORD_INVALID")) {
                                showWarningSingle("Invalid password!");
                                hideProgressDialog();
                                //Toast.makeText(LoginActivity.this, "Invalid username or password!", Toast.LENGTH_LONG).show();
                            }
//                            else if (dataresponse.getStatusToken().equals("USER_ALREADY_LOGIN")) {
//                                showWarningSingle("User already login!");
//                                hideProgressDialog();
//                            }
                        }
                    });
        } catch (Exception e) {
            Log.e("error login", e.toString());
            hideProgressDialog();
        }
    }

    private void showWarningSingle(String title) {
        //membuat alert dialog

        try {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LoginActivity.this);
            // set title
            alertDialogBuilder.setTitle("Warning");
            // set dialog message
            alertDialogBuilder
                    .setMessage(title)
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_READ_PHONE_STATE:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    //TODO
                }
                break;

            default:
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

//        if (ConstantaWO.IS_DEBUG) {
//            //Log.e("=======================", "================");
//            //Log.e("test", "test");
//            try {
//                final String tokenFCM = FirebaseInstanceId.getInstance().getToken();
//                //Log.e("token resume : ", tokenFCM);
//            } catch (Exception ignored) {
//
//            }
//        }
    }

    private void syncDataWO() {

        try {
            RESTService RESTService = ServiceFactory.createRetrofitService(RESTService.class);
            RESTService.getAllWO(sessionManager.getUserId())
//        RESTService.getNew("STF0005")
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    //.onErrorReturn(error -> "Empty result")
                    .subscribe(new Observer<ResponseNewWO>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            try {
                                Log.e("server error", e.toString());
                                hideProgressDialog();
                                //Toast.makeText(getApplication(), "Bad Internet Connection...", Toast.LENGTH_SHORT).show();
                                Intent orderNew = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(orderNew);
                                finish();
                            } catch (Exception onError) {
                                Log.e("Exception onError", e.toString());
                                hideProgressDialog();
                            }
                        }

                        @Override
                        public void onNext(ResponseNewWO response) {
                            int jumlah = 0;
                            jumlah = response.getResult().size();
                            Log.e("isinya ada : ", "" + jumlah);

                            try {
                                if (response.getStatus().equals("SUCCESS")) {
                                    int size = 0;
                                    size = response.getResult().size();
                                    if (size != 0) {
                                        List<ModelWOHeader> wo = response.getResult();
                                        for (int i = 0; i < size; i++) {
                                            //wo.get(i).setOrderAssignmentId("ASSIGNMENT123");
                                            //Log.e("isi getOrderAssigmentId", "" + wo.get(i).getOrderAssignmentId());
                                            //Log.e("isi getOrderHeaderId", "" + wo.get(i).getOrderHeaderId());
                                            //Log.e("isi getSoNumber", "" + wo.get(i).getSoNumber());
                                            //Log.e("isi getOrderStatus", "" + wo.get(i).getOrderStatus());

                                            if (db.checkIDWO(wo.get(i).getOrderHeaderId()) == false) {
                                                //Log.e("isi size tasklist", "" + wo.get(i).getOrderItemView().size());
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
                                //Toast.makeText(getApplication(), "Failed to insert data : " + throwable, Toast.LENGTH_SHORT).show();
                            } finally {
                                hideProgressDialog();
                                Intent orderNew = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(orderNew);
                                finish();
                            }
                        }

                    });
        } catch (Exception e) {
            Log.e("error login", e.toString());
        }
    }

    private void showLocationWarning(String title) {
        //membuat alert dialog

        try {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LoginActivity.this);
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
                            startActivity(viewIntent);
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
        if (checkSelfPermission(ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //permission_location = true;
            return true;
        }
        if (shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION)) {
            Toast.makeText(LoginActivity.this, "Active your permission first!", Toast.LENGTH_SHORT).show();
        } else {
            requestPermissions(new String[]{ACCESS_FINE_LOCATION}, REQUEST_FINE_LOCATION);
        }
        return false;
    }

    private boolean mayRequestImei() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            //permission_readphonestate = true;
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_PHONE_STATE)) {
            Toast.makeText(LoginActivity.this, "Active your permission first!", Toast.LENGTH_SHORT).show();
        } else {
            requestPermissions(new String[]{READ_PHONE_STATE}, REQUEST_READ_PHONE_STATE);
        }
        return false;
    }

    private void showProgressDialog() {
        try {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("Loading Progress");
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
        } catch (Exception e) {
            //Log.e("error progress dialog", "" + e.toString());
        }
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }
}


//    private void syncDataTableStatus() {
//        if (db.checkTableStatusEmpty() == true) {
//            Toast.makeText(LoginActivity.this, "KOSONG STATUS", Toast.LENGTH_SHORT).show();
//            db.setTableStatus();
//        } else {
//            Toast.makeText(LoginActivity.this, "TIDAK KOSONG STATUS", Toast.LENGTH_SHORT).show();
//        }
//    }
