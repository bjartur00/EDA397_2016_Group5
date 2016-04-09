package se.chalmers.agile.pairprogrammingapp.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import se.chalmers.agile.pairprogrammingapp.R;
import se.chalmers.agile.pairprogrammingapp.utils.ExtraKeys;

public class EditNoteActivity extends AppCompatActivity {
    private final static String KEY_POSITION = "KEY_POSITION";
    private final static String KEY_CONTENT = "KEY_CONTENT";

    private int mPosition;
    private String mContent;
    private EditText mEtContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(android.R.drawable.ic_menu_close_clear_cancel);

        mEtContent = (EditText) findViewById(R.id.etContent);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                mPosition = extras.getInt(ExtraKeys.NOTE_POSITION);
                mContent = extras.getString(ExtraKeys.NOTE_CONTENT);
            } else {
                mContent = null;
            }
        } else {
            mPosition = savedInstanceState.getInt(KEY_POSITION);
            mContent = savedInstanceState.getString(KEY_CONTENT);
        }

        if (mContent != null) {
            init();
        }
    }

    private void init() {
        EditText etContext = (EditText) findViewById(R.id.etContent);
        etContext.setText(mContent);
        etContext.setSelection(etContext.length());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_note, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

            // set title
            alertDialogBuilder.setTitle("Discard changes");

            // set dialog message
            alertDialogBuilder
                    .setMessage("Are you sure you want to discard your changes!")
                    .setCancelable(false)
                    .setPositiveButton("Discard",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            // Close current activity
                            setResult(RESULT_CANCELED);
                            finish();
                        }
                    })
                    .setNegativeButton("No",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            // Close dialog
                            dialog.cancel();
                        }
                    });

            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();
            return true;
        }
        if (id == R.id.action_save) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

            // set title
            alertDialogBuilder.setTitle("Save changes");

            // set dialog message
            alertDialogBuilder
                    .setMessage("Are you sure you want to save your changes!")
                    .setCancelable(false)
                    .setPositiveButton("Save",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            // Close current activity
                            Intent result = new Intent();
                            result.putExtra(ExtraKeys.NOTE_POSITION, mPosition);
                            result.putExtra(ExtraKeys.NOTE_CONTENT, mEtContent.getText().toString());
                            setResult(RESULT_OK, result);
                            finish();
                        }
                    })
                    .setNegativeButton("No",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            // Close dialog
                            dialog.cancel();
                        }
                    });

            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);

        // Save the note data
        outState.putInt(KEY_POSITION, mPosition);
        outState.putString(KEY_CONTENT, mContent);
    }
}
