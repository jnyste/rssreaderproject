package me.jsbn.lobstersreader;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;

import androidx.room.Room;

/**
 * Main activity for Lobster Reader, containing the main UI view.
 */
public class MainActivity extends AppCompatActivity {

    ListView postsListView;
    ArrayList<LobstersPost> postsList;
    ArticleAdapter articleAdapter;
    LobstersRssReader rssReader;
    AppDatabase db;

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

        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "lobstersPosts").allowMainThreadQueries().build();

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
     * Create the menu with the BOOKMARKS button.
     * @param menu Menu to inflate.
     * @return True or false depending on if inflating the menu succeeded.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Handle the bookmarks button being tapped.
     * @param item The options item selected
     * @return True or false depending on if the action succeeded.
     */
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
                List<LobstersPost> hiddenPosts = db.lobstersPostDao().getAllHidden();

                for (LobstersPost p : rssReader.getPosts(tag)) {
                    if (!(hiddenPosts.contains(p)))
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
