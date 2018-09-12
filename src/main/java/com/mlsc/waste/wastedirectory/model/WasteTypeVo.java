package com.mlsc.waste.wastedirectory.model;

import java.util.Comparator;
import java.util.List;

public class WasteTypeVo implements Comparator<WasteTypeVo> {

    private String id;
    private String code;
    private String description;

    List<Waste> wasteList = null;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Waste> getWasteList() {
		return wasteList;
	}

	public void setWasteList(List<Waste> wasteList) {
		this.wasteList = wasteList;
	}

	@Override
	public int compare(WasteTypeVo o1, WasteTypeVo o2) {
		if (o1.getCode().compareTo(o2.getCode()) > 0) {  
            return 1;  
        } else if (o1.getCode().compareTo(o2.getCode()) < 0) {  
            return -1;  
        }  
		return 0;
	}
    
    
	
}
