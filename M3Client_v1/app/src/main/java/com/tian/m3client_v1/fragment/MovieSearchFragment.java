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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;
import com.tian.m3client_v1.MovieViewActivity;
import com.tian.m3client_v1.R;
import com.tian.m3client_v1.entity.ItemModel;
import com.tian.m3client_v1.networkconnection.SearchGoogleAPI;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MovieSearchFragment extends Fragment {
    private EditText editText;
    private ImageButton btnSearch;
    private TextInputLayout search;
    Context context;
    ListView listView;
    ImageView ivResult;
    List<ItemModel> itemModelList = new ArrayList<>();
    MovieAdapter movieAdapter;
    private static Integer user_id;

    public MovieSearchFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Inflate the View for this fragment
        View view = inflater.inflate(R.layout.moviesearch_fragment, container, false);

        Bundle bundle = getArguments();
        if (bundle != null) {
            user_id = bundle.getInt("userId",1);
        }

        listView = view.findViewById(R.id.listView);
        editText = view.findViewById(R.id.etSearch);
        btnSearch = view.findViewById(R.id.btn_search);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (android.os.Build.VERSION.SDK_INT > 9) {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                }
                final String keyword = editText.getText().toString();
                if (keyword.isEmpty()) {
                    Toast.makeText(getContext(), "Please enter the movie name!", Toast.LENGTH_SHORT).show();
                } else {
                    new AsyncTask<String, Void, String>() {
                        @Override
                        protected String doInBackground(String... params) {
                            return SearchGoogleAPI.search(keyword, new String[]{"num"}, new String[]{"5"});
                        }
                        @Override
                        protected void onPostExecute(String result) {
                            display(result);
                        }
                    }.execute();
                }
            }
        });
        return view;
    }

    public void display(String s) {
        List<HashMap<String, String>> searchResultList = SearchGoogleAPI.getInfo(s);
        ArrayList<String> names = new ArrayList<String>();
        ArrayList<String> desc = new ArrayList<String>();
        ArrayList<String> images = new ArrayList<String>();
        ArrayList<String> orgUrl = new ArrayList<String>();
        for (int i = 0; i < searchResultList.size(); i++) {
            String imSrc = searchResultList.get(i).get("imageSrc");
            String title = searchResultList.get(i).get("title");
            String year = searchResultList.get(i).get("year");
            String org = searchResultList.get(i).get("orgUrl");
            names.add(title);
            desc.add(year);
            images.add(imSrc);
            orgUrl.add(org);
        }

        for (int i = 0; i < desc.size(); i++) {
            ItemModel itemModel = new ItemModel(names.get(i),desc.get(i),images.get(i),orgUrl.get(i));
            itemModelList.add(itemModel);
        }
        movieAdapter = new MovieAdapter(itemModelList,getContext());
        listView.setAdapter(movieAdapter);
    }

    public class MovieAdapter extends BaseAdapter {

        private List<ItemModel> itemModels;
        private List<ItemModel> itemModelsListFiltered;
        private Context context;

        public MovieAdapter(List<ItemModel> itemModels, Context context) {
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
            View view = getLayoutInflater().inflate(R.layout.row_items,null);
            final ImageView ivResult = view.findViewById(R.id.imageview);
//           imageView = view.findViewById(R.id.imageview);
            TextView itemName = view.findViewById(R.id.itemName);
            TextView itemDesc = view.findViewById(R.id.itemDesc);

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
            itemDesc.setText(itemModelsListFiltered.get(position).getDesc());

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
