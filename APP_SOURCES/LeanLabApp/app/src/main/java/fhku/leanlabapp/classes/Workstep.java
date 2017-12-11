package fhku.leanlabapp.classes;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import fhku.leanlabapp.classes.exceptions.JsonToObjectMapper_Exception;
import fhku.leanlabapp.interfaces.JsonStrConverter;
import fhku.leanlabapp.interfaces.Mapper;

public class Workstep extends Mapper {
    private static final String LOG_TAG = "PRODUCTIONSTEP";
    private int Workstepid;
    private int Productionstepid;

    //IMPORTANT: Be sure to empty the arraylist if you use it, unless you except sth to be in there
    public static ArrayList<Workstep> Loaded_Worksteps = new ArrayList<>(); //do not add to list automatically (only if you need it)

    //Constructor
    public Workstep(int Workstepid, int Productionstepid) {
        this.setWorkstepid(Workstepid);
        this.setProductionstepid(Productionstepid);
    }

    public Workstep(int Workstepid) { //no empty constructor, because id is primary key
        this.setWorkstepid(Workstepid);
    } //because of primary key

    //Getter/Setter ------------------------------
    public int getWorkstepid() {
        return this.Workstepid;
    }

    public void setWorkstepid(int Workstepid) {
        this.Workstepid = Workstepid;
    }

    public int getProductionstepid() {
        return this.Productionstepid;
    }

    public void setProductionstepid(int Productionstepid) {
        this.Productionstepid = Productionstepid;
    }

    // MAPPING METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    @Override
    public Workstep MapJsonToObject(JSONObject json) throws JsonToObjectMapper_Exception {
        Workstep obj;
        try {
            obj = new Workstep(json.getInt("Workstepid"), json.getInt("Productionstepid"));
            obj = new Workstep(json.getInt("WorkstepID"), json.getInt("ProductionstepID"));
        } catch(JSONException e) {
            Log.e("MapJsonToObject","JSON could not be mapped to Object!");
            e.printStackTrace();
            throw new JsonToObjectMapper_Exception("JSON could not be mapped to Object!");
        }
        return obj;
    }

}
