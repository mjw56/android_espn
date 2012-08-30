package com.example.espn.headlines;

import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class ListHeadlines  extends Activity {
	
	// JSON node keys
	private static final String TAG_HEADLINE = "headline";
	private static final String TAG_HREF = "href";
	public static String headline;
	public static String href;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_list_item);
        
        // getting intent data
        Intent in = getIntent();
        
        // Get JSON values from previous intent
        headline = in.getStringExtra(TAG_HEADLINE);
        href = in.getStringExtra(TAG_HREF);
        
        // Displaying all values on the screen
        TextView lblHeadline = (TextView) findViewById(R.id.headline_label);
        TextView lblHref = (TextView) findViewById(R.id.href_label);
        
        String href_text = "<a href='"+ href +"'>Read The Full Story</a>";
        
        lblHeadline.setText(headline);
        //lblHref.setText(href_text);
        
        lblHref.setText(Html.fromHtml(href_text));
        lblHref.setMovementMethod(LinkMovementMethod.getInstance());
              
        Button button = (Button) findViewById(R.id.tweet_this);
        
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
              Intent intent = new Intent(ListHeadlines.this, TweetActivity.class);
              startActivity(intent);
              TweetActivity.headline = headline;
              TweetActivity.href = href;
              
            }
          });
        
        Button fb_button = (Button) findViewById(R.id.FacebookShareButton);
        
        fb_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
              Intent intent = new Intent(ListHeadlines.this, FacebookShareActivity.class);
              startActivity(intent);
            }

          });
        
    }
}