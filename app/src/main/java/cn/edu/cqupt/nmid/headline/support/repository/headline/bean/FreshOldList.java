
package cn.edu.cqupt.nmid.headline.support.repository.headline.bean;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FreshOldList {

    @SerializedName("freshOldNews")
    @Expose
    private List<FreshOldNews> freshOldNews = new ArrayList<FreshOldNews>();
    @SerializedName("code")
    @Expose
    private Integer code;

    /**
     * 
     * @return
     *     The freshOldNews
     */
    public List<FreshOldNews> getFreshOldNews() {
        return freshOldNews;
    }

    /**
     * 
     * @param freshOldNews
     *     The freshOldNews
     */
    public void setFreshOldNews(List<FreshOldNews> freshOldNews) {
        this.freshOldNews = freshOldNews;
    }

    /**
     * 
     * @return
     *     The code
     */
    public Integer getCode() {
        return code;
    }

    /**
     * 
     * @param code
     *     The code
     */
    public void setCode(Integer code) {
        this.code = code;
    }

}
