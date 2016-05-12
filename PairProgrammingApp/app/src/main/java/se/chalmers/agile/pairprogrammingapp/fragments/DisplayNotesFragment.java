package se.chalmers.agile.pairprogrammingapp.fragments;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import se.chalmers.agile.pairprogrammingapp.utils.SecretKeys;

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

    }

    @Override
    public void onDeleteNoteClicked(int position) {

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
                    addNote2Trello(content);
                } else {
                    mNotes.get(position).setContent(content);
                    mAdapter.notifyItemModified(position);
                }
            }
        }
    }

    private void addNote2Trello(final String content) {
        Map<String, String> params = new HashMap<>();
        params.put("key", SecretKeys.API_KEY);
        params.put("token", ((PairProgrammingApplication)getActivity().getApplication()).getToken());
        params.put("name", content);
        params.put("due", JsonSerializer.sDateFormat.format(new Date()));
        RequestHandler.loadJsonDataPost(TrelloUrls.addNoteUrl(mNotesListId),
                new RequestHandler.OnJsonDataLoadedListener() {
                    @Override
                    public void onJsonDataLoadedSuccessfully(JSONObject data) {
                        mNotes.add(new Note(content));
                        mAdapter.notifyItemAdded();
                    }

                    @Override
                    public void onJsonDataLoadingFailure(int errorId) {
                    }
                }, params, Request.Priority.HIGH, TAG);
    }
}
