package in.techdrop.tejas.postparsing;

import android.app.ProgressDialog;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import in.techdrop.tejas.postparsing.Adapters.ViewPostAdapter;
import in.techdrop.tejas.postparsing.util.Config;
import in.techdrop.tejas.postparsing.util.HttpConnection;

import static android.widget.Toast.LENGTH_LONG;

/**
 * Created by Carl_johnson on 21-09-2017.
 */

public class ViewPosts extends AppCompatActivity
{
  private ProgressDialog pDialog;
  ListView postListView;
  public ViewPosts CustomViewPost = null;
  public ArrayList<HashMap<String,String>> CustomListPostViewArray;
  ViewPostAdapter adapter;
  Resources res;
  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.viewposts);

    CustomViewPost = this;

    pDialog = new ProgressDialog(ViewPosts.this);

    CustomListPostViewArray = new ArrayList<>();

    res = getResources();

    postListView= (ListView) findViewById(R.id.viewpost_listview);

    new ViewPost().execute();
  }

  protected class ViewPost extends AsyncTask<Void , Void ,Void>
  {
    @Override
    protected void onPreExecute() {
      super.onPreExecute();
      // Showing progress dialog
      pDialog = new ProgressDialog(ViewPosts.this);
      pDialog.setMessage("Please wait...");
      pDialog.setCancelable(false);
      pDialog.show();

    }

    @Override
    protected Void doInBackground(Void... params)
    {
      HttpConnection sh = new HttpConnection();

      // Making a request to url and getting response
      String jsonStr = sh.sendGetRequest(Config.URL_VIEW_POSTS);

      Log.e("Response", "Response from url: " + jsonStr);

      if (jsonStr != null)
      {
        try
        {

          JSONArray result = new JSONArray(jsonStr);

          // looping through All Contacts
          for (int i = 0; i < result.length(); i++)
          {
            JSONObject jsonResponce = result.getJSONObject(i);

            String postid =jsonResponce.getString("id");
            String title = jsonResponce.getString("title");
            String body = jsonResponce.getString("body");

            System.out.println("\n id :- "+postid+"title :-"+title+"Body:-"+body);
            // tmp hash map for single contact
            HashMap<String, String> Post = new HashMap<>();

            // adding each child node to HashMap key => value
            Post.put(Config.KEY_POST_ID,postid);
            Post.put(Config.KEY_POST_TITLE, title);
            Post.put(Config.KEY_POST_BODY,body);

            /*Post.put(Config.KEY_CONTENT, content);*/

            // adding contact to contact list
            CustomListPostViewArray.add(Post);
          }
        } catch (final JSONException e) {
          // Log.e(TAG, "Json parsing error: " + e.getMessage());
          runOnUiThread(new Runnable() {
            @Override
            public void run() {
              Toast.makeText(getApplicationContext(),
                "Json parsing error: " + e.getMessage(),
                LENGTH_LONG)
                .show();
            }
          });
        }
      } else {
        //Log.e(TAG, "Couldn't get json from server.");
        runOnUiThread(new Runnable() {
          @Override
          public void run()
          {
//            Intent i = new Intent(ViewPosts.this,NoInternetConnection.class);
//            startActivity(i);
            //Toast.makeText(getApplicationContext(),"Couldn't get json from server. Check LogCat for possible errors!", LENGTH_LONG).show();
          }
        });
      }

      return null;
    }

    @Override
    protected void onPostExecute(Void aVoid)
    {
      super.onPostExecute(aVoid);
      if (pDialog.isShowing())
        pDialog.dismiss();

      adapter = new ViewPostAdapter(CustomViewPost, CustomListPostViewArray, res);

      postListView.setAdapter(adapter);
    }
  }

}
