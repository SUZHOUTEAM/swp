<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage=""%>
<%
    String appPath = request.getContextPath();
    String ticketId = (String)request.getAttribute("ticketId");
%>

<jsp:include page="/common/webCommon/user_top.jsp" flush="true">
	<jsp:param name="title" value="资源池" />
</jsp:include>
<jsp:include page="/common/webCommon/wasteCircleMenu.jsp" flush="true">
	<jsp:param name="menuId" value="#myIndex" />
</jsp:include>
<link href="<%=appPath %>/thirdparty/city-picker/css/city-picker.css" rel="stylesheet" type="text/css" />
<script src="<%=appPath %>/thirdparty/city-picker/js/city-picker.data.js"></script>
<script src="<%=appPath %>/thirdparty/city-picker/js/city-picker.js"></script>
<link rel="stylesheet" href="<%=appPath%>/main/common/elementui/index.css">
<link rel="stylesheet" href="<%=appPath %>/css/wastecircle/index.css?3">
<link rel="stylesheet" href="<%=appPath %>/thirdparty/treeview/jquery.treeview.css"/>
<script src="<%=appPath %>/thirdparty/treeview/jquery.treeview.js"></script>
<!-- 我的易废圈所使用的css文件 -->
<style>
	.textarea_label {
		float: left;
		vertical-align: top;
	}
	textarea.remark-textarea{
		padding:0px!important;
		width: 90%!important;
	}
	.activityDiv{
		width: 900px;
		margin: 10% auto;
		border-radius: 3px;
		position: relative;
	}
	.activityDiv .el-button--text{
		position: absolute;
		z-index: 1000;
		right: 10px;
		color: #666;
	}
	.priceDiv{
		position: absolute;
		left: 0;
		right: 0;
		margin: 221.5px auto 0;
		width: 767px;
		text-align: left;
	}
	.price-title{
		color: #fff04b;
		font-weight: bold;
		font-size: 24px;
	}
	.price-text{
		color: #ff474d;
		margin-right:112px;
		font-size: 24px;
		position: relative;
	}
	.price-text b{
		font-size: 72px;
		position: absolute;
		font-family: SimHei;
		top: -50px;
	}
	.old-price-text{
		text-decoration: line-through;
		color: #336ef6;
		font-size: 24px;
	}
	.live-tip{
		position: absolute;
		bottom: 59px;
		width: 1200px;
		text-align: center;
		margin: 0 auto;
		left: 0;
		right: 0;
		color: #c4c6ff;
		font-size: 20px;
		font-weight: bold;
	}
	.remainTime{
		 position: absolute;
		 left: 0;
		 right: 0;
		 margin: 0 auto;
		 text-align: center;
		 color: #ff5056;
		 font-weight: bold;
		 top: 279px;
		 font-size: 16px;
	 }
