package se.chalmers.agile.pairprogrammingapp.model;

/**
 * Created by wanziguelva on 16-04-23.
 */

/**
 * This class specifies the Project object
 */
public class Project {
    private String mProName;
    private String mProProgress;

    public Project (String proName, String proProgress){
        mProName = proName;
        mProProgress = proProgress;
    }

    public String getmProName(){
        return mProName;
    }

    public void setmProName(String mProName) {
        this.mProName = mProName;
    }

    public String getmProProgress(){
        return mProProgress;
    }

    public void setmProProgress(String mProProgress){
        this.mProProgress = mProProgress;
    }
}

