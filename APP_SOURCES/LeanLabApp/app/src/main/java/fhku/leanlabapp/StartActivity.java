package fhku.leanlabapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Jojo on 24.10.2017.
 */

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }

    public void openQrScanner(View view){
        Toast.makeText(this, "1", Toast.LENGTH_SHORT).show();
    }

}
