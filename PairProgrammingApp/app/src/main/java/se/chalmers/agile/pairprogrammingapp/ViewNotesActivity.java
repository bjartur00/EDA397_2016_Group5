package se.chalmers.agile.pairprogrammingapp;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import se.chalmers.agile.pairprogrammingapp.model.Note;
import se.chalmers.agile.pairprogrammingapp.modelview.NotesListAdapter;
import se.chalmers.agile.pairprogrammingapp.utils.ExtraKeys;
import se.chalmers.agile.pairprogrammingapp.utils.StaticTestIds;

public class ViewNotesActivity extends AppCompatActivity implements NotesListAdapter.OnNoteItemClickedListener {
    private final static String KEY_APP_ID = "KEY_APP_ID";

    private String appId = null;

    private RecyclerView rvList;

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

            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                appId = bundle.getString(ExtraKeys.APPLICATION_ID);
            } else {
                appId = null;
            }
        } else {
            appId = savedInstanceState.getString(KEY_APP_ID);
        }

        if (appId != null) {
            init();
        }
    }

    private void init() {
        // Prepare test data
        ArrayList<Note> notes = new ArrayList<>();

        if (appId.equals(StaticTestIds.APP_ID_1)) {
            notes.add(new Note("Note for app 1"));
            notes.add(new Note("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse tincidunt odio sed dui accumsan placerat. Nullam sit amet nisi tortor. Etiam viverra pretium odio sit amet gravida. Sed vel lorem a magna bibendum vehicula. Aliquam tristique tellus ligula, nec gravida turpis elementum vel. Praesent dignissim nulla eget libero eleifend pharetra. Ut vel tellus congue nisl hendrerit accumsan non non augue. Suspendisse facilisis eget lorem at dapibus. Pellentesque dui ante, tempor vel lorem eget, suscipit egestas enim. Proin nec vestibulum lorem. Integer vitae libero lacus. In consectetur ligula a vehicula consequat.\n" +
                    "\n" +
                    "Integer fringilla vestibulum enim nec efficitur. Vivamus viverra id risus in pretium. Suspendisse a scelerisque purus, id tempor mauris. In hac habitasse platea dictumst. Duis cursus purus eget placerat pharetra. Sed laoreet pretium nunc, a posuere nisi auctor egestas. Mauris varius luctus placerat. Ut efficitur sit amet eros sed fermentum. Suspendisse pharetra rhoncus diam, a gravida leo rutrum ac. Quisque quis posuere orci. Cras ut lacus iaculis, dapibus mi sit amet, cursus dui.\n" +
                    "\n" +
                    "Vivamus commodo eu ante eget commodo. Vivamus tellus neque, pellentesque eget mi nec, malesuada molestie orci. Praesent quis tincidunt justo, eget vulputate dolor. Integer dolor mi, dictum at scelerisque tempus, aliquam et nunc. Maecenas nec convallis justo. Aenean vel maximus elit. Suspendisse fringilla bibendum ante.\n" +
                    "\n"));
            notes.add(new Note("test 3"));
        } else if (appId.equals(StaticTestIds.APP_ID_2)) {
            notes.add(new Note("Note for app 2"));
            notes.add(new Note("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse tincidunt odio sed dui accumsan placerat. Nullam sit amet nisi tortor. Etiam viverra pretium odio sit amet gravida. Sed vel lorem a magna bibendum vehicula. Aliquam tristique tellus ligula, nec gravida turpis elementum vel. Praesent dignissim nulla eget libero eleifend pharetra. Ut vel tellus congue nisl hendrerit accumsan non non augue. Suspendisse facilisis eget lorem at dapibus. Pellentesque dui ante, tempor vel lorem eget, suscipit egestas enim. Proin nec vestibulum lorem. Integer vitae libero lacus. In consectetur ligula a vehicula consequat.\n" +
                    "\n" +
                    "Integer fringilla vestibulum enim nec efficitur. Vivamus viverra id risus in pretium. Suspendisse a scelerisque purus, id tempor mauris. In hac habitasse platea dictumst. Duis cursus purus eget placerat pharetra. Sed laoreet pretium nunc, a posuere nisi auctor egestas. Mauris varius luctus placerat. Ut efficitur sit amet eros sed fermentum. Suspendisse pharetra rhoncus diam, a gravida leo rutrum ac. Quisque quis posuere orci. Cras ut lacus iaculis, dapibus mi sit amet, cursus dui.\n" +
                    "\n" +
                    "Vivamus commodo eu ante eget commodo. Vivamus tellus neque, pellentesque eget mi nec, malesuada molestie orci. Praesent quis tincidunt justo, eget vulputate dolor. Integer dolor mi, dictum at scelerisque tempus, aliquam et nunc. Maecenas nec convallis justo. Aenean vel maximus elit. Suspendisse fringilla bibendum ante.\n" +
                    "\n"));
        } else {
            notes.add(new Note("Note for app 3"));
            notes.add(new Note("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse tincidunt odio sed dui accumsan placerat. Nullam sit amet nisi tortor. Etiam viverra pretium odio sit amet gravida. Sed vel lorem a magna bibendum vehicula. Aliquam tristique tellus ligula, nec gravida turpis elementum vel. Praesent dignissim nulla eget libero eleifend pharetra. Ut vel tellus congue nisl hendrerit accumsan non non augue. Suspendisse facilisis eget lorem at dapibus. Pellentesque dui ante, tempor vel lorem eget, suscipit egestas enim. Proin nec vestibulum lorem. Integer vitae libero lacus. In consectetur ligula a vehicula consequat.\n" +
                    "\n" +
                    "Integer fringilla vestibulum enim nec efficitur. Vivamus viverra id risus in pretium. Suspendisse a scelerisque purus, id tempor mauris. In hac habitasse platea dictumst. Duis cursus purus eget placerat pharetra. Sed laoreet pretium nunc, a posuere nisi auctor egestas. Mauris varius luctus placerat. Ut efficitur sit amet eros sed fermentum. Suspendisse pharetra rhoncus diam, a gravida leo rutrum ac. Quisque quis posuere orci. Cras ut lacus iaculis, dapibus mi sit amet, cursus dui.\n" +
                    "\n" +
                    "Vivamus commodo eu ante eget commodo. Vivamus tellus neque, pellentesque eget mi nec, malesuada molestie orci. Praesent quis tincidunt justo, eget vulputate dolor. Integer dolor mi, dictum at scelerisque tempus, aliquam et nunc. Maecenas nec convallis justo. Aenean vel maximus elit. Suspendisse fringilla bibendum ante.\n" +
                    "\n"));
            notes.add(new Note("test note A"));
            notes.add(new Note("test note B"));
            notes.add(new Note("test note C"));
        }

        // Show data
        rvList = (RecyclerView) findViewById(R.id.list);
        rvList.setLayoutManager(new LinearLayoutManager(this));
        rvList.setAdapter(new NotesListAdapter(notes, this));
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);

        // Save the app ID
        outState.putString(KEY_APP_ID, appId);
    }

    @Override
    public void onNoteItemClicked(int position) {
        Toast.makeText(this, position + "", Toast.LENGTH_SHORT).show();
    }
}