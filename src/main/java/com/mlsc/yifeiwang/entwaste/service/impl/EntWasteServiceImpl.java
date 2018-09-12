package com.mlsc.yifeiwang.entwaste.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mlsc.yifeiwang.entwaste.entity.EntWaste;
import com.mlsc.yifeiwang.waste.entity.WasteName;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.solr.SimpleSolr;
import com.mlsc.solr.model.WasteIndex;
import com.mlsc.solr.util.DocumentUtil;
import com.mlsc.waste.user.model.User;
import com.mlsc.waste.utils.Constant;
import com.mlsc.waste.utils.Util;
import com.mlsc.waste.wastedirectory.model.Waste;
import com.mlsc.waste.wastedirectory.service.WasteService;
import com.mlsc.yifeiwang.waste.service.IWasteNameService;
import com.mlsc.yifeiwang.entwaste.mapper.EntWasteMapper;
import com.mlsc.yifeiwang.entwaste.model.EntWasteModel;
import com.mlsc.yifeiwang.entwaste.model.EntWasteParams;
import com.mlsc.yifeiwang.entwaste.service.IEntWasteService;
import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.util.ClientUtils;
import org.apache.solr.common.SolrDocumentList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Caoyy
 * @since 2017-09-12
 */
@Service
public class EntWasteServiceImpl extends ServiceImpl<EntWasteMapper, EntWaste> implements IEntWasteService {
    private final static Logger logger = LoggerFactory.getLogger(EntWasteServiceImpl.class);

    @Autowired
    private IWasteNameService wasteNameService;

    @Autowired
    private WasteService wasteService;


    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String saveEntWaste(User user, EntWasteParams entWasteParams) throws Exception {
        String entWasteId = "";
        try {
            saveWasteName(user, entWasteParams);
            EntWaste entWaste = insertEntWaste(user, entWasteParams);
            entWasteId = entWaste.getId();
            entWasteParams.setEntWasteId(entWasteId);
        } catch (Exception e) {
            logger.error("保存企业危废时异常", e);
            throw e;
        }
        return entWasteId;
    }

    private EntWaste insertEntWaste(User user, EntWasteParams entWasteParams) {
        EntWaste entWaste = new EntWaste();
        entWaste.setEntId(entWasteParams.getEntId());
        entWaste.setWasteId(entWasteParams.getWasteId());
        entWaste.setWasteCode(entWasteParams.getWasteCode());
        entWaste.setWasteName(entWasteParams.getWasteName());
        entWaste.setUnitCode(entWasteParams.getUnitCode());
        Date date = new Date();
        entWaste.setCreateBy(user.getUserId());
        entWaste.setCreateTime(date);
        entWaste.setEditBy(user.getUserId());
        entWaste.setEditTime(date);
        this.insert(entWaste);
        return entWaste;
    }

    private void saveWasteName(User user, EntWasteParams entWasteParams) {
        try {
            WasteName wasteName = new WasteName();
            wasteName.setName(entWasteParams.getWasteName());
            wasteName.setWasteId(entWasteParams.getWasteId());
            wasteName.setStatus(Constant.ENABLED);
            int count = wasteNameService.getWasteNameId(wasteName);
            if (count == 0) {
                wasteNameService.saveWasteName(user, wasteName);
            }
        } catch (Exception e) {
            logger.error("保存或获取危废信息时出错", e);
            throw e;
        }
    }


    @Override
    public List<EntWasteModel> listEntWaste(User user, EntWasteParams entWasteParams, PagingParameter pagingParameter) throws Exception {
        List<EntWasteModel> entWasteList = null;

        try {
            if (StringUtils.isBlank(entWasteParams.getEntId())) {
                entWasteParams.setEntId(user.getEnterpriseId());
            }
            int count = this.baseMapper.countEntWaste(entWasteParams);
            if (count > 0) {
                Util.initPagingParameter(pagingParameter);
                entWasteParams.setStartRowIndex(pagingParameter.getStart());
                entWasteParams.setRows(pagingParameter.getLimit());
                pagingParameter.setTotalRecord(count);
                entWasteList = this.baseMapper.listEntWaste(entWasteParams);
            }
        } catch (Exception e) {
            logger.error("获取企业危废列表时异常", e);
            throw e;
        }
        return entWasteList;
    }

    @Override
    public EntWasteModel getEntWasteDetailById(EntWasteParams entWasteParams) throws Exception {
        EntWasteModel entWasteModel = null;
        try {
            List<EntWasteModel> entWasteModels = this.baseMapper.listEntWaste(entWasteParams);
            if (entWasteModels != null && entWasteModels.size() > 0) {
                entWasteModel = entWasteModels.get(0);
            }
        } catch (Exception e) {
            logger.error("获取企业危废信息时异常", e);
            throw e;
        }
        return entWasteModel;
    }

    @Override
    public boolean isExistEnterpriseWaste(EntWasteParams entWasteParams) throws Exception {
        boolean isExist;
        try {
            int count = this.baseMapper.getEntWasteId(entWasteParams);
            if (count > 0) {
                isExist = true;
            } else {
                isExist = false;
            }
        } catch (Exception e) {
            logger.error("获取企业危废时异常", e);
            throw e;
        }
        return isExist;
    }

    @Override
    public List<EntWasteModel> listEntWasteByLicenceId(String licenceId, User user) throws Exception {
        List<EntWasteModel> entWasteList;
        try {
            entWasteList = this.baseMapper.listEntWasteByLicenceId(licenceId, user.getEnterpriseId());
        } catch (Exception e) {
            logger.error("根据许可证获取企业危废列表时异常", e);
            throw e;
        }
        return entWasteList;
    }


