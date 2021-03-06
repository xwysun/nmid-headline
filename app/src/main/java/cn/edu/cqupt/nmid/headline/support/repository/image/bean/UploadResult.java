
package cn.edu.cqupt.nmid.headline.support.repository.image.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UploadResult {

    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("upload")
    @Expose
    private Upload upload;

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
     *     The upload
     */
    public Upload getUpload() {
        return upload;
    }

    /**
     * 
     * @param upload
     *     The upload
     */
    public void setUpload(Upload upload) {
        this.upload = upload;
    }

}
