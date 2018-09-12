/**
 *
 */
package com.mlsc.yifeiwang.codedirectory.model;

import java.io.Serializable;

/**
 * @author hou
 */
public class CodeVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String type_code;  //字典类型编码
    private String type_name; //字典类型名称
    private String information;
    private String status;

    /**
     * @ return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }


    /**
     * @ return the type_name
     */
    public String getType_name() {
        return type_name;
    }

    /**
     * @param type_name the type_name to set
     */
    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    /**
     * @ return the information
     */
    public String getInformation() {
        return information;
    }

    /**
     * @param information the information to set
     */
    public void setInformation(String information) {
        this.information = information;
    }

    /**
     * @ return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }


    /**
     * @return the type_code
     */
    public String getType_code() {
        return type_code;
    }

    /**
     * @param type_code the type_code to set
     */
    public void setType_code(String type_code) {
        this.type_code = type_code;
    }


}
