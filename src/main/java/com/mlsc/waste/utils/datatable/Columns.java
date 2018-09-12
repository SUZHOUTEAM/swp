package com.mlsc.waste.utils.datatable;

/**
 * 用于映射DataTable与服务器交互的数所列相关参数
 * 
 * @author dinghq
 *
 */
public class Columns {
      /**
      * 数据字段名
      */
    private String data;
      /**
      * 别名
      */
    private String name;
    
      /**
      * 是否支持查询
      */
    private boolean searchable;
    
      /**
      * 是否支持排序
      */
    private boolean orderable;
    
      /**
      * 查询内容
      */
    private String searchValue;
    
    /**
    * 未知
    */
    private String searchReges;
    
    

    public Columns(String data, String name, boolean searchable,
            boolean orderable, String searchValue, String searchReges) {
        super();
        this.data = data;
        this.name = name;
        this.searchable = searchable;
        this.orderable = orderable;
        this.searchValue = searchValue;
        this.searchReges = searchReges;
    }

    /**
     * @return the data
     */
    public String getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(String data) {
        this.data = data;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the searchable
     */
    public boolean isSearchable() {
        return searchable;
    }

    /**
     * @param searchable the searchable to set
     */
    public void setSearchable(boolean searchable) {
        this.searchable = searchable;
    }

    /**
     * @return the orderable
     */
    public boolean isOrderable() {
        return orderable;
    }

    /**
     * @param orderable the orderable to set
     */
    public void setOrderable(boolean orderable) {
        this.orderable = orderable;
    }

    /**
     * @return the searchValue
     */
    public String getSearchValue() {
        return searchValue;
    }

    /**
     * @param searchValue the searchValue to set
     */
    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }

    /**
     * @return the searchReges
     */
    public String getSearchReges() {
        return searchReges;
    }

    /**
     * @param searchReges the searchReges to set
     */
    public void setSearchReges(String searchReges) {
        this.searchReges = searchReges;
    }
}
