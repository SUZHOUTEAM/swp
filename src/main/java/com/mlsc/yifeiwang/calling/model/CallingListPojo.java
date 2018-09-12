package com.mlsc.yifeiwang.calling.model;

/**
 * 行业列表四级显示，封装一个POJO
 * @author Administrator
 *
 */
public class CallingListPojo{

    private String id;// 四级行业的id
    
    private String  fourthLevel;//四级行业
    
    private String  thirdLevel;//三级行业
    
    private String  secondLevel;//二级行业
    
    private String  firstLevel;//一级行业
    
    private String c_status;
    

    public String getC_status() {
        return c_status;
    }

    public void setC_status(String c_status) {
        this.c_status = c_status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFourthLevel() {
        return fourthLevel;
    }

    public void setFourthLevel(String fourthLevel) {
        this.fourthLevel = fourthLevel;
    }

    public String getThirdLevel() {
        return thirdLevel;
    }

    public void setThirdLevel(String thirdLevel) {
        this.thirdLevel = thirdLevel;
    }

    public String getSecondLevel() {
        return secondLevel;
    }

    public void setSecondLevel(String secondLevel) {
        this.secondLevel = secondLevel;
    }

    public String getFirstLevel() {
        return firstLevel;
    }

    public void setFirstLevel(String firstLevel) {
        this.firstLevel = firstLevel;
    }
}
