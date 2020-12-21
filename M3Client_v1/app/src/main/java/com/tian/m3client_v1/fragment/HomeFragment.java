package com.tian.m3client_v1.fragment;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import com.tian.m3client_v1.R;
import com.tian.m3client_v1.networkconnection.NetworkConnection;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class HomeFragment extends Fragment {
    NetworkConnection networkConnection;
    ImageSwitcher imageSwitcher;
    int[] imageList = {R.drawable.home1,R.drawable.home2,R.drawable.home3};
    int count = imageList.length;
    ImageButton btPre,brNext;
    int currentIndex = 0;

    private TextView textView, tvTime, tvNoData;
    private ListView memoirList;
    List<HashMap<String, String>> memoirListArray;
    SimpleAdapter myListAdapter;
    private String[] colHEAD = new String[]{"MovieName", "ReleaseDate", "MovieRating"};
    private int[] dataCell = new int[]{R.id.movieName, R.id.movieDate, R.id.movieRating};

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        networkConnection = new NetworkConnection();

        Bundle bundle = getArguments();
        Integer userid = 1;
        String result = null;
        if (bundle != null) {
            result = bundle.getString("user_name");
            userid = bundle.getInt("userId",1);
        }
        textView = view.findViewById(R.id.tv);
        textView.setText("Hello, " + result);
        tvNoData = view.findViewById(R.id.tvNoData);

        imageSwitcher = view.findViewById(R.id.home_pics);
        btPre = view.findViewById(R.id.bt_pre);
        brNext = view.findViewById(R.id.bt_next);
        imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView = new ImageView(getActivity().getApplicationContext());
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                imageView.setLayoutParams(new ImageSwitcher.LayoutParams(
                        LinearLayout.LayoutParams.FILL_PARENT,
                        LinearLayout.LayoutParams.FILL_PARENT));
                return imageView;
            }
        });
        imageSwitcher.setImageResource(imageList[0]);
        btPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                --currentIndex;
                if (currentIndex < 0)
                    currentIndex = imageList.length-1;
                imageSwitcher.setImageResource(imageList[currentIndex]);
            }
        });

        brNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentIndex++;
                if (currentIndex == count)
                    currentIndex = 0;
                imageSwitcher.setImageResource(imageList[currentIndex]);
            }
        });


        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        String currentDate1 = simpleDateFormat.format(date);
        tvTime = view.findViewById(R.id.current_date);
        tvTime.setText("Today is " + currentDate1);
        memoirList = view.findViewById(R.id.memoirList);
        GetMemoirTask getMemoirTask = new GetMemoirTask();
        getMemoirTask.execute(userid);
        return view;
    }

    private class GetMemoirTask extends AsyncTask<Integer, Void, String> {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected String doInBackground(Integer... params) {
            int id = params[0].intValue();
            memoirListArray = networkConnection.findMemoir(id);
            myListAdapter = new SimpleAdapter(getContext(), memoirListArray, R.layout.list_view, colHEAD, dataCell);

            return null;
        }

        @Override
        protected void onPostExecute(String results) {
            if (myListAdapter.isEmpty()){
                tvNoData.setText("No memoir records");
            } else
                memoirList.setAdapter(myListAdapter);
        }
    }

}
