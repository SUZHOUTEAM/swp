<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%
    String appPath=request.getContextPath();
    String ticketId = (String)request.getAttribute("ticketId");
%>
<%------- 导入页头信息 -------%>
<jsp:include page="/common/webCommon/user_top.jsp" flush="true">
    <jsp:param name="title" value="委托" />
</jsp:include>
<jsp:include page="/common/webCommon/facilitatorMenu.jsp" flush="true">
    <jsp:param name="menuId" value="#myRelease" />
</jsp:include>
<link rel="stylesheet" href="<%=appPath%>/main/common/elementui/index.css">
<link rel="stylesheet" href="<%=appPath%>/css/wastecircle/publish.css">
<link rel="stylesheet" href="<%=appPath%>/css/myBusiness/business.css?1">
<link rel="stylesheet" href="<%=appPath%>/thirdparty/datetimepicker/css/bootstrap-datetimepicker.min.css"/>
<script type="text/javascript" src="<%=appPath%>/thirdparty/datetimepicker/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=appPath%>/thirdparty/datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>
<section id="app">
    <template>
    <el-breadcrumb separator="/">
        <el-breadcrumb-item>我的易废圈</el-breadcrumb-item>
        <el-breadcrumb-item onclick="vue.detailShow=false">委托</el-breadcrumb-item>
        <el-breadcrumb-item v-if="detailShow">报价详情</el-breadcrumb-item>
    </el-breadcrumb>
        <el-row :gutter="10" class="searchDiv">
            <el-col :xs="12" :sm="12" :md="12" :lg="12" align="left">
                <b>企业名称：</b>
                <el-input v-model="entName" placeholder="请输入企业名称"></el-input>
            </el-col>
            <el-col :xs="12" :sm="12" :md="12" :lg="12" align="right">
                <b>联系电话：</b>
                <el-input v-model="contactTel" placeholder="请输入联系电话"></el-input>
            </el-col>
        </el-row>
        <div class="searchDiv">
                <b>发布时间：</b>
                <el-button type="text" :class="{'select':dateType=='TODAY'}" @click="queryDateType('TODAY')">今天</el-button>
                <el-button type="text" :class="{'select':dateType=='WEEK'}" @click="queryDateType('WEEK')">本周</el-button>
                <el-button type="text" :class="{'select':dateType=='MONTH'}" @click="queryDateType('MONTH')">本月</el-button>
                <div style="display: inline-block;vertical-align: middle;">
                    <el-date-picker
                            v-model="startDateTime"
                            type="date"
                            @change="changeDate"
                            format="yyyy-MM-dd"
                            placeholder="选择开始日期">
                    </el-date-picker>
                    <el-date-picker
                            v-model="endDateTime"
                            type="date"
                            @change="changeDate"
                            format="yyyy-MM-dd"
                            placeholder="选择结束日期">
                    </el-date-picker>
                </div>
                <el-button type="primary" icon="el-icon-search" size="small" @click="search" style="margin-left: 20px">查询</el-button>
        </div>
        <div>
                <div class="publish_container" v-show="!detailShow">
                    <div class="panel panel-body item" v-for="(item,index) in list">
                        <div class="item_message">
                            <el-row :gutter="10" style="margin-bottom:10px">
                                <el-col :xs="17" :sm="17" :md="17" :lg="17"><b>客户名称：</b>
                                    <a href="javascript:" title="查看企业详情" @click="queryEntDetail(item.releaseEntId)">{{item.entName}}</a>
                                    <span class="split">|</span><b>联系电话：</b>{{item.releaseEntContactTel||'--'}}
                                </el-col>
                                <el-col :xs="7" :sm="7" :md="7" :lg="7" align="right" style="font-size:12px"><i class="time_icon"></i>{{item.releaseDate.substring(0,16)}}</el-col>
                            </el-row>
                            <div style="margin-bottom:10px">
                                <b>委托者：</b>{{item.releaseOwner||'--'}}<span class="split">|</span><b>总计：</b><font>{{item.totalWasteCount}}</font>种危废，累计<font>{{item.totalWasteAmountDesc}}</font>
                            </div>
                            <div class="itemReleaseList">
                                <div class="itemRelease" v-for="(wasteItem,index) in item.releaseWasteDetails">
                                    <span>{{wasteItem.wasteName}}</span><span>{{wasteItem.wasteCode}}</span><span>{{wasteItem.wasteAmount}}{{wasteItem.unitValue}}</span>
                                </div>
                            </div>
                            <div style="margin-top: 9px">
                                <b>备注：</b>{{item.releaseRemark||'--'}}。
                            </div>
                            <div style="margin-top: 9px;text-align: right">
                                <el-button type="text"  style="margin-right: 20px;" v-if="item.releaseStatus!='DONE'&&item.releaseStatus!='REFUSED'" title="编辑" @click="edit(list[index],index)">编辑</el-button>
                                <el-button type="text" style="margin-right: 20px;" v-if="!item.activityName&&item.releaseStatus!='END'" title="完结" @click="close(item,index)">完结</el-button>
                                <el-tag v-if="!item.activityName&&item.releaseStatus=='END'" style="margin-right: 20px;" type="warning">已完结</el-tag>
                                <el-button type="text" style="margin-right: 20px;" v-if="item.releaseStatus!='DONE'&&item.releaseStatus!='REFUSED'" title="删除" @click="del(item.releaseId)">删除</el-button>
                                <el-button type="text" style="width:85px" @click="inquiryDetail(index)" v-if="item.inquiryEntCount>0">查看报价({{item.inquiryEntCount}})</el-button>
                                <el-button type="text" style="color:#cccccc;width:85px;cursor: default" v-if="item.inquiryEntCount==0">{{item.releaseStatus=='REFUSED'?'被谢绝':'暂无报价'}}</el-button>
                            </div>
                        </div>
                    </div>
                    <div v-show="!list||list.length==0" style="padding: 20px;text-align: center"><span>暂无相关数据</span></div>
                    <div class="loading" v-show="listLoading"><img src="<%=appPath %>/main/pc/img/load.gif"></div>
                    <div v-show="list&&list.length>0" align="right">
                        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page.sync="pageIndex" :page-sizes="[5,10,20,30,50]" :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper" :total="total">
                        </el-pagination>
                    </div>
                </div>
                <div class="inquiry_list" v-show="detailShow" v-if="list.length>0&&list[index]">
                    <div class="panel panel-body item">
                        <div class="item_message">
                            <el-row :gutter="10" style="margin-bottom:10px">
                                <el-col :xs="17" :sm="17" :md="17" :lg="17"><b>发布者：</b>{{list[index].releaseOwner||'--'}}<span class="split">|</span><b>总计：</b><font>{{list[index].totalWasteCount}}</font>种危废，累计<font>{{list[index].totalWasteAmountDesc}}</font>
                                    <span class="split">|</span >来源：
                                    <a href="javascript:" style="color: #1171d1" v-if="list[index].activityName" @click="viewActivityDetail(list[index].activityId)">{{list[index].activityName}}</a>
                                    <font v-else>资源池</font>
                                </el-col>
                                <el-col :xs="7" :sm="7" :md="7" :lg="7" align="right" style="font-size:12px"><i class="time_icon"></i>{{list[index].releaseDate.substring(0,16)}}</el-col>
                            </el-row>
                            <div class="itemReleaseList">
                                <div class="itemRelease" v-for="(wasteItem,index) in list[index].releaseWasteDetails">
                                    <span>{{wasteItem.wasteName}}</span><span>{{wasteItem.wasteCode}}</span><span>{{wasteItem.wasteAmount}}{{wasteItem.unitValue}}</span>
                                </div>
                            </div>
                            <div style="margin-top: 9px">
                                <b>备注：</b>{{list[index].releaseRemark}}。
                            </div>
                            <div style="margin-top: 9px;text-align: right">
                                <el-button type="text"  style="margin-right: 20px;" v-if="list[index].releaseStatus!='DONE'&&list[index].releaseStatus!='REFUSED'" title="编辑" @click="edit(list[index],index)">编辑</el-button>
                                <el-button type="text" style="margin-right: 20px;" v-if="!list[index].activityName&&list[index].releaseStatus!='END'" title="完结" @click="close(list[index],index)">完结</el-button>
                                <el-tag v-if="!list[index].activityName&&list[index].releaseStatus=='END'" style="margin-right: 20px;" type="warning">已完结</el-tag>
                                <el-button type="text" style="margin-right: 20px;" @click="del(list[index].releaseId)">删除</el-button>
                                <el-button type="text" style="width:85px" @click="inquiryDetail(index)" v-if="list[index].inquiryEntCount>0">查看报价({{list[index].inquiryEntCount}})</el-button>
                                <el-button type="text" style="color:#cccccc;width:85px;cursor: default" v-if="list[index].inquiryEntCount==0">暂无报价</el-button>
                            </div>
                        </div>
                    </div>
                    <div v-if="inquiryList.length>0" style="margin: -9px 10px 0;">
                        <div class="buy_item panel panel-body" v-for="item in inquiryList">
                            <el-row :gutter="10" style="margin-bottom:10px">
                                <el-col :xs="21" :sm="21" :md="21" :lg="21">
                                    <span class="status" :class="statusClass[item.busiStatus]">{{item.busiStatus}}</span><b style="margin-left: 14px">单位：</b><a href="javascript:" title="查看企业详情" @click="entDetail(item.inquiryEntId)"><em style="margin-right: 4px">{{item.inquiryEnterName}}</em></a>
                                    <a class="qipao_logo" href="javascript:" @click="contact(item.inquiryEntId,item.inquiryEnterName)" title="联系TA" style="margin-right: 14px;">
                                        <img src="../main/pc/img/qipao.jpg"></a>
                                    <b>报价方式：</b><em>{{item.quotedType==0?'打包报价':'单独报价'}}</em><b>金额：</b><em><b style="color: #ff474c;font-weight:bold">￥{{item.totalPriceStr}}</b></em>
                                    <b>总数：</b><em><b style="color: #1171d1">{{item.totalAmount}}</b><span v-if="item.inquiryWasteCount==list[index]['totalWasteCount']">(包含全部)</span></em>
                                </el-col>
                                <el-col :xs="3" :sm="3" :md="3" :lg="3" align="right" style="font-size:12px;color: #6a7580;">
                                    {{item.inquiryDate.substring(0,16)}}
                                </el-col>
                            </el-row>
                            <el-table :data="item.inquiryDetail" border
                                      style="width:100%">
                                <el-table-column
                                        prop="wasteName"
                                        label="危废名称">
                                </el-table-column>
                                <el-table-column
                                        prop="wasteCode"
                                        label="危废代码">
                                </el-table-column>
                                <el-table-column
                                        prop="amount"
                                        align="right"
                                        label="数量">
                                </el-table-column>
                                <el-table-column
                                        prop="unitValue"
                                        label="单位">
                                </el-table-column>
                                <el-table-column
                                        align="right"
                                        v-if="item.quotedType!=0"
                                        label="单价" >
                                    <template scope="scope">
                                        <span v-if="scope.row.price == 0" >-</span>
                                        <span v-if="scope.row.price!=0" >{{scope.row.priceStr}}</span>
                                    </template>
                                </el-table-column>
                                <el-table-column
                                        align="right"
                                        v-if="item.quotedType!=0"
                                        label="单项总价" >
                                    <template scope="scope">
                                        <span v-if="scope.row.wasteTotalPrice == 0" >-</span>
                                        <span style="color: #ff474c;" class='wasteTotalPrice' v-if="scope.row.wasteTotalPrice!=0">￥{{scope.row.wasteTotalPriceStr}}</span>
                                    </template>
                                </el-table-column>
                            </el-table>
                            <el-row :gutter="10" style="margin-top:10px">
                                <el-col :xs="24" :sm="24" :md="24" :lg="24"><b>备注：</b><em>{{item.inquiryRemark||'--'}}</em></el-col>
                            </el-row>
                            <div style="margin-top: 9px;text-align: right" v-show="item.busiStatus=='待确认'">
                                <el-button style="background-color: #ebecf0" @click="reject(item)">谢绝</el-button>
                                <el-button type="primary" @click="confirm(item)" class="over">成交</el-button>
                            </div>
                        </div>
                        <div style="padding: 50px 0;text-align: center" v-show="!inquiryList||inquiryList.length==0">
                            暂无相关数据
                        </div>
                        <div v-show="inquiryList&&inquiryList.length>0" align="right">
                            <el-pagination @size-change="handleSizeChange_inquiry" @current-change="handleCurrentChange_inquiry" :current-page.sync="inquiryIndex" :page-sizes="[5,10,20,30,50]" :page-size="inquirySize" layout="total, sizes, prev, pager, next, jumper" :total="inquiryTotal">
                            </el-pagination>
                        </div>
                    </div>
                </div>

        </div>
            <el-dialog
                    class="edit-dialog"
                    :title="currentRelease&&currentRelease.entName.concat('---委托编辑')"
                    :visible.sync="dialogVisible"
                    width="80%">
                    <div class="edit-container" v-if="currentRelease">
                        <el-table :data="currentWasteList" v-loading="publishLoading"
                                  element-loading-text="正在发布中" border max-height="300"
                                  style="width:100%">
                            <el-table-column
                                    prop="wasteName"
                                    label="危废名称">
                            </el-table-column>
                            <el-table-column
                                    prop="wasteCode"
                                    label="危废代码">
                            </el-table-column>
                            <el-table-column
                                    prop="amount"
                                    align="right"
                                    label="数量">
                                <template scope="scope">
                                    <el-input v-model="scope.row['amount']" style="width:150px" placeholder="请输入数量"></el-input>
                                </template>
                            </el-table-column>
                            <el-table-column
                                    prop="unitValue"
                                    label="单位">
                            </el-table-column>
                            <el-table-column
                                    width="80px"
                                    label="操作">
                                <template scope="scope">
                                    <el-button type="text" @click="deleteRow(scope.$index,currentWasteList)">移除</el-button>
                                </template>
                            </el-table-column>
                        </el-table>
                        <el-row style="margin-top: 20px">
                            <el-col :span="18">
                                <b>备注：</b><el-input style="width: 400px" v-model="currentRelease.releaseRemark" placeholder="请输入备注"></el-input>
                            </el-col>
                            <el-col :span="6" align="right">
                                <el-button type="primary" icon="el-icon-delete" @click="quickDel">一键移除</el-button>
                            </el-col>
                        </el-row>
                        <el-row style="margin-top: 20px">
                            <el-col :span="12" style="line-height: 41px">
                                <b>合计：</b>{{currentWasteList.length}}种危废，{{getQualityTextBySelection()}}
                            </el-col>
                            <el-col :span="12" align="right">
                                <el-button type="primary" icon="el-icon-plus" @click="addWaste(currentRelease.releaseEntId)">添加危废</el-button>
                            </el-col>
                        </el-row>
                    </div>
                <span slot="footer" class="dialog-footer">
                    <el-button @click="dialogVisible = false">取 消</el-button>
                    <el-button type="primary" @click="publish">确 定</el-button>
              </span>
            </el-dialog>
    </template>
