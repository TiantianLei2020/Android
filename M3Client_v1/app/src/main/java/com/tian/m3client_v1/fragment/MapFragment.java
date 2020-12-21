package com.tian.m3client_v1.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tian.m3client_v1.R;
import com.tian.m3client_v1.networkconnection.NetworkConnection;
import com.tian.m3client_v1.networkconnection.SearchGoogleAPI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MapFragment extends Fragment implements OnMapReadyCallback {
    private TextView textView;
    private GoogleMap mapToUser;

    private NetworkConnection networkConnection;
    private Integer loginUser = 2;
    private double latitude;
    private double longitude;
    Integer userId = 1;
    static String userAddress = null;

    public MapFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        networkConnection = new NetworkConnection();
        View view = inflater.inflate(R.layout.google_map_fragment, container, false);
        Bundle bundle = getArguments();
        if (bundle != null) {
            userId = bundle.getInt("userId",1);
            userAddress = bundle.getString("userAddress");
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapPage_googleMap_auto_generated);
        mapFragment.getMapAsync(this);
        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapToUser = googleMap;
        mapToUser.getUiSettings().setZoomControlsEnabled(true);

        new UserHomeMapMakerTask().execute();
        new findCinemaTask().execute();

    }

    private class UserHomeMapMakerTask extends AsyncTask<Void, Void, String[]> {

        @Override
        protected String[] doInBackground(Void... strings) {

            SearchGoogleAPI searchGoogleAPI = new SearchGoogleAPI();
            return searchGoogleAPI.findLatLngByAddress(userAddress);
        }

        @Override
        protected void onPostExecute(String[] result) {
            if (result == null) {
                Toast.makeText(getContext(), "Can not find your home address in map", Toast.LENGTH_SHORT).show();
            } else {
                LatLng homeLocation = new LatLng(Double.parseDouble(result[0]), Double.parseDouble(result[1]));
                float zoomLevel = 15.0F;
                mapToUser.addMarker(new MarkerOptions()
                        .position(homeLocation)
                        .title("My Home Location")
                        .icon(BitmapDescriptorFactory.defaultMarker(12)));
                mapToUser.moveCamera(CameraUpdateFactory.newLatLngZoom(homeLocation, zoomLevel));

            }

        }
    }

    private class findCinemaTask extends AsyncTask<Void, Void, ArrayList<String[]>> {

        @Override
        protected ArrayList<String[]> doInBackground(Void... strings) {
            ArrayList<String[]> cinemaLagLNTs = new ArrayList<>();
            String results = networkConnection.findCinema();
            JSONArray ja = null;
            try {
                ja = new JSONArray(results);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (ja != null) {
                for (int i = 0; i < ja.length(); i++) {
                    try {
                        JSONObject jo = ja.getJSONObject(i);
                        String cinemaName = jo.getString("cinemaname");
                        String cinemaPost = jo.getString("cinemaPostcode");
                        String cinameAddress = cinemaName.replace("+", " ") + "+" + cinemaPost + "+" + "Melbourne";
                        SearchGoogleAPI searchGoogleAPI = new SearchGoogleAPI();
                        String[] laglnt = searchGoogleAPI.findLatLngByAddress(cinameAddress);
                        if (laglnt == null) {

                        } else {
                            String[] cinemaInfo = new String[]{cinemaName, laglnt[0], laglnt[1]};
                            cinemaLagLNTs.add(cinemaInfo);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            return cinemaLagLNTs;
        }

        @Override
        protected void onPostExecute(ArrayList<String[]> cinemaLagLNTs) {
            if (cinemaLagLNTs.size()==0) {
                Toast.makeText(getContext(), "No cinema address found", Toast.LENGTH_SHORT).show();
            } else {
                for (String[] cinemaInfo : cinemaLagLNTs) {
                    LatLng cinemaLocation = new LatLng(Double.parseDouble(cinemaInfo[1]), Double.parseDouble(cinemaInfo[2]));
                    mapToUser.addMarker(new MarkerOptions()
                            .position(cinemaLocation)
                            .title(cinemaInfo[0])
                            .icon(BitmapDescriptorFactory.defaultMarker(220)));
                }
            }
        }
    }

}
