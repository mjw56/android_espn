package com.example.espn.headlines;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class AndroidParseJSON extends ListActivity {

	// url to make request
	private static String url = "http://api.espn.com/v1/sports/news/headlines/top?apikey=bxesq8b3qq5gf7cuew3nkw69";
	 
	// JSON Nodes
	private static final String TAG_HEADLINES = "headlines";
	private static final String TAG_HEADLINE = "headline";
	private static final String TAG_LINKS = "links";
	private static final String TAG_WEB = "web";
	private static final String TAG_HREF = "href";
	 
	// headlines JSONArray
	JSONArray headlines = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		// Hashmap for ListView
		ArrayList<HashMap<String, String>> headlineList = new ArrayList<HashMap<String, String>>();

		// Creating JSON Parser instance
		JSONParse jParser = new JSONParse();

		// getting JSON string from URL
		JSONObject json = jParser.getJSONFromUrl(url);

		try {
			// Getting Array of Headlines
			headlines = json.getJSONArray(TAG_HEADLINES);
			
	        // looping through All Headlines
	        for(int i = 0; i < headlines.length(); i++){
	            JSONObject h = headlines.getJSONObject(i);
	     
	            // Storing each json item in variable
	            String headline = h.getString(TAG_HEADLINE);
	     
	            // Phone number is agin JSON Object
	            JSONObject links = h.getJSONObject(TAG_LINKS);
	            JSONObject web = links.getJSONObject(TAG_WEB);
	            String href = web.getString(TAG_HREF);
				
				// creating new HashMap
				HashMap<String, String> map = new HashMap<String, String>();
				
				// adding each child node to HashMap key => value
				map.put(TAG_HEADLINE, headline);
				map.put(TAG_HREF, href);

				// adding HashList to ArrayList
				headlineList.add(map);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		
		/**
		 * Updating parsed JSON data into ListView
		 * */
		ListAdapter adapter = new SimpleAdapter(this, headlineList,
				R.layout.list_item,
				new String[] { TAG_HEADLINE, TAG_HREF}, new int[] {
						R.id.headline, R.id.href});

		setListAdapter(adapter);

		// selecting single ListView item
		ListView lv = getListView();

		// Launching new screen on Selecting Single ListItem
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// getting values from selected ListItem
				String headline = ((TextView) view.findViewById(R.id.headline)).getText().toString();
				String href = ((TextView) view.findViewById(R.id.href)).getText().toString();
				
				// Starting new intent
				Intent in = new Intent(getApplicationContext(), ListHeadlines.class);
				in.putExtra(TAG_HEADLINE, headline);
				in.putExtra(TAG_HREF, href);
				startActivity(in);
		}
		});
	}
}