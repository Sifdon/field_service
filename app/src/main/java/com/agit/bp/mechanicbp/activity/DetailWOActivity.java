package com.agit.bp.mechanicbp.activity;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.LayerDrawable;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.transition.Fade;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.agit.bp.mechanicbp.R;
import com.agit.bp.mechanicbp.adapters.TasklistAdapter;
import com.agit.bp.mechanicbp.database.ConstantaWO;
import com.agit.bp.mechanicbp.database.DatabaseHelper;
import com.agit.bp.mechanicbp.fragments.OrderFragment;
import com.agit.bp.mechanicbp.models.ModelWOHeader;
import com.agit.bp.mechanicbp.models.ModelWOTakslist;
import com.agit.bp.mechanicbp.models.RequestStatusWO;
import com.agit.bp.mechanicbp.models.RequestUserMechanic;
import com.agit.bp.mechanicbp.models.ResponseNewWO;
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
import com.google.firebase.iid.FirebaseInstanceId;
import com.h6ah4i.android.widget.advrecyclerview.expandable.RecyclerViewExpandableItemManager;
import com.mikepenz.actionitembadge.library.ActionItemBadge;
import com.mikepenz.actionitembadge.library.utils.NumberUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.READ_PHONE_STATE;

/**
 * Created by NiyatiR on 8/9/2016.
 */