</section>
<!-- 先引入 Vue -->
<script src="<%=appPath%>/main/common/elementui/vue.js"></script>
<!-- 引入组件库 -->
<script src="<%=appPath%>/main/common/elementui/index.js"></script>
<script src="<%=appPath %>/app/js/constants.js"></script>
<script src="<%=appPath %>/thirdparty/la-number/la-number.js"></script>
<script>
    var vue=new Vue({
        el: '#app',
        data: {
            pageIndex:getParam('pageIndex')?getParam('pageIndex')*1:1,
            pageSize:5,
            list:[],
            listLoading:true,
            total:0,
            index:0,
            inquiryList:[],
            inquiryIndex:1,
            inquirySize:5,
            inquiryTotal:0,
            inquiryLoading:false,
            detailShow:false,
            statusClass:statusClass,
            activityId:'',
            activityList:[],
            dialogVisible:false,
            currentIndex:0,
            currentRelease:null,
            currentWasteList:[],
            publishLoading:false,
            totalQtyText:'0吨',
            totalQtyT:0,
            releaseIndex:getParam('releaseIndex'),
            dateType:'',
            entName:'',
            contactTel:'',
            startDateTime:'',
            endDateTime:''
        },
        created:function() {
            this.getList();
            this.getActivityList();
        },
        methods: {
            getList: function () {
                this.list=[];
                var _this = this;
                this.entName=this.entName.replace(/(^\s*)|(\s*$)/g, '');
                this.contactTel=this.contactTel.replace(/(^\s*)|(\s*$)/g, '');
                var param={
                    activityId:'',
                    entName:this.entName,
                    releaseEntContactTel:this.contactTel,
                    dateType: this.dateType,
                    startDateTime:this.startDateTime?this.startDateTime.format('yyyy-MM-dd hh:mm:ss'):'',
                    endDateTime:this.endDateTime?(this.endDateTime.format('yyyy-MM-dd')+' 23:59:59'):'',
                };
                if(_this.releaseIndex||_this.releaseIndex==0) {
                    param = JSON.parse(readCookie("searchCondition"));
                    this.entName=param.entName;
                    this.contactTel=param.releaseEntContactTel;
                    this.dateType=param.dateType;
                    $('#start_date').val(param.startDateTime.substring(0,10));
                    $('#end_date').val(param.endDateTime.substring(0,10))
                }
                document.cookie = "searchCondition="+JSON.stringify(param);
                ajax({
                    url: '/entRelease/listWasteEntReleaseByEntId.htm?ticketId=' + getParam('ticketId') + '&pageIndex=' + (this.pageIndex) + '&pageSize=' + this.pageSize,
                    dataType: 'json',
                    contentType: 'application/json',
                    data: JSON.stringify(param),
                    success: function (result) {
                        console.log(result);
                        _this.listLoading = false;
                        if (result.status == 1 && result.data.wasteEntRelaseList) {
                            _this.list=result.data.wasteEntRelaseList;
                            _this.total=result.data.paging.totalRecord;
                            if(_this.releaseIndex||_this.releaseIndex==0){
                                _this.edit(_this.list[_this.releaseIndex],_this.releaseIndex);
                                _this.releaseIndex=null;
                            }
                        }
                    }
                })
            },
            publish:function () {
                var param={
                    remark:this.currentRelease.releaseRemark,
                    releaseCount:this.currentWasteList.length,
                    totalAmountDesc:this.totalQtyText,
                    totalAmount:this.totalQtyT,
                    entReleaseId:this.currentRelease.releaseEntId
                };
                var entReleaseDetails=[];
                var flag=false;
                var errorMsg='';
                var pattern=/^(?=([0-9]{1,10}$|[0-9]{1,7}\.))(0|[1-9][0-9]*)(\.[0-9]{1,3})?$/;
                for(var i in this.currentWasteList){
                    var obj={};
                    var selection=this.currentWasteList[i];
                    obj.entWasteId=selection['entWasteId'];
                    obj.releaseAmount=selection['amount'];
                    if(selection['amount']==0){
                        flag=true;
                        errorMsg='危废数量不能为0,或请移除改项';
                    }
                    if(!pattern.test(selection['amount'])){
                        flag=true;
                        errorMsg='危废数量总长不超过十位,最多三位小数';
                    }
                    entReleaseDetails.push(obj);
                }
                if(flag){
                    $.notify(errorMsg,{status:'info',timeout:2000});
                    return;
                }
                param.releaseDetail=entReleaseDetails;
                console.log(param);
                var _this=this;
//                return;
                vue.$confirm('此操作将修改该发布,修改成功后该发布下的报价请重新报价, 是否确定?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(function(){
                    _this.publishLoading=true;
                    ajax({
                        url:'/entRelease/save.htm?ticketId='+getParam('ticketId'),
                        dataType:'json',
                        contentType:'application/json',
                        data:JSON.stringify(param),
                        success:function (result) {
                            console.log(result);
                            if(result.status==1){
                                ajax({
                                    url:'/entRelease/deleteWasteEntReleaseByReleaseId.htm?ticketId=<%=ticketId%>',
                                    contentType:'application/json',
                                    data:JSON.stringify({
                                        releaseId:vue.currentRelease.releaseId
                                    }),
                                    dataType:'json',
                                    success:function (data) {
                                        _this.publishLoading = false;
                                        if(data.status==1&&data.data==true){
                                            _this.dialogVisible=false;
                                            $.notify('修改成功',{status:'success',timeout:2500});
                                            vue.pageIndex=1;
                                            vue.getList();
                                        }
                                    }
                                })
                            }
                        }
                    })
                }).catch(function(){});
            },
            getInquiryList: function () {
                this.inquiryList=[];
                var _this = this;
                var param = {
                    releaseId:this.list[this.index]['releaseId'],
                    pageIndex:this.inquiryIndex,
                    pageSize:this.inquirySize
                };
                ajax({
                    url: '/entInquiry/listEntInquiryByReleaseId.htm',
                    dataType: 'json',
                    data: param,
                    success: function (result) {
                        console.log(result);
                        _this.inquiryLoading = false;
                        if (result.status == 1 && result.data.inquiryList) {
                            _this.inquiryList=result.data.inquiryList;
                            _this.inquiryTotal=result.data.paging.totalRecord;
                        } else {

                        }
                    }
                })
            },
            search:function () {

              this.pageIndex=1;
              this.getList();
            },
            queryDateType:function (dateType) {
                this.dateType=dateType;
                this.startDateTime='';
                this.endDateTime='';
                this.search();
            },
            changeDate:function () {
                this.dateType='';
                this.search();
            },
            getActivityList:function(){
                var _this=this;
                ajax({
                    url:'/wasteActivity/listActiviyNameByApplyEntId',
                    dataType: 'json',
                    success:function (data) {
                        var obj={'activityName':'全部',activityId:''};
                        var defaultObj ={'activityName':'委托处理',activityId:'resourcePull'};
                        var arr=[obj,defaultObj];
                        arr=arr.concat(data.data);
                        _this.activityList = arr;
                    }
                });
            },
            handleSizeChange:function(val) {
                this.pageIndex=1;
                this.pageSize = val;
                this.getList()
            },
            handleCurrentChange:function(val) {
                this.pageIndex = val;
                this.getList()
            },
            handleSizeChange_inquiry:function(val) {
                this.inquiryIndex=1;
                this.inquirySize = val;
                this.getInquiryList();
            },
            handleCurrentChange_inquiry:function(val) {
                this.inquiryIndex = val;
                this.getInquiryList();
            },
            inquiryDetail:function (index) {
                this.index=index;
                this.detailShow=true;
                this.inquiryIndex=1;
                this.getInquiryList();
            },
            activityNameChange:function(){
                this.pageIndex=1;
                this.list=[];
                this.detailShow=false;
                this.getList();
            },
            goBack:function () {
                this.detailShow=false;
            },
            contact:function (entId,inquiryEnterName) {
                getEnterpriseContacts(entId,inquiryEnterName,'委托页面');
            },
            viewActivityDetail:function (activityId) {
                window.location='<%=appPath%>/main/pc/view/wasteActivityDetail.html?ticketId=<%=ticketId%>&activityId='+activityId;
            },
            del:function (releaseId) {
                vue.$confirm('此操作将删除该发布, 是否确定?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(function(){
                    ajax({
                        url:'/entRelease/deleteWasteEntReleaseByReleaseId.htm?ticketId=<%=ticketId%>',
                        contentType:'application/json',
                        data:JSON.stringify({
                            releaseId:releaseId
                        }),
                        dataType:'json',
                        success:function (data) {
                            if(data.status==1&&data.data==true){
                                vue.detailShow=false;
                                $.notify('删除成功',{status:'success',timeout:2500});
                                vue.pageIndex=1;
                                vue.getList();
                            }
                        }
                    })
                }).catch(function(){});
            },
            edit:function (item,index) {
                this.currentIndex=index;
                this.currentRelease=item;
                this.dialogVisible=true;
                var obj={};
                for(var i in item.releaseWasteDetails){
                    obj[item.releaseWasteDetails[i]['entWasteId']]=item.releaseWasteDetails[i]['wasteAmount'];
                }
                ajax({
                    url:'/entWaste/listEntWasteByEntId.htm',
                    data:{
                        entId:item.releaseEntId
                    },
                    success:function (result) {
                        console.log(result);
                        if(result.status==1){
                            var currentWasteList=result.data;
                            var arr1=[];
                            var arr2=[];
                            for(var j in currentWasteList){
                                if(obj[currentWasteList[j]['entWasteId']]){
                                    currentWasteList[j]['amount']=obj[currentWasteList[j]['entWasteId']];
                                    arr1.push(currentWasteList[j]);
                                }else{
                                    currentWasteList[j]['amount']=0;
                                    arr2.push(currentWasteList[j]);
                                }
                            }
                            vue.currentWasteList=arr1.concat(arr2);
                            vue.totalQtyText='0吨';
                            vue.totalQtyT=0;
                        }
                    }
                })
            },
            deleteRow:function (index,list) {
                list.splice(index,1);
            },
            addWaste:function (releaseEntId) {
                window.location='<%=appPath%>/facilitator/addWaste.htm?ticketId=<%=ticketId%>&customerId='+releaseEntId+'&pageIndex='+this.pageIndex+'&releaseIndex='+this.currentIndex;
            },
            quickDel:function () {
                for(var i=vue.currentWasteList.length-1;i>=0;i--){
                    if(vue.currentWasteList[i]['amount']==0){
                        vue.currentWasteList.splice(i,1);
                    }
                }
            },
            close:function (item,index) {
                var releaseId=item.releaseId;
                vue.$confirm('如选择完结，系统将不再公开此次危废信息', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(function(){
                    ajax({
                        url:'/entRelease/endWasteEntReleaseByReleaseId?ticketId=<%=ticketId%>',
                        data:JSON.stringify({releaseId:releaseId}),
                        contentType:'application/json',
                        success:function (result) {
                            if(result.status==1&&result.data==true){
                                $.notify('完结成功',{status:'success',timeout:2500});
                                item.releaseStatus='END';
                                Vue.set(vue.list,index,item);
                            }
                        }
                    })
                }).catch(function(){});
            },
            reject:function (item) {
                vue.$confirm('此操作将拒绝该报价, 是否确定?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(function(){
                    ajax({
                        url:'/entInquiry/rejectEntInquiry.htm',
                        data:{
                            inquiryId:item.inquiryId,
                            inquiryEntId:item.inquiryEntId
                        },
                        dataType:'json',
                        success:function (data) {
                            if(data.status==1&&data.data==true){
                                $.notify('拒绝成功',{status:'success',timeout:2500});
                                vue.inquiryIndex=1;
                                vue.getInquiryList();
                                collectingUserBehavior(getParam('ticketId'),'RESPONSE');
                            }
                        }
                    })
                }).catch(function(){});
            },
            confirm:function (item) {
                var activityId=this.list[this.index].activityId;
                var releaseId=this.list[this.index].releaseId;
                var inquiryId=item.inquiryId;
                var param={
                    inquiryId:inquiryId,
                    inquiryEntId:item.inquiryEntId,
                    disEntId:item.entDisEntId
                };
                if(activityId){
                    param.activityId=activityId;
                }
                if(releaseId){
                    param.releaseId=releaseId;
                }
                vue.$confirm('此操作将确认该报价,确认后将生成新的订单，且该委托不可编辑和删除，是否确定?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(function(){
                    ajax({
                        url:'/entInquiry/confirmEntInquiry.htm',
                        data:param,
                        dataType:'json',
                        success:function (data) {
                            if(data.status==1&&data.data==true){
                                $.notify('确认成功,已形成新的订单',{status:'success',timeout:2500});
                                vue.inquiryIndex=1;
                                vue.getInquiryList();
                                collectingUserBehavior(getParam('ticketId'),'RESPONSE');
                            }
                        }
                    })
                }).catch(function(){});
            },
            entDetail:function (entId) {
                window.location = "<%=appPath%>/enterprise/czDetail.htm?ticketId=<%=ticketId%>&enterpriseId=" + entId + "&breadcrumbName=委托-myRelease";
            },
            queryEntDetail:function (entId) {
                window.location='<%=appPath%>/myenterprise/viewEnterpriseDetail.htm?ticketId=<%=ticketId%>&onlyViewFlg=true&enterpriseId='+entId+'&breadcrumbName=委托&index=myRelease';
            },
            getQualityTextBySelection:function() {
                var arr=this.currentWasteList;
                if(arr.length==0){
                    this.totalQtyText='0吨';
                    this.totalQtyT =0;
                    return '0吨';
                }
                var obj = {};
                var laNumber = new $.LaNumber();
                for(var i in arr) {
                    var key = arr[i]['unitValue'];
                    var value = parseFloat(arr[i]['amount']);
                    if(key=='吨'||key=='千克'||key=='克'){
                        switch (key){
                            case '千克':
                                value=laNumber.div(value,1000);
                                break;
                            case '克':
                                value=laNumber.div(value,1000000);
                                break;
                        }
                        if(obj['吨']){
                            obj['吨']=laNumber.add(obj['吨'],value);
                        }else{
                            obj['吨']=value;
                        }
                    }else{
                        if(obj[key]) {
                            obj[key] =laNumber.add(obj[key],value) ;
                        } else {
                            obj[key] = value;
                        }
                    }
                }
                var str='';
                for (var prop in obj) {
                    str+=obj[prop]+prop+'，';
                }
                str=str.substring(0,str.length-1);
                this.totalQtyText=str;
                this.totalQtyT = obj['吨'];
                return str;
            }
        }

    })
</script>
<%------- 导入底部信息 -------%>
<jsp:include page="/common/webCommon/bottom.jsp" flush="true" />

<%------- 结束导入底部信息 -------%>
<script type="text/javascript">
    Date.prototype.format = function(fmt) {
        var o = {
            "M+" : this.getMonth()+1,                 //月份
            "d+" : this.getDate(),                    //日
            "h+" : this.getHours(),                   //小时
            "m+" : this.getMinutes(),                 //分
            "s+" : this.getSeconds(),                 //秒
            "q+" : Math.floor((this.getMonth()+3)/3), //季度
            "S"  : this.getMilliseconds()             //毫秒
        };
        if(/(y+)/.test(fmt)) {
            fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
        }
        for(var k in o) {
            if(new RegExp("("+ k +")").test(fmt)){
                fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
            }
        }
        return fmt;
    }
</script>
</body>