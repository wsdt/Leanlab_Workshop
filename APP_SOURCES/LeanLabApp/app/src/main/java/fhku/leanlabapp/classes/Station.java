package fhku.leanlabapp.classes;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import fhku.leanlabapp.classes.exceptions.JsonToObjectMapper_Exception;
import fhku.leanlabapp.interfaces.JsonStrConverter;
import fhku.leanlabapp.interfaces.Mapper;

public class Station extends Mapper {
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

    // MAPPING METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    @Override
    public Station MapJsonToObject(String json) throws JsonToObjectMapper_Exception {
        JsonStrConverter tmp = new JsonStrConverter(json);
        tmp.convertStrToJson();
        return MapJsonToObject(tmp.getJson_obj());
    }

    @Override
    public Station MapJsonToObject(JSONObject json) throws JsonToObjectMapper_Exception {
        Station obj;
        try {
            obj = new Station(json.getInt("Stationid"), json.getString("Stationname"));
        } catch(JSONException e) {
            Log.e("MapJsonToObject","JSON could not be mapped to Object!");
            e.printStackTrace();
            throw new JsonToObjectMapper_Exception("JSON could not be mapped to Object!");
        }
        return obj;
    }


}
