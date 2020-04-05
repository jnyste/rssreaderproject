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
    ArrayList<LobstersPost> postsList;
    ArticleAdapter articleAdapter;
    LobstersRssReader rssReader;

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

                rssReader = new LobstersRssReader();
                for (LobstersPost p : rssReader.getPosts("")) {
                    postsList.add(p);
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
        swipeRefreshLayout.setRefreshing(true);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        swipeRefreshLayout.setRefreshing(false);
    }

}
