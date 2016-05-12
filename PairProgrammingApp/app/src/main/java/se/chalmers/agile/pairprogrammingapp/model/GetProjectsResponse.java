package se.chalmers.agile.pairprogrammingapp.model;

import java.util.ArrayList;

/**
 * Created by wissam on 12/05/16.
 */
public class GetProjectsResponse {
    private ArrayList<Project> mProjects;
    private String mId;
    private String mUsername;

    public GetProjectsResponse(ArrayList<Project> mProjects, String id, String username) {
        this.mProjects = mProjects;
        this.mId = id;
        this.mUsername = username;
    }

    public ArrayList<Project> getProjects() {
        return mProjects;
    }

    public String getId() {
        return mId;
    }

    public String getUsername() {
        return mUsername;
    }
}
