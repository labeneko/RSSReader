package com.example.takahiro_tsuno.rssreader.RssFeed;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;
@Root(name="channel")
public class Channel {
    @ElementList(entry="item", inline = true)
    public List<Item> itemList;
}
