package com.tian.m3client_v1.fragment;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.tian.m3client_v1.R;
import com.tian.m3client_v1.networkconnection.NetworkConnection;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ReportFragment extends Fragment implements DatePickerDialog.OnDateSetListener{
    private PieChart pieChart;
    private BarChart mChart;
    private NetworkConnection networkConnection;
    private Spinner spinner;
    private ImageButton btnStart,btnEnd;
    private Button btnPie,btnCal;
    private TextView tvStart,tvEnd,tvTemp;
    private java.util.Date utilStartDate;
    private java.util.Date utilEndDate;

    public ReportFragment() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.report_fragment, container, false);

        networkConnection = new NetworkConnection();
        Bundle bundle = getArguments();
        Integer userid = 1;
        if (bundle != null) {
            userid = bundle.getInt("userId",1);

        }
        spinner = view.findViewById(R.id.barYear);
        mChart = view.findViewById(R.id.chartBar);
        mChart.getDescription().setEnabled(false);

        btnCal = view.findViewById(R.id.btnCal);
        btnStart = view.findViewById(R.id.btnFrom);
        btnEnd = view.findViewById(R.id.btnTo);
        btnPie = view.findViewById(R.id.btnPie);
        tvStart = view.findViewById(R.id.startDate);
        tvEnd = view.findViewById(R.id.endDate);
        tvTemp = view.findViewById(R.id.tmpDate);
        pieChart = view.findViewById(R.id.chart2);
        pieChart.getDescription().setEnabled(false);

        btnCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDailog();
            }
        });
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tm = tvTemp.getText().toString();
                tvStart.setText(tm);
            }
        });

        btnEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tm = tvTemp.getText().toString();
                tvEnd.setText(tm);
            }
        });
        final String finalUserid1 = userid.toString();
        btnPie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sDate = tvStart.getText().toString();
                String eDate = tvEnd.getText().toString();
                new AsyncTask<String, Void, String>(){
                    @Override
                    protected String doInBackground(String... params) {
                        String userId = params[0];
                        String start = params[1];
                        String end = params[2];
                        return networkConnection.getByScope(userId,start,end);
                    }

                    @Override
                    protected void onPostExecute(String results) {
                        if (results == "[]"){
                            Toast toast = Toast.makeText(getActivity(), "No data found", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        } else {
                            setPieChart(results);
                        }

                    }
                }.execute(finalUserid1,sDate,eDate);

            }
        });


        Button btnBar = view.findViewById(R.id.barChart);
        final Integer finalUserid = userid;
        btnBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String yy = spinner.getSelectedItem().toString();
                Integer yyyy = Integer.parseInt(yy);
                new AsyncTask<Integer, Void, String>(){
                    @Override
                    protected String doInBackground(Integer... params) {
                        Integer userId = params[0];
                        Integer year = params[1];
                        return networkConnection.getByMonth(userId,year);
                    }

                    @Override
                    protected void onPostExecute(String results) {
                        if (results == "[]"){
                            Toast toast = Toast.makeText(getActivity(), "No data found", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        } else {
                            setData(13,results);
                        }

                    }
                }.execute(finalUserid,yyyy);

                mChart.setFitBars(true);
            }
        });

        return view;
    }


    private void setPieChart(String results){
        ArrayList<PieEntry> yValues = new ArrayList<>();
        try {
            JSONArray ja = new JSONArray(results);
            for (int i = 0; i<ja.length(); i++) {
                JSONObject jo = ja.getJSONObject(i);
                String p = jo.getString("cinemaPostcode");
                String n = jo.getString("watchedNumber");
                String nn = n + "f";
                float value = Float.parseFloat(nn);

                yValues.add(new PieEntry(value,p));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        PieDataSet dataSet = new PieDataSet(yValues, "Postcode");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        PieData data = new PieData((dataSet));
        data.setValueTextSize(10f);
        data.setDrawValues(true);
        data.setValueTextColor(Color.YELLOW);
        pieChart.setData(data);
        pieChart.invalidate();
    }

    private void setData(int count,String results){
        ArrayList<BarEntry> yVals = new ArrayList<>();
        YAxis yAxis = mChart.getAxisLeft();
        yAxis.setGranularity(1.0F);

        String[] months = new String[]{"","Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec",""};
        XAxis xAxis = mChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(months));
        xAxis.setLabelCount(months.length);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setAxisMinimum(0f);
        xAxis.setAxisMaximum(13f);
        xAxis.setDrawLabels(true);
        xAxis.setLabelCount(count);
        xAxis.setDrawAxisLine(false);
        mChart.getAxisRight().setEnabled(false);

        try {
            JSONArray ja = new JSONArray(results);
            for (int i = 0; i<ja.length(); i++) {
                JSONObject jo = ja.getJSONObject(i);
                String number = jo.getString("watchedNumber");
                String month = jo.getString("month");
                int value = Integer.parseInt(number);
                yVals.add(new BarEntry(i+1,value));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        BarDataSet set = new BarDataSet(yVals, "Month");
        set.setColors(ColorTemplate.MATERIAL_COLORS);
        set.setDrawValues(true);

        BarData data = new BarData(set);
        mChart.setData(data);
        mChart.invalidate();
        mChart.animateY(500);
    }

    private void showDatePickerDailog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd");
        String startDate = sp.format(c.getTime());
        tvTemp.setText(startDate);
    }
}
