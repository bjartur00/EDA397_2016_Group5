package se.chalmers.agile.pairprogrammingapp.fragments;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import se.chalmers.agile.pairprogrammingapp.PairProgrammingApplication;
import se.chalmers.agile.pairprogrammingapp.R;
import se.chalmers.agile.pairprogrammingapp.activities.EditNoteActivity;
import se.chalmers.agile.pairprogrammingapp.model.GetNotesResponse;
import se.chalmers.agile.pairprogrammingapp.model.Note;
import se.chalmers.agile.pairprogrammingapp.modelview.NotesListAdapter;
import se.chalmers.agile.pairprogrammingapp.network.JsonSerializer;
import se.chalmers.agile.pairprogrammingapp.network.RequestHandler;
import se.chalmers.agile.pairprogrammingapp.network.TrelloUrls;
import se.chalmers.agile.pairprogrammingapp.utils.ExtraKeys;

public class DisplayNotesFragment extends Fragment implements NotesListAdapter.OnNoteItemClickedListener {
    private final static String TAG = "se.chalmers.agile.pairprogrammingapp.fragments.DisplayNotesFragment";

    private static final String ARG_PROJECT_ID = "projectId";
    private final static int EDIT_REQUEST_CODE = 5000;
    private final static int NEW_NOTE_POSITION = -2;

    private String mProjectId;
    private String mNotesListId;
    private ArrayList<Note> mNotes;
    private NotesListAdapter mAdapter;


    public DisplayNotesFragment() {
        // Required empty public constructor
    }

    /**
     * Returns a new instance of the DisplayNotesFragment fragment used to display
     * notes of the project with the passed in project ID.
     *
     * @param projectId Project ID.
     * @return A new instance of fragment DisplayNotesFragment.
     */
    public static DisplayNotesFragment newInstance(String projectId) {
        DisplayNotesFragment fragment = new DisplayNotesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PROJECT_ID, projectId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mProjectId = getArguments().getString(ARG_PROJECT_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_display_notes, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RequestHandler.loadJsonArrayGet(TrelloUrls.getNotesUrl(mProjectId, ((PairProgrammingApplication) getActivity().getApplication()).getToken()),
                new RequestHandler.OnJsonArrayLoadedListener() {
                    @Override
                    public void onJsonDataLoadedSuccessfully(JSONArray data) {
                        GetNotesResponse response = JsonSerializer.json2Notes(data);
                        mNotesListId = response.getNotesListId();
                        mNotes = response.getNotes();

                        // Show data
                        RecyclerView rvList = (RecyclerView) getView().findViewById(R.id.list);
                        rvList.setLayoutManager(new LinearLayoutManager(getContext()));
                        mAdapter = new NotesListAdapter(mNotes, DisplayNotesFragment.this);
                        rvList.setAdapter(mAdapter);
                    }

                    @Override
                    public void onJsonDataLoadingFailure(int errorId) {
                    }
                }, Request.Priority.HIGH, TAG);
    }

    @Override
    public void onNoteItemClicked(int position) {
        Intent intent = new Intent(getActivity(), EditNoteActivity.class);
        intent.putExtra(ExtraKeys.NOTE_POSITION, position);
        intent.putExtra(ExtraKeys.NOTE_CONTENT, mNotes.get(position).getContent());

        startActivityForResult(intent, EDIT_REQUEST_CODE);
    }

    @Override
    public void onDeleteNoteClicked(final int position) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

        // set title
        alertDialogBuilder.setTitle("Delete note");

        // set dialog message
        alertDialogBuilder
                .setMessage("Are you sure you want to delete the note?")
                .setCancelable(false)
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        deleteNoteOnTrello(position);
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

    public void addNewNote() {
        Intent intent = new Intent(getActivity(), EditNoteActivity.class);
        intent.putExtra(ExtraKeys.NOTE_POSITION, NEW_NOTE_POSITION);

        startActivityForResult(intent, EDIT_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == EDIT_REQUEST_CODE) {
                int position = data.getIntExtra(ExtraKeys.NOTE_POSITION, NEW_NOTE_POSITION);
                String content = data.getStringExtra(ExtraKeys.NOTE_CONTENT);
                if (position == NEW_NOTE_POSITION) {
                    addNoteOnTrello(content);
                } else {
                    editNoteOnTrello(position, content);
                }
            }
        }
    }

    private void addNoteOnTrello(final String content) {
        Map<String, String> params = new HashMap<>();
        RequestHandler.loadJsonDataPost(
                TrelloUrls.addNoteUrl(
                        mNotesListId,
                        content,
                        JsonSerializer.sDateFormat.format(new Date()),
                        ((PairProgrammingApplication) getActivity().getApplication()).getToken()),
                new RequestHandler.OnJsonDataLoadedListener() {
                    @Override
                    public void onJsonDataLoadedSuccessfully(JSONObject data) {
                        mNotes.add(JsonSerializer.json2Note(data));
                        mAdapter.notifyItemAdded();
                    }

                    @Override
                    public void onJsonDataLoadingFailure(int errorId) {
                    }
                },
                params,
                Request.Priority.HIGH,
                TAG);
    }

    private void editNoteOnTrello(final int position, final String content) {
        RequestHandler.loadJsonDataPut(
                TrelloUrls.editNoteUrl(
                        mNotes.get(position).getId(),
                        content,
                        ((PairProgrammingApplication) getActivity().getApplication()).getToken()),
                new RequestHandler.OnJsonDataLoadedListener() {
                    @Override
                    public void onJsonDataLoadedSuccessfully(JSONObject data) {
                        mNotes.get(position).setContent(content);
                        mAdapter.notifyItemModified(position);

                        // Change modification date on the note
                        RequestHandler.loadJsonDataPut(TrelloUrls.editNoteModDateUrl(
                                        mNotes.get(position).getId(),
                                        JsonSerializer.sDateFormat.format(new Date()),
                                        ((PairProgrammingApplication) getActivity().getApplication()).getToken()),
                                null,
                                null,
                                Request.Priority.HIGH,
                                TAG);
                    }

                    @Override
                    public void onJsonDataLoadingFailure(int errorId) {
                    }
                },
                null,
                Request.Priority.HIGH,
                TAG);
    }

    private void deleteNoteOnTrello(final int position) {
        RequestHandler.loadJsonDataDelete(
                TrelloUrls.deleteNoteUrl(
                        mNotes.get(position).getId(),
                        ((PairProgrammingApplication) getActivity().getApplication()).getToken()),
                new RequestHandler.OnJsonDataLoadedListener() {
                    @Override
                    public void onJsonDataLoadedSuccessfully(JSONObject data) {
                        mAdapter.deleteItem(position);
                    }

                    @Override
                    public void onJsonDataLoadingFailure(int errorId) {
                    }
                },
                Request.Priority.HIGH,
                TAG);
    }
}
