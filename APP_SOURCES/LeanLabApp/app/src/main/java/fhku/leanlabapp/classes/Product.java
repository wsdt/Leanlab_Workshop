package fhku.leanlabapp.classes;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import fhku.leanlabapp.classes.exceptions.JsonToObjectMapper_Exception;
import fhku.leanlabapp.interfaces.JsonStrConverter;
import fhku.leanlabapp.interfaces.Mapper;

public class Product extends Mapper{
    private static final String LOG_TAG = "PRODUCT";
    private int Productid;
    private String Productname;
    //IMPORTANT: Be sure to empty the arraylist if you use it, unless you except sth to be in there
    public static ArrayList<Product> Loaded_Products = new ArrayList<>(); //do not add Products to list automatically (only if you need it)

    //Constructor
    public Product(int Productid, String Productname) {
        this.setProductid(Productid);
        this.setProductname(Productname);
    }

    public Product(int Productid) { //no empty constructor, because id is primary key
        this.setProductid(Productid);
    }

    //Getter/Setter ------------------------------
    public int getProductid() {
        return this.Productid;
    }

    public void setProductid(int Productid) {
        this.Productid = Productid;
    }

    public String getProductname() {
        return this.Productname;
    }

    public void setProductname(String Productname) {
        if (Productname.length() > 50) {
            Productname = Productname.substring(0,49);
            Log.w(LOG_TAG,"Productname TOO long! Shortened it.");
        }
        this.Productname = Productname;
    }

    // MAPPING METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    @Override
    public Product MapJsonToObject(String json) throws JsonToObjectMapper_Exception {
        JsonStrConverter tmp = new JsonStrConverter(json);
        tmp.convertStrToJson();
        return MapJsonToObject(tmp.getJson_obj());
    }

    @Override
    public Product MapJsonToObject(JSONObject json) throws JsonToObjectMapper_Exception {
        Product obj;
        try {
            obj = new Product(json.getInt("Productid"), json.getString("Productname"));
        } catch(JSONException e) {
            Log.e("MapJsonToObject","JSON could not be mapped to Object!");
            e.printStackTrace();
            throw new JsonToObjectMapper_Exception("JSON could not be mapped to Object!");
        }
        return obj;
    }


}