</style>
<section>
	<div class="el-breadcrumb">
        <span class="el-breadcrumb__item">
            <span class="el-breadcrumb__item__inner">资源池</span>
        </span>
	</div>
	<div class="addFriend_loading"><img src="<%=appPath %>/main/pc/img/load.gif"></div>
	<div class="treeview_container">
		<ul id="navigation"></ul>
	</div>
	<!--<div id="msg">已成功加入购物车</div>-->
	<div class="message-container">
		<div class="messages">
			<div class="searchDiv">
				<div class="pull-left">
					<div class="input-group input-group_css">
						<input type="text" placeholder="企业名称" id="entName" class="enterCss" value="${entName}" autocomplete="off" style="border-radius: 4px;">
						<div class="input-group-btn">
							<button type="button" class="btn btn-default dropdown-toggle form-control" data-toggle="" style="display: none;">
                                    <span class="caret"></span>
                                </button>
							<ul class="dropdown-menu" role="menu">
							</ul>
						</div>
					</div>
					<div class="input-group input-group_css">
						<input type="text" placeholder="危废代码" readonly id="codeWaste" class="eightCode treeview_btn" value="${eightCode}" autocomplete="off" style="border-radius: 4px;">
						<div class="input-group-btn">
							<button type="button" class="btn btn-default dropdown-toggle form-control" data-toggle="" style="display: none;">
                                    <span class="caret"></span>
                                </button>
							<ul class="dropdown-menu" role="menu">
							</ul>
						</div>
					</div>
					<div class="input-group input-group_css">
						<input type="text" class="area" placeholder="区域" id="_area" value="${area}" data-toggle="city-picker">
					</div>
					<!-- 隐藏框保存企业和八位码  entName -->
					<input type="hidden" id="ent" name="ent" value="">
					<button type="button" id="search" class="btn btn-seek">搜索</button>
				</div>
				<div class="shaixuan">
					<%--<span class="check1Span"><input type="checkbox" id="check1" checked="checked"/><label for="check1">可处置</label></span>
					<span class="check2Span"><input type="checkbox" id="check2"><label for="check2">已关注</label></span>--%>
					<a href="javascript:;" id="myFavorite">我的收藏</a>
					<a href="javascript:;" id="distance_btn" class="active">距离<i class="fa fa-long-arrow-down"></i></a>
					<a href="javascript:;" id="reset_btn" class="reset">重置</a>
				</div>
				<div class="clear"></div>
			</div>
			<div class="list">

			</div>
			<div id="noEntReminder" style="padding-top:80px;text-align: center;display: none">
				<img src="../main/pc/img//noEntReminder.png">
				<div style="font-size: 16px;margin-top:40px;color:#6a7580;">您还没有添加企业，请先添加企业！
				<br>
					将自动跳转至企业绑定页面！
				</div>
			</div>
			<div class="loading"><img src="<%=appPath %>/main/pc/img/load.gif"></div>
		</div>

		<%-- <div class="myinfo">
			<p class="logo"><img src="<%=appPath %>/main/pc/img/company_default.png"></p>
		<p class="cname"></p>
		<p class="ccode"></p>
		<p class="enterType"><span class="enterprise-type-badge "></span></p>
		<p class="total_count">
			<span><label>0</label><a href="<%=appPath %>/wastecircle/initMyPublish.htm?ticketId=<%=ticketId %>" title="我的资源单"><font>发布</font></a></span>
			<span><label>0</label><a href="<%=appPath %>/follow/myFollow.htm?ticketId=<%=ticketId %>" title="我的关注"><font>关注</font></a></span>
			<span><label>0</label><a href="<%=appPath %>/orders/myOrders.htm?ticketId=<%=ticketId %>" title="我的订单"><font>业务</font></a></span>
		</p>
	</div> --%>
		<div class="activityDialog" id="app">
			<div class="activityDiv">
				<el-button type="text" icon="el-icon-close" @click="closeActivityDialog">关闭</el-button>
				<el-carousel  arrow="hover" :autoplay="true" :interval="5000" height="480px" :indicator-position="activityList.length>1?'':'none'" v-if="activityList.length>0">
					<el-carousel-item v-for="(item,index) in activityList" :key="index" :style="'background: url('+item.logo+') center center /auto 100% no-repeat'">
						<div class="remainTime" v-if="item.enrollFee>0&&item.currentTime<item.riseEndDate" v-cloak>
							众筹截止时间：{{item.riseEndDateStr.substring(0,16)}}
						</div>
						<div class="priceDiv" v-if="item.enrollFee>0&&item.currentTime<item.riseEndDate" v-cloak>
							<span class="price-title">众筹优惠</span>
							<span class="price-text">￥<b>{{item.enrollFee*item.discount}}</b></span>
							<span class="old-price-text">原价{{item.enrollFee}}</span>
						</div>
						<div class="priceDiv" v-if="item.enrollFee>0&&item.currentTime>=item.riseEndDate" v-cloak>
							<span class="price-title">报名费用</span>
							<span class="price-text">￥<b>{{item.enrollFee}}</b></span>
						</div>
						<div class="timeDiv" v-if="!item.hidden" v-cloak>
							<span class="time"><small>{{item.remainText||'剩余时间'}}：</small><b v-if="item.remainTimeText" v-cloak>{{item.remainTimeText}}</b></span>
						</div>
						<a href="javascript:;" v-if="item.enrollFee>0" class="activity_btn" @click="goEnrollFee(item.activityId,item.activityName)">查看详情></a>
						<a href="javascript:;" v-if="item.videoResource&&item.enrollFee==0" class="activity_btn" @click="goLive(item.videoResource,item.activityName)">在线观看</a>
						<a href="javascript:;" v-if="!item.videoResource&&item.enrollFee==0" class="activity_btn" @click="goActivityDetail(item.activityId,item.activityName)">查看详情></a>
						<div class="live-tip" v-if="item.enrollFee>0">易废网专家--郑仁华，为你详细解读环境法</div>
					</el-carousel-item>
				</el-carousel>
			</div>
		</div>
