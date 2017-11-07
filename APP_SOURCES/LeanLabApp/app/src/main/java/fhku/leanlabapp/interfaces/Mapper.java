package fhku.leanlabapp.interfaces;

import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;



public class Mapper extends ObjectMapper {
    /* Maps e.g. a Mysql object/JSON object/String into a Java class or other types. */
    @JsonIgnoreType
    private static class IgnoreMe {  };

    public Module getIgnoreJacksonModule() {
        return new SimpleModule().setMixInAnnotation(ObjectMapper.class, IgnoreMe.class);
    }


    // Json String <==> String Array ------------------------------------------
    public String[] JsonStrToStringArray(String jsonStr) throws IOException {
        //TODO: Might not work
        return this.readValue(jsonStr,String[].class);
        /*JSONObject tmp = this.JsonStrToJsonObj(jsonStr);
        JSONArray jsonArray = jsnobject.getJSONArray("locations");
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject explrObject = jsonArray.getJSONObject(i);
        }*/
    }
    public String JsonStringArrayToString(String[] jsonArr) throws JsonProcessingException {
        //TODO: Might not work
        return this.writeValueAsString(jsonArr);
    }

    // Json Obj <==> Java Obj ------------------------------------------
    public Object JsonObjtoObj(JSONObject jsonObj, Object obj) throws IOException {
        //TODO: Might not work
        return this.JsonStrToObj(this.JsonObjToJsonStr(jsonObj),obj);
    }
    public JSONObject ObjToJsonObj(Object obj) throws JsonProcessingException {
        //TODO: Might not work
        return (new JsonStrConverter(this.ObjToJsonStr(obj))).convertStrToJson();
    }

    // Json String <==> Java Obj ------------------------------------------
    public Object JsonStrToObj(String jsonStr, Object obj) throws IOException {
        //TODO: Might not work
        return this.readValue(jsonStr, obj.getClass());
    }
    public String ObjToJsonStr(Object obj) throws JsonProcessingException {
        //TODO: Might not work
        return this.writeValueAsString(obj);
    }

    // Json Obj <==> Java String ------------------------------------------
    public String JsonObjToJsonStr(JSONObject jsonObj) { //Dummy Method, if someone wants to convert a String to Json with the Mapper
        return (new JsonStrConverter(jsonObj)).convertJsonToStr();
    }
    public JSONObject JsonStrToJsonObj(String jsonStr) { //Dummy Method, if someone wants to convert a JSON to String with the Mapper
        return (new JsonStrConverter(jsonStr)).convertStrToJson();
    }

}
