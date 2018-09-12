/**
 * 
 */
package com.mlsc.waste.licence.model;


public class OperationLicenceDetailExtend extends OperationLicenceDetail {

    private static final long serialVersionUID = 2821591994970094340L;
    
    private String waste_name;  //危废名称,当waste_name_id不存在时，保存用户填写的危废名称

    /**
     * @return the waste_name
     */
    public String getWaste_name() {
        return waste_name;
    }

    /**
     * @param waste_name the waste_name to set
     */
    public void setWaste_name(String waste_name) {
        this.waste_name = waste_name;
    }
}
