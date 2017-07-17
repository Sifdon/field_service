package com.agit.bp.mechanicbp.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.agit.bp.mechanicbp.R;
import com.agit.bp.mechanicbp.adapters.TableTasklistHistoryAdapter;
import com.agit.bp.mechanicbp.adapters.TableWOHistoryAdapter;
import com.agit.bp.mechanicbp.models.ModelReportTasklist;
import com.agit.bp.mechanicbp.models.ModelReportWO;
import com.inqbarna.tablefixheaders.TableFixHeaders;

import java.util.List;

/**
 * Created by NiyatiR on 10/25/2016.
 */
public class DetailReport extends AppCompatActivity {

    List<ModelReportWO> reportWO;
    List<ModelReportTasklist> reportTasklist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_report);

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
        toolbar_title.setText("Detail Report");

        TableFixHeaders table_tasklist = (TableFixHeaders) findViewById(R.id.table_tasklist);
        table_tasklist.setAdapter(new TableTasklistHistoryAdapter(this, reportTasklist));

        TableFixHeaders table_wo = (TableFixHeaders) findViewById(R.id.table_wo);
        table_wo.setAdapter(new TableWOHistoryAdapter(this, reportWO));

    }

    void setValue(){

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
