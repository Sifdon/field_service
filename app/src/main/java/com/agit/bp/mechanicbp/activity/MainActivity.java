package com.agit.bp.mechanicbp.activity;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.transition.Fade;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.agit.bp.mechanicbp.R;
import com.agit.bp.mechanicbp.database.DatabaseHelper;
import com.agit.bp.mechanicbp.drawable.CustomViewPager;
import com.agit.bp.mechanicbp.fragments.HomeFragment;
import com.agit.bp.mechanicbp.fragments.NotificationFragment;
import com.agit.bp.mechanicbp.fragments.OrderFragment;
import com.agit.bp.mechanicbp.fragments.OrderOnProcessFragment;
import com.agit.bp.mechanicbp.models.RequestUserMechanic;
import com.agit.bp.mechanicbp.models.ResponseUserMechanic;
import com.agit.bp.mechanicbp.services.RESTService;
import com.agit.bp.mechanicbp.services.ServiceFactory;
import com.agit.bp.mechanicbp.services.SessionManager;
import com.agit.bp.mechanicbp.services.SyncOfflineData;
import com.appeaser.sublimenavigationviewlibrary.OnNavigationMenuEventListener;
import com.appeaser.sublimenavigationviewlibrary.SublimeBaseMenuItem;
import com.appeaser.sublimenavigationviewlibrary.SublimeNavigationView;
import com.gigamole.navigationtabbar.ntb.NavigationTabBar;
import com.google.firebase.iid.FirebaseInstanceId;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements HomeFragment.OnFragmentInteractionListener, OrderFragment.OnFragmentInteractionListener, OrderOnProcessFragment.OnFragmentInteractionListener, NotificationFragment.OnFragmentInteractionListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    private HomeFragment homeFragment;
    private OrderFragment orderFragment;
    private OrderOnProcessFragment orderOnProcessFragment;
    private NotificationFragment notificationFragment;

    /**
     * The {@link ViewPager} that will host the section contents.
     */

    private CustomViewPager mViewPager;
    public LayerDrawable newOrderMenu;
    String checkintent = "new";
    String dataStatus = "";
    final String SS_KEY_MENU = "ss.key.menu.2";
    //SublimeMenu navbar_header;
    SublimeNavigationView sn_view;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    ProgressDialog mProgressDialog;

    private SessionManager sessionManager;
    DatabaseHelper db;

    public static MainActivity ClassNotif = null;
    public static MainActivity ClassNew = null;
    public static MainActivity ClassOnProcess = null;
    public static MainActivity ClassHome = null;

    ArrayList<NavigationTabBar.Model> models;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Fabric.with(this, new Crashlytics());
//        setBadgeCount();
        // hanya bisa jika notif di klik
        setContentView(R.layout.activity_home);
        setupWindowAnimations();
        //sessionManager = new SessionManager(this);

        sessionManager = new SessionManager(this);
        db = new DatabaseHelper(this);

        homeFragment = new HomeFragment();
        orderFragment = new OrderFragment();
        orderOnProcessFragment = new OrderOnProcessFragment();
        notificationFragment = new NotificationFragment();

        ClassHome = MainActivity.this;
        ClassNotif = null;
        ClassNew = null;
        ClassOnProcess = null;
        DetailWOActivity.ClassDetail = null;
        WriteSignature.ClassVOC = null;

        if (Build.VERSION.SDK_INT >= 21) {
            // Set the status bar to dark-semi-transparentish
//            getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
//                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            getWindow().setStatusBarColor(getResources().getColor(R.color.colorAccent));
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        //gambar icon menu nav bar
        if (toolbar != null) {
            toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
        }

        setSupportActionBar(toolbar);
        //getSupportActionBar().setIcon(R.drawable.logotextbpwhite);
        //getSupportActionBar().setLogo(R.drawable.logo_toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (CustomViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setPagingEnabled(false);

//        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
//        tabLayout.setupWithViewPager(mViewPager);
//        tabLayout.setTabMode(TabLayout.MODE_FIXED);
//        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
//
//        tabLayout.setupWithViewPager(mViewPager);

        final NavigationTabBar navigationTabBar = (NavigationTabBar) findViewById(R.id.ntb);

        models = new ArrayList<>();
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_home_black_24dp),
//                        Color.GRAY
                        Color.parseColor("#d5d5d5")
                ).title("Home")
                        .badgeTitle("")
                        .build()
        );
        //0

        //models = new ArrayList<>();
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_note_add_black_24dp),
//                        Color.GRAY
                        Color.parseColor("#d5d5d5")
                ).title("What's New")
                        .badgeTitle("")
                        .build()
        );
        //models.get(0).showBadge();
        //1

        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_format_paint_black_24dp),
                        Color.parseColor("#d5d5d5")
                ).title("On Process")
                        .badgeTitle("")
                        .build()
        );
        //models.get(1).showBadge();
        //2

        //int count = db.countNotif();

        int count = db.countNotif();
        if (count != 0) {
            models.add(
                    new NavigationTabBar.Model.Builder(
                            getResources().getDrawable(R.drawable.ic_notifications_active_black_24dp),
                            Color.parseColor("#d5d5d5")
                    ).title("Notification")
                            .badgeTitle("" + count)
                            .build()
            );
            models.get(3).showBadge();

        } else {
            models.add(
                    new NavigationTabBar.Model.Builder(
                            getResources().getDrawable(R.drawable.ic_notifications_active_black_24dp),
                            Color.parseColor("#d5d5d5")
                    ).title("Notification")
                            //.badgeTitle("0")
                            .build()
            );
            //setBadgeCount();
            //models.get(2).showBadge();
        }

        navigationTabBar.setModels(models);
        navigationTabBar.setViewPager(mViewPager);

        mViewPager.setCurrentItem(0);
