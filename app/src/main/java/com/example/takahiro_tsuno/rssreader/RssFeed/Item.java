package com.example.takahiro_tsuno.rssreader.RssFeed;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;
@Root(name="item")
public class Item implements Serializable{
    @Element(name = "title")
    public String title;
    @Element(name = "link")
    public String link;
    @Element(name = "description")
    public String description;
}
