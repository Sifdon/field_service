package com.agit.bp.mechanicbp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.agit.bp.mechanicbp.R;
import com.agit.bp.mechanicbp.database.DatabaseHelper;
import com.agit.bp.mechanicbp.models.ModelStatusWO;
import com.agit.bp.mechanicbp.models.ModelWOHeader;

import java.util.List;

/**
 * Created by erwin on 6/14/16.
 */
public class CustomerInfo extends RecyclerView.Adapter<CustomerInfo.MyViewHolder> {

    private Context mContext;
    private ModelWOHeader mOrderList;
    private List<ModelStatusWO> mStatus;
    private DatabaseHelper db;

    public CustomerInfo(Context mContext, ModelWOHeader mOrderList) {
        this.mContext = mContext;
        this.mOrderList = mOrderList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View v = inflater.inflate(R.layout.item_child, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int i) {

        holder.tv_custname.setText(mOrderList.getCustomerName()); // ini
        holder.tv_address.setText(mOrderList.getAddress()); // ini
        holder.tv_contactperson.setText(mOrderList.getContactPerson()); // ini
        holder.tv_phone.setText(mOrderList.getPhone()); // ini
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_custname;
        public TextView tv_address;
        public TextView tv_contactperson;
        public TextView tv_phone;

        public MyViewHolder(View view) {
            super(view);
            tv_custname = (TextView) view.findViewById(R.id.tv_custname);
            //tv_custname.setText(workOrder.getCustomerName());

            tv_address = (TextView) view.findViewById(R.id.tv_address);
            //tv_address.setText(workOrder.getAddress());

            tv_contactperson = (TextView) view.findViewById(R.id.tv_contactperson);
            //tv_contactperson.setText(workOrder.getContactPerson());

            tv_phone = (TextView) view.findViewById(R.id.tv_phone);
        }
    }
}