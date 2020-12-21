package com.tian.m3client_v1.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.tian.m3client_v1.R;
import com.tian.m3client_v1.networkconnection.NetworkConnection;

public class AddCinemaActivity extends AppCompatActivity {
    private EditText name,postcode;
    private Button btnSubmit;
    NetworkConnection networkConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cinema);
        networkConnection = new NetworkConnection();

        name = findViewById(R.id.movieName);
        postcode = findViewById(R.id.postcode);
        btnSubmit = findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String n = name.getText().toString();
                String p = postcode.getText().toString();
                String details[] = {n,p};
                if (n != null && p != null){
                    AddCinemaTask addCinemaTask = new AddCinemaTask();
                    addCinemaTask.execute(details);
                } else {
                    Toast toast = Toast.makeText(AddCinemaActivity.this,
                            "Cinema Name and Postcode cannot be empty", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }
        });


    }

    private class AddCinemaTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {

            return networkConnection.addCinema(params);
        }
        @Override
        protected void onPostExecute(String result) {
            Toast toast = Toast.makeText(AddCinemaActivity.this,
                    "Add cinema successful!", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }
}