</section>
<script type="text/javascript">
	var appPath = '<%=appPath%>';
</script>
<script src="<%=appPath%>/thirdparty/node_modules/readmore.min.js"></script>
<%--<script src="<%=appPath%>/thirdparty/parsleyjs/dist/parsley.min.js"></script>--%>
<script type="text/javascript" src="<%=appPath%>/app/js/constants.js"></script>
<script src="<%=appPath%>/thirdparty/bootstrap-suggest-plugin-master/dist/bootstrap-suggest.js"></script>
<script src="<%=appPath%>/thirdparty/bootstrap-icheck/icheck.min.js"></script>
<%--<script src="<%=appPath%>/main/common/fly/jquery.fly.min.js"></script>--%>
<script src="<%=appPath%>/app/js/constants.js"></script>
<script src="<%=appPath%>/main/common/jquery.form.js"></script>
<!-- 先引入 Vue -->
<script src="<%=appPath%>/main/common/elementui/vue.min.js"></script>
<!-- 引入组件库 -->
<script src="<%=appPath%>/main/common/elementui/index.js"></script>
<%--<script type="text/javascript" src="<%=appPath%>/main/pc/js/resourcePool.js"></script>--%>
<script type="text/javascript" src="<%=appPath%>/main/pc/js/wastecircle.js?9"></script>
<script>
    var vue = new Vue({
        el: '#app',
        data:{
            activityList:[],
        },
        created:function () {
            this.getCurrentActivity();
        },
        methods:{
            getCurrentActivity:function () {
                ajax({
                    url:'/wasteActivity/getCurrentActivity',
                    data:{
                        cantonCode:returnCitySN['cid'].substring(0,2)
                    },
                    success:function (result) {
                        console.log(result);
                        if(result.status==1&&result.data.activityList&&result.data.activityList.length>0){
                            var activityList=result.data.activityList;
                            var currentDate=result.data.serverTime;
                            for(var i in activityList){
                                var imgUrl=IMG_VIEW_URL+'&fileID='+activityList[i].subjectFileId;
                                activityList[i]['logo']=imgUrl;
                                var longTime=new Date(currentDate.replace(new RegExp("-","gm"),"/")).getTime();
                                var startDate=new Date(activityList[i].startDate.replace(new RegExp("-","gm"),"/")).getTime();
                                var endDate=new Date(activityList[i].endDate.replace(new RegExp("-","gm"),"/")).getTime();
                                if(activityList[i].enrollFee>0){
                                    activityList[i].riseEndDateStr=activityList[i].riseEndDate;
                                    var riseEndDate=new Date(activityList[i].riseEndDate.replace(new RegExp("-","gm"),"/")).getTime();
                                    activityList[i].currentTime=longTime;
                                    activityList[i].startDate=startDate;
                                    activityList[i].endDate=endDate;
                                    activityList[i].riseEndDate=riseEndDate;
                                    vue.createTimerRise(longTime,riseEndDate,startDate,endDate,i);
                                }else{
                                    var date=longTime<startDate||activityList[i].videoResource?startDate:endDate;
                                    vue.createTimer(date,longTime,i);
                                }
                            }
                            vue.activityList=activityList;
							$('.activityDialog').show();
                        }else{
                            vue.activityList=[];
                        }
                    },
                    error:function () {
                        vue.activityList=[];
                    }
                })
            },
            createTimerRise:function (currentTime,riseEndDate,startDate,endDate,index) {
                if(currentTime<riseEndDate){
                    setInterval(function () {
                        currentTime+=1000;
                        var item=vue.activityList[index];
                        if(currentTime<riseEndDate){
                            if(item.remainText!='优惠倒计时'){
                                item.remainText='优惠倒计时';
                                Vue.set(vue.activityList,index,item);
                            }
                            vue.ShowCountDown(riseEndDate,currentTime,index);
                        }else{
                            if(item.remainText!='距离开播'){
                                item.currentTime=riseEndDate;
                                item.remainText='距离开播';
                                Vue.set(vue.activityList,index,item);
                            }
                            vue.ShowCountDown(startDate,currentTime,index);
                        }
                    },1000)
                }else if(currentTime<startDate){
                    setInterval(function () {
                        var item=vue.activityList[index];
                        if(item.remainText!='距离开播'){
                            item.currentTime=riseEndDate;
                            item.remainText='距离开播';
                            Vue.set(vue.activityList,index,item);
                        }
                        currentTime+=1000;
                        vue.ShowCountDown(startDate,currentTime,index);
                    },1000)
                }else{
                    var timer=setInterval(function () {
                        if(vue.activityList.length>0){
                            var item=vue.activityList[index];
                            item.currentTime=riseEndDate;
                            item.remainTimeText='已开始';
                            Vue.set(vue.activityList,index,item);
                            clearInterval(timer);
                        }
                    },1000)
                }
            },
            createTimer:function (endDate,longTime,index) {
                setInterval(function(){
                    longTime+=1000;
                    vue.ShowCountDown(endDate,longTime,index);
                },1000)
            },
            ShowCountDown:function(dateFinal,dateNow,index){
                var dateSub = dateFinal - dateNow;  //计算差值，单位毫秒
                if(dateSub<0){
                    var item=this.activityList[index];
                    item.remainTimeText='已开始';
                    Vue.set(this.activityList,index,item);
                    return;
                }
                var day , hour , minute, second ,dayBase , hourBase, minuteBase, secondBase;
                day = hour = minute = second = dayBase = hourBase = minuteBase = secondBase = 0;  //初始化各个数值
                var timeHtml = '';
                timeHtml += " ";
                dayBase = 24 * 60 * 60 * 1000;  //计算天数的基数，单位毫秒。1天等于24*60*60*1000毫秒
                hourBase = 60 * 60 * 1000;  //计算小时的基数，单位毫秒。1小时等于60*60*1000毫秒
                minuteBase = 60 * 1000;  //计算分钟的基数，单位毫秒。1分钟等于60*1000毫秒
                secondBase = 1000;  //计算秒钟的基数，单位毫秒。1秒钟等于1000毫秒
                day = Math.floor(dateSub / dayBase);  //计算天数，并取下限值。如 5.9天 = 5天
                hour = Math.floor(dateSub % dayBase / hourBase);  //计算小时，并取下限值。如 20.59小时 = 20小时
                minute = Math.floor(dateSub % dayBase % hourBase / minuteBase);  //计算分钟，并取下限值。如 20.59分钟 = 20分钟
                second = Math.floor(dateSub % dayBase % hourBase % minuteBase / secondBase);  //计算秒钟，并取下限值。如 20.59秒 = 20秒
                //当天数小于等于0时，就不用显示
                if(day <= 0){
                    timeHtml += this.toDouble(hour) + ':' + this.toDouble(minute) + ':' + this.toDouble(second) ;
                }else{
                    timeHtml += day + '天 : ' + this.toDouble(hour) + ':' + this.toDouble(minute) + ':' + this.toDouble(second) ;
                }
                var item=this.activityList[index];
                item.remainTimeText=timeHtml;
                Vue.set(this.activityList,index,item);
                // $('.time b').html(timeHtml);
            },
            toDouble:function(num){
                if(num < 10){
                    return '0'+ num;
                }else{
                    return '' + num;
                }
            },
            goActivityDetail:function (activityId,activityName) {
                window.location='<%=appPath%>/wasteActivity/buy.htm?ticketId=<%=ticketId%>&activityId='+activityId;
            },
			goEnrollFee:function (activityId,activityName) {
				collectingUserBehavior(ticketId,'VIEWACTIVITY',activityName);
				window.location=appPath+'/main/pc/view/feeActivityDetail.html?activityId='+activityId+'&ticketId='+ticketId;
			},
            closeActivityDialog:function () {
				$('.activityDialog').hide();
            }
        }
    });
</script>