package fhku.leanlabapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import fhku.leanlabapp.classes.Product;
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

        //was wir brauchen:
        //anzahl und ids von Worksteps eines productionstep
        //bei

        //

    }

    public void loadWorksteps(String product, String station){

        try {

            String sqlstatemnt = "sql_statement=Select * From Workstep Join Productionstep ON Workstep.Productionstepid Where Productionstep.Productionstepid = Workstep.Productionstepid AND Stationid = " + station + " AND Produktid = " +product+";";


            Workstep workstep = new Workstep(1);
            Workstep.Loaded_Worksteps = workstep.MapJsonRowsToObject(DbConnection.sendRequestForResult_ASYNC(
                    new String[] {sqlstatemnt}, "get", false
            ));


        }catch (Exception e){
            e.printStackTrace();
        }

    }





}
