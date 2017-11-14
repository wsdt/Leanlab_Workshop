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

import fhku.leanlabapp.interfaces.DbConnection;


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




            KevinTest();

    }

    public void KevinTest() {
        new Thread(new Runnable() {
            public void run() {
                String json_str = DbConnection.sendRequestForResult(DbConnection.encodeParameters(new String[] {"sql_statement=SELECT * FROM User"}),"post",false);
                Log.e("WORKED","JSON"+json_str);
            }
        }).start();
    }




}