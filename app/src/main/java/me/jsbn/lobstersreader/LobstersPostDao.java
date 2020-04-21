package me.jsbn.lobstersreader;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
/**
 * DAO representing database queries for Lobste.rs posts.
 */
public interface LobstersPostDao {
    @Query("SELECT * FROM LobstersPost")
    List<LobstersPost> getAll();

    @Insert
    void insertAll(LobstersPost... LobstersPosts);

    @Delete
    void delete(LobstersPost LobstersPost);

    @Query("DELETE FROM LobstersPost WHERE guid = :guid")
    abstract void deleteByGuid(String guid);

    @Query("SELECT * FROM LobstersPost WHERE postState = 1")
    abstract List<LobstersPost> getAllBookmarked();

    @Query("SELECT * FROM LobstersPost WHERE postState = 2")
    abstract List<LobstersPost> getAllHidden();

}