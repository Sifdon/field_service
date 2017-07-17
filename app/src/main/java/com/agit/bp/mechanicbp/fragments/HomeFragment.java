package com.agit.bp.mechanicbp.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.agit.bp.mechanicbp.R;
import com.agit.bp.mechanicbp.activity.MainActivity;
import com.agit.bp.mechanicbp.adapters.SlidingImageAdapter;
import com.agit.bp.mechanicbp.database.DatabaseHelper;
import com.agit.bp.mechanicbp.drawable.ViewpagerGallery;
import com.agit.bp.mechanicbp.services.SessionManager;

import me.relex.circleindicator.CircleIndicator;

//import com.agit.bp.mechanicbp.activity.WorkOrderDetail;

public class HomeFragment extends Fragment implements AdapterView.OnItemClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters
    private Context mContext;
    RelativeLayout mRelativeLayout;
    private OnFragmentInteractionListener mListener;

    //    private View view;
    private DatabaseHelper db;
    private ScrollView scrollView;

    ProgressDialog mProgressDialog;

    private SlidingImageAdapter slidingImageAdapter;
    private ViewpagerGallery viewPager;
    TextView mechanic_name;
    TextView count_new;
    TextView count_onprocess;
    TextView count_syncoffline;

    private SessionManager sessionManager;

    public static HomeFragment homeFragment = null;
    private final Handler handler = new Handler();
    int iteration = 0;
    Runnable ViewPagerVisibleScroll = new Runnable() {
        @Override
        public void run() {
            if (slidingImageAdapter != null && viewPager != null) {
                if (iteration >= 0 && iteration <= slidingImageAdapter.getCount() - 1) {
                    viewPager.setCurrentItem(iteration, true);
                    iteration++;
                } else {
                    iteration = 0;
                }
                handler.postDelayed(ViewPagerVisibleScroll, 8000);
            }
        }
    };

    static final int NUM_ITEMS = 5;
    //    ImageFragmentPagerAdapter imageFragmentPagerAdapter;
//    ViewPager viewPager;
    public static final String[] IMAGE_NAME = {"aboutapp", "aboutapp", "aboutapp", "aboutapp", "aboutapp"};

    private View view;

    public HomeFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sessionManager = new SessionManager(getActivity());
        db = new DatabaseHelper(getActivity());
        homeFragment = this;
        mContext = getContext();
        Log.e("swipe", "oncreate");
        //syncDataWO();
    }

    private String getFirstWord(String text) {
        if (text.indexOf(' ') > -1) { // Check if there is more than one word.
            return text.substring(0, text.indexOf(' ')); // Extract first word.
        } else {
            return text; // Text is the first word itself.
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);

//        empty_order = (ImageView) view.findViewById(R.id.empty_order);
        mechanic_name = (TextView) view.findViewById(R.id.mechanic_name);
        mechanic_name.setText("Hi, " + getFirstWord(sessionManager.getName()));

        count_new = (TextView) view.findViewById(R.id.count_new);
        count_new.setText(""+db.getCountAllActivity("new"));

        count_onprocess = (TextView) view.findViewById(R.id.count_onprocess);
        count_onprocess.setText(""+db.getCountAllActivity("onprocess"));

        count_syncoffline = (TextView) view.findViewById(R.id.count_syncoffline);
        count_syncoffline.setText(""+db.getCountAllActivity("syncoffline"));

        scrollView = (ScrollView) view.findViewById(R.id.scrollView);
        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {

            @Override
            public void onScrollChanged() {
                int scrollY = scrollView.getScrollY();
//                if (scrollY == 0)
//                    swipeRefreshLayout.setEnabled(true);
//                else
//                    swipeRefreshLayout.setEnabled(false);
            }
        });

        Log.e("swipe", "oncreateView");
//        countActivity();

        slidingImageAdapter = new SlidingImageAdapter(getActivity(), IMAGE_NAME);
        viewPager = (ViewpagerGallery) view.findViewById(R.id.pager);
        viewPager.setAdapter(slidingImageAdapter);
        CircleIndicator indicator = (CircleIndicator) view.findViewById(R.id.indicator);
        indicator.setViewPager(viewPager);


        handler.postDelayed(ViewPagerVisibleScroll, 100);

        return view;
    }

    public void refreshCount(){
        count_new.setText(""+db.getCountAllActivity("new"));
        count_onprocess.setText(""+db.getCountAllActivity("onprocess"));
        count_syncoffline.setText(""+db.getCountAllActivity("syncoffline"));

        if (MainActivity.ClassHome != null) {
            Log.e("isi classmain", "main");
            //OrderFragment.orderFragment.load();
            //HomeFragment.homeFragment.refreshCount();
            MainActivity.ClassHome.setBadgeCount();
        }

        if (MainActivity.ClassNew != null) {
            Log.e("isi classmain", "main");
            //OrderFragment.orderFragment.load();
            //HomeFragment.homeFragment.refreshCount();
            MainActivity.ClassNew.setBadgeCount();
        }
        if (MainActivity.ClassOnProcess != null) {
            Log.e("isi classmain", "onprocess");
            //OrderOnProcessFragment.orderOnProcessFragment.load();
            //HomeFragment.homeFragment.refreshCount();
            MainActivity.ClassOnProcess.setBadgeCount();
        }

        if (MainActivity.ClassNotif != null) {
            Log.e("isi classmain", "ClassNotif");
            //NotificationFragment.notificationFragment.load();
            //HomeFragment.homeFragment.refreshCount();
            MainActivity.ClassNotif.setBadgeCount();
        }

    }

    private void showProgressDialog() {
        try {
            mProgressDialog = new ProgressDialog(mContext);
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

    @Override
    public void onResume() {
        super.onResume();
//        countActivity();
        Log.e("swipe", "onResume");
        refreshCount();
//        countActivity();
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void createOrder(View view) {
        mContext = getContext();
        mRelativeLayout = (RelativeLayout) view.findViewById(R.id.order_card);

        // Initialize a new CardView
        CardView card = new CardView(mContext);

        // Set the CardView layoutParams
        LayoutParams params = new LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT
        );
        card.setLayoutParams(params);

        // Set CardView corner radius
        card.setRadius(0);

        // Set cardView content padding
        card.setContentPadding(15, 15, 15, 15);

        // Set a background color for CardView
        card.setCardBackgroundColor(Color.parseColor("#FFC6D6C3"));

        // Set the CardView maximum elevation
        card.setMaxCardElevation(15);

        // Set CardView elevation
        card.setCardElevation(1);

        // Initialize a new TextView to put in CardView
        TextView tv = new TextView(mContext);
        tv.setLayoutParams(params);
        tv.setText("CardView\nProgrammatically");
        tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 30);
        tv.setTextColor(Color.RED);

        // Put the TextView in CardView
        card.addView(tv);
    }
}