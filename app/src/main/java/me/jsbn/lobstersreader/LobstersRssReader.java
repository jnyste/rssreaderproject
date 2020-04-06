package me.jsbn.lobstersreader;

import android.util.Log;
import android.util.Xml;
import org.xmlpull.v1.XmlPullParser;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class LobstersRssReader {

    XmlPullParser xmlParser;

    LobstersRssReader() {
        xmlParser = Xml.newPullParser();
    }

    public ArrayList<LobstersPost> getPosts(String tag) {

        InputStream data;

        if (tag.equals("")) {
            data = getFeedFromUrl("https://lobste.rs/rss");
        } else {
            data = getFeedFromUrl("https://lobste.rs/t/" + tag + ".rss");
        }

        ArrayList<LobstersPost> posts = new ArrayList<>();
        LobstersPost currentPost = null;
        String textContent = "";
        int xmlEventType;

        try {
            xmlParser.setInput(data, null);
            xmlEventType = xmlParser.getEventType();
        } catch (Exception e) {
            xmlEventType = XmlPullParser.END_DOCUMENT;
        }

        while (xmlEventType != XmlPullParser.END_DOCUMENT) {
            String xmlTagName = xmlParser.getName();
            switch (xmlEventType) {
                case XmlPullParser.START_TAG:
                    if (xmlTagName.equalsIgnoreCase("item")) {
                        currentPost = new LobstersPost();
                    }
                    break;
                case XmlPullParser.TEXT:
                    textContent = xmlParser.getText();
                    break;
                case XmlPullParser.END_TAG:
                    if (xmlTagName.equalsIgnoreCase("item")) {
                        posts.add(currentPost);
                    }
                    else if (xmlTagName.equalsIgnoreCase("title") && currentPost != null) {
                        currentPost.setTitle(textContent);
                    }
                    else if (xmlTagName.equalsIgnoreCase("link") && currentPost != null) {
                        currentPost.setLink(textContent);
                    }
                    else if (xmlTagName.equalsIgnoreCase("author")) {
                        currentPost.setAuthor(textContent);
                    }
                    else if (xmlTagName.equalsIgnoreCase("pubDate")) {
                        currentPost.setPubDate(textContent);
                    }
                    else if (xmlTagName.equalsIgnoreCase("comments")) {
                        currentPost.setComments(textContent);
                    }
                    else if (xmlTagName.equalsIgnoreCase("category")) {
                        currentPost.categories.add(textContent);
                    }
                    break;
                default:
                    break;
            }

            try {
                xmlEventType = xmlParser.next();
            } catch (Exception e) {}
        }

        return posts;

    }

    public InputStream getFeedFromUrl(String feedUrl) {
        try {
            URL url = new URL(feedUrl);
            return url.openStream();
        } catch (Exception e) {
            return null;
        }
    }

}
