<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%
    String appPath = request.getContextPath();
    String ticketId = (String) request.getAttribute("ticketId");
%>
<%------- 导入页头信息 -------%>
<jsp:include page="/common/webCommon/user_top.jsp" flush="true">
    <jsp:param name="title" value="订单" />
</jsp:include>
<jsp:include page="/common/webCommon/facilitatorMenu.jsp" flush="true">
    <jsp:param name="menuId" value="#myReleaseOrders" />
</jsp:include>
<link rel="stylesheet" href="<%=appPath%>/main/common/elementui/index.css">
<link rel="stylesheet" href="<%=appPath%>/css/myBusiness/business.css">
<link rel="stylesheet" href="<%=appPath%>/css/myBusiness/record.css">
<link rel="stylesheet" href="<%=appPath%>/thirdparty/datetimepicker/css/bootstrap-datetimepicker.min.css"/>
<script type="text/javascript" src="<%=appPath%>/thirdparty/datetimepicker/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=appPath%>/thirdparty/datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>
<section>
    <div class="el-breadcrumb">
        <span class="el-breadcrumb__item">
            <span class="el-breadcrumb__item__inner">我的易废圈</span>
            <span class="el-breadcrumb__separator">/</span>
        </span>
        <span class="el-breadcrumb__item">
            <span class="el-breadcrumb__item__inner">线下订单</span>
            <span class="el-breadcrumb__separator">/</span>
        </span>
    </div>
    <div class="pabel">
        <div id="app">
            <template>
                <el-row :gutter="10" class="searchDiv">
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" align="right" class="info">
                        <b>时间：</b>
                        <el-button type="text" :class="{'select':year==''}" @click="queryYear('')">全部</el-button>
                        <el-button type="text" :class="{'select':year=='2018'}" @click="queryYear('2018')">本年度</el-button>
                        <el-date-picker
                                v-model="year" style="margin-left: 10px"
                                type="year" value-format="yyyy"
                                placeholder="选择年" @change="changeYear">
                        </el-date-picker>
                        <%--<div class="yearSelect"><input type="text" id="year" placeholder="选择年份" readonly></div>--%>
                    </el-col>
                </el-row>
                <div id="selected" class="bar" v-if="orderTotal">
                    <div class="barct">
                        <span class="condition">筛选结果：</span>
                        <em>共{{orderTotal.queryCount}}笔订单符合筛选条件</em>
                        <span class="par">
							<b>总数：</b>
                            <em>{{orderTotal.totalAmount||0}}</em>
						</span>
                        <span class="par money">
							<b>金额：</b>
                            <em>￥{{fmoney(orderTotal.totalPrice,2)}}</em>
						</span>
                    </div>
                </div>
                <div v-for="(item,index) in list">
                    <div class="buy_item panel panel-body">
                    <el-row :gutter="10" style="margin-bottom:10px">
                        <el-col :xs="18" :sm="18" :md="18" :lg="18">
                            <span class="status" :class="statusClass[item.busiStatus]">{{item.busiStatus}}</span><b style="margin-left: 14px">单位：</b><a href="javascript:;" title="查看企业详情" @click="entDetail(item.inquiryEntId)"><em style="margin-right: 4px">{{item.inquiryEntName}}</em></a>
                            <a class="qipao_logo" href="javascript:;" @click="contact(item.inquiryEntId,item.inquiryEntName)" title="联系TA" style="margin-right: 14px;">
                                <img src="../main/pc/img/qipao.jpg"></a>
                            <b>报价方式：</b><em>{{item.quotedType==0?'打包报价':'单独报价'}}</em><b>金额：</b><em><b style="color: #ff474c;font-weight:bold">￥{{item.totalPrice}}</b></em>
                            <b>总数：</b><em><b style="color: #1171d1">{{item.totalAmount}}</b></em>
                        </el-col>
                        <el-col :xs="6" :sm="6" :md="6" :lg="6" align="right" style="font-size:12px;color: #6a7580;">
                            {{item.orderDate}}
                        </el-col>
                    </el-row>
                    <el-table :data="item.orderDetail" border
                              style="width:100%;">
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
                                <span v-if="scope.row.price!=0">￥{{scope.row.priceStr}}</span>
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
                    <div style="margin-top: 9px;text-align: right;" v-show="item.busiStatus=='已完成'&&!item.evaluated">
                        <el-button type="text" class="signBtn" icon="el-icon-document" style="margin-right: 4px" @click="showContractRecord(item,index)">合同备案</el-button>
                        <el-button type="text" class="signBtn" v-if="item.orderContractStatus=='已上传'" icon="el-icon-download" style="margin-right: 29px" @click="downloadContract(item)">合同下载</el-button>
                        <el-tag type="warning" style="margin: 0 36px 0 12px;" v-else>合同起草中</el-tag>
                        <el-button type="text" class="comment_btn" @click="fold(item,index)"><i class="comment_icon"></i>评价<i class="el-input__icon el-icon-arrow-down" :class="{'is-reverse':item.commentShow}"></i></el-button>
                    </div>
                    <div style="margin-top: 9px;text-align: right;" v-show="item.busiStatus=='已完成'&&item.evaluated">
                        <el-button type="text" class="signBtn" icon="el-icon-document" style="margin-right: 4px" @click="showContractRecord(item,index)">合同备案</el-button>
                        <el-button type="text" class="signBtn" v-if="item.orderContractStatus=='已上传'" icon="el-icon-download" style="margin-right: 29px" @click="downloadContract(item)">合同下载</el-button>
                        <el-tag type="warning" style="margin: 0 36px 0 12px;" v-else>合同起草中</el-tag>
                        <el-button type="text" class="comment_btn" @click="fold(item,index)"><i class="comment_icon commented"></i>{{item.commentShow?'收起评价':'已评价，查看'}}<i class="el-input__icon el-icon-arrow-down" :class="{'is-reverse':item.commentShow}"></i></el-button>
                    </div>
                    <div style="margin-top: 9px;text-align: right;" v-show="item.busiStatus=='进行中'">
                        <el-button type="text" class="signBtn" icon="el-icon-document" style="margin-right: 20px" @click="showContractRecord(item,index)">合同备案</el-button>
                        <el-button type="text" class="signBtn" v-if="item.orderContractStatus=='已上传'" icon="el-icon-download" @click="downloadContract(item)">合同下载</el-button>
                        <el-tag type="warning" v-else>合同起草中</el-tag>
                    </div>
                </div>
                <div class="comment_container" v-show="item.commentShow&&!item.evaluated">
                        <p>发布评价</p>
                        <table>
                            <tr>
                                <td style="vertical-align: middle">综合评价：</td>
                                <td>
                                    <el-radio class="radio" v-model="item.commentType"  label="5" :name="'commentType_'+item.id">好评</el-radio>
                                    <el-radio class="radio" v-model="item.commentType"  label="3" :name="'commentType_'+item.id">中评</el-radio>
                                    <el-radio class="radio" v-model="item.commentType"  label="1"  :name="'commentType_'+item.id">差评</el-radio>
                                </td>
                            </tr>
                            <tr>
                                <td style="padding-top: 17px">增加描述：</td>
                                <td>
                                    <el-input
                                            type="textarea"
                                            :rows="2"
                                            :id="'commentText_'+item.id"
                                            placeholder="请输入描述"
                                            v-model="item.commentText">
                                    </el-input>
                                </td>
                            </tr>
                        </table>
                        <el-row style="text-align: center;margin-top: 20px">
                            <el-button type="primary" class="over" @click="comment(item,index)">发布评论</el-button>
                            <el-button @click="fold(item,index)">取消</el-button>
                        </el-row>
                    </div>
                    <div class="comment_container" v-show="item.commentShow&&item.evaluated">
                        <p>查看评价</p>
                        <table v-for="(comment,i) in item.commentList">
                            <thead>
                            <tr>
                                <th colspan="2">我发布的评价</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td style="vertical-align: middle">综合评价：</td>
                                <td>
                                    {{COMMENT[comment.score]}}
                                </td>
                            </tr>
                            <tr>
                                <td style="vertical-align: middle;">增加描述：</td>
                                <td>
                                    {{comment.comment}}
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div style="padding: 50px 0;text-align: center" v-show="!list||list.length==0">
                    暂无相关数据
                </div>
                <div v-show="list&&list.length>0" align="right">
                    <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page.sync="pageIndex" :page-sizes="[5,10,20,30,50]" :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper" :total="total">
                    </el-pagination>
                </div>
                <el-dialog
                        title="合同备案"
                        :visible.sync="contractRecordVisible"
                        width="80%">
                    <el-table :data="multipleSelection" v-if="multipleSelection.length>0"
                              v-loading="publishLoading"
                              element-loading-text="正在询价中" border
                              style="width:100%">
                        <el-table-column
                                prop="wasteName"
                                label="危废名称" width="180px">
                        </el-table-column>
                        <el-table-column
                                prop="wasteCode"
                                width="130px"
                                label="危废代码">
                        </el-table-column>
                        <el-table-column
                                prop="wasteAmount"
                                align="right"
                                label="数量">
                        </el-table-column>
                        <el-table-column
                                label="单位"
                                prop="unitValue">
                        </el-table-column>
                        <el-table-column
                                prop="price"
                                align="right"
                                v-if="buyType==2"
                                label="单价">
                            <template scope="scope">
                                <el-input placeholder="请输入单价" v-model="multipleSelection[scope.$index]['price']"></el-input>
                            </template>
                        </el-table-column>
                        <el-table-column
                                label="单项总价"
                                v-if="buyType==2"
                                prop="totalPrice">
                            <template scope="scope">
                                <span  style="color: #ff0000;">{{getOneToTalPrice(multipleSelection[scope.$index]['price'],multipleSelection[scope.$index]['wasteAmount'])}}</span>
                            </template>
                        </el-table-column>
                        <div slot="append" class="sum">
                            <div class="totalPriceMessage">
                                <label style="color: #6b7580">总价：</label>
                                <input class="form-control" placeholder="请输入打包总价" id="totalAmount" v-show="buyType==1"/>
                                <span v-show="buyType==2" class="totalText">{{getTotalPrice()}}</span>
                                <el-button type="primary" @click="submit" :loading="submitLoading">备案</el-button>
                            </div>
                            <span>合计：</span>您将备案<font>{{multipleSelection.length}}</font>种危废，累计<font>{{getQualityTextBySelection()}}</font>
                        </div>
                        <div slot="append" class="sum">
                            <span>报价：</span>
                            <template>
                                <el-radio class="radio" v-model="buyType" label="1">打包报价</el-radio>
                                <el-radio class="radio" v-model="buyType" label="2">单独报价</el-radio>
                            </template>
                        </div>
                        <div slot="append" class="sum" style="width:700px;padding-bottom: 0;">
                            <span>备注：</span>
                            <el-input type="textarea" v-model="remark" placeholder="请填写运费合算方式及包装方式，如：价格包含运费，放在吨袋里面独立包装"></el-input>
                        </div>
                    </el-table>
                </el-dialog>
            </template>
        </div>
        </body>
        <!-- 先引入 Vue -->
        <script src="<%=appPath%>/main/common/elementui/vue.js"></script>
        <!-- 引入组件库 -->
        <script src="<%=appPath%>/main/common/elementui/index.js"></script>
        <script src="<%=appPath %>/app/js/constants.js"></script>
        <script src="<%=appPath%>/main/common/upload/upload.js"></script>
        <script src="<%=appPath %>/thirdparty/la-number/la-number.js"></script>
        <script>
            var vue=new Vue({
                el: '#app',
                data: {
                    list:[],
                    total: null,
                    listLoading: true,
                    tableData:[],
                    statusClass:orderStatusClass,
                    pageIndex:1,
                    pageSize:5,
                    inquiryStatus:'',
                    activityList:[],
                    year:'',
                    startDateTime:'',
                    endDateTime:'',
                    myEntId:'',
                    activityId:'',
                    COMMENT:COMMENT,
                    orderTotal:null,
                    contractRecordVisible:false,
                    multipleSelection:[],
                    remark:'',
                    buyType:'1',
                    totalWasteAmountDesc:'',
                    publishLoading:false,
                    submitLoading:false,
                    currentRecord:{},
                    recordContractId:'',
                    currentIndex:0
                },
                created: function () {
                    this.getList();
                    this.getActivityList();
                },
                methods: {
                    getList: function () {
                        this.listLoading = true;
                        var _this = this;
                        var param={
                            pageIndex:this.pageIndex,
                            pageSize:this.pageSize,
                            year:this.year,
                            activityId:this.activityId
                        }
                        ajax({
                            url: '/entOrders/listOrderByReleaseEntId.htm',
                            dataType: 'json',
                            data:param,
                            success: function (result) {
                                console.log(result);
                                _this.listLoading = false;
                                if (result.status == 1) {
                                    var orderList=result.data.orderList;
                                    for(var i in orderList){
                                        orderList[i]['commentShow']=false;
                                        orderList[i]['commentType']='good';
                                        orderList[i]['commentText']='';
                                        orderList[i]['commentLoaded']=false;
                                        orderList[i]['commentList']=[];
                                    }
                                    _this.list=orderList;
                                    _this.total=result.data.paging.totalRecord;
                                }
                            }
                        })
                    },
                    getActivityList:function(){
                        var _this=this;
                        ajax({
                            url:'/wasteActivity/listOrderActiviyNameByApplyEntId',
                            dataType: 'json',
                            success:function (data) {
                                var obj={'activityName':'全部',activityId:''};
                                var defaultObj ={'activityName':'资源池',activityId:'resourcePull'};
                                var arr=[obj,defaultObj];
                                arr=arr.concat(data.data);
                                _this.activityList = arr;
                            }
                        });
                    },
                    getOrderTotal:function () {
                        var _this = this;
                        var param={
                            year:this.year,
                            activityId:this.activityId
                        }
                        ajax({
                            url: '/entOrders/countOrderWasteAmount4ReleaseEnt.htm',
                            dataType: 'json',
                            data:param,
                            success: function (result) {
                                console.log(result);
                                if (result.status == 1) {
                                    _this.orderTotal=result.data;
                                }
                            }
                        })
                    },
                    entDetail:function (entId) {
                        window.location = "<%=appPath%>/enterprise/czDetail.htm?ticketId=<%=ticketId%>&enterpriseId=" + entId + "&breadcrumbName=线下订单-myReleaseOrders";
                    },
                    viewActivityDetail:function (activityId) {
                        window.location='<%=appPath%>/main/pc/view/wasteActivityDetail.html?ticketId=<%=ticketId%>&activityId='+activityId;
                    },
                    search:function () {
                        this.pageIndex=1;
                        this.list=[];
                        this.getList();
                        this.getOrderTotal();
                    },
                    queryStatus:function (status) {
                        this.inquiryStatus=status;
                        this.search();
                    },
                    contact:function (entId,inquiryEntName) {
                        getEnterpriseContacts(entId,inquiryEntName,'线下订单');
                    },
                    queryYear:function (year) {
                        this.year=year;
                        this.search();
                    },
                    changeYear:function () {
                        this.search();
                    },
                    handleSizeChange:function(val) {
                        this.pageSize = val
                        this.getList()
                    },
                    handleCurrentChange:function(val) {
                        this.pageIndex = val
                        this.getList()
                    },
                    handleFilter:function() {
                        this.getList()
                    },
                    downloadContract:function (item) {
                        var fileList=upload.getImgListByBusinessCode(item.id);
                        if(fileList.length>0){
                            upload.download(fileList[0].fileID);
                        }
                    },
                    showContractRecord:function (item,index) {
                        this.currentIndex=index;
                        this.contractRecordVisible=true;
                        this.recordContractId=item.recordContractId;
                        if(item.recordContractId){
                            this.getRecordContractById(item);
                            return;
                        }
                        this.buyType=item.quotedType==0?'1':'2';
                        this.remark=item.inquiryRemark;
                        var arr=item.orderDetail;
                        for(var i in arr){
                            arr[i]['wasteAmount']=arr[i]['amount'];
                            if(this.buyType=='1'){
                                arr[i]['price']=0;
                            }
                        }
                        this.currentRecord=item;
                        this.multipleSelection=arr;
                        if(this.buyType=='1'){
                            var timer=setInterval(function () {
                                if($('#totalAmount').length>0){
                                    clearInterval(timer);
                                    $('#totalAmount').val(item.totalPrice);
                                }
                            },100)
                        }else{
                            $('#totalAmount').val('');
                        }
                    },
                    getRecordContractById:function (item) {
                        ajax({
                            url:'/entRecordContract/getRecordContractById.htm?ticketId=<%=ticketId%>',
                            contentType:'application/json',
                            data:JSON.stringify({id:item.recordContractId}),
                            success:function (result) {
                                console.log(result);
                                if(result.status==1&&result.data){
                                    var item1=result.data;
                                    vue.buyType=item1.quotedType==0?'1':'2';
                                    if(item1.quotedType=='0'){
                                        var timer=setInterval(function () {
                                            if($('#totalAmount').length>0){
                                                clearInterval(timer);
                                                $('#totalAmount').val(item1.totalPrice);
                                            }
                                        },100)
                                    }else{
                                        $('#totalAmount').val('');
                                    }
                                    vue.remark=item1.remark;
                                    var arr=item1.contractDetailModelList;
                                    for(var i in arr){
                                        arr[i]['wasteAmount']=arr[i]['amount'];
                                        if(vue.buyType=='1'){
                                            arr[i]['price']=0;
                                        }
                                    }
                                    vue.currentRecord=item;
                                    vue.multipleSelection=arr;
                                }
                            }
                        })
                    },
                    submit:function () {
                        this.submitLoading=true;
                        var totalAmount=$('#totalAmount').val();
                        var param={
                            releaseEntId:this.currentRecord.releaseEntId,//发布企业Id
                            releaseId:this.currentRecord.releaseId,//发布Id
                            quotedType:this.buyType*1-1,//报价类型
                            totalAmount:this.totalQtyText,//总量
                            totalPrice:this.buyType=='1'?totalAmount:this.totalAmount,//总价
                            remark:this.remark,//备注
                            facilitatorEntId:this.currentRecord.facilitatorEntId,
                            orderId:this.currentRecord.id,
                            id:this.recordContractId
                        };
                        var inquiryDetail=[];
                        var laNumber = new $.LaNumber();
                        var pattern=/^(?=([0-9]{1,10}$|[0-9]{1,7}\.))(0|[1-9][0-9]*)(\.[0-9]{1,3})?$/;
                        var priceFlag=true;
                        for(var i in this.multipleSelection){
                            var obj=this.multipleSelection[i];
                            var o={};
                            if(this.recordContractId){
                                o.id=obj['id'];
                            }else{
                                o.orderDetailId=obj['id'];
                            }
                            o.orderId = this.currentRecord.id;
                            if(this.buyType=='1'){
                                o.price=0;
                                o.totalPrice=0;
                            }else{
                                o.price=parseFloat(obj['price']);
                                o.totalPrice=laNumber.mul(obj['price'],obj['wasteAmount']);
                                if(!pattern.test(o.price)||o.price==0){
                                    priceFlag=false;
                                }
                            }
                            inquiryDetail.push(o);
                        }
                        if(this.buyType=='1'&&(!pattern.test(totalAmount)||totalAmount=='0')||this.buyType=='2'&&!priceFlag){
                            $.notify('价格必须是不为0的正数，总长不超过十位,最多三位小数',{status:'info',timeout:2500});
                            this.submitLoading=false;
                            return;
                        }
                        param['entRecordContractDetailList']=inquiryDetail;
                        console.log(param);
                        var url=this.recordContractId?'updateRecordContract':'saveRecordContract';
                        ajax({
                            url:'/entRecordContract/'+url+'.htm?ticketId='+getParam('ticketId'),
                            data:JSON.stringify(param),
                            dataType:'json',
                            contentType:'application/json',
                            success:function (result) {
                                console.log(result);
                                vue.submitLoading=false;
                                if(result.status==1&&result.data){
                                    $.notify('备案成功',{status:'success',timeout:3000});
                                    if(!vue.recordContractId){
                                        var item=vue.list[vue.currentIndex];
                                        item.recordContractId=result.data;
                                        Vue.set(vue.list,vue.currentIndex,item);
                                    }
                                    vue.contractRecordVisible=false;
                                }
                            },
                            error:function () {
                                vue.submitLoading=false;
                            }
                        })
                    },
                    getTotalPrice:function () {
                        var arr=this.multipleSelection;
                        var totalPrice=0;
                        var laNumber = new $.LaNumber();
                        for(var i in arr) {
                            var value=laNumber.mul(arr[i]['wasteAmount'],arr[i]['price']);
                            totalPrice=laNumber.add(totalPrice,value);
                        }
                        this.totalAmount=totalPrice;
                        return fmoney(totalPrice,2)+'元';
                    },
                    getOneToTalPrice:function (price,qty) {
                        var laNumber = new $.LaNumber();
                        return laNumber.mul(price,qty);
                    },
                    getQualityTextBySelection:function() {
                        var arr=this.multipleSelection;
                        var obj = {};
                        var laNumber = new $.LaNumber();
                        for(var i in arr) {
                            var key = arr[i]['unitValue'];
                            var value = parseFloat(arr[i]['wasteAmount']);
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
                        return str;
                    },
                    fold:function(item,index){
                        item.commentShow=!item.commentShow;
                        if(item.evaluated&&item.commentShow&&!item.commentLoaded){
                            if(!this.myEntId){
                                this.myEntId=window.globalInit.enterpriseId;
                            }
                            ajax({
                                url:'/entEvaluate/listEntEvaluate.htm',
                                data:{
                                    orderId:item.id
                                },
                                success:function (data) {
                                    console.log(data);
                                    if(data.status==1){
                                        item.commentList=data.data;
                                        item.commentLoaded=true;
                                    }
                                    Vue.set(vue.list, index, item);
                                }
                            });
                        }else{
                            Vue.set(vue.list, index, item);
                        }
                    },
                    comment:function (item,index) {
                        var id=item.id;
                        var name='commentType_'+id;
                        var commentTypeList=$('[name='+name+']');
                        var commentType='';
                        for(var i in commentTypeList){
                            var commentTypeInput=commentTypeList.eq(i);
                            if(commentTypeInput.parent().hasClass('is-checked')){
                                commentType=commentTypeInput.val();
                            }
                        }
                        var commentText=$('#commentText_'+id).val();
                        console.log(commentType+' '+commentText);
                        if(!commentType){
                            $.notify('请选择综合评价',{status:'info',timeout:1000});
                            return;
                        }
                        if(!commentText){
                            $.notify('请填写评价描述',{status:'info',timeout:1000});
                            return;
                        }
                        ajax({
                            url:'/entEvaluate/addEntEvaluate',
                            data:{
                                orderId:id,
                                score:commentType,
                                comment:commentText,
                                evaluatedEntId:item.inquiryEntId,
                                evaluatedBy:item.releaseEntId
                            },
                            success:function (data) {
                                if(data.status==1&&data.data){
                                    $.notify('评价成功',{status:'success',timeout:1000});
                                    item.evaluated=true;
                                    item.commentShow=false;
                                    Vue.set(vue.list, index, item);
                                }
                            }
                        });
                    },
                    fmoney:function(s, n) {  //s:传入的float数字 ，n:希望返回小数点几位
                        n = n > 0 && n <= 20 ? n : 2;
                        s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";
                        var l = s.split(".")[0].split("").reverse(),
                            r = s.split(".")[1];
                        t = "";
                        for (var i = 0; i < l.length; i++) {
                            t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");
                        }
                        return t.split("").reverse().join("") + "." + r;
                    }
                }

            })
        </script>
    </div>
</section>

<%------- 导入底部信息 -------%>
<jsp:include page="/common/webCommon/bottom.jsp" flush="true"/>

<%------- 结束导入底部信息 -------%>
<script type="text/javascript">
    $(document).ready(function () {
        $('#year').datetimepicker({
            language: 'zh-CN',
            weekStart: 0,
            todayBtn: 'linked',
            autoclose: true,
            todayHighlight: false,
            forceParse: true,
            format: "yyyy",
            startView:4,
            minView:4
        }).on('changeDate', function (ev) {
            reInitDatePicker ($(this));
            vue.year=$('#year').val();
            vue.search();
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