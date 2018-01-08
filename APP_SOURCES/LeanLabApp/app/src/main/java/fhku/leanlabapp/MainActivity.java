package fhku.leanlabapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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
import static fhku.leanlabapp.classes.JoinQuery.Loaded_JoinQuerys;


public class MainActivity extends AppCompatActivity {

    int step = 1;
    ArrayList<JoinQuery> liste = exampleArraylist();
    final int maxstep = liste.size();
    long begintime = getTime();

    VideoView video;
    ImageView picture;

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

        TextView view1 = (TextView)findViewById(R.id.edittext);

        TextView viewstation = (TextView)findViewById(R.id.station);

        TextView viewproduct = (TextView)findViewById(R.id.product);

        ImageButton buttonforward = (ImageButton)findViewById(R.id.imgbtnforward);

        final ImageButton buttonback = (ImageButton)findViewById(R.id.imgbtnback);
        buttonback.setVisibility(View.INVISIBLE);

        final TextView textViewCurrentStep = (TextView)findViewById(R.id.currentstep);

        TextView textViewMaxStep = (TextView)findViewById(R.id.maxstep);

        setCurrentstep(step);

        setMaxstep(liste);

        DbConnection.loadVideo(video, "");

        buttonforward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (step < maxstep){

                    step = step + 1;

                    buttonback.setVisibility(View.VISIBLE);

                    setCurrentstep(step);

                } else if (step == maxstep) {

                    long endtime = getTime();
                    long neededTime = endtime - begintime;
                    long neededTimeSeconds = neededTime / 1000;

                    String time = String.valueOf(neededTimeSeconds);
                    String time1 = String.valueOf(neededTime);

                    int achievedPoints = (int) calcPoints(neededTimeSeconds);
                    User.currentUser.setPoints(User.currentUser.getPoints()+achievedPoints);
                    User.currentUser.updateUser(context);

                    String points1 = String.valueOf(achievedPoints);

                    Toast.makeText(getApplicationContext(), "Millisekunden: " + time1 + " Sekunden: " + time + " Achieved Points: " + points1, Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getApplicationContext(), LastActivity.class);

                    intent.putExtra("Punkte", achievedPoints);
                    finish();
                    startActivity(intent);

                }
            }
        });

        buttonback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (step > 1) {

                    step = step - 1;

                    setCurrentstep(step);

                    if (step ==1){
                        buttonback.setVisibility(View.INVISIBLE);
                    }


                } else if (step == 1) {

                    Toast.makeText(getApplicationContext(), "Erster Schritt",Toast.LENGTH_SHORT);
                    buttonback.setVisibility(View.INVISIBLE);

                }

            }
        });








        Intent intent = getIntent();


        String station = intent.getStringExtra("station");
        String product = intent.getStringExtra("product");
        String stationidtemp = intent.getStringExtra("stationid");
        String productidtemp = intent.getStringExtra("productid");

        int stationid = Integer.parseInt(stationidtemp);
        int productid = Integer.parseInt(productidtemp);

        viewproduct.setText(product);
        viewstation.setText(station);

        DbConnection.loadVideo(this.video, productid+"_"+stationid+".mp4");
        DbConnection.loadPicture(this.picture, productid+"_"+stationid+".JPG");



/*

        //1. Idee wie man es einbinden könnte
        TextView test = (TextView) findViewById(R.id.edittext);
        test.setText("sql_statement=SELECT Contenttext FROM Content WHERE TypID = 3;");

        //hard coded, wir brauchen noch die Funkton für die Abfrage von TypID = 1
        ImageView image = (ImageView) findViewById(R.id.btnCamera);
        image.setImageResource(R.drawable.fh_kufstein_logo);


        //For the video
        VideoView videoView = (VideoView) findViewById(R.id.btnVideo);











    }


    public void loadWorksteps(){ Hier wieder entfernen

        try {
            String sqlstatement = "sql_statement=SELECT * FROM Content WHERE `WorkstepID` = ANY (Select `WorkstepID` From Workstep Join Productionstep ON Workstep.ProductionstepID Where Productionstep.ProductionstepID = Workstep.ProductionstepID AND StationID = 'stationid' AND ProductID = 'productid' );";

            //String sqlstatement1 = "sql_statement=Select * From Productionstep;";
            //String sqlstatement2 = "sql_statement=Select * From Content;";

            Loaded_JoinQuerys = (new JoinQuery()).MapJsonRowsToObject(DbConnection.sendRequestForResult_ASYNC(
                    new String[] {sqlstatement}, "get", false,this
            ));
            /*
            Productionstep.Loaded_Productionsteps = (new Productionstep(1).MapJsonRowsToObject(DbConnection.sendRequestForResult_ASYNC(
                    new String[] {sqlstatement1}, "get", false,this
            )));
            Content.Loaded_Contents = (new Content(1).MapJsonRowsToObject(DbConnection.sendRequestForResult_ASYNC(
                    new String[] {sqlstatement2}, "get", false,this
            )));
*/

/*
        }catch (Exception e){
            e.printStackTrace();
        }
*/
    }


    //oder könnte es so funktionieren? (Anfang)
    private void setthecontents(){

        setHtmlText();

        setImageScr();

        setVideoScr();

    }

    private void setImageScr(){

    }

    private void setVideoScr(){

    }

    private void setHtmlText(){

    }

    private String getVideoScr(){
        return " ";
    }

    private String getImageScr(){
        return " ";
    }

    private String getHtmlText(){
        return " ";
    }

    private int checkLengthList (ArrayList<JoinQuery> list){
        return list.size();
    }

    private ArrayList<JoinQuery> exampleArraylist(){

        ArrayList <JoinQuery> liste = new ArrayList<>();

        JoinQuery querytest = new JoinQuery();
        querytest.setContentID(1);
        querytest.setContenttext("Nummer1");
        querytest.setTypID(1);
        querytest.setWokstepID(1);

        JoinQuery querytest1 = new JoinQuery();
        querytest1.setContentID(2);
        querytest1.setWokstepID(1);
        querytest1.setTypID(1);
        querytest1.setContenttext("Nummer2");

        JoinQuery querytest2 = new JoinQuery();
        querytest2.setContenttext("Nummer3");
        querytest2.setTypID(1);
        querytest2.setContentID(1);
        querytest2.setWokstepID(1);

        liste.add(querytest);
        liste.add(querytest1);
        liste.add(querytest2);

        return liste;

    }

    public void stepForward(){

    }

    private void stepBack(){

    }

    public void setCurrentstep(int step){

        TextView currentstep = (TextView)findViewById(R.id.currentstep);

        String i = String.valueOf(step);

        String output = "Schritt " + i;

        currentstep.setText(output);

    }

    public void setMaxstep(ArrayList<JoinQuery> liste){

        TextView maxstepview = (TextView)findViewById(R.id.maxstep);

        int a = maxstep;

        String b = String.valueOf(a);

        String output = "Schritt " + b;

        maxstepview.setText(output);

    }

    private long getTime(){
        return System.currentTimeMillis();
    }

    public long calcPoints(long seconds){

        long maxtime = maxstep * timePerWorkstep;

        long points;

        if (seconds >= maxtime) {
            points = 0;
        } else {
            points = maxtime - seconds;
        }

        return points;

    }

    private int getMaxSteps(ArrayList<JoinQuery> list){
        //Hier muss aus der JoinQuery die Anzahl der verschiedenen Workstep IDs ermittelt werden
        int maxstep = list.size();
        return maxstep;
    }

}