public class DetailWOActivity extends AppCompatActivity implements
        RecyclerViewExpandableItemManager.OnGroupCollapseListener,
        RecyclerViewExpandableItemManager.OnGroupExpandListener {

    //recyclerviewer
    private static final String SAVED_STATE_EXPANDABLE_ITEM_MANAGER = "RecyclerViewExpandableItemManager";
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mWrappedAdapter;
    private RecyclerViewExpandableItemManager mRecyclerViewExpandableItemManager;
    private RecyclerView recyclerViewTaskList;

    //class call

    //no use
    private ActionBarDrawerToggle mDrawerToggle;
    public LayerDrawable newOrderMenu;

    //model class
    private List<ModelWOTakslist> orderList;
    private TasklistAdapter orderAdapter;

    //SublimeMenu navbar_header;
    SublimeNavigationView sn_view;
    private DrawerLayout mDrawerLayout;
    final String SS_KEY_MENU = "ss.key.menu.2";

    //object layout
    TextView tv_WOID;
    TextView tv_WODate;
    TextView WO_STATUS;
    Button btn_1;
    Button btn_2;
    LinearLayout bottomContainerTwoButtons;
    CardView btnCusInfo;
    CardView btnWOInfo;

    Button btn_dummy;

    ProgressDialog mProgressDialog;

    private static final int REQUEST_FINE_LOCATION = 1;
    private static final int REQUEST_READ_PHONE_STATE = 1;

    //database uses
    private DatabaseHelper db;
    ModelWOHeader dataWO;
    ///////////
    String WOID = "";
    int button1_sequence = 0;
    int button2_sequence = 0;
    String buttonclicked = "";
    int recentStatus = 0;
    int statusID_updateButton = 0;
    String statusNAME_updateButton = "";
    String path = "";
    String note = "";

    //converter JSON
    ConverterJSON converterJSON;

    private SessionManager sessionManager;
    AlertDialog alertDialog;

    //setting count bagde
    public static DetailWOActivity ClassDetail = null;

    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailwo);
        setupWindowAnimations();

        //declaration object layout
        btn_1 = (Button) findViewById(R.id.btn_1);
        btn_2 = (Button) findViewById(R.id.btn_2);
        btnCusInfo = (CardView) findViewById(R.id.btnCusInfo);
        btnWOInfo = (CardView) findViewById(R.id.btnWOInfo);
        bottomContainerTwoButtons = (LinearLayout) findViewById(R.id.bottomContainerTwoButtons);
        tv_WOID = (TextView) findViewById(R.id.tv_WOID);
        tv_WODate = (TextView) findViewById(R.id.tv_WODate);
        WO_STATUS = (TextView) findViewById(R.id.tv_WOSTATUS);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this::swipeDetailWO);
        //Log.d("swiperefresh", String.valueOf(swipeRefreshLayout));

        // ini yang bikin loadingnya ada diatas
        //swipeRefreshLayout.setRefreshing(true);

        //recyclerviewer
        recyclerViewTaskList = (RecyclerView) findViewById(R.id.list_tasklist);
        recyclerViewTaskList.clearFocus();


        db = new DatabaseHelper(DetailWOActivity.this);
        converterJSON = new ConverterJSON();
        sessionManager = new SessionManager(this);


        //kill all static variable
        MainActivity.ClassHome = null;
        MainActivity.ClassNotif = null;
        MainActivity.ClassNew = null;
        MainActivity.ClassOnProcess = null;
        ClassDetail = DetailWOActivity.this;
        WriteSignature.ClassVOC = null;

        //btn_dummy = (Button) findViewById(R.id.btn_popup);

        //toolbar navbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            //toolbar.setNavigationIcon(R.drawable.ic_menu_24dp);
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_navigate_before_white_48dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        TextView toolbar_title = (TextView) findViewById(R.id.toolbar_title);

        try {
            dataWO = (ModelWOHeader) getIntent().getExtras().getSerializable("submit");

            WOID = dataWO.getOrderHeaderId();

            db.updateCountNotif(0, dataWO.getOrderHeaderId());

            //Log.e("isi statustitle", "isi : " + WOID);

            //set WO ID
            tv_WOID.setText(dataWO.getSoNumber());
            tv_WODate.setText(dataWO.getOrderDate());
            toolbar_title.setText("Detail WO");
            WO_STATUS.setText(dataWO.getOrderStatusName());

            RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(this);
            recyclerViewTaskList.setLayoutManager(mLayoutManager1);
            recyclerViewTaskList.setNestedScrollingEnabled(false);
            recyclerViewTaskList.setHasFixedSize(false);
            orderList = new ArrayList<>();

        } catch (Exception e) {
        } finally {
            setButtonStatus();
            loadTasklist();
        }

        if (Build.VERSION.SDK_INT >= 21) {
            // Set the status bar to dark-semi-transparentish
//            getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
//                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

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
                    //Toast.makeText(DetailWOActivity.this, "Anda klik logout", Toast.LENGTH_SHORT).show();
                    showProgressDialog();
                    ResponseUserMechanic user = db.logout(sessionManager.getUserId());
                    if (user != null) {
                        // ada insert variabel session user
                        Log.e("username logout :", "" + user.getUsername());
                        Log.e("password logout :", "" + user.getPassword());
                        logout(user.getUsername(), user.getPassword());
                    } else {
                        //Toast.makeText(DetailWOActivity.this, "Sign Out Later!", Toast.LENGTH_LONG).show();
                        showWarningSingle("Failed sign out. Try sign out later!");
                    }
                } else if (title.equals("About Application")) {
                    //Toast.makeText(MainActivity.this, "About Application", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(DetailWOActivity.this, AboutApplication.class);
                    startActivity(intent);
                }
                return true;
            }
        });
        TextView txt = (TextView) sn_view.getHeaderView().findViewById(R.id.tv_navbar_username);
        txt.setText(sessionManager.getName());
        ///////////////////////////////////////////

        btnCusInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(DetailWOActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                View view = getLayoutInflater().inflate(R.layout.item_child, null);
                TextView tv_custname = (TextView) view.findViewById(R.id.tv_custname);
                TextView tv_address = (TextView) view.findViewById(R.id.tv_address);
                TextView tv_contactperson = (TextView) view.findViewById(R.id.tv_contactperson);
                TextView tv_phone = (TextView) view.findViewById(R.id.tv_phone);
                Button btnDismiss = (Button) view.findViewById(R.id.btnDismiss);

                tv_custname.setText(dataWO.getCustomerName()); // ini
                tv_address.setText(dataWO.getAddress()); // ini
                tv_contactperson.setText(dataWO.getContactPerson()); // ini
                tv_phone.setText(dataWO.getPhone()); // ini


                btnDismiss.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

//                    ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
//                        @Override
//                        public void onItemClicked(RecyclerView recyclerView, int position, View v) {
//                            Toast.makeText(KonfirmasiActivity.this, "" + detailTransaksiAdapter.getBarang(position).id, Toast.LENGTH_SHORT).show();
//                        }
//                    });

                dialog.setContentView(view);
                dialog.show();

                Window window = dialog.getWindow();
                window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            }
        });

        btnWOInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Animation animFadein = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.animation_fade_in);
