package in.techdrop.tejas.postparsing.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import in.techdrop.tejas.postparsing.R;

/**
 * Created by Carl_johnson on 21-09-2017.
 */

public class ViewPostAdapter extends BaseAdapter {

  private static LayoutInflater inflater = null;
  public Resources res;
  public String post_id;
  private Activity activity;
  private ArrayList<HashMap<String ,String>> data;

  public ViewPostAdapter(Activity a, ArrayList d, Resources resLoc)
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
      convertView = inflater.inflate(R.layout.list_posts,null);
      holder = new ViewHolder();

      holder.id = (TextView) convertView.findViewById(R.id.id);
      holder.title = (TextView) convertView.findViewById(R.id.title);
      holder.body = (TextView) convertView.findViewById(R.id.body);

    }
    else
    {
      holder = (ViewHolder) convertView.getTag();

      if(data.size()<=0)
      {
        holder.id.setText("No data");
               /*Intent f = new Intent(activity.getApplication(), NoData.class);
               activity.startActivity(f);
*/
      }
      else
      {
        holder = (ViewHolder) convertView.getTag();
      }
    }

    holder.id.setText(data.get(position).get("id"));
    holder.title.setText(data.get(position).get("title"));
    holder.body.setText(data.get(position).get("body"));

    convertView.setTag(holder);

    return convertView;
  }


  public static class ViewHolder
  {

    public TextView id ,title, body;

  }

}
