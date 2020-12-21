package com.tian.m3client_v1;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.tian.m3client_v1.entity.Credentialstable;
import com.tian.m3client_v1.entity.Usertable;
import com.tian.m3client_v1.networkconnection.MD5;
import com.tian.m3client_v1.networkconnection.NetworkConnection;

import org.json.JSONArray;
import org.json.JSONObject;


public class LoginActivity extends AppCompatActivity {
    NetworkConnection networkConnection = null;
    Credentialstable cre = new Credentialstable();

    //private SharedViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        networkConnection = new NetworkConnection();

        final TextInputLayout username = findViewById(R.id.username);
        final TextInputLayout psw = findViewById(R.id.password);
        Button signInBtn = findViewById(R.id.signIn);
        signInBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String uname = username.getEditText().getText().toString().trim();
                String ps = psw.getEditText().getText().toString().trim();
                GetByUsernameTask getByUsernameTask = new GetByUsernameTask();
                if (!uname.isEmpty()&&!ps.isEmpty()) {
                    getByUsernameTask.execute(uname);

                } else {
                    Toast toast = Toast.makeText(LoginActivity.this,
                            "Please enter your email and password!", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }
        });

        Button signUpBtn = findViewById(R.id.signUp);
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    private class GetByUsernameTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String username = params[0];
            return networkConnection.getByUsername(username);
        }

        @Override
        protected void onPostExecute(String results) {
            login(results);
        }
    }

    public void login(String s) {
        MD5 md5 = new MD5();
        TextInputLayout psw = findViewById(R.id.password);
        String ps = psw.getEditText().getText().toString().trim();
        if (!s.equals("[]")) {
            try {
                JSONArray ja = new JSONArray(s);
                for (int i = 0; i < ja.length(); i++) {
                    JSONObject jo = ja.getJSONObject(i);
                    cre.setCredentialsId(jo.getInt("credentialsId"));
                    cre.setUsername(jo.getString("username"));
                    cre.setPassword(jo.getString("password"));
                    cre.setUserId(new Usertable(jo.getJSONObject("userId").getInt("userId"),
                            jo.getJSONObject("userId").getString("userName"),
                            jo.getJSONObject("userId").getString("stNumber"),
                            jo.getJSONObject("userId").getString("stName"),
                            jo.getJSONObject("userId").getInt("postode")));
                }
            }catch(Exception e) {
                e.printStackTrace();
            }
            String pwd = cre.getPassword();
            String tmp = md5.MD5_Hash(ps);
            if (pwd.equals(tmp)) {
                Intent intent = new Intent(this, HomeActivity.class);
                intent.putExtra("credentialsId", cre.getCredentialsId());
                intent.putExtra("username", cre.getUsername());
                intent.putExtra("userId",cre.getUserId().getUserId());
                intent.putExtra("userName", cre.getUserId().getUserName());
                intent.putExtra("stNumber", cre.getUserId().getStNumber());
                intent.putExtra("stName", cre.getUserId().getStName());
                intent.putExtra("postcode",cre.getUserId().getPostode());
                startActivity(intent);
            } else {
                Toast toast = Toast.makeText(LoginActivity.this,
                        "Password error! Try again please.", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        } else {
            Toast toast = Toast.makeText(LoginActivity.this,
                    "This email does not exist, please try again or sign up first!", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

}
