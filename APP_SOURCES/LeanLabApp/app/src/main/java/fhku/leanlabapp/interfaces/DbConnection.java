package fhku.leanlabapp.interfaces;

import android.util.Log;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import javax.net.ssl.HttpsURLConnection;

/*********************************************************************************
    HOW-TO-USE:
        ONLY SEND REQUESTS LIKE THIS: (parameters = ARRAY)
            String json_str = sendRequestForResult(encodeParameters(parameters), post|get, true|false);

            parameters[] has to look like this:
                parameters[0] = "name1=value1"
                parameters[1] = "name2=value2" ...


        To send a sql-statement add a parameter named like this:
                parameters[21] = "sql_statement=SELECT * FROM TABLE;"

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

**********************************************************************************/


//https://stackoverflow.com/questions/1812891/java-escape-string-to-prevent-sql-injection

public class DbConnection {
    //IMPORTANT: DO NOT ADD HTTP OR HTTPS INTO THAT VARIABLE!
    private static final String WEBSERVICE_PHP = "localhost/PP_Webservice/db_connection.php";

    //User and password will be added to the parameter list, webservice only accepts post/get requests if db_user table contains same data as here mentioned (security)
    private static final String USER = "default";
    private static final String PASSWORD = "dD56hjJ5dSWf";
    //Always use HTTPS requests!


    public static String encodeParameter(String parameter) { //parameter must not look like: "param1", "param1=val1&", ...
        String encodedParameter = "";

        if (parameter.contains("=") && (!parameter.contains("&"))) { //only encode valid parameters (param1=val1)
            try {
                encodedParameter = URLEncoder.encode(parameter, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                Log.e("encodeParameter", "UTF-8 encoding unknown!");
            }
        }
        //Parameter will NOT be added to parameter list, if parameter is not encoded!
        return encodedParameter;
    }

    public static String encodeParameters(String[] parameters) {
        int counter = 0;
        String encodedParameters = "";

        for (String parameter : parameters) {
            parameter = encodeParameter(parameter);

            if (!(parameter.equals("") || parameter.isEmpty())) {
                if ((counter++) != 0) {
                    encodedParameters += "&";
                } else {
                    encodedParameters += "Username="+USER+"&Password="+PASSWORD+"&";
                }
                encodedParameters += parameter; //Only add parameter if not empty (if encoded right and not empty)
            }
        }
        return encodedParameters; //return parameter1=value1&parameter2=value2 ...
    }


    //IMPORTANT: Variable 'parameters' must be sent to encodeParameters() before!
    public static String sendRequestForResult(final String parameters, String method, boolean useHTTPS) {
        method = (!method.equals("POST") && !method.equals("GET")) ? "POST" : method; // wenn method falsch übergeben, dann mach POST

        URL url = null;
        String response = null;

        if (!useHTTPS) {
            Log.w("sendRequestForResult","Used HTTP (not secure). Please use HTTPS instead.");
            HttpURLConnection connection;
            OutputStreamWriter request = null;

            try {
                url = new URL("http://"+WEBSERVICE_PHP);
                connection = (HttpURLConnection) url.openConnection();
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                connection.setRequestMethod(method); //POST or GET

                request = new OutputStreamWriter(connection.getOutputStream());
                request.write(parameters);
                request.flush();
                request.close();
                String line = "";
                InputStreamReader isr = new InputStreamReader(connection.getInputStream());
                BufferedReader reader = new BufferedReader(isr);

                //Maybe instead of string into array or json
                StringBuilder sb = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                //Response from server is saved here as String
                response = sb.toString();
                //Log.e("RESPONSE",response);
                //System.out.println(response);

                Log.i("sendRequest_HTTP","Response-Code: " + connection.getResponseCode());
                Log.i("sendRequest_HTTP","Response-Message: " + connection.getResponseMessage());
                Log.i("sendRequest_HTTP","Response-Content: "+response);

            } catch (IOException e) {
                Log.e("sendRequest_HTTP", "Could not send parameters to webservice!");
                e.printStackTrace();
            }
        } else {
            //USE HTTPS
            //TODO: Untested

            try {
                url = new URL("https://" + WEBSERVICE_PHP);
                HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
                connection.setRequestMethod(method);

                connection.setRequestProperty("Content-length", String.valueOf(parameters.length()));
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0;Windows98;DigExt)");
                connection.setDoOutput(true);
                connection.setDoInput(true);

                DataOutputStream output = new DataOutputStream(connection.getOutputStream());

                output.writeBytes(parameters);

                output.close();

                DataInputStream input = new DataInputStream(connection.getInputStream());

                StringBuilder sb = new StringBuilder();
                for (int c = input.read(); c != -1; c = input.read())
                    //System.out.print((char) c);
                     sb.append(c); //wrschl. wird jedes Zeichen einzeln hierhergesendet. Deshalb \n schwierig
                input.close();

                response = sb.toString();

                Log.i("sendRequest_HTTPS","Response-Code: " + connection.getResponseCode());
                Log.i("sendRequest_HTTPS","Response-Message: " + connection.getResponseMessage());
                Log.i("sendRequest_HTTPS","Response-Content: "+response);

            } catch (IOException e) {
                Log.e("sendRequest_HTTPS", "Could not send parameters to webservice!");
                e.printStackTrace();
            }
        }
    return response;
    }

    //From: https://commons.apache.org/proper/commons-lang/javadocs/api-2.6/src-html/org/apache/commons/lang/StringEscapeUtils.html#line.692
    public static String escapeSql(String str) {
        //Do not put here a sql string (unless you do not want it)
        if (str==null) {
            return null;
        }
        return str.replace("'","''");
    }

}
