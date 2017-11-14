package fhku.leanlabapp.classes;

import android.util.Log;

import java.util.ArrayList;

public class Station {
    private static final String LOG_TAG = "STATION";
    private int Stationid;
    private String Stationname;
    //IMPORTANT: Be sure to empty the arraylist if you use it, unless you except sth to be in there
    public static ArrayList<Station> Loaded_Stations = new ArrayList<>(); //do not add to list automatically (only if you need it)

    //Constructor
    public Station(int Stationid, String Stationname) {
        this.setStationid(Stationid);
        this.setStationname(Stationname);
    }

    public Station(int Stationid) { //no empty constructor, because id is primary key
        this.setStationid(Stationid);
    }

    //Getter/Setter ------------------------------
    public int getStationid() {
        return this.Stationid;
    }

    public void setStationid(int Stationid) {
        this.Stationid = Stationid;
    }

    public String getStationname() {
        return this.Stationname;
    }

    public void setStationname(String Stationname) {
        if (Stationname.length() > 50) {
            Stationname = Stationname.substring(0,49);
            Log.w(LOG_TAG,"Stationname TOO long! Shortened it.");
        }
        this.Stationname = Stationname;
    }


}
