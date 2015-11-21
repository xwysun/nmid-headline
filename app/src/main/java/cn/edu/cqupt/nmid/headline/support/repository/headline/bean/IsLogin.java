
package cn.edu.cqupt.nmid.headline.support.repository.headline.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IsLogin {

    @SerializedName("login")
    @Expose
    private Boolean login;
    @SerializedName("code")
    @Expose
    private Integer code;

    /**
     * 
     * @return
     *     The login
     */
    public Boolean getLogin() {
        return login;
    }

    /**
     * 
     * @param login
     *     The login
     */
    public void setLogin(Boolean login) {
        this.login = login;
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
