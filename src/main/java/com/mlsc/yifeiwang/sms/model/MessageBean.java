/**
 * 
 */
package com.mlsc.yifeiwang.sms.model;




/**
 * 警告信息的实体
 * @author zhugl
 * 
 */
public class MessageBean {
    
    public static final String STATUS_SUCCESS = "success";
    
    public static final String STATUS_INFO = "info";
    
    public static final String STATUS_DANGER = "danger";
    
    public static final String NOTICE_TYPE_NOTIFY = "1";//通知形式
    
    public static final String NOTICE_TYPE_LAYER = "2";//通知形式

    private String errorType; //错误级别(info , warning ,error)
    
    private String noticeType; //错误级别(info , warning ,error)
    
    private String errorContent;  //异常信息
    
    /**
     * 构造方法，
     */
    public MessageBean() {
    }
    
    /**
     * 构造方法
     * 
     */
    public MessageBean(String errorType, String noticeType, String errorContent) {
        this.errorType = errorType;
        this.noticeType = noticeType;
        this.errorContent = errorContent;
    }

    /**
     * @return the errorType
     */
    public String getErrorType() {
        return errorType;
    }

    /**
     * @param errorType the errorType to set
     */
    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    /**
     * @return the noticeType
     */
    public String getNoticeType() {
        return noticeType;
    }

    /**
     * @param noticeType the noticeType to set
     */
    public void setNoticeType(String noticeType) {
        this.noticeType = noticeType;
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
