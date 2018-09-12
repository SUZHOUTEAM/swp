package com.mlsc.yifeiwang.im.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mlsc.rpc.thrift.api.dto.RPCSysUser;
import com.mlsc.rpc.thrift.api.service.ISysUserService.Iface;
import com.mlsc.rpc.thrift.client.IRPCServiceClient;
import com.mlsc.yifeiwang.sms.common.CheckSumBuilder;
import com.mlsc.yifeiwang.im.common.YunXinConstants;
import com.mlsc.yifeiwang.im.common.YunXinStatusCode;
import com.mlsc.yifeiwang.im.model.ImNotice;
import com.mlsc.yifeiwang.im.service.ImService;
import com.mlsc.waste.user.model.User;
import com.mlsc.waste.utils.LoginStatusUtils;
import com.mlsc.waste.utils.Util;
import com.mlsc.waste.wastecircle.model.EnterpriseInfo;
import com.mlsc.waste.wastecircle.service.WasteCircleService;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SuppressWarnings("deprecation")
@Service("imService")
public class ImServiceImpl implements ImService {

    private Logger logger = Logger.getLogger(ImServiceImpl.class);
    @Autowired
    private IRPCServiceClient client;

    @Autowired
    private WasteCircleService wasteCircleService;

    public static final String appKey = Util.getIMAppkey();

    public static final String appSecret = Util.getIMSecretkey();

