package se.chalmers.agile.pairprogrammingapp.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import se.chalmers.agile.pairprogrammingapp.R;
import se.chalmers.agile.pairprogrammingapp.model.Note;
import se.chalmers.agile.pairprogrammingapp.modelview.NotesListAdapter;
import se.chalmers.agile.pairprogrammingapp.utils.ExtraKeys;
import se.chalmers.agile.pairprogrammingapp.utils.StaticTestIds;

public class ViewNotesActivity extends AppCompatActivity implements NotesListAdapter.OnNoteItemClickedListener {
    private final static String KEY_APP_ID = "KEY_APP_ID";
    private final static int EDIT_REQUEST_CODE = 5000;
    private final static int NEW_NOTE_POSITION = -2;

    private String mAppId = null;
    private ArrayList<Note> mNotes;

    private NotesListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_notes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        // TODO use this to create a new note
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewNotesActivity.this, EditNoteActivity.class);
                intent.putExtra(ExtraKeys.NOTE_POSITION, NEW_NOTE_POSITION);

                startActivityForResult(intent, EDIT_REQUEST_CODE);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                mAppId = extras.getString(ExtraKeys.APPLICATION_ID);
            } else {
                mAppId = null;
            }
        } else {
            mAppId = savedInstanceState.getString(KEY_APP_ID);
        }

        if (mAppId != null) {
            init();
        }
    }

    private void init() {
        // Prepare test data
        mNotes = new ArrayList<>();

        if (mAppId.equals(StaticTestIds.APP_ID_1)) {
            mNotes.add(new Note("Note for app 1"));
            mNotes.add(new Note("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse tincidunt odio sed dui accumsan placerat. Nullam sit amet nisi tortor. Etiam viverra pretium odio sit amet gravida. Sed vel lorem a magna bibendum vehicula. Aliquam tristique tellus ligula, nec gravida turpis elementum vel. Praesent dignissim nulla eget libero eleifend pharetra. Ut vel tellus congue nisl hendrerit accumsan non non augue. Suspendisse facilisis eget lorem at dapibus. Pellentesque dui ante, tempor vel lorem eget, suscipit egestas enim. Proin nec vestibulum lorem. Integer vitae libero lacus. In consectetur ligula a vehicula consequat.\n" +
                    "\n" +
                    "Integer fringilla vestibulum enim nec efficitur. Vivamus viverra id risus in pretium. Suspendisse a scelerisque purus, id tempor mauris. In hac habitasse platea dictumst. Duis cursus purus eget placerat pharetra. Sed laoreet pretium nunc, a posuere nisi auctor egestas. Mauris varius luctus placerat. Ut efficitur sit amet eros sed fermentum. Suspendisse pharetra rhoncus diam, a gravida leo rutrum ac. Quisque quis posuere orci. Cras ut lacus iaculis, dapibus mi sit amet, cursus dui.\n" +
                    "\n" +
                    "Vivamus commodo eu ante eget commodo. Vivamus tellus neque, pellentesque eget mi nec, malesuada molestie orci. Praesent quis tincidunt justo, eget vulputate dolor. Integer dolor mi, dictum at scelerisque tempus, aliquam et nunc. Maecenas nec convallis justo. Aenean vel maximus elit. Suspendisse fringilla bibendum ante.\n" +
                    "\n"));
            mNotes.add(new Note("test 3"));
        } else if (mAppId.equals(StaticTestIds.APP_ID_2)) {
            mNotes.add(new Note("Note for app 2"));
            mNotes.add(new Note("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse tincidunt odio sed dui accumsan placerat. Nullam sit amet nisi tortor. Etiam viverra pretium odio sit amet gravida. Sed vel lorem a magna bibendum vehicula. Aliquam tristique tellus ligula, nec gravida turpis elementum vel. Praesent dignissim nulla eget libero eleifend pharetra. Ut vel tellus congue nisl hendrerit accumsan non non augue. Suspendisse facilisis eget lorem at dapibus. Pellentesque dui ante, tempor vel lorem eget, suscipit egestas enim. Proin nec vestibulum lorem. Integer vitae libero lacus. In consectetur ligula a vehicula consequat.\n" +
                    "\n" +
                    "Integer fringilla vestibulum enim nec efficitur. Vivamus viverra id risus in pretium. Suspendisse a scelerisque purus, id tempor mauris. In hac habitasse platea dictumst. Duis cursus purus eget placerat pharetra. Sed laoreet pretium nunc, a posuere nisi auctor egestas. Mauris varius luctus placerat. Ut efficitur sit amet eros sed fermentum. Suspendisse pharetra rhoncus diam, a gravida leo rutrum ac. Quisque quis posuere orci. Cras ut lacus iaculis, dapibus mi sit amet, cursus dui.\n" +
                    "\n" +
                    "Vivamus commodo eu ante eget commodo. Vivamus tellus neque, pellentesque eget mi nec, malesuada molestie orci. Praesent quis tincidunt justo, eget vulputate dolor. Integer dolor mi, dictum at scelerisque tempus, aliquam et nunc. Maecenas nec convallis justo. Aenean vel maximus elit. Suspendisse fringilla bibendum ante.\n" +
                    "\n"));
        } else {
            mNotes.add(new Note("Note for app 3"));
            mNotes.add(new Note("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse tincidunt odio sed dui accumsan placerat. Nullam sit amet nisi tortor. Etiam viverra pretium odio sit amet gravida. Sed vel lorem a magna bibendum vehicula. Aliquam tristique tellus ligula, nec gravida turpis elementum vel. Praesent dignissim nulla eget libero eleifend pharetra. Ut vel tellus congue nisl hendrerit accumsan non non augue. Suspendisse facilisis eget lorem at dapibus. Pellentesque dui ante, tempor vel lorem eget, suscipit egestas enim. Proin nec vestibulum lorem. Integer vitae libero lacus. In consectetur ligula a vehicula consequat.\n" +
                    "\n" +
                    "Integer fringilla vestibulum enim nec efficitur. Vivamus viverra id risus in pretium. Suspendisse a scelerisque purus, id tempor mauris. In hac habitasse platea dictumst. Duis cursus purus eget placerat pharetra. Sed laoreet pretium nunc, a posuere nisi auctor egestas. Mauris varius luctus placerat. Ut efficitur sit amet eros sed fermentum. Suspendisse pharetra rhoncus diam, a gravida leo rutrum ac. Quisque quis posuere orci. Cras ut lacus iaculis, dapibus mi sit amet, cursus dui.\n" +
                    "\n" +
                    "Vivamus commodo eu ante eget commodo. Vivamus tellus neque, pellentesque eget mi nec, malesuada molestie orci. Praesent quis tincidunt justo, eget vulputate dolor. Integer dolor mi, dictum at scelerisque tempus, aliquam et nunc. Maecenas nec convallis justo. Aenean vel maximus elit. Suspendisse fringilla bibendum ante.\n" +
                    "\n"));
            mNotes.add(new Note("test note A"));
            mNotes.add(new Note("test note B"));
            mNotes.add(new Note("test note C"));
        }

        // Show data
        RecyclerView rvList = (RecyclerView) findViewById(R.id.list);
        rvList.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new NotesListAdapter(mNotes, this);
        rvList.setAdapter(mAdapter);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);

        // Save the app ID
        outState.putString(KEY_APP_ID, mAppId);
    }

    @Override
    public void onNoteItemClicked(int position) {
        Intent intent = new Intent(this, EditNoteActivity.class);
        intent.putExtra(ExtraKeys.NOTE_POSITION, position);
        intent.putExtra(ExtraKeys.NOTE_CONTENT, mNotes.get(position).getContent());

        startActivityForResult(intent, EDIT_REQUEST_CODE);
    }

    @Override
    public void onDeleteNoteClicked(final int position) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // set title
        alertDialogBuilder.setTitle("Delete note");

        // set dialog message
        alertDialogBuilder
                .setMessage("Are you sure you want to delete the note?")
                .setCancelable(false)
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Delete the note
                        mAdapter.deleteItem(position);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Close the dialog
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK) {
            if (requestCode == EDIT_REQUEST_CODE) {
                int position = data.getIntExtra(ExtraKeys.NOTE_POSITION, NEW_NOTE_POSITION);
                String content = data.getStringExtra(ExtraKeys.NOTE_CONTENT);

                if (position == NEW_NOTE_POSITION) {
                    mNotes.add(new Note(content));
                    mAdapter.notifyItemAdded();
                } else {
                    mNotes.get(position).setContent(content);
                    mAdapter.notifyItemModified(position);
                }
            }
        }
    }
}