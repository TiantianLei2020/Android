package com.tian.m3client_v1;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputLayout;
import com.tian.m3client_v1.fragment.DatePickerFragment;
import com.tian.m3client_v1.networkconnection.MD5;
import com.tian.m3client_v1.networkconnection.NetworkConnection;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    NetworkConnection networkConnection;
    MD5 md5 = new MD5();
    private Spinner stateSpinner;
    private TextInputLayout email,psw,pswCon,fname,sname,stno,stname,postcode;
    private TextView birth;
    private RadioButton male,female;
    private EditText edEmail,edPwd,edPwdCon,edFn,edSn,edSno,edSna,edpost;
    static boolean emailF = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        networkConnection = new NetworkConnection();

        // date picker
        Button button = findViewById(R.id.birBtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        email = findViewById(R.id.email);
        psw = findViewById(R.id.psw);
        pswCon = findViewById(R.id.pswConfirm);
        fname = findViewById(R.id.first_name);
        sname = findViewById(R.id.sur_name);
        birth = findViewById(R.id.bir);
        stno = findViewById(R.id.st_no);
        stname = findViewById(R.id.st_name);
        postcode = findViewById(R.id.postcode);
        stateSpinner = findViewById(R.id.state);
        male = findViewById(R.id.male);
        female = findViewById(R.id.female);

        edEmail = findViewById(R.id.edEmail);
        edPwd = findViewById(R.id.edPsd);
        edPwdCon = findViewById(R.id.edPsdCon);
        edFn = findViewById(R.id.tfn);
        edSn = findViewById(R.id.tsn);
        edSno = findViewById(R.id.edSno);
        edSna = findViewById(R.id.edSna);
        edpost = findViewById(R.id.edpost);


        // check inputs
        edEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String s = edEmail.getText().toString().trim();
                if (!hasFocus) {
                    if (TextUtils.isEmpty(s)) {
                        email.setError("Email cannot be empty!");
                        email.setErrorEnabled(true);
                    }else if (!validationEmail(s)){
                        email.setError("Username must be a email");
                    }else {
                        email.setErrorEnabled(false);
                    }
                }
            }
        });

        edPwd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String s = edPwd.getText().toString().trim();
                if (!hasFocus) {
                    if (s.length()<6 || s.length() > 15) {
                        psw.setError("The password must be between 6 and 15!");
                        psw.setErrorEnabled(true);
                    }else {
                        psw.setErrorEnabled(false);
                    }
                }
            }
        });
        edPwdCon.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String s = edPwdCon.getText().toString().trim();
                String ss = edPwd.getText().toString().trim();
                if (!hasFocus){
                    if (!s.equals(ss)) {
                        pswCon.setError("Inconsistent password!");
                        pswCon.setErrorEnabled(true);
                    }else {
                        pswCon.setErrorEnabled(false);
                    }
                }
            }
        });
        edFn.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String s = edFn.getText().toString().trim();
                if (!hasFocus) {
                    if (TextUtils.isEmpty(s)) {
                        fname.setError("First Name cannot be empty!");
                        fname.setErrorEnabled(true);
                    }else {
                        fname.setErrorEnabled(false);
                    }
                }
            }
        });
        edSn.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String s = edSn.getText().toString().trim();
                if (!hasFocus){
                    if (TextUtils.isEmpty(s)) {
                        sname.setError("Surname cannot be empty!");
                        sname.setErrorEnabled(true);
                    }else {
                        sname.setErrorEnabled(false);
                    }
                }

            }
        });
        edSno.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String s = edSno.getText().toString().trim();
                if (!hasFocus) {
                    if (TextUtils.isEmpty(s)) {
                        stno.setError("Stree Number cannot be empty!");
                        stno.setErrorEnabled(true);
                    }else {
                        stno.setErrorEnabled(false);
                    }
                }
            }
        });
        edSna.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String s = edSna.getText().toString().trim();
                if (!hasFocus){
                    if (TextUtils.isEmpty(s)) {
                        stname.setError("Stree Number cannot be empty!");
                        stname.setErrorEnabled(true);
                    }else {
                        stname.setErrorEnabled(false);
                    }
                }
            }
        });
        edpost.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String s = edpost.getText().toString().trim();
                if (!hasFocus) {
                    if (TextUtils.isEmpty(s)) {
                        postcode.setError("Stree Number cannot be empty!");
                        postcode.setErrorEnabled(true);
                    }else {
                        postcode.setErrorEnabled(false);
                    }
                }
            }
        });

        // Submit the data
        Button subBtn = findViewById(R.id.submit);
        subBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date(System.currentTimeMillis());
                String currentDate1 = simpleDateFormat.format(date);
                String currentDate2 = currentDate1 + "T00:00:00+11:00";

                String e = edEmail.getText().toString().trim();
                String p = edPwd.getText().toString().trim();
                String f = edFn.getText().toString().trim();
                String s = edSn.getText().toString().trim();
                String b = birth.getText().toString();
                String so = edSno.getText().toString().trim();
                String sn = edSna.getText().toString();
                String po = edpost.getText().toString().trim();
                // get gender
                String sex = "";
                if (male.isChecked()) {
                    sex = male.getText().toString();
                } else {
                    sex = female.getText().toString();
                }
                // get state
                String sta = stateSpinner.getSelectedItem().toString();

                String pp = md5.MD5_Hash(p);
                String bb = b + "T00:00:00+11:00";

                GetByUsernameTask getByUsernameTask1 = new GetByUsernameTask();
                getByUsernameTask1.execute(e);
                if (emailF){
                    email.setErrorEnabled(false);
                    if (!p.isEmpty() && !f.isEmpty() && !s.isEmpty() && !b.isEmpty()
                        && !so.isEmpty() && !sn.isEmpty() && !po.isEmpty()){
                        // add user
                        String[] details = {null,e,pp,currentDate2,null,f,s,sex,bb,so,sn,sta,po};
                        AddUserTask addUserTask = new AddUserTask();
                        addUserTask.execute(details);
                    }else {
                        Toast toast = Toast.makeText(SignUpActivity.this,
                                "Please check the information you filled in!", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                } else {
                    email.setError("Email already exists!");
                    email.setErrorEnabled(true);
                    Toast toast = Toast.makeText(SignUpActivity.this,
                            "Email already exists!", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }
        });

        // return to Login page
        Button reBtn = findViewById(R.id.ret);
        reBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    // date picker
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd");
        String currentDateString = sp.format(c.getTime());
        TextView tv = findViewById(R.id.bir);
        tv.setText(currentDateString);
    }

    private class GetByUsernameTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String username = params[0];
            return networkConnection.getByUsername(username);
        }

        @Override
        protected void onPostExecute(String results) {
            if (results.equals("[]")){
                emailF = true;
            } else {
                emailF = false;
            }

        }
    }

    //add new user
    private class AddUserTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {

            return networkConnection.addUser(params);
        }
        @Override
        protected void onPostExecute(String result) {
            Toast toast = Toast.makeText(SignUpActivity.this,
                        "Registered successful！", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    // user name must be email
    public boolean validationEmail(String s){
        boolean isEmail = false;
        try {
            String REGEX1 = "^\\w+((-\\w+)|(\\.\\w+))*@\\w+(\\.\\w+)+$";
            /*String REGEX2 = "^([a-z0-9A-Z]+[-|_|\\\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\\\.)+[a-zA-Z]{2,}$";*/
            Pattern p = Pattern.compile(REGEX1);
            Matcher matcher = p.matcher(s);
            isEmail = matcher.matches();
        } catch (Exception e) {
            isEmail = false;
        }
        return isEmail;
    }
}
