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

/**
 * Created by wissam on 25/04/16.
 */
public class TrelloUrls {
    public static ArrayList<TestCase> testCases = new ArrayList<>();
    public static ArrayList<Unit> units = new ArrayList<>();

    public static ArrayList<TestCase> getTestCases(String userToken){
        String urlRequest = "https://api.trello.com/1/boards/EwjJOxfr/cards?lists=open&list_fields=name&fields=name,idList,labels,&key=e7f2387af84a2e749732e48d8290c204&token=" + userToken;
        RequestHandler.loadJsonArrayGet(urlRequest, new trelloRequestTestCases(), Request.Priority.HIGH, "tag");
        return testCases;
    }

    public static ArrayList<Unit> getUnits(String userToken){
        String urlRequest = "https://api.trello.com/1/boards/EwjJOxfr/lists?lists=open&list_fields=name&fields=name,idList,labels,&key=e7f2387af84a2e749732e48d8290c204&token=" + userToken;
        RequestHandler.loadJsonArrayGet(urlRequest, new trelloRequestUnits(), Request.Priority.HIGH, "tag");
        return units;
    }

    public static User[] getMembers(String currentUsername, String boardId, String devKey, String authToken){
        String urlRequest = "https://api.trello.com/1/boards/" + boardId + "/members?key=" + devKey +"&token=" + authToken;
        RequestHandler.loadJsonArrayGet(urlRequest, new trelloRequestMembers(), Request.Priority.HIGH, "tag");
        User[] users = new User[10];
        return users;
    }

    public static Project[] getProjects(String userToken){
        String urlRequest = "https://api.trello.com/1/members/wanzigu?fields=username,fullName,url&boards=all&board_fields=name&key=e7f2387af84a2e749732e48d8290c204&token=" + userToken;
        RequestHandler.loadJsonArrayGet(urlRequest, new trelloRequestProjects(), Request.Priority.HIGH, "tag");
        Project[] projects = new Project[10];
        return projects;
    }

    static class trelloRequestTestCases implements RequestHandler.OnJsonArrayLoadedListener {
        @Override
        public void onJsonDataLoadedSuccessfully(JSONArray data) {
            int iColor = 0;
            for(int i=0;i < data.length();i++){
                try {
                    String color = "";
                    try {
                        color = new JSONArray(data.getJSONObject(i).getString("labels")).getJSONObject(0).getString("color");
                    } catch (Exception e){
                        color = "";
                    }
                    if(color.contains("green")){
                        iColor = 1;
                    } else if (color.contains("yellow")) {
                        iColor = 2;
                    } else if (color.contains("red")) {
                        iColor = 3;
                    }
                    color = "";
                    testCases.add(new TestCase(data.getJSONObject(i).getString("name"), "", iColor, i, data.getJSONObject(i).getString("idList")));
                } catch (Exception e){};
            }
        }
        @Override
        public void onJsonDataLoadingFailure(int errorId) {
            Log.i("error", "error");
        }
    }

    static class trelloRequestUnits implements RequestHandler.OnJsonArrayLoadedListener {
        @Override
        public void onJsonDataLoadedSuccessfully(JSONArray data) {
            for(int i=0;i < data.length();i++){
                try {
                    units.add(new Unit(data.getJSONObject(i).getString("name"), "xx", data.getJSONObject(i).getString("id")));
                } catch (Exception e){};
            }
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
