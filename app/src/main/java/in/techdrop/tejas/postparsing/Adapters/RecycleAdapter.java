package in.techdrop.tejas.postparsing.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import in.techdrop.tejas.postparsing.ProductList;
import in.techdrop.tejas.postparsing.R;

/**
 * Created by Carl_johnson on 17-10-2017.
 */

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ProductViewHolder>
{

  private Context context;
  private List<ProductList> productList;

  public RecycleAdapter(List<ProductList> productList, Context volleyJson) {
    this.productList = productList;
    this.context = volleyJson;
  }


  @Override
  public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
  {
    LayoutInflater inflater = LayoutInflater.from(context);
    View view = inflater.inflate(R.layout.product_list, null);
    return new ProductViewHolder(view);
  }

  @Override
  public void onBindViewHolder(ProductViewHolder holder, int position)
  {
    ProductList product = productList.get(position);

    holder.id.setText(product.getId());
    holder.username.setText(product.getUser());

    String profile = product.getUserImage();
    String MainImage = product.getImg();

    if (profile == null && MainImage == null)
    {
      System.out.println("Path is empty...");
    }
    else
    {
      if (MainImage.contains(".jpg") && profile.contains(".jpg"))
      {
        Picasso.with(context).load(profile).into(holder.profilepic);
        Picasso.with(context).load(MainImage).into(holder.imgMain);
      }
    }
    holder.Likes.setText(product.getLikes());
    holder.Views.setText(product.getViews());
  }

  @Override
  public int getItemCount() {
    return productList.size();
  }

  class ProductViewHolder extends RecyclerView.ViewHolder {

    TextView id ,username, Likes ,Views;
    ImageView profilepic,imgMain;


    public ProductViewHolder(View itemView)
    {
      super(itemView);

      id = (TextView) itemView.findViewById(R.id.img_id);
      username = (TextView) itemView.findViewById(R.id.txtUsername);
      profilepic = (ImageView) itemView.findViewById(R.id.imgProfile);
      imgMain = (ImageView) itemView.findViewById(R.id.imgMain);
      Likes = (TextView) itemView.findViewById(R.id.txtLikesCount);
      Views = (TextView) itemView.findViewById(R.id.txtViewNo);

    }
  }
}
