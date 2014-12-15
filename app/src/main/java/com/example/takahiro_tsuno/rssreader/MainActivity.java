package com.example.takahiro_tsuno.rssreader;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


public class MainActivity extends Activity {

    public class RssLoadTask extends RssApiLoadTask{
        public RssLoadTask(Context context){
            super(context);
        }

        @Override
        protected void onPostExecute(RssContentList result) {
            super.onPostExecute(result);

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

                        // 右側に遷移するアニメーション
                        overridePendingTransition(R.anim.left_leave, R.anim.left_enter);

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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
