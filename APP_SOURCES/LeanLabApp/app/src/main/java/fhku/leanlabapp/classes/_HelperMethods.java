package fhku.leanlabapp.classes;


import org.json.JSONObject;
import fhku.leanlabapp.interfaces.JsonStrConverter;

/**
 * Created by kevin on 14.11.2017.
 */

public class _HelperMethods {
    public static String escapeHTML(String html) {
        html = html.replaceAll("&","&amp;"); //& must be the first, because all escaped characters contain &
        html = html.replaceAll("\"","&quot;");
        html = html.replaceAll("<","&lt;");
        html = html.replaceAll(">", "&gt;");

        return html;
    }

    public static <OBJ> OBJ MapJsonToObject(String json, OBJ obj) {
        JsonStrConverter tmp = new JsonStrConverter(json);
        tmp.convertStrToJson();
        return MapJsonToObject(tmp.getJson_obj(),obj);
    }

    public static <OBJ> OBJ MapJsonToObject(JSONObject json, OBJ obj) {
        
        return obj;
    }

}
