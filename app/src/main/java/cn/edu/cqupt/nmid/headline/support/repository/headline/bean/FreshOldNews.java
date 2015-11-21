
package cn.edu.cqupt.nmid.headline.support.repository.headline.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FreshOldNews {

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

}
