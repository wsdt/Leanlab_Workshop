package fhku.leanlabapp.interfaces;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;

import java.io.IOException;


public class Mapper extends ObjectMapper {
    /* Maps e.g. a Mysql object/JSON object/String into a Java class or other types. */

    public Object JsonObjtoObj(JSONObject jsonObj, Object obj) throws IOException {
        return this.JsonStrToObj(this.JsonObjToJsonStr(jsonObj),obj);
    }

    public JSONObject ObjToJsonObj(Object obj) throws JsonProcessingException {
        return (new JsonStrConverter(this.ObjToJsonStr(obj))).convertStrToJson();
    }

    public Object JsonStrToObj(String jsonStr, Object obj) throws IOException {
        return this.readValue(jsonStr, obj.getClass());
    }

    public String ObjToJsonStr(Object obj) throws JsonProcessingException {
        return this.writeValueAsString(obj);
    }

    public String JsonObjToJsonStr(JSONObject jsonObj) { //Dummy Method, if someone wants to convert a String to Json with the Mapper
        return (new JsonStrConverter(jsonObj)).convertJsonToStr();
    }

    public JSONObject JsonStrToJsonObj(String jsonStr) { //Dummy Method, if someone wants to convert a JSON to String with the Mapper
        return (new JsonStrConverter(jsonStr)).convertStrToJson();
    }
}
