package fhku.leanlabapp.interfaces;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import fhku.leanlabapp.classes.exceptions.JsonToObjectMapper_Exception;

/**
 * Created by kevin on 20.11.2017.
 */

public abstract class Mapper {
    // MAPPING METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    public abstract Object MapJsonToObject(String json) throws JsonToObjectMapper_Exception;
    public abstract Object MapJsonToObject(JSONObject json) throws JsonToObjectMapper_Exception;

    public ArrayList<Object> MapJsonRowsToObject(String json) throws JsonToObjectMapper_Exception {
        JsonStrConverter tmp = new JsonStrConverter(json);
        return MapJsonRowsToObject(tmp.convertStrToJson());
    }

    public ArrayList<Object> MapJsonRowsToObject(JSONObject json) throws JsonToObjectMapper_Exception {
        JSONObject json_data = GetJsonDataResult(json);
        Iterator<?> keys = json_data.keys();
        ArrayList<Object> all_rows_mapped = new ArrayList<>();

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
    public <OBJ> ArrayList<OBJ> CastArrayListObjToSpecObj(ArrayList list, OBJ class_type) {
        ArrayList<OBJ> new_list = new ArrayList<>();
        for (Object o : list) {
            new_list.add((OBJ) o);
        }
        return new_list;
    }
}
