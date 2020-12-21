package com.tian.m3client_v1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tian.m3client_v1.entity.FireMovie;
import com.tian.m3client_v1.entity.ItemModel;
import com.tian.m3client_v1.fragment.AddMemoirActivity;
import com.tian.m3client_v1.networkconnection.SearchGoogleAPI;
import com.tian.m3client_v1.networkconnection.YoutubeConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class MovieViewActivity extends YouTubeBaseActivity {
    YouTubePlayerView youTubePlayerView;
    YouTubePlayer.OnInitializedListener onInitializedListener;
    Button playBtn,addWatch,addMemoir;

    TextView name,year,genre,cast,releaseDate,country,directors,plot,score;
    ItemModel itemModel;
    SearchGoogleAPI searchGoogleAPI = null;
    HashMap<String, String> detials;
    RatingBar ratingBar;
    private static String imageSrc = null;
    private static String moviename = null;
    private static String moviereleaseDate = null;
    private static Integer user_id;
    private static String imdbID;
    DatabaseReference databaseReference;
    private long maxId;
    private boolean isExist = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_view);
        searchGoogleAPI = new SearchGoogleAPI();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("FireMovie");

        name = findViewById(R.id.textViewName);
        year = findViewById(R.id.textViewYear);
        genre = findViewById(R.id.textViewGenre);
        cast = findViewById(R.id.textViewCast);
        releaseDate = findViewById(R.id.textViewDate);
        country = findViewById(R.id.textViewCountry);
        directors = findViewById(R.id.textViewDirec);
        plot = findViewById(R.id.textViewPlot);
        score = findViewById(R.id.textViewScore);ratingBar = findViewById(R.id.rating);
        addWatch = findViewById(R.id.addWatch);
        addMemoir = findViewById(R.id.addMemoir);

        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            itemModel = (ItemModel) intent.getSerializableExtra("item");
            user_id = intent.getIntExtra("userId",1);
        }
        imageSrc = itemModel.getImageSrc();

        String org = itemModel.getOrgUrl();
        String orgtt = null;
        String ttt = null;
        imdbID = null;
        if (org != null) {
            imdbID = org.substring(26,35);
            ttt = SearchGoogleAPI.searchOMDB(imdbID);
        } else {
            imdbID = itemModel.getIMDB();
            ttt = SearchGoogleAPI.searchOMDB(imdbID);
        }

        detials = SearchGoogleAPI.getDetials(ttt);
        moviename = itemModel.getName();
        moviereleaseDate = detials.get("releaseDate");
        String yyyy = null;
        yyyy = itemModel.getDesc();
        if (yyyy == null) {
            String yyy = detials.get("releaseDate");
            yyyy = yyy.substring(yyy.length()-4);
        }
        String nn = "Movie Name: " + itemModel.getName();
        String yy = "Year: " + yyyy;
        String gg = "Genre: " + detials.get("genre");
        String cc = "Cast: " + detials.get("cast");
        String dd = "Release Date: " + detials.get("releaseDate");
        String con = "Country: " + detials.get("country");
        String dire = "Director: " + detials.get("director");
        String pt = "Plot: " + detials.get("plot");
        String ss= "Score: " + detials.get("score");

        name.setText(nn);
        year.setText(yy);
        genre.setText(gg);
        cast.setText(cc);
        releaseDate.setText(dd);
        country.setText(con);
        directors.setText(dire);
        plot.setText(pt);
        score.setText(ss);

        String vedioId = SearchGoogleAPI.searchVedio(itemModel.getName(), new String[]{"num","type"}, new String[]{"1","vedio"});
        String vid = null;
        try {
            JSONObject jsonObject = new JSONObject(vedioId);
            JSONArray jsonArray = jsonObject.getJSONArray("items");
            if(jsonArray != null && jsonArray.length() > 0) {
                vid = jsonArray.getJSONObject(0).getString("link");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String vvid = vid.substring(vid.indexOf("=")+1);
        youTubePlayerView = findViewById(R.id.trailer);
        onInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo(vvid);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };
        playBtn = findViewById(R.id.playBtn);
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                youTubePlayerView.initialize(YoutubeConfig.getApiKey(), onInitializedListener);
            }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    maxId = dataSnapshot.getChildrenCount();
                    if (maxId > 0) {
                        ArrayList<FireMovie> fireMovieArrayList = new ArrayList<>();
                        for (DataSnapshot d : dataSnapshot.getChildren()) {
                            FireMovie fireMovie = d.getValue(FireMovie.class);
                            fireMovieArrayList.add(fireMovie);
                        }
                        for (int i = 0; i < fireMovieArrayList.size(); i++) {
                            if (fireMovieArrayList.get(i).getImdbID().equals(imdbID)) {
                                isExist = true;
                            }

                        }
                    }
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        float star = Float.parseFloat(detials.get("score"));
        ratingBar.setRating(star/2);
        addWatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String moviename = itemModel.getName();
                String releaseDate = detials.get("releaseDate");
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(System.currentTimeMillis());
                String newDate = simpleDateFormat.format(date);

                if (!isExist){
                    //Firebase
                    FireMovie fireMovie = new FireMovie();
                    fireMovie.setMovName(moviename);
                    fireMovie.setMovRelDate(moviereleaseDate);
                    fireMovie.setMovAddDateTime(newDate);
                    fireMovie.setImdbID(imdbID);
                    fireMovie.setPerId(user_id.toString());
                    databaseReference.child(String.valueOf(maxId + 1)).setValue(fireMovie);

                    com.tian.m3client_v1.room.Movie movie = new com.tian.m3client_v1.room.Movie(0,moviename,releaseDate,newDate);
                    HomeActivity.movieDB.movieDAO().insert(movie);
                    Toast.makeText(MovieViewActivity.this, "Added watchlist and firebase successfully",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MovieViewActivity.this, "Watchlist already exists for this movie!",Toast.LENGTH_SHORT).show();
                }

            }
        });
        addMemoir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent();
                intent1.setClass(MovieViewActivity.this, AddMemoirActivity.class);
                intent1.putExtra("movieName",moviename);
                intent1.putExtra("movieReleaseDate",moviereleaseDate);
                intent1.putExtra("imageSrc",imageSrc);
                intent1.putExtra("user_id",user_id);
                startActivity(intent1);
            }
        });
    }

}
