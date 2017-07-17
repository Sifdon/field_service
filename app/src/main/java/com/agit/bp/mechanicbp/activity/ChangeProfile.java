package com.agit.bp.mechanicbp.activity;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.agit.bp.mechanicbp.R;

/**
 * Created by NiyatiR on 10/17/2016.
 */
public class ChangeProfile extends AppCompatActivity{

    LinearLayout change_contact_number;
    LinearLayout change_password;

    String field_contact_number = "";
    String field_password_old = "";
    String field_password_new = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_profile);

        change_contact_number = (LinearLayout) findViewById(R.id.change_contact_number);
        change_password = (LinearLayout) findViewById(R.id.change_password);

        //toolbar navbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_navigate_before_white_48dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        change_contact_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogContactNumber();
            }
        });

        change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogPassword();
            }
        });

    }

    private void showDialogContactNumber() {
        //membuat input dialog

        try {
            LayoutInflater li = LayoutInflater.from(ChangeProfile.this);
            View promptsView = li.inflate(R.layout.profile_change_contact, null);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ChangeProfile.this);
            // set prompts.xml to alertdialog builder
            alertDialogBuilder.setView(promptsView);

            final EditText userInput = (EditText) promptsView.findViewById(R.id.field_contact_number);
            final Button btnSave = (Button) promptsView.findViewById(R.id.btnSaveDialog);
            final Button btnBatal = (Button) promptsView.findViewById(R.id.btnBatalDialog);

            // set dialog message
            alertDialogBuilder.setCancelable(false);

            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();
            // show it
            alertDialog.show();

            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    field_contact_number = userInput.getText().toString();
                    if (!field_contact_number.equals("")) {
                        if (alertDialog.isShowing()) {
                            alertDialog.dismiss();
                            //syncLocation();
                        }
                    } else {
                        Toast.makeText(ChangeProfile.this, "Field may not null!", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            btnBatal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (alertDialog.isShowing())
                        alertDialog.dismiss();
                }
            });
        } catch (Exception e) {
            Log.e("error dialog", e.toString());
        }
    }

    private void showDialogPassword() {
        //membuat input dialog

        try {
            LayoutInflater li = LayoutInflater.from(ChangeProfile.this);
            View promptsView = li.inflate(R.layout.profile_change_password, null);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ChangeProfile.this);
            // set prompts.xml to alertdialog builder
            alertDialogBuilder.setView(promptsView);

            final EditText e_field_password_old = (EditText) promptsView.findViewById(R.id.field_password_old);
            final EditText e_field_password_new = (EditText) promptsView.findViewById(R.id.field_password_new);

            final Button btnSave = (Button) promptsView.findViewById(R.id.btnSaveDialog);
            final Button btnBatal = (Button) promptsView.findViewById(R.id.btnBatalDialog);

            // set dialog message
            alertDialogBuilder.setCancelable(false);

            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();
            // show it
            alertDialog.show();

            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    field_password_old = e_field_password_old.getText().toString();
                    field_password_new = e_field_password_new.getText().toString();

                    if (!field_password_old.equals("") || !field_password_new.equals("")) {
                        if (alertDialog.isShowing()) {
                            alertDialog.dismiss();
                            //syncLocation();
                        }
                    } else {
                        Toast.makeText(ChangeProfile.this, "Field may not null!", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            btnBatal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (alertDialog.isShowing())
                        alertDialog.dismiss();
                }
            });
        } catch (Exception e) {
            Log.e("error dialog", e.toString());
        }
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
