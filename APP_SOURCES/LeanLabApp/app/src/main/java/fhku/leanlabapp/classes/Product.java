package fhku.leanlabapp.classes;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;

import java.io.IOException;

import fhku.leanlabapp.interfaces.Mapper;


public class Product extends Mapper {
    private int Productid;
    private String Productname;

    //Constructor
    public Product(int Productid, String Productname) {
        this.setProductid(Productid);
        this.setProductname(Productname);
    }

    public Product() {}

    //Getter/Setter ------------------------------
    public int getProductid() {
        return Productid;
    }

    public void setProductid(int Productid) {
        this.Productid = Productid;
    }

    public String getProductname() {
        return Productname;
    }

    public void setProductname(String Productname) {
        this.Productname = Productname;
    }


}
