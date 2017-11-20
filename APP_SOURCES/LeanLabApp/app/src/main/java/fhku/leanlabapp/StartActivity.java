package fhku.leanlabapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import fhku.leanlabapp.classes.User;
import fhku.leanlabapp.interfaces.database.DbConnection;

public class StartActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        String [] stations = bringMyStations();
        String [] prodcuts = bringMyProducts();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Button b = (Button) findViewById(R.id.buttonqr1);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartActivity.this, QrActivity.class));
            }
        });

        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        Spinner spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner1.setOnItemSelectedListener(this);

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,stations);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinner1.setAdapter(aa);


        Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
        spinner2.setOnItemSelectedListener(this);
        ArrayAdapter bb = new ArrayAdapter(this,android.R.layout.simple_spinner_item,prodcuts);
        bb.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinner2.setAdapter(bb);



        Intent intent = getIntent();

        String qrcode = intent.getStringExtra(QrActivity.EXTRA_Message);

        spinner1.setSelection(aa.getPosition(qrcode));


        //Für euch zum Austesten, Zeile einfach auskommentieren. KevinTest()-Funktion bitte NICHT löschen (dient auch mir vorerst noch als Referenz).
        KevinTest();

    }


    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position,long id) {

    }


    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    public void KevinTest() {
        //Beispiel SQL Abfrage für Tabelle Users und Ausgabe der ersten Zeile.
        try {
            User tmp = new User("tmp");
            User.Loaded_Users = tmp.CastArrayListObjToSpecObj(tmp.MapJsonRowsToObject(DbConnection.sendRequestForResult_ASYNC(new String[] {"sql_statement=SELECT * FROM User;"},"post",false)),tmp);

            Log.e("WORKED","Username: "+(User.Loaded_Users.get(0)).getUsername()+"/// Password: "+(User.Loaded_Users.get(0)).getPassword());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    public String [] bringMyStations(){
        String [] stations = {"Station1", "Station2", "Station3", "Station4"};
        return stations;
    }

    public String [] bringMyProducts(){
        String [] products = {"Product1", "Product2", "Product3", "Product4"};
        return products;
    }

}