package fhku.leanlabapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import fhku.leanlabapp.classes.User;
import fhku.leanlabapp.interfaces.Dialog;
import fhku.leanlabapp.interfaces.database.DbConnection;

/*
* ANLEITUNG FÜR CONTENT FILES UPLOAD:
*   Dateien werden an den Server geschickt und am Server selbst gespeichert.
*   Erst nach erfolgreicher Speicherung wird der Pfad zu der jeweiligen Datei in die Datenbank gespeichert.
* */


public class UserActivity extends AppCompatActivity {
    private static final String TAG = "USERACT";
    private Context thisActivity;
    public User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        thisActivity = this;

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.userlayout);

        layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                hideKeyboard(view);
                return false;
            }
        });

        final Button goButton = (Button) findViewById(R.id.go);
        final EditText user = (EditText)findViewById(R.id.edittext);

        //Überprüfungen für Usernamen
        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = (String)user.getText().toString();
                if (isUsernameValid(username)) {
                    Log.d(TAG,"Username is valid!");
                    if (doesUserExist(username)) {
                        //Login
                        User.currentUser = getUser(username);
                        goToStart();
                    } else {
                        //Register new user
                        String result = User.registerUser(thisActivity,username);
                        Log.i("Info", "else");
                        //Login into new Useraccount
                        if (doesUserExist(username)) {
                            User.currentUser = getUser(username);
                            goToStart();
                            Log.i("Info", "3. if");
                        }
                        Log.d(TAG,"Tried to register user and got result: "+result);
                    }
                } else {
                    Log.e(TAG,"Username is not valid!");
                    (Dialog.createDialog(thisActivity,"Invalid Username", "Username is not valid!\nYour username need to be shorter than 50 characters.",R.drawable.fh_kufstein_logo_transparent)).show();
                }
            }
        });




    }
    private void hideKeyboard(View view) {
        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private void goToStart(){
        Intent intent = new Intent(getApplicationContext(), StartActivity.class);
        startActivity(intent);
    }

    private User getUser(String username) {
        //Only call if user exists
        loadUsersFromDbIfNotAvailable();
        User resultUser = User.Loaded_Users.get(0); //default user

        for (User user : User.Loaded_Users) {
            if (user.getUsername().equals(username)) {
                resultUser = user;
            }
        }
        return resultUser;
    }

    private boolean isUsernameValid(String username) {
        boolean isUsernameValid = false;
        if (username.length() <= 50) {
            isUsernameValid = true;
        }
        return isUsernameValid;
    }

    private boolean doesUserExist(String username) {
        boolean doesExist = false;

        loadUsersFromDb();

        for (User user : User.Loaded_Users) {
            if (user.getUsername().equals(username)) {
                doesExist = true;
            }
        }
        return doesExist;
    }

    private void loadUsersFromDbIfNotAvailable() {
        //Load Users from DB if not loaded already
        if (User.Loaded_Users.size() <= 0 || User.Loaded_Users == null) {
            loadUsersFromDb();
        }
    }

    private void loadUsersFromDb() {
        try {
            User.Loaded_Users = (new User("")).MapJsonRowsToObject(DbConnection.sendRequestForResult_ASYNC(
                    new String[]{"sql_statement=SELECT * FROM User;"}, "get", false, this));
        } catch (Exception e) {
            Log.e(TAG,"Could not load current Users!");
            e.printStackTrace();
            Dialog conerr = Dialog.createDialog(this,"Connection Error","Could not load registered users!\nMaybe you are not connected to the WLAN.",R.drawable.fh_kufstein_logo_transparent);
            conerr.show();
        }
    }


}
