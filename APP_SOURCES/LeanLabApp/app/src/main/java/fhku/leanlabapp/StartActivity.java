package fhku.leanlabapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


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

            /*String json = "{\"productid\" : \"1\",\"productname\":\"testname\"}";
        Product tmp = new Product();
        try {
            tmp = (Product) tmp.mapJSONStrtoJavaClass(json);
            Log.e("NOExc",tmp.getProductid()+"\nName: "+tmp.getProductname());
        } catch (IOException e) {
            Log.e("IOExc","error###############");
        }*/

    }




}