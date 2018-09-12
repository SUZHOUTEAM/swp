/**
 * 
 */
package com.mlsc.common.exception;

import com.mlsc.yifeiwang.sms.model.ParamterMessageBean;

import java.util.List;

/**
 * 业务上的异常
 * @author zhugl
 * 
 */
public class ParamterException extends Exception {

    private static final long serialVersionUID = 1L;
       
    private ParamterMessageBean messageBean;  //异常信息
    
    private List<ParamterMessageBean> messageBeans;  //异常信息
    
    /**
     * 构造方法，
     */
    public ParamterException() {
    }
    
    /**
     * 构造方法
     * 
     */
    public ParamterException(ParamterMessageBean messageBean) {
        this.messageBean = messageBean;
    }
    
    /**
     * 构造方法
     * 
     */
    public ParamterException(List<ParamterMessageBean> messageBeans) {
        this.messageBeans = messageBeans;
    }

    /**
     * @return the messageBean
     */
    public ParamterMessageBean getMessageBean() {
        return messageBean;
    }

    /**
     * @param messageBean the messageBean to set
     */
    public void setMessageBean(ParamterMessageBean messageBean) {
        this.messageBean = messageBean;
    }

    /**
     * @return the messageBeans
     */
    public List<ParamterMessageBean> getMessageBeans() {
        return messageBeans;
    }

    /**
     * @param messageBeans the messageBeans to set
     */
    public void setMessageBeans(List<ParamterMessageBean> messageBeans) {
        this.messageBeans = messageBeans;
    }
}
