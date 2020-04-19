package me.jsbn.lobstersreader;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import androidx.room.Room;

public class SavedActivity extends AppCompatActivity {

    ListView postsListView;
    ArrayList<LobstersPost> postsList;
    AppDatabase db;
    ArticleAdapter articleAdapter;
    ArrayList<LobstersPost> savedPosts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "bookmarkedPosts").allowMainThreadQueries().build();
        savedPosts = (ArrayList<LobstersPost>) db.lobstersPostDao().getAll();

        postsListView = (ListView) findViewById(R.id.postsListView);
        postsList = new ArrayList<>();
        articleAdapter = new ArticleAdapter(this, savedPosts);
        postsListView.setAdapter(articleAdapter);

    }

}
