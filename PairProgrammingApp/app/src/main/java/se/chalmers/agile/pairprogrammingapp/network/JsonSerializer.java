package se.chalmers.agile.pairprogrammingapp.network;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import se.chalmers.agile.pairprogrammingapp.model.GetNotesResponse;
import se.chalmers.agile.pairprogrammingapp.model.GetProjectsResponse;
import se.chalmers.agile.pairprogrammingapp.model.Note;
import se.chalmers.agile.pairprogrammingapp.model.Project;
import se.chalmers.agile.pairprogrammingapp.model.Unit;
import se.chalmers.agile.pairprogrammingapp.utils.Constants;
import se.chalmers.agile.pairprogrammingapp.model.User;

/**
 * Created by wissam on 26/04/16.
 */
public class JsonSerializer {
    public static DateFormat sDateFormat = new SimpleDateFormat(
            "yyyy-MM-dd'T'HH:mm:ss.SSSZ");

    public static GetProjectsResponse json2Projects(JSONObject object) {
        ArrayList<Project> result = new ArrayList<>();
        String id = "";
        String username = "";
        try {
            id = object.getString("id");
            username = object.getString("username");
            JSONArray projectsArray = object.getJSONArray("boards");

            for (int index = 0; index < projectsArray.length(); index++) {
                JSONObject projectObject = projectsArray.getJSONObject(index);
                String projectName = projectObject.getString("name");
                String projectId = projectObject.getString("id");
                result.add(new Project(projectName, projectId));
            }
        } catch (Exception ignored) {

        }
        return new GetProjectsResponse(result, id, username);
    }

    public static ArrayList<Unit> json2Units(JSONArray unitsArray) {
        ArrayList<Unit> result = new ArrayList<>();
        try {
            for (int index = 0; index < unitsArray.length(); index++) {
                JSONObject unitObject = unitsArray.getJSONObject(index);
                String unitName = unitObject.getString("name");
                // The name shouldn't equal to the notes or timer lists names
                if (!unitName.equals(Constants.NOTES_LIST_NAME) && !unitName.equals(Constants.TIMER_LIST_NAME)) {
                    String unitId = unitObject.getString("id");
                    result.add(new Unit(unitName, unitId));
                }
            }
        } catch (Exception ignored) {

        }
        return result;
    }

    public static GetNotesResponse json2Notes(JSONArray unitsArray) {
        String notesListId = "";
        ArrayList<Note> notes = new ArrayList<>();
        try {
            for (int unitIndex = 0; unitIndex < unitsArray.length(); unitIndex++) {
                JSONObject unitObject = unitsArray.getJSONObject(unitIndex);
                String unitName = unitObject.getString("name");
                if (unitName.equals(Constants.NOTES_LIST_NAME)) {
                    notesListId = unitObject.getString("id");
                    JSONArray notesArray = unitObject.getJSONArray("cards");
                    for (int noteIndex = 0; noteIndex < notesArray.length(); noteIndex++) {
                        JSONObject noteObject = notesArray.getJSONObject(noteIndex);
                        String noteId = noteObject.getString("id");
                        String noteContent = noteObject.getString("name");
                        Date noteCreated = new Date();
                        try {
                            noteCreated = sDateFormat.parse(noteObject
                                    .getString("due"));
                        } catch (Exception ignored) {
                        }
                        notes.add(new Note(noteId, noteContent, noteCreated));
                    }
                }
            }
        } catch (Exception ignored) {

        }
        return new GetNotesResponse(notesListId, notes);

    }

    public static ArrayList<User> json2Members(JSONArray membersArray) {
        ArrayList<User> result = new ArrayList<>();
        try {
            for (int i = 0; i < membersArray.length(); i++) {
                JSONObject membersObject = membersArray.getJSONObject(i);
                String membersFullName = membersObject.getString("fullName");
                String membersUserName = membersObject.getString("username");
                result.add(new User(membersFullName, membersUserName));
            }
        } catch (Exception e) {

        }
        return result;
    }
}
