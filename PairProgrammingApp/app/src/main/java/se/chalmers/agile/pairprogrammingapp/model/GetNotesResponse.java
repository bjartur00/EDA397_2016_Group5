package se.chalmers.agile.pairprogrammingapp.model;

import java.util.ArrayList;

/**
 * Created by wissam on 11/05/16.
 */
public class GetNotesResponse {
    private String mNotesListId;
    private ArrayList<Note> mNotes;

    public GetNotesResponse(String notesListId, ArrayList<Note> notes) {
        this.mNotesListId = notesListId;
        this.mNotes = notes;
    }

    public String getNotesListId() {
        return mNotesListId;
    }

    public ArrayList<Note> getNotes() {
        return mNotes;
    }
}
