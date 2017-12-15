package fhku.leanlabapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.ResultSet;
import java.util.ArrayList;

import fhku.leanlabapp.classes.Content;
import fhku.leanlabapp.classes.JoinQuery;
import fhku.leanlabapp.classes.Product;
import fhku.leanlabapp.classes.Productionstep;
import fhku.leanlabapp.classes.Workstep;
import fhku.leanlabapp.interfaces.database.DbConnection;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView view1 = (TextView)findViewById(R.id.edittext);

        TextView viewstation = (TextView)findViewById(R.id.station);

        TextView viewproduct = (TextView)findViewById(R.id.product);

        Intent intent = getIntent();

        String station = intent.getStringExtra("station");
        String product = intent.getStringExtra("product");
        String stationidtemp = intent.getStringExtra("stationid");
        String productidtemp = intent.getStringExtra("productid");

        int stationid = Integer.parseInt(stationidtemp);
        int productid = Integer.parseInt(productidtemp);



        viewproduct.setText(product);
        viewstation.setText(station);

        loadWorksteps(product, station);



        /*

        Toast.makeText(getApplicationContext(), a, Toast.LENGTH_SHORT).show();

        int b  = Content.Loaded_Contents.get(0).getContentid();

        Toast.makeText(getApplicationContext(), b, Toast.LENGTH_SHORT).show();*/

        //Workstep.Loaded_Worksteps.get(0).getProductionstepid();
        //Toast.makeText(getApplicationContext(), int1, Toast.LENGTH_SHORT).show();



        //was wir brauchen:
        //anzahl und ids von Worksteps eines productionstep
        //bei

        //

    }

    public void loadWorksteps(String product, String station){

        try {
            String sqlstatement = "sql_statement=SELECT * FROM Content WHERE `WorkstepID` <ANY (Select `WorkstepID` From Workstep Join Productionstep ON Workstep.ProductionstepID Where Productionstep.ProductionstepID = Workstep.ProductionstepID AND Station = 'station' AND Product = 'product' );";
            /*
            String sqlstatement1 = "sql_statement=Select * From Productionstep;";
            String sqlstatement2 = "sql_statement=Select * From Content;";
            */
            JoinQuery.Loaded_JoinQuerys = (new JoinQuery()).MapJsonRowsToObject(DbConnection.sendRequestForResult_ASYNC(
                    new String[] {sqlstatement}, "get", false,this
            ));
            /*
            Productionstep.Loaded_Productionsteps = (new Productionstep(1).MapJsonRowsToObject(DbConnection.sendRequestForResult_ASYNC(
                    new String[] {sqlstatement1}, "get", false,this
            )));
            Content.Loaded_Contents = (new Content(1).MapJsonRowsToObject(DbConnection.sendRequestForResult_ASYNC(
                    new String[] {sqlstatement2}, "get", false,this
            )));
*/


        }catch (Exception e){
            e.printStackTrace();
        }

    }





}
