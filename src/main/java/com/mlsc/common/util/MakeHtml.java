package com.mlsc.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * 生成静态文件
 */
public class MakeHtml {

    private final static Logger log = LoggerFactory.getLogger(MakeHtml.class);

    /**
     * 将动态url生成静态页，并写入本地磁盘
     *
     * @param pageurl 动态页url
     * @param fileUrl 静态页保存位置全路径（例如：D:/tomcat/webapps/index.html）
     */
    public static void makeHtml(String pageurl, String fileUrl) {
        BufferedReader br = null;
        OutputStreamWriter bw = null;
        try {
            URL url = new URL(pageurl);
            URLConnection conn = url.openConnection();// 获得连接

            br = new BufferedReader(new InputStreamReader(
                    conn.getInputStream(), StringUtils.DEFAULT_ENCODING));
            bw = new OutputStreamWriter(
                    new FileOutputStream(fileUrl), StringUtils.DEFAULT_ENCODING);
            String str = null;
            while ((str = br.readLine()) != null) {
                bw.write(str);
            }
            bw.flush();
        } catch (MalformedURLException e) {
            log.error("生成静态页时发生错误：提供的Url错误！", e);
        } catch (IOException e) {
            log.error("生成静态页时发生错误：IO读写错误！", e);
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    log.error("关闭OutputStreamWriter时异常！", e);
                }

            }
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    log.error("关闭BufferedReader时异常", e);
                }

            }
        }
    }

    /**
     * 获取tomcat应用服务安装地址
     * @return
     */
    public static String getTomcatPath() {
        String path = MakeHtml.class.getResource("/").getPath();//得到d:/tomcat/webapps/工程名WEB-INF/classes/路径
        path = path.substring(1, path.indexOf("/swp/"));//从路径字符串中取出工程路劲
        System.out.println(path);
        return '/' + path;
    }

    public static String getPath(HttpServletRequest request) {
        String path = request.getServletContext().getRealPath("/");
        path = path.replaceAll("\\\\", "/");
        return path;
    }
}
