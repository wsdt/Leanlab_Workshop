package fhku.leanlabapp.interfaces.database;


import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.net.ssl.HttpsURLConnection;

import fhku.leanlabapp.R;
import fhku.leanlabapp.interfaces.Dialog;
import jp.wasabeef.richeditor.Utils;

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
 use the built-in JsonStrConverter with:

 JsonStrConverter tmp = new JsonStrConverter(response); //response is a String
 tmp.convertStrToJson();
 JSONObject jsonobj = tmp.getJson_obj();

 You can also convert a JSON Object to a String by:
 String json_str = json_obj.toString();

 OR the built in method: (advantage of the above: numeral additional methods, your JSON
 JsonStrConverter tmp = new JsonStrConverter(response); //response is a JSONObject
 tmp.convertJsonToStr();
 String json_str = tmp.getJson_str();

 ++++++++++++++++++++++++++ FULL EXAMPLE +++++++++++++++++++++++++++++
 We want to receive all data from a product from the server. So we need to make a sql query.
 E.g. SELECT * FROM product;

 To execute the query, we need to pack the data we want to send in an array. This makes it also
 possible to send multiple data at once, so your app gets faster.

 String[] parameters = {"sql_statement=SELECT * FROM product"}; //example for sending ONLY a sql statement --> USED IN OUR EXAMPLE
 String[] parameters = {"vname=Kevin","nname="Riedl","Strasse","Roemerstrasse 3a"}; //example for sending multiple data
 String[] parameters = {"sql_statement=SELECT * FROM product","vname=Kevin","nname="Riedl","Strasse","Roemerstrasse 3a"}; //you can also combine them

 By implementing one of the lines above, we have a variable/array named 'parameters'.
 Now we need to send our data to our webservice. This works with the method below.

 String json_str = DbConnection.sendRequestForResult_ASYNC(parameters,"post"|"get",false|true));

 The first parameter of 'sendRequestForResult' must be an array.
 The second parameter let's you decide whether you want to send a POST or GET Request. If you send a invalid string, the method will automatically send a POST request.
 The third parameter let's you decide whether you want to send your data encrypted with SSL (HTTPS) or not (HTTP). True is for HTTPS, False for HTTP
 IMPORTANT: For HTTPS your server needs to support SSL, which might not be our case!


 So in our example we want to send the sql statement above (first example of parameter array) with an unencrypted connection as a post request:
 String json_str = DbConnection.sendRequestForResult_ASYNC(new String[] {"sql_statement=SELECT * FROM User;"},"post",false));

 Gratulation! Now we have a JSON string with all rows and cols of our sql query.
 E.g."{\"HEADER_RESP\":{\"Code\":\"$code\",\"Reason\":\"$reason\",\"Description\":\"The server responded with an error code of $code and provided following message for you: $description\"},\"DATA\":{\"ResultObj\":$data}}";
 $data might look like this: {"row1":{"col1":"val1","col2":"val2"}, "row2":{"col1":"val1","col2":"val2"}}

 But we have a small problem now: How can we use this data in our program? We must convert the String into something more useful, like a JSON_Object, an Array
 or into an instance of an object (e.g. instance of class 'product').

 So if we want to convert the Json String into a JSON_Object we would use:
 JsonStrConverter tmp = new JsonStrConverter(json_str); //response is a String
 tmp.convertStrToJson();
 JSONObject products = tmp.getJson_obj();

 But that is only one possible solution. You could also convert the JSON string into a java object or map it onto a class instance.
 E.g.:
 User tmp = new User("tmp");
 User.Loaded_Users = tmp.MapJsonRowsToObject(DbConnection.sendRequestForResult_ASYNC(new String[] {"sql_statement=SELECT * FROM User;"},"post",false));

 **********************************************************************************/


//https://stackoverflow.com/questions/1812891/java-escape-string-to-prevent-sql-injection

public class DbConnection {
    //IMPORTANT: DO NOT ADD HTTP OR HTTPS INTO THAT VARIABLE!
    //DEBUG_PURPOSES:
    //private static final String WEBSERVICE_PHP = "leanlab.web.fh-kufstein.ac.at/db_connection.php";
    private static final String WEBSERVICE_PHP = "192.168.12.115/LeanLabWorking/db_connection.php"; //NEEDED LOGIN INTO WLAN: FH_LEANLAB / PWD: L3anL4b#
    private static final int TIMEOUT_SOCKET = 4000; // in ms, if over then connection gets stopped

    //UserActivity and password will be added to the parameter list, webservice only accepts post/get requests if db_user table contains same data as here mentioned (security)
    private static final String USER = "default";
    private static final String PASSWORD = "dD56hjJ5dSWf";
    //Always use HTTPS requests!


    public static String encodeParameter(String parameter) { //parameter must not look like: "param1", "param1=val1&", ...
        String encodedParameter = "";

        if (parameter.contains("=") && (!parameter.contains("&"))) { //only encode valid parameters (param1=val1)
            String[] parameter_tmp = parameter.split("=", 2); // Index 0 = Name of parameter / Index 1 = Value of Parameter --> ein zweites = wird encoded
            try {
                // = bei attributzuweisung darf nicht encoded werden
                encodedParameter = parameter_tmp[0] + "=" + URLEncoder.encode(parameter_tmp[1], "UTF-8");
            } catch (UnsupportedEncodingException e) {
                // Log.e("encodeParameter", "UTF-8 encoding unknown!");
                e.printStackTrace();
            }
        } else {
            Log.e("encodeParameter", "Parameter will be ignored: " + parameter);
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
                    encodedParameters += "Username=" + USER + "&Password=" + PASSWORD + "&";
                }
                encodedParameters += parameter; //Only add parameter if not empty (if encoded right and not empty)
            }
        }
        return encodedParameters; //return parameter1=value1&parameter2=value2 ...
    }


    public static String sendRequestForResult_ASYNC(final String[] PARAMETERS, final String METHOD, final boolean useHTTPS, @NonNull final Context CONTEXT) throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                return DbConnection.sendRequestForResult(encodeParameters(PARAMETERS), METHOD, useHTTPS, CONTEXT);
            }
        };

        Future<String> future = executor.submit(callable);
        //future.get() returns JSON or raises an exception if thread dies, so safer
        executor.shutdown();

        String result = null;
        try {
            result = future.get(TIMEOUT_SOCKET, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            Dialog noConnection = Dialog.createDialog(CONTEXT, "Connection failed", "Please connect to the WiFi 'FH_LEANLAB' to access this application.\n\nPlease try to reconnect with your WLAN.", R.drawable.fh_kufstein_logo_transparent);
            noConnection.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Log.i("sendRequestFR_Async", "Timeout Dialog was confirmed with OK.");
                    android.os.Process.killProcess(android.os.Process.myPid());
                }
            });
            noConnection.show();
            Log.e("sendRequestFR_Async", "Future could not be retrieved.");
            e.printStackTrace();
        }
        return result;
    }


    //IMPORTANT: Variable 'parameters' must be sent to encodeParameters() before!
    private static String sendRequestForResult(final String parameters, String method, boolean useHTTPS, @NonNull Context context) {
        method = (!method.equals("POST") && !method.equals("GET")) ? "POST" : method; // wenn method falsch übergeben, dann mach POST

        URL url = null;
        String response = null;

        if (!useHTTPS) {
            Log.w("sendRequestForResult", "Used HTTP (not secure). Please use HTTPS instead.");
            HttpURLConnection connection;
            OutputStreamWriter request = null;

            try {
                url = new URL("http://" + WEBSERVICE_PHP);
                connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(TIMEOUT_SOCKET); //timeout when connection is stopped
                connection.setDoOutput(method.equals("POST")); //if post then do output, if get then not
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

                Log.i("sendRequest_HTTP", "Response-Code: " + connection.getResponseCode());
                Log.i("sendRequest_HTTP", "Response-Message: " + connection.getResponseMessage());
                Log.i("sendRequest_HTTP", "Response-Content: " + response);

            } catch (SocketTimeoutException e) {
                //If exception then show pop up always on active/foreground activity
                /*try {
                    Looper.prepare(); //without that an exception will be raised
                    Dialog dialog = (Dialog.createDialog(context,"Connection failed","Please connect to the WiFi 'FH_LEANLAB' to access this application.", R.drawable.fh_kufstein_logofh));
                    dialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Log.i("SocketTimeoutException","Timeout Dialog was confirmed with OK.");
                        }
                    });
                    dialog.show();
                    Log.d("SocketTimeoutException","Tried to show dialog.");

                } catch (NullPointerException f) {
                    Log.e("SocketTimeoutException","Context is null! Please call this function only with a valid Activity context!");
                    f.printStackTrace();
                }*/

                //If timeout then show message
                Log.e("sendRequest_HTTP", "Could not establish connection with database. Timeout of " + TIMEOUT_SOCKET + " ms exceeded.");
                e.printStackTrace();
            } catch (IOException e) {
                Log.e("sendRequest_HTTP", "Could not send parameters to webservice!");
                e.printStackTrace();
            }
        } else {
            //USE HTTPS
            Log.e("Certificate", "WARNING: HTTPS is implemented, but will NOT work, because a SSL certificate is missing!");

            try {
                url = new URL("https://" + WEBSERVICE_PHP);
                HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
                connection.setRequestMethod(method);
                connection.setConnectTimeout(TIMEOUT_SOCKET); //set timeout
                connection.setRequestProperty("Content-length", String.valueOf(parameters.length()));
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                connection.setRequestProperty("UserActivity-Agent", "Mozilla/4.0 (compatible; MSIE 5.0;Windows98;DigExt)");
                connection.setDoOutput(method.equals("POST")); //if post then do output, if get then not
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

                Log.i("sendRequest_HTTPS", "Response-Code: " + connection.getResponseCode());
                Log.i("sendRequest_HTTPS", "Response-Message: " + connection.getResponseMessage());
                Log.i("sendRequest_HTTPS", "Response-Content: " + response);

            } catch (SocketTimeoutException e) {
                //If timeout then show message
                Log.e("sendRequest_HTTPS", "Could not establish connection with database. Timeout of " + TIMEOUT_SOCKET + " ms exceeded.");
                e.printStackTrace();
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
        if (str == null) {
            return null;
        }
        return str.replace("'", "''");
    }

    public static void loadVideo(VideoView video, String link) {
        if (doesVideoExist(link)) {
            try {
                MediaController mediaController = new MediaController(video.getContext());
                mediaController.setAnchorView(video);
                video.setMediaController(mediaController);
                Uri videolink = Uri.parse("http://192.168.12.115/LeanLabWorking/vid/" + link);
                video.setVideoURI(videolink);
                video.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Log.e("loadVideo","Video not found: http://192.168.12.115/LeanLabWorking/vid/"+link);
            video.setVisibility(View.GONE);
        }
    }

    private static boolean doesVideoExist(final String link) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Callable<Boolean> callable = new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                try {
                    HttpURLConnection.setFollowRedirects(false);
                    // note : you may also need
                    //        HttpURLConnection.setInstanceFollowRedirects(false)
                    HttpURLConnection con =
                            (HttpURLConnection) new URL("http://192.168.12.115/LeanLabWorking/vid/"+link).openConnection();
                    con.setRequestMethod("HEAD");
                    Log.d("doesVideoExist", "Status-Code: "+con.getResponseCode());
                    return (con.getResponseCode() == HttpURLConnection.HTTP_PARTIAL) || (con.getResponseCode() == HttpsURLConnection.HTTP_OK);
                }
                catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }
        };
        Boolean future = null;
        try {
            future = executorService.submit(callable).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        executorService.shutdown();
        Log.e("doesVideoExist","BOOLEAN: "+((future == null) ? "null" : future.toString()));
        return future;
    }

}
