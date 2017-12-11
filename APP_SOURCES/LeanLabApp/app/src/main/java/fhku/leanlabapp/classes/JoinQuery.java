package fhku.leanlabapp.classes;

import android.graphics.Paint;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import fhku.leanlabapp.classes.exceptions.JsonToObjectMapper_Exception;
import fhku.leanlabapp.interfaces.Mapper;

public class JoinQuery extends Mapper {
    private int WorkstepID;
    private int ProductionstepID;
    private int TypeID;
    private int ContentID;
    private String Contenttext;
    private String Typname;
    private int ProductID;
    private int StationID;

    public static ArrayList<JoinQuery> Loaded_JoinQuery = new ArrayList<>(); //do not add JoinQuery to list automatically (only if you need it)

    public JoinQuery(int StationID, int ProductID, int ProductionstepID, int WorkstepID, int TypeID, String Contenttext){
        this.setStationID(StationID);
        this.setProductID(ProductID);
        this.setProductionstepID(ProductionstepID);
        this.setWokstepID(WorkstepID);
        this.setTypeID(TypeID);
        this.setContenttext(Contenttext);
    }

    public JoinQuery(int WorkstepID, int ProductionstepID, int TypeID, int ContentID, String Contenttext, String Typname, int ProductID, int StationID){
        this.setWokstepID(WorkstepID);
        this.setProductionstepID(ProductionstepID);
        this.setTypeID(TypeID);
        this.setContentID(ContentID);
        this.setContenttext(Contenttext);
        this.setTypname(Typname);
        this.setProductID(ProductID);
        this.setStationID(StationID);
    }

    @Override
    public Object MapJsonToObject(JSONObject json) throws JsonToObjectMapper_Exception {
        JoinQuery obj;
        try {
            obj = new JoinQuery(json.getInt("StationID"), json.getInt("ProductID"),
                    json.getInt("ProductionstepID"),json.getInt("WorkstepID"),
                    json.getInt("TypeID"), json.getString("Contenttext"));
        } catch(JSONException e) {
            Log.e("MapJsonToObject","JSON could not be mapped to Object!");
            e.printStackTrace();
            throw new JsonToObjectMapper_Exception("JSON could not be mapped to Object!");
        }
        return obj;
    }

    public int getWokstepID() {
        return WorkstepID;
    }

    public void setWokstepID(int wokstepID) {
        WorkstepID = wokstepID;
    }

    public int getProductionstepID() {
        return ProductionstepID;
    }

    public void setProductionstepID(int productionstepID) {
        ProductionstepID = productionstepID;
    }

    public String getContenttext() {
        return Contenttext;
    }

    public void setContenttext(String contenttext) {
        Contenttext = contenttext;
    }

    public int getTypeID() {
        return TypeID;
    }

    public void setTypeID(int typeID) {
        TypeID = typeID;
    }

    public int getContentID() {
        return ContentID;
    }

    public void setContentID(int contentID) {
        ContentID = contentID;
    }

    public String getTypname() {
        return Typname;
    }

    public void setTypname(String typname) {
        Typname = typname;
    }

    public int getProductID() {
        return ProductID;
    }

    public void setProductID(int productID) {
        ProductID = productID;
    }

    public int getStationID() {
        return StationID;
    }

    public void setStationID(int stationID) {
        StationID = stationID;
    }
}
