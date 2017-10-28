package in.techdrop.tejas.postparsing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

  Button btnPost,btnSearchImages,btnVolleyJson;

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);

      btnPost = (Button) findViewById(R.id.posts);
      btnSearchImages = (Button) findViewById(R.id.btnImgParsing);
      btnVolleyJson = (Button) findViewById(R.id.btnVolleyJson);



      btnSearchImages.setOnClickListener(this);
      btnPost.setOnClickListener(this);
      btnVolleyJson.setOnClickListener(this);


  }

    @Override
    public void onClick(View v)
    {
        if (btnPost == v)
        {
          Intent i = new Intent(getApplicationContext(),ViewPosts.class);
          startActivity(i);
        }
        if (btnSearchImages == v)
        {
          Intent i = new Intent(getApplicationContext(),SearchImages.class);
          startActivity(i);
        }
        if(btnVolleyJson == v)
        {
          Intent i = new Intent(getApplicationContext(),VolleyJson.class);
          startActivity(i);
        }
    }
}
