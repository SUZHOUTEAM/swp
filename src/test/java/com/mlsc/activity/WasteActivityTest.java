package com.mlsc.activity;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.mlsc.BaseTest;
import com.mlsc.yifeiwang.activity.common.PriceType;
import com.mlsc.yifeiwang.activity.entity.WasteActivity;
import com.mlsc.yifeiwang.activity.entity.WasteActivityEnterprise;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.yifeiwang.activity.model.ActivityQueryParam;
import com.mlsc.yifeiwang.activity.model.EnterpriseWasteVo;
import com.mlsc.yifeiwang.activity.model.WasteActivityDetailVO;
import com.mlsc.yifeiwang.activity.model.WasteActivityVO;
import com.mlsc.yifeiwang.activity.service.IWasteActivityEnterpriseService;
import com.mlsc.yifeiwang.activity.service.IWasteActivityService;
import com.mlsc.waste.user.model.User;
import com.mlsc.waste.utils.LoginStatusUtils;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.mlsc.yifeiwang.activity.common.PriceType.SINGLE;

/**
 * Created by yinxl on 2017/7/31.
 */
public class WasteActivityTest extends BaseTest {


    @Test
    public void getActivityDetailById() throws Exception {
        ActivityQueryParam param = new ActivityQueryParam();
        param.setActivityId("2a6e4bd6d6d84c4ba40879eb174b8a62");
        param.setWasteEnterpriseId("2235868126758912");
        User user= LoginStatusUtils.getUserByTicketId(getTicketId());
        List<WasteActivityDetailVO> activityDetailListByEnt = wasteActivityService.listCapacityreleaseByActivityId(user, param);
        for(WasteActivityDetailVO  activityDetailByEnt : activityDetailListByEnt){
            param.setWasteEnterpriseId(activityDetailByEnt.getWasteEntId());
            param.setWasteId(activityDetailByEnt.getWasteId());
            List<EnterpriseWasteVo> enterpriseWastelist = wasteActivityService.listEnterpriseWasteByWasteId(param);
            activityDetailByEnt.setEnterpriseWastelist(enterpriseWastelist);
        }
        Assert.assertTrue(activityDetailListByEnt.size() > 0);
    }


    @Test
    public void listCapacityreleaseByActivityId() throws Exception {
        ActivityQueryParam param = new ActivityQueryParam();
        param.setActivityId("12c4997da5224434badfa31ece74b39b");
        List<WasteActivityDetailVO> activityDetailById = wasteActivityService.getActivityDetailById(param);
        System.out.println(activityDetailById.size());

    }
    @Test
    public void listOrderActiviyNameTest() throws Exception{
        List<WasteActivityVO> list =  wasteActivityService.listOrderActiviyName("2235872864471040");
        Assert.assertTrue(list.size()>0);
    }

    @Autowired
    private IWasteActivityService wasteActivityService;

    @Autowired
    private IWasteActivityEnterpriseService wasteActivityEnterpriseService;

    List<ActivityEnt> entList = new ArrayList<>();

    @Before
    public void initData() {
        entList.add(new ActivityEnt("1897218280196096", "山东多友科技有限公司"));
        entList.add(new ActivityEnt("1897237513619456", "山东清泽能源有限公司"));
        entList.add(new ActivityEnt("1897240455153664", "山东东巨化工股份有限公司"));
        entList.add(new ActivityEnt("1897251067070464", "淄博齐翔腾达化工股份有限公司"));
        entList.add(new ActivityEnt("1897256842643456", "青岛国辉环境工程有限公司"));
        entList.add(new ActivityEnt("1897485945587712", "枣庄市永进医疗废弃物处理有限公司"));
        entList.add(new ActivityEnt("1897492470155264", "青岛新世纪环境工程有限公司"));
        entList.add(new ActivityEnt("1897497015552000", "合肥市吴山固体废物处置有限公司"));
        entList.add(new ActivityEnt("1897501220259840", "安徽嘉朋特环保科 技服务有限公司"));
        entList.add(new ActivityEnt("1897505159628800", "滁州超越环保科技有限公司"));
    }

    @Test
    public void deleteLogicBatchIds(){
       boolean result = wasteActivityService.deleteLogicBatchIds(Arrays.asList("2a6e4bd6d6d84c4ba40879eb174b8a62","fbfa322ec934418f9575ba3764e8df51"));
       Assert.assertTrue(result);
    }

