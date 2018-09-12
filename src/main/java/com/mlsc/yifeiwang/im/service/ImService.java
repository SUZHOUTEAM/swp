package com.mlsc.yifeiwang.im.service;

import com.alibaba.fastjson.JSONArray;
import com.mlsc.rpc.thrift.api.dto.RPCSysUser;
import com.mlsc.yifeiwang.im.model.ImNotice;

import java.util.List;

public interface ImService {

    JSONArray getFriendslist(String phoneNo);

    JSONArray queryChatLog(String faccid, String phoneNo);

    List<ImNotice> getChatLogList(String ticketId);

    JSONArray getUinfos(String phoneNo);

    JSONArray getUserStaus(String accid, String phoneNo);

    String genterateIMToken(String phoneNo,String userName) throws Exception;

    String addFriend(String ticketId, String phoneNo, String receivedAccId, RPCSysUser receivedUser) throws Exception;

    boolean checkIsFriend(String phoneNo, String receivedAccId) throws Exception;

    void sendInitIMMsg(String ticketId, String phoneNo, String receivedAccId, RPCSysUser receivedUser) throws Exception;

    String refreshToken(String phoneNo) throws Exception;

	boolean updateUserInfo(String phoneNo, String userName) throws Exception;
}
