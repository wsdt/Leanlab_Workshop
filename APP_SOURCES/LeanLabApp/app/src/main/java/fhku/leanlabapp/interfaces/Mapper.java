package fhku.leanlabapp.interfaces;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import fhku.leanlabapp.classes.exceptions.JsonToObjectMapper_Exception;


public abstract class Mapper <OBJ> {
    // MAPPING METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    //MAPPING SINGLE ROW TO OBJECT - START #############################################
    public OBJ MapJsonToObject(String json) throws JsonToObjectMapper_Exception {
        JsonStrConverter tmp = new JsonStrConverter(json);
        return MapJsonToObject(tmp.convertStrToJson());
    }

    public abstract OBJ MapJsonToObject(JSONObject json) throws JsonToObjectMapper_Exception;
    //MAPPING SINGLE ROW TO OBJECT - END ###############################################

    //MAPPING SEVERAL ROWS TO OBJECT - START ###########################################
    public ArrayList<OBJ> MapJsonRowsToObject(String json) throws JsonToObjectMapper_Exception {
        JsonStrConverter tmp = new JsonStrConverter(json);
        return MapJsonRowsToObject(tmp.convertStrToJson());
    }

    public ArrayList<OBJ> MapJsonRowsToObject(JSONObject json) throws JsonToObjectMapper_Exception {
        JSONObject json_data = GetJsonDataResult(json);
        Iterator<?> keys = json_data.keys();
        ArrayList<OBJ> all_rows_mapped = new ArrayList<>();

        //Thanks to: https://stackoverflow.com/questions/9151619/how-to-iterate-over-a-jsonobject
        while (keys.hasNext()) {
            String key = (String) keys.next();
            try {
                if (json_data.get(key) instanceof JSONObject) {
                    all_rows_mapped.add(MapJsonToObject((JSONObject) json_data.get(key)));
                }
            } catch (JSONException e) {
                e.printStackTrace();
                throw new JsonToObjectMapper_Exception();
            }
        }
        return all_rows_mapped;
    }
    //MAPPING SEVERAL ROWS TO OBJECT - END ###############################################

    //EXTRACT JSON DATA FROM JSON RESULT - START #########################################
    public String GetJsonDataResult(String json) throws JsonToObjectMapper_Exception {
        JsonStrConverter tmp = new JsonStrConverter(json);
        JSONObject json_data = GetJsonDataResult(tmp.convertStrToJson());
        tmp.setJson_obj(json_data);
        return tmp.convertJsonToStr();
    }
    public JSONObject GetJsonDataResult(JSONObject json) throws JsonToObjectMapper_Exception {
        JSONObject json_data;
        try {
            json_data = json.getJSONObject("DATA");
            json_data = json_data.getJSONObject("ResultObj"); //da verschachtelt
        } catch (JSONException e) {
            e.printStackTrace();
            throw new JsonToObjectMapper_Exception();
        }
        return json_data;
    }
    //EXTRACT JSON DATA FROM JSON RESULT - END ############################################


    public ArrayList<OBJ> CastArrayListObjToSpecObj(ArrayList list, OBJ class_type) {
        ArrayList<OBJ> new_list = new ArrayList<>();
        for (Object o : list) {
            new_list.add((OBJ) o);
        }
        return new_list;
    }
}
