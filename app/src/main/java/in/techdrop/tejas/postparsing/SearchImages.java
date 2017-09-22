package in.techdrop.tejas.postparsing;

import android.app.ProgressDialog;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import in.techdrop.tejas.postparsing.Adapters.VIewImagesAdapter;
import in.techdrop.tejas.postparsing.util.Config;
import in.techdrop.tejas.postparsing.util.HttpConnection;

import static android.widget.Toast.LENGTH_LONG;

public class SearchImages extends AppCompatActivity {

  EditText SearchInput;
  Button btnSearch;
  private ProgressDialog pDialog;
  ListView ImageList;
  public SearchImages CustomSearchImages = null;
  public ArrayList<HashMap<String,String>> CustomListImagesArray;
  VIewImagesAdapter adapter;
  Resources res;
  String query;

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_search_images);
    SearchInput = (EditText) findViewById(R.id.editTextSearch);
    btnSearch = (Button) findViewById(R.id.btnImgParsing);

    CustomSearchImages = this;

    pDialog = new ProgressDialog(SearchImages.this);

    CustomListImagesArray = new ArrayList<>();

    res = getResources();

    ImageList= (ListView) findViewById(R.id.listViewImage);

  }

  void SearchImages(View v)
  {
      new ImageSearch().execute();
      Toast.makeText(getApplication(),"Searching",Toast.LENGTH_SHORT).show();
  }

  protected class ImageSearch extends AsyncTask<Void , Void ,Void>
  {
    @Override
    protected void onPreExecute() {
      super.onPreExecute();
      // Showing progress dialog
      pDialog = new ProgressDialog(SearchImages.this);
      pDialog.setMessage("Please wait...");
      pDialog.setCancelable(false);
      pDialog.show();

      query = SearchInput.getText().toString().trim();

      System.out.println("Query :- " +query);

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected Void doInBackground(Void... params)
    {
      HttpConnection sh = new HttpConnection();

      // Making a request to url and getting response
      String jsonStr = sh.sendGetRequest(Config.URL_VIEW_IMAGES+Config.KEY_PIXABAY+"&q="+query+"&image_type=photo&pretty=true&per_page=20");

      Log.e("Response", "Response from url: " + jsonStr);

      if (jsonStr != null)
      {
        try
        {

          JSONObject result = new JSONObject(jsonStr);

          //System.out.println("Json Objcet :- " + result);

          JSONArray jsonArray =  result.getJSONArray("hits");

          //System.out.println("Json Array :- " + jsonArray);



          // looping through All Contacts
          for (int i = 0; i <= jsonArray.length(); i++)
          {
            JSONObject jsonResponce = jsonArray.getJSONObject(i);

            ///System.out.println("Json Object in Array :- " + jsonResponce);

            String id =jsonResponce.getString("user_id");
            String user = jsonResponce.getString("user");
            String userImage = jsonResponce.getString("userImageURL");
            String img = jsonResponce.getString("previewURL");
            String views = jsonResponce.getString("views");
            String likes = jsonResponce.getString("likes");

            //System.out.println("\n id :- "+ id +"username :-"+ user +"profilePhoto:-"+ userImage + "Image :- " + img + " Views:- " + views + " Likes :- " + likes);
            // tmp hash map for single contact
            HashMap<String, String> Post = new HashMap<>();

            // adding each child node to HashMap key => value
            Post.put(Config.KEY_IMG_ID,id);
            Post.put(Config.KEY_USERNAME, user);
            Post.put(Config.KEY_PROFILE_IMG,userImage);
            Post.put(Config.KEY_IMG_URL,img);
            Post.put(Config.KEY_VIEWS,views);
            Post.put(Config.KEY_LIKES,likes);

            /*Post.put(Config.KEY_CONTENT, content);*/

            // adding contact to contact list
            CustomListImagesArray.add(Post);

            System.out.println(CustomListImagesArray);
          }

        } catch (final JSONException e)
        {
          // Log.e(TAG, "Json parsing error: " + e.getMessage());
          runOnUiThread(new Runnable()
          {
            @Override
            public void run()
            {
              //Toast.makeText(getApplicationContext(), "Json parsing error: " + e.getMessage(), LENGTH_LONG).show();
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
            Toast.makeText(getApplicationContext(),"Couldn't get json from server. Check LogCat for possible errors!", LENGTH_LONG).show();
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

      adapter = new VIewImagesAdapter(CustomSearchImages, CustomListImagesArray, res);

      ImageList.setAdapter(adapter);
    }
  }

}
