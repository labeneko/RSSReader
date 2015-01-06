package com.example.takahiro_tsuno.rssreader;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;


public class ContentActivity extends Activity {

    private static final String EXTRA_KEY_CONTENT = "extra_key_content";

    private ProgressBar progressBar;

    public static void startActivity(Context context, RssContent rssContent){
        Intent intent = new Intent(context, ContentActivity.class);
        intent.putExtra(EXTRA_KEY_CONTENT, rssContent);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        progressBar = (ProgressBar) findViewById(R.id.content_progress_bar);

        RssContent rssContent = getIntent().getParcelableExtra(EXTRA_KEY_CONTENT);

        ActionBar actionBar = getActionBar();
        actionBar.setTitle(rssContent.title);
        actionBar.setDisplayHomeAsUpEnabled(true);

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

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                finish();

                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
