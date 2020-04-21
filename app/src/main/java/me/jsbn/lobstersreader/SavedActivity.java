package me.jsbn.lobstersreader;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import androidx.room.Room;

/**
 * Activity representing the Bookmarks view.
 */
public class SavedActivity extends AppCompatActivity {

    ListView postsListView;
    ArrayList<LobstersPost> postsList;
    AppDatabase db;
    ArticleAdapter articleAdapter;
    ArrayList<LobstersPost> savedPosts;

    /**
     * Create the activity and load the user's bookmarks.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "lobstersPosts").allowMainThreadQueries().build();
        savedPosts = (ArrayList<LobstersPost>) db.lobstersPostDao().getAllBookmarked();

        postsListView = (ListView) findViewById(R.id.postsListView);
        postsList = new ArrayList<>();
        articleAdapter = new ArticleAdapter(this, savedPosts);
        postsListView.setAdapter(articleAdapter);

    }

}
