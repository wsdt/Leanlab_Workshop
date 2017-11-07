package fhku.leanlabapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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

            /*String json = "{\"Productid\":\"1\",\"Productname\":{\"test\":\"testname\"}}";
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