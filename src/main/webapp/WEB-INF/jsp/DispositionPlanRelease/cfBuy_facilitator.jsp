<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%
    String appPath=request.getContextPath();
    String ticketId = (String)request.getAttribute("ticketId");
%>
<%------- 导入页头信息 -------%>
<jsp:include page="/common/webCommon/user_top.jsp" flush="true">
    <jsp:param name="title" value="我的易废圈" />
</jsp:include>
<jsp:include page="/common/webCommon/wasteCircleMenu.jsp" flush="true">
    <jsp:param name="menuId" value="#myIndex" />
</jsp:include>
<link rel="stylesheet" href="<%=appPath %>/thirdparty/sweetalert/dist/sweetalert.css?1">
<link rel="stylesheet" href="<%=appPath%>/main/common/elementui/index.css">
<link rel="stylesheet" href="<%=appPath%>/main/pc/css/publish.css?2">
<section>
    <div class="el-breadcrumb">
        <span class="el-breadcrumb__item">
            <span class="el-breadcrumb__item__inner" onclick="history.go(-1);">${breadcrumbName}</span>
            <span class="el-breadcrumb__separator">/</span>
        </span>
        <span class="el-breadcrumb__item">
            <span class="el-breadcrumb__item__inner">报价</span>
            <span class="el-breadcrumb__separator">/</span>
        </span>
    </div>
    <div class="panel panel-body">
        <div id="app">
            <template>
                <div class="buy_con panel panel-body" style="margin-bottom: 0">
                    <el-row :gutter="10" style="margin-bottom:10px">
                        <el-col :xs="17" :sm="17" :md="17" :lg="17">{{entName}}<span class="split">|</span><b>总计：</b><font>{{tableData.length}}</font>种危废，累计<font>{{totalWasteAmountDesc}}</font></el-col>
                        <el-col :xs="7" :sm="7" :md="7" :lg="7" align="right" style="font-size:12px"><i class="time_icon"></i>发布于{{releaseDate}}</el-col>
                    </el-row>
                    <el-table :data="tableData" border v-loading.body="listLoading" ref="dataSelectTable"
                              @selection-change="handleSelectionChange"
                              style="width:100%">
                        <el-table-column
                                type="selection"
                                width="75">
                        </el-table-column>
                        <el-table-column
                                label="危废名称"
                                prop="wasteName">
                        </el-table-column>
                        <el-table-column
                                label="危废代码"
                                width="130px"
                                prop="wasteCode">
                        </el-table-column>
                        <el-table-column
                                label="数量"
                                align="right"
                                width="200px"
                                prop="wasteAmount">
                        </el-table-column>
                        <el-table-column
                                label="单位"
                                width="60px"
                                prop="unitValue">
                        </el-table-column>
                    </el-table>
                    <el-row :gutter="10" style="margin-top:10px"  v-show="multipleSelection.length==0">
                        <el-col :xs="24" :sm="24" :md="24" :lg="24" style="text-align: center">
                            <el-button type="primary"  class="quote-btn" disabled>报价</el-button>
                        </el-col>
                    </el-row>
                </div>
                <div v-show="multipleSelection.length>0" class="panel panel-body" style="margin: 1px 15px" id="selectionTable">
                    <div class="title">报价单</div>
                    <el-table :data="multipleSelection"
                              v-loading="publishLoading"
                              element-loading-text="正在报价中" border
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
                        <el-table-column label="操作" width="100px" prop="entWasteId">
                            <template scope="scope">
                                <el-button type="text" title="删除" @click.native.prevent="deleteRow(scope.$index,multipleSelection)">删除</el-button>
                            </template>
                        </el-table-column>
                        <div slot="append" class="sum">
                            <span>合计：</span>您将报价<font>{{multipleSelection.length}}</font>种危废，累计<font>{{getQualityTextBySelection()}}</font>
                        </div>
                        <div slot="append" class="sum">
                            <div class="totalPriceMessage">
                                <label style="color: #6b7580">总价：</label>
                                <input class="form-control" placeholder="请输入打包总价" id="totalAmount" v-show="buyType==1"/>
                                <span v-show="buyType==2" class="totalText">{{getTotalPrice()}}</span>

                            </div>
                            <span>报价：</span>
                            <template>
                                <el-radio class="radio" v-model="radio" label="1" id="1">打包报价</el-radio>
                                <el-radio class="radio" v-model="radio" label="2" id="2">单独报价</el-radio>
                            </template>
                        </div>
                        <div slot="append" class="sum">
                            <span>备注：</span>
                            <el-input type="textarea" style="width: 700px" v-model="remark" placeholder="请填写运费合算方式及包装方式，如：价格包含运费，放在吨袋里面独立包装"></el-input>
                        </div>
                        <div slot="append" class="sum" style="width: 910px" align="center">
                            <el-popover
                                    @show="showCustomerEvent"
                                    placement="top"
                                    width="400"
                                    v-model="showFacilitatorCustomer"
                                    trigger="click">
                                <el-row>
                                    <el-col :span="18" class="tip-title">请选择经营单位进行报价</el-col>
                                    <el-col :span="6" align="right">
                                        <el-button type="text" @click="showFacilitatorCustomer=false" class="cancel">取消</el-button>
                                    </el-col>
                                </el-row>
                                <el-radio-group v-model="facilitatorCustomerId">
                                    <el-radio :label="item.customerId" v-for="(item,index) in facilitatorCustomerList" >{{item.customerName}}</el-radio>
                                </el-radio-group>
                                <el-row>
                                    <el-col :span="16">
                                        <el-pagination  @current-change="handleCurrentChange" :current-page.sync="pageIndex" layout="prev, pager, next"
                                                        :page-size="pageSize" :total="total">
                                        </el-pagination>
                                    </el-col>
                                    <el-col :span="8" align="right">
                                        <el-button type="primary" :disabled="!facilitatorCustomerId" @click="submit" :loading="submitLoading">确认报价</el-button>
                                    </el-col>
                                </el-row>
                                <el-button type="primary" slot="reference" class="quote-btn">报价</el-button>
                                <%--<el-button type="primary" slot="reference" @click="submit" :loading="submitLoading">报价</el-button>--%>
                            </el-popover>
                        </div>
                    </el-table>
                </div>
            </template>
        </div>
        </body>
        <!-- 先引入 Vue -->
        <script src="<%=appPath%>/main/common/elementui/vue.min.js"></script>
        <!-- 引入组件库 -->
        <script src="<%=appPath%>/main/common/elementui/index.js"></script>
        <script src="<%=appPath %>/app/js/constants.js"></script>
        <script src="<%=appPath %>/thirdparty/sweetalert/dist/sweetalert.min.js?1"></script>
        <script src="<%=appPath %>/thirdparty/la-number/la-number.js"></script>
        <script>

            var vue=new Vue({
                el: '#app',
                data: {
                    tableData: [],
                    listLoading:true,
                    multipleSelection:[],
                    wasteSelected:false,
                    publishLoading:false,
                    dialogVisible:false,
                    totalQtyText:'0吨',
                    remark:'',
                    buyType:'1',
                    entName:'',
                    totalWasteAmountDesc:'',
                    releaseDate:'',
                    radio:'1',
                    releaseRemark:'',
                    info:{},
                    totalAmount:0,
                    activityId:getParam('activityId'),
                    submitLoading:false,
                    facilitatorEntId:getParam('facilitatorEntId')||'',
                    facilitatorCustomerList: [],
                    facilitatorCustomerId:'',
                    showFacilitatorCustomer:false,
                    pageSize:5,
                    pageIndex:1,
                    total:0
                },
                created:function() {
                    this.getList();
                    this.getFacilitatorCustomerList();
                },
                mounted:function () {
                    var _this=this;
                    $('.el-radio.radio').click(function () {
                        $(this).find('.el-radio__input').addClass('is-checked');
                        $(this).siblings().find('.el-radio__input').removeClass('is-checked');
                        vue.buyType=$(this).attr('id');
                        return false;
                    });
                    Vue.nextTick(function () {
                        $('.cell:first').append('<span style="margin-left: 4px;font-size: 12px;font-weight: 100;">全选</span>')
                    });
                },
                methods:{
                    showCustomerEvent:function () {
                      var param=this.getParamInfo();
                      if(!param){
                        this.showFacilitatorCustomer=false;
                      }
                    },
                    getFacilitatorCustomerList:function () {
                        var param={
                            pageIndex:this.pageIndex,
                            pageSize:this.pageSize
                        };
                        ajax({
                            url:'/facilitatorCustomer/listFacilitatorCustomer.htm',
                            dataType:'json',
                            data:param,
                            success:function (result) {
                                if(result.status==1&&result.data.customerList&&result.data.customerList.length>0) {
                                    vue.facilitatorCustomerList = result.data.customerList;
                                    vue.total=result.data.pagingParameter.totalRecord;
                                }
                            }
                        });
                    },
                    handleSizeChange:function(val) {
                        this.pageIndex=1;
                        this.pageSize = val;
                        this.getFacilitatorCustomerList();
                    },
                    handleCurrentChange:function(val) {
                        this.pageIndex = val;
                        this.getFacilitatorCustomerList();
                        this.facilitatorCustomerId='';
                    },
                    getList:function () {
                        this.listLoading = true;
                        var _this=this;
                        ajax({
                            url:'/entReleaseDetail/listReleaseDetail.htm',
                            dataType:'json',
                            data:{
                                releaseId:getParam('releaseId')
                            },
                            success:function (result) {
                                console.log(result);
                                _this.listLoading = false;
                                if(result.status==1&&result.data){
                                    var releaseWasteDetails=result.data.releaseWasteDetails;
                                    _this.entName=result.data.entName;
                                    _this.totalWasteAmountDesc=result.data.totalWasteAmountDesc;
                                    _this.releaseRemark=result.data.releaseRemark;
                                    _this.releaseDate=result.data.releaseDate.substring(0,7);
                                    for(var i in releaseWasteDetails){
                                        releaseWasteDetails[i].status=0;
                                        releaseWasteDetails[i].price=0;
                                        releaseWasteDetails[i].totalPrice=0;
                                        releaseWasteDetails[i].index=i;
                                    }
                                    _this.tableData=releaseWasteDetails;
                                    _this.info=result.data;
                                }
                            }
                        })
                    },
                    getParamInfo:function () {
                        var totalAmount=$('#totalAmount').val();
                        var param={
                            releaseEntId:this.info.releaseEntId,//发布企业Id
                            releaseId:this.info.releaseId,//发布Id
                            quotedType:this.buyType*1-1,//报价类型
                            totalAmount:this.totalQtyText,//总量
                            totalPrice:this.buyType=='1'?totalAmount:this.totalAmount,//总价
                            remark:this.remark,//备注
                        };
                        if(this.activityId){
                            param.activityId=this.activityId;
                        }
                        if(this.facilitatorEntId){
                            param.facilitatorEntId=this.facilitatorEntId;
                        }
                        param.disEntId=this.facilitatorCustomerId;
                        param.inquiryEntId=globalInit.enterpriseId;
                        var inquiryDetail=[];
                        var laNumber = new $.LaNumber();
                        var pattern=/^(?=([0-9]{1,10}$|[0-9]{1,7}\.))(0|[1-9][0-9]*)(\.[0-9]{1,3})?$/;
                        var priceFlag=true;
                        for(var i in this.multipleSelection){
                            var obj=this.multipleSelection[i];
                            var o={};
                            o.releaseDetailId=obj['detailId'];
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
                            return false;
                        }
                        param['inquiryDetail']=inquiryDetail;
                        console.log(param);
                        return param;
                    },
                    submit:function () {
                        this.submitLoading=true;
                        var param=this.getParamInfo();
                        ajax({
                            url:'/entInquiry/saveEntInquiry.htm?ticketId='+getParam('ticketId'),
                            data:JSON.stringify(param),
                            dataType:'json',
                            contentType:'application/json',
                            success:function (result) {
                                console.log(result);
                                if(result.status==1&&result.data){
                                    var str='${breadcrumbName}';
                                    collectingUserBehavior(getParam('ticketId'),'INQUIRY');
                                    swal({
                                        title: "报价成功!",
                                        text: "你可在我的报价中查看产废企业回复",
                                        type: "success",
                                        confirmButtonColor: "#1171d1",
                                        closeOnConfirm: true,
                                        confirmButtonText: "知道了"
                                        },
                                    function(){
                                        goBack();
                                    });
                                }else{
                                    this.submitLoading=false;
                                }
                            },
                            error:function () {
                                this.submitLoading=false;
                            }
                        })
                    },
                    handleSelectionChange:function (val) {
                        this.multipleSelection=val;
                    },
                    join:function (index, rows) {
                        rows[index]['status']=1;
                        this.multipleSelection.push(rows[index]);
                    },
                    deleteRow:function(index, rows) {
                        var i=rows[index]["index"];
                        this.$refs.dataSelectTable.toggleRowSelection(this.tableData[i]);
                    },
                    select:function () {
                        if(this.multipleSelection.length==0){
                            $.notify('请选择危废',{status:'info',timeout:1500});
                            return;
                        }
                        this.wasteSelected=true;
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
                    }
                }

            });
            function goBack() {
                document.cookie="index=true;path=<%=appPath%>/wastecircle/";
                window.history.go(-1);
            }
        </script>
    </div>
</section>

<%------- 导入底部信息 -------%>
<jsp:include page="/common/webCommon/bottom.jsp" flush="true" />

<%------- 结束导入底部信息 -------%>
<script type="text/javascript">
    $(document).ready(function() {
        var rs = window.globalInit();
        rs.done(function () {
//            init();
        });
//        $('.sum').width($('.sum').parent().parent().width()-60+'px');
//        window.onresize=function () {
//            $('.sum').width($('.sum').parent().parent().width()-60+'px');
//        }

    });
</script>