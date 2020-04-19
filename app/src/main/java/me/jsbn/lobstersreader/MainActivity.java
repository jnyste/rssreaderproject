package me.jsbn.lobstersreader;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.room.Room;

public class MainActivity extends AppCompatActivity {

    ListView postsListView;
    ArrayList<LobstersPost> postsList;
    ArticleAdapter articleAdapter;
    LobstersRssReader rssReader;
    AppDatabase db;

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * Initialize the main activity and fetch posts, optionally given a tag whose posts to list.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        postsListView = (ListView) findViewById(R.id.postsListView);

        postsList = new ArrayList<>();
        articleAdapter = new ArticleAdapter(this, postsList);
        postsListView.setAdapter(articleAdapter);

        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "bookmarkedPosts").allowMainThreadQueries().build();

        // If onCreate was called from tapping a tag in the UI, fetch posts for a given tag
        if (getIntent().getExtras() != null) {
            fetchPosts(getIntent().getExtras().getString("me.jsbn.lobstersreader.CATEGORY"));

            setTitle("Lobste.rs - " + getIntent().getExtras().getString("me.jsbn.lobstersreader.CATEGORY"));
        } else {
            // Fetch all posts
            fetchPosts("");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.savedPostsButton) {
            Intent intent = new Intent(MainActivity.this, SavedActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
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
