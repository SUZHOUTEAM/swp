package yifeiwang;

import com.mlsc.yifeiwang.operaction.entity.WebsiteOperation;

/**
 * Created by user on 2017/11/20.
 */
public class jobTest {
    public static String JOB_NAME = "动态任务调度";
    public static String TRIGGER_NAME = "动态任务触发器";
    public static String JOB_GROUP_NAME = "XLXXCC_JOB_GROUP";
    public static String TRIGGER_GROUP_NAME = "XLXXCC_JOB_GROUP";

    public static void main(String[] args) {
        try {
            System.out.println("【系统启动】开始(每1秒输出一次)...");
            WebsiteOperation websiteOperation = new WebsiteOperation();
            websiteOperation.setId("123");
            websiteOperation.setCronJob("0/1 * * * * ?");
//            QuartzManager.addJob(websiteOperation, NotifyWebsiteUser.class);

            System.out.println("【系统启动】开始(每2秒输出一次)...");
            WebsiteOperation websiteOperation2 = new WebsiteOperation();
            websiteOperation2.setId("12345");

            websiteOperation2.setCronJob("0 15 10 ? * 2");
//            QuartzManager.addJob(websiteOperation2, NotifyWebsiteUser.class);

            Thread.sleep(12000);
            System.out.println("【移除定时】开始...");
//            QuartzManager.removeJob(websiteOperation2);
            System.out.println("【移除定时】成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
