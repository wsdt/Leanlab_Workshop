package fhku.leanlabapp.classes;

import android.util.Log;
import java.util.ArrayList;

public class Content {
    private static final String LOG_TAG = "CONTENT";
    private int Contentid;
    private String Contenttext;
    private int Workstepid;
    private int Typid;

    //IMPORTANT: Be sure to empty the arraylist if you use it, unless you except sth to be in there
    public static ArrayList<Content> Loaded_Types = new ArrayList<>(); //do not add to list automatically (only if you need it)

    //Constructor
    public Content(int Contentid, String Contenttext, int Workstepid, int Typid) {
        this.setContentid(Contentid);
        this.setContenttext(Contenttext);
        this.setWorkstepid(Workstepid);
        this.setTypid(Typid);
    }

    //Constructor
    public Content(int Contentid) {
        this.setContentid(Contentid); //because of primary key
    }

    public int getContentid() {
        return this.Contentid;
    }

    public void setContentid(int contentid) {
        this.Contentid = contentid;
    }

    public String getContenttext() {
        return this.Contenttext;
    }

    public void setContenttext(String contenttext) {
        //Escape before validation, because html will be longer after escaping!
        contenttext = _HelperMethods.escapeHTML(contenttext);

        if (contenttext.length() > 500) {
            contenttext = contenttext.substring(0,499);
            Log.w(LOG_TAG,"Contexttext TOO long! Shortened it.");
        }
        this.Contenttext = contenttext;
    }

    public int getWorkstepid() {
        return this.Workstepid;
    }

    public void setWorkstepid(int workstepid) {
        this.Workstepid = workstepid;
    }

    public int getTypid() {
        return this.Typid;
    }

    public void setTypid(int typid) {
        this.Typid = typid;
    }
}
