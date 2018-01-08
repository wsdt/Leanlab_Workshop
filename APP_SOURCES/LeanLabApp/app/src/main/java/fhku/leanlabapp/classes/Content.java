package fhku.leanlabapp.classes;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import fhku.leanlabapp.classes.exceptions.JsonToObjectMapper_Exception;
import fhku.leanlabapp.classes.helper._HelperMethods;
import fhku.leanlabapp.interfaces.JsonStrConverter;
import fhku.leanlabapp.interfaces.Mapper;

public class Content extends Mapper{
    private static final String LOG_TAG = "CONTENT";
    private int Contentid;
    private String Contenttext; // PRODUCTID_STATIONID_CONTENTID.EXTENSION
    private int Workstepid;
    private int Typid;

    //IMPORTANT: Be sure to empty the arraylist if you use it, unless you except sth to be in there
    public static ArrayList<Content> Loaded_Contents = new ArrayList<>(); //do not add to list automatically (only if you need it)


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

    // MAPPING METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    @Override
    public Content MapJsonToObject(JSONObject json) throws JsonToObjectMapper_Exception {
        Content obj;
        try {
            obj = new Content(json.getInt("ContentID"), json.getString("Contenttext"),json.getInt("WorkstepID"),json.getInt("TypID"));
        } catch(JSONException e) {
            Log.e("MapJsonToObject","JSON could not be mapped to Object!");
            e.printStackTrace();
            throw new JsonToObjectMapper_Exception("JSON could not be mapped to Object!");
        }
        return obj;
    }

}

