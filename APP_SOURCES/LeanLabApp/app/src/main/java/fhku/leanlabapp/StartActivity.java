package fhku.leanlabapp;

import android.content.Intent;
import android.media.DeniedByServerException;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import fhku.leanlabapp.classes.Product;
import fhku.leanlabapp.classes.Station;
import fhku.leanlabapp.classes.User;
import fhku.leanlabapp.interfaces.JsonStrConverter;
import fhku.leanlabapp.interfaces.database.DbConnection;

public class StartActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    public class OnItemSelectedListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> arg0, View arg1, int position,long id) {

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        ImageButton qrButton = (ImageButton) findViewById(R.id.qrButton);

        qrButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartActivity.this, QrActivity.class));
            }
        });


        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        Spinner spinnerProducts = (Spinner) findViewById(R.id.spinnerProducts);
        Spinner spinnerStations = (Spinner) findViewById(R.id.spinnerStations);
        spinnerProducts.setOnItemSelectedListener(new OnItemSelectedListener());
        spinnerStations.setOnItemSelectedListener(new OnItemSelectedListener());


        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,loadStations());
        ArrayAdapter bb = new ArrayAdapter(this,android.R.layout.simple_spinner_item,loadProducts());
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bb.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinnerProducts.setAdapter(aa);
        spinnerStations.setAdapter(bb);


        //Setting the ArrayAdapter data on the Spinner
        Intent intent = getIntent();
        String qrcode = intent.getStringExtra(QrActivity.EXTRA_Message);

        spinnerStations.setSelection(aa.getPosition(qrcode));
        spinnerProducts.setSelection(bb.getPosition(qrcode));


    }


    public void KevinTest() {
        //Beispiel SQL Abfrage für Tabelle Users und Ausgabe der ersten Zeile.
        try {
            User tmp = new User("tmp");



            User.Loaded_Users = tmp.MapJsonRowsToObject(DbConnection.sendRequestForResult_ASYNC(new String[]
                    {"sql_statement=SELECT * FROM User;"},"post",false));


            Log.e("WORKED","Username: "+(User.Loaded_Users.get(0)).getUsername()+"/// Password: "+(User.Loaded_Users.get(0)).getPassword());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList loadStations(){
        try {
            Station station = new Station(1);
            Station.Loaded_Stations = station.MapJsonRowsToObject(DbConnection.sendRequestForResult_ASYNC
                            (new String[] {"sql_statement=Select * From Station;"}, "post", false));
        } catch (Exception e) {
            e.printStackTrace();
        }
        ArrayList<String> stationlist = new ArrayList<>();
        if (Station.Loaded_Stations != null) {
            for (Station station : Station.Loaded_Stations) {
                stationlist.add(station.getStationname()+" ("+station.getStationid()+")");
            }
        } else {
            Log.e("loadStations","Could not load stations!");
        }

        return stationlist;
    }

    public ArrayList loadProducts(){
        try {
            Product product1 = new Product(1);
            Product.Loaded_Products = product1.MapJsonRowsToObject(DbConnection.sendRequestForResult_ASYNC(
                    new String[] {"sql_statement=Select * From Product;"}, "get", false
            ));
        }catch (Exception e){
            e.printStackTrace();
        }

        ArrayList<String> productlist = new ArrayList<>();
        if (Product.Loaded_Products != null) {
            for (Product product : Product.Loaded_Products) {
                productlist.add(product.getProductname()+" ("+product.getProductid()+")");
            }
        } else {
            Log.e("loadProducts","Could not load products!");
        }
        return productlist;
    }



    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

}