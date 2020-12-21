package com.tian.m3client_v1.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tian.m3client_v1.HomeActivity;
import com.tian.m3client_v1.MovieViewActivity;
import com.tian.m3client_v1.R;
import com.tian.m3client_v1.entity.ItemModel;
import com.tian.m3client_v1.networkconnection.SearchGoogleAPI;
import com.tian.m3client_v1.room.Movie;
import com.tian.m3client_v1.room.MovieDB;
import com.tian.m3client_v1.room.MovieViewModel;

import java.util.ArrayList;
import java.util.List;

public class WatchListFragment extends Fragment{

    List<Movie> list = HomeActivity.movieDB.movieDAO().getAll();

    MovieAdapter movieAdapter;
    List<Movie> itemModelList = new ArrayList<>();
    ListView listView;
    MovieDB movieDB = null;
    MovieViewModel movieViewModel;

    private static Integer user_id;
    private DatabaseReference databaseReference;
    public WatchListFragment() {

    }

    @Override
    public View  onCreateView(LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.watchlist_fragment, container, false);

        Bundle bundle = getArguments();
        if (bundle != null) {
            user_id = bundle.getInt("userId",1);
        }

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        listView = view.findViewById(R.id.listView);

        ArrayList<String> rdates = new ArrayList<String>();
        ArrayList<String> names = new ArrayList<String>();
        ArrayList<String> addTime = new ArrayList<String>();
        for (int i = 0; i < list.size(); i++) {
            names.add(list.get(i).getMovieName());
            addTime.add(list.get(i).getAddDate());
            rdates.add(list.get(i).getReleaseDate());
        }
        for (int i = 0; i < list.size(); i++) {
            Movie itemModel = new Movie(i,names.get(i),rdates.get(i),addTime.get(i));
            itemModelList.add(itemModel);
        }
        movieAdapter = new MovieAdapter(itemModelList,getContext());
        listView.setAdapter(movieAdapter);

        return view;
    }

    public class MovieAdapter extends BaseAdapter {

        private List<Movie> itemModels;
        private List<Movie> itemModelsListFiltered;
        private Context context;
        private ItemModel item;

        public MovieAdapter(List<Movie> itemModels, Context context) {
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
            View view = getLayoutInflater().inflate(R.layout.watchlist_single_view,null);
            TextView mname = view.findViewById(R.id.mname);
            TextView adate = view.findViewById(R.id.adate);
            TextView rdate = view.findViewById(R.id.rdate);
            Button btn = view.findViewById(R.id.btnDelete);

            mname.setText(itemModelsListFiltered.get(position).getMovieName());
            rdate.setText(itemModelsListFiltered.get(position).getReleaseDate());
            adate.setText(itemModelsListFiltered.get(position).getAddDate());
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Do you want to remove the film from the watchlist and firebase?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Movie movie = new Movie();
                                    int id = list.get(position).getUid();
                                    movie.setUid(id);
                                    HomeActivity.movieDB.movieDAO().delete(movie);
                                    HomeActivity.fragmentManager.beginTransaction()
                                            .replace(R.id.content_frame, new WatchListFragment(), null).commit();
                                    Toast toast = Toast.makeText(getActivity(),
                                            "Delete successful!", Toast.LENGTH_SHORT);
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                    toast.show();
                                    // delete from firebase
                                    databaseReference = FirebaseDatabase.getInstance().getReference().child("FireMovie");
                                    databaseReference.child(String.valueOf(position+1)).setValue(null);
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            });

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String n = itemModelsListFiltered.get(position).getMovieName();
                    String nn = n.substring(0,n.indexOf("(")-1);
                    String name = nn.replace(" ","+");
                    String year = n.substring(n.indexOf("(") + 1, n.indexOf(")"));
                    String IMDBID =  SearchGoogleAPI.searchOMDBByNameAndYear(name,year);
                    item = new ItemModel(nn,IMDBID);
                    Intent intent = new Intent(getActivity(),
                            MovieViewActivity.class);
                    intent.putExtra("item", item);
                    intent.putExtra("userId",user_id);
                    startActivity(intent);
                }
            });
            return view;
        }
    }


}
