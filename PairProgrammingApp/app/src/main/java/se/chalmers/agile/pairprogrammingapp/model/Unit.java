package se.chalmers.agile.pairprogrammingapp.model;

/**
 * Created by wanziguelva on 16-04-24.
 */

/**
 * This class specifies the Project object
 */
public class Unit {
    private String mUnitName;
    private String mUnitProgress;

    public Unit (String UnitName, String UnitProgress){
        mUnitName = UnitName;
        mUnitProgress = UnitProgress;
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

}
