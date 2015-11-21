
package cn.edu.cqupt.nmid.headline.support.repository.headline.bean;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Class {

    @SerializedName("grade0")
    @Expose
    private List<String> grade0 = new ArrayList<String>();
    @SerializedName("grade1")
    @Expose
    private List<String> grade1 = new ArrayList<String>();
    @SerializedName("grade2")
    @Expose
    private List<String> grade2 = new ArrayList<String>();
    @SerializedName("grade3")
    @Expose
    private List<String> grade3 = new ArrayList<String>();

    /**
     * 
     * @return
     *     The grade0
     */
    public List<String> getGrade0() {
        return grade0;
    }

    /**
     * 
     * @param grade0
     *     The grade0
     */
    public void setGrade0(List<String> grade0) {
        this.grade0 = grade0;
    }

    /**
     * 
     * @return
     *     The grade1
     */
    public List<String> getGrade1() {
        return grade1;
    }

    /**
     * 
     * @param grade1
     *     The grade1
     */
    public void setGrade1(List<String> grade1) {
        this.grade1 = grade1;
    }

    /**
     * 
     * @return
     *     The grade2
     */
    public List<String> getGrade2() {
        return grade2;
    }

    /**
     * 
     * @param grade2
     *     The grade2
     */
    public void setGrade2(List<String> grade2) {
        this.grade2 = grade2;
    }

    /**
     * 
     * @return
     *     The grade3
     */
    public List<String> getGrade3() {
        return grade3;
    }

    /**
     * 
     * @param grade3
     *     The grade3
     */
    public void setGrade3(List<String> grade3) {
        this.grade3 = grade3;
    }

}
