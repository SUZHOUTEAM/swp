<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%
    String appPath = request.getContextPath();
    String ticketId = (String) request.getAttribute("ticketId");
%>
<%------- 导入页头信息 -------%>
<jsp:include page="/common/webCommon/user_top.jsp" flush="true">
    <jsp:param name="title" value="我的订单" />
</jsp:include>
<jsp:include page="/common/webCommon/wasteCircleMenu.jsp" flush="true">
    <jsp:param name="menuId" value="#myOrders" />
</jsp:include>
<link rel="stylesheet" href="<%=appPath%>/main/common/elementui/index.css">
<link rel="stylesheet" href="<%=appPath%>/css/myBusiness/business.css?1">
<section>
    <div class="el-breadcrumb">
        <span class="el-breadcrumb__item">
            <span class="el-breadcrumb__item__inner">我的易废圈</span>
            <span class="el-breadcrumb__separator">/</span>
        </span>
        <span class="el-breadcrumb__item">
            <span class="el-breadcrumb__item__inner">我的订单</span>
            <span class="el-breadcrumb__separator">/</span>
        </span>
    </div>
    <div id="app">
        <el-dialog
                :title="dialogTitle"
                :visible.sync="dialogVisible"
                v-cloak
                width="518px">
            <div class="qualification">
                <div class="qualification-title"><span class="item-title-text">经营单位资质</span></div>
                <div class="qualification-imgs">
                    <div class="qualification-img item-img1">
                        <div class="image">
                            <img :src="qualification.busiLicImg" @click="showLargeImg(qualification.busiLicImg)"/>
                        </div>
                        <div class="qualification-text">营业执照</div>
                    </div>
                    <div  class="qualification-img qualification-img2">
                        <div class="image">
                            <img :src="qualification.licImg" @click="showLargeImg(qualification.licImg)"/>
                        </div>
                        <div class="qualification-text">经营许可证</div>
                    </div>
                </div>
            </div>
            <div class="qualification">
                <div class="qualification-title"><span class="item-title-text">服务商资质</span></div>
                <div class="qualification-imgs">
                    <div class="qualification-img item-img1">
                        <div class="image">
                            <img :src="qualification.facilitatorBusiLicImg" @click="showLargeImg(qualification.facilitatorBusiLicImg)"/>
                        </div>
                        <div class="qualification-text">营业执照</div>
                    </div>
                    <div  class="qualification-img qualification-img2">
                        <div class="image">
                            <img :src="qualification.authImg" @click="showLargeImg(qualification.authImg)"/>
                        </div>
                        <div class="qualification-text">授权书</div>
                    </div>
                </div>
            </div>
        </el-dialog>
        <div class="dialog" v-show="showLarge" @click.self="showLarge=false">
            <a href="javascript:" class="close-dialog" title="关闭" @click="showLarge=false"><i class="el-icon-circle-close-outline"></i></a>
            <img :src="largeImg" class="largeImg"/>
        </div>
        <template>
            <el-row class="searchDiv">
                    <b>时间：</b>
                    <el-button type="text" :class="{'select':year==''}" @click="queryYear('')">全部</el-button>
                    <el-button type="text" :class="{'select':year=='2018'}" @click="queryYear('2018')">本年度</el-button>
                    <el-date-picker style="margin-left: 10px"
                            v-model="year"
                            type="year" value-format="yyyy"
                            placeholder="选择年" @change="changeYear">
                    </el-date-picker>
                    <%--<div class="yearSelect"><input type="text" id="year" placeholder="选择年份" readonly></div>--%>
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
                <div class="content-wrapper panel panel-body buy_item">
                <el-row :gutter="10" style="margin-bottom:10px">
                    <el-col :xs="18" :sm="18" :md="18" :lg="18">
                        <span class="status" :class="statusClass[item.busiStatus]">{{item.busiStatus}}</span><b style="margin-left: 14px">单位：</b><a href="javascript:" title="查看企业详情" @click="entDetail(item.inquiryEntId)">
                        <em style="margin-right: 4px" v-if="item.disEntName">{{item.disEntName}}-{{item.inquiryEntName}}</em>
                        <em style="margin-right: 4px" v-else>{{item.inquiryEntName}}</em>
                        </a>
                        <a class="qipao_logo" href="javascript:" @click="contact(item.inquiryEntId,item.inquiryEntName)" title="联系TA" style="margin-right: 14px;">
                            <img src="../main/pc/img/qipao.jpg"></a>
                        <b>来源：</b><em><b style="color: #1171d1">
                        <a href="javascript:" style="color: #1171d1" v-if="list[index].activityName" @click="viewActivityDetail(list[index].activityId)">{{list[index].activityName}}</a>
                        <font v-else>危废处置询价</font>
                    </b></em>
                    </el-col>
                    <el-col :xs="6" :sm="6" :md="6" :lg="6" align="right" style="font-size:12px;color: #6a7580;">
                        {{item.orderDate}}
                    </el-col>
                </el-row>
                <el-row :gutter="10" style="margin-bottom:10px">
                    <el-col :xs="24" :sm="24" :md="24" :lg="24">
                        <b>报价方式：</b><em>{{item.quotedType==0?'打包报价':'单独报价'}}</em><b>金额：</b><em><b style="color: #ff474c;font-weight:bold">￥{{item.totalPrice}}</b></em>
                        <b>总数：</b><em><b style="color: #1171d1">{{item.totalAmount}}</b></em>
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
                <%--<div style="margin-top: 9px;text-align: right;" v-show="item.busiStatus=='已完成'&&!item.evaluated">--%>
                    <%--<el-button type="text" class="signBtn" v-if="item.orderContractStatus=='已上传'" icon="el-icon-download" style="margin-right: 29px" @click="downloadContract(item)">合同下载</el-button>--%>
                    <%--<el-tag type="warning" style="margin: 0 36px 0 12px;" v-else>合同起草中</el-tag>--%>
                    <%--<el-button type="text" class="comment_btn" @click="fold(item,index)"><i class="comment_icon"></i>评价<i class="el-input__icon el-icon-arrow-down" :class="{'is-reverse':item.commentShow}"></i></el-button>--%>
                <%--</div>--%>
                <%--<div style="margin-top: 9px;text-align: right;" v-show="item.busiStatus=='已完成'&&item.evaluated">--%>
                    <%--<el-button type="text" class="signBtn" v-if="item.orderContractStatus=='已上传'" icon="el-icon-download" style="margin-right: 29px" @click="downloadContract(item)">合同下载</el-button>--%>
                    <%--<el-tag type="warning" style="margin: 0 36px 0 12px;" v-else>合同起草中</el-tag>--%>
                    <%--<el-button type="text" class="comment_btn" @click="fold(item,index)"><i class="comment_icon commented"></i>{{item.commentShow?'收起评价':'已评价，查看'}}<i class="el-input__icon el-icon-arrow-down" :class="{'is-reverse':item.commentShow}"></i></el-button>--%>
                <%--</div>--%>
                <div style="margin-top: 9px;text-align: right;">
                    <el-button type="warning"  icon="el-icon-document" style="width: 103px" v-show="item.inquiryEntType=='DIS_FACILITATOR'" @click="viewQualification(item)">查看资质</el-button>
                    <el-button type="text" class="signBtn"  v-if="item.orderContractStatus=='已上传'" icon="el-icon-download" @click="downloadContract(item)">合同下载</el-button>
                    <el-tag type="warning"  v-else>合同起草中</el-tag>
                </div>
            </div>
            </div>
            <div style="padding: 50px 0;text-align: center" v-show="!list||list.length==0">
                暂无相关数据
            </div>
            <div v-show="list&&list.length>0" align="right" style="margin-top: 20px">
                <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page.sync="pageIndex" :page-sizes="[5,10,20,30,50]" :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper" :total="total">
                </el-pagination>
            </div>
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
                    qualification:{},
                    dialogVisible:false,
                    showLarge:false,
                    largeImg:'',
                    dialogTitle:'',
                    authImg:''
                },
                created: function () {
                    this.getList();
                    this.getActivityList();
                },
                methods: {
                    viewQualification:function (item) {
                        this.dialogTitle=item.inquiryEnterName;
                        ajax({
                            url:'/facilitatorCustomer/getCustomerImg',
                            data:{
                                facilitatorEntId: item.inquiryEntId,
                                customerId: item.disEntId
                            },
                            success:function (result) {
                                console.log(result);
                                if(result.status==1){
                                    vue.dialogVisible=true;
                                    vue.qualification={
                                        authImg:result.data.authImg?(IMG_VIEW_URL+'&fileID='+result.data.authImg):'http://www.yifeiwang.com/img/source/upload.png',
                                        busiLicImg:result.data.busiLicImg?(IMG_VIEW_URL+'&fileID='+result.data.busiLicImg):'http://www.yifeiwang.com/img/source/upload.png',
                                        facilitatorBusiLicImg:result.data.facilitatorBusiLicImg?(IMG_VIEW_URL+'&fileID='+result.data.facilitatorBusiLicImg):'http://www.yifeiwang.com/img/source/upload.png',
                                        licImg:result.data.licImg?(IMG_VIEW_URL+'&fileID='+result.data.licImg):'http://www.yifeiwang.com/img/source/upload.png'
                                    }
                                }
                            }
                        });
                    },
                    showLargeImg:function (url) {
                        this.showLarge=true;
                        this.largeImg=url;
                    },
                    addComment:function (item,index) {
                        item.commentShow=true;
                        item.evaluated=false;
                        Vue.set(this.list,index,item);
                    },
                    getList: function () {
                        this.listLoading = true;
                        var _this = this;
                        var param={
                            pageIndex:this.pageIndex,
                            pageSize:this.pageSize,
                            year:this.year,
                            activityId:this.activityId
                        };
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
                                var defaultObj ={'activityName':'危废处置询价',activityId:'resourcePull'};
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
                        };
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
                        window.location = "<%=appPath%>/enterprise/czDetail.htm?ticketId=<%=ticketId%>&enterpriseId=" + entId + "&breadcrumbName=我的订单";
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
                    contact:function (entId,entName) {
                        getEnterpriseContacts(entId,entName,'产废单位我的订单');
                    },
                    queryYear:function (year) {
                        this.year=year;
                        this.search();
                    },
                    changeYear:function () {
                        this.search();
                    },
                    activityNameChange:function(){
                        this.search();
                    },
                    handleSizeChange:function(val) {
                        this.pageSize = val;
                        this.getList()
                    },
                    handleCurrentChange:function(val) {
                        this.pageIndex = val;
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
                            if(item.commentLoaded){
                                item.evaluated=true;
                            }
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
                                                Vue.set(vue.list, index, item);
                                            }
                                        }
                                    });
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
        var rs = window.globalInit();
        rs.done(function () {

        })
    });
</script>