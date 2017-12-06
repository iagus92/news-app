package com.example.iago92.newsapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * Created by iago92 on 6/12/17.
 */

public class ArticleWebView extends AppCompatActivity{

    private Intent data;
    private String url;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        data = this.getIntent();
        url = data.getStringExtra("url");
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.article_webview);
        ImageButton btBack = (ImageButton) this.findViewById(R.id.btBack);
        WebView wvView = (WebView) this.findViewById(R.id.wvView);
        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArticleWebView.this.finish();
            }
        });
        WebSettings settings = wvView.getSettings();
        settings.setBuiltInZoomControls(true);
        settings.setJavaScriptEnabled(true);
        wvView.addJavascriptInterface(new WebAppInterface(this), "Android");
        wvView.setWebViewClient(new WebViewClient());
        wvView.loadUrl(url);
    }
}
 class WebAppInterface {
    private Context mContext;

    /**
     * Instantiate the interface and set the context
     */
    WebAppInterface(Context c) {
        mContext = c;
    }

    /**
     * Show a toast from the web page
     */
    @JavascriptInterface
    public void showToast(String toast) {
        Toast.makeText(mContext, toast, Toast.LENGTH_LONG).show();
    }
}