
package cn.edu.cqupt.nmid.headline.support.repository.headline.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetInfo implements Parcelable {

    @SerializedName("resume")
    @Expose
    private String resume;
    @SerializedName("home_page")
    @Expose
    private String homePage;
    @SerializedName("teacherInfo_pid")
    @Expose
    private Integer teacherInfoPid;
    @SerializedName("img_url")
    @Expose
    private String imgUrl;
    @SerializedName("name")
    @Expose
    private String name;

    /**
     * 
     * @return
     *     The resume
     */
    public String getResume() {
        return resume;
    }

    /**
     * 
     * @param resume
     *     The resume
     */
    public void setResume(String resume) {
        this.resume = resume;
    }

    /**
     * 
     * @return
     *     The homePage
     */
    public String getHomePage() {
        return homePage;
    }

    /**
     * 
     * @param homePage
     *     The home_page
     */
    public void setHomePage(String homePage) {
        this.homePage = homePage;
    }

    /**
     * 
     * @return
     *     The teacherInfoPid
     */
    public Integer getTeacherInfoPid() {
        return teacherInfoPid;
    }

    /**
     * 
     * @param teacherInfoPid
     *     The teacherInfo_pid
     */
    public void setTeacherInfoPid(Integer teacherInfoPid) {
        this.teacherInfoPid = teacherInfoPid;
    }

    /**
     * 
     * @return
     *     The imgUrl
     */
    public String getImgUrl() {
        return imgUrl;
    }

    /**
     * 
     * @param imgUrl
     *     The img_url
     */
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    /**
     * 
     * @return
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.resume);
        dest.writeString(this.homePage);
        dest.writeValue(this.teacherInfoPid);
        dest.writeString(this.imgUrl);
        dest.writeString(this.name);
    }

    public GetInfo() {
    }

    private GetInfo(Parcel in) {
        this.resume = in.readString();
        this.homePage = in.readString();
        this.teacherInfoPid = (Integer) in.readValue(Integer.class.getClassLoader());
        this.imgUrl = in.readString();
        this.name = in.readString();
    }

    public static final Parcelable.Creator<GetInfo> CREATOR = new Parcelable.Creator<GetInfo>() {
        public GetInfo createFromParcel(Parcel source) {
            return new GetInfo(source);
        }

        public GetInfo[] newArray(int size) {
            return new GetInfo[size];
        }
    };
}
