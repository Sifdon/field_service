package com.agit.bp.mechanicbp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.agit.bp.mechanicbp.R;
import com.agit.bp.mechanicbp.database.DatabaseHelper;
import com.agit.bp.mechanicbp.models.ModelNotification;
import com.agit.bp.mechanicbp.models.ModelStatusWO;

import java.util.List;

/**
 * Created by erwin on 6/14/16.
 */
public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {

    private Context mContext;
    private List<ModelNotification> mOrderList;
    private List<ModelStatusWO> mStatus;
    private DatabaseHelper db;

    public NotificationAdapter(Context mContext, List<ModelNotification> mOrderList) {
        this.mContext = mContext;
        this.mOrderList = mOrderList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View v = inflater.inflate(R.layout.adapter_notification, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int i) {

        holder.tv_titleNotif.setText(mOrderList.get(i).getTitle());
        holder.tv_WONumber.setText(": "+ mOrderList.get(i).getSoNumber());
        holder.tv_CustomerName.setText(": "+ mOrderList.get(i).getCustomerName());
//        holder.tv_address.setText(": "+ mOrderList.get(i).getAddress());
//        holder.tv_wodate.setText(": "+ mOrderList.get(i).getOrderDate());
//        holder.tv_scheduleddate.setText(": "+ (String.valueOf(mOrderList.get(i).getStartDate())));

    }

    @Override
    public int getItemCount() {
        return mOrderList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_titleNotif;
        TextView tv_WONumber;
        TextView tv_CustomerName;

        public MyViewHolder(View v) {
            super(v);
            tv_titleNotif = (TextView) v.findViewById(R.id.tv_titleNotif);
            tv_WONumber = (TextView) v.findViewById(R.id.tv_WONumber);
            tv_CustomerName = (TextView) v.findViewById(R.id.tv_CustomerName);
//            tv_address = (TextView) v.findViewById(R.id.tv_address);
//            tv_wodate = (TextView) v.findViewById(R.id.tv_wodate);
//            tv_scheduleddate = (TextView) v.findViewById(R.id.tv_scheduleddate);
        }
    }
}