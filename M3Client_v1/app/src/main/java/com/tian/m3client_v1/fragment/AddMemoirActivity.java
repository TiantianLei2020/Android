package com.tian.m3client_v1.fragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.tian.m3client_v1.R;
import com.tian.m3client_v1.networkconnection.NetworkConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddMemoirActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    NetworkConnection networkConnection;
    private TextView mName,mRelease,mWatchDate,mWatchTime;
    private Spinner spinner;
    private EditText comments;
    private RatingBar ratingBar;
    private Button datePick,addCinema,submit;
    private ImageView image;
    static List<String> cinemaSpinner = new ArrayList<String>();
    private static Integer user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_memoir);
        networkConnection = new NetworkConnection();

        final Intent intent = getIntent();
        final String movieName = intent.getStringExtra("movieName");
        String reDate = intent.getStringExtra("movieReleaseDate");
        String imgSrc = intent.getStringExtra("imageSrc");
        user_id = intent.getIntExtra("userId",1);

        mName = findViewById(R.id.movieName);
        mRelease = findViewById(R.id.releaseDate);
        mWatchDate = findViewById(R.id.watchDate);
        mWatchTime = findViewById(R.id.watchTime);
        spinner = findViewById(R.id.cinema);
        comments = findViewById(R.id.comments);
        ratingBar = findViewById(R.id.stars);
        datePick = findViewById(R.id.datePick);
        submit = findViewById(R.id.submit);
        image = findViewById(R.id.image);
        addCinema = findViewById(R.id.addCinema);

        mName.setText(movieName);
        mRelease.setText(reDate);
        new AsyncTask<String, Void, Bitmap>(){
            ImageView imageView = image;
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
                image.setImageBitmap(bitmap);
            }
        }.execute(imgSrc);


        datePick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(),"date picker");
            }
        });

        new  AsyncTask<Void, Void, String>(){
            @Override
            protected String doInBackground(Void... strings) {
                return networkConnection.findCinema();
            }
            @Override
            protected void onPostExecute(String results) {
                selectCinema(results);
            }
        }.execute();

        addCinema.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(AddMemoirActivity.this,AddCinemaActivity.class);
                startActivity(intent1);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mname = mName.getText().toString();
                String mrelease = mRelease.getText().toString();
                Date date = new Date();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy");
                try {
                    date = simpleDateFormat.parse(mrelease);
                }catch (ParseException ex) {
                    ex.printStackTrace();
                }
                SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd");
                String date2 = sp.format(date);
                String date3 = date2 + "T00:00:00+11:00";
                String watchDate = mWatchDate.getText().toString();
                String watchDate2 = watchDate + "T00:00:00+11:00";
                String watchTime = "2020-01-01T18:00:00+10:00";
                String comm = comments.getText().toString();
                String star = Float.toString(ratingBar.getRating());
                String uid = user_id.toString();
                String cid = spinner.getSelectedItem().toString().substring(0,1);

                String[] details = {mname,date3,watchDate2,watchTime,comm,star,uid,cid};
                AddMemoirTask addMemoirTask = new AddMemoirTask();
                addMemoirTask.execute(details);
            }
        });
    }

    public void selectCinema(String s){
        try {
            JSONArray ja = new JSONArray(s);
            for (int i = 0; i < ja.length(); i++) {
                JSONObject jo = ja.getJSONObject(i);
                String cinemaName = jo.getString("cinemaname");
                String cinemaPost = jo.getString("cinemaPostcode");
                String cinemaid = jo.getString("cinemaId");
                String cinemaAddress = null;
                cinemaAddress = cinemaid + " " + cinemaName + " " + cinemaPost;
                cinemaSpinner.add(cinemaAddress);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ArrayAdapter<String> spinnerAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, cinemaSpinner);

        spinner.setAdapter(spinnerAdapter);

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd");
        String currentDateString = sp.format(c.getTime());
        TextView tv = findViewById(R.id.watchDate);
        tv.setText(currentDateString);
    }

    private class LoadImage extends AsyncTask<String, Void, Bitmap> {
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
            image.setImageBitmap(bitmap);
        }
    }

    //add new user
    private class AddMemoirTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {

            return networkConnection.addMemoir(params);
        }
        @Override
        protected void onPostExecute(String result) {
            Toast toast = Toast.makeText(AddMemoirActivity.this,
                    "Added memoir successful！", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }
}
