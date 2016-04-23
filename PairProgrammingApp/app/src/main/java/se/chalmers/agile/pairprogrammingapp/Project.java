package se.chalmers.agile.pairprogrammingapp;

/**
 * Created by wanziguelva on 16-04-23.
 */

public class Project {
    private String mProName;
    private String mProProgress;

    Project (String proName, String proProgress){
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

