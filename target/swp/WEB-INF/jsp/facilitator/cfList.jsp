<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%
	String appPath=request.getContextPath();
	String ticketId = (String)request.getAttribute("ticketId");
%>
<%------- 导入页头信息 -------%>
<jsp:include page="/common/webCommon/user_top.jsp" flush="true">
	<jsp:param name="title" value="我的易废圈" />
</jsp:include>
<jsp:include page="/common/webCommon/facilitatorMenu.jsp" flush="true">
	<jsp:param name="menuId" value="#myIndex" />
</jsp:include>
<link rel="stylesheet" href="<%=appPath%>/main/common/elementui/index.css">
<link rel="stylesheet" href="<%=appPath%>/css/wastecircle/style.css?1">
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
					<dl class="filter_item" id="wasteCode_select" style="display: none">
						<dt>排除：</dt>
						<dd>
							<a href="javascript:" v-for="(item,index) in wasteInfoList" @click="selectWaste(item,index)" class="wasteInfoItem" :class="{'select':item.select}">
								{{item.code}}
							</a>
							<a href="javascript:" class="select" @click="changeAllWasteShow">{{allWasteShow?'&lt;收起':'展开全部&gt;'}}</a>
						</dd>
					</dl>
					<dl class="filter_item" id="eightCode_select" v-show="eightCodeList.length>0">
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
                    <dl class="filter_item">
                        <dt>收藏：</dt>
                        <dd>
                            <a href="javascript:" :class="{'select':favoriteType==false}" @click="selectFavorite(false)">不限</a>
                            <a href="javascript:" :class="{'select':favoriteType==true}" @click="selectFavorite(true)">我收藏的资源</a>
                        </dd>
                    </dl>
                    <dl class="filter_item">
                        <dt>报价：</dt>
                        <dd>
                            <a href="javascript:" :class="{'select':inquiryStatus==2}" @click="selectInquiryStatus(2)">不限</a>
                            <a href="javascript:" :class="{'select':inquiryStatus==1}" @click="selectInquiryStatus(1)">已报价</a>
                            <a href="javascript:" :class="{'select':inquiryStatus==0}" @click="selectInquiryStatus(0)">未报价</a>
                        </dd>
                    </dl>
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
								<em>不含：(</em>
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
								<p class="item_info"><span class="item_name" @click="entDetail(item.releaseEntId,item.facilitatorEntId)">{{item.entName}}</span>
									<el-tag type="success" v-if="item.facilitatorEntId==selfEntId">自营业务</el-tag>
									<el-tag v-if="item.inquiryStatus==1">已报价</el-tag>
									<span class="item_publish"><i class="time_icon"></i>
									发布于{{item.releaseDate.substring(0,16)}}
									</span>
								</p>
								<p class="item_address" v-if="item.entAddress"><i class="publisharea"></i>{{item.entAddress}}<span>（距离{{item.distance}}公里）</span>
								</p>
								<p style="margin: 4px 0" v-else></p>
								<p class="total_amount"> 总计：<span> {{item.totalWasteCount}} </span> 种危废，累计<span>{{item.totalWasteAmountDesc}}</span>。你可处置的危废有:<span> {{item.disposalWasteCount||0}} </span> 种危废{{item.disposalWasteAmount?',累计':''}}<span>{{item.disposalWasteAmount||''}}</span>。</p>
								<div class="itemReleaseList">
									<div class="itemRelease" v-for="(wasteItem,index) in item.releaseWasteDetails">
										<span >{{wasteItem.wasteName}}</span><span>{{wasteItem.wasteCode}}</span><span>{{wasteItem.wasteAmount}}{{wasteItem.unitValue}}</span>
									</div>
								</div>
								<el-row :gutter="10" style="padding-right: 20px;margin-top: 9px;word-wrap: break-word;" v-show="!item.releaseRemark==''">
									<span><b>备注：</b>{{item.releaseRemark}}。</span>
								</el-row>
								<el-row :gutter="10" style="padding-right: 20px;margin-top: 9px;">
									<el-col :span="24" align="right">
										<el-button type="text" class="qipao_logo" href="javascript:" v-if="item.facilitatorEntId!=selfEntId" @click="contact(item.releaseEntId,item.entName)" style="margin-right: 18px;margin-left: 18px">
											<img src="../main/pc/img/qipao.jpg">联系TA</el-button>
										<el-button type="text" href="javascript:" class="goDetail" @click="buy(item)">
											<i class="responsepublish"></i>报价</el-button>
										<el-button type="text" :class="{'favorited':item.favorited,favorite_logo:true}" href="javascript:" @click="favorite(item,index)">
											<i class="favorite"></i>{{item.favorited?'已':''}}收藏
										</el-button>
									</el-col>
								</el-row>
							</div>
						</div>
					</div>
					<div class="loadComplete" v-show="list.length==0&&!listLoading">暂无相关数据</div>
				</div>
				<div class="loadComplete" v-show="loadComplete&&list.length>0">数据加载完成</div>
				<div class="loading" v-show="listLoading&&!loadComplete"><img src="<%=appPath %>/main/pc/img/load.gif"></div>
				<el-dialog
						class="signDialog animated bounceInRight"
						:visible.sync="signDialogVisible"
						:modal-append-to-body="false"
						:modal="false"
						:before-close="closeSignDialog"
						width="320px">
					<span slot="title" class="title">标记</span>
					<div class="entInfo">
						<p><em>单位：</em><b>{{signItem.entName}}</b></p>
						<p><em>总数：</em><b><font color="#1171d1">{{signItem.disposalWasteAmount}}</font></b></p>
					</div>
					<div class="editArea">
						<el-button-group>
							<el-select v-model="signType" placeholder="请选择标记类型" style="float: left">
								<el-option
										v-for="item in signTypeList"
										:key="item.code"
										:label="item.value"
										:value="item.code">
								</el-option>
							</el-select>
							<el-button type="primary" @click="showSignForm">新增标记</el-button>
						</el-button-group>
						<div class="sign_form" v-if="signFormShow">
							<div class="sign_form_title">添加{{SIGNTYPE[signType]}}标记</div>
							<div class="sign_form_content">
								<el-input v-if="signType=='TAGING'"
										  type="textarea"
										  :rows="2"
										  placeholder="说点什么吧"
										  v-model="say">
								</el-input>
								<div v-if="signType=='SAMPLING'" align="center">
									<el-date-picker
											v-model="sampleDate"
											type="date"
											align="right"
											placeholder="请选择取样时间">
									</el-date-picker>
									<el-input v-model="sampleName" placeholder="请输入联系人姓名"></el-input>
									<el-input v-model="sampleMobile" placeholder="请输入联系人电话"></el-input>
								</div>
								<el-radio-group v-model="sampleStatus" v-if="signType=='SAMPLE'">
									<el-radio label="DELIVERED_LAB">已送实验室</el-radio>
									<el-radio label="QUALIFIED">合格</el-radio>
									<el-radio label="UNQUALIFIED">不合格</el-radio>
								</el-radio-group>
								<el-radio-group v-model="contractStatus" v-if="signType=='CONTRACT'">
									<el-radio label="DRAFTCONTRACT">合同已拟定</el-radio>
									<el-radio label="DELIVERYCONTRACT">合同已寄出</el-radio><br/>
									<el-radio label="SIGNCONTRACT" style="margin-top: 10px">合同已签定</el-radio>
								</el-radio-group>
							</div>
							<div class="sign_form_btn">
								<el-button type="primary" size="small" style="height: 30px" @click="saveSign">保存</el-button>
							</div>
						</div>
					</div>
					<div class="signTimeLine" v-loading="signDialogloading"
						 element-loading-spinner="el-icon-loading"
						 element-loading-background="rgba(246, 247, 251, 0.8)">
						<el-steps direction="vertical" :active="1" v-if="signList.length>0">
							<el-step v-for="(item,index) in signList">
								<div slot="title" class="signTitle">
									<span class="signTime">{{item.createTime.substring(0,16)}}</span>
									<span class="signCreate">{{item.createBy}}</span>
									<el-button type="text" class="el-icon-delete" title="删除该标记" @click="delSign(item.id)"></el-button>
								</div>
								<div slot="description" class="signContent" v-if="item.tagType=='TAGING'">
									{{item.comments}}
								</div>
								<div slot="description" class="signContent" v-if="item.tagType=='SAMPLING'">
									取样时间：{{item.sampleDate.substring(0,10)}}<br/>
									联系人：{{item.contacts}}<br/>
									联系电话：{{item.contactsTel}}
								</div>
								<div slot="description" class="signContent" v-if="item.tagType=='SAMPLE'">
									{{item.comments}}
								</div>
								<div slot="description" class="signContent" v-if="item.tagType=='CONTRACT'">
									{{TAGSTATUS[item.busiStatus]}}
								</div>
							</el-step>
						</el-steps>
						<span v-else style="display: inline-block;text-align: center;width: 100%;">暂无标记信息</span>
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
					list:[],
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
					inquiryStatus:2,
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
					selfEntId:localStorage.entId
                },
                created:function() {
                    this.getList();
                },
                mounted:function () {
                    $('#provice_select dd a.provinceItem:gt(7)').hide();
					$('#wasteCode_select dd a.wasteInfoItem:gt(7)').hide();
					$('#wasteCode_select').show();
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
								_this.getList();
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
                },
                methods:{
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
                        param.inquiryStatus=this.inquiryStatus==2?'':this.inquiryStatus;
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
                            url:'/entRelease/listWasteEntRelease4FacilitatorEnt.htm?ticketId='+getParam('ticketId')+'&pageIndex='+(this.pageIndex++)+'&pageSize='+this.pageSize,
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
                                        _this.list.push(result.data[i]);
                                    }
                                }else{

								}
                            }
                        })
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
                    selectInquiryStatus:function (inquiryStatus) {
                        this.inquiryStatus=inquiryStatus;
                        this.search();
                    },
                    contact:function (entId,entName) {
                        getEnterpriseContacts(entId,entName,'服务商资源池');
                    },
					buy:function (item) {
                        var releaseId=item.releaseId;
                        var facilitatorEntIdStr=item.facilitatorEntId?('&facilitatorEntId='+item.facilitatorEntId):'';
                        var src=appPath+'/facilitator/cfBuy.htm?ticketId='+getParam('ticketId')+'&releaseId='+releaseId+facilitatorEntIdStr;
                        window.location=src;
                    },
                    entDetail:function (entId,facilitatorEntId) {
                        var str=facilitatorEntId?('&facilitatorEntId='+facilitatorEntId):'';
                        window.location = appPath + "/myenterprise/viewEnterpriseDetail.htm?ticketId="
                            + getParam('ticketId') + "&enterpriseId=" + entId + "&index=myIndex"+"&breadcrumbName=资源池"+str;
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
						this.selectQuality(0);
                        this.eightCodeList=[];
						this.allWasteShow=false;
						this.currentCode='';
						this.currentIndex=0;
						this.wasteCode={};
                        $('#provice_select dd a.provinceItem:gt(7)').hide();
                        $('#wasteCode_select dd a.wasteInfoItem:gt(7)').hide();
                        this.allProvincesShow=false;
                        this.allWasteShow=false;
                        for(var index in vue.wasteInfoList){
                            var item=vue.wasteInfoList[index];
                            item.select=false;
                            Vue.set(vue.wasteInfoList, index, item)
                        }
                        this.search();
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
</body>