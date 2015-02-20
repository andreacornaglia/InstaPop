package com.example.oliveiras.instapop;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class PhotosActivity extends ActionBarActivity {

    public static final String CLIENT_ID = "36038c51e70a43eca1e974e41c2a8c69";
    private ArrayList<InstagramPhoto> photos;
    private InstagramPhotosAdapter aPhotos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);
        photos = new ArrayList<>();
        //we need to create the adapter linking it to the source
        aPhotos = new InstagramPhotosAdapter(this, photos);
        //Find the Listview from the layout
        ListView lvPhotos = (ListView) findViewById(R.id.lvPhotos);
        //Set the adapter binding it to the ListView
        lvPhotos.setAdapter(aPhotos);
        //Fetch the photos
        fetchPopularPhotos();
    }

    public void fetchPopularPhotos(){
        String url = "https://api.instagram.com/v1/media/popular?client_id=" +CLIENT_ID;
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, null, new JsonHttpResponseHandler(){

            //onsuccess (work)
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                //super.onSuccess(statusCode, headers, response);
                //Log.i("DEBUG", response.toString());
                JSONArray photosJSON = null;
                try {
                    photosJSON = response.getJSONArray("data"); //array of items
                    //now iterate the array to get each variable data
                    for(int i=0; i< photosJSON.length();i++) {
                        //get the JSON object at that position in the array
                        JSONObject photoJSON = photosJSON.getJSONObject(i);
                        InstagramPhoto photo = new InstagramPhoto();
                        //- Author of the Photo: {“data” => [x] =>”user” => “username”}
                        photo.username = photoJSON.getJSONObject("user").getString("username");
                        // Caption: {“data” => [x] =>”caption”=>”text”}
                        if (photoJSON.optJSONObject("caption") != null) {
                            photo.caption = photoJSON.getJSONObject("caption").getString("text");
                        }
                        //- URL: {“data” => [x] =>”images” => “standard_resolution” => “url”}
                        photo.imageURL = photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getString("url");
                        //likes count
                        photo.likesCount = photoJSON.getJSONObject("likes").getInt("count");
                        //user photo
                        photo.userURL = photoJSON.getJSONObject("user").getString("profile_picture");
                        photos.add(photo);

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                };

                //callback
                aPhotos.notifyDataSetChanged();
            }

            //on failure
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable){

            };

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_photos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
