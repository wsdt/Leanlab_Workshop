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
    private int TypID;
    private int ContentID;
    private int StationID;
    private int ProductID;
    private String Contenttext;


    public static ArrayList<JoinQuery> Loaded_JoinQuerys = new ArrayList<>(); //do not add JoinQuery to list automatically (only if you need it)

    public JoinQuery(){}

    public JoinQuery(int ContentID, String Contenttext, int WorkstepID, int StationID, int ProductID, int TypID){
      this.setContentID(ContentID);
      this.setWokstepID(WorkstepID);
      this.setTypID(TypID);
      this.setContenttext(Contenttext);
      this.setStationID(StationID);
      this.setProductID(ProductID);
      }

    @Override
    public Object MapJsonToObject(JSONObject json) throws JsonToObjectMapper_Exception {
        JoinQuery obj;
        try {
            obj = new JoinQuery(json.getInt("ContentID"), json.getString("Contenttext"),
                    json.getInt("WorkstepID"),
                    json.getInt("StationID"),
                    json.getInt("ProductID"),
                    json.getInt("TypID"));
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


    public String getContenttext() {
        return Contenttext;
    }

    public void setContenttext(String contenttext) {
        Contenttext = contenttext;
    }

    public int getTypID() {
        return TypID;
    }

    public void setTypID(int typID) {
        TypID = typID;
    }

    public int getContentID() {
        return ContentID;
    }

    public void setContentID(int contentID) {
        ContentID = contentID;
    }

    @Override
    public String toString() {
        return new StringBuilder().
                append(this.getContentID())
                .append(this.getContenttext())
                .append(this.getTypID())
                .append(";")
                .append(this.getWokstepID())
                .append(";")
                .append(this.getStationID())
                .append(";")
                .append(this.getProductID()).toString();
    }


    public int getStationID() {
        return StationID;
    }

    public void setStationID(int stationID) {
        StationID = stationID;
    }

    public int getProductID() {
        return ProductID;
    }

    public void setProductID(int productID) {
        ProductID = productID;
    }
}
