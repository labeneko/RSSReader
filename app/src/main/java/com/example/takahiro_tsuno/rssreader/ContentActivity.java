package com.example.takahiro_tsuno.rssreader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;


public class ContentActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content);

        Intent intent = getIntent();
        RssContent rssContent = (RssContent) intent.getExtras().get("rss_content");

        TextView textView = (TextView) findViewById(R.id.content_string);
        textView.setText(rssContent.title);

        WebView webView = (WebView) findViewById(R.id.content_website);
        webView.setWebViewClient(new WebViewClient(){
            // こうすることでブラウザが勝手に起動するのを回避
            // 後で調べる
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(rssContent.url);
    }
}
