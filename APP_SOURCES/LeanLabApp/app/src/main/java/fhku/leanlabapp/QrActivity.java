package fhku.leanlabapp;

import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.zxing.Result;

import fhku.leanlabapp.interfaces.compatibility.MarshMallowPermission;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QrActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler{

    private ZXingScannerView zXingScannerView;
    public static final String EXTRA_Message = "qrcode";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);

        MarshMallowPermission marshMallowPermission = new MarshMallowPermission(this);

        if (marshMallowPermission.checkPermissionForCamera() == false) {

            marshMallowPermission.requestPermissionForCamera();

            zXingScannerView = new ZXingScannerView(getApplicationContext());

            setContentView(zXingScannerView);

            zXingScannerView.setResultHandler(this);

            zXingScannerView.startCamera();

        } else {
            zXingScannerView = new ZXingScannerView(getApplicationContext());

            setContentView(zXingScannerView);

            zXingScannerView.setResultHandler(this);

            zXingScannerView.startCamera();
        }
    }

    @Override
    protected void onPause() {

        super.onPause();

        zXingScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result result) {

        Toast.makeText(getApplicationContext(),result.getText(),Toast.LENGTH_SHORT).show();

        String qrcode = result.getText().toString();

        Intent intent = new Intent(this, StartActivity.class);
        intent.putExtra(EXTRA_Message, qrcode);
        intent.putExtra("checker", "1");
        startActivity(intent);



        //Hier würde jetzt der Abgleich mit der Datenbank erfolgen
        //Wenn die Richtige Station ausgewählt ist läd eine Activity mit den Schritten
        //Wenn die Falsche Station ausgewählt ist wird eine Toast ausgesendet mit dies "dies ist nicht die Richtige station" o. ä.
        //Wenn der QR nicht in der Datenbank ist kann eventuell eine Meldung kommen wie "dieser QR Code befindet sich nicht in der Datenbank, bitte Versuchen sie es erneut oder wenden sie sich an den administrator


        zXingScannerView.resumeCameraPreview(this);


    }
}
