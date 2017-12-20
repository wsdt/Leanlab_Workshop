package fhku.leanlabapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fhku.leanlabapp.classes.Product;
import fhku.leanlabapp.classes.Station;
import fhku.leanlabapp.interfaces.database.DbConnection;


public class StartActivityAdmin extends AppCompatActivity implements View.OnClickListener{

    public Spinner spinnerProducts;
    public Spinner spinnerStations;
    private ArrayAdapter aa, bb;
    String station = "";
    String product = "";
    String productid = "";
    String stationid = "";

    public class OnItemSelectedListener implements AdapterView.OnItemSelectedListener{

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            Spinner spinner = (Spinner) adapterView;

            if (spinner.getId() == R.id.spinner1)
            {
                product = spinner.getItemAtPosition(i).toString();
                Log.i("product", product);
                String tmp = product;

                Pattern p = Pattern.compile("[+-]?[0-9]+");
                Matcher m = p.matcher(tmp);

                while ( m.find() ) {
                    productid = tmp.substring(m.start(), m.end());
                }

            } else if (spinner.getId() == R.id.spinner2){
                station = spinner.getItemAtPosition(i).toString();
                Log.i("station", station);
                String tmp = station;

                Pattern p = Pattern.compile("[+-]?[0-9]+");
                Matcher m = p.matcher(tmp);

                while ( m.find() ) {
                    stationid = tmp.substring(m.start(), m.end());
                }
            }
        }
            



        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
            Toast.makeText(getApplicationContext(), "halamadrid", Toast.LENGTH_SHORT).show();

        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_admin);

        Button buttonAdmin = (Button) findViewById(R.id.admin);
        Button buttonEdit = (Button) findViewById(R.id.edit);
        Button buttonAdd = (Button) findViewById(R.id.add);

        Log.i("test", "test");

        makeSpinnerInteractive();


        buttonAdmin.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intentAdmin = new Intent(StartActivityAdmin.this, StartActivity.class);
                StartActivityAdmin.this.startActivity(intentAdmin);
                return false;
            }
        });
        
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentGo = new Intent(StartActivityAdmin.this, MainActivityAdmin.class);
                intentGo.putExtra("productname", product);
                intentGo.putExtra("productid", productid);
                intentGo.putExtra("stationname", station);
                intentGo.putExtra("stationid", stationid);
                StartActivityAdmin.this.startActivity(intentGo);
            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInputDialog();
            }
        });
    }

    protected void showInputDialog() {

        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(StartActivityAdmin.this);
        View promptView = layoutInflater.inflate(R.layout.dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(StartActivityAdmin.this);
        alertDialogBuilder.setView(promptView);

        final EditText editText = (EditText) promptView.findViewById(R.id.edittext);
        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Code für Überprüfung der Station und Produkt auf bereits bestehende Produkte/Stationen
                        // Speicherung von Produkt und Station in den Spinner

                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    @Override
    public void onClick(View v) {

    }

    private void makeSpinnerInteractive() {
        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        spinnerProducts = (Spinner) findViewById(R.id.spinner1);
        spinnerStations = (Spinner) findViewById(R.id.spinner2);
        spinnerProducts.setOnItemSelectedListener(new StartActivityAdmin.OnItemSelectedListener());
        spinnerStations.setOnItemSelectedListener(new StartActivityAdmin.OnItemSelectedListener());


        //Creating the ArrayAdapter instance having the country list
        aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item, loadProducts());
        bb = new ArrayAdapter(this,android.R.layout.simple_spinner_item,loadStations());
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bb.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinnerProducts.setAdapter(aa);
        spinnerStations.setAdapter(bb);

    }

    public ArrayList loadProducts(){

        ArrayList<String> productlist = new ArrayList<>();
        if (Product.Loaded_Products!= null) {
            for (Product product : Product.Loaded_Products) {
                productlist.add(product.getProductname()+" ("+product.getProductid()+")");

            }
        } else {
            Log.e("loadProducts","Could not load stations!");
        }

        return productlist;
    }

    public ArrayList loadStations(){

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





}
