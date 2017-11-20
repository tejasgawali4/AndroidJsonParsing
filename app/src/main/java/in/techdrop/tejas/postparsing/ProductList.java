package in.techdrop.tejas.postparsing;

/**
 * Created by Carl_johnson on 17-10-2017.
 */

public class ProductList
{
  private String id;
  private String user;
  private String userImage;
  private String img;
  private String views;
  private String likes;


  public ProductList(String user_id, String user, String userImageURL, String previewURL, String views, String likes) {
    this.id = user_id;
    this.user = user;
    this.userImage = userImageURL;
    this.img = previewURL;
    this.views = views;
    this.likes = likes;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getUser() {
    return user;
  }

  public void setUser(String user) {
    this.user = user;
  }

  public String getUserImage() {
    return userImage;
  }

  public void setUserImage(String userImage) {
    this.userImage = userImage;
  }

  public String getImg() {
    return img;
  }

  public void setImg(String img) {
    this.img = img;
  }

  public String getViews() {
    return views;
  }

  public void setViews(String views) {
    this.views = views;
  }

  public String getLikes() {
    return likes;
  }

  public void setLikes(String likes) {
    this.likes = likes;
  }
}
