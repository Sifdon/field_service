package com.agit.bp.mechanicbp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.agit.bp.mechanicbp.R;
import com.agit.bp.mechanicbp.models.ModelWOHeader;
import com.agit.bp.mechanicbp.models.ParentItem;
import com.h6ah4i.android.widget.advrecyclerview.expandable.ExpandableItemConstants;
import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractExpandableItemAdapter;
import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractExpandableItemViewHolder;

import java.util.List;

/**
 * Created by NiyatiR on 8/9/2016.
 */
public class TestingAdapter extends AbstractExpandableItemAdapter<TestingAdapter.ParentViewHolder, AbstractExpandableItemViewHolder> {
    private static final String TAG = "MyExpandableItemAdapter";

    // NOTE: Make accessible with short name
    private interface Expandable extends ExpandableItemConstants {
    }

    private List<ParentItem> data;
    public static final int FIRST_VIEW_TYPE = 0;
    public static final int SECOND_VIEW_TYPE = 1;

    public static class ParentViewHolder extends AbstractExpandableItemViewHolder {
        //public ExpandableItemIndicator mIndicator;

        public TextView txtParent;
        public ModelWOHeader workOrder;

        public ParentViewHolder(View v) {
            super(v);
            txtParent = (TextView) v.findViewById(R.id.textViewParent);
        }
    }

    public static class ChildViewHolder extends AbstractExpandableItemViewHolder {
        public TextView tv_WOid;
        public TextView tv_custname;
        public TextView tv_address;
        public TextView tv_contactperson;
        public TextView tv_phone;

        public ChildViewHolder(View view) {
            super(view);

            tv_custname = (TextView) view.findViewById(R.id.tv_custname);
            //tv_custname.setText(workOrder.getCustomerName());

            tv_address = (TextView) view.findViewById(R.id.tv_address);
            //tv_address.setText(workOrder.getAddress());

            tv_contactperson = (TextView) view.findViewById(R.id.tv_contactperson);
            //tv_contactperson.setText(workOrder.getContactPerson());

            tv_phone = (TextView) view.findViewById(R.id.tv_phone);
            //tv_phone.setText(String.valueOf(workOrder.getPhone()));
        }
    }

    public static class ChildViewHolder2 extends AbstractExpandableItemViewHolder {

        public TextView tv_materialSN;
        public TextView tv_warranty;
        public TextView tv_ordertype;
        public TextView tv_workcenter;
        public TextView tv_createddate;
        public TextView tv_eststartdate;
        public TextView tv_estfinishdate;
        public TextView tv_description;
        public TextView tv_plant;
        public TextView tv_sparepart;
        public TextView tv_estimateTime;
        public TextView tv_mechanicname;

        public ChildViewHolder2(View view) {
            super(view);

            tv_materialSN = (TextView) view.findViewById(R.id.tv_materialSN);
            //tv_materialSN.setText(workOrder.getMaterial());

            tv_warranty = (TextView) view.findViewById(R.id.tv_warranty);
            //tv_warranty.setText(workOrder.getWarranty());

            tv_ordertype = (TextView) view.findViewById(R.id.tv_ordertype);
            //tv_ordertype.setText(String.valueOf(workOrder.getOrderType()));

            tv_workcenter = (TextView) view.findViewById(R.id.tv_workcenter);
            //tv_workcenter.setText(String.valueOf(workOrder.getWorkCenter()));

            tv_createddate = (TextView) view.findViewById(R.id.tv_createddate);
            //tv_createddate.setText(String.valueOf(workOrder.getOrderDate()));

            //ubah
            tv_eststartdate = (TextView) view.findViewById(R.id.tv_eststartdate);
            //tv_eststartdate.setText(String.valueOf(workOrder.getStartDate()));

            tv_estfinishdate = (TextView) view.findViewById(R.id.tv_estfinishdate);
            //tv_estfinishdate.setText(String.valueOf(workOrder.getEndDate()));
            //

            tv_description = (TextView) view.findViewById(R.id.tv_description);
            //tv_description.setText(String.valueOf(workOrder.getRemark()));

            //ubah
            tv_plant = (TextView) view.findViewById(R.id.tv_plant);
            //tv_plant.setText(String.valueOf(workOrder.getPlant()));

            tv_sparepart = (TextView) view.findViewById(R.id.tv_sparepart);
            //tv_sparepart.setText(String.valueOf(workOrder.getSparepart()));

            tv_estimateTime = (TextView) view.findViewById(R.id.tv_estimateTime);
            //tv_estimateTime.setText(String.valueOf(workOrder.getEstimateTime()));

        }
    }

