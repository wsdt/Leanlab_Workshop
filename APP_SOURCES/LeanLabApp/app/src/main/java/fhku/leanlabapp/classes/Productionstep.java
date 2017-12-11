package fhku.leanlabapp.classes;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import fhku.leanlabapp.classes.exceptions.JsonToObjectMapper_Exception;
import fhku.leanlabapp.interfaces.JsonStrConverter;
import fhku.leanlabapp.interfaces.Mapper;

public class Productionstep extends Mapper {
    private static final String LOG_TAG = "PRODUCTIONSTEP";
    private int Productionstepid;
    private int Productid;
    private int Stationid;
    //IMPORTANT: Be sure to empty the arraylist if you use it, unless you except sth to be in there
    public static ArrayList<Productionstep> Loaded_Productionsteps = new ArrayList<>(); //do not add to list automatically (only if you need it)

    //Constructor
    public Productionstep(int Productionstepid, int Productid, int Stationid) {
        this.setProductionstepid(Productionstepid);
        this.setProductid(Productid);
        this.setStationid(Stationid);
    }

    public Productionstep(int Productionstepid) { //no empty constructor, because id is primary key
        this.setStationid(Stationid);
    } //because of primary key

    //Getter/Setter ------------------------------
    public int getProductionstepid() {
        return this.Productionstepid;
    }

    public void setProductionstepid(int Productionstepid) {
        this.Productionstepid = Productionstepid;
    }

    public int getProductid() {
        return this.Productid;
    }

    public void setProductid(int Productid) {
        this.Productid = Productid;
    }

    public int getStationid() { return this.Stationid; }

    public void setStationid(int Stationid) {
        this.Stationid = Stationid;
    }

    // MAPPING METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    @Override
    public Productionstep MapJsonToObject(JSONObject json) throws JsonToObjectMapper_Exception {
        Productionstep obj;
        try {
            obj = new Productionstep(json.getInt("Productionstepid"),json.getInt("Productid"),json.getInt("Stationid"));
            obj = new Productionstep(json.getInt("ProductionstepID"),json.getInt("ProductID"),json.getInt("StationID"));
        } catch(JSONException e) {
            Log.e("MapJsonToObject","JSON could not be mapped to Object!");
            e.printStackTrace();
            throw new JsonToObjectMapper_Exception("JSON could not be mapped to Object!");
        }
        return obj;
    }
}
