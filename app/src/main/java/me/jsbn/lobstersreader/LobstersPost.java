package me.jsbn.lobstersreader;

import java.util.ArrayList;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

/**
 * Class representing a post on Lobste.rs.
 */
@Entity
@TypeConverters({MyTypeConverters.class})
public class LobstersPost {

    @PrimaryKey
    @NonNull
    String guid;
    String title;
    String link;
    String author;
    String pubDate;
    String comments;
    String description;
    int postState = 0; // 0 = Normal, 1 = Bookmarked, 2 = Hidden

    ArrayList<String> categories = new ArrayList<>();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<String> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<String> categories) {
        this.categories = categories;
    }

    public int getPostState() { return postState; }

    public void setPostState(int postState) { this.postState = postState; }

    /**
     * Two Lobste.rs posts are equal when their GUIDs match.
     * @param obj The post to compare with.
     * @return true if equal, false if not equal.
     */
    @Override
    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof LobstersPost))
            return false;
        return this.getGuid().equals(((LobstersPost) obj).getGuid());
    }
}
