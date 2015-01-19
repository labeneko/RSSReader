package com.example.takahiro_tsuno.rssreader;


import android.content.Context;
import android.net.http.AndroidHttpClient;

import com.example.takahiro_tsuno.rssreader.RssFeed.Rss;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.simpleframework.xml.core.Persister;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RssApi {
    public static Rss getRss(Context context) throws IOException, XmlPullParserException{

        Rss rss = null;

        List<String> urlList = new ArrayList<String>();
        urlList.add("http://rss.rssad.jp/rss/itmnews/2.0/news_venture.xml");
        urlList.add("http://rss.rssad.jp/rss/itmenterprise/2.0/enterprise.xml");
        urlList.add("http://rss.rssad.jp/rss/itmnews/2.0/news_web20.xml");

        Collections.shuffle(urlList);
        HttpGet httpGet = new HttpGet(urlList.get(0));

        // USER AGENTあとで調べる
        AndroidHttpClient client = AndroidHttpClient.newInstance("RSS sample", context);

        Persister persister = new Persister();

        try{
            HttpResponse response = client.execute(httpGet);
            HttpEntity httpEntity = response.getEntity();
            String stringContent = EntityUtils.toString(httpEntity, "UTF-8");

            // ここから先、XMLのパースをしている
            rss = persister.read(Rss.class, stringContent, false);

        }catch (HttpResponseException e){
            System.out.println(e);
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            client.close();
        }

        return rss;
    }
}
