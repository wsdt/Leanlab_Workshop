package fhku.leanlabapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import fhku.leanlabapp.classes.Product;
import fhku.leanlabapp.classes.Station;
import fhku.leanlabapp.interfaces.database.DbConnection;

public class StartActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    public Spinner spinnerProducts;
    public Spinner spinnerStations;
    private ArrayAdapter aa, bb;
    String station = "";
    String stationid = "";
    String product = "";
    String productid = "";

    public class OnItemSelectedListener implements AdapterView.OnItemSelectedListener {
        // Methode, wenn ein Produkt oder eine Station ausgewählt wird mit Überprüfungen
        @Override
        public void onItemSelected(AdapterView<?> arg0, View arg1, int position,long id) {
            //position--;

            Spinner spinner = (Spinner) arg0;

            //TODO: Receive values from QRCode Activity and setText() to Spinner

            if (spinner.getId() == R.id.spinnerProducts) {
                product = spinnerProducts.getItemAtPosition(position).toString();
                String tmp = product;

                Pattern p = Pattern.compile("[+-]?[0-9]+");
                Matcher m = p.matcher(tmp);

                while ( m.find() ) {
                    productid = tmp.substring(m.start(), m.end());
                }


            } else if (spinner.getId() == R.id.spinnerStations){
                station = spinnerStations.getItemAtPosition(position).toString();
                String tmp = station;

                Pattern p = Pattern.compile("[+-]?[0-9]+");
                Matcher m = p.matcher(tmp);

                while ( m.find() ) {
                    stationid = tmp.substring(m.start(), m.end());
                }
            }

            Log.i("go", "Station: + " + station + " Stationid: " + stationid + " Produkt: " + product + " Produktid: " + productid);

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

        Button buttonGo = (Button)findViewById(R.id.go);

        Button buttonAdmin = (Button) findViewById(R.id.admin1);

        buttonAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Bearbeitungsmodus noch nicht verfügbar!",Toast.LENGTH_SHORT).show();
            }
        });

        //Bearbeitungsmodus funktioniert

       /* buttonAdmin.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intentAdmin = new Intent(StartActivity.this, StartActivityAdmin.class);
                StartActivity.this.startActivity(intentAdmin);
                Log.i("info", "went to startactivityadmin");
                return false;
           }
        });*/

//Übergeben eines Intent an die MainActivity
        buttonGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("product", product);
                intent.putExtra("productid", productid);
                intent.putExtra("station", station);
                intent.putExtra("stationid", stationid);

                Log.i("intent to main", "Station: " + station + " Produkt: " + product);

                startActivity(intent);

            }

        });

        qrButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(StartActivity.this, QrActivity.class),QrActivity.REQUEST_CODE);
            }
        });

        makeSpinnerInteractive();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("onActivityResult","Got a result");
        switch(requestCode) {
            case QrActivity.REQUEST_CODE:
                if (resultCode == RESULT_OK||resultCode==1) {
                    //Setting the ArrayAdapter data on the Spinner
                    Log.d("StartActivity_Result","Got valid qrcode: "+data.getStringExtra(QrActivity.EXTRA_Message));
                    setScannedValues(data);
                } else {
                    Log.e("FetchQRCode","Could not fetch QRCode");
                    Toast.makeText(this,"Could not fetch QRCode.",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                Log.e("RequestCode","Unknown Request Code");
                Toast.makeText(this, "Unknown Request Code. ",Toast.LENGTH_SHORT).show();
        }
    }

    private void setScannedValues(Intent data) {
        int position = data.getIntExtra(QrActivity.EXTRA_Message+"_id",1)-1;
        String category = data.getStringExtra(QrActivity.EXTRA_Message+"_category");


        //TODO: Gescannter Value noch nicht gesetzt!
        if (category.equals("station")) {
            spinnerStations.setSelection(bb.getPosition(bb.getItem(position)));
        } else if (category.equals("product")) {
            spinnerProducts.setSelection(aa.getPosition(aa.getItem(position)));
        } else {
            Log.e("setScannedValues","Could not set default value!");
        }
    }

    private void makeSpinnerInteractive() {
        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        spinnerProducts = (Spinner) findViewById(R.id.spinnerProducts);
        spinnerStations = (Spinner) findViewById(R.id.spinnerStations);
        spinnerProducts.setOnItemSelectedListener(new OnItemSelectedListener());
        spinnerStations.setOnItemSelectedListener(new OnItemSelectedListener());


        //Creating the ArrayAdapter instance having the country list
        aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,loadProducts());
        bb = new ArrayAdapter(this,android.R.layout.simple_spinner_item,loadStations());
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bb.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinnerProducts.setAdapter(aa);
        spinnerStations.setAdapter(bb);

    }


    public ArrayList loadStations(){
        try {
            Station station = new Station(1);
            Station.Loaded_Stations = station.MapJsonRowsToObject(DbConnection.sendRequestForResult_ASYNC
                            (new String[] {"sql_statement=Select * From Station;"}, "post", false,this));
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
                    new String[] {"sql_statement=Select * From Product;"}, "get", false,this));
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