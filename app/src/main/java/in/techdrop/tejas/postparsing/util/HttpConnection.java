package in.techdrop.tejas.postparsing.util;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Carl_johnson on 21-09-2017.
 */

public class HttpConnection {

  private static final String TAG = HttpConnection.class.getSimpleName();
  //Method to send httpPostRequest
  //This method is taking two arguments
  //First argument is the URL of the script to which we will send the request
  //Other is an HashMap with name value pairs containing the data to be send with the request
  public String sendPostRequest(String requestURL, HashMap<String, String> postDataParams) {
    //Creating a URL
    URL url;

    System.out.println(postDataParams.keySet() + "\n" + postDataParams.values());
    //StringBuilder object to store the message retrieved from the server
    StringBuilder sb = new StringBuilder();
    try {
      //Initializing Url
      url = new URL(requestURL);

      //Creating an httmlurl connection
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();

      //Configuring connection properties
      conn.setReadTimeout(15000);
      conn.setConnectTimeout(15000);
      conn.setRequestMethod("POST");
      conn.setDoInput(true);
      conn.setDoOutput(true);
      System.out.println("after Connection");
      //Creating an output stream
      OutputStream os = conn.getOutputStream();

      System.out.println("os" + os);
      //Writing parameters to the request
      //We are using a method getPostDataString which is defined below
      BufferedWriter writer = new BufferedWriter(
        new OutputStreamWriter(os, "UTF-8"));
      writer.write(getPostDataString(postDataParams));

      System.out.println("writer" + writer.toString());
      System.out.println("os 2" + os.toString());

      writer.flush();
      writer.close();
      os.close();

      System.out.println("before ResponceCode");
      int responseCode = conn.getResponseCode();
      System.out.println("after ResponceCode");

      if (responseCode == HttpsURLConnection.HTTP_OK) {

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        sb = new StringBuilder();
        String response;

        //Reading server response
        while ((response = br.readLine()) != null){
          sb.append(response);
          System.out.println("after Check responce" + response);
        }
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
    return sb.toString();
  }

  public String sendGetRequest(String requestURL){
        /*StringBuilder sb =new StringBuilder();
        try {
            URL url = new URL(requestURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String s;
            while((s=bufferedReader.readLine())!=null){
                sb.append(s+"\n");
            }
        }catch(Exception e){
        }
        return sb.toString();*/

    String response = null;
    try {
      URL url = new URL(requestURL);
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      conn.setRequestMethod("GET");
      // read the response
      InputStream in = new BufferedInputStream(conn.getInputStream());
      response = convertStreamToString(in);
    } catch (MalformedURLException e) {
      Log.e(TAG, "MalformedURLException: " + e.getMessage());
    } catch (ProtocolException e) {
      Log.e(TAG, "ProtocolException: " + e.getMessage());
    } catch (IOException e) {
      Log.e(TAG, "IOException: " + e.getMessage());
    } catch (Exception e) {
      Log.e(TAG, "Exception: " + e.getMessage());
    }
    return response;
  }

  private String convertStreamToString(InputStream is) {
    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
    StringBuilder sb = new StringBuilder();

    String line;
    try {
      while ((line = reader.readLine()) != null) {
        sb.append(line).append('\n');
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        is.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    return sb.toString();
  }

  public String sendGetRequestParam(String requestURL, String id){
    StringBuilder sb =new StringBuilder();
    try {
      URL url = new URL(requestURL+id);
      HttpURLConnection con = (HttpURLConnection) url.openConnection();
      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

      String s;
      while((s=bufferedReader.readLine())!=null){
        sb.append(s+"\n");
      }
    }catch(Exception e){
    }
    return sb.toString();
  }


  private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
    StringBuilder result = new StringBuilder();
    boolean first = true;
    for (Map.Entry<String, String> entry : params.entrySet()) {
      if (first)
        first = false;
      else
        result.append("&");

      result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
      result.append("=");
      result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
    }
    return result.toString();
  }

}


