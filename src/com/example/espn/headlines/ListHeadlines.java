package com.example.espn.headlines;

import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.widget.TextView;

public class ListHeadlines  extends Activity {
	
	// JSON node keys
	private static final String TAG_HEADLINE = "headline";
	private static final String TAG_HREF = "href";

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_list_item);
        
        // getting intent data
        Intent in = getIntent();
        
        // Get JSON values from previous intent
        String headline = in.getStringExtra(TAG_HEADLINE);
        String href = in.getStringExtra(TAG_HREF);
        
        // Displaying all values on the screen
        TextView lblHeadline = (TextView) findViewById(R.id.headline_label);
        TextView lblHref = (TextView) findViewById(R.id.href_label);
        
        String href_text = "<a href='"+ href +"'>Read The Full Story</a>";
        
        lblHeadline.setText(headline);
        //lblHref.setText(href_text);
        
        lblHref.setText(Html.fromHtml(href_text));
        lblHref.setMovementMethod(LinkMovementMethod.getInstance());
    }
}