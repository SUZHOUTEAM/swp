<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%
	String appPath=request.getContextPath();
	String ticketId = (String)request.getAttribute("ticketId");
%>
<%------- 导入页头信息 -------%>
<jsp:include page="/common/webCommon/user_top.jsp" flush="true">
	<jsp:param name="title" value="旗舰店" />
</jsp:include>
<jsp:include page="/common/webCommon/facilitatorMenu.jsp" flush="true">
	<jsp:param name="menuId" value="#flagshipStore" />
</jsp:include>
<link rel="stylesheet" href="<%=appPath%>/main/common/elementui/index.css">
<link rel="stylesheet" href="<%=appPath%>/main/pc/css/money-dialog.css?1">
<link rel="stylesheet" href="<%=appPath%>/css/wastecircle/flagship.css">
<section style="margin-bottom: 0 !important;padding-bottom: 50px">
	<div id="app" v-cloak>
			<div class="banner">
				<div class="banner-content">
					<el-row>
						<el-col :span="18" class="banner-title">易废网江苏旗舰店
							<a href="javascript:" class="chat-btn" @click="contact()" title="联系TA">
								联系TA</a>
						</el-col>
						<el-col :span="6" align="right">
						</el-col>
					</el-row>
					<el-row style="margin-top: 24px">
						<el-col :span="18" class="banner-subtitle">提供危废取样，报价，合同签订，危废转移及危废管家服务</el-col>
						<el-col :span="6" align="right" class="banner-tel">
							<i class="tel"></i>0512-62717018
						</el-col>
					</el-row>
				</div>
			</div>
			<div class="panel panel-body" v-if="list.length>0">
				<div class="service-title"><i class="partner-icon"></i><b>金牌合作伙伴</b><em>提供危废处置服务</em></div>
				<div class="cardList" v-loading="listLoading">
					<div class="cardItem" v-for="(item,index) in list" :key="index" :class="{'first':index==0}">
						<el-card :body-style="{ padding: '0px' }">
							<div class="image" v-loading="!item.imgLoaded" element-loading-spinner="el-icon-loading"
								 element-loading-background="rgba(208, 225,255, 1)"><img v-if="item.imgLoaded" :src="item.logo"/>
							</div>
							<div style="padding: 6px 14px;">
								<div class="entName">{{item.entName}}</div>
								<div class="address"><i class="address_icon"></i>{{item.entAddress}}</div>
								<div class="entContent">
									<i class="xukezheng_icon"></i>
									<b>{{item.dispositionType?item.dispositionType.split(',')[0]:'--'}}</b>
									<span class="split">|</span>
									<b>有效期：{{item.licenceEndDate}}</b>
									<%--<span style="margin-left: 20px;text-overflow: ellipsis;white-space: nowrap;overflow:hidden;text-overflow: ellipsis;width: 177px;display: inline-block;"><b :title="item.dispositionType">{{item.dispositionType}}</b></span>--%>
								</div>
							</div>
						</el-card>
					</div>
					<div class="clear"></div>
				</div>
				<div class="other"><span class="left-point">·</span>及其它17家合作伙伴<span class="right-point">·</span></div>
			</div>
			<div class="panel panel-body">
				<div class="service-title"><i class="service-icon"></i><b>管家服务</b><em>满足环保要求、避免处罚风险、规范管理危废</em></div>
				<div class="package-list">
					<div class="package-item">
						<div class="package-header-first">尊享服务</div>
						<div>危废仓库建设及改造咨询</div>
						<div>危废仓库规范化管理</div>
						<div>危废包装规范化管理</div>
						<div>危废年度管理计划申报托管</div>
						<div>危废月度申报托管</div>
						<div>危废应急制度编制及演练</div>
						<div>危废业务知识培训</div>
						<div>危废管理制度编制</div>
					</div>
					<div class="package-item">
						<div class="package-header" id="gong">
							<div class="package-header-title"><i class="header-icon gong"></i>公爵套餐</div>
							<div class="package-header-price"><b>2万</b>/年</div>
							<a href="javascript:" class="package-header-btn" @click="checkEntAccount(1)">购买</a>
						</div>
						<div><i class="star light"></i></div>
						<div><i class="star light"></i></div>
						<div><i class="star light"></i></div>
						<div><i class="star light"></i></div>
						<div><i class="star light"></i></div>
						<div><i class="star light"></i></div>
						<div><i class="star light"></i></div>
						<div><i class="star light"></i></div>
					</div>
					<div class="package-item active" id="bo">
						<div class="package-header">
							<div class="commend">荐</div>
							<div class="package-header-title"><i class="header-icon bo"></i>伯爵套餐</div>
							<div class="package-header-price"><b>1万</b>/年</div>
							<a href="javascript:" class="package-header-btn focus" @click="checkEntAccount(2)">购买</a>
						</div>
						<div><i class="star light"></i></div>
						<div><i class="star light"></i></div>
						<div><i class="star light"></i></div>
						<div><i class="star light"></i></div>
						<div><i class="star light"></i></div>
						<div><i class="star gray"></i></div>
						<div><i class="star gray"></i></div>
						<div><i class="star light"></i></div>
					</div>
					<div class="package-item" id="hou">
						<div class="package-header">
							<div class="package-header-title"><i class="header-icon hou"></i>候爵套餐</div>
							<div class="package-header-price"><b>6000</b>/年</div>
							<a href="javascript:" class="package-header-btn" @click="checkEntAccount(3)">购买</a>
						</div>
						<div><i class="star gray"></i></div>
						<div><i class="star gray"></i></div>
						<div><i class="star light"></i></div>
						<div><i class="star light"></i></div>
						<div><i class="star gray"></i></div>
						<div><i class="star gray"></i></div>
						<div><i class="star gray"></i></div>
						<div><i class="star gray"></i></div>
					</div>
					<div class="clear"></div>
				</div>
			</div>
		<el-dialog v-cloak
				class="money-dialog"
				title=""
				:visible.sync="moneyShow"
				center>
			<div class="money-dialog-bg">
				<div class="money-text1" v-if="paySuccess"><i class="yinhao"></i><span style="color: #7b3e04;font-weight: bold;">你已成功购买{{packageList[package-1]['value']}}！</span><i class="yinhao"></i></div>
				<div class="money-text1" v-else><i class="yinhao"></i>您需要花费<b>{{money}}个</b>易废币<i class="yinhao"></i></div>
				<div class="money-text2"  v-if="paySuccess">稍后将有工作人员联系您确认后续服务事项并签订合同。</div>
				<div class="money-text2" v-else>来购买该套餐。</div>
				<div class="money-text3">当前账户余额：<b>{{myBitAmount}}</b>个易废币</div>
				<div class="money-btn-div">
					<el-button class="confirm-btn" v-if="myBitAmount>=money&&!paySuccess" @click="confirmPay()">确定支付</el-button>
					<el-button class="confirm-btn" v-if="myBitAmount<money&&!paySuccess" @click="recharge()">立即充值</el-button>
					<el-button class="recharge-btn" v-if="paySuccess" @click="goMyBit()">立即查看</el-button>
					<el-button class="cancel-btn" type="text" @click="moneyShow = false;package=2;">取 消</el-button>
				</div>
				<div class="money-time-tip" v-if="paySuccess">{{remainTime}}秒后自动关闭</div>
			</div>
		</el-dialog>
	</div>
