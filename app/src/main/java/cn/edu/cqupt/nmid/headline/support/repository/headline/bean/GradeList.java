
package cn.edu.cqupt.nmid.headline.support.repository.headline.bean;

import java.lang.*;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class GradeList {

    @SerializedName("classes")
    @Expose
    private List<Class> classes = new ArrayList<Class>();
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("grades")
    @Expose
    private List<String> grades = new ArrayList<String>();

    /**
     * 
     * @return
     *     The classes
     */
    public List<Class> getClasses() {
        return classes;
    }

    /**
     * 
     * @param classes
     *     The classes
     */
    public void setClasses(List<Class> classes) {
        this.classes = classes;
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

    /**
     * 
     * @return
     *     The grades
     */
    public List<String> getGrades() {
        return grades;
    }

    /**
     * 
     * @param grades
     *     The grades
     */
    public void setGrades(List<String> grades) {
        this.grades = grades;
    }

}
