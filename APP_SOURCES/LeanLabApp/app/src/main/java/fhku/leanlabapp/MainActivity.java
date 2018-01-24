package fhku.leanlabapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

import fhku.leanlabapp.classes.JoinQuery;
import fhku.leanlabapp.classes.Product;
import fhku.leanlabapp.classes.User;
import fhku.leanlabapp.interfaces.database.DbConnection;
import fhku.leanlabapp.interfaces.database.LoadImageTask;

import static fhku.leanlabapp.classes.JoinQuery.Loaded_JoinQuerys;


public class MainActivity extends AppCompatActivity implements LoadImageTask.Listener {

    int step = 1;
    //ArrayList<JoinQuery> liste = exampleArraylist();
    int maxstep;
    long begintime = getTime();

    VideoView video;
    ImageView picture;

    int stationid;
    int productid;
    ImageButton buttonback;

    final int timePerWorkstep = 60;
    Context context;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        //USER MUSS NOCH GELADEN WERDEN UND PUNKTE VOM USER AUSGELESEN UND RÜCKGEGEBEN WERDEN

        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        this.context = this;
        this.video = (VideoView) findViewById(R.id.btnVideo);
        this.picture = (ImageView) findViewById(R.id.btnCamera);

        TextView view1 = (TextView) findViewById(R.id.edittext);

        TextView viewstation = (TextView) findViewById(R.id.station);

        TextView viewproduct = (TextView) findViewById(R.id.product);

        ImageButton buttonforward = (ImageButton) findViewById(R.id.imgbtnforward);

        this.buttonback = (ImageButton) findViewById(R.id.imgbtnback);
        buttonback.setVisibility(View.INVISIBLE);

        final TextView textViewCurrentStep = (TextView) findViewById(R.id.currentstep);

        TextView textViewMaxStep = (TextView) findViewById(R.id.maxstep);

        setCurrentstep(step);



