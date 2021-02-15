package com.example.android.quakereport;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static android.os.Build.VERSION_CODES.M;
import static android.provider.ContactsContract.CommonDataKinds.Website.URL;
import static com.example.android.quakereport.EarthquakeActivity.LOG_TAG;

public final class QueryUtils {
    private QueryUtils() {
    }

    public static List<Earthquake> extractFeatureFromJson(String earthquakeJSON) {

        if(TextUtils.isEmpty(earthquakeJSON)){
            return null;
        }

        List<Earthquake> earthquakes = new ArrayList<>();
        try {

            JSONObject baseJsonResponse = new JSONObject(earthquakeJSON);
            JSONArray earthArray = baseJsonResponse.getJSONArray("features");

            for (int i = 0; i<earthArray.length(); i++){
               //find information
                JSONObject currentearthquake = earthArray.getJSONObject(i);
                JSONObject properties = currentearthquake.getJSONObject("properties");

                //Extract Information
                Double mag = properties.getDouble("mag");
                String location = properties.getString("place");
                long time =properties.getLong("time");
                String stringURL = properties.getString("url");

                //Add info to earthquake
                Earthquake earthquake = new Earthquake(mag,location,time, stringURL);
                earthquakes.add(earthquake);
            }

        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }
        return earthquakes;
    }

    private static URL createUrl(String stringUrl){
        URL url = null;
        try{
            url = new URL(stringUrl);
        }catch (MalformedURLException e){
            Log.e(LOG_TAG, "Problem building the URL",e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException{
        String jsonRepesponse = " ";
        if (url == null){
            return jsonRepesponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setReadTimeout(10000);
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonRepesponse = readFromStream(inputStream);
            }else{
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        }catch (IOException e){
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        }finally{
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonRepesponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();

    }

    public static List<Earthquake> fetchEarthquakeData(String requestUrl){

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        try{
            jsonResponse = makeHttpRequest(url);
        }catch (IOException e){
            Log.e(LOG_TAG, "Problem making the HTTp request.", e);
        }
        List<Earthquake> earthquakes = extractFeatureFromJson(jsonResponse);
        return earthquakes;
    }


}
