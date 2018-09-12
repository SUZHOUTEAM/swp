var index={
		ticketId:'',isLogin:false,entType:'',
		getParam:function(paraName) {
			var search = document.location.search,
				reg = new RegExp("[?&]+" + paraName + "=([^&]+)");
			if (search && reg.test(search)) {
				return decodeURIComponent(RegExp['$1']).replace(/\+/g, " ");
			}
			return null;
		},
    	publish:function () {
            index.isLogin=true;
            var str='INDEXDISPOSALWASTE';
            if(index.isLogin&&index.entType=='PRODUCTION'){
                collectingUserBehavior(index.ticketId,str);
                window.location='../../../entRelease/entWasteList.htm?ticketId='+index.ticketId;
            }else if(index.isLogin&&index.entType=='FACILITATOR'){
                collectingUserBehavior(index.ticketId,str);
                window.location='../../../facilitator/publish.htm?ticketId='+index.ticketId;
            }else{
                collectingUserBehavior(index.isLogin?index.ticketId:'',str);
                // var ticketId=index.ticketId?('?ticketId='+index.ticketId):'';
                window.location='../../../login.jsp';
            }
        },
		bindMenuEvent:function () {
			$('#menu-home,.logo a').click(function () {
                collectingUserBehavior(index.ticketId,'TOPMEANU_INDEX');
			});
			$('#menu-company').click(function () {
                collectingUserBehavior(index.ticketId,'TOPMEANU_DISPOSITION');
			});
			$('#menu-activity').click(function () {
                collectingUserBehavior(index.ticketId,'TOPMEANU_ACTIVITY')
			});
			$('#menu-information').click(function () {
                collectingUserBehavior(index.ticketId,'TOPMEANU_WASTEINFO');
			});
			$('#menu-czDesc').click(function () {
                collectingUserBehavior(index.ticketId,'TOPMEANU_DISPOSAL');
			});
			$('#menu-tmall').click(function () {
                var ticketStr=(index.ticketId?('?ticketId='+index.ticketId):'');
                collectingUserBehavior(index.ticketId,'TOPMEANU_FLAGSHIP');
				if(index.isLogin){
					window.location.href = "/swp/wastecircle/tmall.htm" + ticketStr
				}else{
					window.location.href = "../../../tmall.html"
				}
			});
            $('.register-btn').click(function () {
                collectingUserBehavior('','REGISTER');
            })
		},
		initvalue:function(){
			$.ajax({
                url: "../../../user/getTicketId",
                data: {
                    'ticketId': localStorage.ticketId,
                    'passWord': localStorage.password,
                    'phoneNum': localStorage.phoneNo,
                },
				type: "POST",
				dataType: 'json',
				async: false,
				success: function(data) {
					// var ssoOpen=data.data.ssoOpen;
					// if(ssoOpen=='1'){//单点服务器开启
					// 	var platformInfo=JSON.parse(data.data.matchPlatform);
					// 	var str="";
					// 	var ticketStr=(index.ticketId?('?ticketId='+index.ticketId):'');
					// 	var arr=platformInfo;
					// 	for (var i=0;i<arr.length;i++) {
					// 		str+='<li><a href="'+arr[i]['url']+ticketStr+'" title="'+arr[i]['name']+'" id="helpcenter">'+arr[i]['name']+'</a></li>';
					// 	}
					// 	$('.header .container >ul').append(str);
					// }
                    localStorage.ticketId = data.data.ticketId;
					var baseVo=data.data.user;
					if(baseVo != null ){
						index.isLogin=true;
                        index.entType=baseVo.entType;
                        index.enterpriseName=baseVo.enterpriseName;
                        index.phoneNo=baseVo.phoneNo;
                        $('.no-login').hide();
                        $('.is-login').show();
                        if(!index.entType&&baseVo.userType=='99'){
                            $('.is-login a').attr('href','../../../enterprisemanagement/list.htm?ticketId='+index.ticketId);
						}else{
                        	if(index.entType=='FACILITATOR'){
                                $('.is-login a').attr('href','../../../facilitator/cfList.htm?ticketId='+index.ticketId);
                            }else if(index.entType=='PRODUCTION'){
                                $('.is-login a').attr('href','../../../wastecircle/tmall.htm?ticketId='+index.ticketId);
							}else{
                                $('.is-login a').attr('href','../../../wastecircle/init.htm?ticketId='+index.ticketId);
                            }
						}
                        $('#userName').html(baseVo.userName+'！');
                        document.cookie='uid='+baseVo.phoneNo+';path=/swp';
                        document.cookie='sdktoken='+baseVo.imToken+';path=/swp';
                        document.cookie='nickName='+escape(baseVo.userName)+';path=/swp';
					}
				}
			});
		},
		init:function(){
			this.ticketId = localStorage.ticketId;
			if(this.ticketId){
                this.initvalue();
			}
			this.bindMenuEvent();
		}
};
$(function() {
	index.init();
});