package fhku.leanlabapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;

import fhku.leanlabapp.classes.Product;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        String json = "{\"productid\" : \"1\",\"productname\":\"testname\"}";
        Product tmp = new Product();
        try {
            tmp = (Product) tmp.mapJSONStrtoJavaClass(json);
            Log.e("NOExc",tmp.getProductid()+"\nName: "+tmp.getProductname());
        } catch (IOException e) {
            Log.e("IOExc","error###############");
        }
    }

    public void openQrScanner(View view){
        Toast.makeText(this, "1", Toast.LENGTH_SHORT).show();
    }

}
