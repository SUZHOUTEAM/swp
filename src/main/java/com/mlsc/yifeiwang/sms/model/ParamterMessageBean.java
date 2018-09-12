/**
 * 
 */
package com.mlsc.yifeiwang.sms.model;




/**
 * 警告信息的实体
 * @author zhugl
 * 
 */

public class ParamterMessageBean {
    
    private String paramterName; //错误参数名称
    
    private String errorContent;  //错误信息
    
    /**
     * 构造方法，
     */
    public ParamterMessageBean() {
    }
    
    /**
     * 构造方法
     * 
     */
    public ParamterMessageBean(String paramterName, String errorContent) {
        this.paramterName = paramterName;
        this.errorContent = errorContent;
    }

    /**
     * @return the paramterName
     */
    public String getParamterName() {
        return paramterName;
    }

    /**
     * @param paramterName the paramterName to set
     */
    public void setParamterName(String paramterName) {
        this.paramterName = paramterName;
    }

    /**
     * @return the errorContent
     */
    public String getErrorContent() {
        return errorContent;
    }

    /**
     * @param errorContent the errorContent to set
     */
    public void setErrorContent(String errorContent) {
        this.errorContent = errorContent;
    }
}
