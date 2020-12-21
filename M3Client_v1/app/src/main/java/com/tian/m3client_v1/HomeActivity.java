package com.tian.m3client_v1;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.room.Room;

import com.google.android.material.navigation.NavigationView;
import com.tian.m3client_v1.fragment.HomeFragment;
import com.tian.m3client_v1.fragment.MapFragment;
import com.tian.m3client_v1.fragment.MemoirFragment;
import com.tian.m3client_v1.fragment.MovieSearchFragment;
import com.tian.m3client_v1.fragment.ReportFragment;
import com.tian.m3client_v1.fragment.WatchListFragment;
import com.tian.m3client_v1.room.MovieDB;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static MovieDB movieDB;
    public static FragmentManager fragmentManager;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private static String user_name;
    private static Integer user_id;
    private static String user_address;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        fragmentManager = getSupportFragmentManager();
        movieDB = Room.databaseBuilder(getApplicationContext(), MovieDB.class,"moviedb")
                .allowMainThreadQueries().build();

        Intent intent = getIntent();
        user_name = intent.getStringExtra("userName");
        user_id = intent.getIntExtra("userId",1);
        user_address = intent.getStringExtra("stNumber") + "+" + intent.getStringExtra("stName") + "+" + intent.getIntExtra("postcode",3000);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nv);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.Open, R.string.Close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //these two lines of code show the navigation drawer icon top left hand side
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        navigationView.setNavigationItemSelectedListener(this);
        Bundle sendBundle = new Bundle();
        sendBundle.putString("user_name",user_name);
        sendBundle.putInt("userId",user_id);

        HomeFragment hf = new HomeFragment();
        hf.setArguments(sendBundle);
        replaceFragment(hf);
//        replaceFragment(new HomeFragment());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_home:
                Bundle sendBundle = new Bundle();
                sendBundle.putString("user_name",user_name);
                sendBundle.putInt("userId",user_id);
                HomeFragment hf = new HomeFragment();
                hf.setArguments(sendBundle);
                replaceFragment(hf);
//                replaceFragment(new HomeFragment());
                break;
            case R.id.nav_search:
                Bundle sendBundle2 = new Bundle();
                sendBundle2.putInt("userId",user_id);
                MovieSearchFragment msf = new MovieSearchFragment();
                msf.setArguments(sendBundle2);
                replaceFragment(msf);
                break;
            case R.id.nav_memoir:
                Bundle sendBundle3 = new Bundle();
                sendBundle3.putInt("userId",user_id);
                MemoirFragment mf = new MemoirFragment();
                mf.setArguments(sendBundle3);
                replaceFragment(mf);
                break;
            case R.id.nav_watchlist:
                Bundle sendBundle6 = new Bundle();
                sendBundle6.putInt("userId",user_id);
                WatchListFragment wlf = new WatchListFragment();
                wlf.setArguments(sendBundle6);
                replaceFragment(wlf);
                break;
            case R.id.nav_reports:
                Bundle sendBundle4 = new Bundle();
                sendBundle4.putInt("userId",user_id);
                ReportFragment rf = new ReportFragment();
                rf.setArguments(sendBundle4);
                replaceFragment(rf);
                break;
            case R.id.nav_maps:
                Bundle sendBundle5 = new Bundle();
                sendBundle5.putInt("userId",user_id);
                sendBundle5.putString("userAddress",user_address);
                MapFragment mapf = new MapFragment();
                mapf.setArguments(sendBundle5);
                replaceFragment(mapf);
                break;
            case R.id.nav_logout:
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
        }
        //this code closes the drawer after you selected an item from the menu, otherwise stay open
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void replaceFragment(Fragment nextFragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, nextFragment);
        fragmentTransaction.commit();
    }


}
