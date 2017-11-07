package fhku.leanlabapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.fasterxml.jackson.databind.DatabindContext;
import com.fasterxml.jackson.databind.Module;

import org.json.JSONObject;

import java.io.IOException;

import fhku.leanlabapp.classes.Product;


public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Button b = (Button) findViewById(R.id.buttonqr1);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartActivity.this, QrActivity.class));
            }
        });

        Spinner spinner = (Spinner) findViewById(R.id.spinner);



        String [] arraySpinner = new String[] {
                "1", "2", "3", "4", "5"
        };



            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, arraySpinner);
            spinner.setAdapter(adapter);

            Intent intent = getIntent();

            String qrcode = intent.getStringExtra(QrActivity.EXTRA_Message);



            TextView textView = (TextView) findViewById(R.id.qrcode);
            textView.setText(qrcode);


        
            /*String json = "{\"productid\" : \"1\",\"productname\":\"testname\"}";
        Product tmp = new Product();
        try {
            /*Module temp = tmp.getIgnoreJacksonModule();
            Log.e("IOExc","ModuleString:"+temp.toString());
            tmp = (Product) tmp.JsonStrToObj(json,tmp);
            Log.e("NOExc",tmp.getProductid()+"\nName: "+tmp.getProductname());*
            JSONObject jsonobj=tmp.JsonStrToJsonObj(json);
            Log.e("SUCCESS",jsonobj.get("Productid").toString());

            json = tmp.JsonObjToJsonStr(jsonobj);
            Log.e("SUCCESS","JSON STRING: "+json);
        } catch (Exception e) {
            Log.e("IOExc","ERROR: "+e.getMessage());
            for (StackTraceElement m : e.getStackTrace()) {
                Log.e("IOExc",m.toString());
            }
        }*/

    }




}