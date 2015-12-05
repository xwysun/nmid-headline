
package cn.edu.cqupt.nmid.headline.support.repository.headline.bean;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TeacherList {

    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("getInfo")
    @Expose
    private List<GetInfo> getInfo = new ArrayList<GetInfo>();

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

    /**
     * 
     * @return
     *     The getInfo
     */
    public List<GetInfo> getGetInfo() {
        return getInfo;
    }

    /**
     * 
     * @param getInfo
     *     The getInfo
     */
    public void setGetInfo(List<GetInfo> getInfo) {
        this.getInfo = getInfo;
    }

}
