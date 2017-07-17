package com.agit.bp.mechanicbp.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.agit.bp.mechanicbp.R;
import com.agit.bp.mechanicbp.models.ModelWOHeader;

import java.util.List;

/**
 * Created by erwin on 6/14/16.
 */
public class tempOnProcessWOListAdapter extends BaseAdapter {

    private Context mContext;
    private List<ModelWOHeader> mOrderList;
    private String intent;
    String param = "";

    public tempOnProcessWOListAdapter(Context mContext, List<ModelWOHeader> mOrderList) {
        this.mContext = mContext;
        this.mOrderList = mOrderList;
    }

    @Override
    public int getCount() {
        return this.mOrderList.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    String btn_text2;
    String btn_text1;

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View v = View.inflate(mContext, R.layout.adapter_onprocess_fragment_list, null);
        TextView tv_WOId = (TextView) v.findViewById(R.id.tv_WOId);
        tv_WOId.setText(mOrderList.get(i).getSoNumber());

        TextView tv_ordertype = (TextView) v.findViewById(R.id.tv_ordertype);
        tv_ordertype.setText(mOrderList.get(i).getOrderType());

        TextView tv_materialSN = (TextView) v.findViewById(R.id.tv_materialSN);
        tv_materialSN.setText(mOrderList.get(i).getMaterial());

        TextView tv_custname = (TextView) v.findViewById(R.id.tv_custname);
        tv_custname.setText(mOrderList.get(i).getCustomerName());

        TextView tv_address = (TextView) v.findViewById(R.id.tv_address);
        tv_address.setText(mOrderList.get(i).getAddress());

        TextView tv_assigneddate = (TextView) v.findViewById(R.id.tv_assigneddate);
        tv_assigneddate.setText(mOrderList.get(i).getOrderDate());

        TextView tv_scheduleddate = (TextView) v.findViewById(R.id.tv_scheduleddate);
        tv_scheduleddate.setText((String.valueOf(mOrderList.get(i).getStartDate())));

        TextView tv_status = (TextView) v.findViewById(R.id.tv_status);
        tv_status.setText((String.valueOf(mOrderList.get(i).getOrderStatusName())));
//        Intent intent = new Intent(v.getContext(), WriteSignature.class);
//        mContext.startActivity(intent);
        v.setTag(mOrderList.get(i).getMechanicId());
        return v;
    }
}