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
    <jsp:param name="menuId" value="#myRelease" />
</jsp:include>
<link rel="stylesheet" href="<%=appPath%>/main/common/elementui/index.css">
<link rel="stylesheet" href="<%=appPath%>/main/pc/css/publish.css">
<style type="text/css">
    .el-select .el-input__inner{
        background-color: #ffffff;
    }
</style>
<section id="app">
    <template>
    <el-breadcrumb separator="/">
        <el-breadcrumb-item>委托处理</el-breadcrumb-item>
        <el-breadcrumb-item>{{wasteSelected?'发布危废':'选择客户和待处理危废'}}</el-breadcrumb-item>
    </el-breadcrumb>
        <div  class="panel panel-body">
                <div v-show="!wasteSelected" class="selectTable">
                    <div class="title">选择客户</div>
                    <el-select v-model="customerId" style="width: 400px;margin-bottom: 10px"
                               filterable
                               remote
                               reserve-keyword
                               placeholder="请输入并选择客户"
                               :remote-method="remoteMethod"
                               @change="getList"
                               :loading="outLoading">
                        <el-option
                                v-for="item in customerList"
                                :key="item.customerId"
                                :label="item.customerName"
                                :value="item.customerId">
                        </el-option>
                    </el-select>
                    <el-button type="text" @click="addCustomer" style="margin-left: 20px">+添加客户</el-button>
                    <div class="title">选择待处置产废</div>
                    <el-table :show-header="false"  @selection-change="handleSelectionChange"  max-height="500"
                              :data="tableData"  v-loading.body="listLoading" ref="dataSelectTable" border
                              style="width: 100%">
                        <el-table-column
                                prop="entWasteId"  type="selection">
                        </el-table-column>
                        <el-table-column
                                prop="wasteName">
                        </el-table-column>
                        <el-table-column
                                prop="wasteCode">
                        </el-table-column>
                    </el-table>
                    <el-row :gutter="10" style="margin-top:10px"  v-if="customerId">
                        <el-col :xs="6" :sm="6" :md="6" :lg="6"><el-button type="primary" style="width: 120px" @click="select">确 定</el-button></el-col>
                        <el-col :xs="18" :sm="18" :md="18" :lg="18" style="text-align: right">
                            <span style="color: #6a7580;margin-right: 10px;font-size: 12px;">上面没有要处理的危废，请点击这里</span>
                            <el-button type="text" @click="addWaste">+添加危废</el-button>
                        </el-col>
                    </el-row>
                </div>
                <div v-show="wasteSelected">
                    <div class="title">发布待处置产废</div>
                    <el-table :data="multipleSelection"
                              v-loading="publishLoading"
                              element-loading-text="正在发布中"
                              border
                              style="width:100%">
                        <el-table-column
                                prop="wasteName"
                                label="危废名称" width="180px">
                        </el-table-column>
                        <el-table-column
                                prop="wasteCode"
                                label="危废代码">
                        </el-table-column>
                        <el-table-column
                                prop="quality"
                                align="right"
                                label="数量">
                            <template scope="scope">
                                <el-input placeholder="请输入数量" v-model="multipleSelection[scope.$index]['quality']"></el-input>
                            </template>
                        </el-table-column>
                        <el-table-column
                                prop="unitValue"
                                label="单位">
                        </el-table-column>
                        <el-table-column label="操作" width="100px" prop="entWasteId">
                            <template scope="scope">
                                <el-button type="text" @click.native.prevent="deleteRow(scope.$index,multipleSelection)">移除</el-button>
                            </template>
                        </el-table-column>
                        <div slot="append" class="sum">
                            <span>合计：</span>您将发布<font>{{multipleSelection.length}}</font>种危废，累计<font>{{getQualityTextBySelection()}}</font>
                        </div>
                        <div slot="append" class="sum" style="width:700px;padding-bottom: 0;">
                            <span>备注：</span>
                            <el-input type="textarea" v-model="remark" placeholder="请输入备注"></el-input>
                        </div>
                    </el-table>
                    <div style="margin-top: 20px">
                        <el-button @click="publish" type="primary" >确认发布</el-button>
                        <el-button @click="unselected">取消</el-button>
                    </div>
                </div>
                <el-dialog :visible.sync="dialogVisible"
                        size="small">
                        <a href="javascript:" class="closeELDialog" @click="closeDialog"><i class="el-icon-circle-close"></i></a>
                        <div align="center" style="font-size: 16px"><i class="el-icon-circle-check" style="color: #86d464;margin-right: 10px;"></i>发布成功！</div>
                        <div class="ent_container" v-show="entList.length>0">
                            <div style="padding: 10px 0;">为你匹配并通知了{{entList.length}}家企业<br><i class="el-icon-arrow-down" style="color: #acccf2"></i></div>
                            <div>
                                <div class="item" v-for="(item,index) in entList">
                                    <div class="ent_logo"><img :src="item.imgSrc"/></div>
                                    <div class="ent_message">
                                        <div class="entName">{{item.entName}}</div>
                                        <div class="address"><i class="el-icon-address"></i>距离{{item.distance}}公里</div>
                                    </div>
                                </div>
                            </div>
                        </div>
                </el-dialog>

        </div>
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
            tableData: [],
            listLoading:true,
            multipleSelection:[],
            wasteSelected:false,
            publishLoading:false,
            dialogVisible:false,
            totalQtyText:'0吨',
            totalQtyT:'',
            remark:'',
            entList:[],
            customerList:[],
            customerId:getParam('customerId')||'',
            outLoading:false
        },
        created:function() {
            this.getList();
            this.remoteMethod('');
        },
        mounted:function () {
          if(this.customerId){
              this.getList();
          }
        },
        methods:{
            remoteMethod:function (query) {
                this.outLoading = true;
                var param={
                    pageIndex:this.pageIndex,
                    pageSize:this.pageSize,
                    customerName:query
                };
                ajax({
                    url:'/facilitatorCustomer/listFacilitatorCustomer.htm',
                    data:param,
                    success:function (result) {
                        vue.outLoading = false;
                        console.log(result);
                        if(result.status==1){
                            vue.customerList=result.data.customerList;
                        }
                    }
                })
            },
            getList:function () {
                this.listLoading = true;
                var _this=this;
                var param={
                    entId:this.customerId
                };
                ajax({
                    url:'/entWaste/listEntWasteByEntId.htm',
                    dataType:'json',
                    data:param,
                    success:function (result) {
                        console.log(result);
                        _this.listLoading = false;
                        if(result.status==1&&result.data&&result.data.length>0){
                            for(var i in result.data){
                                result.data[i].quality=0;
                            }
                            _this.tableData=result.data;
                        }else{
                            _this.tableData=[];
                        }
                    }
                })
            },
            handleSelectionChange:function(val){
                this.multipleSelection = val;
            },
            deleteRow:function(index, rows) {
                rows.splice(index, 1);
                if(rows.length==0){
                    this.wasteSelected=false;
                }
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
                    totalAmountDesc:this.totalQtyText,
                    totalAmount:this.totalQtyT,
                    entReleaseId:this.customerId
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
//                            _this.dialogVisible=true;
                            $.notify('发布成功',{status:'success',timeout:2000});
//                            collectingUserBehavior(getParam('ticketId'),'RELEASEWASTE');
                            setTimeout(function () {
                                window.location='<%=appPath%>/facilitator/publishList.htm?ticketId=<%=ticketId%>';
                            },2000);
                        }
                    }
                })
            },
            addWaste:function () {
                window.location='<%=appPath%>/facilitator/addWaste.htm?ticketId=<%=ticketId%>&customerId='+this.customerId;
            },
            addCustomer:function () {
                window.location='<%=appPath%>/facilitator/addCustomer.htm?ticketId=<%=ticketId%>';
            },
            unselected:function () {
                this.wasteSelected=false;
            },
            closeDialog:function () {
                window.location='<%=appPath%>/facilitator/publishList.htm?ticketId=<%=ticketId%>';
            },
            getQualityTextBySelection:function() {
                var arr=this.multipleSelection;
                var obj = {};
                var laNumber = new $.LaNumber();
                for(var i in arr) {
                    var key = arr[i]['unitValue'];
                    var value = parseFloat(arr[i]['quality']);
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