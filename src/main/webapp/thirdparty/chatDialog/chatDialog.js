var ChatDialog=function(phoneNo){
	this.ticketId=ticketId;
	this.appPath=appPath;
	this.phoneNo=phoneNo;
	//this.init();
}
//拖拽 
ChatDialog.prototype={
	startChat:function(){
		var str='<div class="win">'+
					'<div class="wTop">'+
						'<span class="userlogo"><img src="'+this.appPath+'/webdemo/im/images/default-icon.png""/></span>'+
						'<span class="userName">&nbsp;</span>'+
						'<a href="javascript:;" class="closeDialog" title="关闭">X</a>'+
						'<span class="btn_record">查看云记录</span>'+
					'</div>'+
					'<div class="content">'+
						'<iframe src="" scrolling="no" frameborder="0"></iframe>'+
						'<div class="ent">'+
							'<p class="load"><img src="'+this.appPath+'/main/pc/img/load.gif"></p>'+
						'</div>'+
					'</div>'+
				'</div>';
		var win=$('.win');
		if(win.length>0){
			win.remove();
		}
		$('body').append(str);
		this.dragAndDrop();
		this.initPosition();
		this.clickShowBtn();
		this.loadData();
	},
	loadData:function(){
		var _this=this;
		$.ajax({
   	        url:this.appPath+"/im/getUinfos.htm",
   	        data:{
   	        	ticketId:_this.ticketId,
   	        	phoneNo:_this.phoneNo
   	        },
   	        type: "POST",
   	        dataType: 'json',
   	        async: false,
   	        success: function(data) {
				var enterpriseInfo=data.data.enterpriseInfo;
				if(!enterpriseInfo){
					$('.win').hide();
					var option={status:'danger',timeout:1500};
					$.notify('建立会话失败',option);
					return;
				}
				var uinfos=data.data.uinfos;
				var userInfo=data.data.userInfo;
				var uploadfile=data.data.uploadfile;
				var imgurl=_this.appPath+"/webdemo/im/images/default-icon.png";
				if(enterpriseInfo.businessCode&&enterpriseInfo.fileId){
					 imgurl = IMG_VIEW_URL+'&businessCode=' + enterpriseInfo.businessCode + '&fileID=' + enterpriseInfo.fileId;
				}
				var userHead=_this.appPath+'/webdemo/im/images/default-icon.png';
				if(uploadfile&&uploadfile.length>0){
					var obj=uploadfile[0];
					userHead = IMG_VIEW_URL+'&businessCode=' + obj.businessCode + '&fileID=' + obj.fileId;
					$('.win .wTop .userlogo img').attr('src',userHead);
				}
   	            $('.win .wTop .userName').html(enterpriseInfo.enterName+':'+(userInfo?userInfo['chineseName']:'--'));
   	            var str='<p><img src="'+imgurl+'"></p>'+
						'<p class="entName">'+enterpriseInfo.enterName+'</p>'+
						'<p class="entCode">企业代码：'+enterpriseInfo.enterCode+'</p>'+
						'<p class="entType '+enterpriseInfo.enterType['code']+'">'+enterpriseInfo.enterType['value']+'</p>';
   	            $('.win .content .ent').html(str);
   	            var chatUrl =_this.appPath+"/webdemo/im/mymain.html?ticketId="+_this.ticketId+"&publisherId="+_this.phoneNo+"&appPath="+_this.appPath;
   	            $('.win .content iframe').attr('src',chatUrl);
   	        }
   	    });
	},
	dragAndDrop:function() {
		var _move = false; //移动标记 
		var _x, _y; //鼠标离控件左上角的相对位置 
		$(".wTop").mousedown(function(e) {
			_move = true;
			_x = e.pageX - parseInt($(".win").css("left"));
			_y = e.pageY - parseInt($(".win").css("top"));
			//$(".wTop").fadeTo(20,0.5);//点击开始拖动并透明显示 
		});
		$(document).mousemove(function(e) {
			if(_move) {
				var x = e.pageX - _x; //移动时鼠标位置计算控件左上角的绝对位置 
				var y = e.pageY - _y;
				$(".win").css({ top: y, left: x }); //控件新位置 
			}
		}).mouseup(function() {
			_move = false;
			//$(".wTop").fadeTo("fast",1);//松开鼠标后停止移动并恢复成不透明 
		});
	},
	initPosition:function() {
		//计算初始化位置 
		var itop = ($(window).height() - $(".win").height()) / 2;
		var ileft = ($(window).width() - $(".win").width()) / 1.8;
		//设置被拖拽div的位置 
		$(".win").css({ top: itop, left: ileft });
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
	loadHistoryData:function(){
		var _this=this;
		$.ajax({
   	        url:_this.appPath+"/imService/queryChatLog.htm?num="+new Date().getTime(),
   	        data:{
   	        	ticketId:_this.ticketId,
   	        	toId:_this.phoneNo
   	        },
   	        type: "POST",
   	        dataType: 'json',
   	        async: false,
   	        success: function(data) {
   	        	if(data.status==1){
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
					if(chatLog.length==0){
		    		 	str = '<div class="no-msg tc"><span class="radius5px">暂无消息</span></div>';
					}
   	        		for (var i=0;i<chatLog.length;i++) {
   	        			var obj=chatLog[i];
   	        			var flag=obj.from==_this.phoneNo;//true是对方  false是自己
   	        			str+='<p class="u-msgTime">- - - - -&nbsp;'+_this.format(obj.sendtime)+'&nbsp;- -- - -</p>'+
	   	        			'<div class="item item-'+(flag?'you':'me')+'">'+
								'<img class="img j-img" src="'+(flag?youFace:meFace)+'">'+
								'<div class="msg msg-text j-msg">'+
									'<div class="box">'+
										'<div class="cnt">'+
											_this.getMsgBody(obj.body)+
										'</div>'+
									'</div>'+
								'</div></div>';
   	        		}
   	        		$('#cloudMsgList').html(str);
   	        		$('.u-audio').on('click',function(){
   	        			$(this).addClass('play');
   	        			var time=$(this).find('a').attr('data-dur')*1;
   	        			setTimeout(function(){
   	        				$('.u-audio').removeClass('play');
   	        			},time);
   	        		});
   	        	}
   	        }
   	    });
	},
	getMsgBody:function(body){
		var str='';
		var msg=body.msg;
		if(msg){
			str=getEmojiHtml(msg);
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
	clickShowBtn:function() {//点击显示按钮 
		$(".win .closeDialog").click(function() {
			$(".win").hide();
		});
		var _this=this;
		$('.btn_record').on('click',function(){
			var str='<div class="cloud-msg-container" id="cloudMsgContainer">'+
				      	'<div class="info-box">'+
							'<div class="title tc">'+
								'<button class="btn back-btn radius5px j-backBtn">关闭</button>云记录'+
							'</div>'+
							'<div class="info-content">'+
//								'<div class="u-status tc"><span class="radius5px"><a class="j-loadMore">加载更多记录</a></span></div>'+
								'<ul id="cloudMsgList" class="f-cb"><div class="load"><img src="'+_this.appPath+'/main/pc/img/load.gif"></div></ul>'+
							'</div>'+
						'</div>'+
				      '</div>';
			var cloudMsgContainer=$('#cloudMsgContainer');
			if(cloudMsgContainer.length>0){
				cloudMsgContainer.remove();
			}
			$('body').append(str);
			$('#cloudMsgContainer .back-btn').on('click',function(){
				$('#cloudMsgContainer').remove();
			});
			_this.loadHistoryData();
		});
	}
}
