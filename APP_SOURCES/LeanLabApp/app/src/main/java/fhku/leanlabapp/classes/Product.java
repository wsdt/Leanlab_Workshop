package fhku.leanlabapp.classes;

public class Product {
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