    public JSONObject executeAction(String actionUrl, List<NameValuePair> nvps){
        JSONObject jObject = null;
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();

        try(CloseableHttpClient httpClient = httpClientBuilder.build()) {
            HttpPost httpPost = new HttpPost(actionUrl);
            String nonce = "12345";
            String curTime = String.valueOf(System.currentTimeMillis() / 1000L);
            String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce, curTime);// 参考
            // 设置请求的header
            httpPost.addHeader("AppKey", appKey);
            httpPost.addHeader("Nonce", nonce);
            httpPost.addHeader("CurTime", curTime);
            httpPost.addHeader("CheckSum", checkSum);
            httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
            try {
                httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
                HttpResponse response = httpClient.execute(httpPost);
                jObject = JSONObject.parseObject(EntityUtils.toString(response.getEntity(), "utf-8"));
            } catch (Exception e) {
                logger.error("executeAction", e);
            }
        } catch (IOException e) {
            logger.error("executeAction", e);
        }
        return jObject;
    }

    @Override
    public JSONArray getFriendslist(String phoneNo) {
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("accid", phoneNo));
        nvps.add(new BasicNameValuePair("createtime", "1443599631111"));
        JSONArray jObjectArray = new JSONArray();
        try {
            JSONObject jsonObject = executeAction(YunXinConstants.IM_FRIEND_GET_ACTION, nvps);
            if (null != jsonObject && jsonObject.get("friends") != null){
                jObjectArray = JSONObject.parseArray(jsonObject.get("friends").toString());
            }
        } catch (Exception e) {
            logger.error("getFriendslist", e);
        }
        return jObjectArray;
    }
    @Override
    public List<ImNotice> getChatLogList(String ticketId) {
        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        String phoneNo = user.getPhoneNo();
        List<ImNotice> imNoticeList = new ArrayList<ImNotice>();
        // 获取好友列表
        try {
            JSONArray friendsList = getFriendslist(phoneNo);
            for (Object jObjectItem : friendsList) {
                JSONObject jsonObject = (JSONObject) jObjectItem;
                // 获取好友id
                String faccid = jsonObject.get("faccid").toString();
                // 根据好友id获取企业信息
                Iface userServiceManager = client.getUserServiceManager();
                RPCSysUser userInfoByLoginName = userServiceManager.queryUserInfoByPhoneNum(Util.uuid32(), faccid);
                User friendUser = new User();
                friendUser.setUserId(userInfoByLoginName.getUserId());
                EnterpriseInfo enterpriseInfoByUserId = wasteCircleService.getEnterpriseInfoByUserId(ticketId, friendUser);
                // 获取我和好友的聊天记录
                JSONArray chatLog = queryChatLog(faccid, phoneNo);
                if (chatLog.size() > 0) {
                    ImNotice imnotice = getImNotice(enterpriseInfoByUserId, chatLog, faccid);
                    imNoticeList.add(imnotice);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imNoticeList;
    }

    @Override
    public JSONArray queryChatLog(String faccid, String phoneNo) {
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("from", faccid));
        nvps.add(new BasicNameValuePair("to", phoneNo));
        nvps.add(new BasicNameValuePair("begintime", "1443599631111"));
        nvps.add(new BasicNameValuePair("endtime", String.valueOf((new Date()).getTime())));
        nvps.add(new BasicNameValuePair("limit", "100"));
        JSONArray jObjectArray = new JSONArray();
        try {
            JSONObject jsonObject = executeAction(YunXinConstants.IM_QUERY_SESSION_MSG_ACTION, nvps);
            if (null != jsonObject) {
                jObjectArray = JSONObject.parseArray(jsonObject.get("msgs").toString());
            }
        } catch (Exception e) {
            logger.error("queryChatLog", e);
        }
        return jObjectArray;
    }

    private ImNotice getImNotice(EnterpriseInfo enterpriseInfoByUserId, JSONArray chatLog, String faccid) throws Exception {
        ImNotice imnotice = new ImNotice();
        JSONObject jObject = (JSONObject) chatLog.get(0);
        JSONObject getJObjectByBody = (JSONObject) jObject.get("body");
        String latestChatLog = toString(getJObjectByBody.get("msg")).trim();
        String picUrl = toString(getJObjectByBody.get("url")).trim();
        String time = toString(jObject.get("sendtime")).trim();
        Date date = new Date(Long.parseLong(time));
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
        imnotice.setChatTime(sdf.format(date));
        if(getJObjectByBody.get("ext")!=null&&getJObjectByBody.get("ext").equals("aac")){
            imnotice.setLatestChatLog("【语音】");
        }else{
            imnotice.setLatestChatLog(latestChatLog);
        }
        imnotice.setPicUrl(picUrl);
        imnotice.setPhoneNum(faccid);
        if (enterpriseInfoByUserId != null) {
            imnotice.setEntName(enterpriseInfoByUserId.getEnterName());
            imnotice.setEntId(enterpriseInfoByUserId.getId());
        }
        return imnotice;
    }

    private String toString(Object object) throws Exception {
        return object == null ? "" : object.toString();
    }

    @Override
    public String genterateIMToken(String phoneNo, String userName) throws Exception  {
        String token  = "";
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("accid", phoneNo));
        nvps.add(new BasicNameValuePair("name", userName));
        try {
            JSONObject jsonObject = executeAction(YunXinConstants.IM_USER_CREATE_ACTION, nvps);/*{ "code":200,  "info":{"token":"xx","accid":"xx","name":"xx"} }*/
            YunXinStatusCode statusCode = YunXinStatusCode.getStatusCode(jsonObject);
            if(null != statusCode){
                if (statusCode.equals(YunXinStatusCode.SUCCESS)) {
                    token = jsonObject.getJSONObject("info").getString("token");
                }else if(statusCode.equals(YunXinStatusCode.ALREADY_REGISTER_ERROR)){
                    token = refreshToken(phoneNo);
                }else if(statusCode.equals(YunXinStatusCode.CURTIME_EXPIRED_ERROR)){

                }
            }
        } catch (Exception e) {
            logger.error("genterateIMToken",e);
        }
        return token;
    }

    private boolean isNotNullAndSuccess(YunXinStatusCode statusCode) {
        return null != statusCode && statusCode.equals(YunXinStatusCode.SUCCESS);
    }

    @Override
    public JSONArray getUinfos(String phoneNo) {
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        JSONArray accids = new JSONArray();
        accids.add(phoneNo);
        nvps.add(new BasicNameValuePair("accids", accids.toJSONString()));
        JSONArray jObjectArray = new JSONArray();
        try {
            JSONObject jsonObject = executeAction(YunXinConstants.IM_GET_UINFOS_ACTION, nvps);
            YunXinStatusCode statusCode = YunXinStatusCode.getStatusCode(jsonObject);
            if (isNotNullAndSuccess(statusCode)){
                jObjectArray = jsonObject.getJSONArray("uinfos");
            }
        } catch (Exception e) {
            logger.error("getUinfos", e);
        }
        return jObjectArray;
    }

    @Override
    public JSONArray getUserStaus(String accid, String phoneNo) {
        JSONArray publisherAccids = new JSONArray();
        publisherAccids.add(phoneNo);
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("accid", accid));
        nvps.add(new BasicNameValuePair("eventType", "1"));
        nvps.add(new BasicNameValuePair("publisherAccids", publisherAccids.toJSONString()));
        nvps.add(new BasicNameValuePair("ttl", "60"));
        JSONArray jObjectArray = new JSONArray();
        try {
            JSONObject jsonObject = executeAction(YunXinConstants.IM_SUBSCRIBE_ADD_ACTION, nvps);
            YunXinStatusCode statusCode = YunXinStatusCode.getStatusCode(jsonObject);

            if (isNotNullAndSuccess(statusCode)) {
                jObjectArray = jsonObject.getJSONArray("uinfos");
            }
        } catch (Exception e) {
            logger.error("getUserStaus", e);
        }
        return jObjectArray;
    }

    @Override
    public String addFriend(String ticketId, String phoneNo, String receivedAccId, RPCSysUser receivedUser) throws Exception {
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("accid", phoneNo));
        nvps.add(new BasicNameValuePair("faccid", receivedUser.getPhoneNum()));
        nvps.add(new BasicNameValuePair("type", String.valueOf(1)));
        String result = "0";// 给返回结果一个默认值,返回成功还是失败
        try {
            JSONObject jsonObject = executeAction(YunXinConstants.IM_FRIEND_ADD_ACTION, nvps);
            YunXinStatusCode statusCode = YunXinStatusCode.getStatusCode(jsonObject);
            if (isNotNullAndSuccess(statusCode)) {
                result = statusCode.getCode();
            }
        } catch (Exception e) {
            logger.error("addFriend", e);
            throw new Exception(e);
        }
        return result;
    }

    @Override
    public boolean checkIsFriend(String phoneNo, String receivedAccId) throws Exception {
        boolean isFriend = false;
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("accid", phoneNo));
        nvps.add(new BasicNameValuePair("createtime", "1443599631111"));
        try {
            JSONObject jsonObject = executeAction(YunXinConstants.IM_FRIEND_GET_ACTION, nvps);
            YunXinStatusCode statusCode = YunXinStatusCode.getStatusCode(jsonObject);
            if (isNotNullAndSuccess(statusCode) && !jsonObject.getString("size").equals("0")) {
                isFriend = isFriend(receivedAccId, jsonObject);
            }
        } catch (Exception e) {
            logger.error("checkIsFriend", e);
            throw new Exception(e);
        }
        return isFriend;
    }
    
    @Override
    public boolean updateUserInfo(String phoneNo, String userName) throws Exception {
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("accid", phoneNo));
        nvps.add(new BasicNameValuePair("name", userName));
        try {
            JSONObject jsonObject = executeAction(YunXinConstants.IM_USER_UPDATEUSERINFO_ACTION, nvps);
            YunXinStatusCode statusCode = YunXinStatusCode.getStatusCode(jsonObject);
            if (isNotNullAndSuccess(statusCode) ) {
                return true;
            }
        } catch (Exception e) {
            logger.error("update userinfo error: ", e);
            throw new Exception(e);
        }
        return false;
    }

    private boolean isFriend(String receivedAccId,  JSONObject jsonObject) {
        boolean isFriend = false;
        List<JSONObject> jObjectArray = JSONObject.parseArray(jsonObject.getString("friends"), JSONObject.class);
        for (JSONObject jfriend : jObjectArray) {
            String faccid = jfriend.getString("faccid");
            if (faccid.equals(receivedAccId)) {
                isFriend = true;
                break;
            }
        }
        return isFriend;
    }

    @Override
    public void sendInitIMMsg(String ticketId, String phoneNo, String receivedAccId, RPCSysUser receivedUser) throws Exception {
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("ope", String.valueOf(0)));
        nvps.add(new BasicNameValuePair("type", String.valueOf(0)));
        nvps.add(new BasicNameValuePair("from", phoneNo));
        nvps.add(new BasicNameValuePair("to", receivedUser.getPhoneNum()));
        nvps.add(new BasicNameValuePair("body", "{'msg':'你好'}"));
        try {
            JSONObject jsonObject = executeAction(YunXinConstants.IM_SEND_MSG_ACTION, nvps);
            YunXinStatusCode statusCode = YunXinStatusCode.getStatusCode(jsonObject);

            if (isNotNullAndSuccess(statusCode)){
                logger.debug(jsonObject.toString());
            }
        } catch (Exception e) {
            logger.error("sendInitIMMsg", e);
            throw new Exception(e);
        }
    }
    @Override
    public String refreshToken(String phoneNo) throws Exception {
        String token  = "";
        List<NameValuePair> nvps = new ArrayList<NameValuePair>(1);
        nvps.add(new BasicNameValuePair("accid", phoneNo));
        try {
            JSONObject jsonObject = executeAction(YunXinConstants.IM_REFRESH_TOKEN_ACTION, nvps);/*{ "code":200,  "info":{"token":"xx","accid":"xx","name":"xx"} }*/
            YunXinStatusCode statusCode = YunXinStatusCode.getStatusCode(jsonObject);
            if (isNotNullAndSuccess(statusCode)) {
                token = jsonObject.getJSONObject("info").getString("token");
            }
        } catch (Exception e) {
            logger.error("refreshToken",e);
        }
        return token;
    }
}
