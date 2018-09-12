<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%
	String appPath=request.getContextPath();
	String ticketId = (String)request.getAttribute("ticketId");
%>
<%------- 导入页头信息 -------%>
<jsp:include page="/common/top.jsp" flush="true">
	<jsp:param name="title" value="创建运营计划"/>
</jsp:include>
<jsp:include page="/common/left.jsp" flush="true">
	<jsp:param name="menuId" value="#operationList"/>
</jsp:include>
<link rel="stylesheet" href="<%=appPath%>/main/common/elementui/index.css">
<link rel="stylesheet" href="<%=appPath%>/css/wastecircle/style.css">
<link rel="stylesheet" href="<%=appPath%>/css/dispositionPlanRelease/operation.css">
<script type="text/javascript" src="<%=appPath%>/thirdparty/city-picker/js/city-picker.data.js"></script>
<section id="app"  v-loading="saving"  element-loading-text="拼命加载中"
		 element-loading-spinner="el-icon-loading"
		 element-loading-background="rgba(0, 0, 0, 0.6)">
	<template>
		<el-breadcrumb separator="/">
			<el-breadcrumb-item>运营管理</el-breadcrumb-item>
			<el-breadcrumb-item onClick="history.go(-1);">运营计划</el-breadcrumb-item>
			<el-breadcrumb-item>{{this.operationId?'编辑':'创建'}}运营计划</el-breadcrumb-item>
		</el-breadcrumb>
		<el-dialog
				title="企业危废"
				:visible.sync="dialogVisible"
				width="700px">
			<el-table  tooltip-effect="dark"
					   :data="entWasteList"  v-loading.body="entWasteListLoading" border
					   max-height="400"
					   style="width: 100%">
				<el-table-column
						label="危废代码"
						prop="wasteCode">
				</el-table-column>
				<el-table-column
						label="危废名称"
						prop="wasteName">
				</el-table-column>
			</el-table>
		</el-dialog>
		<div>
			<el-steps :active="currentStep" simple process-status="wait">
				<el-step title="一：选择处置企业"  onClick="vue.changeCurrentStep(1)" :class="{'currentStep':currentStep==1}"></el-step>
				<el-step title="二：选择产废企业"  onClick="vue.changeCurrentStep(2)" :class="{'currentStep':currentStep==2}"></el-step>
				<el-step title="三：设定通知" onClick="vue.changeCurrentStep(3)" :class="{'currentStep':currentStep==3}"></el-step>
			</el-steps>
			<div class="panel panel-body" style="border-top-left-radius: 0;border-top-right-radius: 0;padding-top: 25px">
				<div v-show="currentStep==1">
					<el-transfer
							filterable
							:filter-method="filterMethod"
							filter-placeholder="请输入企业名称"
							:button-texts="['取消选择', '确认选择']"
							:titles="['待选择企业', '已选择企业']"
							v-model="selectCZData"
							:data="czListData">
					</el-transfer>
					<el-button type="primary" :disabled="selectCZData.length==0" style="margin-top: 20px" @click="saveDisposalEnterprise">保存</el-button>
				</div>
				<div v-show="currentStep==2">
					<div class="search_bd">
						<!-- 租金 -->
						<dl class="filter_item" id="provice_select">
							<dt>区域：</dt>
							<dd>
								<a href="javascript:;" @click="selectProvince('','')" :class="province==''?'select':''">不限</a>
								<a href="javascript:;" v-for="(value,key) in provinces" :data-key="key" :class="province==key?'provinceItem select':'provinceItem'" @click="selectProvince(key,value)">
									{{value}}
								</a>
								<a href="javascript:;" class="select" @click="changeAllProvincesShow">{{allProvincesShow?'&lt;收起':'展开全部&gt;'}}</a>
							</dd>
						</dl>
						<dl class="filter_item" id="city_select" v-show="cityList">
							<dt>&nbsp;</dt>
							<dd>
								<a href="javascript:;" :class="city==''?'select':''" @click="selectCity('','')">不限</a>
								<a href="javascript:;" v-for="(value,key) in cityList" :data-key="key" @click="selectCity(key,value)" :class="city==key?'select':''">
									{{value}}
								</a>
							</dd>
						</dl>
						<dl class="filter_item" id="district_select" v-show="districts">
							<dt>&nbsp;</dt>
							<dd>
								<a href="javascript:;" :class="district==''?'select':''" @click="selectDistrict('','')">不限</a>
								<a href="javascript:;" v-for="(value,key) in districts" :data-key="key" @click="selectDistrict(key,value)" :class="district==key?'select':''">
									{{value}}
								</a>
							</dd>
						</dl>
						<dl class="filter_item">
							<dt>管理计划：</dt>
							<dd>
								<a href="javascript:;" :class="{'select':declaration==1}" @click="queryDeclaration(1)">
									<span class="icon-checkbox"></span>
									<span class="text">今年未做管理计划</span>
								</a>
								<a href="javascript:;" :class="{'select':declaration==0}" @click="queryDeclaration(0)">
									<span class="icon-checkbox"></span>
									<span class="text">今年已做管理计划</span>
								</a>
							</dd>
						</dl>
						<%--<dl class="filter_item" v-if="declaration==1">
							<dt>筛选条件：</dt>
							<dd>
								<a href="javascript:;" :class="{'select':caseType==0}" @click="queryCaseType(0)">
									<span class="icon-checkbox"></span>
									<span class="text">按申报类别</span>
								</a>
								<a href="javascript:;" :class="{'select':caseType==1}" @click="queryCaseType(1)">
									<span class="icon-checkbox"></span>
									<span class="text">按申报总量</span>
								</a>
								<a href="javascript:;" :class="{'select':caseType==2}" @click="queryCaseType(2)">
									<span class="icon-checkbox"></span>
									<span class="text">按往年转移企业</span>
								</a>
							</dd>
						</dl>--%>
						<dl class="filter_item" id="wasteCode_select">
							<dt>{{declaration==1?'往年':''}}申报类别：</dt>
							<dd>
								<a href="javascript:;" v-for="(item,index) in wasteInfoList" @click="selectWaste(item,index)" class="wasteInfoItem" :class="{'select':item.select}">
									{{item.code}}
								</a>
								<a href="javascript:;" class="select" @click="changeAllWasteShow">{{allWasteShow?'&lt;收起':'展开全部&gt;'}}</a>
							</dd>
						</dl>
						<dl class="filter_item" id="eightCode_select" v-show="eightCodeList.length>0">
							<dt>&nbsp;</dt>
							<dd>
								<a href="javascript:;"  id="total_checkbox">
									<span class="icon-checkbox"></span>
									<span class="text">全选</span>
								</a>
								<a href="javascript:;" v-for="(item,index) in eightCodeList" @click="selectEightCode(item,index)"
								   :class="{'select':item.select}">
									<span class="icon-checkbox"></span>
									<span class="text">{{item.code}}</span>
								</a>
							</dd>
						</dl>
						<dl class="filter_item">
							<dt>{{declaration==1?'往年':'今年'}}申报量：</dt>
							<dd class="weightContainer">
								<el-col :span="11">
									<el-input v-model="minWeight" placeholder="最小量"></el-input>
								</el-col>
								<el-col class="line" :span="2">-</el-col>
								<el-col :span="11">
									<el-input v-model="maxWeight" placeholder="最大量"></el-input>
								</el-col>
							</dd>
						</dl>
						<dl class="filter_item" v-if="declaration==1&&caseType==1">
							<dt>往年总申报量：</dt>
							<dd class="weightContainer">
								<el-col :span="11">
									<el-input v-model="totalMinWeight" placeholder="最小量"></el-input>
								</el-col>
								<el-col class="line" :span="2">-</el-col>
								<el-col :span="11">
									<el-input v-model="totalMaxWeight" placeholder="最大量"></el-input>
								</el-col>
							</dd>
						</dl>
						<dl class="filter_item">
							<dt style="position: relative;top: 6px;">{{declaration==1?'往年':'计划'}}转移企业：</dt>
							<dd class="weightContainer">
								<el-select  style="width: 400px"
											v-model="referenceEntList"
											multiple
											filterable
											remote
											reserve-keyword
											placeholder="请输入企业名称"
											:remote-method="remoteMethod"
											:loading="outLoading">
									<el-option
											v-for="item in options"
											:key="item.entName"
											:label="item.entName"
											:value="item.entName">
									</el-option>
								</el-select>
							</dd>
						</dl>
						<dl class="filter_item">
							<dt>资质匹配：</dt>
							<dd>
								<a href="javascript:;" :class="{'select':qualification==1}" @click="queryQualification(1)">
									<span class="icon-checkbox"></span>
									<span class="text">是</span>
								</a>
								<%--<a href="javascript:;" :class="{'select':qualification==2}" @click="queryQualification(2)">
									<span class="icon-checkbox"></span>
									<span class="text">否</span>
								</a>--%>
							</dd>
						</dl>

					</div>
					<el-row>
						<el-col :span="12"><el-button type="primary" style="margin-top: 20px" @click="queryCF">筛选</el-button></el-col>
						<el-col :span="12" align="right"><el-button type="primary" v-loading="" :disabled="tableData.length==0" style="margin-top: 20px" @click="saveWebSiteOperationInfo">保存</el-button></el-col>
					</el-row>
					<div v-if="tableData.length==0&&cfSearched&&!listLoading" style="margin-top: 20px;text-align: center;">
						暂无相关数据
					</div>
					<div class="selectTable" v-if="tableData.length>0||listLoading" style="margin-top: 20px">
						<el-table  tooltip-effect="dark" :highlight-current-row="true" @row-click="rowClick"
								   :data="tableData"  v-loading.body="listLoading" border
								   style="width: 100%">
							<el-table-column
									label="企业名称"
									prop="entName">
							</el-table-column>
							<el-table-column
									label="联系人"
									prop="contacts">
								<template scope="scope">
									<span v-if="scope.row.contacts" >{{scope.row.contacts}}</span>
									<span v-if="!scope.row.contacts" >--</span>
								</template>
							</el-table-column>
							<el-table-column
									label="联系电话"
									prop="contactsTel">
							</el-table-column>
						</el-table>
					</div>
					<div v-show="tableData&&tableData.length>0" align="right" style="margin-top: 10px">
						<el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page.sync="pageIndex" :page-sizes="[10,20,30,50]" :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper" :total="total">
						</el-pagination>
					</div>

				</div>
				<div v-show="currentStep==3">
					<div>
						<div style="position:fixed;right: 20px;top: 40%;" v-if="cron">cron表达式：{{cron}}</div>
						<el-form label-position="right" label-width="160px" :model="operation" ref="operationValidateForm" :rules="operationRules">
							<el-form-item label="运营计划名称：" prop="operationName" style="width: 400px">
								<el-input v-model="operation.operationName" placeholder="输入运营计划名称"></el-input>
							</el-form-item>
							<el-form-item label="开始时间：" prop="startDate">
								<el-date-picker
										v-model="operation.startDate"
										type="datetime"
										placeholder="选择日期">
								</el-date-picker>
							</el-form-item>
							<el-form-item label="结束时间：" prop="endDate">
								<el-date-picker
										v-model="operation.endDate"
										type="datetime"
										placeholder="选择日期">
								</el-date-picker>
							</el-form-item>
							<el-form-item label="重复：" prop="repeatRule">
								<el-select v-model="operation.repeatRule" placeholder="请选择规则" @change="changeRepeatRule">
									<el-option
											v-for="item in repeatRuleList"
											:key="item.value"
											:label="item.label"
											:value="item.value">
									</el-option>
								</el-select>
							</el-form-item>
							<el-form-item label="发送时间(周几)：" prop="weekDay" id="weekDay" v-if="operation.repeatRule==2">
								<el-radio-group v-model="operation.weekDay" @change="changeWeekDay">
									<el-radio :label="item.value" :key="item.value" border v-for="item in weekDayList">{{item.label}}</el-radio>
								</el-radio-group>
							</el-form-item>
							<el-form-item label="发送时间(天)：" prop="monthDay" id="monthDay" v-if="operation.repeatRule==3">
								<el-radio-group v-model="operation.monthDay"  @change="changeMonthDay">
									<el-radio :label="index" v-for="index in 31" border>{{index<10?('0'+index):index}}</el-radio>
								</el-radio-group>
							</el-form-item>
							<el-form-item label="发送时间(时分)：" prop="hm">
								<el-time-picker
										arrow-control
										v-model="operation.hm"
										format="HH:mm"
										@change="changeHm"
										placeholder="选择时分">
								</el-time-picker>
							</el-form-item>
							<el-form-item align="left">
								<el-button type="primary" class="save" @click="saveOperation">保存</el-button>
							</el-form-item>
						</el-form>
					</div>
				</div>
			</div>

		</div>
	</template>
