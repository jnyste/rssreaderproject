package me.jsbn.lobstersreader;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {LobstersPost.class}, version = 1)

/**
 * Room database for storing bookmarked and hidden posts
 */
public abstract class AppDatabase extends RoomDatabase {
    public abstract LobstersPostDao lobstersPostDao();
}