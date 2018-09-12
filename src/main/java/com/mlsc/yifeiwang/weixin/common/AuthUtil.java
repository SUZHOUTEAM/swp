package com.mlsc.yifeiwang.weixin.common;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by user on 2018/7/17.
 */
public class AuthUtil {
    public static JSONObject doGetJson(String url) throws IOException {
        JSONObject jsonObject = null;
        //首先初始化HttpClient对象
        DefaultHttpClient client = new DefaultHttpClient();
        //通过get方式进行提交
        HttpGet httpGet = new HttpGet(url);
        //通过HTTPclient的execute方法进行发送请求
        HttpResponse response = client.execute(httpGet);
        //从response里面拿自己想要的结果
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            jsonObject = JSONObject.parseObject(EntityUtils.toString(entity, "utf-8"));
        }
        //把链接释放掉
        httpGet.releaseConnection();
        return jsonObject;
    }
}
