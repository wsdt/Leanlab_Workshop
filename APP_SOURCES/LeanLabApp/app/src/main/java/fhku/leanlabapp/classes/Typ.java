package fhku.leanlabapp.classes;

import android.util.Log;

import java.util.ArrayList;

public class Typ {
    private static final String LOG_TAG = "TYP";
    private int Typid;
    private String Typname;
    //IMPORTANT: Be sure to empty the arraylist if you use it, unless you except sth to be in there
    public static ArrayList<Typ> Loaded_Types = new ArrayList<>(); //do not add to list automatically (only if you need it)

    //Constructor
    public Typ(int Typid, String Typname) {
        this.setTypid(Typid);
        this.setTypname(Typname);
    }

    //Constructor
    public Typ(int Typid) {
        this.setTypid(Typid); //because of primary key
    }

    public int getTypid() {
        return Typid;
    }

    public void setTypid(int typid) {
        Typid = typid;
    }

    public String getTypname() {
        return Typname;
    }

    public void setTypname(String typname) {
        if (Typname.length() > 50) {
            Typname = Typname.substring(0,49);
            Log.w(LOG_TAG,"Typname TOO long! Shortened it.");
        }
        Typname = typname;
    }
}
