package me.jsbn.lobstersreader;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    SwipeRefreshLayout swipeRefreshLayout;
    ListView postsListView;
    ArrayList<RssParser.Item> postsList;
    ArticleAdapter articleAdapter;
    RssParser rssParser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        postsListView = (ListView) findViewById(R.id.postsListView);

        postsList = new ArrayList<>();

       articleAdapter = new ArticleAdapter(this, postsList);
       postsListView.setAdapter(articleAdapter);

        new Thread(new Runnable() {
            @Override
            public void run() {
                RssParser.Item item = null;

                rssParser = new RssParser(getString(R.string.rss_feed_url));
                for (RssParser.Item i : rssParser.items) {
                    postsList.add(i);
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        articleAdapter.notifyDataSetChanged();
                    }
                });
            }
        }).start();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updatePosts();
            }
        });
    }

    public void updatePosts() {
        Log.d("me.jsbn.debug", "updatePosts() called!");

        swipeRefreshLayout.setRefreshing(true);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        swipeRefreshLayout.setRefreshing(false);

    }

}
