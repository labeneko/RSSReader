package com.example.takahiro_tsuno.rssreader;

import java.util.List;

public class RssContentList {

    public List<RssContent> rssContentList;


    public RssContentList(List<RssContent> _rssContentList){
        rssContentList = _rssContentList;
    }

    public List<RssContent> getRssContentList() {
        return rssContentList;
    }
}
