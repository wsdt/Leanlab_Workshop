package fhku.leanlabapp.interfaces;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/*
We get a JSON-String as return value, so if you need a JSONObject itself, then
                use the built-in JsonConverter with:

                    JsonConverter tmp = new JsonConverter(response); //response is a String
                    tmp.convertStrToJson();
                    JSONObject jsonobj = tmp.getJson_obj();

                You can also convert a JSON Object to a String by:
                    String json_str = json_obj.toString();

                    OR the built in method: (advantage of the above: numeral additional methods, your JSON
                    JsonConverter tmp = new JsonConverter(response); //response is a JSONObject
                    tmp.convertJsonToStr();
                    String json_str = tmp.getJson_str();
*/

public class JsonConverter {
    private String json_str;
    private JSONObject json_obj;

    public JsonConverter(String json_str) {
        this.setJson_str(json_str);
    }

    public JsonConverter(JSONObject json_obj) {
        this.setJson_obj(json_obj);
    }

    public void convertStrToJson() {
        try {
            this.setJson_obj(new JSONObject(this.getJson_str().replace("\"","\\\"")));
        } catch(JSONException e) {
            Log.e("convertStrToJson","Could not convert String to Json object. \nString: "+this.getJson_str()+"\nEscaped String: "+this.getJson_str().replace("\"","\\\""));
        }
    }

    public void convertJsonToStr() {
        setJson_str(this.getJson_obj().toString());
    }


    public void setJson_obj(JSONObject json_obj) {
        this.json_obj = json_obj;
    }

    public JSONObject getJson_obj() {
        return this.json_obj;
    }

    public void setJson_str(String json_str) {
        this.json_str = json_str;
    }

    public String getJson_str() {
        return this.json_str;
    }
}
