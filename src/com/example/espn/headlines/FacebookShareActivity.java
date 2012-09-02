package com.example.espn.headlines;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Picture;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

public class FacebookShareActivity extends Activity {

	private static final String APP_ID = "410400315689956";
	private static final String[] PERMISSIONS = new String[] {"publish_stream"};

	private static final String TOKEN = "access_token";
        private static final String EXPIRES = "expires_in";
        private static final String KEY = "facebook-credentials";

	private Facebook facebook;
	private String messageToPost;
	private String linkToPost;
	private String imageURLToPost;
	private String captionToPost;
	private String descriptionToPost;
	private String nameToPost;
	
	public static String headline;
	public static String href;

	public boolean saveCredentials(Facebook facebook) {
        	Editor editor = getApplicationContext().getSharedPreferences(KEY, Context.MODE_PRIVATE).edit();
        	editor.putString(TOKEN, facebook.getAccessToken());
        	editor.putLong(EXPIRES, facebook.getAccessExpires());
        	return editor.commit();
    	}

    	public boolean restoreCredentials(Facebook facebook) {
        	SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(KEY, Context.MODE_PRIVATE);
        	facebook.setAccessToken(sharedPreferences.getString(TOKEN, null));
        	facebook.setAccessExpires(sharedPreferences.getLong(EXPIRES, 0));
        	return facebook.isSessionValid();
    	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		facebook = new Facebook(APP_ID);
		restoreCredentials(facebook);

		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.facebook_dialog);

		String facebookMessage = getIntent().getStringExtra("facebookMessage");
		if (facebookMessage == null){
			facebookMessage = "Test wall post";
		}
		linkToPost = href;
		messageToPost = headline;
		imageURLToPost = "http://a.espncdn.com/i/apis/attribution/espn-red_50.png";
		nameToPost = headline;
		captionToPost = "This text is the caption";
		descriptionToPost = "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.";
	}

	public void doNotShare(View button){
		finish();
	}
	public void share(View button){
		if (! facebook.isSessionValid()) {
			loginAndPostToWall();
		}
		else {
			postToWall(messageToPost, linkToPost, imageURLToPost, nameToPost, captionToPost, descriptionToPost);
		}
	}

	public void loginAndPostToWall(){
		 facebook.authorize(this, PERMISSIONS, Facebook.FORCE_DIALOG_AUTH, new LoginDialogListener());
		 
	}

	public void postToWall(String message, String link, String image, String name, String caption, String description){
		Bundle parameters = new Bundle();
                parameters.putString("message", message);
                parameters.putString("picture", image);
                parameters.putString("link", link);
                parameters.putString("name", name);
                parameters.putString("caption", caption);
                parameters.putString("description", description);
                try {
        	        facebook.request("me");
			String response = facebook.request("me/feed", parameters, "POST");
			Log.d("Tests", "got response: " + response);
			if (response == null || response.equals("") ||
			        response.equals("false")) {
				showToast("Blank response.");
			}
			else {
				showToast("Message posted to your facebook wall!");
			}
			finish();
		} catch (Exception e) {
			showToast("Failed to post to wall!");
			e.printStackTrace();
			finish();
		}
	}

	public void getBirthdays() throws MalformedURLException, IOException {
		Bundle bundle = new Bundle();
		String graphpath = "me/friends";
		bundle.putString("fields", "first_name");
		String response = facebook.request(graphpath, bundle, "GET");
		Log.d("Tests", "got response: " + response);
	}
	
	public void getPosts() throws MalformedURLException, IOException {
		Bundle bundle = new Bundle();
		String graphpath = "me/feed";
		bundle.putString("name", "test_album");
		bundle.putString("message", "message for the test album");
		String response = facebook.request(graphpath, bundle, "GET");
		Log.d("Tests", "got response: " + response);
	}
	
	public void getUserID() throws MalformedURLException, IOException {
		Bundle bundle = new Bundle();
		String graphpath = "me/friends";
		bundle.putString("fields", "id");
		String response = facebook.request(graphpath, bundle, "GET");
		Log.d("Tests", "got response: " + response);
	}
	
	public void getFavoriteAthletes() throws MalformedURLException, IOException {
		Bundle bundle = new Bundle();
		String graphpath = "me/friends";
		bundle.putString("fields", "favorite_athletes");
		String response = facebook.request(graphpath, bundle, "GET");
		Log.d("Tests", "got response: " + response);
	}
	
	public void getFavoriteTeams() throws MalformedURLException, IOException {
		Bundle bundle = new Bundle();
		String graphpath = "me/friends";
		bundle.putString("fields", "favorite_teams");
		String response = facebook.request(graphpath, bundle, "GET");
		Log.d("Tests", "got response: " + response);
	}
	
	class LoginDialogListener implements com.example.espn.headlines.Facebook.DialogListener {
	    public void onComplete(Bundle values) {
	    	saveCredentials(facebook);
	    	if (messageToPost != null){
			postToWall(messageToPost, linkToPost, imageURLToPost, nameToPost, captionToPost, descriptionToPost);
		}
	    }
	    public void onFacebookError(FacebookError error) {
	    	showToast("Authentication with Facebook failed!");
	        finish();
	    }
	    public void onError(DialogError error) {
	    	showToast("Authentication with Facebook failed!");
	        finish();
	    }
	    public void onCancel() {
	    	showToast("Authentication with Facebook cancelled!");
	        finish();
	    }
	}

	private void showToast(String message){
		Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
	}
}