</section>
<!-- 先引入 Vue -->
<script src="<%=appPath%>/main/common/elementui/vue.min.js"></script>
<!-- 引入组件库 -->
<script src="<%=appPath%>/main/common/elementui/index.js"></script>
<script src="<%=appPath %>/app/js/constants.js?1"></script>
<script src="<%=appPath %>/main/common/resLoader.js"></script>
<script>
    var vue=new Vue({
        el: '#app',
        data: {
            list:[],
            listLoading:true,
            packageList:packageList,
            package:2,
            moneyShow:false,
            paySuccess:false,
            myBitAmount:0,
            remainTime:5,
            money:0
        },
        created:function() {
            this.getList();
        },
        mounted:function () {
            $(".package-item:not(:first-child)").hover(function(){
                $(this).addClass("active").siblings().removeClass('active');
            });
            $(".package-list").mouseleave(function(){
                if(vue.package==2){
                    $('#bo').addClass("active").siblings().removeClass('active');
				}
            })
        },
        methods:{
            getList:function () {
                ajax({
                    url:'/enterpriseConfiguration/listEnterpriseInfoByCantonCode.htm',
                    data:{
                        pageIndex:1,
                        pageSize:9,
                        releaseEntId:globalInit.enterpriseId,
                        cantonCode:localStorage.province
                    },
                    success:function (result) {
                        console.log(result);
                        vue.listLoading=false;
                        if(result.status==1){
                            var list=[];
                            for(var i in result.data.enterpriseModelList){
                                var obj=result.data.enterpriseModelList[i];
                                if(obj['fileId']){
                                    var imgUrl=IMGVIEWURL+obj['fileId'];
                                    obj['logo']=imgUrl;
                                }else{
                                    obj['logo']='main/pc/img/ent_default_short.png';
                                }
                                obj['imgLoaded']=false;
                                vue.getLoaderEnt(obj['logo'],i).start();
                                list.push(obj);
                            }
                            vue.list=list;
                        }
                    }
                })
            },
            getLoaderEnt:function(imgUrl,index){
                var loader = new resLoader({
                    resources:[imgUrl],
                    onStart: function(total) {
                    },
                    onProgress: function(current, total) {
                        // $('.loading').html('正在加载中...'+Math.floor(current*100/total)+'%');
                    },
                    onComplete: function(total) {
                        var item=vue.list[index];
                        if(item){
                            item.imgLoaded=true;
                            Vue.set(vue.list,index,item);
                        }
                    }
                });
                return loader;
            },
            goEntDetail:function (entId,entName) {
				collectingUserBehavior('<%=ticketId%>','VIEWDISPOSOTIONENT',entName,'旗舰店');
				window.location.href='<%=appPath%>/enterprise/czDetail.htm?enterpriseId='+entId+'&ticketId=<%=ticketId%>&breadcrumbName=本地旗舰店';
            },
            checkEntAccount:function (package) {
                this.package=package;
                vue.paySuccess=false;
                vue.money=this.packageList[this.package-1]['price'];
                ajax({
                    url:'/entBitcionAccount/checkEntAccount.htm?ticketId=<%=ticketId%>',
                    data:JSON.stringify({
                        consumeAmount:this.money
                    }),
                    contentType:'application/json',
                    success:function (result) {
                        console.log(result);
                        if(result.status==1){
                            vue.myBitAmount=result.data.bitcion;
                            vue.moneyShow=true;
                        }
                    }
                });
            },
            confirmPay:function () {
                ajax({
                    url:'/entBindServe/saveBindServe.htm?ticketId=<%=ticketId%>',
                    data:JSON.stringify({
                        consumeAmount:this.money,
//                        bindServiceId:this.currentReleaseId,
                        serviceType:this.packageList[this.package-1]['code'],
						remark:'购买'+this.packageList[this.package-1]['value']
                    }),
                    contentType:'application/json',
                    success:function (result) {
                        console.log(result);
                        if(result.status==1){
                            vue.paySuccess=true;
                            var timer=setInterval(function () {
                                vue.remainTime-=1;
                                if(vue.remainTime==0){
                                    clearInterval(timer);
                                    vue.moneyShow=false;
                                    vue.package='';
                                }
                            },1000);
                        }
                    }
                });
            },
			contact:function () {
                getEnterpriseContacts(facilitatorEnt.entId,facilitatorEnt.entName,'旗舰店');
            },
            goInquiry:function () {
                window.location='<%=appPath%>/entRelease/entWasteList.htm?ticketId=<%=ticketId%>';
            },
            recharge:function () {
                window.location='<%=appPath%>/personaluser/recharge.htm?ticketId=<%=ticketId%>';
            },
            goMyBit:function () {
                window.location='<%=appPath%>/personaluser/myBit.htm?ticketId=<%=ticketId%>';
            }
        }
    })
</script>
<%------- 导入底部信息 -------%>
<jsp:include page="/common/webCommon/bottom.jsp" flush="true" />

<%------- 结束导入底部信息 -------%>
</body>