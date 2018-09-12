<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%
    String appPath=request.getContextPath();
    String ticketId = (String)request.getAttribute("ticketId");
%>
<%------- 导入页头信息 -------%>
<jsp:include page="/common/webCommon/user_top.jsp" flush="true">
    <jsp:param name="title" value="我的活动" />
</jsp:include>
<jsp:include page="/common/webCommon/wasteCircleMenu.jsp" flush="true">
    <jsp:param name="menuId" value="#myActivity" />
</jsp:include>
<link rel="stylesheet" href="<%=appPath%>/main/common/elementui/index.css">
<link rel="stylesheet" href="<%=appPath%>/css/wastecircle/publish.css">
<link rel="stylesheet" href="<%=appPath%>/css/myBusiness/business.css">
<link rel="stylesheet" href="<%=appPath%>/css/myBusiness/sign.css">
<link rel="stylesheet" href="<%=appPath%>/main/pc/css/money-dialog.css?1">
<link rel="stylesheet" href="<%=appPath%>/thirdparty/datetimepicker/css/bootstrap-datetimepicker.min.css"/>
<script type="text/javascript" src="<%=appPath%>/thirdparty/datetimepicker/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=appPath%>/thirdparty/datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>
<section id="app">
    <template>
    <el-breadcrumb separator="/">
        <el-breadcrumb-item>我的易废圈</el-breadcrumb-item>
        <el-breadcrumb-item onclick="vue.detailShow=false">我的活动</el-breadcrumb-item>
        <el-breadcrumb-item v-if="detailShow">{{list[index]['activityName']}}-活动申请单</el-breadcrumb-item>
    </el-breadcrumb>
    <div class="">
        <div>
                <div class="publish_container" v-show="!detailShow">
                    <el-row style="margin-bottom: 10px" class="searchDiv">
                        <el-col :span="12">
                            <b>活动状态：</b>
                            <el-button type="text" :class="{'select':orderStatus==''}" @click="queryStatus('')">全部</el-button>
                            <el-button type="text" :class="{'select':orderStatus=='1'}" @click="queryStatus('1')">未开始</el-button>
                            <el-button type="text" :class="{'select':orderStatus=='2'}" @click="queryStatus('2')">进行中</el-button>
                            <el-button type="text" :class="{'select':orderStatus=='3'}" @click="queryStatus('3')">已结束</el-button>
                        </el-col>
                        <el-col :span="12" align="right">
                            <el-button type="primary" size="small" icon="el-icon-plus" @click="add">创建活动</el-button>
                        </el-col>
                    </el-row>
                    <%--<div class="searchDiv" style="margin-bottom: 10px">--%>
                            <%----%>
                    <%--</div>--%>
                    <div class="panel panel-body" v-for="(item,index) in list">
                        <div class="item_message">
                            <el-row :gutter="10" style="margin-bottom: 12px">
                                <el-col :xs="12" :sm="12" :md="12" :lg="12">
                                    <span class="status" :class="ACTIVITYSTATUS[item.status-1]&&ACTIVITYSTATUS[item.status-1]['code']" style="padding: 0 10px">{{ACTIVITYSTATUS[item.status-1]['value']}}</span>
                                    <b style="margin-left:8px">活动标题：</b><span>{{item.activityName}}</span>
                                </el-col>
                                <el-col :xs="12" :sm="12" :md="12" :lg="12" align="right" style="font-size:12px">
                                    <b>活动时间：</b><span>{{formatDate(item.startDate,'yyyy/MM/dd')}}-{{formatDate(item.endDate,'yyyy/MM/dd')}}</span>
                                </el-col>
                            </el-row>
                            <table style="width: 100%">
                                <tr>
                                    <td style="width: 35%"><b>申请量：</b><font>{{item.applyAmount||0}}吨</font></td>
                                    <td style="width: 50%">
                                        <b>申请数：</b><font>{{item.applyEntCount||0}}家</font>
                                        <span v-for="(releaseStatus,i) in item.releaseStatusList">
                                            <b>{{INQUIRYSTATUS[releaseStatus.status]}}：</b><font>{{releaseStatus.count||0}}</font>
                                        </span>
                                    </td>
                                    <td rowspan="2" style="width: 15%;text-align: center;padding: 8px 0;line-height: 20px">
                                        <span v-if="ACTIVITYSTATUS[item.status]&&ACTIVITYSTATUS[item.status]['code']=='DRAFT'" class="noSubmit">暂无申请</span>
                                        <el-button v-else type="text" @click="inquiryDetail(index)">查看详情</el-button><br/>
                                        <el-button type="text" v-if="ACTIVITYSTATUS[item.status-1]['code']=='DRAFT'" @click="edit(item.activityId)">编辑</el-button>
                                        <el-button type="text" v-if="ACTIVITYSTATUS[item.status-1]['code']=='SUBMIT'" @click="pause(item,index)" style="margin-left: 0">停止</el-button>
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width: 35%"><b>成交量：</b><font>{{item.volumeAmount||0}}吨</font></td>
                                    <td style="width: 50%"><b>成交数：</b><font>{{item.approveEntCount||0}}家</font></td>
                                </tr>
                            </table>
                            <el-row :gutter="10" style="line-height: 42px;margin-top: 7px" v-if="item.payStatus">
                                <el-col :xs="20" :sm="20" :md="20" :lg="20">
                                    <b>覆盖产废企业数：</b><span>{{item.coverEntCount}}家</span>
                                    <b style="margin-left:15px">需消费易废币：</b><span style="color: #f05050">{{item.bitCion}}个</span>
                                    <el-tooltip class="item" effect="dark" :content="'5000（基础服务费）+'+Math.floor(item.coverEntCount*0.5)+'（运营费）'" placement="top-start">
                                        <el-button type="text" style="width: 29px;color: #6a7580;font-size: 16px;padding: 0 2px;position: relative;top:0.49px;" icon="el-icon-question"></el-button>
                                    </el-tooltip>
                                </el-col>
                                <el-col :xs="4" :sm="4" :md="4" :lg="4" align="right" style="font-size:12px">
                                    <el-tag type="success" v-if="item.payStatus=='PAYMENT'">支付成功</el-tag>
                                    <el-button type="primary" v-else size="small" @click="checkEntAccount(item,index)">确认支付</el-button>
                                </el-col>
                            </el-row>
                        </div>
                    </div>
                    <div v-show="!list||list.length==0&&!listLoading" style="padding: 20px;text-align: center"><span>暂无相关数据</span></div>
                    <div class="loading" v-show="listLoading"><img src="<%=appPath %>/main/pc/img/load.gif"></div>
                    <div v-show="list&&list.length>0" align="right">
                        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page.sync="pageIndex" :page-sizes="[5,10,20,30,50]" :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper" :total="total">
                        </el-pagination>
                    </div>
                </div>
                <div class="inquiry_list" v-show="detailShow" v-if="list.length>0&&list[index]">
                    <div class="searchDiv">
                        <b>状态：</b>
                        <el-button type="text" :class="{'select':actionStatus==''}" @click="queryActionStaus('')">全部</el-button>
                        <el-button type="text" :class="{'select':actionStatus=='RELEASED'}" @click="queryActionStaus('RELEASED')">待处理({{statusCount.RELEASED}})</el-button>
                        <el-button type="text" :class="{'select':actionStatus=='REFUSED'}" @click="queryActionStaus('REFUSED')">谢绝({{statusCount.REFUSED}})</el-button>
                        <el-button type="text" :class="{'select':actionStatus=='SUBMIT'}" @click="queryActionStaus('SUBMIT')">询价中({{statusCount.SUBMIT}})</el-button>
                        <el-button type="text" :class="{'select':actionStatus=='DONE'}" @click="queryActionStaus('DONE')">成交({{statusCount.DONE}})</el-button>
                    </div>
                    <div v-if="inquiryList.length>0" style="margin: -9px 0 0;">
                        <div class="buy_item panel panel-body" v-for="(item,index) in inquiryList">
                            <el-row :gutter="10" style="margin-bottom:10px">
                                <el-col :xs="17" :sm="17" :md="17" :lg="17">
                                    <b>产废单位：</b><em style="margin-right: 4px">{{item.entName}}</em></a>
                                    <a class="qipao_logo" href="javascript:" @click="contact(item.releaseEntId,item.entName)" title="联系TA" style="margin-right: 14px;">
                                        <img src="../main/pc/img/qipao.jpg"></a>
                                    <b v-if="item.entAddress"><i class="publisharea"></i></b><em>{{item.entAddress}}</em>
                                    <span class="status" v-if="INQUIRYSTATUS[item.releaseStatus]&&item.releaseStatus!='SUBMIT'&&item.releaseStatus!='RELEASED'" :class="item.releaseStatus" style="padding: 0 10px;">{{INQUIRYSTATUS[item.releaseStatus]}}</span>
                                    <span class="status" V-else-if="item.releaseStatus=='SUBMIT'" :class="item.releaseStatus" title="查看询价" style="padding: 0 10px;cursor:pointer;" @click="goMyInquiry">{{INQUIRYSTATUS[item.releaseStatus]}}</span>
                                </el-col>
                                <el-col :xs="7" :sm="7" :md="7" :lg="7" align="right" style="font-size:12px;color: #6a7580;">
                                    <b>{{formatDate(item.releaseDate,'yyyy-MM-dd HH:mm')}}</b>
                                </el-col>
                            </el-row>
                            <el-row :gutter="10" style="margin-bottom:10px" class="total">
                                <el-col>
                                    <b>总计：</b><em><font>{{item.totalWasteCount}}</font>种危废,累计<font>{{item.totalWasteAmountDesc}}</font></em>
                                </el-col>
                            </el-row>
                            <div class="itemReleaseList">
                                <div class="itemRelease" v-for="(item1,i) in item.releaseWasteDetails">
                                    <span> {{item1.wasteName}}</span><span>{{item1.wasteCode}}</span><span>{{item1.wasteAmount}}{{item1.unitValue}}</span>
                                </div>
                            </div>
                            <el-row :gutter="10" style="margin-top:10px" v-if="item.releaseRemark">
                                <el-col :xs="24" :sm="24" :md="24" :lg="24"><b>备注：</b><em>{{item.releaseRemark||'--'}}</em></el-col>
                            </el-row>
                            <div style="margin-top: 9px;text-align: right">
                                <el-tag v-if="item.tagInfo.tagStatus" style="margin-right: 20px">{{item.tagInfo.tagStatus}}</el-tag>
                                <el-button type="text" class="signBtn" @click="getSign(item,index)"><i class="signIcon"></i>标记({{item.tagInfo?item.tagInfo.count:0}})<i class="el-icon-arrow-right"></i></el-button>
                                <el-button style="background-color: #ebecf0"  @click="reject(item.releaseId)"  v-if="item.releaseStatus=='RELEASED'">谢绝</el-button>
                                <el-button type="primary" @click="buy(item.releaseId)"  v-if="item.releaseStatus=='RELEASED'" class="over">询价</el-button>
                            </div>
                        </div>
                        <div v-show="inquiryList&&inquiryList.length>0" align="right">
                            <el-pagination @size-change="handleSizeChange_inquiry" @current-change="handleCurrentChange_inquiry" :current-page.sync="inquiryIndex" :page-sizes="[5,10,20,30,50]" :page-size="inquirySize" layout="total, sizes, prev, pager, next, jumper" :total="inquiryTotal">
                            </el-pagination>
                        </div>
                    </div>
                    <div style="padding: 50px 0;text-align: center" v-show="!inquiryList||inquiryList.length==0">
                        暂无相关数据
                    </div>
                </div>

        </div>
    </div>
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
                <p><em>总数：</em><b><font color="#1171d1">{{signItem.totalWasteAmountDesc}}</font></b></p>
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
                        <div v-if="signType=='SAMPLE'">
                            <el-radio-group v-model="sampleStatus">
                                <el-radio label="SAMPLED">已取样</el-radio>
                                <el-radio label="DELIVERED_LAB">已送实验室</el-radio><br/>
                                <el-radio label="QUALIFIED" style="margin-top: 10px">合格</el-radio>
                                <el-radio label="UNQUALIFIED" style="margin-top: 10px;margin-left: 44px">不合格</el-radio>
                            </el-radio-group>
                            <el-input type="textarea" style="margin-top: 10px"
                                      :rows="2"
                                      placeholder="备注"
                                      v-model="sampleRemark">
                            </el-input>
                        </div>
                        <el-radio-group v-model="contractStatus" v-if="signType=='CONTRACT'">
                            <el-radio label="DRAFTCONTRACT">合同已拟定</el-radio>
                            <el-radio label="DELIVERYCONTRACT">合同已寄出</el-radio><br/>
                            <el-radio label="SIGNCONTRACT" style="margin-top: 10px">合同已签定</el-radio>
                        </el-radio-group>
                        <el-input v-if="signType=='PRICE'"
                                  type="textarea"
                                  :rows="2"
                                  placeholder="备注"
                                  v-model="priceRemark">
                        </el-input>
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
                            取样时间：{{item.sampleDate?item.sampleDate.substring(0,10):''}}<br/>
                            联系人：{{item.contacts}}<br/>
                            联系电话：{{item.contactsTel}}
                        </div>
                        <div slot="description" class="signContent" v-if="item.tagType=='SAMPLE'">
                            <div v-if="item.comments&&item.comments.split('#').length>1">
                                <div>{{item.comments.split('#')[0]}}</div>
                                <div><span style="font-size: 12px;color: #6b7580;">备注：</span>{{item.comments.split('#')[1]}}</div>
                            </div>
                            <span v-else>{{item.comments}}</span>
                        </div>
                        <div slot="description" class="signContent" v-if="item.tagType=='CONTRACT'">
                            {{TAGSTATUS[item.busiStatus]}}
                        </div>
                        <div slot="description" class="signContent" v-if="item.tagType=='TERMINATE'">
                            {{TAGSTATUS[item.tagType]}}
                        </div>
                        <div slot="description" class="signContent" v-if="item.tagType=='PRICE'">
                            <div v-if="item.comments&&item.comments.split('#').length>1">
                                <div>{{item.comments.split('#')[0]}}</div>
                                <div><span style="font-size: 12px;color: #6b7580;">备注：</span>{{item.comments.split('#')[1]}}</div>
                            </div>
                            <span v-else>{{item.comments}}</span>
                        </div>
                    </el-step>
                </el-steps>
                <span v-else style="display: inline-block;text-align: center;width: 100%;">暂无标记信息</span>
            </div>
        </el-dialog>
        <el-dialog
                class="money-dialog"
                title=""
                :visible.sync="moneyShow"
                center>
            <div class="money-dialog-bg">
                <div class="money-text1" v-if="paySuccess"><i class="yinhao"></i><span style="color: #7b3e04;font-weight: bold;">支付成功！</span><i class="yinhao"></i></div>
                <div class="money-text1" v-else><i class="yinhao"></i>您需要花费<b>{{consumeAmount}}个</b>易废币<i class="yinhao"></i></div>
                <div class="money-text2"  v-if="paySuccess">您现在可以联系易废网管理人员来开启该活动了。</div>
                <div class="money-text2" v-else>来开启该活动。</div>
                <div class="money-text3">当前账户余额：<b>{{myBitAmount}}</b>个易废币</div>
                <div class="money-btn-div">
                    <el-button class="confirm-btn" v-if="myBitAmount>=consumeAmount&&!paySuccess" @click="confirmPay()">确定支付</el-button>
                    <el-button class="confirm-btn" v-if="myBitAmount<consumeAmount&&!paySuccess" @click="recharge()">立即充值</el-button>
                    <el-button class="recharge-btn" v-if="paySuccess" @click="recharge()">立即查看</el-button>
                    <el-button class="cancel-btn" type="text" @click="moneyShow = false">取 消</el-button>
                </div>
                <div class="money-time-tip" v-if="paySuccess">{{remainTime}}秒后自动关闭</div>
            </div>
        </el-dialog>
    </template>
