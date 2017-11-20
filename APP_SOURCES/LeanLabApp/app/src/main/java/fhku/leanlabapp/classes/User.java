package fhku.leanlabapp.classes;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import fhku.leanlabapp.classes.exceptions.JsonToObjectMapper_Exception;
import fhku.leanlabapp.interfaces.JsonStrConverter;
import fhku.leanlabapp.interfaces.Mapper;

public class User extends Mapper{
    private static final String LOG_TAG = "USER";
    private String Username;
    private String Password;
    //IMPORTANT: Be sure to empty the arraylist if you use it, unless you except sth to be in there
    public static ArrayList<User> Loaded_Users = new ArrayList<>(); //do not add Products to list automatically (only if you need it)

    //Constructor
    public User(String Username, String Password) {
        this.setUsername(Username);
        this.setPassword(Password);
    }

    public User(String Username) { //no empty constructor, because id is primary key
        this.setUsername(Username);
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
    public User MapJsonToObject(String json) throws JsonToObjectMapper_Exception {
        JsonStrConverter tmp = new JsonStrConverter(json);
        tmp.convertStrToJson();
        return MapJsonToObject(tmp.getJson_obj());
    }

    @Override
    public User MapJsonToObject(JSONObject json) throws JsonToObjectMapper_Exception {
        User obj;
        try {
            obj = new User(json.getString("Username"), json.getString("Password"));
        } catch(JSONException e) {
            Log.e("MapJsonToObject","JSON could not be mapped to Object!");
            e.printStackTrace();
            throw new JsonToObjectMapper_Exception("JSON could not be mapped to Object!");
        }
        return obj;
    }
}
