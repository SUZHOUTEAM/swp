package yifeiwang;

import com.mlsc.BaseTest;
import com.mlsc.yifeiwang.enterprise.entity.SysEnterpriseBase;
import com.mlsc.yifeiwang.enterprise.mapper.SysEnterpriseBaseMapper;
import org.junit.Test;
import com.mlsc.waste.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * Created by user on 2018/3/19.
 */
public class UtilTest extends BaseTest {

    @Autowired
    private SysEnterpriseBaseMapper enterpriseBaseMapper;

    @Test
    public void getLngAndLatTest() {
        try {
            List<SysEnterpriseBase> list = enterpriseBaseMapper.listEnterpriseInfo();
            list.forEach(enterBase -> {
                Map<String, Double> resultMap = Util.getLngAndLat(enterBase.getEntAddress());
                if(resultMap.get("lat")!=null && resultMap.get("lng")!=null){
                    enterBase.setPosx(resultMap.get("lng"));
                    enterBase.setPosy(resultMap.get("lat"));
//                    enterpriseBaseMapper.updateEnterprisePosx(enterBase);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
