
package cn.edu.cqupt.nmid.headline.support.repository.headline.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import com.activeandroid.Model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FreshNews extends Model implements Parcelable{

    @SerializedName("news_pid")
    @Expose
    private Integer newsPid;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("author")
    @Expose
    private String author;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("image")
    @Expose
    private List<Image> image = new ArrayList<Image>();

    /**
     * 
     * @return
     *     The newsPid
     */
    public Integer getNewsPid() {
        return newsPid;
    }

    /**
     * 
     * @param newsPid
     *     The news_pid
     */
    public void setNewsPid(Integer newsPid) {
        this.newsPid = newsPid;
    }

    /**
     * 
     * @return
     *     The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * 
     * @param title
     *     The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 
     * @return
     *     The time
     */
    public String getTime() {
        return time;
    }

    /**
     * 
     * @param time
     *     The time
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * 
     * @return
     *     The author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * 
     * @param author
     *     The author
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * 
     * @return
     *     The type
     */
    public String getType() {
        return type;
    }

    /**
     * 
     * @param type
     *     The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 
     * @return
     *     The image
     */
    public List<Image> getImage() {
        return image;
    }

    /**
     * 
     * @param image
     *     The image
     */
    public void setImage(List<Image> image) {
        this.image = image;
    }


    public FreshNews(int idmember, String category) {
        this.newsPid = idmember;
        this.type = category;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.newsPid);
        dest.writeString(this.title);
        dest.writeString(this.time);
        dest.writeString(this.author);
        dest.writeString(this.type);
        dest.writeTypedList(image);
    }

    private FreshNews(Parcel in) {
        this.newsPid = (Integer) in.readValue(Integer.class.getClassLoader());
        this.title = in.readString();
        this.time = in.readString();
        this.author = in.readString();
        this.type = in.readString();
        in.readTypedList(image, Image.CREATOR);
    }

    public static final Creator<FreshNews> CREATOR = new Creator<FreshNews>() {
        public FreshNews createFromParcel(Parcel source) {
            return new FreshNews(source);
        }

        public FreshNews[] newArray(int size) {
            return new FreshNews[size];
        }
    };
}
