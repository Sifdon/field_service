package com.agit.bp.mechanicbp.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.agit.bp.mechanicbp.R;
import com.agit.bp.mechanicbp.models.ModelReportTasklist;
import com.inqbarna.tablefixheaders.adapters.SampleTableAdapter;

import java.util.List;

/**
 * Created by NiyatiR on 10/26/2016.
 */

public class TableTasklistHistoryAdapter extends SampleTableAdapter {

    //private final int width;//lebar row
    private final int height;//tinggi row
    Activity activity = null;
    Context context;

    private final String headers[] = {
            "No.",
            "Tasklist Id",
            "Tasklist",
            "Tasklist Status",
            "Completed By",
            "Time Duration"
    };

    private final int[] widths = {
            60,
            150,
            150,
            150,
            150,
            150
    };
    private final float density;

    public TableTasklistHistoryAdapter(Activity activity, List<ModelReportTasklist> reportTasklist) {
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
        return 5;
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

        if (column == -1) {//1
            ((TextView) convertView.findViewById(android.R.id.text1)).setText(""+(row+1));
        } else if (column == 0) {//2
            ((TextView) convertView.findViewById(android.R.id.text1)).setText("T"+(row+1));
        } else if (column == 1) {//3
            ((TextView) convertView.findViewById(android.R.id.text1)).setText("Tasklist"+(row+1));
        } else if (column == 2) {//4
            ((TextView) convertView.findViewById(android.R.id.text1)).setText("Completed");
        } else if (column == 3) {//5
            ((TextView) convertView.findViewById(android.R.id.text1)).setText("Niyati Rizkia");
        } else if (column == 4) {//6
            ((TextView) convertView.findViewById(android.R.id.text1)).setText("00:00:14");
        }
        return convertView;
    }
}
