package com.agit.bp.mechanicbp.drawable;

/**
 * Created by NiyatiR on 9/1/2016.
 */

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.agit.bp.mechanicbp.R;
import com.mikepenz.actionitembadge.library.ActionItemBadge;
import com.mikepenz.actionitembadge.library.utils.NumberUtils;


public class ToolbarActivity extends AppCompatActivity {

    private static final int SAMPLE2_ID = 34536;
    private int badgeCount = 10;
    private int badgeDrawableCount = 100000000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar_main);


        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        /*if (badgeCount == 0) {
            ActionItemBadge.hide(menu.findItem(R.id.item_samplebadge));
        } else {
            ActionItemBadge.update(this, menu.findItem(R.id.item_samplebadge), FontAwesome.Icon.faw_android, style, badgeCount);
        }
        if (badgeDrawableCount == 0) {
            ActionItemBadge.hide(menu.findItem(R.id.item_sampleBadge_drawable));
        } else {
            ActionItemBadge.update(this, menu.findItem(R.id.item_sampleBadge_drawable), ContextCompat.getDrawable(this, R.drawable.ic_notification), style, NumberUtils.formatNumber(badgeDrawableCount));
        }*/
        //ActionItemBadge.update(this, menu.findItem(R.id.action_notifications), ContextCompat.getDrawable(this, R.drawable.ic_home_24dp), style, NumberUtils.formatNumber(badgeDrawableCount));ActionItemBadge.update(this, menu.findItem(R.id.action_basket), basket, ActionItemBadge.BadgeStyles.BLUE, count);

        ActionItemBadge.update(this, menu.findItem(R.id.action_notifications), ContextCompat.getDrawable(this, R.drawable.ic_notifications_active_white_24dp), ActionItemBadge.BadgeStyles.BLUE, NumberUtils.formatNumber(badgeDrawableCount));
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.ic_notification) {
            Toast.makeText(this, R.string.sample_3, Toast.LENGTH_SHORT).show();
            badgeCount--;
            ActionItemBadge.update(item, badgeCount);
        }
        return super.onOptionsItemSelected(item);
    }
}