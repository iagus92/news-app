package com.example.iago92.newsapp.core;

import com.example.iago92.newsapp.models.SourceArticle;
import com.example.iago92.newsapp.models.SourceList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by iago92 on 2/11/17.
 */

public interface ApiEndpoint {

    // Lista todas las noticias del recurso pasado por parámetro.
    @GET("articles")
    Call<SourceArticle> getNews(@Query("apiKey") String key, @Query("source") String source);

    // Lista todos las recursos del recurso pasado por parámetro.
    @GET("sources")
    Call<SourceList> getSources(@Query("apiKey") String key);

}
