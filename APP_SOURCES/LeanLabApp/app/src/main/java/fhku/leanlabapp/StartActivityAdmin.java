package fhku.leanlabapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;


public class StartActivityAdmin extends AppCompatActivity implements View.OnClickListener{

    public Spinner spinnerProducts;
    public Spinner spinnerStations;
    private ArrayAdapter aa, bb;
    String station = "";
    String product = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_admin);

        Button buttonAdmin = (Button) findViewById(R.id.admin);
        ImageButton buttonEdit = (ImageButton) findViewById(R.id.edit);
        ImageButton buttonAdd = (ImageButton) findViewById(R.id.add);


        buttonAdmin.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intentAdmin = new Intent(StartActivityAdmin.this, StartActivity.class);
                StartActivityAdmin.this.startActivity(intentAdmin);
                return false;
            }
        });
        
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentGo = new Intent(StartActivityAdmin.this, MainActivityAdmin.class);
                StartActivityAdmin.this.startActivity(intentGo);
            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInputDialog();
            }
        });
    }

    protected void showInputDialog() {

        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(StartActivityAdmin.this);
        View promptView = layoutInflater.inflate(R.layout.dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(StartActivityAdmin.this);
        alertDialogBuilder.setView(promptView);

        final EditText editText = (EditText) promptView.findViewById(R.id.edittext);
        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Code für Überprüfung der Station und Produkt auf bereits bestehende Produkte/Stationen
                        // Speicherung von Produkt und Station in den Spinner

                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    @Override
    public void onClick(View v) {

    }
}
