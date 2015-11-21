
package cn.edu.cqupt.nmid.headline.support.repository.headline.bean;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddMsg {

    @SerializedName("status")
    @Expose
    private Boolean status;

    /**
     * 
     * @return
     *     The status
     */
    public Boolean getStatus() {
        return status;
    }

    /**
     * 
     * @param status
     *     The status
     */
    public void setStatus(Boolean status) {
        this.status = status;
    }

}
