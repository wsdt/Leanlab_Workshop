package fhku.leanlabapp.classes;

import org.json.JSONObject;

import fhku.leanlabapp.interfaces.Mapper;


public class Product implements Mapper {
    private int productid;
    private String productname;

    //Constructor
    public Product(int productid, String productname) {
        this.setProductid(productid);
        this.setProductname(productname);
    }

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
    public Object mapJSONStrtoJavaClass(String jsonStr) {
        return null;
    }

    @Override
    public String mapJavaClasstoJSONStr(Object genObj) {
        return null;
    }
}
