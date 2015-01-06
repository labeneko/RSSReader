package com.example.takahiro_tsuno.rssreader;


import android.content.Context;
import android.net.http.AndroidHttpClient;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RssApi {
    public static List<RssContent> getRss(Context context) throws IOException, XmlPullParserException{

        List<RssContent> rssContentList = new ArrayList<RssContent>();

        List<String> urlList = new ArrayList<String>();
        urlList.add("http://rss.rssad.jp/rss/itmnews/2.0/news_venture.xml");
        urlList.add("http://rss.rssad.jp/rss/itmenterprise/2.0/enterprise.xml");
        urlList.add("http://rss.rssad.jp/rss/itmnews/2.0/news_web20.xml");

        Collections.shuffle(urlList);
        HttpGet httpGet = new HttpGet(urlList.get(0));

        // USER AGENTあとで調べる
        AndroidHttpClient client = AndroidHttpClient.newInstance("RSS sample", context);

        try{
            HttpResponse response = client.execute(httpGet);
            HttpEntity httpEntity = response.getEntity();
            String stringContent = EntityUtils.toString(httpEntity, "UTF-8");

            // ここから先、XMLのパースをしている
            XmlPullParserFactory xmlPullParserFactory = XmlPullParserFactory.newInstance();
            xmlPullParserFactory.setNamespaceAware(true); //?

            XmlPullParser xmlPullParser = xmlPullParserFactory.newPullParser();
            xmlPullParser.setInput(new StringReader(stringContent));

            RssContent rssContent = null;
            String tag = null;

            int event = xmlPullParser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {
                switch (event) {
                    case XmlPullParser.START_TAG:
                        tag = xmlPullParser.getName();
                        if (tag.equals("item")) {
                            rssContent = new RssContent();
                        }
                        break;
                    case XmlPullParser.TEXT:
                        if (rssContent != null) {
                            String text = xmlPullParser.getText();
                            // 空白データは対象外
                            if (text.trim().length() != 0) {
                                if (tag.equals("title")) {
                                    rssContent.setTitle(text);
                                } else if (tag.equals("link")) {
                                    rssContent.setUrl(text);
                                } else if (tag.equals("description")) {
                                    rssContent.setDescription(text.replaceAll("<.+?>", ""));
                                }
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        tag = xmlPullParser.getName();
                        if (tag.equals("item")) {
                            // ListViewに反映するためのArrayAdapterに追加
                            rssContentList.add(rssContent);
                        }
                        break;
                }
                event = xmlPullParser.next();
            }

        }catch (HttpResponseException e){
            System.out.println(e);
        }finally {
            client.close();
        }

        return rssContentList;
    }
}
