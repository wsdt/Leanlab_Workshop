package fhku.leanlabapp.classes;


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

}
