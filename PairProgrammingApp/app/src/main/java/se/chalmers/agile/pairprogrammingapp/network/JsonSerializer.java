package se.chalmers.agile.pairprogrammingapp.network;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import se.chalmers.agile.pairprogrammingapp.model.Project;
import se.chalmers.agile.pairprogrammingapp.model.Unit;

/**
 * Created by wissam on 26/04/16.
 */
public class JsonSerializer {
    public static ArrayList<Project> json2Projects(JSONArray projectsArray) {
        ArrayList<Project> result = new ArrayList<>();
        try {
            for (int i = 0; i < projectsArray.length(); i++) {
                JSONObject projectObject = projectsArray.getJSONObject(i);
                String projectName = projectObject.getString("name");
                String projectId = projectObject.getString("id");
                result.add(new Project(projectName, projectId));
            }
        } catch (Exception e) {

        }
        return result;
    }

    public static ArrayList<Unit> json2Units(JSONArray unitsArray) {
        ArrayList<Unit> result = new ArrayList<>();
        try {
            for (int i = 0; i < unitsArray.length(); i++) {
                JSONObject unitObject = unitsArray.getJSONObject(i);
                String unitName = unitObject.getString("name");
                String unitId = unitObject.getString("id");
                result.add(new Unit(unitName, unitId));
            }
        } catch (Exception e) {

        }
        return result;
    }
}
