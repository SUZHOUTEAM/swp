package com.mlsc.waste.utils;


public class MapUtil {

    public static final double DEF_PI = 3.14159265359; // PI
    public static final double DEF_2PI= 6.28318530712; // 2*PI
    public static final double DEF_PI180= 0.01745329252; // PI/180.0
    public static final double DEF_R =6370693.5; // radius of earth
    
    /**
     * 百度地图
     * 获取给定经纬度和半径距离的经纬度范围
     * @param lon 经度
     * @param lat 纬度
     * @param raidus 单位:m
     * @return 数组 minLng, minLat, maxLng, maxLat
     */
    public static double[] getAround(double lon, double lat, int raidus) {
 
        Double latitude = lat;//纬度
        Double longitude = lon;//经度
 
        Double degree = (24901 * 1609) / 360.0;
        double raidusMile = raidus;//范围
 
        Double dpmLat = 1 / degree;
        Double radiusLat = dpmLat * raidusMile;
        Double minLat = latitude - radiusLat;
        Double maxLat = latitude + radiusLat;
 
        Double mpdLng = Math.abs(degree * Math.cos(latitude * (Math.PI / 180)));
        Double dpmLng = 1 / mpdLng;
        Double radiusLng = dpmLng * raidusMile;
        Double minLng = longitude - radiusLng;
        Double maxLng = longitude + radiusLng;
 
        return new double[] { minLng, minLat, maxLng, maxLat };
    }
    
   /**
    * 根据两点的经纬度算出两点距离
    * @param lon1
    * @param lat1
    * @param lon2
    * @param lat2
    * @return
    */
    public static double getDistance(double lon1, double lat1, double lon2, double lat2) {
        double ew1;
        double ns1;
        double ew2;
        double ns2;
        double distance;
        // 角度转换为弧度
        ew1 = lon1 * DEF_PI180;
        ns1 = lat1 * DEF_PI180;
        ew2 = lon2 * DEF_PI180;
        ns2 = lat2 * DEF_PI180;
        // 求大圆劣弧与球心所夹的角(弧度)
        distance = Math.sin(ns1) * Math.sin(ns2) + Math.cos(ns1) * Math.cos(ns2) * Math.cos(ew1 - ew2);
        // 调整到[-1..1]范围内，避免溢出
        if (distance > 1.0){
            distance = 1.0;
        } else if (distance < -1.0){
            distance = -1.0;
        }
              
        // 求大圆劣弧长度
        distance = DEF_R * Math.acos(distance);
        return distance;
    }
    
    public static String getDistanceStr(double distance) {
        if(distance != Constant.DISTANCE_INFINITY*1000){
            if(Math.round(distance/100d)/10d>0){
                return  String.valueOf(Math.round(distance/100d)/10d)+"公里";
            }else {
                return String.valueOf(Math.round(distance))+"米";
               
            }
        }else{
            return null;
        }
    }
}
