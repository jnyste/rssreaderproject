package me.jsbn.lobstersreader;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import com.prof.rssparser.Article;
import com.prof.rssparser.Channel;
import com.prof.rssparser.OnTaskCompleted;
import com.prof.rssparser.Parser;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {

    SwipeRefreshLayout swipeRefreshLayout;
    ListView postsListView;
    Parser rssParser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        postsListView = (ListView) findViewById(R.id.postsListView);
        rssParser = new Parser();

        rssParser.onFinish(new OnTaskCompleted() {
            @Override
            public void onTaskCompleted(@NotNull Channel channel) {
                channel.getArticles();
            }

            @Override
            public void onError(@NotNull Exception e) {
                Log.d("me.jsbn.debug", e.toString());
            }
        });

        rssParser.execute(getString(R.string.rss_feed_url));

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
