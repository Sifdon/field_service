package com.agit.bp.mechanicbp.activity;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.transition.Fade;
import android.transition.TransitionInflater;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.agit.bp.mechanicbp.R;
import com.agit.bp.mechanicbp.database.ConstantaWO;
import com.agit.bp.mechanicbp.database.DatabaseHelper;
import com.agit.bp.mechanicbp.fragments.OrderFragment;
import com.agit.bp.mechanicbp.models.ModelWOHeader;
import com.agit.bp.mechanicbp.models.RequestStatusVOC;
import com.agit.bp.mechanicbp.models.RequestUserMechanic;
import com.agit.bp.mechanicbp.models.ResponseStatusWO;
import com.agit.bp.mechanicbp.models.ResponseUserMechanic;
import com.agit.bp.mechanicbp.services.ConverterJSON;
import com.agit.bp.mechanicbp.services.GoogleLocationService;
import com.agit.bp.mechanicbp.services.RESTService;
import com.agit.bp.mechanicbp.services.ServiceFactory;
import com.agit.bp.mechanicbp.services.SessionManager;
import com.agit.bp.mechanicbp.services.SyncOfflineData;
import com.appeaser.sublimenavigationviewlibrary.OnNavigationMenuEventListener;
import com.appeaser.sublimenavigationviewlibrary.SublimeBaseMenuItem;
import com.appeaser.sublimenavigationviewlibrary.SublimeNavigationView;
import com.github.gcacace.signaturepad.views.SignaturePad;
import com.google.firebase.iid.FirebaseInstanceId;
import com.mikepenz.actionitembadge.library.ActionItemBadge;
import com.mikepenz.actionitembadge.library.utils.NumberUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

import id.zelory.compressor.Compressor;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.READ_PHONE_STATE;

/**
 * Created by NiyatiR on 7/29/2016.
 */
public class WriteSignature extends AppCompatActivity {

    Button btn_write_signature;
    Button btn_submit;
    Button btn_back;
    EditText v_comment;
    EditText v_customername;
    RadioButton mechanic_woresult_satisfy;
    RadioButton mechanic_woresult_notsatisfy;
    RadioButton mechanic_woresult_done;
    RadioButton mechanic_woresult_notyet;
    RadioButton mechanic_score_good;
    RadioButton mechanic_score_fair;
    RadioButton mechanic_score_notgood;
    ImageView img_signature;

    TextView tv_WOID;
    TextView tv_WODate;
    TextView WO_Status;

    //signature github
    final Context context = this;
    private SignaturePad mSignaturePad;
    Bitmap signatureBitmap;
    private Button mClearButton;
    private Button mSaveButton;

    DatabaseHelper db;
    ModelWOHeader dataWO;
    String dataWOID = "ID WO";
    private String path = "";

    String feedback_comment = "";
    String feedback_custname = "";
    String feedback_woresultIsSatisfy = "";
    String feedback_woresultIsDone = "";
    String feedback_scoreMec = "";
    String feedback_signatureImage = "";

    ProgressDialog mProgressDialog;
    AlertDialog alertDialog;

    private static final int REQUEST_FINE_LOCATION = 1;
    private static final int REQUEST_READ_PHONE_STATE = 1;

    private DrawerLayout mDrawerLayout;

    private SessionManager sessionManager;
    //converter JSON
    ConverterJSON converterJSON;
    public static WriteSignature ClassVOC = null;

    //SublimeMenu navbar_header;
    SublimeNavigationView sn_view;
    final String SS_KEY_MENU = "ss.key.menu.2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signature);
        setupWindowAnimations();

        //kill all static variable
        MainActivity.ClassHome = null;
        MainActivity.ClassNotif = null;
        MainActivity.ClassNew = null;
        MainActivity.ClassOnProcess = null;
        DetailWOActivity.ClassDetail = null;
        ClassVOC = WriteSignature.this;

