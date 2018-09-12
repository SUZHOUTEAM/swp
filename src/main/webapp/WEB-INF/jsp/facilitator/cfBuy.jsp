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
<link rel="stylesheet" href="<%=appPath%>/main/pc/css/publish.css">
<section>
    <div class="el-breadcrumb">
        <span class="el-breadcrumb__item">
            <span class="el-breadcrumb__item__inner" onclick="history.go(-1);">资源池</span>
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
                              style="width:100%">
                        <el-table-column
                                label="危废名称"
                                width="180"
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
                                prop="wasteAmount">
                        </el-table-column>
                        <el-table-column
                                label="单位"
                                prop="unitValue">
                        </el-table-column>
                        <el-table-column align="center" label="操作" width="200">
                            <template scope="scope">
                                <el-button v-if="scope.row.status=='0'" title="加入报价单" size="small" type="text" @click="join(scope.$index,tableData)">加入报价单
                                </el-button>
                                <span v-if="scope.row.status=='1'" style="width:80px;color: #b6b6b6;font-size: 12px;display: inline-block">已加入</span>
                            </template>
                        </el-table-column>
                    </el-table>
                    <%--<el-row :gutter="10" style="margin-top:10px">--%>
                        <%--<el-col :xs="24" :sm="24" :md="24" :lg="24"><b>备注：</b>{{releaseRemark}}</el-col>--%>
                    <%--</el-row>--%>
                    <el-row :gutter="10" style="margin-top:10px">
                        <el-col :xs="24" :sm="24" :md="24" :lg="24" style="text-align: right"><el-button type="text"  @click="addWaste"><i class="responsepublish"></i>全部报价</el-button></el-col>
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
                            <div class="totalPriceMessage">
                                <label style="color: #6b7580">总价：</label>
                                <input class="form-control" placeholder="请输入打包总价" id="totalAmount" v-show="buyType==1"/>
                                <span v-show="buyType==2" class="totalText">{{getTotalPrice()}}</span>
                                <el-button type="primary" @click="submit" :loading="submitLoading">报价</el-button>
                            </div>
                            <span>合计：</span>您将报价<font>{{multipleSelection.length}}</font>种危废，累计<font>{{getQualityTextBySelection()}}</font>
                        </div>
                        <div slot="append" class="sum">
                            <span>报价：</span>
                            <template>
                                <el-radio class="radio" v-model="radio" label="1">打包报价</el-radio>
                                <el-radio class="radio" v-model="radio" label="2">单独报价</el-radio>
                            </template>
                        </div>
                        <div slot="append" class="sum" style="width:700px;padding-bottom: 0;">
                            <span>备注：</span>
                            <el-input type="textarea" v-model="remark" placeholder="请填写运费合算方式及包装方式，如：价格包含运费，放在吨袋里面独立包装"></el-input>
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
                    facilitatorEntId:getParam('facilitatorEntId')||''
                },
                created:function() {
                    this.getList();
                },
                mounted:function () {
                    var _this=this;
                    $('.el-radio.radio').click(function () {
                        $(this).find('.el-radio__input').addClass('is-checked');
                        $(this).siblings().find('.el-radio__input').removeClass('is-checked');
                        vue.buyType=$(this).index().toString();
                        return false;
                    });
                },
                methods:{
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
                                    _this.releaseDate=result.data.releaseDate.substring(0,16);
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
                    submit:function () {
                        this.submitLoading=true;
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
                            return;
                        }
                        param['inquiryDetail']=inquiryDetail;
                        console.log(param);
                        ajax({
                            url:'/entInquiry/saveEntInquiry.htm?ticketId='+getParam('ticketId'),
                            data:JSON.stringify(param),
                            dataType:'json',
                            contentType:'application/json',
                            success:function (result) {
                                console.log(result);
                                if(result.status==1&&result.data){
                                    var str='${breadcrumbName}';
                                    $.notify('报价成功,3秒后将返回资源池...',{status:'success',timeout:3000});
                                    collectingUserBehavior(getParam('ticketId'),'INQUIRY');
                                    setTimeout(function () {
                                        if(vue.activityId){
                                            window.location='<%=appPath%>/wasteActivity/myActivity.htm?ticketId=<%=ticketId%>&activityId='+vue.activityId;
                                        }else{
                                            goBack();
                                        }
                                    },3000);
                                }else{
                                    this.submitLoading=false;
                                }
                            },
                            error:function () {
                                this.submitLoading=false;
                            }
                        })
                    },
                    join:function (index, rows) {
                        rows[index]['status']=1;
                        this.multipleSelection.push(rows[index]);
                    },
                    deleteRow:function(index, rows) {
                        var i=rows[index]["index"];
                        this.tableData[i]['status']=0;
                        rows.splice(index, 1);
                    },
                    select:function () {
                        if(this.multipleSelection.length==0){
                            $.notify('请选择危废',{status:'info',timeout:1500});
                            return;
                        }
                        this.wasteSelected=true;
                    },
                    publish:function () {
                        var param={
                            remark:this.remark,
                            releaseCount:this.multipleSelection.length,
                            totalAmount:this.totalQtyText
                        };
                        var entReleaseDetails=[];
                        var flag=false;
                        var pattern=/^(?=([0-9]{1,10}$|[0-9]{1,7}\.))(0|[1-9][0-9]*)(\.[0-9]{1,3})?$/;
                        for(var i in this.multipleSelection){
                            var obj={};
                            var selection=this.multipleSelection[i];
                            obj.entWasteId=selection['entWasteId'];
                            obj.releaseAmount=selection['quality'];
                            if(selection['quality']==0||!pattern.test(selection['quality'])){
                                flag=true;
                            }
                            entReleaseDetails.push(obj);
                        }
                        if(flag){
                            $.notify('危废数量不能为0,总长不超过十位,最多三位小数',{status:'info',timeout:2000});
                            return;
                        }
                        param.releaseDetail=entReleaseDetails;
                        this.publishLoading=true;
                        console.log(param);
                        var _this=this;
                        ajax({
                            url:'/entRelease/save.htm?ticketId='+getParam('ticketId'),
                            dataType:'json',
                            contentType:'application/json',
                            data:JSON.stringify(param),
                            success:function (result) {
                                console.log(result);
                                _this.publishLoading = false;
                                if(result.status==1){
                                    _this.dialogVisible=true;
                                }
                            }
                        })
                    },
                    addWaste:function () {
                        this.multipleSelection=[];
                        for(var index in this.tableData){
                            this.tableData[index]['status']=1;
                            this.multipleSelection.push(this.tableData[index]);
                        }
                    },
                    unselected:function () {
                        this.wasteSelected=false;
                    },
                    closeDialog:function () {
                        this.wasteSelected=false;
                        this.dialogVisible=false;
                        this.$refs.dataSelectTable.clearSelection();
                        for(var i in this.tableData){
                            this.tableData[i].quality=0;
                        }
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