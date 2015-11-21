
package cn.edu.cqupt.nmid.headline.support.repository.headline.bean;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FreshNewList {

    @SerializedName("freshNews")
    @Expose
    private List<FreshNews> freshNews = new ArrayList<FreshNews>();
    @SerializedName("code")
    @Expose
    private Integer code;

    /**
     * 
     * @return
     *     The freshNews
     */
    public List<FreshNews> getFreshNews() {
        return freshNews;
    }

    /**
     * 
     * @param freshNews
     *     The freshNews
     */
    public void setFreshNews(List<FreshNews> freshNews) {
        this.freshNews = freshNews;
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