</section>
<!-- 先引入 Vue -->
<script src="<%=appPath%>/main/common/elementui/vue.js"></script>
<!-- 引入组件库 -->
<script src="<%=appPath%>/main/common/elementui/index.js"></script>
<script src="<%=appPath %>/app/js/util.js"></script>
<script src="<%=appPath %>/main/pc/js/wasteInfoList.js"></script>
<script>
    var vue=new Vue({
        el: '#app',
        data:{
            czList:[],
			czListData:[],
            selectCZ: [],
            selectCZData:[],
            operationId:getParam('operationId')?getParam('operationId'):'',
            currentStep:1,
            provinces:ChineseDistricts['provinces'],
            allProvincesShow:false,
            province:'',
            cityList:null,
            city:'',
            districts:null,
            district:'',
            declaration:'1',
            minWeight:'',
            maxWeight:'',
            totalMinWeight:'',
            totalMaxWeight:'',
            qualification:'',
            options: [],
            referenceEntList: [],
            outLoading:false,
			pageIndex:1,
			pageSize:10,
			total:0,
			caseType:0,
            tableData:[],
            listLoading:false,
			cfSearched:false,
            operation:{
                startDate:'',
                endDate:'',
                repeatRule:'',
                hm:'',
                operationName:'',
                weekDay:'2',
				monthDay:1
			},
            repeatRuleList:[{label:'每天一次',value:'1'},{label:'每周一次',value:'2'},{label:'每月一次',value:'3'}],
            weekDayList:[{value:'2',label:'周一'},{value:'3',label:'周二'},{value:'4',label:'周三'},{value:'5',label:'周四'},{value:'6',label:'周五'},{value:'7',label:'周六'},{value:'1',label:'周日'}],
            operationRules: {
                startDate: [
                    { type: 'date', required: true, message: '请选择开始时间', trigger: 'blur' },
                ],
                endDate:[
                    { type: 'date', required: true, message: '请选择结束时间', trigger: 'blur'  }
                ],
                repeatRule:[
                    { required: true, message: '请选择重复规则', trigger: 'change' },
				],
                hm:[
                    { type: 'date', required: true, message: '请选择发送时间', trigger: 'blur' }
				],
                operationName:[
					{ required: true, message: '请输入运营计划名称', trigger: 'blur'}
				]
            },
			hour:0,
			min:0,
			cron:'',
			czListMap:{},
            wasteInfoList:wasteInfoList,
            eightCodeList:[],
            allWasteShow:false,
            currentCode:'',
            wasteCode:{},
            dialogVisible:false,
            entWasteList:[],
            entWasteListLoading:true,
            saving:false
    	},
        created:function() {
            this.getCZList();
        },
        mounted:function () {
            $('#provice_select dd a.provinceItem:gt(7)').hide();
            $('#wasteCode_select dd a.wasteInfoItem:gt(7)').hide();
            $('#wasteCode_select').show();
            if(!this.operationId){
                $('#provice_select dd a.provinceItem:gt(7)').hide();
			}
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
            editAction:function () {
                if(this.operationId){
                    this.getOperationInfo();
                    this.getCFlist();
                }
            },
            rowClick:function (row, event, column) {
                this.dialogVisible=true;
                this.entWasteListLoading=true;
                this.entWasteList=[];
                ajax({
                    url:'/websiteOperation/listEntWasteByEntId',
                    data:{
                        entId:row.entId
                    },
                    success:function (result) {
                        console.log(result);
                        vue.entWasteListLoading=false;
                        if(result.status==1&&result.data.length>0){
                            vue.entWasteList=result.data;
                        }
                    }
                })
            },
            getOperationInfo:function () {
				ajax({
					url:'/websiteOperation/getWebSiteOperationInfo.htm',
					data:{
					    id:this.operationId
					},
					success:function (result) {
					    console.log(result);
						if(result.status==1){
						    var operationInfo=result.data;
						    var idList=[];
						    for(var k in operationInfo.disposalEnterpriseList){
						        var index=vue.czListMap[operationInfo.disposalEnterpriseList[k]['entName']];
						        idList.push(index);
							}
                            vue.selectCZData=idList;//处置企业
							if(operationInfo.areaCode){
                                vue.allProvincesShow=true;
                                vue.cantonCode=operationInfo.areaCode;
                                operationInfo.areaCode=vue.parseAreaCode(operationInfo.areaCode)
                                if(operationInfo.areaCode.substring(2,6)=='0000'){//省
                                    vue.province=operationInfo.areaCode;
                                }else if(operationInfo.areaCode.substring(4,6)=='00'){//市
                                    vue.province=operationInfo.areaCode.substring(0,2)+'0000';
                                    vue.cityList=ChineseDistricts[vue.province];
                                    vue.city=operationInfo.areaCode;
                                }else{
                                    vue.province=operationInfo.areaCode.substring(0,2)+'0000';
                                    vue.cityList=ChineseDistricts[vue.province];
                                    vue.city=operationInfo.areaCode.substring(0,4)+'00';
                                    vue.districts=ChineseDistricts[vue.city];
                                    vue.district=operationInfo.areaCode;
                                }
                            }
							vue.declaration=operationInfo.hasPlan?operationInfo.hasPlan:1;
                            if(operationInfo.wasteType){
                                var wasteType=operationInfo.wasteType.split(',');
                                for(var k in vue.wasteInfoList){
                                    var item=vue.wasteInfoList[k];
                                    if(wasteType.indexOf(item.code.substr(2))>-1){
                                        vue.selectWaste(item,k);
                                    }
                                }
                            }
//							vue.caseType=operationInfo.applyType?operationInfo.applyType:0;
//							if(vue.caseType==0){
                                vue.minWeight=operationInfo.startAmount;
                                vue.maxWeight=operationInfo.endAmount;
//                            }
//                            if(vue.caseType==1){
//                                vue.totalMinWeight=operationInfo.startAmount;
//                                vue.totalMaxWeight=operationInfo.endAmount;
//                            }
							vue.qualification=operationInfo.qualificationMatch==0?2:operationInfo.qualificationMatch;
							var entNameList=[];
							for(var i in operationInfo.outsourcingDisposalEnterpriseList){
                                entNameList.push(operationInfo.outsourcingDisposalEnterpriseList[i]['outsourcingDisposalName'])
							}
                            vue.referenceEntList=entNameList;
                            var cronJob=operationInfo.cronJob;
                            vue.cron=cronJob;
                            var repeatRule=cronJob.endWith('* * ?')?'1':(cronJob.endWith('* ?')?'3':'2');
                            var timeArr=cronJob.split(' ');
                            vue.hour=timeArr[2];
                            vue.min=timeArr[1];
                            var weekDay='2';
                            var monthDay=1;
                            if(repeatRule=='2'){//每周一次
                                weekDay=timeArr[5];
							}
							if(repeatRule=='3'){
                                monthDay=timeArr[3]*1;
							}
                            var operation={
                                startDate:new Date(operationInfo.startTime),
                                endDate:new Date(operationInfo.endTime),
                                repeatRule:repeatRule,
                                hm:new Date('2018-12-01 '+vue.hour+':'+vue.min+':00'),
                                operationName:operationInfo.operationName,
                                weekDay:weekDay,
                                monthDay:monthDay
							}
							vue.operation=operation;

						}
                    }
				})
            },
			parseAreaCode:function (code) {
				if(code.length==2){
				    return code+'0000';
				}
				if(code.length==4){
				    return code+'00';
				}
				return code;
            },
            getCZList:function () {
				ajax({
					url:'/disposalEnterprise/listDisposalEnterprise.htm',
					data:{
                        entName:''
					},
					success:function (result) {
					    console.log(result);
						if(result.status==1&&result.data&&result.data.length>0){
                            const czListData = [];
                            for (var i = 0; i < result.data.length; i++) {
                                var obj=result.data[i];
                                czListData.push({
                                    key: i,
                                    label: obj.entName,
                                });
                                vue.czListMap[obj.entName]=i;
                            }
							vue.czListData=czListData;
							vue.czList=result.data;
                            vue.editAction();
						}
                    }
				});
            },
            remoteMethod:function (query) {
                this.outLoading = true;
				ajax({
					url:'/websiteOperation/initOutSouringWasteEnterprise.htm',
					data:{
                        entName:query
					},
					success:function (result) {
                        vue.outLoading = false;
						console.log(result);
						if(result.status==1){
						    vue.options=result.data;
						}
                    }
				})
            },
            filterMethod:function (query, item) {
                return item.label.indexOf(query)>-1;
            },
            saveDisposalEnterprise:function () {
                if(this.selectCZData.length==0){
                    $.notify('请选择处置企业',{status:'info',timeout:2000});
                    return;
				}
                vue.$confirm('此操作将保存处置企业信息, 是否确定?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(function(){
                    vue.saving=true;
                    var selectCZ=[];
                    for(var i in vue.selectCZData){
                        selectCZ.push(vue.czList[vue.selectCZData[i]]);
                        if(vue.operationId){
                            selectCZ[i]['operationId']=vue.operationId;
                        }
                    }
                    vue.selectCZ=selectCZ;
//					return;
                    ajax({
                        url:'/disposalEnterprise/saveOrUpdateDisposalEnterpriseList.htm?ticketId=<%=ticketId%>',
                        data:JSON.stringify(selectCZ),
                        contentType:'application/json',
                        success:function (result) {
                            vue.saving=false;
                            if(result.status==1){
                                $.notify('处置企业保存成功',{status:'success',timeout:2000});
                                vue.operationId=result.data;
                                vue.currentStep=2;
                            }
                        }
                    })
                }).catch(function(){vue.saving=false;});
            },
            changeCurrentStep:function (step) {
				this.currentStep=step;
            },
            selectProvince:function (key,value) {
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
                console.log(this.cantonCode);
//                this.search();
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
                console.log(this.cantonCode);
//                this.search();
            },
            selectDistrict:function (key,value) {
                this.district=key;
                this.districtName=value;
                this.cantonCode=key?key:(this.city?this.city:this.province);
                console.log(this.cantonCode);
//                this.search();
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
            queryDeclaration:function(declaration) {
				this.declaration=declaration;
				this.clearCase();
            },
            clearCase:function () {
                this.wasteCode={};
                this.minWeight='';
                this.maxWeight='';
                this.allWasteShow=false;
                var timer=setInterval(function () {
                    var list=$('#wasteCode_select dd a.wasteInfoItem:gt(7)');
                    if(list.length>0){
                        $('#wasteCode_select dd a.wasteInfoItem:gt(7)').hide();
                        clearInterval(timer);
                    }
                },10)
                for(var i in this.wasteInfoList){
                    this.wasteInfoList[i]['select']=false;
                }
                this.eightCodeList=[];
                this.referenceEntList=[];
            },
            queryQualification:function (qualification) {
                if(this.qualification==qualification){
                    this.qualification='';
				}else{
                    this.qualification=qualification
				}
            },
            selectAllWaste:function () {
                this.currentCode='';
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
			getParamInfo:function () {
                var param={
                    operationId:this.operationId
                };
                if(this.cantonCode){
                    param.cantonCodes=[this.cantonCode];
                }
                param.hasPlan=this.declaration;
				var wasteCodeList=this.getWasteCodeList();
				if( wasteCodeList.length>0){
					param.wasteTypes=wasteCodeList;
					if(this.minWeight){
						param.detailStartAmount=this.minWeight;
					}
					if(this.maxWeight){
						param.detailEndAmount=this.maxWeight;
					}
				}else{
					if(this.minWeight){
						param.planStartAmount=this.minWeight;
					}
					if(this.maxWeight){
						param.planEndAmount=this.maxWeight;
					}
				}
                if(vue.referenceEntList.length>0){
                    param.outSourceDisposalEnterName=vue.referenceEntList;
                }
                if(this.qualification&&!this.operationId){
                    $.notify('选择资质是否匹配前,请先选择处置企业',{status:'info',timeout:4000});
                    this.listLoading=false;
                    return false;
                }
                if(this.qualification){
                    param.qualificationMatch=this.qualification==2?0:this.qualification;
                }
                return param;
            },
			getCFlist:function () {
                this.listLoading=true;
                ajax({
                    url:'/websiteOperationContacts/listOperationContacts.htm',
                    data:{
                        operationId:this.operationId,
						current:this.pageIndex,
						size:this.pageSize
					},
                    success:function (result) {
                        vue.listLoading=false;
                        console.log(result);
                        if(result.status==1&&result.data.records){
                            vue.tableData=result.data.records;
                            vue.total=result.data.total;
                        }else{
                            vue.tableData=[];
                            vue.total=0;
                        }
                    }
                });
            },
            queryCF:function () {
                this.listLoading=true;
                var param=this.getParamInfo();
                if(!param){return;}
                console.log(JSON.stringify(param));
				ajax({
					url:'/websiteOperation/listWasteEnterprise.htm?ticketId=<%=ticketId%>&type='+this.declaration+'&pageIndex='+this.pageIndex+'&pageSize='+this.pageSize,
					data:JSON.stringify(param),
					contentType:'application/json',
					success:function (result) {
					    vue.listLoading=false;
                        vue.cfSearched=true;
					    console.log(result);
						if(result.status==1&&result.data.enterList){
							vue.tableData=result.data.enterList;
							vue.total=result.data.pagingParameter.totalRecord;
						}else{
                            vue.tableData=[];
                            vue.total=0;
						}
                    }
				});
            },
            saveWebSiteOperationInfo:function () {
                vue.$confirm('此操作将保存产废企业信息, 是否确定?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(function(){
                    var param=vue.getParamInfo();
                    if(!param){return;}
                    vue.saving=true;
                    ajax({
                        url:'/websiteOperation/saveOrUpdateWebSiteOperationInfo.htm?ticketId=<%=ticketId%>',
                        data:JSON.stringify(param),
                        contentType:'application/json',
                        success:function (result) {
                            vue.saving=false;
                            if(result.status==1){
                                $.notify('产废企业保存成功',{status:'success',timeout:2000});
                                vue.operationId=result.data;
                                vue.currentStep=3;
                            }
                        }
                    })
                }).catch(function(){vue.saving=false;});
            },
            handleSizeChange:function(val) {
                this.pageIndex=1;
                this.pageSize = val;
                if(getParam('operationId')&&!this.cfSearched){
                    this.getCFlist();
				}else{
                    this.queryCF();
				}
            },
            handleCurrentChange:function(val) {
                this.pageIndex = val
                if(getParam('operationId')&&!this.cfSearched){
                    this.getCFlist();
                }else{
                    this.queryCF();
                }
            },
            saveOperation:function () {
                vue.saving=true;
                this.$refs['operationValidateForm'].validate(function(valid){
                    if (valid) {
						var param={
                            operationId:vue.operationId,
                            operationName:vue.operation.operationName,
							startTime:vue.operation.startDate,
							endTime:vue.operation.endDate,
							cronJob:vue.cron
						}
						console.log(JSON.stringify(param));
						ajax({
							url:'/websiteOperation/updateWebSiteOperationInfo?ticketId=<%=ticketId%>',
							data:JSON.stringify(param),
                            contentType:'application/json',
							success:function (result) {
                                vue.saving=false;
                                if(result.status==1&&result.data){
                                    $.notify('运营计划创建成功',{status:'success',timeout:2000});
                                    history.go(-1);
                                }
                            }
						})
                    }
                });
            },
            changeRepeatRule:function () {
				switch (this.operation.repeatRule*1){
					case 1:
                        this.cron='0 '+this.min+' '+this.hour+' * * ?';
					    break;
					case 2:
                        this.cron='0 '+this.min+' '+this.hour+' ? * '+this.operation.weekDay;
					    break;
					case 3:
                        this.cron='0 '+this.min+' '+this.hour+' '+this.operation.monthDay+' * ?';
					    break;
				}
            },
            changeWeekDay:function () {
				this.cron='0 '+this.min+' '+this.hour+' ? * '+this.operation.weekDay;
            },
            changeMonthDay:function () {
                this.cron='0 '+this.min+' '+this.hour+' '+this.operation.monthDay+' * ?';
            },
            changeHm:function () {
                if(this.operation.hm){
                    this.hour=this.operation.hm.getHours();
                    this.min=this.operation.hm.getMinutes();
				}
				this.changeRepeatRule();
            }
        }

    })
</script>
<%------- 导入底部信息 -------%>
<jsp:include page="/common/webCommon/bottom.jsp" flush="true" />

<%------- 结束导入底部信息 -------%>
<script type="text/javascript">
    $(document).ready(function() {

    });
    String.prototype.endWith=function(endStr){
        var d=this.length-endStr.length;
        return (d>=0&&this.lastIndexOf(endStr)==d)
    }
</script>