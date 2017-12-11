package fhku.leanlabapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import fhku.leanlabapp.classes.Content;
import fhku.leanlabapp.classes.Product;
import fhku.leanlabapp.classes.Productionstep;
import fhku.leanlabapp.classes.Workstep;
import fhku.leanlabapp.interfaces.database.DbConnection;

/**
 * Created by Jojo on 24.10.2017.
 */

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView viewstation = (TextView)findViewById(R.id.station);

        TextView viewproduct = (TextView)findViewById(R.id.product);

        Intent intent = getIntent();

        String station = intent.getStringExtra("station");
        String product = intent.getStringExtra("product");

        viewproduct.setText(product);
        viewstation.setText(station);

        loadWorksteps(product, station);

        //Workstep.Loaded_Worksteps.get(0).getProductionstepid();
        //Toast.makeText(getApplicationContext(), int1, Toast.LENGTH_SHORT).show();



        //was wir brauchen:
        //anzahl und ids von Worksteps eines productionstep
        //bei

        //

    }

    public void loadWorksteps(String product, String station){

        try {
            String sqlstatement = "sql_statment=Select * From Workstep;";
            String sqlstatement1 = "sql_statment=Select * From Productionstep;";
            String sqlstatement2 = "sql_statment=Select * From content;";
            Workstep.Loaded_Worksteps = (new Workstep(1)).MapJsonRowsToObject(DbConnection.sendRequestForResult_ASYNC(
                    new String[] {sqlstatement}, "get", false
            ));
            Productionstep.Loaded_Productionsteps = (new Productionstep(1).MapJsonRowsToObject(DbConnection.sendRequestForResult_ASYNC(
                    new String[] {sqlstatement1}, "get", false
            )));
            Content.Loaded_Contents = (new Content(1).MapJsonRowsToObject(DbConnection.sendRequestForResult_ASYNC(
                    new String[] {sqlstatement2}, "get", false
            )));

            

            //String sqlstatemnt = "sql_statement=Select * From Workstep Join Productionstep ON Workstep.Productionstepid Where Productionstep.Productionstepid = Workstep.Productionstepid AND Stationid = " + station + " AND Produktid = " +product+";";
            //Log.i("info", sqlstatemnt);


        }catch (Exception e){
            e.printStackTrace();
        }

    }





}
