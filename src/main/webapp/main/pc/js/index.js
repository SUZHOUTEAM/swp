var index={
		ticketId:'',isLogin:false,entType:'',process:true,
		getParam:function(paraName) {
			var search = document.location.search,
				reg = new RegExp("[?&]+" + paraName + "=([^&]+)");
			if (search && reg.test(search)) {
				return decodeURIComponent(RegExp['$1']).replace(/\+/g, " ");
			}
			return null;
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
                    window.location.href = "tmall.html"
                }
            });
            $('.register-btn').click(function () {
                collectingUserBehavior('','REGISTER');
            })
		},
		initvalue:function(){
			$.ajax({
                url: "./user/getTicketId",
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

					var baseVo=data.data.user;
                    localStorage.ticketId = data.data.ticketId;
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
					if(baseVo != null ){
						index.isLogin=true;
						index.entType=baseVo.entType;
						if(!index.entType&&baseVo.userType=='99'){
                            $('.is-login a').attr('href','enterprisemanagement/list.htm?ticketId='+index.ticketId);
						}else{
                            if(index.entType!='PRODUCTION'){
                                if(typeof vue!='undefined'){
                                    vue.btnShow=false;
                                }
                            }
                            if(index.entType=='FACILITATOR'){
                                $('.is-login a').attr('href','facilitator/cfList.htm?ticketId='+index.ticketId);
                            }else{
                                $('.is-login a').attr('href','wastecircle/init.htm?ticketId='+index.ticketId+'');
							}
                        }
                        var inquiryGuide=localStorage.inquiryGuide;
                        if(index.entType=='PRODUCTION'&&(!inquiryGuide||inquiryGuide.indexOf(baseVo.userId)==-1)&&typeof vue!='undefined'){
							vue.guideShow=true;
						}
						$('.no-login').hide();
                        $('.is-login').show();
						$('#userName').html(baseVo.userName+'！');
                        document.cookie='uid='+baseVo.phoneNo+';path=/swp';
                        document.cookie='sdktoken='+baseVo.imToken+';path=/swp';
                        document.cookie='nickName='+escape(baseVo.userName)+';path=/swp';
                    }
                    index.process=false;
				}
			});
		},
		init:function(){
			this.ticketId=localStorage.ticketId;
			if(!this.ticketId){
				index.process=false;
			}else{
                this.initvalue();
			}
			this.bindMenuEvent();
		}
};
$(function() {
	index.init();
});