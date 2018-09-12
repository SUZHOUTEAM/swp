package com.mlsc.yifeiwang.sms.controller;

import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/websocket/{channel}/{account}")
public class SysNoticeWSController {
    private Logger logger = Logger.getLogger(SysNoticeController.class);
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    private String account;
    private String channel;

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
    private static Map<String,List<SysNoticeWSController>> channels  = new ConcurrentHashMap<String,List<SysNoticeWSController>>();
    /**
     * 连接建立成功调用的方法
     * @param session  可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen
    public void onOpen(@PathParam("account") String account,@PathParam("   channel") String channel, Session session) {
        this.session = session;
        this.account=account;
        this.channel=channel;

        if(channels.get(channel)==null){
            List<SysNoticeWSController> clients = new ArrayList<SysNoticeWSController>();
            clients.add(this);
            channels.put(channel,clients);
        }else{
            channels.get(channel).add(this);
        }
        sendMessageAll(String.valueOf(channels.get(channel).size()),channel);
        System.out.println("已连接");
    }

    @OnClose
    public void onClose(){
        List<SysNoticeWSController> clients = channels.get(channel);
        if(clients!=null && clients.size()>0 ){
            Iterator<SysNoticeWSController> it = clients.iterator();
            while(it.hasNext()){
                SysNoticeWSController controller = it.next();
                if(controller.account.equals(this.account)){
                    it.remove();
                    channels.put(channel,clients);
                    sendMessageAll(String.valueOf(channels.get(channel).size()),channel);
                    break;
                }
            }
        }
    }

    @OnMessage
    public void onMessage(String message) throws IOException {
//        User user = LoginStatusUtils.getUserByTicketId(ticketId);
//        ISysNoticeService sysNoticeService= ContextLoader.getCurrentWebApplicationContext().getBean(ISysNoticeService.class);
//        int count = sysNoticeService.getUnreadNoticeCount(user.getUserId());
        sendMessageAll(message,this.channel,this.account);
    }

//    public static void sendMessageTo(String message,String channel, String To) throws IOException {
//        for (SysNoticeWSController item : clients.values()) {
//            if (item.userId.equals(To) )
//                item.session.getAsyncRemote().sendText(message);
//        }
//    }


    public static void sendMessageAll(String message,String channel,String account) throws IOException {
        List<SysNoticeWSController> clients = channels.get(channel);
        for (SysNoticeWSController item :clients) {
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("message",message);
            jsonObject.put("account",account);
            item.session.getAsyncRemote().sendText(jsonObject.toJSONString());
        }
    }
    public static void sendMessageAll(String count,String channel) {
        List<SysNoticeWSController> clients = channels.get(channel);
        for (SysNoticeWSController item :clients) {
            item.session.getAsyncRemote().sendText(count);
        }
    }

}
