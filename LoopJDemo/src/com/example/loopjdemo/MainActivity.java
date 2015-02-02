package com.example.loopjdemo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.Header;
import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.impl.cookie.BasicClientCookie2;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;

public class MainActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// testClient();
		getPublicTimeline();		
		
		
		CookieStore cookieStore = new BasicCookieStore();
		BasicClientCookie2 cookie = new BasicClientCookie2("AUTH_TOKEN", "MY_TOKEN");
		cookie.setVersion(1);
		cookie.setDomain("my.domain.com");
		cookie.setPath("/");
		cookieStore.addCookie(cookie);
		
	}

	public void testClient() {
		AsyncHttpClient client = new AsyncHttpClient();
		client.get("http://www.dantri.com.vn", new AsyncHttpResponseHandler() {
			@Override
			public void onStart() {
				// called before request is started
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					byte[] response) {
				// called when response HTTP status is "200 OK"
				if (response != null) {
					Log.d("Trien", "0");
					Log.d("Trien", response.toString());
				} else
					Log.d("Trien", "null");
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					byte[] errorResponse, Throwable e) {
				// called when response HTTP status is "4XX" (eg. 401, 403, 404)
				if (errorResponse != null) {
					Log.d("Trien", "1");
					Log.d("Trien", errorResponse.toString());
				} else
					Log.d("Trien", "null1");
			}

			@Override
			public void onRetry(int retryNo) {
				// called when request is retried
			}

		});
	}

	public void getPublicTimeline() {
		MyRestClient.get("statuses/public_timeline.json", null,
				new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(int statusCode, Header[] headers,
							JSONObject response) {
						// If the response is JSONObject instead of expected
						// JSONArray
					}

					@Override
					public void onSuccess(int statusCode, Header[] headers,
							JSONArray timeline) {
						try {
							// Pull out the first event on the public timeline
							JSONObject firstEvent = null;
							firstEvent = (JSONObject) timeline.get(0);

							String tweetText = null;
							tweetText = firstEvent.getString("text");
							// Do something with the response
							Log.d("Trien", "response:" + tweetText);
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}

					@Override
					public void onFailure(int statusCode, Header[] headers,
							Throwable throwable, JSONObject errorResponse) {
						Log.d("Trien", "response:" + errorResponse.toString());
					}
				});
	}

	public void testUsingParams() {
		RequestParams params = new RequestParams();
		params.put("username", "james");
		params.put("password", "123456");
		params.put("email", "my@email.com");
		try {
			params.put("profile_picture", new File("pic.jpg"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // Upload a File
		// params.put("profile_picture2", someInputStream); // Upload an
		// InputStream
		// params.put("profile_picture3", new ByteArrayInputStream(someBytes));
		// // Upload some bytes

		Map<String, String> map = new HashMap<String, String>();
		map.put("first_name", "James");
		map.put("last_name", "Smith");
		params.put("user", map); // url params:
									// "user[first_name]=James&user[last_name]=Smith"

		Set<String> set = new HashSet<String>(); // unordered collection
		set.add("music");
		set.add("art");
		params.put("like", set); // url params: "like=music&like=art"

		List<String> list = new ArrayList<String>(); // Ordered collection
		list.add("Java");
		list.add("C");
		params.put("languages", list); // url params:
										// "languages[]=Java&languages[]=C"

		String[] colors = { "blue", "yellow" }; // Ordered collection
		params.put("colors", colors); // url params:
										// "colors[]=blue&colors[]=yellow"

		List<Map<String, String>> listOfMaps = new ArrayList<Map<String, String>>();
		Map<String, String> user1 = new HashMap<String, String>();
		user1.put("age", "30");
		user1.put("gender", "male");
		Map<String, String> user2 = new HashMap<String, String>();
		user2.put("age", "25");
		user2.put("gender", "female");
		listOfMaps.add(user1);
		listOfMaps.add(user2);
		params.put("users", listOfMaps); // url params:
											// "users[][age]=30&users[][gender]=male&users[][age]=25&users[][gender]=female"

		AsyncHttpClient client = new AsyncHttpClient();
		client.post("http://myendpoint.com", params,
				new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(int statusCode, Header[] headers,
							JSONArray response) {
						// TODO Auto-generated method stub
						super.onSuccess(statusCode, headers, response);
					}

					@Override
					public void onFailure(int statusCode, Header[] headers,
							String responseString, Throwable throwable) {
						// TODO Auto-generated method stub
						super.onFailure(statusCode, headers, responseString,
								throwable);
					}
				});
	}

	public void testCookies(){
		SyncHttpClient client = new SyncHttpClient();
		PersistentCookieStore myCookieStore = new PersistentCookieStore(this);
		client.setCookieStore(myCookieStore);
		
		BasicClientCookie newCookie = new BasicClientCookie("cookiesare", "awesome");
		newCookie.setVersion(1);
		newCookie.setDomain("mydomain.com");
		newCookie.setPath("/");
		myCookieStore.addCookie(newCookie);
		
	}
}
