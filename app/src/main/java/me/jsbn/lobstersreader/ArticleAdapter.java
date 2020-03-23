package me.jsbn.lobstersreader;

import android.content.Context;
import android.media.Image;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ArticleAdapter extends ArrayAdapter<RssParser.Item> {

    private Context context;
    private List<RssParser.Item> postsList = new ArrayList<>();

    public ArticleAdapter(Context _context, ArrayList<RssParser.Item> articleList) {
        super(_context, 0, articleList);
        context = _context;
        postsList = articleList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null)
            listItem = LayoutInflater.from(context).inflate(R.layout.lobsters_post, parent, false);

        RssParser.Item currentPost = postsList.get(position);

        ImageView userProfilePictureImageView = (ImageView) listItem.findViewById(R.id.userProfilePictureImageView);
        Picasso.get().load("https://lobste.rs/avatars/" + currentPost.author.substring(0, currentPost.author.indexOf("@")) + "-32.png").placeholder(R.drawable.ic_launcher_background).error(R.drawable.ic_launcher_foreground).into(userProfilePictureImageView);

        String postLinkString = "<a href='" + currentPost.link + "'>" + currentPost.title + "</a>" + " (" + currentPost.link + ")";
        TextView postTitleTextView = (TextView) listItem.findViewById(R.id.postTitleTextView);
        postTitleTextView.setText(Html.fromHtml(postLinkString));
        postTitleTextView.setMovementMethod(LinkMovementMethod.getInstance());

        TextView postTagsTextView = (TextView) listItem.findViewById(R.id.postTagsTextView);
        postTagsTextView.setText(String.join(", ", currentPost.category));

        TextView postAuthorTextView = (TextView) listItem.findViewById(R.id.postAuthorTextView);
        postAuthorTextView.setText(currentPost.author.substring(0, currentPost.author.indexOf("@")));

        TextView postCommentsTextView = (TextView) listItem.findViewById(R.id.postCommentsTextView);
        String commentsLinkString = "<a href='" + currentPost.comments + "'>Comments</a>";
        postCommentsTextView.setText(Html.fromHtml(commentsLinkString));
        postCommentsTextView.setMovementMethod(LinkMovementMethod.getInstance());

        return listItem;
    }
}
