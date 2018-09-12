<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%
	String appPath=request.getContextPath();
	String ticketId = (String)request.getAttribute("ticketId");
%>
<%------- 导入页头信息 -------%>
<jsp:include page="/common/webCommon/user_top.jsp" flush="true">
	<jsp:param name="title" value="资源池" />
</jsp:include>
<jsp:include page="/common/webCommon/wasteCircleMenu.jsp" flush="true">
	<jsp:param name="menuId" value="#myIndex" />
</jsp:include>
<link rel="stylesheet" href="<%=appPath%>/main/common/elementui/index.css">
<link rel="stylesheet" href="<%=appPath%>/css/wastecircle/style.css?5">
<link rel="stylesheet" href="<%=appPath%>/css/myBusiness/sign.css">
<link rel="stylesheet" href="<%=appPath%>/main/pc/css/money-dialog.css?2">
<script type="text/javascript" src="<%=appPath%>/thirdparty/city-picker/js/city-picker.data.js"></script>
<section style="margin-bottom: 0 !important;">
	<div class="el-breadcrumb">
        <span class="el-breadcrumb__item">
            <span class="el-breadcrumb__item__inner">资源池</span>
        </span>
	</div>
		<div id="app">
			<template>
				<div class="search_bd">
					<!-- 租金 -->
					<dl class="filter_item" id="provice_select">
						<dt>区域：</dt>
						<dd>
							<a href="javascript:" @click="selectProvince('','')" :class="province==''?'select':''">不限</a>
							<a href="javascript:" v-for="(item,index) in provinces" :data-key="item.key" :class="province==item.key?'provinceItem select':'provinceItem'" @click="selectProvince(item.key,item.value)">
								{{item.value}}
							</a>
							<a href="javascript:" class="select" @click="changeAllProvincesShow">{{allProvincesShow?'&lt;收起':'展开全部&gt;'}}</a>
						</dd>
					</dl>
					<dl class="filter_item" id="city_select" v-show="cityClick&&cityList">
						<dt>&nbsp;</dt>
						<dd>
							<a href="javascript:" :class="city==''?'select':''" @click="selectCity('','')">不限</a>
							<a href="javascript:" v-for="(value,key) in cityList" :data-key="key" @click="selectCity(key,value)" :class="city==key?'select':''">
								{{value}}
							</a>
						</dd>
					</dl>

					<dl class="filter_item" id="district_select" v-show="districts">
						<dt>&nbsp;</dt>
						<dd>
							<a href="javascript:" :class="district==''?'select':''" @click="selectDistrict('','')">不限</a>
							<a href="javascript:" v-for="(value,key) in districts" :data-key="key" @click="selectDistrict(key,value)" :class="district==key?'select':''">
								{{value}}
							</a>
						</dd>
					</dl>

					<dl class="filter_item" id="quality_select">
						<dt>按量：</dt>
						<dd>
							<a href="javascript:" :class="{'select':qualityType==0}" @click="selectQuality(0)">不限</a>
							<a href="javascript:" :class="{'select':qualityType==1}" @click="selectQuality(1)">3吨以下</a>
							<a href="javascript:" :class="{'select':qualityType==2}" @click="selectQuality(2)">3-5吨</a>
							<a href="javascript:" :class="{'select':qualityType==3}" @click="selectQuality(3)">5-10吨</a>
							<a href="javascript:" :class="{'select':qualityType==4}" @click="selectQuality(4)">10-30吨</a>
							<a href="javascript:" :class="{'select':qualityType==5}" @click="selectQuality(5)">30-100吨</a>
							<a href="javascript:" :class="{'select':qualityType==6}" @click="selectQuality(6)">100-300吨</a>
							<a href="javascript:" :class="{'select':qualityType==7}" @click="selectQuality(7)">300-1000吨</a>
							<a href="javascript:" :class="{'select':qualityType==8}" @click="selectQuality(8)">1000吨以上</a>
						</dd>
					</dl>
					<!-- 方式 -->
					<dl class="filter_item" id="wasteCode_select" v-show="moreShow">
						<dt>代码：</dt>
						<dd>
							<a href="javascript:" v-for="(item,index) in wasteInfoList" @click="selectWaste(item,index)" class="wasteInfoItem" :class="{'select':item.select}">
								{{item.code}}
							</a>
							<a href="javascript:" class="select" @click="changeAllWasteShow">{{allWasteShow?'&lt;收起':'展开全部&gt;'}}</a>
						</dd>
					</dl>
					<%--<a href="javascript:;" class="more-condition">更多条件></a>--%>
					<dl class="filter_item" id="eightCode_select" v-show="eightCodeList.length>0&&moreShow">
						<dt>&nbsp;</dt>
						<dd>
							<a href="javascript:" id="total_checkbox">
								<span class="icon-checkbox"></span>
								<span class="text">全选</span>
							</a>
							<a href="javascript:" v-for="(item,index) in eightCodeList" @click="selectEightCode(item,index)"
							   :class="{'select':item.select}">
								<span class="icon-checkbox"></span>
								<span class="text">{{item.code}}</span>
							</a>
						</dd>
					</dl>
                    <dl class="filter_item" id="favorite_select" v-show="moreShow">
                        <dt>收藏：</dt>
                        <dd>
                            <a href="javascript:" :class="{'select':favoriteType==false}" @click="selectFavorite(false)">不限</a>
                            <a href="javascript:" :class="{'select':favoriteType==true}" @click="selectFavorite(true)">我收藏的资源</a>
                        </dd>
                    </dl>
                    <dl class="filter_item" v-show="moreShow">
                        <dt>权利：</dt>
                        <dd>
                            <a href="javascript:" :class="{'select':binding==0}" @click="selectBinding(0)">不限</a>
                            <a href="javascript:" :class="{'select':binding==1}" @click="selectBinding(1)">已购买</a>
                            <a href="javascript:" :class="{'select':binding==2}" @click="selectBinding(2)">未购买</a>
                        </dd>
                    </dl>
					<div class="moreBtn" @click="moreShow=!moreShow">
						<em class="bor1"></em>
						<em class="bor2"></em>
						<span>{{moreShow?'收起':'更多筛选'}}<i :class="moreShow?'el-icon-arrow-up':'el-icon-arrow-down'"></i></span>
					</div>
				</div>

				<div id="selected" class="bar" style="display: block;" v-show="provinceName||qualityType!=0||JSON.stringify(wasteCode) !== '{}'">
					<div class="barct">
						<span class="condition">筛选条件：</span>
						<span class="par" v-show="provinceName">
							<em>{{provinceName}}{{cityName?('>'+cityName):cityName}}{{districtName?('>'+districtName):districtName}}</em>
							<a href="javascript:" @click="selectProvince('','')"><i class="el-icon-close"></i></a>
						</span>
						<span class="par" v-show="qualityType!=0">
							<em>{{getQualityTypeText()}}</em>
							<a href="javascript:" @click="selectQuality(0)"><i class="el-icon-close"></i></a>
						</span>
						<span id="wasteCodeCondition" v-show="JSON.stringify(wasteCode) !== '{}'">
								<em>含：(</em>
								<span>
									<span class="par" v-for="(value,key) in wasteCode" v-if="typeof value=='number'||typeof value=='string'">
										<em>{{key}}</em>
										<a href="javascript:" @click="selectWaste(wasteInfoList[value],value)"><i class="el-icon-close"></i></a>
									</span>
									<span v-else>
										<span class="par" v-for="(item,index) in value" v-if="item.select">
											<em>{{item.code}}</em>
											<a href="javascript:" @click="clearEightCode(item)"><i class="el-icon-close"></i></a>
										</span>
									</span>
								</span>
							)
						</span>
						<a href="javascript:" class="clearCondition" @click="clearAll">清空</a>
					</div>
				</div>
				<div class="ent_container">
					<div class="item_container" v-for="(item,index) in list">
						<div class="item">
							<div class="item_logo">
								<img :src="item.imgSrc">
							</div>
							<div class="item_message">
								<p class="item_info">
									<span class="item_name"><span @click="entDetail(item.isPay,item.releaseId,index)">{{item.entName}}</span></span>
									<span class="item_publish"><i class="time_icon"></i>
									发布于{{item.releaseDate.substring(0,7)}}
									</span>
								</p>
								<p class="item_address" v-if="item.entAddress"><i class="publisharea"></i>{{item.entAddress}}<span>（距离{{item.distance}}公里）</span>
								</p>
								<p v-else style="height:10px"></p>
								<p class="total_amount"> 总计：<span> {{item.totalWasteCount}} </span> 种危废，累计<span>{{item.totalWasteAmountDesc}}</span>。</p>
								<div class="itemReleaseList">
									<div class="itemRelease" v-for="(wasteItem,index) in item.releaseWasteDetails">
										<span >{{wasteItem.wasteName}}</span><span>{{wasteItem.wasteCode}}</span><span>{{wasteItem.wasteAmount}}{{wasteItem.unitValue}}</span>
									</div>
								</div>
								<el-row :gutter="10" style="padding-right: 20px;margin-top: 9px;word-wrap: break-word;" v-show="!item.releaseRemark==''">
									<span><b>备注：</b>{{item.releaseRemark}}。</span>
								</el-row>
								<el-row :gutter="10" style="padding-right: 20px;margin-top: 9px;text-align: right">
									<el-button type="text" v-if="item.deleteFlag==1" style="margin-right:10px;cursor: default " >
										询价被撤销
									</el-button>
									<el-button style="cursor: default;margin-right: 29px; " type="text" v-if="item.releaseStatus=='DONE' ||  item.releaseStatus=='END'" >
										{{item.releaseStatusValue}}
									</el-button>
									<el-button type="text" :class="{'favorited':item.favorited,favorite_logo:true}" href="javascript:" @click="favorite(item,index)">
										<i class="favorite"></i>{{item.favorited?'已':''}}收藏
									</el-button>
									<el-button type="text" class="qipao_logo" href="javascript:" v-if="item.facilitatorEntId" @click="contact(item.facilitatorEntId,item.entName,item.isPay,item.releaseId,index)" style="margin-right: 18px;margin-left: 18px">
										<img src="../main/pc/img/qipao.jpg">联系TA</el-button>
									<el-button type="text" class="qipao_logo" href="javascript:" v-else @click="contact(item.releaseEntId,item.entName,item.isPay,item.releaseId,index)" style="margin-right: 18px;margin-left: 18px">
										<img src="../main/pc/img/qipao.jpg">联系TA</el-button>
									<el-button type="text" href="javascript:" style="margin-right:10px" v-if="item.inquiryStatus=='1'"
											   @click="updateInquiry(item,index)">
										<i class="el-icon-circle-check" style="color: #1171d1; font-size: 16px;margin-right: 3px;"></i>已报价
									</el-button>
									<el-button type="text" href="javascript:" class="goDetail" v-show="item.disposalWasteCount && item.inquiryStatus=='0' " @click="buy(item,index)">
										<i class="responsepublish"></i>报价</el-button>
									<el-button type="text" href="javascript:" class="goDetail grey" v-show="!item.disposalWasteCount ">
										<i class="responsepublish"></i>报价</el-button>
								</el-row>
							</div>
						</div>
					</div>
					<div class="loadComplete" v-show="list.length==0&&!listLoading">暂无相关数据</div>
				</div>
				<div class="loadComplete" v-show="loadComplete&&list.length>0">数据加载完成</div>
				<div class="loading" v-show="listLoading&&!loadComplete"><img src="<%=appPath %>/main/pc/img/load.gif"></div>
				<el-dialog
						class="money-dialog"
						title=""
						@close="paySuccess&&payCallback"
						:visible.sync="moneyShow"
						center>
					<div class="money-dialog-bg">
						<div class="money-text1" v-if="paySuccess"><i class="yinhao"></i><span style="color: #7b3e04;font-weight: bold;">支付成功！</span><i class="yinhao"></i></div>
						<div class="money-text1" v-else><i class="yinhao"></i>您需要花费<b>8个</b>易废币<i class="yinhao"></i></div>
						<div class="money-text2"  v-if="paySuccess">您现在可以进行报价。</div>
						<div class="money-text2" v-else>来进行报价。</div>
						<div class="money-text3">当前账户余额：<b>{{myBitAmount}}</b>个易废币</div>
						<div class="money-btn-div">
							<el-button class="confirm-btn" v-if="myBitAmount>=8&&!paySuccess" @click="confirmPay()">确定支付</el-button>
							<el-button class="confirm-btn" v-if="myBitAmount<8&&!paySuccess" @click="recharge()">立即充值</el-button>
							<el-button class="recharge-btn" v-if="paySuccess" @click="cancel">立即查看</el-button>
							<el-button class="cancel-btn" type="text" @click="cancel">取 消</el-button>
						</div>
						<div class="money-time-tip" v-if="paySuccess">{{remainTime}}秒后自动关闭</div>
					</div>
				</el-dialog>
				<el-dialog
						class="fee-bit-dialog"
						title=""
						:visible.sync="freeBitShow"
						center>
					<div class="free-bit-bg facilitator">
						<div class="money-btn-div" style="bottom: 114px">
							<el-button class="confirm-btn" @click="getFreeBitcion()">立即领取</el-button>
						</div>
					</div>
				</el-dialog>
				<el-dialog
						class="no-company-dialog"
						title=""
						width="470px"
						:visible.sync="noCompany"
						center>
					<img src="<%=appPath%>/main/pc/img/noCompany.png" class="noCompany-img"/>
					<div class="no-company-bg">
						<div class="noCompany-text">您还未登记所代理的处置企业，请立即登记，登记后进行报价</div>
						<a href="javascript:" class="noCompany-btn" @click="goAddCustomer">立即登记</a>
					</div>
				</el-dialog>
			</template>
		</div>

		<!-- 先引入 Vue -->
		<script src="<%=appPath%>/main/common/elementui/vue.min.js"></script>
		<!-- 引入组件库 -->
		<script src="<%=appPath%>/main/common/elementui/index.js"></script>
		<script src="<%=appPath %>/app/js/constants.js"></script>
		<script src="<%=appPath %>/thirdparty/la-number/la-number.js"></script>
		<script src="<%=appPath %>/main/pc/js/wasteInfoList.js"></script>
		<script>
            var vue=new Vue({
                el: '#app',
                data: {
                    pageIndex:1,
					pageSize:5,
                    list: [],
                    listLoading:true,
					loadComplete:false,
					provinces:ChineseDistricts['provinces'],
                    allProvincesShow:false,
					province:'',
					cityList:null,
					city:'',
                    districts:null,
                    district:'',
					wasteInfoList:wasteInfoList,
					eightCodeList:[],
                    allWasteShow:false,
					currentCode:'',
					currentIndex:0,
                    wasteCode:{},
					provinceName:'',
					cityName:'',
                    districtName:'',
					areaName:'',
                    cantonCode:'',
                    qualityType:0,
                    favoriteType:false,
                    entDialogVisible:false,
					entParam:{
                        hasLicence:true,
                        hasSalesNote:true,
                        hasSummary:true
					},
                    signDialogVisible:false,
                    signDialogloading:false,
                    signType:'',
                    signTypeList:[],
                    SIGNTYPE:SIGNTYPE,
                    say:'',
                    sampleDate:'',
                    sampleName:'',
                    sampleMobile:'',
                    sampleStatus:'DELIVERED_LAB',
                    signFormShow:false,
                    signItem:{},
                    signList:[],
                    contractStatus:'DRAFTCONTRACT',
                    TAGSTATUS:TAGSTATUS,
                    moneyShow:false,
					paySuccess:false,
					remainTime:3,
					myBitAmount:0,
					currentReleaseId:'',
					currentReleaseIndex:0,
                    binding:0,
                    freeBitShow:false,
					moreShow:false,
					payCallback:function () {},
					noCompany:false,
					cityClick:false
                },
                created:function() {
                    var currentCantonCode = localStorage.province+"0000";

                    this.selectProvince(currentCantonCode,ChineseDistricts['provinces'][currentCantonCode]);
//                    this.getList();
                    this.checkHasCompany();
                    this.checkBitcionAccountExist();
                    var arr1=[],arr2=[];
                    for(var key in this.provinces){
                        if(key=='320000'){
                            arr1.unshift({
                                key:key,
                                value:this.provinces[key]
                            });
						}else if (key == '350000') {
                            arr1.push({
                                key: key,
                                value: this.provinces[key]
                            });
                        }else if(key=='130000'){
                            arr1.push({
                                key:key,
                                value:this.provinces[key]
                            });
						}else{
                            arr2.push({
                                key:key,
                                value:this.provinces[key]
                            });
						}
					}
					this.provinces=arr1.concat(arr2);
                },
                mounted:function () {
                    $('#provice_select dd a.provinceItem:gt(7)').hide();
					$('#wasteCode_select dd a.wasteInfoItem:gt(7)').hide();
//					$('#wasteCode_select').show();
                    var _this=this;
                    $(window).scroll(function () {
                        if(_this.loadComplete){
                            return;
						}
                        var scrollTop=$(this).scrollTop();
                        var scrollHeight=$(document).height();
                        var windowHeight=$(this).height();
                        if(scrollTop+windowHeight+100>=scrollHeight){
                            if(!_this.listLoading){
                                _this.listLoading = true;
//                                setTimeout(function () {
                                    _this.getList();
//                                },1000)
							}
                        }
                    });
                    $('#total_checkbox').on('click',function () {
						var flag=$(this).hasClass('select');
                        var item=vue.wasteInfoList[vue.currentIndex];
						if(flag){
                            $(this).removeClass('select');
                            delete vue.wasteCode[vue.currentCode];
                            item.select=false;
                            Vue.set(vue.wasteInfoList, vue.currentIndex, item);

						}else{
                            $(this).addClass('select');
                            item.select=true;
                            Vue.set(vue.wasteInfoList, vue.currentIndex, item);
                            Vue.set(vue.wasteCode, vue.currentCode, vue.currentIndex);
						}
                        for(var index in vue.eightCodeList){
                            var item=vue.eightCodeList[index];
                            item.select=!flag;
                            Vue.set(vue.eightCodeList, index, item)
						}
                        if(flag&&vue.isAllNotSelectEightCode(vue.eightCodeList)){
                            vue.eightCodeList=[];
                        }
                    });
                    this.cityClick=false;
                },
                methods:{
                    cancel:function () {
                        this.moneyShow = false;
						if(this.paySuccess){
						    this.payCallback()
						}
                    },
                    selectBinding:function (binding) {
						this.binding=binding;
						this.search();
                    },
                    search:function () {
                        this.list=[];
                        this.pageIndex=1;
                        this.listLoading = true;
                        this.loadComplete=false;
						this.getList();
                    },
					getParamInfo:function () {
                        var amountIntervalStr=['','0,3','3,5','5,10','10,30','30,100','100,300','300,1000','1000'][this.qualityType];
                        var param={};
                        var wasteCodeList=this.getWasteCodeList();
                        if(wasteCodeList.length>0){
                            param.wasteCodeList=wasteCodeList;
                        }
                        if(this.cantonCode){
                            param.cantonCodeList=[this.cantonCode];
                        }
                        if(amountIntervalStr){
                            param.amountIntervalStr=[amountIntervalStr];
                        }
                        param.favorited=this.favoriteType;
                        param.binding=['',true,false][this.binding];
                        return param;
                    },
					getWasteCodeList:function () {
                        var arr=[];
						for(var key in this.wasteCode){
						    var value=this.wasteCode[key];
							if(typeof value=='number'||typeof value=='string'){
							    arr.push(key.substring(2,4));
							}else{
								for(var j in value){
								    if(value[j]['select']==true){
										arr.push(value[j]['code']);
									}
								}
							}
						}
						return arr;
                    },
                    getList:function () {
                        var _this=this;
                        var param=this.getParamInfo()||{};
                        ajax({
                            url:'/entRelease/listWasteEntRelease.htm?ticketId='+getParam('ticketId')+'&pageIndex='+(this.pageIndex++)+'&pageSize='+this.pageSize,
                            dataType:'json',
                            contentType:'application/json',
                            data:JSON.stringify(param),
                            success:function (result) {
                                console.log(result);
                                _this.listLoading = false;
                                if(result.status==1&&result.data){
                                    if(result.data.length<_this.pageSize){
										_this.loadComplete=true;
									}
                                    for(var i in result.data){
//                                        result.data[i]['favorite']=false;//默认设置未收藏
                                        if(result.data[i]['fileId']){
                                            result.data[i]['imgSrc']=IMG_VIEW_URL+'&fileID=' + result.data[i]['fileId'];
                                        }else{
                                            result.data[i]['imgSrc']="<%=appPath%>/main/pc/img/company_logo.png";
                                        }
										result.data[i]['isPay']=result.data[i]['entBindStatus']==1;
                                        _this.list.push(result.data[i]);
                                    }
                                }else{

								}
                            }
                        })
                    },
                    updateInquiry:function(item,index){
                        var releaseId = item.releaseId;
                        var facilitatorEntIdStr = item.facilitatorEntId ? ('&facilitatorEntId=' + item.facilitatorEntId) : '';
                        var src = appPath + '/entInquiry/list.htm?ticketId=' + getParam('ticketId') + '&releaseEntId=' + item.releaseEntId ;
                        window.location = src;
                    },
                    getWasteInfo:function () {
                        var _this=this;
						ajax({
							url:'/yifeiwaste/listWasteInfo.htm',
							success:function (data) {
								console.log(data);
								for(var i in data.data){
								    data.data[i]['select']=false;
								    for(var j in data.data[i].wasteList){
                                        data.data[i].wasteList[j]['parentCode']=data.data[i].code;
                                        data.data[i].wasteList[j]['parentIndex']=i;
                                        data.data[i].wasteList[j]['index']=j;
									}
								}
								_this.wasteInfoList=data.data;
                            }
						});
                    },
                    checkBitcionAccountExist:function () {
						ajax({
							url:'/entBitcionAccount/bitcionAccountExist.htm',
							success:function (result) {
								console.log(result);
								if(result.status==1&&!result.data){
									vue.freeBitShow=true;
								}
                            }
						});
                    },
                    getFreeBitcion:function () {
                        ajax({
                            url:'/entBitcionAccount/getFreeBitcion.htm',
                            success:function (result) {
                                if(result.status==1){
                                    $.notify('领取成功',{status:'success',timeout:2000});
                                    vue.freeBitShow=false;
                                }
                            }
                        });
                    },
                    favorite:function (item,index) {
                        var url=item.favorited?'/entFavorite/cancelEntFavorite':'/entFavorite/addEntFavorite';
                        ajax({
                            url:url,
                            data:{
                                entId:window.globalInit.enterpriseId,
                                referenceId:item.releaseId,
                                favoriteType:'MSGID'
                            },
                            success:function (data) {
                                if(data.status==1&&data.data){
                                    var msg=item.favorited?'取消':'';
                                    $.notify(msg+'收藏成功',{status:'success',timeout:1000});
                                    item.favorited=!item.favorited;
                                    Vue.set(vue.list, index, item);
                                    if(vue.favoriteType){
                                        vue.search();
									}
                                }
                            }
                        });
                    },
                    selectFavorite:function (favoriteType) {
                        this.favoriteType=favoriteType;
                        this.search();
                    },
                    checkEntAccount:function () {
                        vue.paySuccess=false;
                        ajax({
                            url:'/entBitcionAccount/checkEntAccount.htm?ticketId=<%=ticketId%>',
                            data:JSON.stringify({
                                consumeAmount:8
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
					updateRemark:function (serviceId,entName) {
                        ajax({
                            url:'/entBindServe/updateBindServe?ticketId=<%=ticketId%>',
                            data:JSON.stringify({
                                id:serviceId,
								remark:'支付“'+entName+'”发布的危废处置权'
                            }),
							contentType:'application/json',
                            success:function (result) {
                                console.log(result);
                                if(result.status==1){

                                }
                            }
                        });
                    },
					showEntBaseInfo:function (serviceId) {
                        var item=this.list[this.currentReleaseIndex];
                        ajax({
							url:'/enterprise/getEnterpriseInfoByEntId',
							data:{
                                entId:item.releaseEntId
							},
							success:function (result) {
								console.log(result);
								if(result.status==1){
                                    item.entName=result.data.entName;
                                    item.entAddress=result.data.entAddress;
                                    item.isPay=true;
                                    Vue.set(vue.list,vue.currentReleaseIndex,item);
                                    vue.updateRemark(serviceId,item.entName);
								}
                            }
						});
                    },
                    confirmPay:function () {
                        ajax({
                            url:'/entBindServe/saveBindServe.htm?ticketId=<%=ticketId%>',
                            data:JSON.stringify({
                                consumeAmount:8,
                                bindServiceId:this.currentReleaseId,
                                serviceType:'RESOURCE_POOL'
                            }),
                            contentType:'application/json',
                            success:function (result) {
                                console.log(result);
                                if(result.status==1){
                                    vue.paySuccess=true;
                                    vue.remainTime=3;
                                    vue.myBitAmount = vue.myBitAmount - 8;
                                    var timer=setInterval(function () {
										vue.remainTime-=1;
										if(vue.remainTime==0){
										    clearInterval(timer);
                                            vue.moneyShow=false;
                                            vue.payCallback&&typeof vue.payCallback=='function'&&vue.payCallback();
										}
                                    },1000);
                                    vue.showEntBaseInfo(result.data);
                                }
                            }
                        });
                    },
                    goAddCustomer:function () {
                        window.location='<%=appPath%>/enterprise/addAgency.htm?ticketId=<%=ticketId%>';
                    },
                    recharge:function () {
                        window.location='<%=appPath%>/personaluser/recharge.htm?ticketId=<%=ticketId%>';
                    },
                    goMyBit:function () {
                        window.location='<%=appPath%>/personaluser/myBit.htm?ticketId=<%=ticketId%>&activeName=second';
                    },
                    contact:function (entId,entName,isPay,releaseId,index) {
                        if(isPay){
                            getEnterpriseContacts(entId,entName,'资源池');
						}else{
                            this.payCallback=function () {
                                collectingUserBehavior('<%=ticketId%>','PC_PAY_CONTRACT',entName);
                                getEnterpriseContacts(entId,entName,'资源池');
                            };
                            this.currentReleaseId=releaseId;
                            this.currentReleaseIndex=index;
                            this.checkEntAccount();
						}
                    },
                    checkHasCompany:function () {
                        ajax({
                            url:'/facilitatorCustomer/listFacilitatorCustomer.htm',
                            dataType:'json',
                            data:{
                                pageIndex:1,
                                pageSize:5
                            },
                            success:function (result) {
                                console.log(result);
                                if(result.status==1){
                                    if(!result.data.customerList||result.data.customerList.length==0){
                                        vue.noCompany=true;
                                    }
                                }
                            }
                        });
                    },
					buy:function (item,index) {
                        if(!item.isPay){
                            this.payCallback=function () {
                                collectingUserBehavior('<%=ticketId%>','PC_PAY_INQUIRY');
                                ajax({
                                    url:'/facilitatorCustomer/listFacilitatorCustomer.htm',
                                    dataType:'json',
                                    data:{
                                        pageIndex:1,
                                        pageSize:5
                                    },
                                    success:function (result) {
                                        console.log(result);
                                        if(result.status==1){
                                            if(!result.data.customerList||result.data.customerList.length==0){
                                                vue.noCompany=true;
                                            }else{
                                                var releaseId=item.releaseId;
                                                var facilitatorEntIdStr=item.facilitatorEntId?('&facilitatorEntId='+item.facilitatorEntId):'';
                                                var src=appPath+'/entRelease/cfBuy_facilitator.htm?ticketId='+getParam('ticketId')+'&releaseId='+releaseId+"&breadcrumbName=资源池"+facilitatorEntIdStr;
                                                window.location=src;
                                            }
                                        }
                                    }
                                });
                            };
                            this.currentReleaseId=item.releaseId;
                            this.currentReleaseIndex=index;
                            this.checkEntAccount();
                            return;
						}
                        ajax({
                            url:'/facilitatorCustomer/listFacilitatorCustomer.htm',
                            dataType:'json',
                            data:{
                                pageIndex:1,
								pageSize:5
							},
                            success:function (result) {
                                console.log(result);
                                if(result.status==1){
                                    if(!result.data.customerList||result.data.customerList.length==0){
                                        vue.noCompany=true;
									}else{
                                        var releaseId=item.releaseId;
                                        var facilitatorEntIdStr=item.facilitatorEntId?('&facilitatorEntId='+item.facilitatorEntId):'';
                                        var src=appPath+'/entRelease/cfBuy_facilitator.htm?ticketId='+getParam('ticketId')+'&releaseId='+releaseId+"&breadcrumbName=资源池"+facilitatorEntIdStr;
                                        window.location=src;
									}
								}
                            }
                        });
                    },
                    entDetail: function (isPay,releaseId,index) {
                        if (!isPay) {
                            this.currentReleaseId = releaseId;
                            this.currentReleaseIndex = index;
                            this.checkEntAccount();
                        }
                    },
                    changeAllProvincesShow:function () {
                        if(this.allProvincesShow){
                            $('#provice_select dd a.provinceItem:gt(7)').hide();
						}else{
                            $('#provice_select dd a.provinceItem:gt(7)').show();
						}
						this.allProvincesShow=!this.allProvincesShow;
                    },
                    changeAllWasteShow:function () {
                        if(this.allWasteShow){
                            $('#wasteCode_select dd a.wasteInfoItem:gt(7)').hide();
						}else{
                            $('#wasteCode_select dd a.wasteInfoItem:gt(7)').show();
						}
						this.allWasteShow=!this.allWasteShow;
                    },
                    selectProvince:function (key,value) {
                        this.cityClick=true;
						this.selectPCD(key,value);
                        this.search();
                    },
					selectPCD:function (key,value) {
                        this.province=key;
                        this.provinceName=value;
                        if(key){
                            this.cityList=ChineseDistricts[key];
                        }else{
                            this.cityList=null;
                        }
                        this.city='';
                        this.district='';
                        this.cityName='';
                        this.districtName='';
                        this.districts=null;
                        this.cantonCode=key;
                    },
                    selectCity:function (key,value) {
						this.city=key;
						this.cityName=value;
						if(key){
                            this.districts=ChineseDistricts[key];
						}else{
                            this.districts=null;
						}
                        this.district='';
						this.districtName='';
                        this.cantonCode=key?key:this.province;
                        this.search();
                    },
                    selectDistrict:function (key,value) {
						this.district=key;
						this.districtName=value;
						this.cantonCode=key?key:(this.city?this.city:this.province);
                        this.search();
                    },
                    selectWaste:function (item,index) {
                        this.currentCode=item.code;
                        this.currentIndex=index;
                        item.select=!item.select;
                        Vue.set(vue.wasteInfoList, index, item);
                        if(item.select){
                            for(var i in item['wasteList']){
								item['wasteList'][i]['select']=false;
                            }
                            this.eightCodeList=item['wasteList'];
                            this.wasteCode[item['code']]=index;
                            $('#total_checkbox').removeClass('select');
						}else{
                            this.eightCodeList=[];
                            delete this.wasteCode[item['code']];
						}
                        this.search();
                    },
                    selectEightCode:function (item,index) {
                        item.select=!item.select;
                        Vue.set(vue.eightCodeList, index, item);
                        if(!item.select){
                            if(this.isAllNotSelectEightCode(vue.eightCodeList)){
                                var item=vue.wasteInfoList[vue.currentIndex];
								delete vue.wasteCode[vue.currentCode];
								item.select=false;
								Vue.set(vue.wasteInfoList, vue.currentIndex, item);
								$('#total_checkbox').removeClass('select');
								vue.eightCodeList=[];
							}else{
                                Vue.set(vue.wasteCode, vue.currentCode, vue.eightCodeList);
							}
                        }else if(this.isAllSelectEightCode()){//是否选择所有八位码
                            Vue.set(vue.wasteCode, vue.currentCode, vue.currentIndex);
						}else{
                            Vue.set(vue.wasteCode, vue.currentCode, vue.eightCodeList);
						}
                        this.search();
                    },
                    clearEightCode:function (item) {
                        if(item.parentIndex*1===vue.currentIndex*1){
                            this.selectEightCode(item,item.index);
						}else{
                            item.select=false;
							var wasteList=vue.wasteCode[item.parentCode];
                            wasteList[item.index]=item;
                            delete vue.wasteCode[item.parentCode];
                            Vue.set(vue.wasteCode, item.parentCode, wasteList);
                            if(this.isAllNotSelectEightCode(wasteList)){
                                var item1=vue.wasteInfoList[item.parentIndex];
                                delete vue.wasteCode[item.parentCode];
                                item1.select=false;
                                Vue.set(vue.wasteInfoList, item.parentIndex, item1);
							}
						}
                        this.search();
                    },
					isAllSelectEightCode:function () {
                        var flag=true;
                        for(var i in vue.eightCodeList){
                            if(!vue.eightCodeList[i]['select']){
                                flag=false;
							}
						}
						return flag;
                    },
					isAllNotSelectEightCode:function (eightCodeList) {
                        var flag=true;
                        for(var i in eightCodeList){
                            if(eightCodeList[i]['select']){
                                flag=false;
							}
						}
						return flag;
                    },
                    selectQuality:function (type) {
						this.qualityType=type;
                        this.search();
                    },
					getQualityTypeText:function () {
                        var arr=['不限','3吨以下','3-5吨','5-10吨','10-30吨','30-100吨','100-300吨','300-1000吨','1000吨以上'];
						return arr[this.qualityType]
                    },
                    clearAll:function () {
						this.selectPCD('','');
                        this.qualityType=0;
                        this.eightCodeList=[];
						this.allWasteShow=false;
						this.currentCode='';
						this.currentIndex=0;
						this.wasteCode={};
                        $('#provice_select dd a.provinceItem:gt(7)').hide();
                        $('#wasteCode_select dd a.wasteInfoItem:gt(7)').hide();
                        this.allProvincesShow=false;
                        this.allWasteShow=false;
                        this.binding=0;
                        this.favoriteType=false;
                        for(var index in vue.wasteInfoList){
                            var item=vue.wasteInfoList[index];
                            item.select=false;
                            Vue.set(vue.wasteInfoList, index, item)
                        }
                        this.search();
                    },
                    goCzEdit:function () {
						window.location='<%=appPath%>/enterprise/czEdit.htm?ticketId=<%=ticketId%>';
                    },
                    hideEntDialog:function () {
						this.entDialogVisible=false;
                    },
                    viewExample:function () {
                        window.location='<%=appPath%>/enterprise/czDetailExample.htm?ticketId=<%=ticketId%>';
                    },
            		checkEntInfoCompleted:function () {
						ajax({
							url:'/sysEnterpriseBase/checkEntInfoCompleted',
							success:function (data) {
								if(data.status==1){
                                    vue.entDialogVisible=!data.data.isAllCompleted;
                                    Vue.set(vue.entParam,'hasSummary',data.data.hasSummary);
                                    Vue.set(vue.entParam,'hasLicence',data.data.hasLicence);
                                    Vue.set(vue.entParam,'hasSalesNote',data.data.hasSalesNote);
								}
                            }
						});
                    },
                    showSignForm:function () {
                        if(this.signType==''){
                            $.notify('请选择标记类型',{status:'info',timeout:2500});
                            return;
                        }
                        this.signFormShow=true;
                    },
                    delSign:function (id) {
                        ajax({
                            url:'/discussTag/deleteDiscussTag',
                            data:{
                                id:id
                            },
                            success:function (result) {
                                if(result.status&&result.data){
                                    $.notify('删除标记成功',{status:'success',timeout:2500});
                                    vue.getSignList();
                                }
                            }
                        });
                    },
                    saveSign:function () {
                        var param={
                            releaseId:this.signItem.releaseId,
                            tagType:this.signType
                        };
                        switch (this.signType){
                            case 'TAGING':
                                param.comments=this.say;
                                param.tagStatus='TAGGING';
                                break;
                            case 'SAMPLING':
                                param.sampleDate=this.sampleDate;
                                param.contacts=this.sampleName;
                                param.contactsTel=this.sampleMobile;
                                param.tagStatus='SAMPLING';
                                break;
                            case 'SAMPLE':
                                param.tagStatus=this.sampleStatus;
                                break;
                            case 'CONTRACT':
                                param.tagStatus=this.contractStatus;
                                break;
                        }
                        ajax({
                            url:'/discussTag/saveDiscussTag.htm?ticketId=<%=ticketId%>',
                            data:JSON.stringify(param),
                            contentType:'application/json',
                            success:function (result) {
                                if(result.status&&result.data){
                                    $.notify('添加标记成功',{status:'success',timeout:2500});
                                    vue.signFormShow=false;
                                    vue.getSignList();
                                }
                            }
                        });
                    },
                    getSignList:function () {
                        vue.signList=[];
                        this.signDialogloading=true;
                        ajax({
                            url:'/discussTag/listDiscussTag',
                            data:{
                                releaseId:this.signItem.releaseId
                            },
                            success:function (result) {
                                vue.signDialogloading=false;
                                console.log(result);
                                if(result.status==1){
                                    vue.signList=result.data;
                                    var busiStatus='';
                                    if(result.data.length>0){
                                        busiStatus=result.data[0]['busiStatus'];
									}
                                    if(!vue.signItem.tagInfo){
                                        vue.signItem.tagInfo={'count':0,tagStatus:''};
                                    }
                                    vue.signItem.tagInfo.tagStatus=TAGSTATUS[busiStatus];
                                    vue.signItem.tagInfo.count=result.data.length;
                                    Vue.set(vue.list,vue.signItem.index,vue.signItem);
                                }
                            }
                        });
                    },
                    getSign:function (item,index) {
                        item.index=index;
                        this.signItem=item;
                        this.signDialogVisible=true;
                        this.signFormShow=false;
                        this.signType='';
                        $('body').css('overflow-y','hidden');
                        if(this.signTypeList.length==0){
                            this.getSignTypeList();
                        }
                        this.getSignList();
                        var heights=document.body.clientHeight;
                        setTimeout(function () {
                            $('.signTimeLine').css('max-height',heights-318+'px');
                        },300)
                    },
                    closeSignDialog:function () {
                        this.signDialogVisible=false;
                        setTimeout(function () {
                            $('body').css('overflow-y','auto');
                        },500);
                    },
                    getSignTypeList:function () {
                        var _this=this;
                        ajax({
                            url:'/codeType/listCodeValue',
                            data:{
                                typeCode:'TAG_TYPE'
                            },
                            success:function (result) {
                                console.log(result);
                                if(result.status==1){
                                    _this.signTypeList=result.data;
                                }
                            }
                        })
                    }
                }
            })
		</script>
</section>

<%------- 导入底部信息 -------%>
<%--<jsp:include page="/common/webCommon/bottom.jsp" flush="true" />--%>

<%------- 结束导入底部信息 -------%>
<script type="text/javascript">
    $(document).ready(function() {
        var rs = window.globalInit();
        rs.done(function () {
        });
    });
</script>
</body>