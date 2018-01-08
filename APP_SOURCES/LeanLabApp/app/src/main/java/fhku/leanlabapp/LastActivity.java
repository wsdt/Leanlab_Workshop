package fhku.leanlabapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class LastActivity extends AppCompatActivity {

    private final int levelPoints = 100;
    private  int playerPoints = 340;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last);

        //die Textviews usw. hohlen
        TextView levelActivity = (TextView)findViewById(R.id.level);
        TextView currentPointsActivity = (TextView)findViewById(R.id.currentPoints);
        TextView maxPointsActivity = (TextView)findViewById(R.id.maxPoints);
        Button buttonBackToMain = (Button)findViewById(R.id.btnBackToStart);

        //Zuweisung Views

        levelActivity.setText("Level " + calcLevel(playerPoints));
        maxPointsActivity.setText("Punkte " + String.valueOf(levelPoints));
        currentPointsActivity.setText("Punkte " + calcPointsToNextLevel(playerPoints));


        buttonBackToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), StartActivity.class);
                startActivity(intent);
            }
        });






        //WICHITG, HIER MUSS NOCH GEWÄHRLEISTET WERDEN, DASS MAN NICHT AUF DIE ACTIVITY MainActivity ZURUECKSPRINGEN KANN, HABS JETZT MIT FINISH IN DER MAIN GELÖST
        //KANN ABER NICHT KONTROLLIEREN, OB DAS SO FUNKTIONIERT

    }

    private String calcLevel(int playerPoints){

        double levelDouble = (playerPoints / levelPoints) + 1;

        int cast = (int)levelDouble;

        String level = String.valueOf(cast);

        return level;

    }

    private String calcPointsToNextLevel(int playerPoints){

        int points = playerPoints % levelPoints;

        String points1 = String.valueOf(points);

        return points1;

    }




}
