package com.agit.bp.mechanicbp.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.agit.bp.mechanicbp.R;
import com.agit.bp.mechanicbp.models.ModelReportWO;
import com.inqbarna.tablefixheaders.adapters.SampleTableAdapter;

import java.util.List;

/**
 * Created by NiyatiR on 10/25/2016.
 */
public class TableWOHistoryAdapter extends SampleTableAdapter {

    //private final int width;//lebar row
    private final int height;//tinggi row
    Activity activity = null;
    Context context;

    private final String headers[] = {
            "No.",
            "Mechanic Name",
            "Start Date",
            "End Date",
            "Order Status",
            "Latitude",
            "Longitude",
            "Time Duration",
            "Note"
    };

    private final int[] widths = {
            60,
            150,
            150,
            150,
            150,
            150,
            150,
            150,
            150
    };
    private final float density;

    public TableWOHistoryAdapter(Activity activity, List<ModelReportWO> reportWO) {
        super(activity);
        this.activity = activity;
        this.context = activity.getApplicationContext();
        Resources resources = context.getResources();
        //width = resources.getDimensionPixelSize(R.dimen.table_width);
        height = resources.getDimensionPixelSize(R.dimen.table_height);
        density = resources.getDisplayMetrics().density;
    }

    @Override
    public int getRowCount() {
        return 10;
    }

    @Override
    public int getColumnCount() {
        return 8;
    }

    @Override
    public int getWidth(int column) {
        //return width;
        return Math.round(widths[column + 1] * density);
    }

    @Override
    public int getHeight(int row) {
        return height;
    }

//        @Override
//        public String getCellString(int row, int column) {
//            return "Lorem (" + row + ", " + column + ")";
//        }


//    @Override
//    public int getLayoutResource(int row, int column) {
//        final int layoutResource;
//        switch (getItemViewType(row, column)) {
//            case 0:
//                layoutResource = R.layout.table_item_header;// ngubah warna dan tampilan header
//                break;
//            case 1:
//                layoutResource = R.layout.table_item_body_odd; //ngubah tampilan warna row
//                break;
//            case 2:
//                layoutResource = R.layout.table_item_body_even; //ngubah tampilan warna row
//                break;
//            default:
//                throw new RuntimeException("wtf?");
//        }
//        return layoutResource;
//    }


    @Override
    public int getLayoutResource(int row, int column) {
        return 0;
    }

    @Override
    public int getItemViewType(int row, int column) {
        if (row < 0) {
            return 0;
        } else {
            if (row % 2 != 0) {
                return 1;
            } else {
                return 2;
            }
        }
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public View getView(int row, int column, View convertView, ViewGroup parent) {
        final View view;
        switch (getItemViewType(row, column)) {
            case 0:
                view = getHeader(row, column, convertView, parent);
                break;
            case 1:
                view = getBody(row, column, convertView, parent);
                break;
            case 2:
                view = getBody(row, column, convertView, parent);
                break;
            default:
                throw new RuntimeException("wtf?");
        }
        return view;
    }

    @Override
    public String getCellString(int row, int column) {
        return null;
    }

    private View getHeader(int row, int column, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = activity.getLayoutInflater().inflate(R.layout.table_item_header, parent, false);
        }
        ((TextView) convertView.findViewById(android.R.id.text1)).setText(headers[column + 1]);
        return convertView;
    }

    private View getBody(int row, int column, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = activity.getLayoutInflater().inflate(R.layout.table_item_body_even, parent, false);
        }
        convertView.setBackgroundResource(row % 2 == 0 ? R.drawable.table_item_body_even : R.drawable.table_item_body_odd);

        if (column == -1) {//1 no.
            ((TextView) convertView.findViewById(android.R.id.text1)).setText(""+(row+1));
        } else if (column == 0) {//2 name
            ((TextView) convertView.findViewById(android.R.id.text1)).setText("Niyati Rizkia");
        } else if (column == 1) {//3 start date
            ((TextView) convertView.findViewById(android.R.id.text1)).setText("21 Oct 2016 14:23:29");
        } else if (column == 2) {//4 end date
            ((TextView) convertView.findViewById(android.R.id.text1)).setText("21 Oct 2016 14:23:35");
        } else if (column == 3) {//5 order status
            ((TextView) convertView.findViewById(android.R.id.text1)).setText("Arrive at Office");
        } else if (column == 4) {//6 latitude
            ((TextView) convertView.findViewById(android.R.id.text1)).setText("-6.1869565");
        } else if (column == 5) {//7 longitude
            ((TextView) convertView.findViewById(android.R.id.text1)).setText("106.9309603");
        } else if (column == 6) {//8 time duration
            ((TextView) convertView.findViewById(android.R.id.text1)).setText("00:00:06");
        } else if (column == 7) {//9 note
            ((TextView) convertView.findViewById(android.R.id.text1)).setText("Finished yaaay...");
        }

        return convertView;
    }
}
