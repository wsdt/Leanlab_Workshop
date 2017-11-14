package fhku.leanlabapp.classes;

import java.util.ArrayList;

public class Productionstep {
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


}
