package se.chalmers.agile.pairprogrammingapp.network;

import android.util.Log;

import com.android.volley.Request;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import se.chalmers.agile.pairprogrammingapp.model.Project;
import se.chalmers.agile.pairprogrammingapp.model.TestCase;
import se.chalmers.agile.pairprogrammingapp.model.Unit;
import se.chalmers.agile.pairprogrammingapp.model.User;
import se.chalmers.agile.pairprogrammingapp.utils.SecretKeys;

/**
 * Created by wissam on 25/04/16.
 */
public class TrelloUrls {
    public static String getTestCasesUrl(String unitId, String userToken) {
        return "https://api.trello.com/1/lists/" + unitId + "/cards?key=" + SecretKeys.API_KEY + "&token=" + userToken;
    }

    public static String getUnitsUrl(String boardId, String userToken) {
        return "https://api.trello.com/1/boards/" + boardId + "/lists?lists=open&list_fields=name&fields=name,idList,labels,&key=" + SecretKeys.API_KEY + "&token=" + userToken;
    }

    public static String getNotesUrl(String boardId, String userToken) {
        return "https://api.trello.com/1/boards/" + boardId + "/lists?cards=open&card_fields=name,due&fields=name&key=" + SecretKeys.API_KEY + "&token=" + userToken;
    }

    public static String addNoteUrl(String notesListId) {
        return "https://api.trello.com/1/lists/" + notesListId + "/cards";
    }

    public static String getMembersUrl(String boardId, String userToken) {
        return "https://api.trello.com/1/boards/" + boardId + "/members?key=" + SecretKeys.API_KEY + "&token=" + userToken;

    }

    public static String getProjectsUrl(String username, String userToken) {
        return "https://api.trello.com/1/members/" + username + "?boards=open&board_fields=name,id&key=" + SecretKeys.API_KEY + "&token=" + userToken;
    }
}
