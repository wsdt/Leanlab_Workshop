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


}
