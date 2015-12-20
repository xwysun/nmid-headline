
package cn.edu.cqupt.nmid.headline.support.repository.image.bean;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

public class Datum extends Model {

    @SerializedName("nickname")
    @Column(name = "nickname")
    @Expose
    private String nickname;
    @SerializedName("deviceinfo")
    @Column(name = "deviceinfo")
    @Expose
    private String deviceinfo;
    @SerializedName("uploadtime")
    @Column(name = "uploadtime")
    @Expose
    private String uploadtime;
    @SerializedName("count_like")
    @Column(name = "count_like")
    @Expose
    private Integer countLike;
    @SerializedName("avatar")
    @Column(name = "avatar")
    @Expose
    private String avatar;
    @SerializedName("haveClickLike")
    @Expose
    private Boolean haveClickLike;
    @SerializedName("idmemeber")
    @Column(name = "idmember")
    @Expose
    private Integer idmemeber;
    @SerializedName("imageurl")
    @Column(name = "imageurl")
    @Expose
    private String imageurl;
    @SerializedName("previrousurl")
    @Column(name = "previrousurl")
    @Expose
    private String previrousurl;

    boolean ishaveClick=false;


    public boolean isHaveClickLike() {
        return ishaveClick;
    }

    public void setIsLike(boolean isLike) {
        this.ishaveClick = isLike;
    }

    /**
     * 
     * @return
     *     The nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * 
     * @param nickname
     *     The nickname
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * 
     * @return
     *     The deviceinfo
     */
    public String getDeviceinfo() {
        return deviceinfo;
    }

    /**
     * 
     * @param deviceinfo
     *     The deviceinfo
     */
    public void setDeviceinfo(String deviceinfo) {
        this.deviceinfo = deviceinfo;
    }

    /**
     * 
     * @return
     *     The uploadtime
     */
    public String getUploadtime() {
        return uploadtime;
    }

    /**
     * 
     * @param uploadtime
     *     The uploadtime
     */
    public void setUploadtime(String uploadtime) {
        this.uploadtime = uploadtime;
    }

    /**
     * 
     * @return
     *     The countLike
     */
    public Integer getCountLike() {
        return countLike;
    }

    /**
     * 
     * @param countLike
     *     The count_like
     */
    public void setCountLike(Integer countLike) {
        this.countLike = countLike;
    }

    /**
     * 
     * @return
     *     The avatar
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     * 
     * @param avatar
     *     The avatar
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    /**
     * 
     * @return
     *     The haveClickLike
     */
    public Boolean getHaveClickLike() {
        return haveClickLike;
    }

    /**
     * 
     * @param haveClickLike
     *     The haveClickLike
     */
    public void setHaveClickLike(Boolean haveClickLike) {
        this.haveClickLike = haveClickLike;
    }

    /**
     * 
     * @return
     *     The idmemeber
     */
    public Integer getIdmemeber() {
        return idmemeber;
    }

    /**
     * 
     * @param idmemeber
     *     The idmemeber
     */
    public void setIdmemeber(Integer idmemeber) {
        this.idmemeber = idmemeber;
    }

    /**
     * 
     * @return
     *     The imageurl
     */
    public String getImageurl() {
        return imageurl;
    }

    /**
     * 
     * @param imageurl
     *     The imageurl
     */
    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    /**
     * 
     * @return
     *     The previrousurl
     */
    public String getPrevirousurl() {
        return previrousurl;
    }

    /**
     * 
     * @param previrousurl
     *     The previrousurl
     */
    public void setPrevirousurl(String previrousurl) {
        this.previrousurl = previrousurl;
    }
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
