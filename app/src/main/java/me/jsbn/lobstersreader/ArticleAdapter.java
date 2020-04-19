package me.jsbn.lobstersreader;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * ArrayAdapter to display Lobste.rs posts.
 */

public class ArticleAdapter extends ArrayAdapter<LobstersPost> {

    private Context context;
    private List<LobstersPost> postsList = new ArrayList<>();

    /**
     * Initialize a new ArticleAdapter.
     * @param _context Application context.
     * @param articleList ArrayList of articles to display to the user.
     */

    public ArticleAdapter(Context _context, ArrayList<LobstersPost> articleList) {
        super(_context, 0, articleList);
        context = _context;
        postsList = articleList;
    }

    /**
     * Creates a View for a single item in the list.
     * @param position The position of the item in the list.
     * @param convertView Old view to reuse, if possible
     * @param parent The parent to attach this View to.
     * @return The View created.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItem = convertView;
        if (listItem == null)
            listItem = LayoutInflater.from(context).inflate(R.layout.lobsters_post, parent, false);

        LobstersPost currentPost = postsList.get(position);

        // Get the author's profile picture. Use a placeholder if it can't be fetched.
        ImageView userProfilePictureImageView = (ImageView) listItem.findViewById(R.id.userProfilePictureImageView);
        Picasso.get().load("https://lobste.rs/avatars/" + currentPost.getAuthor().substring(0, currentPost.getAuthor().indexOf("@")) + "-32.png").placeholder(R.drawable.ic_launcher_background).error(R.drawable.ic_launcher_foreground).into(userProfilePictureImageView);

        URL currentPostUrl;
        String baseUrl;

        // Extract the base URL from the link in the post.
        try {
            currentPostUrl = new URL(currentPost.link);
            baseUrl = currentPostUrl.getProtocol() + "://" + currentPostUrl.getHost();
        } catch (MalformedURLException e) {
            currentPostUrl = null;
            baseUrl = "http://example.com";
        }

        // Format the title and set the title TextView.
        String postLinkString = "<a href='" + currentPost.getLink() + "'>" + currentPost.getTitle() + "</a>" + " (" + baseUrl + ")";
        TextView postTitleTextView = (TextView) listItem.findViewById(R.id.postTitleTextView);
        postTitleTextView.setText(Html.fromHtml(postLinkString));
        postTitleTextView.setMovementMethod(LinkMovementMethod.getInstance());

        LinearLayout categoriesLayout = listItem.findViewById(R.id.postTagsLayout);

        // Hack? Fixes bug with scrolling up and down where the app can't keep track of the tags in the posts.
        categoriesLayout.removeAllViewsInLayout();

        // Create a new tappable TextView for each category on the post.
         for (String category : currentPost.getCategories()) {
            TextView categoryTextView = new TextView(categoriesLayout.getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 10, 20, 30);
            categoryTextView.setLayoutParams(params);
            categoryTextView.setText(category);
            categoryTextView.setTextSize(14);
            categoryTextView.setPadding(20, 10, 20, 10);
            categoryTextView.setBackground(ContextCompat.getDrawable(context, R.drawable.back));
            categoryTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), MainActivity.class);
                    intent.putExtra("me.jsbn.lobstersreader.CATEGORY", category);
                    getContext().startActivity(intent);
                }
            });
            categoriesLayout.addView(categoryTextView);
        }

        // Set the author TextView
        TextView postAuthorTextView = (TextView) listItem.findViewById(R.id.postAuthorTextView);
        postAuthorTextView.setText("  " + currentPost.getAuthor().substring(0, currentPost.getAuthor().indexOf("@")));

        // Set the comments link TextView
        TextView postCommentsTextView = (TextView) listItem.findViewById(R.id.postCommentsTextView);
        String commentsLinkString = "<a href='" + currentPost.getComments() + "'>Comments</a>";
        postCommentsTextView.setText(Html.fromHtml(commentsLinkString));
        postCommentsTextView.setMovementMethod(LinkMovementMethod.getInstance());

        return listItem;
    }

}
