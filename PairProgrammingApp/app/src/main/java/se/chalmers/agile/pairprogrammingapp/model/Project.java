package se.chalmers.agile.pairprogrammingapp.model;

/**
 * Created by wanziguelva on 16-04-23.
 */

/**
 * This class specifies the Project object
 */
public class Project {
    private String mProName;
//    private String mProProgress;
    private int mProId;

    public Project (String proName, int proId){
        mProName = proName;
        mProId = proId;
    }

    public String getmProName(){
        return mProName;
    }

    public void setmProName(String ProName) {
        this.mProName = ProName;
    }

    public int getmProId(){
        return mProId;
    }

    public void setmProId(int proId){
        this.mProId = proId;
    }
}

