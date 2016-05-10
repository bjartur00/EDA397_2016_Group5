package se.chalmers.agile.pairprogrammingapp.model;

/**
 * Created by wanziguelva on 16-04-24.
 */

/**
 * This class specifies the Project object
 */
public class Unit implements java.io.Serializable{
    private String mUnitName;
    //private String mUnitProgress;
    private String mId;

    public Unit (String unitName, String id){
        mUnitName = unitName;
        mId = id;
    }

    public String getUnitName(){
        return mUnitName;
    }

    public String getID(){
        return mId;
    }


}
