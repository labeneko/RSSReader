package com.example.takahiro_tsuno.rssreader.RssFeed;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class Rss {
    @Element(name="channel")
    public Channel channel;
}
