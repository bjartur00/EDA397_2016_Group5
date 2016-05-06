package se.chalmers.agile.pairprogrammingapp.model;

/**
 * Created by wanziguelva on 16-04-24.
 */

/**
 * This class specifies the Project object
 */
public class Unit implements java.io.Serializable{
    private String mUnitName;
    private String mUnitProgress;
    private String mID;

    public Unit (String UnitName, String UnitProgress, String ID){
        mUnitName = UnitName;
        mUnitProgress = UnitProgress;
        mID = ID;
    }

    public String getmUnitName(){
        return mUnitName;
    }

    public void setmUnitName(String mUnitName) {
        this.mUnitName = mUnitName;
    }

    public String getmUnitProgress(){
        return mUnitProgress;
    }

    public void setmUnitProgress(String mUnitProgress){
        this.mUnitProgress = mUnitProgress;
    }

    public String getmID(){
        return mID;
    }

    public void setmID(String mID){
        this.mID = mID;
    }

}
