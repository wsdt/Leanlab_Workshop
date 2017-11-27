package fhku.leanlabapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.zxing.Result;

import fhku.leanlabapp.classes.Product;
import fhku.leanlabapp.classes.Station;
import fhku.leanlabapp.interfaces.compatibility.MarshMallowPermission;
import fhku.leanlabapp.interfaces.database.DbConnection;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QrActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler{

    private ZXingScannerView zXingScannerView;
    public static final String EXTRA_Message = "qrcode";
    public static final int REQUEST_CODE = 1;

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


        //IMPORTANT: Needed syntax for qrcode: station_{station_id} or product_{product_id}
        String qrcodeIdentifier = qrcode.substring(0,8);
        boolean doesExist = false;
        if (qrcodeIdentifier.equals("station_") || qrcodeIdentifier.equals("product_")) {
            int id=Integer.parseInt(qrcode.substring(8,9));
            String category = qrcode.substring(0,7);
            if (id < 0) { Toast.makeText(this,"ID not valid!",Toast.LENGTH_LONG).show();}

            if (qrcodeIdentifier.equals("station_")) {
                for (Station tmp : Station.Loaded_Stations) {
                    if (tmp.getStationid() == id) {
                        doesExist = true;
                    }
                }
            } else { //must be product_
                for (Product tmp : Product.Loaded_Products) {
                    if (tmp.getProductid() == id) {
                        doesExist = true;
                    }
                }
            }

            Log.d("QRCode","QRCode syntactically valid: "+qrcode);
            Log.d("QRCode","QRCode does exist in database. ");

            Intent intent = new Intent(this, StartActivity.class);
            intent.putExtra(EXTRA_Message, qrcode);
            intent.putExtra(EXTRA_Message+"_id",id);
            intent.putExtra(EXTRA_Message+"_category", category);
            setResult(Activity.RESULT_OK,intent);
            zXingScannerView.stopCameraPreview();
            finishActivity(QrActivity.REQUEST_CODE);
        } else {
            Toast.makeText(this,"Scanned QRCode is not valid!",Toast.LENGTH_LONG).show();
        }

        zXingScannerView.resumeCameraPreview(this);
    }
}