//                btnCusInfo.startAnimation(animFadein);
                //Toast.makeText(DetailWOActivity.this, "klik imageview", Toast.LENGTH_SHORT).show();

                final Dialog dialog = new Dialog(DetailWOActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                View view = getLayoutInflater().inflate(R.layout.item_child2, null);
                TextView tv_materialSN = (TextView) view.findViewById(R.id.tv_materialSN);
                TextView tv_warranty = (TextView) view.findViewById(R.id.tv_warranty);
                TextView tv_ordertype = (TextView) view.findViewById(R.id.tv_ordertype);
                TextView tv_workcenter = (TextView) view.findViewById(R.id.tv_workcenter);
                TextView tv_createddate = (TextView) view.findViewById(R.id.tv_createddate);
                TextView tv_eststartdate = (TextView) view.findViewById(R.id.tv_eststartdate);
                TextView tv_estfinishdate = (TextView) view.findViewById(R.id.tv_estfinishdate);
                TextView tv_description = (TextView) view.findViewById(R.id.tv_description);
                TextView tv_plant = (TextView) view.findViewById(R.id.tv_plant);
                TextView tv_sparepart = (TextView) view.findViewById(R.id.tv_sparepart);
                TextView tv_estimateTime = (TextView) view.findViewById(R.id.tv_estimateTime);
                Button btnDismiss = (Button) view.findViewById(R.id.btnDismiss);

                tv_materialSN.setText(dataWO.getMaterial());
                tv_warranty.setText(dataWO.getWarranty());
                tv_ordertype.setText(dataWO.getOrderType());
                tv_workcenter.setText(dataWO.getWorkCenter());
                tv_createddate.setText(dataWO.getOrderDate());
                tv_eststartdate.setText(dataWO.getStartDate());
                tv_estfinishdate.setText(dataWO.getEndDate());
                tv_description.setText(dataWO.getRemark());
                tv_plant.setText(dataWO.getPlant());
                tv_sparepart.setText(dataWO.getSparepart());
                tv_estimateTime.setText(dataWO.getEstimateTime());

                btnDismiss.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                dialog.setContentView(view);
                dialog.show();

                Window window = dialog.getWindow();
                window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            }
        });

        btn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mayRequestLocation() == true && mayRequestImei() == true) {

                    if (!isLocationEnabled(getApplicationContext())) {
                        //Toast.makeText(LoginActivity.this, "Enable permission first!", Toast.LENGTH_SHORT).show();
                        showLocationWarning("Your location access is off. Enable now?");
                        return;
                    }
                }


                note = "";
                buttonclicked = "button1";
                if (button1_sequence == 3 || button1_sequence == 12 || button1_sequence == 15 || button1_sequence == 22) { //alert dialog
                    showDialogAlert();
                } else if (button1_sequence == 4 || button1_sequence == 11 || button1_sequence == 14 || button1_sequence == 21 || button1_sequence == 24) { //input dialog
                    showInputDialogAlert();
                } else {
                    syncLocation();
                }
            }
        });

        btn_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mayRequestLocation() == true && mayRequestImei() == true) {

                    if (!isLocationEnabled(getApplicationContext())) {
                        //Toast.makeText(LoginActivity.this, "Enable permission first!", Toast.LENGTH_SHORT).show();
                        showLocationWarning("Your location access is off. Enable now?");
                        return;
                    }
                }

                note = "";
                buttonclicked = "button2";
                if (button2_sequence == 3 || button2_sequence == 12 || button2_sequence == 15 || button2_sequence == 22) { //alert dialog
                    showDialogAlert();
                } else if (button2_sequence == 4 || button2_sequence == 11 || button2_sequence == 14 || button2_sequence == 21 || button2_sequence == 24) { //input dialog
                    showInputDialogAlert();
                } else {
                    syncLocation();
                }
            }
        });
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
                .subscribe(new Observer<ResponseUserMechanic>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                        try {
                            Toast.makeText(DetailWOActivity.this, "Failed to sign out. Try again later!", Toast.LENGTH_LONG).show();
                            Log.e("error koneksi logout", e.toString());
                            hideProgressDialog();
                        } catch (Exception onError) {
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

                                stopService(new Intent(DetailWOActivity.this, SyncOfflineData.class));
                                MainActivity.ClassHome = null;
                                MainActivity.ClassNotif = null;
                                MainActivity.ClassNew = null;
                                MainActivity.ClassOnProcess = null;
                                ClassDetail = null;
                                WriteSignature.ClassVOC = null;

                                Intent intent = new Intent(DetailWOActivity.this, LoginActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                //finish();
                            }
                        } else if (dataresponse.getStatusToken().equals("DATA_ALREADY_CLEARED")) {
                            Log.e("berhasil onnext login", "DATA_ALREADY_CLEARED");
                            long result = db.deleteUserMechanic(sessionManager.getUserId());
                            if (result == 1) {
                                db.deleteAllWO();
                                sessionManager.logout();

                                stopService(new Intent(DetailWOActivity.this, SyncOfflineData.class));
                                DetailWOActivity.ClassDetail = null;
                                WriteSignature.ClassVOC = null;

                                Intent intent = new Intent(DetailWOActivity.this, LoginActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }
                        } else {
                            Toast.makeText(DetailWOActivity.this, "Sign Out Later!", Toast.LENGTH_LONG).show();
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

    private void showDialogAlert() {
        //membuat alert dialog
        try {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(DetailWOActivity.this);
            // set title
            alertDialogBuilder.setTitle("Confirmation");
            // set dialog message
            alertDialogBuilder
                    .setMessage("Are you sure continue this step?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            int sizeOnProcessWO = db.checkOnProcessWO(dataWO.getOrderHeaderId());
                            Log.e("isi WO yang process", "" + sizeOnProcessWO);
                            if (sizeOnProcessWO == 0) {
                                syncLocation();
                                dialog.dismiss();
                            } else {
                                //call single alert button
//                                Toast.makeText(DetailWOActivity.this, "Sorry, another WO is in process!", Toast.LENGTH_SHORT).show();
//                                Toast.makeText(DetailWOActivity.this, "You can't continue this WO now!", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                showWarningSingle("Sorry, another WO is in process! You can't continue this WO now!");
                            }
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });
            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();
            // show it
            alertDialog.show();
        } catch (Exception e) {
            Log.e("error dialog", e.toString());
        }
    }

    private void showWarningSingle(String title) {
        //membuat alert dialog

        try {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(DetailWOActivity.this);
            // set title
            alertDialogBuilder.setTitle("Warning");
            // set dialog message
            alertDialogBuilder
                    .setMessage(title)
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
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

    private void showInputDialogAlert() {
        //membuat input dialog

        try {
            LayoutInflater li = LayoutInflater.from(DetailWOActivity.this);
            View promptsView = li.inflate(R.layout.input_dialog, null);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(DetailWOActivity.this);
            // set prompts.xml to alertdialog builder
            alertDialogBuilder.setView(promptsView);

            final EditText userInput = (EditText) promptsView.findViewById(R.id.editTextDialogUserInput);

            final Button btnSave = (Button) promptsView.findViewById(R.id.btnSaveDialog);
            final Button btnBatal = (Button) promptsView.findViewById(R.id.btnBatalDialog);

            // set dialog message
            alertDialogBuilder.setCancelable(false);

            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();
            // show it
            alertDialog.show();

            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    note = userInput.getText().toString();
                    if (!note.equals("")) {
                        if (alertDialog.isShowing()) {
                            alertDialog.dismiss();
                            syncLocation();
                        }
                    } else {
                        Toast.makeText(DetailWOActivity.this, "Please, fill the reason first!", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            btnBatal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (alertDialog.isShowing())
                        alertDialog.dismiss();
                }
            });
        } catch (Exception e) {
            Log.e("error dialog", e.toString());
        }
    }

    public void setButtonStatus() {

        Log.e("masuk setButtonStatus", "masuk setbutton");
        recentStatus = dataWO.getOrderStatus();
        Log.e("isi status", "" + recentStatus);
        //loadTasklist();
        if (recentStatus == 5070 || recentStatus == 5100) { //assigned
            doButton(true, true, 4, 3); //reject | accept
        } else if (recentStatus == 5050) { //accepted
            db.deleteAllWONotification(dataWO.getOrderHeaderId());
            doButton(false, true, 0, 5); // receive hard copy
        } else if (recentStatus == 5051) { // hard copy
            doButton(true, true, 6, 7); // no parts | complete part
        } else if (recentStatus == 5052) { // no parts
            doButton(true, true, 8, 9); //ready to go | is same location
        } else if (recentStatus == 5053) { // complete parts
            doButton(true, true, 8, 9); //ready to go | is same location
        } else if (recentStatus == 5057) { //on the way
            doButton(false, true, 0, 10); //arrived
        } else if (recentStatus == 5059) { //arrived
            doButton(true, true, 11, 13); //stand by | ready to check
        } else if (recentStatus == 5060) { //stand by
            doButton(false, true, 0, 12); //continue
        } else if (recentStatus == 5061) { // checking
            doButton(true, true, 14, 16); //hold | checking done
        } else if (recentStatus == 5062) { //hold
            doButton(false, true, 0, 15); // continue
        } else if (recentStatus == 5054) { // checking done
            btn_1.setEnabled(false);
            btn_1.setBackgroundColor(this.getResources().getColor(R.color.common_google_signin_btn_text_dark_disabled));
            btn_1.setTextColor(this.getResources().getColor(R.color.TXTbutton_2));

            btn_2.setEnabled(false);
            btn_2.setBackgroundColor(this.getResources().getColor(R.color.common_google_signin_btn_text_dark_disabled));
            //btn_2.setTextColor(this.getResources().getColor(R.color.TXTbuttonPositive));
            doButton(true, true, 18, 19); // ready to go | is same location
        } else if (recentStatus == 5055) {// approved status repairing
            btn_1.setEnabled(true);
            //btn_1.setBackgroundColor(this.getResources().getColor(R.color.button_1));
            btn_1.setBackgroundResource(R.drawable.style_button_1);
            btn_1.setTextColor(this.getResources().getColor(R.color.TXTbutton_1));
            btn_2.setEnabled(true);
            btn_2.setBackgroundResource(R.drawable.style_button_2);
            //btn_2.setBackgroundColor(this.getResources().getColor(R.color.button_2));
            //btn_2.setTextColor(this.getResources().getColor(R.color.TXTbuttonPositive));
            doButton(true, true, 18, 19); // ready to go | is same location
        } else if (recentStatus == 5071) {//on the way
            doButton(false, true, 0, 20); // ready to go | is same location
        } else if (recentStatus == 5072) {//arrived
            doButton(true, true, 21, 23); // stand by | ready to repair
        } else if (recentStatus == 5058) { //stand by
            doButton(false, true, 0, 22); // continue
        } else if (recentStatus == 5064) { //repairing
            //loadtasklist
            db.updateStatusTasklistAll(dataWO.getOrderStatus(), dataWO.getOrderStatusName(), dataWO.getOrderHeaderId());
            loadTasklist();
            //pengecekan jika status tasklist belum complete maka disable button repair done
            if (db.checkTasklistCompleted(dataWO.getOrderHeaderId()) != 0) {
                btn_2.setEnabled(false);
                btn_2.setBackgroundColor(this.getResources().getColor(R.color.common_google_signin_btn_text_dark_disabled));
                //btn_2.setTextColor(this.getResources().getColor(R.color.white_disabled_material));
                doButton(true, true, 24, 27); // hold | repair done
            } else {
                btn_2.setEnabled(true);
                btn_2.setBackgroundResource(R.drawable.style_button_2);
                //btn_2.setBackgroundColor(this.getResources().getColor(R.color.button_2));
                //btn_2.setTextColor(this.getResources().getColor(R.color.TXTbuttonPositive));
                //doButton(true, true, 24, 27); // hold | repair done
                doButton(false, true, 0, 27); // hold | repair done
            }
        } else if (recentStatus == 5065) { //hold
            //loadtasklist

            Log.e("isi semua", "" + dataWO.getOrderStatus() + dataWO.getOrderStatusName() + dataWO.getOrderHeaderId());
            db.updateStatusTasklistAll(dataWO.getOrderStatus(), dataWO.getOrderStatusName(), dataWO.getOrderHeaderId());
            loadTasklist();
            btn_2.setEnabled(true);
            //btn_2.setBackgroundColor(this.getResources().getColor(R.color.button_2));
            btn_2.setBackgroundResource(R.drawable.style_button_2);
            doButton(false, true, 0, 25); // continue
        } else if (recentStatus == 5067) { //repairing done | submit ttd udah selesai
            //doButton(false, true, 0, 29); // continue
            //loadTasklist();
            Intent intent = new Intent(DetailWOActivity.this, WriteSignature.class);
            intent.putExtra("dataWO", dataWO);
            startActivity(intent);
            finish();
        } else if (recentStatus == 5056) { //repairing done
            db.updateStatusTasklistAll(dataWO.getOrderStatus(), dataWO.getOrderStatusName(), dataWO.getOrderHeaderId());
            loadTasklist();
            doButton(false, true, 0, 29); // continue
        } else if (recentStatus == 5068) {//leave location
            //loadTasklist();
            doButton(false, true, 0, 30); // continue
        } else if (recentStatus == 5069) {//arrive at office
            if (db.getWOHistoryActivity(dataWO.getOrderHeaderId()) == 0) {
                //finish WO
                Toast.makeText(DetailWOActivity.this, "Finish WO! Congratulation!", Toast.LENGTH_SHORT).show();
                long checkdeleteWO = db.deleteWO(dataWO.getOrderHeaderId());
                hideProgressDialog();
//                Intent intent = new Intent(DetailWOActivity.this, MainActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
                finish();
            } else {
                //HA belum sync semua
                hideProgressDialog();
                doButton(false, true, 0, 0);
                btn_2.setEnabled(false);
                btn_2.setText("Waiting to sync status...");
                btn_2.setBackgroundColor(this.getResources().getColor(R.color.common_google_signin_btn_text_dark_disabled));
                //bottomContainerTwoButtons.setVisibility(View.INVISIBLE);
            }
            //long checkdeleteTasklist = db.deleteRejectedWOTasklist(dataWO.getOrderHeaderId());
            //long checkdeleteHA = db.deleteHistoryActivity(dataWO.getOrderHeaderId());
        } else if (recentStatus == 5099) { //deleted WO
            if (db.getWOHistoryActivity(dataWO.getOrderHeaderId()) == 0) {
                //finish WO
                Toast.makeText(DetailWOActivity.this, "Rejected WO!", Toast.LENGTH_SHORT).show();
                long checkdeleteWO = db.deleteWO(dataWO.getOrderHeaderId());
                hideProgressDialog();
//                Intent intent = new Intent(DetailWOActivity.this, MainActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
                finish();
            } else {
                //HA belum sync semua
                hideProgressDialog();
                doButton(false, true, 0, 0);
                btn_2.setEnabled(false);
                btn_2.setText("Waiting to sync status...");
                btn_2.setBackgroundColor(this.getResources().getColor(R.color.common_google_signin_btn_text_dark_disabled));
                //bottomContainerTwoButtons.setVisibility(View.INVISIBLE);
            }
        }
    }

    private void doButton(boolean visible1, boolean visible2, int sequence1, int sequence2) {
        button1_sequence = sequence1;
        button2_sequence = sequence2;
        if (visible1 == true) {
            btn_1.setVisibility(View.VISIBLE);
            btn_1.setText(ConstantaWO.CONSTANT_STATUS[sequence1].getStatus_buttonname());
        } else {
            btn_1.setVisibility(View.GONE);
        }
        if (visible2 == true) {
            btn_2.setVisibility(View.VISIBLE);
            btn_2.setText(ConstantaWO.CONSTANT_STATUS[sequence2].getStatus_buttonname());
        } else {
            btn_2.setVisibility(View.GONE);
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

    private void syncLocation() {
        Log.e("masuk syncLocation", "syncLocation");
        GoogleLocationService googleLocationService = new GoogleLocationService(DetailWOActivity.this);
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
    }

    private void syncDataWO(Double latitude, Double longitude) {
        showProgressDialog();
        Log.e("syncDataWO Detail", "syncDataWO Detail");
        db = new DatabaseHelper(this);

        //for update db
        if (buttonclicked.equals("button1")) {
            path = ConstantaWO.CONSTANT_STATUS[button1_sequence].getStatus_path();

            statusID_updateButton = ConstantaWO.CONSTANT_STATUS[button1_sequence].getStatus_id();
            statusNAME_updateButton = ConstantaWO.CONSTANT_STATUS[button1_sequence].getStatus_name();
        } else {
            path = ConstantaWO.CONSTANT_STATUS[button2_sequence].getStatus_path();

            statusID_updateButton = ConstantaWO.CONSTANT_STATUS[button2_sequence].getStatus_id();
            statusNAME_updateButton = ConstantaWO.CONSTANT_STATUS[button2_sequence].getStatus_name();
        }
        //start to sent status to backend java

        RequestStatusWO dataRequest = new RequestStatusWO();
        dataRequest.setOrderAssignmentId(dataWO.getOrderAssignmentId());
        Log.e("isi OrderAssignmentId()", "" + dataWO.getOrderAssignmentId());
        dataRequest.setOrderHeaderId(dataWO.getOrderHeaderId());
        dataRequest.setLatitude(latitude);
        dataRequest.setLongitude(longitude);
        dataRequest.setOrderStatus(dataWO.getOrderStatus());
        dataRequest.setOrderStatusName(dataWO.getOrderStatusName());
        dataRequest.setMechanicId(dataWO.getMechanicId());
        Date date = new Date();
        //SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss");// dd/MM/yyyy HH:mm:ss

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Log.e("isi formatter", "" + formatter.format(date).toString());
        dataRequest.setActionTime(formatter.format(date).toString());
        dataRequest.setNote(note);

        RESTService RESTService = ServiceFactory.createRetrofitService(RESTService.class);
        RESTService.sentrequest(path, dataRequest)
                //RESTService.sentrequest(path, dataRequest)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseStatusWO>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {

                        try {
                            Log.e("onError", e.toString());
                            //Toast.makeText(DetailWOActivity.this, "Bad Internet Connection. Sync data later...", Toast.LENGTH_LONG).show();
                            Toast.makeText(DetailWOActivity.this, "Failed to Sync Data. Try again Later!", Toast.LENGTH_LONG).show();

//                            Log.e("Start sinkron HA", "HA");
//                            ModelHistoryActivity dataHA = new ModelHistoryActivity();
//                            dataHA.setType("WO");
//                            Log.e("setType", "" + dataHA.getType());
//                            dataHA.setOrderHeaderId(dataWO.getOrderHeaderId());
//                            Log.e("setOrderHeaderId", "" + dataHA.getOrderHeaderId());
//                            dataHA.setJson(converterJSON.jsontoStringWO(dataRequest));
//                            Log.e("setJson", "" + dataHA.getJson());
//                            dataHA.setPath(path);
//                            Log.e("setPath", "" + dataHA.getPath());
//                            dataHA.setFlag(0);
//                            Log.e("setFlag", "" + dataHA.getFlag());
//                            Log.e("end HA", "**********************************");
//
//                            long checkresult = db.writeHistoryActivity(dataHA);
//                            Log.e("isi insert HA", "" + checkresult);
//                            db.updateStatusWO(dataWO.getOrderHeaderId(), statusID_updateButton, statusNAME_updateButton);
////                        int countHA = db.getCountHistoryActivity();
////                        Log.e("jumlah HA : ", "" + countHA);
//                            setRecentStatus();
//                            //setButtonStatus();
                            hideProgressDialog();
                        } catch (Exception onError) {
                            Log.e("Exception onError", e.toString());
                            hideProgressDialog();
                        }
                    }

                    @Override
                    public void onNext(ResponseStatusWO data) {

                        Log.e("onnext Detail status", data.getStatus());
                        Log.e("onnext Detail status", data.getDescription());
                        if (data.getStatus().equals("SUCCESS") || data.getStatus().equals("STATUS_UPDATED")) {
                            Log.e("onnext sukses", "" + data.getOrderStatus());
                            Log.e("onnext sukses", "" + data.getOrderStatusName());
                            db.updateStatusWO(dataWO.getOrderHeaderId(), data.getOrderStatus(), data.getOrderStatusName());
                        } else {
                            Toast.makeText(DetailWOActivity.this, "Failed to Sync Data. Try again Later!", Toast.LENGTH_LONG).show();
                            Log.e("onnext gagal", "onnext gagal");

//                            Log.e("Start sinkron HA", "HA");
//                            ModelHistoryActivity dataHA = new ModelHistoryActivity();
//                            dataHA.setType("WO");
//                            Log.e("setType", "" + dataHA.getType());
//                            dataHA.setOrderHeaderId(dataWO.getOrderHeaderId());
//                            Log.e("setOrderHeaderId", "" + dataHA.getOrderHeaderId());
//                            dataHA.setJson(converterJSON.jsontoStringWO(dataRequest));
//                            Log.e("setJson", "" + dataHA.getJson());
//                            dataHA.setPath(path);
//                            Log.e("setPath", "" + dataHA.getPath());
//                            dataHA.setFlag(0);
//                            Log.e("setFlag", "" + dataHA.getFlag());
//                            Log.e("end HA", "**********************************");
//
//                            long checkresult = db.writeHistoryActivity(dataHA);
//                            Log.e("isi insert HA", "" + checkresult);
//                            db.updateStatusWO(dataWO.getOrderHeaderId(), statusID_updateButton, statusNAME_updateButton);
                        }
                        setRecentStatus();
                        //setButtonStatus();
                        hideProgressDialog();
                    }
                });
    }

    public void setRecentStatus() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    dataWO = db.getRecentStatusWO(WOID).get(0);
                    if (dataWO == null) {
                        Intent intent = new Intent(DetailWOActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    } else {
                        WOID = dataWO.getOrderHeaderId();
                        WO_STATUS.setText(dataWO.getOrderStatusName());
                        setButtonStatus();
                    }

                } catch (Exception e) {
                    Intent intent = new Intent(DetailWOActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }

            }
        });
    }

    public void swipeDetailWO() {
        RESTService RESTService = ServiceFactory.createRetrofitService(RESTService.class);
        RESTService.getDetailWO(sessionManager.getUserId(), dataWO.getOrderHeaderId(), dataWO.getOrderAssignmentId())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseNewWO>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                        swipeRefreshLayout.setRefreshing(false);

                    }

                    @Override
                    public void onNext(ResponseNewWO response) {
                        Log.e("swipeDetailWO", response.getStatus());
                        try {
                            if (response.getStatus().equals("SUCCESS")) {
                                int size = response.getResult().size();

                                Log.e("isi size", "" + size);

                                if (size != 0) {
                                    List<ModelWOHeader> wo = response.getResult();
                                    for (int i = 0; i < size; i++) {
                                        if (wo.get(i).getOrderStatus() == 5055) {
                                            int checkresult = db.updateStatusWOApproval(wo.get(i).getOrderHeaderId(), wo.get(i).getOrderStatus(), wo.get(i).getOrderStatusName());
                                            //} else if (wo.get(i).getOrderStatus() == 5064) {
                                        }else{
                                            for (int j = 0; j < wo.get(i).getOrderItemView().size(); j++) {
                                                Log.e("status item", "" + wo.get(i).getOrderItemView().get(j).getOrderItemStatus());
                                                if (wo.get(i).getOrderItemView().get(j).getOrderItemStatus() == 7004) {
                                                    Log.e("isi actiontime", "" + wo.get(i).getOrderItemView().get(j).getActionDate());
                                                    db.updateStatusTasklist(wo.get(i).getOrderHeaderId(), wo.get(i).getOrderItemView().get(j).getOrderiItemId(), wo.get(i).getOrderItemView().get(j).getOrderItemStatus(), wo.get(i).getOrderItemView().get(j).getOrderItemStatusName(), wo.get(i).getOrderItemView().get(j).getActionDate());
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    swipeRefreshLayout.setRefreshing(false);
                                }
                            } else if (response.getStatus().equals("EMPTY")) {
                                swipeRefreshLayout.setRefreshing(false);
                            }
                        } catch (Throwable throwable) {
                            //Toast.makeText(getContext(), "Failed to insert data : " + throwable, Toast.LENGTH_SHORT).show();
                            //setRecentStatus();
                            swipeRefreshLayout.setRefreshing(false);
                        } finally {
                            setRecentStatus();
                            loadTasklist();
                            setBadgeCount();
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }
                });
    }

    public void loadTasklist() {
        orderList = db.getAllTasklist(WOID);
        orderAdapter = new TasklistAdapter(this, orderList, dataWO.getOrderAssignmentId(), dataWO.getOrderHeaderId(), sessionManager.getUserId());
        recyclerViewTaskList.setAdapter(orderAdapter);
    }


    @Override
    protected void onResume() {
        super.onResume();
        //setRecentStatus();
        swipeDetailWO();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //dataIntent = (ModelWOHeader) getIntent().getExtras().getSerializable("data");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // save state for the two menus
        //outState.putParcelable(SS_KEY_MENU, navbar_header);
    }

    @Override
    public void onGroupCollapse(int groupPosition, boolean fromUser) {

    }

    @Override
    public void onGroupExpand(int groupPosition, boolean fromUser) {

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
        ClassDetail = null;
        WriteSignature.ClassVOC = null;
    }

    Menu menu2 = null;
    boolean showItem = false;

    public void setBadgeCount() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (menu2 != null) {
                    int count = db.countNotif();
                    if (count != 0) {
                        Log.e("masuk count!=0 Detail", "" + showItem);
                        showItem = true;
                        //badgeCount = count;
                        ActionItemBadge.update(DetailWOActivity.this, menu2.findItem(R.id.action_notifications), ContextCompat.getDrawable(DetailWOActivity.this, R.drawable.ic_notifications_active_white_24dp), ActionItemBadge.BadgeStyles.RED, NumberUtils.formatNumber(count));


                    } else {
                        Log.e("masuk count==0 Detail", "" + showItem);
                        if (showItem == true) {
                            ActionItemBadge.update(DetailWOActivity.this, menu2.findItem(R.id.action_notifications), ContextCompat.getDrawable(DetailWOActivity.this, R.drawable.ic_notifications_active_white_24dp), ActionItemBadge.BadgeStyles.RED, Integer.MIN_VALUE);
                            showItem = false;
                        }
                    }
                } else {
                    Log.e("menu2 kosong", "menu2 kosong");
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_detail, menu);
        menu2 = menu;
        setBadgeCount();
        return super.onCreateOptionsMenu(menu);
        //return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
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
            if (OrderFragment.orderFragment != null) {
                // Toast.makeText(getApplicationContext(), "orderFragment ada", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                //Toast.makeText(getApplicationContext(), "orderFragment tidak ada", Toast.LENGTH_SHORT).show();
                //        Log.e("masuk on back", "bye");
                Intent intent = new Intent(DetailWOActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
            return true;
        } else if (id == R.id.action_unable_continue) {
            //here code
            showPopup();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showPopup() {
        View menuItemView = findViewById(R.id.action_unable_continue);
        PopupMenu popup = new PopupMenu(this, menuItemView);
        MenuInflater inflate = popup.getMenuInflater();
        inflate.inflate(R.menu.menu_unable_continue, popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.item_unable_continue) {
                    showInputDialogAlert();
                    popup.dismiss();
                }
                return true;
            }
        });
        popup.show();
    }

    private void showLocationWarning(String title) {
        //membuat alert dialog

        try {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(DetailWOActivity.this);
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
            Toast.makeText(DetailWOActivity.this, "Active your permission first!", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(DetailWOActivity.this, "Active your permission first!", Toast.LENGTH_SHORT).show();
        } else {
            requestPermissions(new String[]{READ_PHONE_STATE}, REQUEST_READ_PHONE_STATE);
        }
        return false;
    }

    @Override
    public void onBackPressed() {

        if (OrderFragment.orderFragment != null) {
            // Toast.makeText(getApplicationContext(), "orderFragment ada", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            //Toast.makeText(getApplicationContext(), "orderFragment tidak ada", Toast.LENGTH_SHORT).show();
            //        Log.e("masuk on back", "bye");
            Intent intent = new Intent(DetailWOActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
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