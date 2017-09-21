package in.techdrop.tejas.postparsing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

  Button btnPost;

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);

      btnPost = (Button) findViewById(R.id.posts);

    btnPost.setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        Intent i = new Intent(getApplicationContext(),ViewPosts.class);
        startActivity(i);
      }
    });
  }
}