</section>
<!-- 先引入 Vue -->
<script src="<%=appPath%>/main/common/elementui/vue.min.js"></script>
<!-- 引入组件库 -->
<script src="<%=appPath%>/main/common/elementui/index.js"></script>
<script src="<%=appPath %>/app/js/constants.js"></script>
<script src="<%=appPath %>/thirdparty/la-number/la-number.js"></script>
<script>
    var vue=new Vue({
        el: '#app',
        data: {
            pageIndex:1,
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
            ACTIVITYSTATUS:ACTIVITYSTATUS,
            INQUIRYSTATUS:INQUIRYSTATUS,
            orderStatus:'',
            dateType:'',
            actionStatus:'RELEASED',
            statusCount:{'DONE':0,'REFUSED':0,'RELEASED':0,'SUBMIT':0},
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
            consumeAmount:0,
            currentActivityId:'',
            currentActivityIndex:0,
            binding:0,
            priceRemark:''
        },
        created:function() {
            this.getList();
        },
        methods: {
            getList: function () {
                this.list=[];
                var _this = this;
                var param = this.getParamInfo();
                ajax({
                    url: '/wasteActivity/listAcitivityByEnterId.htm?ticketId=' + getParam('ticketId') + '&pageIndex=' + (this.pageIndex) + '&pageSize=' + this.pageSize,
                    dataType: 'json',
                    contentType: 'application/json',
                    data: JSON.stringify(param),
                    success: function (result) {
                        console.log(result);
                        _this.listLoading = false;
                        if (result.status == 1 && result.data.list) {
                            _this.list=result.data.list;
                            _this.total=result.data.paging.totalRecord;
                            var activityId=getParam('activityId');
                            if(!activityId){
                                return;
                            }
                            for(var i in result.data.list){
                                if(result.data.list[i]['activityId']==activityId){
                                    vue.inquiryDetail(i);
                                    return;
                                }
                            }
                        }
                    }
                })
            },
            getInquiryList: function () {
                this.inquiryList=[];
                var _this = this;
                var param = {
                    activityId:this.list[this.index]['activityId'],
                    status:this.actionStatus
                };
                ajax({
                    url: '/entRelease/listWasteEntReleaseByActivityId.htm?ticketId=<%=ticketId%>&pageIndex='+this.inquiryIndex+'&pageSize='+this.inquirySize,
                    dataType: 'json',
                    data:JSON.stringify(param),
                    contentType: 'application/json',
                    success: function (result) {
                        console.log(result);
                        _this.inquiryLoading = false;
                        if (result.status == 1 && result.data.activityList) {
                            _this.inquiryList=result.data.activityList;
                            _this.inquiryTotal=result.data.paging.totalRecord;
                        } else {

                        }
                    }
                })
            },
            getActivityStatusCount:function () {
                var activityId=this.list[this.index]['activityId'];
                ajax({
                    url:'/entRelease/countActivityStatusByActivityId',
                    data:{
                        activityId:activityId
                    },
                    success:function (result) {
                        console.log(result);
                        if(result.status){
                            for(var i in result.data){
                                Vue.set(vue.statusCount,result.data[i]['status'],result.data[i]['count']);
                            }
                        }
                    }
                });
            },
            search:function () {
                this.pageIndex=1;
                this.list=[];
                this.getList();
            },
            getParamInfo:function () {
                var startDate=$('#start_date').val();
                startDate=startDate?(startDate+' 00:00:00'):'';
                var endDate=$('#end_date').val();
                endDate=endDate?(endDate+' 23:59:59'):'';
                var param={
                    status:this.orderStatus,
                    dateType: this.dateType,
                    startDate:startDate,
                    endDate:endDate
                };
                return param;
            },
            queryStatus:function (status) {
                this.orderStatus=status;
                this.search();
            },
            queryActionStaus:function (status) {
                this.actionStatus=status;
                this.getInquiryList();
            },
            queryDateType:function (dateType) {
                this.dateType=dateType;
                $('#start_date').val('');
                $('#end_date').val('');
                this.search();
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
                Vue.set(vue.statusCount,'DONE',0);
                Vue.set(vue.statusCount,'REFUSED',0);
                Vue.set(vue.statusCount,'RELEASED',0);
                Vue.set(vue.statusCount,'SUBMIT',0);
                this.getActivityStatusCount();
            },
            goBack:function () {
                this.detailShow=false;
            },
            checkEntAccount:function (item,index) {
                this.currentActivityIndex=index;
                this.currentActivityId=item.activityId;
                this.consumeAmount=item.bitCion;
                vue.paySuccess=false;
                ajax({
                    url:'/entBitcionAccount/checkEntAccount.htm?ticketId=<%=ticketId%>',
                    data:JSON.stringify({
                        consumeAmount:item.bitCion
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
                var item=vue.list[vue.currentActivityIndex];
                ajax({
                    url:'/entBindServe/saveBindServe.htm?ticketId=<%=ticketId%>',
                    data:JSON.stringify({
                        consumeAmount:item.bitCion,
                        bindServiceId:this.currentActivityId,
                        serviceType:'ACTIVITY',
                        remark:'支付“'+item.activityName+'”的活动举办权'
                    }),
                    contentType:'application/json',
                    success:function (result) {
                        console.log(result);
                        if(result.status==1){
                            vue.paySuccess=true;
                            vue.remainTime=3;
                            vue.myBitAmount-=vue.consumeAmount;
                            item.payStatus='PAYMENT';
                            Vue.set(vue.list,vue.currentActivityIndex,item);
                            var timer=setInterval(function () {
                                vue.remainTime-=1;
                                if(vue.remainTime==0){
                                    clearInterval(timer);
                                    vue.moneyShow=false;
                                }
                            },1000);
                        }
                    }
                });
            },
            recharge:function () {
                window.location='<%=appPath%>/personaluser/recharge.htm?ticketId=<%=ticketId%>';
            },
            contact:function (entId,entName) {
                getEnterpriseContacts(entId,entName,'处置企业我的活动');
            },
            goMyInquiry:function () {
              window.location='<%=appPath%>/entInquiry/list.htm?ticketId=<%=ticketId%>';
            },
            add:function () {
                window.location='<%=appPath%>/wasteActivity/addActivity.htm?ticketId=<%=ticketId%>';
            },
            edit:function (activityId) {
                window.location='<%=appPath%>/wasteActivity/addActivity.htm?ticketId=<%=ticketId%>&activityId='+activityId;
            },
            pause:function (item,index) {
                vue.$confirm('此操作将停止该活动, 是否确定?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(function(){
                    var arr=[item.activityId];
                    ajax({
                        url:'/wasteActivity/stopActivity.htm',
                        data:{
                            ticketId:'<%=ticketId%>',
                            ids:arr.toString(),
                            status:2
                        },
                        success:function (data) {
                            if(data.status==1){
                                $.notify("停止活动成功", { status: 'success',timeout:1500 });
                                item.status=3;
                                Vue.set(vue.list,index,item);
                            }
                        }
                    });
                }).catch(function(){});
            },
            reject:function (releaseId) {
                console.log(releaseId);
                var param={
                    releaseId:releaseId
                };
                vue.$confirm('此操作将拒绝该申请单, 是否确定?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(function(){
                    ajax({
                        url:'/entRelease/refusedWasteEntApply.htm?ticketId=<%=ticketId%>',
                        dataType: 'json',
                        contentType: 'application/json',
                        data:JSON.stringify(param),
                        success:function (data) {
                            if(data.status==1&&data.data==true){
                                $.notify('拒绝成功',{status:'success',timeout:2500});
                                vue.inquiryIndex=1;
                                vue.getInquiryList();
                                vue.getActivityStatusCount();
                            }
                        }
                    })
                }).catch(function(){});
            },
            entDetail:function (entId) {
                window.location = "<%=appPath%>/myenterprise/viewEnterpriseDetail.htm?ticketId=<%=ticketId%>&onlyViewFlg=true&enterpriseId=" + entId + "&breadcrumbName=我的活动";
            },
            buy:function (releaseId) {
                var src=appPath+'/entRelease/cfBuy.htm?ticketId='+getParam('ticketId')+'&releaseId='+releaseId+"&activityId="+this.list[this.index]['activityId']+"&breadcrumbName=我的活动";
                window.location=src;
            },
            formatDate:function (dateStr,fmt) {
                try {
                    fmt=fmt||'yyyy-MM-dd HH:mm:ss';
                    var arr=dateStr.split(' ');
                    var arr1=arr[0].split('-');
                    var year=arr1[0];
                    var month=arr1[1]||'01';
                    var day=arr1[2]||'01';
                    fmt=fmt.replace(/yyyy/g,year);
                    fmt=fmt.replace(/MM/g,month);
                    fmt=fmt.replace(/dd/g,day);
                    if(arr.length>1){
                        var arr2=arr[1].split(':');
                        var hour=arr2[0]||'00';
                        var min=arr2[1]||'00';
                        var second=arr2[2]||'00';
                        fmt=fmt.replace(/HH/g,hour);
                        fmt=fmt.replace(/mm/g,min);
                        fmt=fmt.replace(/ss/g,second);
                    }else{
                        fmt=fmt.replace(/HH/g,'00');
                        fmt=fmt.replace(/mm/g,'00');
                        fmt=fmt.replace(/ss/g,'00');
                    }
                } catch(error) {
                    console.error(error);
                } finally {
                    return fmt;
                }
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
//                        this.signFormShow=false;
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
                        param.comments=this.TAGSTATUS[this.sampleStatus]+'#'+this.sampleRemark;
                        break;
                    case 'CONTRACT':
                        param.tagStatus=this.contractStatus;
                        break;
                    case 'TERMINATE':
                        param.tagStatus='TERMINATE';
                        break;
                    case 'PRICE':
                        param.tagStatus='PRICE';
                        param.comments='报价#'+this.priceRemark;
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
                            Vue.set(vue.inquiryList,vue.signItem.index,vue.signItem);
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
                var heights=document.body.clientHeight;
                if(this.signTypeList.length==0){
                    this.getSignTypeList();
                }
                this.getSignList();
                setTimeout(function () {
                    $('.signTimeLine').css('max-height',heights-353+'px');
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
<%------- 导入底部信息 -------%>
<jsp:include page="/common/webCommon/bottom.jsp" flush="true" />

<%------- 结束导入底部信息 -------%>
<script type="text/javascript">
    $(document).ready(function () {
        var rs = window.globalInit();
        rs.done(function () {
            $('.form_datetime').datetimepicker({
                format : 'yyyy-mm-dd',
                language:  'zh-CN',
                weekStart: 1,
                todayBtn: "linked",
                todayHighlight:true,
                autoclose: true,
                clearBtn:true,
                startView: 2,
                minView: 2,
                forceParse: true,
                pickerPosition: "bottom-left",
            }).on('changeDate', function (ev) {
                reInitDatePicker ($(this));
                vue.dateType='';
                vue.search();
            });
        });
        function reInitDatePicker($that) {
            var id = $that.attr("id");
            var temp = $that.find('input').val();
            if (id == "startDate" ) {
                $('#endDate').datetimepicker('setStartDate',temp);
            } else if (id == "endDate" ) {
                $('#startDate').datetimepicker('setEndDate',temp);
            }
        }

    });
</script>
</body>