        buttonforward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateNextStep();
            }
        });

        buttonback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateBeforeStep();
            }
        });


        Intent intent = getIntent();


        String station = intent.getStringExtra("station");
        String product = intent.getStringExtra("product");
        String stationidtemp = intent.getStringExtra("stationid");
        String productidtemp = intent.getStringExtra("productid");

        this.stationid = Integer.parseInt(stationidtemp);
        this.productid = Integer.parseInt(productidtemp);

        viewproduct.setText(product);
        viewstation.setText(station);

        DbConnection.loadVideo(this.video, productid + "_" + stationid + ".mp4");
        setthecontents(1);
    }


    //oder könnte es so funktionieren? (Anfang)
    private void setthecontents(int workstepid) {

        setHtmlText(workstepid);

        setImageScr(workstepid);

        setMaxstep(Loaded_JoinQuerys);
    }

    private void setImageScr(int workstepid) {
            loadImage(this.productid+"_"+this.stationid+"_"+workstepid + ".JPG");
    }

    @Override
    public void onImageLoaded(Bitmap bitmap) {
        picture.setImageBitmap(bitmap);
        Log.d("onImageLoaded", "Set bitmap.");
    }

    @Override
    public void onError() {
        //this.picture.setVisibility(View.GONE);
        //Toast.makeText(this, "Could not load image. ", Toast.LENGTH_SHORT).show();
        Log.e("onError", "Could not set bitmap");
    }

    public void loadImage(String url) {
        LoadImageTask lIt = new LoadImageTask(this);
        lIt.execute(url);
    }

    private void validateBeforeStep() {
        if (step > 1) {

            step = step - 1;

            setCurrentstep(step);

            if (step == 1) {
                buttonback.setVisibility(View.INVISIBLE);
            }


            setthecontents(step);

        } else if (step == 1) {

            Toast.makeText(getApplicationContext(), "Erster Schritt", Toast.LENGTH_SHORT).show();
            buttonback.setVisibility(View.INVISIBLE);
        }
    }


        private void validateNextStep() {
        if (step < maxstep) {

            step = step + 1;

            buttonback.setVisibility(View.VISIBLE);

            setCurrentstep(step);

            setthecontents(step);

        } else if (step == maxstep) {

            long endtime = getTime();
            long neededTime = endtime - begintime;
            long neededTimeSeconds = neededTime / 1000;

            String time = String.valueOf(neededTimeSeconds);
            String time1 = String.valueOf(neededTime);

            int achievedPoints = (int) calcPoints(neededTimeSeconds);
            User.currentUser.setPoints(User.currentUser.getPoints() + achievedPoints);
            User.currentUser.updateUser(context);

            String points1 = String.valueOf(achievedPoints);

            Toast.makeText(getApplicationContext(), "Millisekunden: " + time1 + " Sekunden: " + time + " Achieved Points: " + points1, Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(getApplicationContext(), LastActivity.class);

            intent.putExtra("Punkte", achievedPoints);
            finish();
            startActivity(intent);

        }

    }


    private void setHtmlText(int workstepid) {
        //if (JoinQuery.Loaded_JoinQuerys == null) {
        Log.d("HTMLTEXT", "JoinQueries null!");
        try {
            String sqlstatement = "sql_statement=SELECT * FROM Content WHERE WorkstepID = ANY (Select WorkstepID From Workstep Join Productionstep ON Workstep.ProductionstepID Where Productionstep.ProductionstepID = Workstep.ProductionstepID);";
            Log.d("HTMLTEXT", "SQL: "+sqlstatement);
            JoinQuery.Loaded_JoinQuerys = (new JoinQuery()).MapJsonRowsToObject(DbConnection.sendRequestForResult_ASYNC(
                    new String[]{sqlstatement}, "get", false, this
            ));
            Log.d("HTMLTEXT", "Loaded Joinqueries");
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("HTMLTEXT", "Failed to load joinqueries.");
        }
        //}

        JoinQuery neededRow = new JoinQuery();
        for (JoinQuery joinQuery : JoinQuery.Loaded_JoinQuerys) {
            Log.d("HTMLTEXT", "Made iteration: " + joinQuery.getTypID() + " / " + joinQuery.getWokstepID());
            Log.d("HTMLTEXT", joinQuery.toString());
            Log.d("HTMLTEXT", "WI: "+workstepid);
            if (joinQuery.getTypID() == 3 && joinQuery.getWokstepID() == workstepid && joinQuery.getProductID() == this.productid && joinQuery.getStationID() == this.stationid) {
                neededRow = joinQuery;
                Log.d("HTMLTEXT", "ROW: " + neededRow.toString());
                break;
            }
        }
        TextView htmltxt = ((TextView) findViewById(R.id.htmltext));
        try {
            Log.d("HTMLTEXT", "Did it");
            htmltxt.setText(Html.fromHtml(neededRow.getContenttext()));
        } catch (NullPointerException e) {
            htmltxt.setText(Html.fromHtml("<h2 style='text-align:center;'>No data found</h2>"));
            e.printStackTrace();
        }
    }



    public void setCurrentstep(int step) {

        TextView currentstep = (TextView) findViewById(R.id.currentstep);

        String i = String.valueOf(step);

        String output = "Schritt " + i;

        currentstep.setText(output);

    }

    public void setMaxstep(ArrayList<JoinQuery> liste) {

        TextView maxstepview = (TextView) findViewById(R.id.maxstep);

        int a = liste.size();

        String b = String.valueOf(a);
        this.maxstep = a;

        String output = "Schritt " + b;

        maxstepview.setText(output);

    }

    private long getTime() {
        return System.currentTimeMillis();
    }

    public long calcPoints(long seconds) {

        long maxtime = maxstep * timePerWorkstep;

        long points;

        if (seconds >= maxtime) {
            points = 0;
        } else {
            points = maxtime - seconds;
        }

        return points;

    }

    private int getMaxSteps(ArrayList<JoinQuery> list) {
        //Hier muss aus der JoinQuery die Anzahl der verschiedenen Workstep IDs ermittelt werden
        return list.size();
    }

}
