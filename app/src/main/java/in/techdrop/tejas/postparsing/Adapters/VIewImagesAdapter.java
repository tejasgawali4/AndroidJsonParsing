package in.techdrop.tejas.postparsing.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import in.techdrop.tejas.postparsing.R;

/**
 * Created by Carl_johnson on 22-09-2017.
 */

public class VIewImagesAdapter extends BaseAdapter {

  private static LayoutInflater inflater = null;
  public Resources res;
  public String post_id;
  private Activity activity;
  private ArrayList<HashMap<String ,String>> data;

  public VIewImagesAdapter(Activity a, ArrayList d, Resources resLoc)
  {
    activity = a;
    data = d;
    res = resLoc;

    inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
  }


  @Override
  public int getCount()
  {
    if(data.size()<= 0)
      return 1;
    return data.size();
  }

  @Override
  public Object getItem(int position) {
    return position;
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  public View getView(final int position, View convertView, ViewGroup parent) {

    ViewHolder holder;

    if(convertView == null)
    {
      convertView = inflater.inflate(R.layout.list_img_post,null);
      holder = new ViewHolder();

      holder.id = (TextView) convertView.findViewById(R.id.img_id);
      holder.username = (TextView) convertView.findViewById(R.id.txtUsername);
      holder.profilepic = (ImageView) convertView.findViewById(R.id.imgProfile);
      holder.imgMain = (ImageView) convertView.findViewById(R.id.imgMain);
      holder.Likes = (TextView) convertView.findViewById(R.id.txtLikesCount);
      holder.Views = (TextView) convertView.findViewById(R.id.txtViewNo);

    }
    else
    {
      holder = (ViewHolder) convertView.getTag();
    }

    holder.id.setText(data.get(position).get("user_id"));
    holder.username.setText(data.get(position).get("username"));

    String profile = data.get(position).get("profile_pic");
    String MainImage = data.get(position).get("img_url");

    if (profile == null && MainImage == null)
    {
        System.out.println("Path is empty...");
    }
    else
    {
      if (MainImage.contains(".jpg") && profile.contains(".jpg"))
      {
        Picasso.with(activity).load(profile).into(holder.profilepic);
        Picasso.with(activity).load(MainImage).into(holder.imgMain);
      }
    }

    holder.Likes.setText(data.get(position).get("likes"));
    holder.Views.setText(data.get(position).get("views"));

    convertView.setTag(holder);

    return convertView;
  }


  public static class ViewHolder
  {

    public TextView id ,username, Likes ,Views;
    public ImageView profilepic,imgMain;

  }
}
