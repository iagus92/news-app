package com.example.iago92.newsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;
import com.example.iago92.newsapp.core.ArticleListAdapter;
import com.example.iago92.newsapp.core.ApiEndpoint;
import com.example.iago92.newsapp.models.SourceArticle;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ArticleActivity extends AppCompatActivity{
    private static final String BASE_URL = "https://newsapi.org/v1/";
    private static final String KEY = "baac9c325c3a48db8a1d763e785f9cf5";
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Intent data;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.articles);
        data = this.getIntent();
        id = data.getStringExtra("id");
        //toolbar.setTitle(id);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        loadArticles();
    }

    public void loadArticles(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiEndpoint apiService = retrofit.create(ApiEndpoint.class);

        Call<SourceArticle> call = apiService.getNews(KEY, id);
        call.enqueue(new Callback<SourceArticle>() {
            @Override
            public void onResponse(Call<SourceArticle> call, Response<SourceArticle> response) {
                SourceArticle s = response.body();
                mAdapter = new ArticleListAdapter(getApplicationContext(), s.getArticles());
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<SourceArticle> call, Throwable t) {
                Toast.makeText(ArticleActivity.this, "No hay conexión a internet",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
