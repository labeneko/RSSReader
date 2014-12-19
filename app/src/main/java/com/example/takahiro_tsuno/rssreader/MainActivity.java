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


                ListView listView = (ListView) findViewById(R.id.list_main);

                // 読み込み処理
                ContentAdapter contentAdapter = new ContentAdapter(MainActivity.this, result.rssContentList);
                listView.setAdapter(contentAdapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                        ListView listView = (ListView) parent;
                        // クリックされたアイテムを取得します
                        RssContent rssContent = (RssContent) listView.getItemAtPosition(position);

                        // ContentActivityに飛びます
                        Intent intent = new Intent(MainActivity.this, ContentActivity.class);
                        intent.putExtra("rss_content", rssContent);
                        startActivity(intent);

                        // アイテムをToastで表示します
                        //Toast.makeText(MainActivity.this, item, Toast.LENGTH_LONG).show();
                    }
                });
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

        new RssLoadTask(this).execute();
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
        ListView listView = (ListView) findViewById(R.id.list_main);
        listView.setAdapter(null);
        new RssLoadTask(this).execute();
    }
}
