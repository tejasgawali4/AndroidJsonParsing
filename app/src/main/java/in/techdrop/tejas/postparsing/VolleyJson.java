package in.techdrop.tejas.postparsing;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import in.techdrop.tejas.postparsing.Adapters.RecycleAdapter;


public class VolleyJson extends AppCompatActivity
{
  RequestQueue rq;
  private String url = "https://pixabay.com/api/?key=6402041-cac20cf45ffc5e653cd6ca161&q=product&image_type=photo&pretty=true&per_page=20";
  ProgressDialog progressDialog; //a list to store all the products
  List<ProductList> productList;
  RecyclerView recyclerView;
  RecycleAdapter adapter;

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_volley_json);

    //getting the recyclerview from xml
    recyclerView = (RecyclerView) findViewById(R.id.recycleView);
    recyclerView.setHasFixedSize(true);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));

    //initializing the productlist
    productList = new ArrayList<>();

    progressDialog = new ProgressDialog(this);
    volleyJsonObjectRequest(url);
  }

  public void volleyJsonObjectRequest(String url)
  {

    progressDialog.setMessage("Loading...");
    progressDialog.show();

    // prepare the Request
    JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
      new Response.Listener<JSONObject>()
      {
        @Override
        public void onResponse(JSONObject response)
        {
            // display response
            Log.d("Response", response.toString());
            progressDialog.hide();

          try
          {
              JSONArray jsonArray = response.getJSONArray("hits");
              Log.d("Array ", String.valueOf(jsonArray));

              for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject product = jsonArray.getJSONObject(i);
                //adding the product to product list
                productList.add(new ProductList(
                  product.getString("user_id"),
                  product.getString("user"),
                  product.getString("userImageURL"),
                  product.getString("previewURL"),
                  product.getString("views"),
                  product.getString("likes")
                ));
              }

            //creating adapter object and setting it to recyclerview
            adapter = new RecycleAdapter(productList , getApplicationContext());
            recyclerView.setAdapter(adapter);
          }
          catch (JSONException e)
          {
              e.printStackTrace();
          }
        }
      },
      new Response.ErrorListener()
      {
        @Override
        public void onErrorResponse(VolleyError error)
        {
            VolleyLog.d("Error.Response" + error.getMessage());
            Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
            progressDialog.hide();
        }
      }
    );

    rq = Volley.newRequestQueue(this);
    rq.add(getRequest);
  }
}
