package com.example.iago92.newsapp.core;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iago92.newsapp.ArticleActivity;
import com.example.iago92.newsapp.ArticleDetailActivity;
import com.example.iago92.newsapp.R;
import com.example.iago92.newsapp.ResourceActivity;
import com.example.iago92.newsapp.models.Article;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by iago92 on 2/11/17.
 */

public class ArticleListAdapter extends RecyclerView.Adapter<ArticleListAdapter.ViewHolder> {
    private List<Article> mDataset;
    private Context context;
    private ArticleDB articleDB;

    // Provide a suitable constructor.
    public ArticleListAdapter(Context contexts, List<Article> myDataset) {
        this.context = contexts;
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ArticleListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.article_list_element, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        if(mDataset.get(position).getUrlToImage() != null)
            Picasso.with(holder.imageView.getContext()).
                load(mDataset.get(position).getUrlToImage()).
                    centerCrop().into(holder.imageView);
        holder.mTextView.setText(mDataset.get(position).getTitle());
        holder.setClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if (!isLongClick) {
                    Intent myIntent = new Intent(context, ArticleDetailActivity.class);
                    myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Article art = new Article(mDataset.get(position).getAuthor(),
                            mDataset.get(position).getTitle(),
                            mDataset.get(position).getDescription(),
                            mDataset.get(position).getUrl(),
                            mDataset.get(position).getUrlToImage(),
                            mDataset.get(position).getPublishedAt());
                    myIntent.putExtra("article", art);
                    context.startActivity(myIntent);
                } else {
                    articleDB = new ArticleDB(context);
                    Long row_id = articleDB.insert(
                            mDataset.get(position).getAuthor(),
                            mDataset.get(position).getTitle(),
                            mDataset.get(position).getDescription(),
                            mDataset.get(position).getUrl(),
                            mDataset.get(position).getUrlToImage(),
                            mDataset.get(position).getPublishedAt());
                    if(row_id != -1) {
                        Toast.makeText(context, "Artículo guardado",
                                Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(context, "Error guardando artículo",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        private ItemClickListener clickListener;
        private TextView mTextView;
        private ImageView imageView;

        public ViewHolder(View v) {
            super(v);
            mTextView = v.findViewById(R.id.element);
            imageView = v.findViewById(R.id.image);
            v.setTag(v);
            imageView.setOnClickListener(this);
            imageView.setOnLongClickListener(this);
        }

        public void setClickListener(ItemClickListener itemClickListener) {
            this.clickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            clickListener.onClick(view, getPosition(), false);
        }

        @Override
        public boolean onLongClick(View view) {
            clickListener.onClick(view, getPosition(), true);
            return true;
        }
    }
}
