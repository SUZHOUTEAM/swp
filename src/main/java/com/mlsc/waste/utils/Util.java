package com.mlsc.waste.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mlsc.common.util.MakeHtml;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.epdp.common.utils.ReflectUtils;
import com.mlsc.waste.user.model.User;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Util {
    static final String SDF_DATE_TIME = "yyyy-MM-dd HH:mm:ss";
    static final String SDF_DATE = "yyyy-MM-dd";
    static final String SDF_TIME = "HH:mm:ss";
    static final String SDF_MONTH = "yyyy-MM";
    static final String SDF_YEAR = "yyyy";
    private final static Logger logger = LoggerFactory.getLogger(Util.class);


    //simpledateformat 线程非安全
    public static String datetimeToString(Date date) {
        return new SimpleDateFormat(SDF_DATE_TIME).format(date);
    }

    public static String dateToString(Date date) {
        return new SimpleDateFormat(SDF_DATE).format(date);
    }

    public static String timeToString(Date date) {
        return new SimpleDateFormat(SDF_TIME).format(date);
    }

    public static String monthToString(Date date) {
        return new SimpleDateFormat(SDF_MONTH).format(date);
    }

    public static String yearToString(Date date) {
        return new SimpleDateFormat(SDF_YEAR).format(date);
    }

    public static Date datetimestrToDate(String str) throws ParseException {
        return new SimpleDateFormat(SDF_DATE_TIME).parse(str);
    }

    public static Date datestrToDate(String str) throws ParseException {
        return new SimpleDateFormat(SDF_DATE).parse(str);
    }

    public static Date timestrToDate(String str) throws ParseException {
        return new SimpleDateFormat(SDF_TIME).parse(str);
    }

    public static Date monthstrToDate(String str) throws ParseException {
        return new SimpleDateFormat(SDF_MONTH).parse(str);
    }

    public static Date yearstrToDate(String str) throws ParseException {
        return new SimpleDateFormat(SDF_YEAR).parse(str);
    }

    /**
     * 数组转化成字符串，用【，】分割
     *
     * @author zhugl date 2016-06-06
     */
    public static String toString(String[] ary) {
        if (ary == null) {
            return null;
        }
        StringBuffer s = new StringBuffer();
        for (int i = 0; i < ary.length; i++) {
            s.append(ary[i] + ",");
        }
        s.deleteCharAt(s.length() - 1);
        return s.toString();
    }

    /**
     * 数组转化成Arrays并去除空的值
     *
     * @author zhugl date 2016-07-27
     */
    public static List<String> stringArrayConvertToList(String[] ary) {
        List<String> stringList = new ArrayList<String>();

        for (String str : ary) {
            if (StringUtils.isNotBlank(str)) {
                stringList.add(str);
            }
        }
        return stringList;
    }

    /**
     * GUID 生成的共同代码
     *
     * @author zhugl date 2016-06-06
     */
    public static String uuid32() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 解决用户的通知信息显示后，按F5刷新页面，通知信息反复出现的bug
     *
     * @author zhugl date 2016-07-27
     */
    public static ModelAndView stopMessageDisplay(ModelAndView mv, HttpServletRequest request) {
        if (StringUtils.isNotBlank(request.getParameter("isfresh"))) {
            mv.addObject("msg", request.getParameter("msg"));
            mv.addObject("status", request.getParameter("status"));
        }

        return mv;
    }

    /**
     * 委托处理计划发布编号等生成的共同代码
     * 现阶段生成的规则暂无，预留一个方法
     *
     * @author zhugl date 2016-07-11
     */
    public static String getRreleaseCode() {
        return ("MLSC" + Util.datetimeToString(new Date())).replaceAll("-|:|\\s", "");
    }

    /**
     * 订单号等生成的共同代码
     * 现阶段生成的规则暂无，预留一个方法
     *
     * @author zhugl date 2016-07-11
     */
    public static String getOrdersCode() {
        return ("YFW" + Util.datetimeToString(new Date())).replaceAll("-|:|\\s", "");
    }

    /**
     * 生产六位随机的整数，用于短信验证码
     *
     * @return
     */
    public static String getSix() {
        int s = (int) (Math.random() * 9000) + 1000;
        return s + "";
    }


    public static boolean afterCurrentDate(String endDate) {
        return !(datetimeToString(new Date()).compareTo(endDate) > 0);
    }


    /**
     * 判断用户是不是平台管理员
     *
     * @return String ticketId;
     */
    public static boolean isSysUser(String ticketId) {
        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        // 判断是不是平台管理员
        boolean isSysUser = false;
        isSysUser = Constant.SYS_USER_TYPE == user.getUserType();
        return isSysUser;
    }

    /**
     * 根据地址解析器，解析出市，区县名称
     *
     * @return String ticketId;
     */
    public static String[] getCityAndDistrict(String cantonValue) {
        String[] cantonValueArr = null;
        if (StringUtils.isNotBlank(cantonValue)) {
            cantonValueArr = cantonValue.split("/");
        }

        return cantonValueArr;
    }

    /**
     *
     */
    public static String getIpAddress() {
        String ip = "";
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            ip = inetAddress.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return ip;
    }

    /**
     * 根据本地IP获取对应的区域地址
     *
     * @return
     */
    public static String getAddressByIp() {
        //获取本地IP
        System.out.println("本机的外网IP是：" + getWebIp("http://www.ip138.com/ip2city.asp"));
        String localIp = getWebIp("http://www.ip138.com/ip2city.asp");
        String address = "";
        address = getAddressByIP(localIp);
        return address;
    }

    public static String getAddressByIP(String strIP) {
        try {
            URL url = new URL("http://api.map.baidu.com/location/ip?ak=F454f8a5efe5e577997931cc01de3974&ip=" + strIP);
            URLConnection conn = url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            String line = null;
            StringBuffer result = new StringBuffer();
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            reader.close();
            String ipAddr = result.toString();
            try {
                JSONObject obj1 = JSON.parseObject(ipAddr);
                if ("0".equals(obj1.get("status").toString())) {
                    JSONObject obj2 = obj1.getJSONObject("content");
                    JSONObject obj3 = obj2.getJSONObject("address_detail");
                    return obj3.get("city").toString();
                } else {
                    return "读取失败";
                }
            } catch (Exception e) {
                logger.error("读取失败", e);
                return "读取失败";
            }

        } catch (IOException e) {
            logger.error("读取失败", e);
            return "读取失败";
        }
    }

    public static String getWebIp(String strUrl) {
        try {
            URL url = new URL(strUrl);
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            String s = "";
            StringBuffer sb = new StringBuffer("");
            String webContent = "";
            while ((s = br.readLine()) != null) {
                sb.append(s + "\r\n");
            }
            br.close();
            webContent = sb.toString();
            int start = webContent.indexOf("[") + 1;
            int end = webContent.indexOf("]");
            webContent = webContent.substring(start, end);
            return webContent;
        } catch (Exception e) {
            logger.error("error open url:", e);
            return "error open url:" + strUrl;
        }
    }

    // 比较两个对象立面所有的值，不包括父类的，compareFields:指定比较的属性名称
    public static boolean compareValue(Object obj1, Object obj2, @SuppressWarnings("rawtypes") Class cls, Set<String> compareFields) {
        boolean result = false;
        if (obj1 == null || obj2 == null) {
            result = false;
            return result;
        }

        if (obj1.getClass() != cls || obj2.getClass() != cls) {
            result = false;
            return result;
        }

        try {
            Field[] fields = cls.getDeclaredFields();
            if (fields != null && fields.length > 0) {
                for (Field field : fields) {
                    if (compareFields != null) {
                        if (compareFields.add(field.getName())) {
                            compareFields.remove(field.getName());
                            continue;
                        }
                    }

                    Object valueByFieldName1 = ReflectUtils
                            .getValueByFieldName(obj1, field.getName());

                    Object valueByFieldName2 = ReflectUtils
                            .getValueByFieldName(obj2, field.getName());
                    if (valueByFieldName1 == null || valueByFieldName2 == null) {
                        result = false;
                        System.out.print("------------------------------------------------------------------------(" + field.getName() + ")比较的两个值是null------------------------------------------------------------------------");
                    } else if (valueByFieldName1 instanceof String) {
                        result = valueByFieldName1.equals(valueByFieldName2);
                    } else if (valueByFieldName1 instanceof Integer) {
                        result = valueByFieldName1.equals(valueByFieldName2);
                    } else if (valueByFieldName1 instanceof Long) {
                        result = valueByFieldName1.equals(valueByFieldName2);
                    } else if (valueByFieldName1 instanceof BigDecimal) {
                        result = ((BigDecimal) valueByFieldName1).compareTo((BigDecimal) valueByFieldName2) == 0;
                    } else if (valueByFieldName1 instanceof Date) {
                        result = ((Date) valueByFieldName1).compareTo((Date) valueByFieldName2) == 0;
                    } else if (valueByFieldName1.getClass() == int.class) {
                        result = valueByFieldName1 == valueByFieldName2;
                    } else if (valueByFieldName1.getClass() == long.class) {
                        result = valueByFieldName1 == valueByFieldName2;
                    }

                    if (!result) {
                        return result;
                    }
                }
            }
        } catch (SecurityException | NoSuchFieldException
                | IllegalArgumentException | IllegalAccessException e) {
            logger.error("比较值错误", e);
        }
        return true;
    }

    public static String getIMAppkey() {
        return PropertyUtil.getImEnv().getString(Constant.IM_APP_ENV_APPKEY);
    }

    public static String getIMSecretkey() {
        return PropertyUtil.getImEnv().getString(Constant.IM_APP_ENV_SECRET);
    }

    public static String listToString(List<String> list) {
        if (list == null) return null;
        return String.join(",", list);
    }


    public static void initPagingParameter(PagingParameter pagingParameter) {
        if (pagingParameter != null) {
            if (pagingParameter.getPageIndex() == 0) {
                pagingParameter.setPageIndex(1);
            }
            pagingParameter.init();
        }
    }

    public static double changeUnitoT(Double amount, String unit) {
        double changedAmount = 0;
        switch (unit) {
            case "T":
            case "吨":
                changedAmount = amount;
                break;
            case "KG":
            case "千克":
                changedAmount = amount / 1000;
                break;
            case "G":
            case "克":
                changedAmount = amount / 1000000;
                break;
            default:
                break;
        }
        return changedAmount;
    }

    public static int getWeight(int page, int index) {
        return ((Constant.WEIGHTMAX - (page - 1)) * Constant.WEIGHTMAX) + (Constant.WEIGHTMAX - (index - 1));
    }


    public static Map getLngAndLat(String address) {
        Map map = new HashMap();
        String urlStr = "http://api.map.baidu.com/geocoder/v2/?address=" + address + "&output=json&ak=F454f8a5efe5e577997931cc01de3974";
        urlStr = urlStr.replaceAll("\r|\n", "");
        try {
            URL url = new URL(urlStr);
            URLConnection conn = url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            String line = null;
            StringBuffer result = new StringBuffer();
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            reader.close();
            String ipAddr = result.toString();

            JSONObject obj = JSONObject.parseObject(ipAddr);
            if (obj.get("status").toString().equals("0")) {
                double lng = obj.getJSONObject("result").getJSONObject("location").getDouble("lng");
                double lat = obj.getJSONObject("result").getJSONObject("location").getDouble("lat");
                map.put("lng", getDecimal(lng));
                map.put("lat", getDecimal(lat));
            } else {
                logger.debug("未找到相匹配的经纬度！");
            }


        } catch (Exception e) {
            logger.error("读取失败", e);
        }

        return map;
    }

    public static double getDecimal(double num) {
        if (Double.isNaN(num)) {
            return 0;
        }
        BigDecimal bd = new BigDecimal(num);
        num = bd.setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();
        return num;
    }

    public static String calculateCantonCode(String cantonCode) {
        if (StringUtils.isNotBlank(cantonCode)) {
            if (cantonCode.endsWith("0000")) {
                cantonCode = cantonCode.substring(0, cantonCode.lastIndexOf("0000"));
            } else if (cantonCode.endsWith("00")) {
                cantonCode = cantonCode.substring(0, cantonCode.lastIndexOf("00"));
            }
        }
        return cantonCode;

    }

    public static boolean isNotEmpty(List list) {
        if (list == null || list.size() == 0) {
            return false;
        }
        return true;
    }

    public static void generateStaticHtml(String paramName,JSONObject data, String filePath) {
        FileWriter wr = null;
        try {
            String path = MakeHtml.getTomcatPath() + filePath;
            File file = new File(path);
            if (!file.exists()) {               //判断是否存在
                file.createNewFile();         //新建文本
            }
            wr = new FileWriter(file);//将File类型文本wr，封装成FileWriter类型
            wr.write(paramName + data.toJSONString() + ";");      //将当前活动信息，写入wr文本中
            wr.flush();
        } catch (IOException e) {
            logger.error("生成静态文件时异常", e);
        } finally {
            if (wr != null) {
                try {
                    wr.close();   //关闭流
                } catch (IOException e) {
                    logger.error("关闭流时异常", e);
                }

            }
        }

    }
    public static void generateStaticHtml(String paramName, JSONArray data, String filePath) {
        FileWriter wr = null;
        try {
            String path = MakeHtml.getTomcatPath() + filePath;
            File file = new File(path);
            if (!file.exists()) {               //判断是否存在
                file.createNewFile();         //新建文本
            }
            wr = new FileWriter(file);//将File类型文本wr，封装成FileWriter类型
            wr.write(paramName + data.toJSONString() + ";");      //将当前活动信息，写入wr文本中
            wr.flush();
        } catch (IOException e) {
            logger.error("生成静态文件时异常", e);
        } finally {
            if (wr != null) {
                try {
                    wr.close();   //关闭流
                } catch (IOException e) {
                    logger.error("关闭流时异常", e);
                }

            }
        }

    }

}
