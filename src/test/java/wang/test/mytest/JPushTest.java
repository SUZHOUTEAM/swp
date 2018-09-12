package wang.test.mytest;

import com.mlsc.waste.utils.Constant;
import com.mlsc.waste.utils.JPushUtil;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2017/7/12 0012.
 */
public class JPushTest {

    public static void main(String[] args) {
        String cantonCodes = "339,688000,9483700,78788760,0034700349900,983893001100";
        List<String> cantonCodesList = Arrays.asList(cantonCodes.split(","));
        List<String> tempList = new ArrayList<>();
        for(String cantonCode: cantonCodesList){
            tempList.add(cantonCode.replaceAll("0*$",""));
        }
        System.out.println(tempList);
        System.out.println(tempList.size());
    }






}
