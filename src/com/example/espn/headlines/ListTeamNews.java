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

public class ListTeamNews  extends Activity {
	
	// JSON node keys
	private static final String TAG_TEAM_LOCATION = "location";
	private static final String TAG_TEAM_NAME = "name";
	private static final String TAG_HREF = "href";
	public static String location;
	public static String name;
	public static String href;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_list_team);
        
        // getting intent data
        Intent in = getIntent();
        
        // Get JSON values from previous intent
        location = in.getStringExtra(TAG_TEAM_LOCATION);
        name = in.getStringExtra(TAG_TEAM_NAME);
        href = in.getStringExtra(TAG_HREF);
        
        // Displaying all values on the screen
        TextView lblTeamLocation = (TextView) findViewById(R.id.location_label);
        TextView lblTeamName = (TextView) findViewById(R.id.name_label);
        TextView lblHref = (TextView) findViewById(R.id.href_label);
        
        String href_text = "<a href='"+ href +"'>Read The Full Story</a>";
        
        lblTeamLocation.setText(location);
        lblTeamName.setText(name);
        //lblHref.setText(href_text);
        
        lblHref.setText(Html.fromHtml(href_text));
        lblHref.setMovementMethod(LinkMovementMethod.getInstance());
              
        Button button = (Button) findViewById(R.id.tweet_this);
        
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
              Intent intent = new Intent(ListTeamNews.this, TweetActivity.class);
              startActivity(intent);
              TweetActivity.headline = "news";
              TweetActivity.href = href;
              
            }
          });
        
        Button fb_button = (Button) findViewById(R.id.FacebookShareButton);
        
        fb_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
              Intent intent = new Intent(ListTeamNews.this, FacebookShareActivity.class);
              startActivity(intent);
              FacebookShareActivity.headline = "news";
              FacebookShareActivity.href = href;
            }
          });      
    }
}