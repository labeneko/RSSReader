package com.example.takahiro_tsuno.rssreader;

import android.content.Context;
import android.os.AsyncTask;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.List;

public class RssApiLoadTask extends AsyncTask<String, Void, RssContentList> {
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
    protected RssContentList doInBackground(String... params){
        try{
            List<RssContent> rssContentList = RssApi.getRss(context);
            return new RssContentList(rssContentList);
        } catch (XmlPullParserException e){
            exception = e;
        } catch (IOException e){
            exception = e;
        }
        return  null;
    }
}
