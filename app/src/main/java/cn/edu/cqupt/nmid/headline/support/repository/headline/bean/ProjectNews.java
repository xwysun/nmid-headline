
package cn.edu.cqupt.nmid.headline.support.repository.headline.bean;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProjectNews {

    @SerializedName("techList")
    @Expose
    private List<TechList> techList = new ArrayList<TechList>();
    @SerializedName("code")
    @Expose
    private Integer code;

    /**
     * 
     * @return
     *     The techList
     */
    public List<TechList> getTechList() {
        return techList;
    }

    /**
     * 
     * @param techList
     *     The techList
     */
    public void setTechList(List<TechList> techList) {
        this.techList = techList;
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
