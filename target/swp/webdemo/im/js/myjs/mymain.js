var mymain ={
        ticketId:'',publisherId:'',appPath:'',
        getParam: function (paraName) {
            var search = document.location.search,
                reg = new RegExp("[?&]+" + paraName + "=([^&]+)");
            if (search && reg.test(search)) {
                return decodeURIComponent(RegExp['$1']).replace(/\+/g, " ");
            }
            return null;
        },
        
        init:function(){
            this.ticketId = this.getParam("ticketId");
            this.publisherId = this.getParam("publisherId");
            this.appPath = this.getParam("appPath");
//          this.getUinfos();
            userUID = readCookie("uid");
			yunXin = new YX(userUID);
			yunXin.openMyChatBox(this.publisherId,"p2p");
			this.showHistory();
        },
        parseData:function(data){
        	var _this=this;
        	var chatLog=data.data.chatLog||[];
    		var fromUserLog=data.data.fromUserLog;
    		var toUserLog=data.data.toUserLog;
    		var str='';
    		var meFace=_this.appPath+"/webdemo/im/images/default-icon.png";
			if(fromUserLog.length>0&&fromUserLog[0].businessCode&&fromUserLog[0].fileId){
				 meFace = IMG_VIEW_URL+'&businessCode=' + fromUserLog[0].businessCode + '&fileID=' + fromUserLog[0].fileId;
			}
    		var youFace=_this.appPath+"/webdemo/im/images/default-icon.png";
			if(toUserLog.length>0&&toUserLog[0].businessCode&&toUserLog[0].fileId){
				 youFace = IMG_VIEW_URL+'&businessCode=' + toUserLog[0].businessCode + '&fileID=' + toUserLog[0].fileId;
			}
			var len=chatLog.length<5?chatLog.length:5;
			if(len==0){
    		 	str = '<div class="no-msg tc"><span class="radius5px">暂无消息</span></div>';
			}
    		for (var i=0;i<len;i++) {
    			var obj=chatLog[i];
    			var timeStr='';
    			var flag=obj.from==_this.publisherId;//true是对方  false是自己
    			if(i == 0){
                        timeStr += _this.makeTimeTag(transTime(new Date(obj.sendtime)));
                }else{
                    if(chatLog[i-1].sendtime-obj.sendtime>5*60*1000){
                        timeStr += _this.makeTimeTag(transTime(new Date(obj.sendtime)));
                    }
                }
    			str='<div class="item item-'+(flag?'you':'me')+'">'+
						'<img class="img j-img" src="'+(flag?youFace:meFace)+'">'+
						'<div class="msg msg-text j-msg">'+
							'<div class="box">'+
								'<div class="cnt">'+
									_this.getMsgBody(obj.body)+
								'</div>'+
							'</div>'+
						'</div></div>'+str;
				str=timeStr+str;
    		}
    		$('#chatContent').html(str);
			setTimeout(function(){
				$("#chatContent").scrollTop(9999);
			},500);
        },
        makeTimeTag : function(time){
	    	return '<p class="u-msgTime">- - - - -&nbsp;'+time+'&nbsp;- -- - -</p>';
		},
        format: function (time) {
	        var now = new Date(time);
	        var LSTR_Year = now.getYear() > 1000 ? now.getYear() : (now.getYear() + 1900);
	        var LSTR_Month = now.getMonth() + 1;
	        var LSTR_Date = now.getDate();
	        var LSTR_Hour=now.getHours();
	        var LSTR_Minutes=now.getMinutes();
	        if (LSTR_Date == 0) {
	            LSTR_Date += 1;
	        }
	        return LSTR_Year + '-'+this.parseNum(LSTR_Month)+ '-'+this.parseNum(LSTR_Date)+' '+this.parseNum(LSTR_Hour)+':'+this.parseNum(LSTR_Minutes);
	    },
	    parseNum:function(num){
	    	return num<10?('0'+num):num;
	    },
        showHistory:function(){
        	$.ajax({
	   	        url:this.appPath+"/imService/queryChatLog.htm?num="+new Date().getTime(),
	   	        data:{
	   	        	ticketId:this.ticketId,
	   	        	toId:this.publisherId
	   	        },
	   	        type: "POST",
	   	        dataType: 'json',
	   	        async: true,
	   	        success: function(data) {
	   	        	mymain.parseData(data);
	   	        }
   	        });
        },
        getMsgBody:function(body){
			var str='';
			var msg=body.msg;
			if(msg){
				str=this.getEmojiHtml(msg);
			}else if(body.ext&&body.ext=='aac'){
				str='<div class="u-audio j-mbox left">'+
						'<a href="javascript:;" class="j-play playAudio" data-dur="'+body.dur+'" data-src="'+body.url+'">点击播放</a>' +
						'<b class="j-duration">'+Math.floor(body.dur/1000)+'"</b><span class="u-icn u-icn-play" title="播放音频"></span>' +
					'</div>';
			}else if(body.ext&&body.url){
				str='<a href="'+body.url+'" target="_blank"><img src="'+body.url+'"></a>'
			}
			return str;
		},
		getEmojiHtml:function(text) {
			var re = /\[([^\]\[]*)\]/g;
			var matches = text.match(re) || [];
			for (var j = 0, len = matches.length; j < len; ++j) {
				if(emoji[matches[j]]){
					text = text.replace(matches[j], '<img class="emoji" src="'+this.appPath+'/webdemo/im/images/emoji/' + emoji[matches[j]].file + '" />');
				}		
			}
			return text;
		},
        getUinfos:function(){
            $.ajax({
                url: this.appPath+"/im/getUinfos.htm?ticketId="+this.ticketId+"&phoneNo="+this.publisherId,
                type: "POST",
                dataType: 'json',
                async: false,
                success: function(result) {
                    if(result.status == 1){
                        userUID = readCookie("uid");
                        yunXin = new YX(userUID);
                        yunXin.openMyChatBox(mymain.publisherId,"p2p");
                        alert(result.data.userStaus[0]);
                        $("#headImg").attr('src',result.data.uinfos[0].icon);
                        $("#nickName").html(result.data.enterpriseInfo.enterName +":"+result.data.uinfos[0].name);
                        
                    }
                   
                }
            });
            
        }
        
        
}
$(document).ready(function(){
	mymain.init();
})
var userUID,yunXin;
