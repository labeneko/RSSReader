package com.example.takahiro_tsuno.rssreader;

import android.content.Context;
import android.os.AsyncTask;

import com.example.takahiro_tsuno.rssreader.RssFeed.Rss;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class RssApiLoadTask extends AsyncTask<String, Void, Rss> {
    private final Context context;
    Exception exception;

    public RssApiLoadTask(Context _context){
        context = _context;
    }

    /**
     * ...は可変引数
     * paramsがString型の配列で入ってくるとのこと
     * 呼び方はdoInBackground(param1,param2,param3 ...)
     */
    @Override
    protected Rss doInBackground(String... params){
        try{
            Rss rss = RssApi.getRss(context);
            return rss;
        } catch (XmlPullParserException e){
            exception = e;
        } catch (IOException e){
            exception = e;
        }
        return  null;
    }

    public Exception getException() {
        return exception;
    }
}
