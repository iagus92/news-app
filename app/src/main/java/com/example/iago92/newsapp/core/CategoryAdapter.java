package com.example.iago92.newsapp.core;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iago92.newsapp.ArticleActivity;
import com.example.iago92.newsapp.R;
import com.example.iago92.newsapp.ResourceActivity;
import com.example.iago92.newsapp.models.Source;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private Context mContext;
    private String category;
    private List<Source> sources;
    private ArrayList<String[]> ids = new ArrayList<>();

    public CategoryAdapter(Context contexts, String category, List<Source> sources) {
        this.mContext = contexts;
        this.category = category;
        String[] v;
        if(category == null){
            for(Source src: sources){
                v = new String[2];
                v[0] = src.getId();
                v[1] = src.getName();
                ids.add(v);
            }
        } else {
            for(Source src: sources){
                if(src.getCategory().equals(category)) {
                    v = new String[2];
                    v[0] = src.getId();
                    v[1] = src.getName();
                    ids.add(v);
                }
            }
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.source_list_element, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.titleTextView.setText(ids.get(position)[1]);
        holder.setClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if (!isLongClick) {
                    Intent myIntent = new Intent(mContext, ArticleActivity.class);
                    myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    myIntent.putExtra("id", ids.get(position)[0]);
                    mContext.startActivity(myIntent);
                } else {

                }
            }
        });
    }

    @Override
    public int getItemCount() {

        return ids.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        private TextView titleTextView;
        private ItemClickListener clickListener;

        public ViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.element);
            itemView.setTag(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
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
