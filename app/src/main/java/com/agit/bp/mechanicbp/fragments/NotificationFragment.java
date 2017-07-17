package com.agit.bp.mechanicbp.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.agit.bp.mechanicbp.R;
import com.agit.bp.mechanicbp.activity.DetailWOActivity;
import com.agit.bp.mechanicbp.activity.MainActivity;
import com.agit.bp.mechanicbp.adapters.NotificationAdapter;
import com.agit.bp.mechanicbp.database.DatabaseHelper;
import com.agit.bp.mechanicbp.models.ModelDeletedWO;
import com.agit.bp.mechanicbp.models.ModelNotification;
import com.agit.bp.mechanicbp.models.ModelWOHeader;
import com.agit.bp.mechanicbp.models.ResponseNewWO;
import com.agit.bp.mechanicbp.services.ItemClickSupport;
import com.agit.bp.mechanicbp.services.RESTService;
import com.agit.bp.mechanicbp.services.ServiceFactory;
import com.agit.bp.mechanicbp.services.SessionManager;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

//import com.agit.bp.mechanicbp.activity.WorkOrderDetail;

public class NotificationFragment extends Fragment implements AdapterView.OnItemClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters
    private Context mContext;
    RelativeLayout mRelativeLayout;
    private OnFragmentInteractionListener mListener;

    private RecyclerView mRecyclerView;

    private NotificationAdapter orderAdapter;

    private List<ModelNotification> orderList;
    List<ModelNotification> dataWO;
    private List<ModelWOHeader> allWO;
    private List<ModelDeletedWO> isDeletedWO;
    private ModelWOHeader dataWONotif;


    private View view;
    private SwipeRefreshLayout swipeRefreshLayout;
    private DatabaseHelper db;

    private SessionManager sessionManager;
    private NestedScrollView scrollView;

    ProgressDialog mProgressDialog;

    private LinearLayout empty_order_layout;
//    private ImageView empty_order;

    public static NotificationFragment notificationFragment = null;

    public NotificationFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static NotificationFragment newInstance() {
        NotificationFragment fragment = new NotificationFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //orderFragment = this;
        sessionManager = new SessionManager(getContext());
        db = new DatabaseHelper(getActivity());
        notificationFragment = this;
        mContext = getContext();
        syncDataWO();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_notification, container, false);

