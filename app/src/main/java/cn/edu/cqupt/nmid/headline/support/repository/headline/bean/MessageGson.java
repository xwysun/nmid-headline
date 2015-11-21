
package cn.edu.cqupt.nmid.headline.support.repository.headline.bean;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MessageGson {

    @SerializedName("recMsg")
    @Expose
    private List<RecMsg> recMsg = new ArrayList<RecMsg>();
    @SerializedName("code")
    @Expose
    private Integer code;

    /**
     * 
     * @return
     *     The recMsg
     */
    public List<RecMsg> getRecMsg() {
        return recMsg;
    }

    /**
     * 
     * @param recMsg
     *     The recMsg
     */
    public void setRecMsg(List<RecMsg> recMsg) {
        this.recMsg = recMsg;
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
