
package cn.edu.cqupt.nmid.headline.support.repository.headline.bean;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class SendCode {

    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("addMsg")
    @Expose
    private AddMsg addMsg;

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
     *     The addMsg
     */
    public AddMsg getAddMsg() {
        return addMsg;
    }

    /**
     * 
     * @param addMsg
     *     The addMsg
     */
    public void setAddMsg(AddMsg addMsg) {
        this.addMsg = addMsg;
    }

}
