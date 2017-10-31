package fhku.leanlabapp.interfaces;

import org.json.JSONObject;


public interface Mapper {
    /* Maps e.g. a Mysql object/JSON object/String into a Java class or other types. */

    Object mapJSONtoJavaClass(JSONObject jsonObj);
    JSONObject mapJavaClasstoJSON(Object genObj);
    Object mapJSONStrtoJavaClass(String jsonStr);
    String mapJavaClasstoJSONStr(Object genObj);
}
