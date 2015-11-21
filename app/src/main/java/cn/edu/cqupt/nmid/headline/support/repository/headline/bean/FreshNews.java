
package cn.edu.cqupt.nmid.headline.support.repository.headline.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.activeandroid.Model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FreshNews extends Model implements Parcelable {

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


    protected FreshNews(Parcel in) {
        newsPid=in.readInt();
        title = in.readString();
        time = in.readString();
        author = in.readString();
        type=in.readString();
    }
    public FreshNews(int idmember, String category) {
        this.newsPid = idmember;
        this.type = category;
    }
    public static final Creator<FreshNews> CREATOR = new Parcelable.Creator<FreshNews>() {
        @Override
        public FreshNews createFromParcel(Parcel in) {
            return new FreshNews(in);
        }

        @Override
        public FreshNews[] newArray(int size) {
            return new FreshNews[size];
        }
    };

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
     * The type
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @param type
     * The type
     */
    public void setType(String type) {
        this.type = type;
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.newsPid);
        dest.writeString(this.author);
        dest.writeString(this.title);
        dest.writeString(this.time);
        dest.writeString(this.type);
    }


}
