/**
 * 
 */
package com.mlsc.common.exception;

import com.mlsc.yifeiwang.sms.model.MessageBean;

import java.util.List;

/**
 * 业务上的异常
 * @author zhugl
 * 
 */
public class WasteBusinessException extends Exception {

    private static final long serialVersionUID = 1L;
       
    private String exceptionType = "1";  //异常种类  （"1":普通的业务异常，2:参数异常等,以后可以进行扩展）默认是普通的业务异常
    
    private MessageBean messageBean;  //异常信息
    
    private List<MessageBean> messageBeans;  //异常信息
    
    /**
     * 构造方法，
     */
    public WasteBusinessException() {
    }
    
    /**
     * 构造方法
     * 
     */
    public WasteBusinessException(String exceptionType, MessageBean messageBean) {
        this.exceptionType = exceptionType;
        this.messageBean = messageBean;
    }
    /**
     * 构造方法
     * 
     */
    public WasteBusinessException(MessageBean messageBean) {
        this.messageBean = messageBean;
    }
    
    /**
     * 构造方法
     * 
     */
    public WasteBusinessException(String exceptionType, List<MessageBean> messageBeans) {
    	this.exceptionType = exceptionType;
    	this.messageBeans = messageBeans;
    }
    /**
     * 构造方法
     * 
     */
    public WasteBusinessException(List<MessageBean> messageBeans) {
        this.messageBeans = messageBeans;
    }

	public String getExceptionType() {
		return exceptionType;
	}

	public void setExceptionType(String exceptionType) {
		this.exceptionType = exceptionType;
	}

	public MessageBean getMessageBean() {
		return messageBean;
	}

	public void setMessageBean(MessageBean messageBean) {
		this.messageBean = messageBean;
	}

	public List<MessageBean> getMessageBeans() {
		return messageBeans;
	}

	public void setMessageBeans(List<MessageBean> messageBeans) {
		this.messageBeans = messageBeans;
	}
}