    public TestingAdapter(List<ParentItem> data) {
        //mProvider = dataProvider;
        this.data = data;
        // ExpandableItemAdapter requires stable ID, and also
        // have to implement the getGroupItemId()/getChildItemId() methods appropriately.
        setHasStableIds(true);
    }

    @Override
    public int getGroupCount() {
        return data.size();
    }

    @Override
    public int getChildCount(int groupPosition) {
        //return data.get(groupPosition).childItems.size();
        return 1;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return data.get(groupPosition).id;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        //return data.get(groupPosition).modelWOHeader.;
        return 1;
    }

    @Override
    public int getGroupItemViewType(int groupPosition) {
        return 0;
    }

    @Override
    public int getChildItemViewType(int groupPosition, int childPosition) {
        return data.get(groupPosition).viewType;
    }

    @Override
    public ParentViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View v = inflater.inflate(R.layout.item_parent, parent, false);
        return new ParentViewHolder(v);
    }

    @Override
    public AbstractExpandableItemViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if (viewType == FIRST_VIEW_TYPE) {
            final View v = inflater.inflate(R.layout.item_child, parent, false);
            return new ChildViewHolder(v);
        } else { // view type 2, // paham ? iyap, sudah
            // dicoba set
            final View v = inflater.inflate(R.layout.item_child2, parent, false);
            return new ChildViewHolder2(v);
        }
    }

    @Override
    public void onBindGroupViewHolder(ParentViewHolder holder, int groupPosition, int viewType) {
        // child item
        //final AbstractExpandableDataProvider.BaseData item = mProvider.getGroupItem(groupPosition);
        ParentItem parentItem = data.get(groupPosition);

        holder.txtParent.setText(parentItem.name);

        // mark as clickable
        holder.itemView.setClickable(true);
    }

    @Override
    public void onBindChildViewHolder(AbstractExpandableItemViewHolder holder, int groupPosition, int childPosition, int viewType) {
        // group item
        //final AbstractExpandableDataProvider.ChildData item = mProvider.getChildItem(groupPosition, childPosition);
        if (viewType == SECOND_VIEW_TYPE) { // buat layout tipe 1
            ((ChildViewHolder2) holder).tv_materialSN.setText(data.get(groupPosition).modelWOHeader.getMaterial());
            ((ChildViewHolder2) holder).tv_warranty.setText(data.get(groupPosition).modelWOHeader.getWarranty());
            ((ChildViewHolder2) holder).tv_ordertype.setText(data.get(groupPosition).modelWOHeader.getOrderType());
            ((ChildViewHolder2) holder).tv_workcenter.setText(data.get(groupPosition).modelWOHeader.getWorkCenter());
            ((ChildViewHolder2) holder).tv_createddate.setText(data.get(groupPosition).modelWOHeader.getOrderDate());
            ((ChildViewHolder2) holder).tv_eststartdate.setText(data.get(groupPosition).modelWOHeader.getStartDate());
            ((ChildViewHolder2) holder).tv_estfinishdate.setText(data.get(groupPosition).modelWOHeader.getEndDate());
            ((ChildViewHolder2) holder).tv_description.setText(data.get(groupPosition).modelWOHeader.getRemark());
            ((ChildViewHolder2) holder).tv_plant.setText(data.get(groupPosition).modelWOHeader.getPlant());
            ((ChildViewHolder2) holder).tv_sparepart.setText(data.get(groupPosition).modelWOHeader.getSparepart());
            ((ChildViewHolder2) holder).tv_estimateTime.setText(data.get(groupPosition).modelWOHeader.getEstimateTime());

        } else if (viewType == FIRST_VIEW_TYPE) { // buat layout tipe 2

            ((ChildViewHolder) holder).tv_custname.setText(data.get(groupPosition).modelWOHeader.getCustomerName()); // ini
            ((ChildViewHolder) holder).tv_address.setText(data.get(groupPosition).modelWOHeader.getAddress()); // ini
            ((ChildViewHolder) holder).tv_contactperson.setText(data.get(groupPosition).modelWOHeader.getContactPerson()); // ini
            ((ChildViewHolder) holder).tv_phone.setText(data.get(groupPosition).modelWOHeader.getPhone()); // ini
        }
    }

    @Override
    public boolean onCheckCanExpandOrCollapseGroup(ParentViewHolder holder, int groupPosition, int x, int y, boolean expand) {
        // check is enabled
        if (!(holder.itemView.isEnabled() && holder.itemView.isClickable())) {
            return false;
        }
        return true;
    }
}
