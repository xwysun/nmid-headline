
package cn.edu.cqupt.nmid.headline.support.repository.headline.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RecMsg {

    @SerializedName("message_pid")
    @Expose
    private Integer messagePid;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("start_time")
    @Expose
    private String startTime;
    @SerializedName("end_time")
    @Expose
    private String endTime;
    @SerializedName("class_number")
    @Expose
    private String classNumber;
    @SerializedName("teacher_pid")
    @Expose
    private Integer teacherPid;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("name")
    @Expose
    private String name;

    /**
     * 
     * @return
     *     The messagePid
     */
    public Integer getMessagePid() {
        return messagePid;
    }

    /**
     * 
     * @param messagePid
     *     The message_pid
     */
    public void setMessagePid(Integer messagePid) {
        this.messagePid = messagePid;
    }

    /**
     * 
     * @return
     *     The content
     */
    public String getContent() {
        return content;
    }

    /**
     * 
     * @param content
     *     The content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 
     * @return
     *     The startTime
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * 
     * @param startTime
     *     The start_time
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     * 
     * @return
     *     The endTime
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * 
     * @param endTime
     *     The end_time
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    /**
     * 
     * @return
     *     The classNumber
     */
    public String getClassNumber() {
        return classNumber;
    }

    /**
     * 
     * @param classNumber
     *     The class_number
     */
    public void setClassNumber(String classNumber) {
        this.classNumber = classNumber;
    }

    /**
     * 
     * @return
     *     The teacherPid
     */
    public Integer getTeacherPid() {
        return teacherPid;
    }

    /**
     * 
     * @param teacherPid
     *     The teacher_pid
     */
    public void setTeacherPid(Integer teacherPid) {
        this.teacherPid = teacherPid;
    }

    /**
     * 
     * @return
     *     The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * 
     * @param title
     *     The title
     */
    public void setTitle(String title) {
        this.title = title;
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

}
