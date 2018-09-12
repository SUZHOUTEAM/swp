package com.mlsc.common.exception;

import com.mlsc.epdp.common.exception.BaseException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by zhanghj on 2017/7/12.
 */
public class BusinessException extends BaseException{
    private static final long serialVersionUID = -6120891019912973064L;

    public static final Map<Integer, String> CODE_MESSAGE_MAPPING = new HashMap<Integer, String>();
    private int errorCode;

    public BusinessException() {
    }

    public BusinessException(int errorCode) {
        super(CODE_MESSAGE_MAPPING.get(Integer.valueOf(errorCode)));
        this.errorCode = errorCode;
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

    public BusinessException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public BusinessException(int errorCode, Throwable cause) {
        super(CODE_MESSAGE_MAPPING.get(Integer.valueOf(errorCode)), cause);
        this.errorCode = errorCode;
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(int errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return CODE_MESSAGE_MAPPING.get(Integer.valueOf(this.errorCode));
    }

    public static void initCodeMessageMapping(Map<String, String> codeMessageMapping) {
        if(codeMessageMapping != null) {
            Iterator var2 = codeMessageMapping.keySet().iterator();

            while(var2.hasNext()) {
                String errorCode = (String)var2.next();
                CODE_MESSAGE_MAPPING.put(Integer.valueOf(errorCode), codeMessageMapping.get(errorCode));
            }

        }
    }
}
