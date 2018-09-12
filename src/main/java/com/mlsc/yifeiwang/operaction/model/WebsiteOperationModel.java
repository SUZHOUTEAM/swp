package com.mlsc.yifeiwang.operaction.model;

import com.mlsc.yifeiwang.operaction.entity.WebsiteOperation;
import com.mlsc.yifeiwang.operaction.entity.WebsiteOpertionDisposalEnterprise;
import com.mlsc.yifeiwang.operaction.entity.WebsiteOutsourcingDisposalEnterprise;

import java.util.List;

/**
 * Created by user on 2017/11/29.
 */
public class WebsiteOperationModel extends WebsiteOperation {
    private int totalEntCount;
    private int readEntCount;
//    private List<WebsiteOperationContacts> operationContactsList;
    private List<WebsiteOpertionDisposalEnterprise> disposalEnterpriseList;
    private List<WebsiteOutsourcingDisposalEnterprise> outsourcingDisposalEnterpriseList;


    public int getTotalEntCount() {
        return totalEntCount;
    }

    public void setTotalEntCount(int totalEntCount) {
        this.totalEntCount = totalEntCount;
    }

    public int getReadEntCount() {
        return readEntCount;
    }

    public void setReadEntCount(int readEntCount) {
        this.readEntCount = readEntCount;
    }

//    public List<WebsiteOperationContacts> getOperationContactsList() {
//        return operationContactsList;
//    }
//
//    public void setOperationContactsList(List<WebsiteOperationContacts> operationContactsList) {
//        this.operationContactsList = operationContactsList;
//    }

    public List<WebsiteOpertionDisposalEnterprise> getDisposalEnterpriseList() {
        return disposalEnterpriseList;
    }

    public void setDisposalEnterpriseList(List<WebsiteOpertionDisposalEnterprise> disposalEnterpriseList) {
        this.disposalEnterpriseList = disposalEnterpriseList;
    }

    public List<WebsiteOutsourcingDisposalEnterprise> getOutsourcingDisposalEnterpriseList() {
        return outsourcingDisposalEnterpriseList;
    }

    public void setOutsourcingDisposalEnterpriseList(List<WebsiteOutsourcingDisposalEnterprise> outsourcingDisposalEnterpriseList) {
        this.outsourcingDisposalEnterpriseList = outsourcingDisposalEnterpriseList;
    }
}
