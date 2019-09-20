package com.satria.authenticguards.agv3mvvm.utils;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.satria.authenticguards.R;

public class PrivaciEndService extends AppCompatActivity {
    public String URL_NEWS = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privaci_end_service);
        URL_NEWS = getIntent().getStringExtra("EXTRA_SESSION_ID");
        WebView webView = (WebView) findViewById(R.id.NewsWebview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.clearCache(true);
        webView.loadUrl(URL_NEWS);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ProgressBar mProgressBar = (ProgressBar) findViewById(R.id.progressProfile);
                mProgressBar.setVisibility(ProgressBar.INVISIBLE);
            }
        }, 3000);
    }
}
