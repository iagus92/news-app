package com.example.iago92.newsapp;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iago92.newsapp.models.Article;
import com.squareup.picasso.Picasso;

public class ArticleDetailActivity extends AppCompatActivity {

    private Intent data;
    private Article article;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        this.getSupportActionBar().hide();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_detail);
        toolbar.setNavigationIcon(R.drawable.ic_menu_back);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        data = this.getIntent();
        article = (Article) data.getSerializableExtra("article");

        toolbar.setTitle(article.getTitle());

        int res = this.getResources().getIdentifier("action_bar_contaier","id",getPackageName());
        this.findViewById(res);
        TextView title = (TextView) this.findViewById(R.id.title);
        TextView author = (TextView) this.findViewById(R.id.author);
        TextView date = (TextView) this.findViewById(R.id.date);
        TextView description = (TextView) this.findViewById(R.id.description);
        TextView url = (TextView) this.findViewById(R.id.url);
        ImageView image = (ImageView) this.findViewById(R.id.image);

        title.setText(article.getTitle());
        author.setText(article.getAuthor());
        date.setText(article.getPublishedAt());
        description.setText(article.getDescription());

        Button boton = (Button) findViewById(R.id.url);
        Picasso.with(getApplicationContext()).load(article.getUrlToImage()).into(image);

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(getApplicationContext(), ArticleWebView.class);
                myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                myIntent.putExtra("url", article.getUrl());
                getApplicationContext().startActivity(myIntent);
            }
        });
    }


}
