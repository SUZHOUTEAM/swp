package com.mlsc.waste.utils.datatable;

import java.util.Map;

public class FilterConditions {
    
    //过滤条件
    private String where = null;
    
    //过滤条件参数
    private Map<String,Object> paramMap = null;
    
    
    public FilterConditions(String where, Map<String, Object> paramMap) {
        super();
        this.where = where;
        this.paramMap = paramMap;
    }

    /**
     * @return the where
     */
    public String getWhere() {
        return where;
    }

    /**
     * @param where the where to set
     */
    public void setWhere(String where) {
        this.where = where;
    }

    /**
     * @return the paramMap
     */
    public Map<String, Object> getParamMap() {
        return paramMap;
    }

    /**
     * @param paramMap the paramMap to set
     */
    public void setParamMap(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }
}
