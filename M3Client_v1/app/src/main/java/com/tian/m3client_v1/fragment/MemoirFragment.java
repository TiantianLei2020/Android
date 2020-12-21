package com.tian.m3client_v1.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.tian.m3client_v1.MovieViewActivity;
import com.tian.m3client_v1.R;
import com.tian.m3client_v1.entity.ItemModel;
import com.tian.m3client_v1.networkconnection.NetworkConnection;
import com.tian.m3client_v1.networkconnection.SearchGoogleAPI;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MemoirFragment extends Fragment {
    private Spinner spinner;
    private ListView listView;
    private ImageView ivResult;
    List<ItemModel> itemModelList = new ArrayList<>();
    MemoirAdapter memoirAdapter;
    NetworkConnection networkConnection;
    private static Integer user_id;

    public MemoirFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.memoir_fragment, container, false);
        networkConnection = new NetworkConnection();

        final Bundle bundle = getArguments();
        if (bundle != null) {
            user_id = bundle.getInt("userId",1);
        }

        spinner = view.findViewById(R.id.sore);
        listView = view.findViewById(R.id.memoirListView);
        final Integer finalUserid = user_id;
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String results = "";

                results = networkConnection.getMemoirById(finalUserid);
                return results;
            }
            @Override
            protected void onPostExecute(String result) {
                display(result);
            }
        }.execute();
        return view;
    }

    private void display(String s){
        List<HashMap<String, String>> memoirArrayList = null;
        HashMap<String, String> map = null;
        Integer size = 1;
        memoirArrayList = new ArrayList<HashMap<String, String>>();
        try {
            JSONArray ja = new JSONArray(s);
            size = ja.length();
            for (int i = 0; i < ja.length(); i++) {
                JSONObject jo = ja.getJSONObject(i);
                map = new HashMap<String, String>();
                String movieName = null;
                movieName = jo.getString("moviename");
                String[] imgAndID = SearchGoogleAPI.searchOMDBImage(movieName);
                String imageSrc = imgAndID[0];
                String IMDB = imgAndID[1];
                map.put("movieName", movieName);
                map.put("releaseDate", jo.getString("moviereleasedate"));
                map.put("watchedDate", jo.getString("watchedDate"));
                map.put("cinemaPost", jo.getString("cinemapostcode"));
                map.put("comments", jo.getString("comment"));
                map.put("score", jo.getString("rating"));
                map.put("imageSrc", imageSrc);
                map.put("IMDBID",IMDB);
                memoirArrayList.add(map);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        ArrayList<String> movieNames = new ArrayList<String>();
        ArrayList<String> releaseDates = new ArrayList<String>();
        ArrayList<String> watchedDates = new ArrayList<String>();
        ArrayList<String> cinemaPosts = new ArrayList<String>();
        ArrayList<String> comments = new ArrayList<String>();
        ArrayList<String> scores = new ArrayList<String>();
        ArrayList<String> imageSrcs = new ArrayList<String>();
        ArrayList<String> IMDBs = new ArrayList<String>();
        for (int i = 0; i < size; i++) {
            String movieName = memoirArrayList.get(i).get("movieName");
            String releaseDate = memoirArrayList.get(i).get("releaseDate");
            String watchedDate = memoirArrayList.get(i).get("watchedDate");
            String cinemaPost = memoirArrayList.get(i).get("cinemaPost");
            String comment = memoirArrayList.get(i).get("comments");
            String score = memoirArrayList.get(i).get("score");
            String imageSre = memoirArrayList.get(i).get("imageSrc");
            String IMDB = memoirArrayList.get(i).get("IMDBID");
            movieNames.add(movieName);
            releaseDates.add(releaseDate);
            watchedDates.add(watchedDate);
            cinemaPosts.add(cinemaPost);
            comments.add(comment);
            scores.add(score);
            imageSrcs.add(imageSre);
            IMDBs.add(IMDB);
        }

        for (int i = 0; i < movieNames.size(); i++) {
            ItemModel itemModel = new ItemModel(movieNames.get(i),imageSrcs.get(i),releaseDates.get(i),watchedDates.get(i)
                    ,cinemaPosts.get(i), comments.get(i),scores.get(i),IMDBs.get(i));
            itemModelList.add(itemModel);
        }
//        final List<ItemModel> tmpList = new ArrayList<>();
//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String choice = parent.getItemAtPosition(position).toString();
//
//                switch (choice) {
//                    case "User Rating":
//                        Collections.sort(itemModelList, new Comparator<ItemModel>() {
//                            @Override
//                            public int compare(ItemModel m1, ItemModel m2) {
//                                String m1Rating = Double.valueOf(m1.getSocre());
//                                double m2Rating = Double.valueOf(m2.getSocre());
//                                if (m1Rating < m2Rating){
//                                    return 1;
//                                }
//                                else return -1;
//                            }
//                        });
//                        break;
//                    case "Watched Rating":
//                        //memoirAdapter.addList(itemModelList);
//                        break;
//                    case "Release Date":

//                        break;
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
        memoirAdapter = new MemoirFragment.MemoirAdapter(itemModelList,getContext());

        listView.setAdapter(memoirAdapter);
    }

    public class MemoirAdapter extends BaseAdapter {

        private List<ItemModel> itemModels;
        private List<ItemModel> itemModelsListFiltered;
        private Context context;

        public MemoirAdapter(List<ItemModel> itemModels, Context context) {
            this.itemModels = itemModels;
            this.itemModelsListFiltered = itemModelList;
            this.context = context;
        }

        @Override
        public int getCount() {
            return itemModelsListFiltered.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = getLayoutInflater().inflate(R.layout.memoir_row_items,null);
            final ImageView ivResult = view.findViewById(R.id.imageview);
            TextView itemName = view.findViewById(R.id.name);
            TextView itemRelease = view.findViewById(R.id.releaseDate);
            TextView itemWatch = view.findViewById(R.id.watchedDate);
            TextView itemPost = view.findViewById(R.id.cinemaPost);
            TextView itemComment = view.findViewById(R.id.comments);
            RatingBar itemRating = view.findViewById(R.id.rating);

            new AsyncTask<String, Void, Bitmap>(){
                ImageView imageView = ivResult;
                @Override
                protected Bitmap doInBackground(String... strings) {
                    String urlLink = strings[0];
                    Bitmap bitmap = null;
                    try {
                        InputStream inputStream = new URL(urlLink).openStream();
                        bitmap = BitmapFactory.decodeStream(inputStream);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    return bitmap;
                }

                @Override
                protected void onPostExecute(Bitmap bitmap) {
                    ivResult.setImageBitmap(bitmap);
                }
            }.execute(itemModelsListFiltered.get(position).getImageSrc());

            itemName.setText(itemModelsListFiltered.get(position).getName());
            String rd = "Release Date: " + itemModelsListFiltered.get(position).getReleaseDate();
            itemRelease.setText(rd);
            String wd = "Watched Date: " + itemModelsListFiltered.get(position).getWatchDate();
            itemWatch.setText(wd);
            String pc = "Cinema Postcode: " + itemModelsListFiltered.get(position).getCinemaPostcode();
            itemPost.setText(pc);
            String cm = "Comment: " + itemModelsListFiltered.get(position).getComments();
            itemComment.setText(cm);
            Float star = Float.parseFloat(itemModelsListFiltered.get(position).getSocre());
            itemRating.setRating(star);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(),
                            MovieViewActivity.class);
                    intent.putExtra("item", itemModelsListFiltered.get(position));
                    intent.putExtra("userId",user_id);
                    startActivity(intent);
                }
            });
            return view;
        }
    }

    private class LoadImage extends AsyncTask<String, Void, Bitmap>{
        ImageView imageView;

        public LoadImage(ImageView ivResult){
            this.imageView = ivResult;
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            String urlLink = strings[0];
            Bitmap bitmap = null;
            try {
                InputStream inputStream = new URL(urlLink).openStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            ivResult.setImageBitmap(bitmap);
        }
    }
}