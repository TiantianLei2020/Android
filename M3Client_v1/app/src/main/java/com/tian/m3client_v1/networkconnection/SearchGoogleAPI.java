package com.tian.m3client_v1.networkconnection;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SearchGoogleAPI {
    private static final String API_KEY = "AIzaSyDl7JbOY16OzBPMb2KjA3utIL13C4y3DNU";
    private static final String SEARCH_ID_cx = "017493137541256431712:lcsv2umwrlv";
    private static final String YOUTUBE_cx = "017493137541256431712:2n43ry5m544";
    private static final String GOOGLE_SEARCH = "https://www.googleapis.com/customsearch/v1?key=";
    private static final String GEOCODEURL = "https://maps.googleapis.com/maps/api/geocode/json?address=";
    private OkHttpClient client=null;
    public SearchGoogleAPI(){
        client = new OkHttpClient();
    }


    public static String search(String keyword, String[] params, String[] values) {
        keyword = keyword.replace(" ","+");
        URL url = null;
        HttpURLConnection connection = null;
        String textResult = "";
        String query_parameter="";

        if (params!=null && values!=null){
            for (int i =0; i < params.length; i ++){
                query_parameter += "&";
                query_parameter += params[i];
                query_parameter += "=";
                query_parameter += values[i];
            }
        }

        try {
            url = new URL(GOOGLE_SEARCH + API_KEY+ "&cx="
                    + SEARCH_ID_cx + "&q="+ keyword + query_parameter);

            connection = (HttpURLConnection)url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            Scanner scanner = new Scanner(connection.getInputStream());
            while (scanner.hasNextLine()) {
                textResult += scanner.nextLine();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            connection.disconnect();
        }
        //System.out.println(textResult);
        return textResult;
    }

    public static List<HashMap<String, String>> getInfo(String result){
//        result.replace("\n","");
        String tmp = null;
        String imageSrc = null;
        String title = null;
        String year = null;
        String org = null;
        List<HashMap<String, String>> searchArrayList = null;
        HashMap<String, String> map = null;
        String[] colHead = new String[]{"imageSrc","title","year"};
        // {} array    [] object
        try{
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("items");
            int length = jsonArray.length();
            searchArrayList = new ArrayList<HashMap<String, String>>();
            if(jsonArray != null && jsonArray.length() > 0) {
                for (int i = 0; i < 5; i++) {
                    map = new HashMap<String, String>();
                    String titleTemp = jsonArray.getJSONObject(i).getString("title");
                    title = titleTemp.replace(" - IMDb","");
                    year = titleTemp.substring(title.indexOf("(") + 1, title.indexOf(")"));
                    JSONObject jsonObject2 = jsonArray.getJSONObject(i).getJSONObject("pagemap");
                    imageSrc = jsonObject2.getJSONArray("cse_image").
                            getJSONObject(0).getString("src");
                    org = jsonObject2.getJSONArray("metatags").
                            getJSONObject(0).getString("og:url");
                    map.put(colHead[0], imageSrc);
                    map.put(colHead[1], title);
                    map.put(colHead[2], year);
                    map.put("orgUrl",org);
                    searchArrayList.add(map);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            map = new HashMap<String, String>();
            map.put("errInfo","NO INFO FOUND");
            searchArrayList.add(map);
        }
        return searchArrayList;
    }

    public static String searchOMDB(String imdbID) {
        String SEARCH_URL = "http://www.omdbapi.com/?i=" + imdbID + "&apikey=85fcf9f";

        StringBuffer response = new StringBuffer();
        String result = "";
        try {
            URL url = new URL(SEARCH_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept","*/*");
            connection.setRequestProperty("Content-Type","application/json; charset=UTF-8");
            InputStream stream;
            int code = connection.getResponseCode();
            if (code == 200) {
                stream = connection.getInputStream();
            } else {
                stream = connection.getErrorStream();
            }
            InputStreamReader reader = new InputStreamReader(stream);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line;
            while ((line = bufferedReader.readLine())!= null) {
                response.append(line);
            }
            bufferedReader.close();
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response.toString();
    }

    public static String[] searchOMDBImage(String movieName) {
        String SEARCH_URL = "http://www.omdbapi.com/?t=" + movieName + "&apikey=85fcf9f";

        StringBuffer response = new StringBuffer();
        String result[] = new String[2];
        try {
            URL url = new URL(SEARCH_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept","*/*");
            connection.setRequestProperty("Content-Type","application/json; charset=UTF-8");
            InputStream stream;
            int code = connection.getResponseCode();
            if (code == 200) {
                stream = connection.getInputStream();
            } else {
                stream = connection.getErrorStream();
            }
            InputStreamReader reader = new InputStreamReader(stream);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line;
            while ((line = bufferedReader.readLine())!= null) {
                response.append(line);
            }
            bufferedReader.close();
            connection.disconnect();

            JSONObject jsonObject = new JSONObject(response.toString());
            result[0] = jsonObject.getString("Poster");
            result[1] = jsonObject.getString("imdbID");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String searchOMDBByNameAndYear(String movieName,String year) {
        String SEARCH_URL = "http://www.omdbapi.com/?t=" + movieName + "&y=" + year + "&apikey=85fcf9f";

        StringBuffer response = new StringBuffer();
        String result = "";
        try {
            URL url = new URL(SEARCH_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept","*/*");
            connection.setRequestProperty("Content-Type","application/json; charset=UTF-8");
            InputStream stream;
            int code = connection.getResponseCode();
            if (code == 200) {
                stream = connection.getInputStream();
            } else {
                stream = connection.getErrorStream();
            }
            InputStreamReader reader = new InputStreamReader(stream);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line;
            while ((line = bufferedReader.readLine())!= null) {
                response.append(line);
            }
            bufferedReader.close();
            connection.disconnect();

            JSONObject jsonObject = new JSONObject(response.toString());
            result = jsonObject.getString("imdbID");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static HashMap<String, String> getDetials(String result){
//        result.replace("\n","");
        String imageSrc = null;
        String title = null;
        String year = null;
        String org = null;
        List<HashMap<String, String>> searchArrayList = null;
        HashMap<String, String> map = null;
        // {} array    [] object
        try{
            JSONObject jsonObject = new JSONObject(result);
            map = new HashMap<String, String>();
            String genre = jsonObject.getString("Genre");
            String cast = jsonObject.getString("Actors");
            String releaseDate = jsonObject.getString("Released");
            String country = jsonObject.getString("Country");
            String director = jsonObject.getString("Director");
            String plot = jsonObject.getString("Plot");
            String score = jsonObject.getString("imdbRating");

            map.put("genre", genre);
            map.put("cast", cast);
            map.put("releaseDate", releaseDate);
            map.put("country",country);
            map.put("director",director);
            map.put("plot",plot);
            map.put("score",score);

        }catch (Exception e){
            e.printStackTrace();
            map = new HashMap<String, String>();
            map.put("errInfo","NO INFO FOUND");
        }
        return map;
    }

    public static String searchVedio(String keyword, String[] params, String[] values) {
        keyword = keyword.replace(" ","+");
        URL url = null;
        HttpURLConnection connection = null;
        String textResult = "";
        String query_parameter="";

        if (params!=null && values!=null){
            for (int i =0; i < params.length; i ++){
                query_parameter += "&";
                query_parameter += params[i];
                query_parameter += "=";
                query_parameter += values[i];
            }
        }

        try {
            url = new URL(GOOGLE_SEARCH + API_KEY+ "&cx="
                    + YOUTUBE_cx + "&q="+ keyword + query_parameter);

            connection = (HttpURLConnection)url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");

            BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
            rd.close();
            textResult = sb.toString();

        }catch (Exception e){
            e.printStackTrace();
        }finally{
            connection.disconnect();
        }
        return textResult;
    }

    public String[] findLatLngByAddress(String address) {

        String[] latlng = new String[2];

        String methodPath = GEOCODEURL + address + "&key=" + API_KEY;
        Request.Builder builder = new Request.Builder();
        builder.url(methodPath);
        Request request = builder.build();
        String results = "";
        try {
            Response response = client.newCall(request).execute();
            results = response.body().string();
            JsonArray ja = new JsonParser().parse(results).getAsJsonObject().get("results").getAsJsonArray();
            JsonObject geometry = ja.get(0).getAsJsonObject().get("geometry").getAsJsonObject();
            JsonObject location = geometry.get("location").getAsJsonObject();
            latlng[0] = location.get("lat").getAsString();
            latlng[1] = location.get("lng").getAsString();

        } catch (Exception e) {
            e.printStackTrace();
            latlng = null;
        }

        return latlng;
    }


}
