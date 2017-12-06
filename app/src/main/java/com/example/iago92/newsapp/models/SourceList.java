package com.example.iago92.newsapp.models;

/**
 * Created by iago92 on 3/11/17.
 */


import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SourceList {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("sources")
    @Expose
    private List<Source> sources = null;

    public String getStatus() {
        return status;
    }

    public List<Source> getSources() {
        return sources;
    }

}