    @Test
    public void testInsertWasteActivity() {

        DateTime now = DateTime.now();

        int step = 5;
        for (int i = 0; i < 100; i++) {

            WasteActivity wa = new WasteActivity();
            wa.setActivityName("活动" + i);
            wa.setActivityRemark("软件开发的历史就是源代码（也就是人脑需要编辑的介质）的形式一直在升级的历史，从最初的机器语言到汇编语言、过程式高级语言到现在流行的面向对象语言。“模型就是源代码”不是可不可能的问题，而是合不合算的问题。真实世界中，涉众对许多系统的质量要求并不高，电商网站、银行系统出个故障，人们习以为常，能够接受，反正出了故障再恢复呗。这样的系统，充分的建模没有必要性。同时，也没有可能性，因为类太多，工期不允许为每个类画状态机。这类系统，只需要对关键的类充分建模。如果做一个心脏起搏器，对质量要求非常高，通过充分的建模尽可能减少人工编码导致的偶发错误是必要的，而且也是可能的，因为类的个数少");
            wa.setCantonCode("320600");
            wa.setStartDate(now.toDate());
            wa.setEndDate(now.plusDays(step).toDate());
            wa.setPriceType(SINGLE.getValue());
            wa.setStatus("1");
            wa.setCreateBy("test");
            wa.setCreateTime(new Date());
            wa.setEditBy("test");
            wa.setEditTime(new Date());
            wasteActivityService.insert(wa);

            WasteActivityEnterprise wae = new WasteActivityEnterprise();
            wae.setActivityId(wa.getId());
            wae.setEntId(entList.get(i % 10).getEntId());
            wae.setEntName(entList.get(i % 10).getEntName());
            wae.setStartPrice("1002");
            wae.setEndPrice("20000");
            wae.setStatus("1");
            wae.setCreateBy("test");
            wae.setCreateTime(new Date());
            wae.setEditBy("test");
            wae.setEditTime(new Date());
            now = now.plusDays(step + 1);
            wasteActivityEnterpriseService.insert(wae);
        }
    }

    @Test
    public void testListWasteActivity() {

        ActivityQueryParam param = new ActivityQueryParam();
        param.setEntName("青岛国辉环境");
        List<WasteActivityVO> list = wasteActivityService.listWasteActivity(param);
        Assert.assertNotNull(list);
        Assert.assertTrue(list != null && list.size() > 0);

        param.setActivityName("活动14");
        list = wasteActivityService.listWasteActivity(param);
        Assert.assertEquals(list.size(), 1);
    }
    @Test
    public void testlistWasteActivityByEntId()throws Exception{
        PagingParameter pagingParameter = new PagingParameter();
        ActivityQueryParam param = new ActivityQueryParam();
        param.setEntId("2235872864471040");
        List<WasteActivityVO> list = wasteActivityService.listWasteActivityByEntId(param,pagingParameter);
        Assert.assertNotNull(list);
        Assert.assertTrue(list != null && list.size() > 0);

    }

    @Test
    public void listWasteActivity(){
        Page<WasteActivity> page = new Page<>(0,1);

        Page<WasteActivity> wasteActivityPage = wasteActivityService.listWasteActivity(page);
        Assert.assertTrue(wasteActivityPage != null);

    }

    @Test
    public void save() throws Exception {
        WasteActivityVO activityVO = new WasteActivityVO();
        activityVO.setActivityName("测试11");
        activityVO.setActivityRemark("我是一个大大大的点点滴滴多");
        activityVO.setCantonCode("320500");
        activityVO.setStartDate(DateTime.now().toDate());
        activityVO.setEndDate(DateTime.now().plusDays(10).toDate());
        activityVO.setEndPrice("200");
        activityVO.setStartPrice("20");
        activityVO.setEntId("1897505159628800");
        activityVO.setEntName("滁州超越环保科技有限公司");
        activityVO.setLogoFileId("dddd");
        activityVO.setSubjectFileId("ddddddd");
        activityVO.setPriceType(PriceType.SINGLE.getValue());
        WasteActivity activity = wasteActivityService.save(getTicketId(), activityVO);
        Assert.assertTrue(activity.getId() != null);

        String activityId = activity.getId();

        Assert.assertTrue(wasteActivityService.deleteById(activityId));
        EntityWrapper<WasteActivityEnterprise> ew = new EntityWrapper<>();
        ew.eq("activity_id", activityId);
        Assert.assertTrue(wasteActivityEnterpriseService.delete(ew));
    }

    @Test
    public void getCurrentActivity() throws Exception {
        ActivityQueryParam activityVo = new ActivityQueryParam();
        activityVo.setCantonCode("32");
        List<WasteActivityVO> activitys= wasteActivityService.listLiveWasteActivity(activityVo);
        Assert.assertNotNull(activitys);

    }
}
