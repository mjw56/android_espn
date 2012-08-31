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
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class AndroidParseJSON extends ListActivity {

	// url to make request
	private static String headlinesURL = "http://api.espn.com/v1/sports/news/headlines/top?apikey=bxesq8b3qq5gf7cuew3nkw69";
	private static String mlbTeamsURL = "http://api.espn.com/v1/sports/baseball/mlb/teams?apikey=bxesq8b3qq5gf7cuew3nkw69";
	private static String nflTeamsURL = "http://api.espn.com/v1/sports/football/nfl/teams?apikey=bxesq8b3qq5gf7cuew3nkw69";
	private static String nbaTeamsURL = "http://api.espn.com/v1/sports/basketball/nba/teams?apikey=bxesq8b3qq5gf7cuew3nkw69";
	private static String nhlTeamsURL = "http://api.espn.com/v1/sports/hockey/nhl/teams?apikey=bxesq8b3qq5gf7cuew3nkw69";
	 
	// JSON Nodes
	private static final String TAG_HEADLINES = "headlines";
	private static final String TAG_HEADLINE = "headline";
	private static final String TAG_LINKS = "links";
	private static final String TAG_WEB = "web";
	private static final String TAG_HREF = "href";
	private static final String TAG_SPORTS = "sports";
	private static final String TAG_LEAGUES = "leagues";
	private static final String TAG_TEAMS = "teams";
	private static final String TAG_TEAM_NAME = "name";
	private static final String TAG_TEAM_LOCATION = "location";
	private static final String TAG_TEAM_ID = "id";
	private static final String TAG_TEAM_URL = "url";
	 
	// headlines JSONArray
	JSONArray headlines = null;
	
	//teams JSONArray
	JSONArray teams = null;
	
	// HashMaps to store our JSON data for ListView
	ArrayList<HashMap<String, String>> headlineList = new ArrayList<HashMap<String, String>>();
	ArrayList<HashMap<String, String>> teamList = new ArrayList<HashMap<String, String>>();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// Creating JSON Parser instance
		JSONParse jParser = new JSONParse();

		// getting JSON string from URL
		JSONObject headlinesJSON = jParser.getJSONFromUrl(headlinesURL);
		

		try {
			// Getting Array of Headlines
			headlines = headlinesJSON.getJSONArray(TAG_HEADLINES);
			
	        // looping through All Headlines
	        for(int i = 0; i < headlines.length(); i++){
	            JSONObject h = headlines.getJSONObject(i);
	     
	            // Storing each json item in variable
	            String headline = h.getString(TAG_HEADLINE);
	     
	            // Traverse down the JSON to extract the URL
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
        
		Button headlines_button = (Button) findViewById(R.id.latest_headlines);
  
		headlines_button.setOnClickListener(new View.OnClickListener() {

		@Override
		public void onClick(View view) {
			listHeadlines();
			}
		});
		
		Button mlb_team_news_button = (Button) findViewById(R.id.mlb_team_news);
		  
		mlb_team_news_button.setOnClickListener(new View.OnClickListener() {

		@Override
		public void onClick(View view) {
			try {
				listTeams(1);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
		});
		
		Button nfl_team_news_button = (Button) findViewById(R.id.nfl_team_news);
		  
		nfl_team_news_button.setOnClickListener(new View.OnClickListener() {

		@Override
		public void onClick(View view) {
			try {
				listTeams(2);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
		});
		
		Button nba_team_news_button = (Button) findViewById(R.id.nba_team_news);
		  
		nba_team_news_button.setOnClickListener(new View.OnClickListener() {

		@Override
		public void onClick(View view) {
			try {
				listTeams(3);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
		});
		
		Button nhl_team_news_button = (Button) findViewById(R.id.nhl_team_news);
		  
		nhl_team_news_button.setOnClickListener(new View.OnClickListener() {

		@Override
		public void onClick(View view) {
			try {
				listTeams(4);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
		});
	}
	
	public void listHeadlines() {
		
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
	
	public void listTeams(int sport) throws JSONException {
		
		JSONParse jParser = new JSONParse();
		JSONObject teamsJSON = null;

		switch(sport){
			case 1: teamsJSON = jParser.getJSONFromUrl(mlbTeamsURL);
					 break;
			case 2: teamsJSON = jParser.getJSONFromUrl(nflTeamsURL);
			         break;
			case 3: teamsJSON = jParser.getJSONFromUrl(nbaTeamsURL);
			         break;
			case 4: teamsJSON = jParser.getJSONFromUrl(nhlTeamsURL);
			         break;
		}
		
		JSONArray sports = teamsJSON.getJSONArray(TAG_SPORTS);
		//Getting Array of teams
		//teams = teamsJSON.getJSONArray(TAG_TEAMS);
		JSONObject sportsObj = sports.getJSONObject(0);
		JSONArray leagues = sportsObj.getJSONArray(TAG_LEAGUES);
		JSONObject leaguesObj = leagues.getJSONObject(0);
		JSONArray teams = leaguesObj.getJSONArray(TAG_TEAMS);
		
        
        // do the same looping for all the teams
        for(int i = 0; i < teams.length(); i++){
            JSONObject h = teams.getJSONObject(i);
     
            // Storing each json item in variable
            String name = h.getString(TAG_TEAM_NAME);
            String location = h.getString(TAG_TEAM_LOCATION);
            String id = h.getString(TAG_TEAM_ID);
     
            // Traverse down the JSON array to grab the team url
            JSONObject links = h.getJSONObject(TAG_LINKS);
            JSONObject web = links.getJSONObject(TAG_WEB);
            JSONObject webTeams = web.getJSONObject(TAG_TEAMS);
            String href = webTeams.getString(TAG_HREF);
			
			// creating new HashMap
			HashMap<String, String> map = new HashMap<String, String>();
			
			// adding each child node to HashMap key => value
			map.put(TAG_TEAM_LOCATION, location);
			map.put(TAG_TEAM_NAME, name);
			map.put(TAG_TEAM_ID, id);
			map.put(TAG_HREF, href);

			// adding HashList to ArrayList
			teamList.add(map);
		}
		
		/**
		 * Updating parsed JSON data into ListView
		 * */
		ListAdapter adapter = new SimpleAdapter(this, teamList,
				R.layout.list_team,
				new String[] { TAG_TEAM_LOCATION, TAG_TEAM_NAME}, new int[] {
						R.id.team_location, R.id.team_name});

		setListAdapter(adapter);

		// selecting single ListView item
		ListView exlv = getListView();

		// Launching new screen on Selecting Single ListItem
		exlv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// getting values from selected ListItem
				String team_location = ((TextView) view.findViewById(R.id.team_location)).getText().toString();
				String team_name = ((TextView) view.findViewById(R.id.team_name)).getText().toString();
				
				// Starting new intent
				Intent in = new Intent(getApplicationContext(), ListHeadlines.class);
				in.putExtra(TAG_TEAM_LOCATION, team_location);
				in.putExtra(TAG_TEAM_NAME, team_name);
				startActivity(in);
		}
		});
	}
}