//        tabLayout.getTabAt(0).setIcon(R.drawable.ic_assignment_black_24dp);
//        tabLayout.getTabAt(1).setIcon(R.drawable.ic_home_white_24dp);

        try {
            checkintent = getIntent().getExtras().getString("status");
            if (checkintent.equals("home")) {
                mViewPager.setCurrentItem(0);
            } else if (checkintent.equals("notification")) {
                mViewPager.setCurrentItem(3);
                ClassHome = null;
                ClassNotif = MainActivity.this;
                ClassNew = null;
                ClassOnProcess = null;
                db.updateCountNotif(1, "");
                showItem = false;
                setBadgeCount();

            } else {
                mViewPager.setCurrentItem(0);
            }
        } catch (Exception e) {
            checkintent = "new";
            mViewPager.setCurrentItem(0);
        }

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                if (position == 0) {
                    ClassHome = MainActivity.this;
                    ClassNotif = null;
                    ClassNew = null;
                    ClassOnProcess = null;
                    showItem = true;

                } else if (position == 1) {
                    if (mSectionsPagerAdapter != null) {
                        ClassHome = null;
                        ClassNotif = null;
                        ClassNew = MainActivity.this;
                        ClassOnProcess = null;
                        showItem = true;
                    }
                } else if (position == 2) {

                    if (mSectionsPagerAdapter != null) {
                        ClassHome = null;
                        ClassNotif = null;
                        ClassNew = null;
                        ClassOnProcess = MainActivity.this;
                        showItem = true;
//                        setBadgeCount();
//                        ((OrderOnProcessFragment) mSectionsPagerAdapter.getItem(position)).load();
                    }

                } else if (position == 3) {
                    ClassHome = null;
                    ClassNotif = MainActivity.this;
                    ClassNew = null;
                    ClassOnProcess = null;
                    db.updateCountNotif(1, "");
                    showItem = false;
                    setBadgeCount();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mDrawerLayout = (DrawerLayout) findViewById(R.id.nav_drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                toolbar, R.string.material_drawer_open, R.string.material_drawer_close) {

            // Called when a drawer has settled in a completely closed state. /
            public void onDrawerClosed(View view) {
//                loadFrag(new LeftFragment(), null, R.id.mainLeftView);
            }

            // Called when a drawer has settled in a completely open state. /
            public void onDrawerOpened(View drawerView) {

            }
        };

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(SS_KEY_MENU)) {
                //navbar_header = savedInstanceState.getParcelable(SS_KEY_MENU);
            }
        }
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

                if (title.equals("Change Profile")) {
                    Intent intent = new Intent(MainActivity.this, ChangeProfile.class);
                    startActivity(intent);
                } else if (title.equals("See Report")) {
                    Uri uri = Uri.parse("http://www.google.com"); // missing 'http://' will cause crashed
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                } else if (title.equals("Logout")) {
                    //finish();
                    Toast.makeText(MainActivity.this, "Anda klik logout", Toast.LENGTH_SHORT).show();
                    showProgressDialog();
                    ResponseUserMechanic user = db.logout(sessionManager.getUserId());
                    if (user != null) {
                        // ada insert variabel session user
//                            Log.e("username logout :", "" + user.getUsername());
//                            Log.e("password logout :", "" + user.getPassword());
                        logout(user.getUsername(), user.getPassword());
                    } else {
                        Toast.makeText(MainActivity.this, "Sign Out Later!", Toast.LENGTH_LONG).show();
                    }
                } else if (title.equals("About Application")) {
                    //Toast.makeText(MainActivity.this, "About Application", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(MainActivity.this, AboutApplication.class);
                    startActivity(intent);
                }
                return true;
            }
        });

        TextView txt = (TextView) sn_view.getHeaderView().findViewById(R.id.tv_navbar_username);
        txt.setText(sessionManager.getName());
        //sn_view.switchMenuTo(R.menu.menu_navbar);
        //sn_view.switchMenuTo(navbar_header);

        /////////////////////////////////////////////////
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
            //Log.e("token before", existingToken);
            if (existingToken == null) {
                existingToken = preferences.getString(getString(R.string.firebase_token_id), "");
            }
            //Log.e("token after", existingToken);
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
                            Toast.makeText(MainActivity.this, "Failed to sign out. Try again later!", Toast.LENGTH_LONG).show();
                            Log.e("error koneksi logout", e.toString());
                            hideProgressDialog();
                        } catch (Exception onError) {
                            Log.e("Exception onError", e.toString());
                            hideProgressDialog();
                        }
                    }

                    @Override
                    public void onNext(ResponseUserMechanic dataresponse) {

                        //Log.e("berhasil onnext login", dataresponse.getStatusToken());
                        if (dataresponse.getStatusToken().equals("LOGOUT SUCCESS")) {
                            //Log.e("berhasil onnext login", "MASUK IF");
                            long result = db.deleteUserMechanic(sessionManager.getUserId());
                            if (result == 1) {
                                db.deleteAllWO();
                                sessionManager.logout();

                                stopService(new Intent(MainActivity.this, SyncOfflineData.class));
                                DetailWOActivity.ClassDetail = null;
                                WriteSignature.ClassVOC = null;

                                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }
                        }else if(dataresponse.getStatusToken().equals("DATA_ALREADY_CLEARED")){
                            //Log.e("berhasil onnext login", "DATA_ALREADY_CLEARED");
                            long result = db.deleteUserMechanic(sessionManager.getUserId());
                            if (result == 1) {
                                db.deleteAllWO();
                                sessionManager.logout();

                                stopService(new Intent(MainActivity.this, SyncOfflineData.class));
                                DetailWOActivity.ClassDetail = null;
                                WriteSignature.ClassVOC = null;

                                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "Sign Out Later!", Toast.LENGTH_LONG).show();
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

    private void showProgressDialog() {
        try {
            mProgressDialog = new ProgressDialog(this);
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // save state for the two menus
        //outState.putParcelable(SS_KEY_MENU, navbar_header);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setupWindowAnimations() {
        Fade fade = (Fade) TransitionInflater.from(this).inflateTransition(R.transition.activity_fade);
        getWindow().setEnterTransition(fade);
        //getWindow().setStatusBarColor(Color.parseColor("#00796a"));
    }

    boolean showItem = false;

    public void setBadgeCount() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.e("masuk setBadgeCount", "setBadgeCount");

                if (ClassNotif != null) {
                    Log.e("masuk set all true", "update true notif");
                    db.updateCountNotif(1, "");
                    showItem = false;
                } else {
                    showItem = true;
                }

                int count = db.countNotif();
                if (count != 0) {
                    if (showItem == true) {
                        //not in notification area
                        Log.e("masuk showItem", "true " + count);
                        models.get(3).setBadgeTitle("" + count);
                        //models.get(3).updateBadgeTitle("" + count);
                        models.get(3).showBadge();
                    } else {
                        //in notification area
                        Log.e("masuk showItem", "false" + count);
                        models.get(3).hideBadge();
                    }
                } else {
                    models.get(3).hideBadge();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        /*MenuItem item = menu.findItem(R.id.action_notifications);
        newOrderMenu = (LayerDrawable) item.getIcon();
        BadgeDrawable.setBadgeCount(this, newOrderMenu, 0);*/

//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu_main, menu);
//        menu2 = menu;
//        setBadgeCount();
////        if (badgeCount > 0) {
////            showItem = true;
////            ActionItemBadge.update(this, menu.findItem(R.id.action_notifications), ContextCompat.getDrawable(this, R.drawable.ic_home_24dp), ActionItemBadge.BadgeStyles.RED, NumberUtils.formatNumber(badgeCount));
////        }

        return super.onCreateOptionsMenu(menu);
        //return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setBadgeCount();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ClassHome = null;
        ClassNotif = null;
        ClassNew = null;
        ClassOnProcess = null;
        HomeFragment.homeFragment = null;
        OrderFragment.orderFragment = null;
        OrderOnProcessFragment.orderOnProcessFragment = null;
        NotificationFragment.notificationFragment = null;
        DetailWOActivity.ClassDetail = null;
        WriteSignature.ClassVOC = null;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

        //Log.d("test", "fragment interaction");
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

//        @Override
//        public int getItemPosition(Object object) {
//            if(object instanceof HomeFragment){
//                ((HomeFragment) object).countActivity();
//            }
//            return super.getItemPosition(object);
//        }

        @Override
        public long getItemId(int position) {
            return super.getItemId(position);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position) {
                case 0:
                    return homeFragment;
                case 1:
                    return orderFragment;
                case 2:
                    return orderOnProcessFragment;
                case 3:
                    return notificationFragment;
            }
            //kbalik sal -___________________- y
            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Home";
                case 1:
                    return "What's New";
                case 2:
                    return "On Process";
                case 3:
                    return "Notification";
            }
            return null;
        }
    }
}
