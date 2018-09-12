package com.mlsc.yifeiwang.wasteinformation.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mlsc.common.util.MakeHtml;
import com.mlsc.common.util.StringUtils;
import com.mlsc.yifeiwang.wasteinformation.entity.WasteInformation;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.waste.user.model.User;
import com.mlsc.waste.utils.Constant;
import com.mlsc.waste.utils.Util;
import com.mlsc.yifeiwang.wasteinformation.mapper.WasteInformationMapper;
import com.mlsc.yifeiwang.wasteinformation.model.WasteInformationModel;
import com.mlsc.yifeiwang.wasteinformation.model.WasteInformationParam;
import com.mlsc.yifeiwang.wasteinformation.service.IWasteInformationService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;

import com.mlsc.waste.utils.PropertyUtil;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author caoyy
 * @since 2018-06-13
 */
@Service
public class WasteInformationServiceImpl extends ServiceImpl<WasteInformationMapper, WasteInformation> implements IWasteInformationService {
    private final static Logger logger = LoggerFactory.getLogger(WasteInformationServiceImpl.class);

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean saveWasteInformation(WasteInformationParam wasteInformationParam, User user, List<String> errors) throws Exception {
        try {
            boolean validate = validateWasteInformation(wasteInformationParam, errors);
            if (validate) {
                Date date = new Date();
                WasteInformation wasteInformation = wasteInformationParam;
                wasteInformation.setId(Util.uuid32());
                wasteInformation.setCreateBy(user.getUserId());
                wasteInformation.setCreateTime(date);
                wasteInformation.setEditBy(user.getUserId());
                wasteInformation.setEditTime(date);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                String timeStampStr = simpleDateFormat.format(date);
                wasteInformation.setHtmlSrc(timeStampStr);
                boolean flag = this.insert(wasteInformation);
                if (flag) {
                    generateStaticHtml(wasteInformation);
                }
                return flag;
            }
        } catch (Exception e) {
            logger.error("保存危废资讯时异常", e);
            throw e;
        }
        return false;
    }

    private void generateStaticHtml(WasteInformation wasteInformation) {
        String htmlName = wasteInformation.getHtmlSrc();
        String tomcatPath = MakeHtml.getTomcatPath();
        String fileDomain = PropertyUtil.getValue("fileDomain");
        logger.info("tomcatPath==" + tomcatPath);
        logger.info("fileDomain==" + fileDomain);
        MakeHtml.makeHtml(fileDomain + "/swp/wasteInformation/view?id=" + wasteInformation.getId(),
                tomcatPath + "/ROOT/knowledge/" + htmlName + ".html");
        MakeHtml.makeHtml(fileDomain + "/swp/wasteInformation/viewForMobile?id=" + wasteInformation.getId(),
                tomcatPath + "/ROOT/knowledge/" + htmlName + "-mobile.html");
    }

    private boolean validateWasteInformation(WasteInformationParam wasteInformationParam, List<String> errors) {
        if (StringUtils.isNullOrEmpty(wasteInformationParam.getTitle())) {
            errors.add("课堂名称为空");
            return false;
        }
        return true;
    }

    @Override
    public List<WasteInformationModel> listWasteInformation(WasteInformationParam wasteInformationParam, PagingParameter pagingParameter) throws Exception {
        List<WasteInformationModel> wasteInformationModels = null;
        try {
            Util.initPagingParameter(pagingParameter);
            wasteInformationParam.setStartRowIndex(pagingParameter.getStart());
            wasteInformationParam.setRows(pagingParameter.getLimit());

            int count = this.baseMapper.countWasteInformation(wasteInformationParam);
            if (count > 0) {
                pagingParameter.setTotalRecord(count);
                wasteInformationModels = this.baseMapper.listWasteInformation(wasteInformationParam);
            }

        } catch (Exception e) {
            logger.error("获取资讯列表时异常", e);
            throw e;
        }
        return wasteInformationModels;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean updateWasteInformation(WasteInformationParam wasteInformationParam, User user) throws Exception {
        if (StringUtils.isNullOrEmpty(wasteInformationParam.getId())) return false;
        boolean flag = false;
        try {
            WasteInformation wasteInformation = wasteInformationParam;
            Date date = new Date();
            wasteInformation.setEditBy(user.getUserId());
            wasteInformation.setEditTime(date);
            EntityWrapper<WasteInformation> ew = new EntityWrapper<>();
            ew.eq("id", wasteInformation.getId());
            flag = this.update(wasteInformation, ew);
            if (flag) {
                generateStaticHtml(wasteInformation);
            }
        } catch (Exception e) {
            logger.error("更新危废咨询时异常", e);
            throw e;
        }

        return flag;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateClickAmount(WasteInformationParam wasteInformationParam) throws Exception {
        if (StringUtils.isNullOrEmpty(wasteInformationParam.getId())) return;
        try {
            this.baseMapper.updateClickAmount(wasteInformationParam);
        } catch (Exception e) {
            logger.error("更新点击量时异常", e);
            throw e;
        }
    }

    @Override
    public WasteInformationModel getWasteInformationById(WasteInformationParam wasteInformationParam) throws Exception {
        try {
            WasteInformationModel wasteInformationModel = this.baseMapper.getWasteInformationById(wasteInformationParam);
            return wasteInformationModel;
        } catch (Exception e) {
            logger.error("根据id获取危废资讯时异常", e);
            throw e;
        }

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean deleteWasteInformationById(User user, List<String> ids) throws Exception {
        try {
            if (ids != null && ids.size() > 0) {
                List<WasteInformation> wasteInformations = new ArrayList<>();
                for (String id : ids) {
                    WasteInformation wasteInformation = this.selectById(id);
                    Date currentDate = new Date();
                    wasteInformation.setDeleteFlag(Constant.DELETED);
                    wasteInformation.setEditBy(user.getUserId());
                    wasteInformation.setEditTime(currentDate);
                    wasteInformations.add(wasteInformation);
                }
                return this.updateBatchById(wasteInformations);
            }
        } catch (Exception e) {
            logger.error("删除危废资讯时异常", e);
            throw e;
        }
        return false;

    }
}
