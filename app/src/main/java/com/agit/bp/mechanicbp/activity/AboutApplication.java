package com.agit.bp.mechanicbp.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.style.RelativeSizeSpan;
import android.view.Menu;
import android.view.MenuItem;

import com.agit.bp.mechanicbp.R;
import com.bluejamesbond.text.DocumentView;
import com.bluejamesbond.text.style.TextAlignment;
import com.bluejamesbond.text.util.ArticleBuilder;

/**
 * Created by NiyatiR on 9/21/2016.
 */
public class AboutApplication extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutapps);

        //toolbar navbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_navigate_before_white_48dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        DocumentView description2 = (DocumentView) findViewById(R.id.description2);
        ArticleBuilder a = new ArticleBuilder();
        a.append("<font color=#009788><b>digiForce Field Service</b></font> is an application to ease and accelerate mechanic activity through the use of mobile applications to receive work order and update work order status. Ease in dispatching work order and monitoring all mechanics job using web application. Technology that used is combination of Java-based web application and native Android mobile application that can help mechanic to get the assignment anywhere and report his activity in real time.", false, new RelativeSizeSpan(1.0f));

//        DocumentView documentView = new DocumentView(this, DocumentView.PLAIN_TEXT);  // Support plain text
        description2.getDocumentLayoutParams().setTextAlignment(TextAlignment.JUSTIFIED);
        description2.setText(a); // Set to `true` to enable justification


        DocumentView description3 = (DocumentView) findViewById(R.id.description3);
        ArticleBuilder a3 = new ArticleBuilder();
        a3.append("<font color=#009788><b>PT. Bina Pertiwi</b></font> " +
                "", false, new RelativeSizeSpan(1.0f));

        a3.append("Jl. Raya Bekasi Km. 22, Daerah Khusus Ibukota Jakarta 13910", false, new RelativeSizeSpan(0.8f));

        a3.append("(021) 46823539", true, new RelativeSizeSpan(0.8f));
        a3.append("<font color=#FF0000><b>Application Version : digiForce Field Service v1.3</b></font>", false, new RelativeSizeSpan(0.8f));

//        DocumentView documentView = new DocumentView(this, DocumentView.PLAIN_TEXT);  // Support plain text
        description3.getDocumentLayoutParams().setTextAlignment(TextAlignment.JUSTIFIED);
        description3.setText(a3); // Set to `true` to enable justification
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
