package se.chalmers.agile.pairprogrammingapp.network;

import android.util.Log;

import com.android.volley.Request;

import org.json.JSONArray;
import org.json.JSONObject;

import se.chalmers.agile.pairprogrammingapp.model.Project;
import se.chalmers.agile.pairprogrammingapp.model.TestCase;
import se.chalmers.agile.pairprogrammingapp.model.User;

/**
 * Created by wissam on 25/04/16.
 */
public class TrelloUrls {
    public static TestCase[] getTestCases(String userToken){
        String urlRequest = "https://api.trello.com/1/boards/EwjJOxfr/cards?lists=open&list_fields=name&fields=name,idList,&key=e7f2387af84a2e749732e48d8290c204&token=" + userToken;
        RequestHandler.loadJsonArrayGet(urlRequest, new trelloRequestTextCases(), Request.Priority.HIGH, "tag");
        TestCase[] testCases = new TestCase[10];
        return testCases;
    }

    public static User[] getMembers(String currentUsername, String boardId, String devKey, String authToken){
        String urlRequest = "https://api.trello.com/1/boards/" + boardId + "/members?key=" + devKey +"&token=" + authToken;
        RequestHandler.loadJsonArrayGet(urlRequest, new trelloRequestMembers(), Request.Priority.HIGH, "tag");
        User[] users = new User[10];
        return users;
    }

    public static Project[] getProjects(String userToken){
        String urlRequest = "https://api.trello.com/1/members/wanzigu?fields=username,fullName,url&boards=all&board_fields=name&key=e7f2387af84a2e749732e48d8290c204&token=" + userToken;
        RequestHandler.loadJsonArrayGet(urlRequest, new trelloRequestTextCases(), Request.Priority.HIGH, "tag");
        Project[] projects = new Project[10];
        return projects;
    }

    static class trelloRequestTextCases implements RequestHandler.OnJsonArrayLoadedListener {
        @Override
        public void onJsonDataLoadedSuccessfully(JSONArray data) {
            Log.i("respond", data.toString());
        }
        @Override
        public void onJsonDataLoadingFailure(int errorId) {
            Log.i("error", "error");
        }
    }

    static class trelloRequestMembers implements RequestHandler.OnJsonArrayLoadedListener {
        @Override
        public void onJsonDataLoadedSuccessfully(JSONArray data) {
            Log.i("respond", data.toString());
            //use the data here
        }
        @Override
        public void onJsonDataLoadingFailure(int errorId) {
            Log.i("error", "error");
        }
    }

    static class trelloRequestProjects implements RequestHandler.OnJsonArrayLoadedListener {
        @Override
        public void onJsonDataLoadedSuccessfully(JSONArray data) {
            Log.i("respond", data.toString());
        }
        @Override
        public void onJsonDataLoadingFailure(int errorId) {
            Log.i("error", "error");
        }
    }
}
