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

    /**
     * Initialize the main activity and fetch posts, optionally given a tag whose posts to list.
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        postsListView = (ListView) findViewById(R.id.postsListView);

        postsList = new ArrayList<>();
        articleAdapter = new ArticleAdapter(this, postsList);
        postsListView.setAdapter(articleAdapter);

        // If onCreate was called from tapping a tag in the UI, fetch posts for a given tag
        if (getIntent().getExtras() != null) {
            fetchPosts(getIntent().getExtras().getString("me.jsbn.lobstersreader.CATEGORY"));
            setTitle("Lobste.rs - " + getIntent().getExtras().getString("me.jsbn.lobstersreader.CATEGORY"));
        } else {
            // Fetch all posts
            fetchPosts("");
        }
    }

    /**
     * Create a new RSS reader instance and fetch the latest posts, and update the list adapter.
     * @param tag The tag to search for
     */

    public void fetchPosts(final String tag) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                rssReader = new LobstersRssReader();
                for (LobstersPost p : rssReader.getPosts(tag)) {
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
    }

}