    @Override
    public List<EntWaste> listEntWasteByIds(List<String> ids) throws Exception {
        List<EntWaste> entWasteList;
        try {
            EntityWrapper<EntWaste> ew = new EntityWrapper<>();
            ew.setSqlSelect("id,wasteCode");
            ew.in("id",ids);
            entWasteList = this.selectList(ew);
        } catch (Exception e) {
            logger.error("根据id获取企业危废时异常", e);
            throw e;
        }
        return entWasteList;
    }

    @Override
    public List<EntWasteModel> listEntWasteByEntId(EntWasteParams entWasteParams) throws Exception {
        List<EntWasteModel> entWasteList;
        try {
            entWasteList = this.baseMapper.listEntWasteByEntId(entWasteParams);
        } catch (Exception e) {
            logger.error("获取企业危废列表时异常", e);
            throw e;
        }
        return entWasteList;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean updateEntWaste(User user, EntWasteModel entWasteModel) throws Exception {
        try {
            Date date = new Date();
            EntWaste entWaste = this.baseMapper.selectById(entWasteModel.getEntWasteId());
            if (!entWaste.getWasteName().equals(entWasteModel.getWasteName())) {
                updateEntWastename(user, entWasteModel);
            }
            entWaste.setId(entWasteModel.getEntWasteId());
            entWaste.setWasteId(entWasteModel.getWasteId());
            entWaste.setWasteCode(entWasteModel.getWasteCode());
            entWaste.setWasteName(entWasteModel.getWasteName());
            entWaste.setEditBy(user.getUserId());
            entWaste.setEditTime(date);
            return this.updateById(entWaste);
        } catch (Exception e) {
            logger.error("更新企业危废时异常", e);
            throw e;
        }
    }

    private void updateEntWastename(User user, EntWasteModel entWasteModel) {
        WasteName wasteName = new WasteName();
        wasteName.setWasteId(entWasteModel.getWasteId());
        wasteName.setName(entWasteModel.getWasteName());
        wasteName.setStatus(Constant.ENABLED);
        wasteNameService.saveWasteName(user, wasteName);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean deleteEntWaste(User user, List<EntWasteModel> entWasteModels) throws Exception {
        boolean deleteResult = true;
        try {
            Date date = new Date();
            for (EntWasteModel entWasteModel : entWasteModels) {
                EntWaste entWaste = this.baseMapper.selectById(entWasteModel.getEntWasteId());
                entWaste.setDeleteFlag(1);
                entWaste.setEditBy(user.getUserId());
                entWaste.setEditTime(date);
                deleteResult = deleteResult && this.updateById(entWaste);
            }
        } catch (Exception e) {
            logger.error("删除企业危废时异常", e);
            throw e;
        }
        return deleteResult;
    }

    @Override
    public String getWasteIdByCode(String code) throws Exception {
        Waste waste = null;
        try {
            waste = wasteService.getWasteByCode(code);
            if (waste != null) {
                return waste.getId();
            }
        } catch (Exception e) {
            logger.error("获取八位码异常", e);
            throw e;
        }
        return null;
    }


    @Override
    public List<WasteName> listWasteName(String wasteId, String wasteName) throws Exception {
        Map<String, Object> datamap = new HashMap<String, Object>();
        List<WasteName> wasteNameList = new ArrayList<>();
        try {
            WasteName wasteNameParam = new WasteName();
            wasteNameParam.setWasteId(wasteId);
            wasteNameParam.setName(wasteName);
            wasteNameList = wasteNameService.listWasteName(wasteNameParam);
        } catch (Exception e) {
            logger.error("获取危废名称的下拉列表存在异常", e);
            throw e;
        }

        return wasteNameList;
    }

    @Override
    public Map<String, Object> getWasteDropdownList(String keyword) {
        Map<String, Object> datamap = new HashMap<String, Object>();
        JSONArray jsonWfArray = new JSONArray();
        JSONObject wfObj = null;
        try {
            SimpleSolr simpleSolr = null;
            simpleSolr = new SimpleSolr("WASTEINFO");

            SolrQuery params = new SolrQuery();
            if (org.apache.commons.lang3.StringUtils.isBlank(keyword)) {
                params.setQuery("*:*");
            } else {
                keyword = keyword.replace(" ", ",");
                keyword = ClientUtils.escapeQueryChars(keyword);
                params.setQuery("wasteKeyWord:" + keyword);
            }

            params.set("start", "0");

            QueryResponse rsp = simpleSolr.getClient().query(params);
            SolrDocumentList docs = rsp.getResults();

            List<WasteIndex> wasteIdList = DocumentUtil.toBeanList4Waste(docs, WasteIndex.class, rsp.getHighlighting());
            for (WasteIndex waste : wasteIdList) {
                wfObj = new JSONObject();
                wfObj.put("id", waste.getId());
                wfObj.put("addenterwastecode", waste.getWasteCodeDisplay());
                wfObj.put("addenterwastename", waste.getWasteNameDisplay());
                wfObj.put("wasteTypeId", waste.getWasteTypeId());
                wfObj.put("addenterwastedesc", waste.getWasteDescDisplay());
                jsonWfArray.add(wfObj);
            }
            datamap.put("value", jsonWfArray);
            datamap.put("redirect", "");
            datamap.put("message", "");
            datamap.put("code", 200);

        } catch (Exception e) {
            logger.error("获取八位码的下拉列表存在异常", e);
            throw new RuntimeException(e);
        }

        return datamap;
    }


}
