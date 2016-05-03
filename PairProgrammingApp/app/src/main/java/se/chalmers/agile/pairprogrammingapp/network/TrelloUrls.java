package se.chalmers.agile.pairprogrammingapp.network;

import android.util.Log;

import com.android.volley.Request;

import org.json.JSONArray;
import org.json.JSONObject;
import se.chalmers.agile.pairprogrammingapp.model.TestCase;

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
}
