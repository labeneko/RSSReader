package com.example.takahiro_tsuno.rssreader;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;


public class MainActivity extends Activity {

    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ContentAdapter contentAdapter;

    public class RssLoadTask extends RssApiLoadTask{
        public RssLoadTask(Context context){
            super(context);
        }

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(RssContentList result) {
            super.onPostExecute(result);
            progressBar.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);

            if(result != null){
                contentAdapter.addAll(result.rssContentList);
            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh);

        // リフレッシュ時の処理を書く
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                rssContentListRefresh();
            }
        });

        final ListView listView = (ListView) findViewById(R.id.list_main);


        // 読み込み処理
        contentAdapter = new ContentAdapter(MainActivity.this);
        listView.setAdapter(contentAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // クリックされたアイテムを取得します
                RssContent rssContent = contentAdapter.getItem(position);

                // ContentActivityに飛びます
                ContentActivity.startActivity(MainActivity.this, rssContent);

                // アイテムをToastで表示します
                //Toast.makeText(MainActivity.this, item, Toast.LENGTH_LONG).show();
            }
        });

        rssContentListRefresh();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch(id){
            case R.id.action_settings:
                return true;

            case R.id.action_reload:
                rssContentListRefresh();
        }

        return super.onOptionsItemSelected(item);
    }

    public void rssContentListRefresh(){
        progressBar.setVisibility(View.VISIBLE);
        contentAdapter.clear();
        new RssLoadTask(this).execute();
    }
}
