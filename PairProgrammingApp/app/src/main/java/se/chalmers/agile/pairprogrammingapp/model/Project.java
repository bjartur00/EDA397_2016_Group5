package se.chalmers.agile.pairprogrammingapp.model;

/**
 * Created by wanziguelva on 16-04-23.
 */

/**
 * This class specifies the Project object
 */
public class Project {
    private String mProjectName;
    private String mProjectId;

    public Project (String projectName, String projectId){
        mProjectName = projectName;
        mProjectId = projectId;
    }

    public String getProjectName() {
        return mProjectName;
    }

    public String getProjectId() {
        return mProjectId;
    }
}

