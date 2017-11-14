package fhku.leanlabapp.classes;

import java.util.ArrayList;

public class Workstep {
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

}
