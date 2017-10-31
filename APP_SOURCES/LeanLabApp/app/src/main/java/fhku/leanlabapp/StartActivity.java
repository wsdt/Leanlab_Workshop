package fhku.leanlabapp;

            import android.content.Intent;
            import android.os.Bundle;
            import android.support.v7.app.AppCompatActivity;
            import android.view.View;
            import android.widget.Button;
            import android.widget.Toast;

/**
 * Created not by Jojo on 24.10.2017.
 */

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

    }




}