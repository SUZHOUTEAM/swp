package com.mlsc.waste.fw.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mlsc.waste.fw.dao.SysCantonDao;
import com.mlsc.waste.fw.model.SysCanton;
import com.mlsc.waste.fw.service.SysCantonService;
import com.mlsc.waste.utils.CodeTypeConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhugl
 */
@Service("SysCantonService")
public  class SysCantonServiceImpl implements SysCantonService{
    private final static Logger logger = LoggerFactory.getLogger(SysCantonServiceImpl.class);

    @Autowired
    private SysCantonDao sysCantonDao;

    @Override
    public SysCanton querySysCantonByCantonCode(String ticketId, String cantonCode) {
        SysCanton SysCanton = null;
        try {
            SysCanton = sysCantonDao.get(cantonCode);
        } catch (Exception e) {
            logger.error("根据行政区代码获取行政区时异常",e);
            throw new RuntimeException(e);
        }

        return SysCanton;
    }

    @Override
    public List<SysCanton> querySysCantonListByParentCantonCode( String parentCantonCode) {
        SysCanton sysCanton = new SysCanton();
        try {
            sysCanton.setParentCantonCode(parentCantonCode);
            sysCanton.setCantonType(1);

            List<SysCanton> sysCantonList = sysCantonDao.query(sysCanton);
            if (sysCantonList != null && sysCantonList.size() > 0) {
                return sysCantonList;
            } else {
                return null;
            }
        } catch (Exception e) {
            logger.error("根据父行政区代码获取行政区时异常",e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public SysCanton queryCantonNameByEnterpriseId( String enterId) {
    	SysCanton sysCanton = new SysCanton();
        try {

            List<SysCanton> sysCantonList = sysCantonDao.queryCantonNameByEnterpriseId(enterId);
            if (sysCantonList != null && sysCantonList.size() > 0) {
            	sysCanton = sysCantonList.get(0);
            } else {
                return null;
            }
        } catch (Exception e) {
            logger.error("根据企业id获取行政区划代码名称时异常",e);
            throw new RuntimeException(e);
        }

        return sysCanton;


    }

    @Override
    public Map<String, Object> getProvinceDistricts(String ticketId) {
        Map<String, Object> datamap = new HashMap<String, Object>();
        JSONObject wfObj = null;
        JSONObject districtsObj = new JSONObject();
        try {
            // 获取省级的行政区
            List<SysCanton> provinceList = querySysCantonListByParentCantonCode(CodeTypeConstant.COUNTRY_CANTONCODE);
            JSONObject provinceObject = new JSONObject();
            JSONArray agArray = new JSONArray();
            JSONArray hkArray = new JSONArray();
            JSONArray lsArray = new JSONArray();
            JSONArray tzArray = new JSONArray();
            for (SysCanton temCanton : provinceList) {
                wfObj = new JSONObject();
                wfObj.put("code", temCanton.getCantonCode());
                wfObj.put("address", temCanton.getCantonName());

                if (temCanton.getCantonName().startsWith("安") ||// "安徽省"
                        temCanton.getCantonName().startsWith("北") ||// 北京市
                        temCanton.getCantonName().startsWith("重") ||// 重庆市
                        temCanton.getCantonName().startsWith("福") ||// 福建省
                        temCanton.getCantonName().startsWith("甘") ||// 甘肃省
                        temCanton.getCantonName().startsWith("广") ||// 广东省/广西壮族自治区
                        temCanton.getCantonName().startsWith("贵")) {// 贵州省
                    agArray.add(wfObj);
                } else if (temCanton.getCantonName().startsWith("海") ||// 海南省
                        temCanton.getCantonName().startsWith("河") ||// 河北省/河南省
                        temCanton.getCantonName().startsWith("黑") ||// 黑龙江省
                        temCanton.getCantonName().startsWith("湖") ||// 湖北省/湖南省
                        temCanton.getCantonName().startsWith("江") ||// 江苏省/江西省
                        temCanton.getCantonName().startsWith("吉")) {// 吉林省
                    hkArray.add(wfObj);
                } else if (temCanton.getCantonName().startsWith("辽") || // "辽宁省"
                        temCanton.getCantonName().startsWith("内") || //内蒙古自治区
                        temCanton.getCantonName().startsWith("宁") || // 宁夏回族自治区
                        temCanton.getCantonName().startsWith("青") || // 青海省
                        temCanton.getCantonName().startsWith("山") || // 山东省/山西省
                        temCanton.getCantonName().startsWith("上") ||// 上海市
                        temCanton.getCantonName().startsWith("陕") ||// 陕西省
                        temCanton.getCantonName().startsWith("四")) {// 四川省
                    lsArray.add(wfObj);
                } else if (temCanton.getCantonName().startsWith("天") ||// 天津市
                        temCanton.getCantonName().startsWith("新") ||// 新疆维吾尔自治区
                        temCanton.getCantonName().startsWith("西") ||// 西藏自治区
                        temCanton.getCantonName().startsWith("云") ||// 云南省
                        temCanton.getCantonName().startsWith("浙")) {// 浙江省
                    tzArray.add(wfObj);
                }
            }

            provinceObject.put("A-G", agArray);
            provinceObject.put("H-K", hkArray);
            provinceObject.put("L-S", lsArray);
            provinceObject.put("T-Z", tzArray);
            districtsObj.put(CodeTypeConstant.COUNTRY_CANTONCODE, provinceObject);

            datamap.put("value", districtsObj);
        } catch (Exception e) {
            logger.error("city-picker数据源获取时异常",e);
            throw new RuntimeException(e);
        }

        return datamap;
    }

    @Override
    public Map<String, Object> getCityDistricts(String parentCantonCode, String ticketId) {
        Map<String, Object> datamap = new HashMap<String, Object>();
        JSONObject wfObj = null;
        JSONObject districtsObj = new JSONObject();
        try {
            List<SysCanton> provinceList = querySysCantonListByParentCantonCode(parentCantonCode);
            if (provinceList != null && provinceList.size() > 0) {
                wfObj = new JSONObject();
                for (SysCanton temCanton : provinceList) {
                    wfObj.put(temCanton.getCantonCode(), temCanton.getCantonName());
                }

                districtsObj.put(parentCantonCode, wfObj);
            }

            datamap.put("value", districtsObj);
        } catch (Exception e) {
            logger.error("根据区域代码加载其子区域代码时异常",e);
            throw new RuntimeException(e);
        }

        return datamap;
    }

    @Override
    public SysCanton queryCantonNameByCantonCode(String cantonCode) {
        try {
            // 获取省级的行政区
            SysCanton sysCanton = sysCantonDao.queryCantonNameByCantonCode(cantonCode);
            return sysCanton;
        } catch (Exception e) {
            logger.error("city-picker数据源获取时异常",e);
            throw new RuntimeException(e);
        }

    }


}