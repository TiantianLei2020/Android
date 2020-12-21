package com.tian.m3client_v1.networkconnection;


import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.google.gson.Gson;
import com.tian.m3client_v1.entity.Cinematable;
import com.tian.m3client_v1.entity.Credentialstable;
import com.tian.m3client_v1.entity.Memoirtable;
import com.tian.m3client_v1.entity.Usertable;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NetworkConnection {
    private OkHttpClient client=null;
    private String results;

    public NetworkConnection(){

        client = new OkHttpClient();
    }

    private static final String BASE_URL = "http://192.168.50.14:8080/M3v2/webresources/";
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public String getByUsername (String username) {
        final String methodPath = "m3.credentialstable/findByUsername/" + username;
        Request.Builder builder = new Request.Builder();
        builder.url(BASE_URL + methodPath);
        Request request = builder.build();

        try {
            Response response = client.newCall(request).execute();
            results = response.body().string();
        }catch (Exception e){
            e.printStackTrace();
        }
        return results;
    }

    public String getMemoirById (Integer userId) {

        String methodPath = "m3.memoirtable/findMemoirByUserId/" + userId;

        Request.Builder builder = new Request.Builder();
        builder.url(BASE_URL + methodPath);
        Request request = builder.build();

        try {
            Response response = client.newCall(request).execute();
            results = response.body().string();

        }catch (Exception e){
            e.printStackTrace();
        }
        return results;
    }

    public String addUser (String[] details) {
        Usertable user = new Usertable(null,details[5],details[6],
                details[7], details[8],details[9],details[10],details[11],Integer.parseInt(details[12]));

        Credentialstable cre = new Credentialstable(null, details[1],
                details[2],details[3], user);
        Gson gson = new Gson();
        String userJson = gson.toJson(cre);
        String strResponse = "";
        //this is for testing, you can check how the json looks like in Logcat
        Log.i("json", userJson);

        final String methodPath = "m3.credentialstable/";
        RequestBody body = RequestBody.create(userJson, JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + methodPath)
                .post(body)
                .build();
        try {
            Response response = client.newCall(request).execute();
            strResponse = response.body().string();
        }catch (Exception e){
            e.printStackTrace();
        }
        return strResponse;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<HashMap<String, String>> findMemoir (Integer id) {
        final String methodPath = "m3.memoirtable/Task6/" + id;
        Request.Builder builder = new Request.Builder();
        builder.url(BASE_URL + methodPath);
        Request request = builder.build();
        List<HashMap<String, String>> memoirArrayList = null;
        HashMap<String, String> map = null;
        String[] colHEAD = new String[]{"MovieName", "ReleaseDate", "MovieRating"};
        try {
            Response response = client.newCall(request).execute();
            results = response.body().string();
            JSONArray ja = new JSONArray(results);
            memoirArrayList = new ArrayList<HashMap<String, String>>();
            for (int i = 0; i < ja.length(); i++) {
                JSONObject jo = ja.getJSONObject(i);
                map = new HashMap<String, String>();
                map.put(colHEAD[0], jo.getString("MovieName"));
                String formedDate = jo.getString("MovieReleaseDate");
                map.put(colHEAD[1], formedDate);
                map.put(colHEAD[2], jo.getString("MovieRating"));
                memoirArrayList.add(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return memoirArrayList;
    }

    public String getByMonth (Integer userId, Integer year) {
        final String methodPath = "m3.memoirtable/Task2/" + userId + "/" + year;
        Request.Builder builder = new Request.Builder();
        builder.url(BASE_URL + methodPath);
        Request request = builder.build();

        try {
            Response response = client.newCall(request).execute();
            results = response.body().string();
            System.out.println(results);
        }catch (Exception e){
            e.printStackTrace();
        }
        return results;
    }

    public String getByScope (String userId, String startDate, String endDate) {
        final String methodPath = "m3.memoirtable/Task1/" + userId + "/" + startDate + "/" + endDate;
        Request.Builder builder = new Request.Builder();
        builder.url(BASE_URL + methodPath);
        Request request = builder.build();

        try {
            Response response = client.newCall(request).execute();
            results = response.body().string();
            System.out.println(results);
        }catch (Exception e){
            e.printStackTrace();
        }
        return results;
    }

    public String findCinema() {
        final String methodPath = "m3.cinematable";
        Request.Builder builder = new Request.Builder();
        builder.url(BASE_URL + methodPath);
        Request request = builder.build();
        try {
            Response response = client.newCall(request).execute();
            results = response.body().string();
        }catch (Exception e){
            e.printStackTrace();
        }
        return results;
    }

    public String addCinema (String[] details) {
        Cinematable cinema = new Cinematable(null,details[0],Integer.parseInt(details[1]));
        Gson gson = new Gson();
        String cinemaJson = gson.toJson(cinema);
        String strResponse = "";
        Log.i("json", cinemaJson);

        final String methodPath = "m3.cinematable/";
        RequestBody body = RequestBody.create(cinemaJson, JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + methodPath)
                .post(body)
                .build();
        try {
            Response response = client.newCall(request).execute();
            strResponse = response.body().string();
        }catch (Exception e){
            e.printStackTrace();
        }
        return strResponse;
    }

    public String addMemoir (String[] details) {
        Cinematable cinematable = new Cinematable();
        Memoirtable memoir = new Memoirtable();

        Usertable user = new Usertable();
        cinematable.setCinema_id(Integer.parseInt(details[7]));
        memoir.setMoviename(details[0]);
        memoir.setMoviereleasedate(details[1]);
        memoir.setWatchedDate(details[2]);
        memoir.setWatchedTime(details[3]);
        memoir.setComment(details[4]);
        memoir.setRating(Double.parseDouble(details[5]));
        user.setUserId(Integer.parseInt(details[6]));

        memoir.setUserId(user);
        memoir.setCinemaId(cinematable);
        Gson gson = new Gson();
        String memoirJson = gson.toJson(memoir);
        String strResponse = "";
        Log.i("json", memoirJson);

        final String methodPath = "m3.memoirtable/";
        RequestBody body = RequestBody.create(memoirJson, JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + methodPath)
                .post(body)
                .build();
        try {
            Response response = client.newCall(request).execute();
            strResponse = response.body().string();
        }catch (Exception e){
            e.printStackTrace();
        }
        return strResponse;
    }
}
