package fhku.leanlabapp.classes;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;

import java.io.IOException;

import fhku.leanlabapp.interfaces.Mapper;


public class Product extends Mapper {
    private int productid;
    private String productname;

    //Constructor
    public Product(int productid, String productname) {
        this.setProductid(productid);
        this.setProductname(productname);
    }

    public Product() {}

    //Getter/Setter ------------------------------
    public int getProductid() {
        return productid;
    }

    public void setProductid(int productid) {
        this.productid = productid;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    //Mapper Interface methods
    @Override
    public Object mapJSONtoJavaClass(JSONObject jsonObj) {

        return null;
    }

    @Override
    public JSONObject mapJavaClasstoJSON(Object genObj) {
        return null;
    }

    @Override
    public Object mapJSONStrtoJavaClass(String jsonStr) throws IOException {
        return this.readValue(jsonStr, this.getClass());
    }

    @Override
    public String mapJavaClasstoJSONStr(Object genObj) {
        return null;
    }
}
