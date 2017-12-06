package com.example.iago92.newsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.iago92.newsapp.core.ApiEndpoint;
import com.example.iago92.newsapp.core.CategoryAdapter;
import com.example.iago92.newsapp.models.Article;
import com.example.iago92.newsapp.models.SourceList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by iago92 on 3/11/17.
 */

public class ResourceActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String BASE_URL = "https://newsapi.org/v1/";
    private static final String KEY = "baac9c325c3a48db8a1d763e785f9cf5";
    private RecyclerView mRecyclerView;
    private CategoryAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        category = null;

        api();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void api(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiEndpoint apiService = retrofit.create(ApiEndpoint.class);

        //
        Call<SourceList> call = apiService.getSources(KEY);
        call.enqueue(new Callback<SourceList>() {
            @Override
            public void onResponse(Call<SourceList> call, Response<SourceList> response) {
                SourceList s = response.body();
                mAdapter = new CategoryAdapter(getApplicationContext(), category, s.getSources());
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<SourceList> call, Throwable t) {
                Toast.makeText(ResourceActivity.this, "No hay conexión a internet",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch(id){
            case R.id.nav_business:
                category = "business";
                break;
            case R.id.nav_entertainment:
                category = "entertainment";
                break;
            case R.id.nav_gaming:
                category = "gaming";
                break;
            case R.id.nav_general:
                category = "general";
                break;
            case R.id.nav_music:
                category = "music";
                break;
            case R.id.nav_politics:
                category = "politics";
                break;
            case R.id.nav_science:
                category = "science-and-nature";
                break;
            case R.id.nav_sport:
                category = "sport";
                break;
            case R.id.nav_technology:
                category = "technology";
                break;
            case R.id.nav_savedArticles:
                category = "saved-articles";
                break;
        }
        menuAction();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void menuAction(){
        if(category != null && category.equals("saved-articles")){
            Intent myIntent = new Intent(getApplicationContext(), SavedArticlesActivity.class);
            myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getApplicationContext().startActivity(myIntent);
        }else{
            api();
        }
    }
}
