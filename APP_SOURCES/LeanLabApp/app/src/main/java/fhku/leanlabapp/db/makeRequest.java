package fhkufstein.ac.at.leanlab_app;


import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;


public class makeRequest {
    private static final String WEBSERVICE_PHP = "http://localhost/PP_Webservice/db_connection.php";

    /*
    ############## FOR TESTING LOCALLY as PC #################
    public static void main(String[] args) {
        sendRequest("sql_statement=SELECT * FROM Product;","POST"); //this form is obligatory (name=value), multiple values are possible --> (name=value&name2=value2)
    }*/

    public static void sendRequest(final String parameters,String method,boolean useHTTPS) {
        method = (!method.equals("POST") && !method.equals("GET")) ? "POST" : method; // wenn method falsch übergeben, dann mach POST

        if (!useHTTPS) {
            HttpURLConnection connection;
            OutputStreamWriter request = null;

            URL url = null;
            String response = null;

            //String parameters = "sql_statement="+sql; //"username="+var1+"&pwd="+var2; //when multiple parameters then concat

            try {
                url = new URL(WEBSERVICE_PHP);
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
                System.out.println(response);

            } catch (IOException e) {
                Log.e("POST_ERROR", "Could not send parameters to webservice!");
                e.printStackTrace();

            }
        } else {
            //USE HTTPS
        }
    }
}
