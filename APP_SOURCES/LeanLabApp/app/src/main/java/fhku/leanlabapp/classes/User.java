package fhku.leanlabapp.classes;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

import fhku.leanlabapp.R;
import fhku.leanlabapp.classes.exceptions.JsonToObjectMapper_Exception;
import fhku.leanlabapp.interfaces.Dialog;
import fhku.leanlabapp.interfaces.JsonStrConverter;
import fhku.leanlabapp.interfaces.Mapper;
import fhku.leanlabapp.interfaces.database.DbConnection;

public class User extends Mapper{
    private static final String LOG_TAG = "USER";
    private String Username;
    private String Password;
    private int Points;
    //IMPORTANT: Be sure to empty the arraylist if you use it, unless you except sth to be in there
    public static ArrayList<User> Loaded_Users = new ArrayList<>(); //do not add Products to list automatically (only if you need it)

    //Constructor
    public User(String Username, String Password, int Points) {
        this.setUsername(Username);
        this.setPassword(Password);
        this.setPoints(Points);
    }

    public User(String Username) { //no empty constructor, because id is primary key
        this.setUsername(Username);
    }

    public static String registerUser(Context context, String username) {
        String result = "";
        try {
            result = DbConnection.sendRequestForResult_ASYNC(
                    new String[]{"sql_statement=INSERT INTO User (Username, Points) VALUES ('"+DbConnection.escapeSql(username)+"', 0);"}, "get", false, context);
        } catch (Exception e) {
            Log.e("User","Could not register new User!");
            e.printStackTrace();
        }
        return result;
    }

    //Getter/Setter ------------------------------
    public String getUsername() {
        return this.Username;
    }

    public void setUsername(String username) {
        this.Username = username;
    }

    public String getPassword() {
        return this.Password;
    }

    public void setPassword(String password) {
        this.Password = password;
    }

    // MAPPING METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    @Override
    public User MapJsonToObject(JSONObject json) throws JsonToObjectMapper_Exception {
        User obj;
        try {
            obj = new User(json.getString("Username"), json.getString("Password"), json.getInt("Points"));
        } catch(JSONException e) {
            Log.e("MapJsonToObject","JSON could not be mapped to Object!");
            e.printStackTrace();
            throw new JsonToObjectMapper_Exception("JSON could not be mapped to Object!");
        }
        return obj;
    }


    public int getPoints() {
        return Points;
    }

    public void setPoints(int points) {
        Points = points;
    }
}