//        empty_order = (ImageView) view.findViewById(R.id.empty_order);
        scrollView = (NestedScrollView) view.findViewById(R.id.scrollView);
        empty_order_layout = (LinearLayout) view.findViewById(R.id.empty_order_layout);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.list_notification);
        RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLayoutManager1);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setHasFixedSize(false);
        orderList = new ArrayList<>();
        orderAdapter = new NotificationAdapter(getContext(), orderList);
        mRecyclerView.setAdapter(orderAdapter);


        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this::syncDataWO);
        Log.d("swiperefresh", String.valueOf(swipeRefreshLayout));

        // ini yang bikin loadingnya ada diatas
        swipeRefreshLayout.setRefreshing(true);

        ItemClickSupport.addTo(mRecyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                //

                dataWONotif = db.getRecentStatusWO(orderList.get(position).getOrderHeaderId()).get(0);

                Log.e("Klik Listview New", "Klik Listview New");
                Intent intent = new Intent(getActivity(), DetailWOActivity.class);
                intent.putExtra("submit", dataWONotif);
                startActivity(intent);
            }
        });

        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {

            @Override
            public void onScrollChanged() {
                int scrollY = scrollView.getScrollY();
                if (scrollY == 0)
                    swipeRefreshLayout.setEnabled(true);
                else
                    swipeRefreshLayout.setEnabled(false);
            }
        });

        syncDataWO();

        return view;
    }

    public void syncDataWO() {
        Log.e("dari message receive", "lalalala");
        Log.e("isi orderfrgament", sessionManager.getUserId());

        isDeletedWO = new ArrayList<>();
        allWO = db.getAllWO("all");
        if (allWO != null) {
            for (int i = 0; i < allWO.size(); i++) {
                ModelDeletedWO tempDeletedWO = new ModelDeletedWO();
                tempDeletedWO.setOrderHeaderId(allWO.get(i).getOrderHeaderId());
                tempDeletedWO.setExist(false);
                isDeletedWO.add(tempDeletedWO);
            }
            Log.e("size allWO", "" + allWO.size());
            Log.e("size isDeletedWO", "" + isDeletedWO.size());
        }

        try {
            RESTService RESTService = ServiceFactory.createRetrofitService(RESTService.class);
            RESTService.getAllWO(sessionManager.getUserId())
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
                                //Toast.makeText(getContext(), "Bad Internet Connection...", Toast.LENGTH_SHORT).show();
                                Log.e("masuk onError getAllWO", ""+e.toString());
                                load();
                            } catch (Exception eOnError) {
                                Log.e("this error", ""+eOnError);
                            }
                        }

                        @Override
                        public void onNext(ResponseNewWO response) {
                            Log.e("MASUK ONNEXT syncDataWO", response.getStatus());
                            try {
                                if (response.getStatus().equals("SUCCESS")) {
                                    int size = response.getResult().size();
                                    if (size != 0) {
                                        List<ModelWOHeader> wo = response.getResult();
                                        for (int i = 0; i < size; i++) {
                                            if (db.checkIDWO(wo.get(i).getOrderHeaderId()) == false) {
                                                Log.e("isi size WO", "" + wo.size());
                                                Log.e("isi size tasklist", "" + wo.get(i).getOrderItemView().size());
                                                Log.e("start insert wo", "");
                                                Log.e("isi order header id : ", "" + wo.get(i).getOrderHeaderId());
                                                db.writeWO(wo.get(i));
                                                Log.e("start insert tasklist", "");
                                                for (int j = 0; j < wo.get(i).getOrderItemView().size(); j++) {
                                                    db.writeTasklist(wo.get(i).getOrderItemView().get(j));
                                                }
                                            } else {
                                                if(wo.get(i).getOrderStatus() == 5055) {
                                                    int checkresult = db.updateStatusWOApproval(wo.get(i).getOrderHeaderId(), wo.get(i).getOrderStatus(), wo.get(i).getOrderStatusName());
                                                    //}else if(wo.get(i).getOrderStatus() == 5064){
                                                }else{
                                                    for (int j = 0; j < wo.get(i).getOrderItemView().size(); j++) {
                                                        //db.writeTasklist(wo.get(i).getOrderItemView().get(j));
                                                        if(wo.get(i).getOrderItemView().get(j).getOrderItemStatus() == 7004){
                                                            db.updateStatusTasklist(wo.get(i).getOrderHeaderId(), wo.get(i).getOrderItemView().get(j).getOrderiItemId(), wo.get(i).getOrderItemView().get(j).getOrderItemStatus(), wo.get(i).getOrderItemView().get(j).getOrderItemStatusName(), wo.get(i).getOrderItemView().get(j).getActionDate());
                                                        }

                                                    }
                                                }


                                                Log.e("WO id asli: ", "" + wo.get(i).getOrderHeaderId());
                                                for (int x = 0; x < isDeletedWO.size(); x++) {
                                                    Log.e("WO id IS : " + x, "" + isDeletedWO.get(x).getOrderHeaderId());
                                                    if (isDeletedWO.get(x).getOrderHeaderId().equals(wo.get(i).getOrderHeaderId())) {
                                                        isDeletedWO.get(x).setExist(true);
                                                        Log.e("set true", "" + isDeletedWO.get(x).getOrderHeaderId());
                                                        break;
                                                    }
                                                }

                                            }
                                        }
                                        for (int x = 0; x < isDeletedWO.size(); x++) {
                                            Log.e("orderheaderid : ", "" + isDeletedWO.get(x).getOrderHeaderId());
                                            Log.e("exist : ", "" + isDeletedWO.get(x).isExist());
                                            if (isDeletedWO.get(x).isExist() == false) {
                                                Log.e("ini harus dihapus", isDeletedWO.get(x).getOrderHeaderId());
                                                long checkdeleteWO = db.deleteWO(isDeletedWO.get(x).getOrderHeaderId());
                                                //long checkdeleteTasklist = db.deleteRejectedWOTasklist(isDeletedWO.get(x).getOrderHeaderId());
                                            }
                                        }
                                    } else {
                                        //delete di dalam mobile jika ada
                                        Log.e("masuk SUKSES size 0", "syncdatawo");
                                        if (isDeletedWO.size() != 0) {
                                            db.deleteAllWO();
                                        }
                                    }
                                } else if (response.getStatus().equals("EMPTY")) {
                                    Log.e("masuk FAILED size 0", "syncdatawo");
                                    if (isDeletedWO.size() != 0) {
                                        Log.e("masuk if size!=0", "delete all");
                                        db.deleteAllWO();
                                    }
                                }
                            } catch (Throwable throwable) {
                                //Toast.makeText(getContext(), "Failed to insert data : " + throwable, Toast.LENGTH_SHORT).show();
                                load();
                            } finally {
                                load();
                            }
                        }
                    });

        } catch (Exception e) {
            Log.e("error run retrofit", "error close before");
        }
    }

    public void load() {
        //db.updateCountNotif(1, "");
        Log.e("MASUK METHOD LOAD", "MASUK LOAD");
        if (orderList != null) {
            orderList.clear();
            orderAdapter.notifyDataSetChanged();
        }
        dataWO = new ArrayList<>();
        dataWO = db.getAllNotification();

        boolean visibleEmptyWO = false;
        try {
            int size = 0;
            size = dataWO.size();
            Log.e("size LOAD notif", "" + size);
            if (size == 0) {
                Log.e("MASUK NULL Notif", "MASUK NULL Notif");
                visibleEmptyWO = true;

            } else {
                Log.e("MASUK ADA DATA", "MASUK ADA DATA");

                visibleEmptyWO = false;

                if (orderList != null) {
                    orderList.clear();
                    orderAdapter.notifyDataSetChanged();
                }
                orderList.addAll(dataWO);

                orderAdapter.notifyDataSetChanged();
                mRecyclerView.setAdapter(orderAdapter);
            }
        } catch (Exception e) {
            visibleEmptyWO = false;
            Log.e("isi e", "" + e.toString());
        } finally {

            Log.e("isi visibleEmptyWO", "" + visibleEmptyWO);
            if (visibleEmptyWO == true) {
                // tidak ada datanya
//                swipeRefreshLayout.setVisibility(View.GONE);
                empty_order_layout.setVisibility(View.VISIBLE);
                scrollView.setVisibility(View.GONE);
            } else {
                //ada datanya
//                swipeRefreshLayout.setVisibility(View.VISIBLE);
                scrollView.setVisibility(View.VISIBLE);
                empty_order_layout.setVisibility(View.GONE);
            }

            if (MainActivity.ClassHome != null) {
                Log.e("isi classmain", "main");
                //OrderFragment.orderFragment.load();
                MainActivity.ClassHome.setBadgeCount();
            }

            if (MainActivity.ClassNew != null) {
                Log.e("isi classmain", "main");
                //OrderFragment.orderFragment.load();
                MainActivity.ClassNew.setBadgeCount();
            }
            if (MainActivity.ClassOnProcess != null) {
                Log.e("isi classmain", "onprocess");
                //OrderOnProcessFragment.orderOnProcessFragment.load();
                MainActivity.ClassOnProcess.setBadgeCount();
            }

            if (MainActivity.ClassNotif != null) {
                Log.e("isi classmain", "ClassNotif");
                //NotificationFragment.notificationFragment.load();
                MainActivity.ClassNotif.setBadgeCount();
            }

            hideProgressDialog();
            swipeRefreshLayout.setRefreshing(false);
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
        syncDataWO();
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
    public void onDestroy() {
        super.onDestroy();
        notificationFragment = null;

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //Log.e("Klik Listview New", "Klik Listview New");

        dataWONotif = db.getRecentStatusWO(orderList.get(i).getOrderHeaderId()).get(0);

//        Intent intent = new Intent(getActivity(), DetailWOActivity.class);
//        intent.putExtra("submit", dataWONotif);
//        startActivityForResult(intent, 0);
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