        tv_WOID = (TextView) findViewById(R.id.tv_WOID);
        tv_WODate = (TextView) findViewById(R.id.tv_WODate);
        WO_Status = (TextView) findViewById(R.id.tv_WOSTATUS);


        db = new DatabaseHelper(WriteSignature.this);
        sessionManager = new SessionManager(this);
        converterJSON = new ConverterJSON();

        if (Build.VERSION.SDK_INT >= 21) {
            // Set the status bar to dark-semi-transparentish
//            getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
//                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.menu_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_navigate_before_white_48dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        TextView toolbar_title = (TextView) findViewById(R.id.toolbar_title);

        try {
            dataWO = (ModelWOHeader) getIntent().getExtras().getSerializable("dataWO");
            dataWOID = dataWO.getOrderHeaderId();
            toolbar_title.setText("Voice of Customer");

            //set WO ID
            tv_WOID.setText(dataWO.getSoNumber());
            tv_WODate.setText(dataWO.getOrderDate());
            WO_Status.setText(dataWO.getOrderStatusName());
            Log.e("isi statustitle", "isi : " + dataWOID);

        } catch (Exception e) {

        } finally {
        }

        btn_write_signature = (Button) findViewById(R.id.btn_writesignature);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        //btn_back = (Button) findViewById(R.id.btn_back);

        v_comment = (EditText) findViewById(R.id.et_comments);
        v_customername = (EditText) findViewById(R.id.et_custname);

        mechanic_woresult_satisfy = (RadioButton) findViewById(R.id.rb_satisfy);
        mechanic_woresult_notsatisfy = (RadioButton) findViewById(R.id.rb_notsatisfy);
        mechanic_woresult_done = (RadioButton) findViewById(R.id.rb_done);
        mechanic_woresult_notyet = (RadioButton) findViewById(R.id.rb_notyet);

        mechanic_score_good = (RadioButton) findViewById(R.id.rb_good);
        mechanic_score_fair = (RadioButton) findViewById(R.id.rb_fair);
        mechanic_score_notgood = (RadioButton) findViewById(R.id.rb_notgood);

        img_signature = (ImageView) findViewById(R.id.img_signature);


        //navbar uses
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(SS_KEY_MENU)) {
                //navbar_header = savedInstanceState.getParcelable(SS_KEY_MENU);
            }
        }
        mDrawerLayout = (DrawerLayout) findViewById(R.id.nav_drawer_layout);
        sn_view = (SublimeNavigationView) findViewById(R.id.navigation_view);
        sn_view.setNavigationMenuEventListener(new OnNavigationMenuEventListener() {
            @Override
            public boolean onNavigationMenuEvent(OnNavigationMenuEventListener.Event event, SublimeBaseMenuItem menuItem) {
                String title = menuItem.getTitle().toString();
                //menuItem.setChecked(!menuItem.isChecked());
                //Toast.makeText(MainActivity.this, "nama item : "+id, Toast.LENGTH_SHORT).show();

                if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
                    mDrawerLayout.closeDrawer(Gravity.LEFT);
                }

                if (title.equals("See Report")) {
                    Uri uri = Uri.parse("http://www.google.com"); // missing 'http://' will cause crashed
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                } else if (title.equals("Logout")) {
                    //finish();
                    Toast.makeText(WriteSignature.this, "Anda klik logout", Toast.LENGTH_SHORT).show();
                    showProgressDialog();
                    ResponseUserMechanic user = db.logout(sessionManager.getUserId());
                    if (user != null) {
                        // ada insert variabel session user
                        Log.e("username logout :", "" + user.getUsername());
                        Log.e("password logout :", "" + user.getPassword());
                        logout(user.getUsername(), user.getPassword());
                    } else {
                        Toast.makeText(WriteSignature.this, "Sign Out Later!", Toast.LENGTH_LONG).show();
                    }
                } else if (title.equals("About Application")) {
                    //Toast.makeText(MainActivity.this, "About Application", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(WriteSignature.this, AboutApplication.class);
                    startActivity(intent);
                }
                return true;
            }
        });
        TextView txt = (TextView) sn_view.getHeaderView().findViewById(R.id.tv_navbar_username);
        txt.setText(sessionManager.getName());
        /////////////////////////////////////////////////

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mayRequestLocation() == true && mayRequestImei() == true) {

                    if (!isLocationEnabled(getApplicationContext())) {
                        //Toast.makeText(LoginActivity.this, "Enable permission first!", Toast.LENGTH_SHORT).show();
                        showLocationWarning("Your location access is off. Enable now?");
                        return;
                    }
                }

                getDataFeedback();
            }
        });

        btn_write_signature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(WriteSignature.this, "Hallo", Toast.LENGTH_SHORT).show();
                final Dialog dialog = new Dialog(WriteSignature.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

                View view = getLayoutInflater().inflate(R.layout.activity_popuplayout, null);

                mSignaturePad = (SignaturePad) view.findViewById(R.id.signature_pad);

                mClearButton = (Button) view.findViewById(R.id.clear_button);
                mSaveButton = (Button) view.findViewById(R.id.save_button);

                mSignaturePad.setOnSignedListener(new SignaturePad.OnSignedListener() {
                    @Override
                    public void onStartSigning() {
                        //Toast.makeText(this, "OnStartSigning", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSigned() {
                        mSaveButton.setEnabled(true);
                        mClearButton.setEnabled(true);
                    }

                    @Override
                    public void onClear() {
                        mSaveButton.setEnabled(false);
                        mClearButton.setEnabled(false);
                    }
                });


                mClearButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mSignaturePad.clear();
                    }
                });

                mSaveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        signatureBitmap = mSignaturePad.getSignatureBitmap();
                        img_signature.setVisibility(View.VISIBLE);
                        img_signature.setImageBitmap(signatureBitmap);
                        if (addJpgSignatureToGallery(signatureBitmap)) {
                            Toast.makeText(getApplicationContext(), "Signature saved into the Gallery", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Unable to store the signature", Toast.LENGTH_SHORT).show();
                        }
//                        if (addSvgSignatureToGallery(mSignaturePad.getSignatureSvg())) {
//                            Toast.makeText(getApplicationContext(), "SVG Signature saved into the Gallery", Toast.LENGTH_SHORT).show();
//                        } else {
//                            Toast.makeText(getApplicationContext(), "Unable to store the SVG signature", Toast.LENGTH_SHORT).show();
//                        }
                        dialog.dismiss();
                    }
                });
                dialog.setContentView(view);
                dialog.show();

            }
        });
    }

    Menu menu2 = null;
    boolean showItem = false;
    public void setBadgeCount() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if(menu2!=null){
                    int count = db.countNotif();

                    if (count != 0) {
                        Log.e("masuk count!=0 Write", "" + showItem);
                        showItem = true;
                        ActionItemBadge.update(WriteSignature.this, menu2.findItem(R.id.action_notifications), ContextCompat.getDrawable(WriteSignature.this, R.drawable.ic_notifications_active_white_24dp), ActionItemBadge.BadgeStyles.RED, NumberUtils.formatNumber(count));

                    } else {
                        Log.e("masuk count==0 Write", "" + showItem);
                        if (showItem == true) {
                            ActionItemBadge.update(WriteSignature.this, menu2.findItem(R.id.action_notifications), ContextCompat.getDrawable(WriteSignature.this, R.drawable.ic_notifications_active_white_24dp), ActionItemBadge.BadgeStyles.RED, Integer.MIN_VALUE);
                            showItem = false;
                        }
                    }
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        menu2 = menu;
        setBadgeCount();
        return super.onCreateOptionsMenu(menu);
        //return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_notifications) {
//            Intent orderNew = new Intent(this, MainActivity.class);
//            startActivity(orderNew);
//            finish();

            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("status", "notification");
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

            return true;
        } else if (id == android.R.id.home) {
            //setResult(Activity.RESULT_OK, new Intent());
            if(OrderFragment.orderFragment != null ){
                // Toast.makeText(getApplicationContext(), "orderFragment ada", Toast.LENGTH_SHORT).show();
                finish();
            }else{
                //Toast.makeText(getApplicationContext(), "orderFragment tidak ada", Toast.LENGTH_SHORT).show();
                //        Log.e("masuk on back", "bye");
                Intent intent = new Intent(WriteSignature.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void logout(String username, String password) {
        //Log.e("masuk syncDataUser", "syncDataUser");

        //get imei device
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String imei_deviceID = tm.getDeviceId();
        Log.e("imei : ", "" + imei_deviceID);
        SharedPreferences preferences;
        String existingToken = "";

        try {
            preferences = PreferenceManager.getDefaultSharedPreferences(this);
            existingToken = FirebaseInstanceId.getInstance().getToken();
            Log.e("token before", existingToken);
            if (existingToken == null) {
                existingToken = preferences.getString(getString(R.string.firebase_token_id), "");
            }
            Log.e("token after", existingToken);
        } catch (Exception e) {

        }

        RequestUserMechanic datarequest = new RequestUserMechanic();
        datarequest.setUsername(username);
        datarequest.setPassword(md5(password));
        datarequest.setApiKey(existingToken);
        datarequest.setImei(imei_deviceID);

        RESTService RESTService = ServiceFactory.createRetrofitService(RESTService.class);
        RESTService.logout(datarequest).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                //.onErrorReturn(error -> "Empty result")
                .subscribe(new Observer<ResponseUserMechanic>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                        try{
                            Toast.makeText(WriteSignature.this, "Failed to sign out. Try again later!", Toast.LENGTH_LONG).show();
                            Log.e("error koneksi logout", e.toString());
                            hideProgressDialog();

                        }catch (Exception onError){
                            Log.e("Exception onError", e.toString());
                            hideProgressDialog();
                        }
                    }

                    @Override
                    public void onNext(ResponseUserMechanic dataresponse) {

                        Log.e("berhasil onnext login", dataresponse.getStatusToken());
                        if (dataresponse.getStatusToken().equals("LOGOUT SUCCESS")) {
                            Log.e("berhasil onnext login", "MASUK IF");
                            long result = db.deleteUserMechanic(sessionManager.getUserId());
                            if (result == 1) {
                                db.deleteAllWO();
                                sessionManager.logout();

                                stopService(new Intent(WriteSignature.this, SyncOfflineData.class));
                                MainActivity.ClassHome = null;
                                MainActivity.ClassNotif = null;
                                MainActivity.ClassNew = null;
                                MainActivity.ClassOnProcess = null;
                                DetailWOActivity.ClassDetail = null;
                                ClassVOC = null;

                                Intent intent = new Intent(WriteSignature.this, LoginActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                //finish();
                            }

                        }else if(dataresponse.getStatusToken().equals("DATA_ALREADY_CLEARED")){
                            Log.e("berhasil onnext login", "DATA_ALREADY_CLEARED");
                            long result = db.deleteUserMechanic(sessionManager.getUserId());
                            if (result == 1) {
                                db.deleteAllWO();
                                sessionManager.logout();

                                stopService(new Intent(WriteSignature.this, SyncOfflineData.class));
                                DetailWOActivity.ClassDetail = null;
                                WriteSignature.ClassVOC = null;

                                Intent intent = new Intent(WriteSignature.this, LoginActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }

                        } else {
                            Toast.makeText(WriteSignature.this, "Sign Out Later!", Toast.LENGTH_LONG).show();
                        }
                        hideProgressDialog();
                    }
                });
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

    public File getAlbumStorageDir(String albumName) {
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStorageDirectory(), albumName);
        if (!file.mkdirs()) {
            Log.e("SignaturePad", "Directory not created");
        }
        return file;
    }

    public void saveBitmapToJPG(Bitmap bitmap, File photo) throws IOException {
        Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newBitmap);
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(bitmap, 0, 0, null);
        OutputStream stream = new FileOutputStream(photo);
        newBitmap.compress(Bitmap.CompressFormat.PNG, 80, stream);
        stream.close();
    }

    public boolean addJpgSignatureToGallery(Bitmap signature) {
        boolean result = false;
        try {
            File photo = new File(getAlbumStorageDir("SignaturePad"), String.format("signature_%d.png", System.currentTimeMillis()));
            saveBitmapToJPG(signature, photo);
            scanMediaFile(photo);
            path = photo.getAbsolutePath();
            System.out.println("lokasi path : " + path);
            img_signature.setVisibility(View.VISIBLE);
            img_signature.setImageBitmap(signature);
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private void scanMediaFile(File photo) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri contentUri = Uri.fromFile(photo);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    public boolean addSvgSignatureToGallery(String signatureSvg) {
        boolean result = false;
        try {
            File svgFile = new File(getAlbumStorageDir("SignaturePad"), String.format("signature_%d.svg", System.currentTimeMillis()));
            OutputStream stream = new FileOutputStream(svgFile);
            OutputStreamWriter writer = new OutputStreamWriter(stream);
            writer.write(signatureSvg);
            writer.close();
            stream.flush();
            stream.close();
            scanMediaFile(svgFile);
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private void getDataFeedback() {
        showProgressDialog();

        if (mechanic_woresult_satisfy.isChecked()) {
            feedback_woresultIsSatisfy = mechanic_woresult_satisfy.getText().toString();
        }
        if (mechanic_woresult_notsatisfy.isChecked()) {
            feedback_woresultIsSatisfy = mechanic_woresult_notsatisfy.getText().toString();
        }
        if (mechanic_woresult_done.isChecked()) {
            feedback_woresultIsDone = mechanic_woresult_done.getText().toString();
        }
        if (mechanic_woresult_notyet.isChecked()) {
            feedback_woresultIsDone = mechanic_woresult_notyet.getText().toString();
        }
        if (mechanic_score_good.isChecked()) {
            feedback_scoreMec = mechanic_score_good.getText().toString();
        }
        if (mechanic_score_fair.isChecked()) {
            feedback_scoreMec = mechanic_score_fair.getText().toString();
        }
        if (mechanic_score_notgood.isChecked()) {
            feedback_scoreMec = mechanic_score_notgood.getText().toString();
        }

        feedback_comment = v_comment.getText().toString();
        feedback_custname = v_customername.getText().toString();

        //ngurus Base64
        if (signatureBitmap != null && !feedback_custname.equals("")) {
            File f = new File(context.getCacheDir(), "tmp.png");
            try {
                f.createNewFile();
                //Convert bitmap to byte array
                Bitmap bitmap = signatureBitmap;
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
                byte[] bitmapdata = bos.toByteArray();

                //write the bytes in file
                FileOutputStream fos = new FileOutputStream(f);
                fos.write(bitmapdata);

                Bitmap compressBitmap = new Compressor.Builder(this)
                        .setMaxWidth(200)
                        .setMaxHeight(200)
                        .build()
                        .compressToBitmap(f);
                bos = new ByteArrayOutputStream();

                // sekarang gini :
                compressBitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);

                byte[] byteArray = bos.toByteArray();
                feedback_signatureImage = Base64.encodeToString(byteArray, Base64.DEFAULT);

                fos.flush();
                fos.close();
                //log.e
//                Log.e("woresultIsSatisfy", "" + feedback_woresultIsSatisfy);
//                Log.e("woresultIsDone", "" + feedback_woresultIsDone);
//                Log.e("scoreMec", "" + feedback_scoreMec);
//                Log.e("comment", "" + feedback_comment);
//                Log.e("custname", "" + feedback_custname);
//                Log.e("signatureImage", "isi : " + feedback_signatureImage);
//                Log.e("signatureImage", "panjang : " + feedback_signatureImage.length());
                syncLocation();
            } catch (IOException ignored) {
            }
        } else {
            Toast.makeText(WriteSignature.this, "Fill recipient name and write signature first!", Toast.LENGTH_LONG).show();
            hideProgressDialog();
        }
    }

    private void syncLocation() {
        Log.e("masuk syncLocation", "syncLocation Feedback");

        try {
            GoogleLocationService googleLocationService = new GoogleLocationService(WriteSignature.this);
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
                            syncDataWO(0.0, 0.0);
                        }

                        @Override
                        public void onNext(Location location) {
                            syncDataWO(location.getLatitude(), location.getLongitude());
                        }
                    });
        } catch (Exception e) {

        }
    }

    private void syncDataWO(Double latitude, Double longitude) {

        int statusID = ConstantaWO.CONSTANT_STATUS[28].getStatus_id();
        String statusNAME = ConstantaWO.CONSTANT_STATUS[28].getStatus_name();
        String path = ConstantaWO.CONSTANT_STATUS[28].getStatus_path();

        RequestStatusVOC dataRequest = new RequestStatusVOC();
        dataRequest.setOrderAssignmentId(dataWO.getOrderAssignmentId());
        dataRequest.setOrderHeaderId(dataWO.getOrderHeaderId());
        dataRequest.setLatitude(latitude);
        dataRequest.setLongitude(longitude);
        dataRequest.setOrderStatus(dataWO.getOrderStatus());
        dataRequest.setOrderStatusName(dataWO.getOrderStatusName());
        dataRequest.setMechanicId(dataWO.getMechanicId());
        Date date = new Date();

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Log.e("isi formatter", ""+formatter.format(date).toString());
        //SimpleDateFormat formatter = new SimpleDateFormat("MMM d, yyyy HH:mm:ss");
        dataRequest.setActionTime(formatter.format(date).toString());
        dataRequest.setNote("");
        dataRequest.setIsSatisfyWO(feedback_woresultIsSatisfy);
        dataRequest.setIsDoneWO(feedback_woresultIsDone);
        dataRequest.setMechanicScore(feedback_scoreMec);
        dataRequest.setRecipientComment(feedback_comment);
        dataRequest.setRecipientName(feedback_custname);
        dataRequest.setSignatureImage(feedback_signatureImage);

        try {
            RESTService RESTService = ServiceFactory.createRetrofitService(RESTService.class);
            //RESTService.sentrequest(path + ".php", dataRequest)
            RESTService.sentrequestFeedback(path, dataRequest).subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<ResponseStatusWO>() {
                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {

                            try{
                                Log.e("onError", e.toString());
                                Toast.makeText(WriteSignature.this, "Failed to Sync Data. Try again Later!", Toast.LENGTH_LONG).show();

//                                Toast.makeText(WriteSignature.this, "Bad Internet Connection. Sync data later...", Toast.LENGTH_LONG).show();
//                                Log.e("Start sinkron HA", "HA");
//                                ModelHistoryActivity dataHA = new ModelHistoryActivity();
//                                dataHA.setType("VOC");
//                                Log.e("setType", "" + dataHA.getType());
//                                dataHA.setOrderHeaderId(dataWO.getOrderHeaderId());
//                                Log.e("setOrderHeaderId", "" + dataHA.getOrderHeaderId());
//                                dataHA.setJson(converterJSON.jsontoStringVOC(dataRequest));
//                                Log.e("setJson", "" + dataHA.getJson());
//                                dataHA.setPath(path);
//                                Log.e("setPath", "" + dataHA.getPath());
//                                dataHA.setFlag(0);
//                                Log.e("setFlag", "" + dataHA.getFlag());
//                                Log.e("end HA", "**********************************");
//
//                                long checkresult = db.writeHistoryActivity(dataHA);
//                                Log.e("isi insert HA", "" + checkresult);
//                                db.updateStatusWO(dataWO.getOrderHeaderId(), statusID, statusNAME);
//                                dataWO = db.getRecentStatusWO(dataWOID).get(0);
//                                Intent intent = new Intent(WriteSignature.this, DetailWOActivity.class);
//                                intent.putExtra("submit", dataWO);
//                                startActivity(intent);

                                hideProgressDialog();
                                finish();
                            }catch (Exception onError){
                                Log.e("Exception onError", e.toString());
                                hideProgressDialog();
                            }
                        }

                        @Override
                        public void onNext(ResponseStatusWO data) {
                            Log.e("ini masuk onnext", "onnext");
                            Log.e("isi deskripsi : ", data.getDescription().toString());
                            Log.e("isi path : ", "" + path);
                            //Log.e("isi status name : ", data.getOrderStatusName().toString());
                            if (data.getStatus().equals("SUCCESS") || data.getStatus().equals("STATUS_UPDATED")) {
                                Log.e("masuk onnext sukses", "onnext sukses");
                                db.updateStatusWO(dataWO.getOrderHeaderId(), data.getOrderStatus(), data.getOrderStatusName());
                                hideProgressDialog();
                                dataWO = db.getRecentStatusWO(dataWOID).get(0);
                                Intent intent = new Intent(WriteSignature.this, DetailWOActivity.class);
                                intent.putExtra("submit", dataWO);
                                startActivity(intent);
                                finish();
                            } else {
                                hideProgressDialog();
                                Log.e("masuk gagal sync", "masuk gagal sync");
                                Toast.makeText(WriteSignature.this, "Failed to Sync Data. Try again Later!", Toast.LENGTH_LONG).show();
//                                ModelHistoryActivity dataHA = new ModelHistoryActivity();
//                                dataHA.setType("VOC");
//                                Log.e("setType", "" + dataHA.getType());
//                                dataHA.setOrderHeaderId(dataWO.getOrderHeaderId());
//                                Log.e("setOrderHeaderId", "" + dataHA.getOrderHeaderId());
//                                dataHA.setJson(converterJSON.jsontoStringVOC(dataRequest));
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
//                                db.updateStatusWO(dataWO.getOrderHeaderId(), statusID, statusNAME);
                            }
                        }
                    });
        } catch (Exception e) {

        }
    }

    private void showProgressDialog() {
        try {
            if (mProgressDialog == null) {
                mProgressDialog = new ProgressDialog(this);
                mProgressDialog.setMessage("Loading Progress");
                mProgressDialog.setIndeterminate(true);
                mProgressDialog.setCancelable(false);
            }
            mProgressDialog.show();
        } catch (Exception e) {
            Log.e("error progress dialog", "" + e.toString());
        }
    }

    private void hideProgressDialog() {
        //mProgressDialog != null
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setupWindowAnimations() {
        Fade fade = (Fade) TransitionInflater.from(this).inflateTransition(R.transition.activity_fade);
        getWindow().setEnterTransition(fade);
    }

    @Override
    public void onBackPressed() {
        if(OrderFragment.orderFragment != null ){
            // Toast.makeText(getApplicationContext(), "orderFragment ada", Toast.LENGTH_SHORT).show();
            finish();
        }else{
            //Toast.makeText(getApplicationContext(), "orderFragment tidak ada", Toast.LENGTH_SHORT).show();
            //        Log.e("masuk on back", "bye");
            Intent intent = new Intent(WriteSignature.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (alertDialog != null) {
            alertDialog.dismiss();
        }

        MainActivity.ClassHome = null;
        MainActivity.ClassNotif = null;
        MainActivity.ClassNew = null;
        MainActivity.ClassOnProcess = null;
        DetailWOActivity.ClassDetail = null;
        ClassVOC = null;
    }


    private void showLocationWarning(String title) {
        //membuat alert dialog

        try {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(WriteSignature.this);
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
            Toast.makeText(WriteSignature.this, "Active your permission first!", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(WriteSignature.this, "Active your permission first!", Toast.LENGTH_SHORT).show();
        } else {
            requestPermissions(new String[]{READ_PHONE_STATE}, REQUEST_READ_PHONE_STATE);
        }
        return false;
    }

}
