<%@ page contentType="text/html; charset=UTF-8" language="java"
         errorPage="" %>
<%
    String appPath = request.getContextPath();
    String ticketId = (String) request.getAttribute("ticketId");
%>

<%------- 导入页头信息 -------%>
<jsp:include page="/common/webCommon/user_top.jsp" flush="true">
    <jsp:param name="title" value="个人账户"/>
</jsp:include>
<%------- 结束导入页头信息 -------%>

<%------- 导入左侧信息 -------%>
<jsp:include page="/common/webCommon/wasteCircleMenu.jsp" flush="true">
    <jsp:param name="menuId" value="#myBit"/>
</jsp:include>
<link rel="stylesheet" href="<%=appPath %>/main/common/elementui/index.css">
<link rel="stylesheet" href="<%=appPath %>/main/pc/css/recharge.css">

<!-- Main section-->
<section>
    <div class="el-breadcrumb">
        <span class="el-breadcrumb__item">
            <span class="el-breadcrumb__item__inner" onclick="history.go(-1);">个人中心</span>
            <span class="el-breadcrumb__separator">/</span>
        </span>
        <span class="el-breadcrumb__item">
            <span class="el-breadcrumb__item__inner">个人账户</span>
            <span class="el-breadcrumb__separator">/</span>
        </span>
    </div>
    <div id="app" v-cloak>
        <el-row class="header-row">
            <el-col :span="18">
                <i class="bit-icon"></i>
                <b>账户名称：</b>
                <span class="header-text">${entName}</span>
                <b>易废币账户余额：</b>
                <span class="header-text"><em>${bitcionAccount.bitcion==null?0:bitcionAccount.bitcion}</em><span style="color: #ff6000">个</span>易废币</span>
            </el-col>
            <el-col :span="6" align="center">
                <el-button class="confirm-btn" @click="recharge()">立即充值</el-button>
            </el-col>
        </el-row>
        <el-tabs v-model="activeName" type="card" @tab-click="handleClick" style="margin-top: 12px">
            <el-tab-pane label="充值记录" name="first">
                <el-table :data="rechargeRecords" border
                          style="width:100%">
                    <el-table-column
                            prop="createTime"
                            width="160px"
                            label="日期">
                    </el-table-column>
                    <el-table-column
                            prop="remark"
                            width="200px"
                            label="充值明细">
                    </el-table-column>
                    <el-table-column
                            prop="wasteCode"
                            label="易废币">
                        <template scope="scope">
                            <span v-if="scope.row.bitCionAmount == 0" >-</span>
                            <span v-if="scope.row.bitCionAmount!=0" style="color: #1171d1" >{{scope.row.bitCionAmount}}</span>
                        </template>
                    </el-table-column>
                    <el-table-column
                            prop="amount"
                            label="金额">
                        <template scope="scope">
                            <span v-if="scope.row.price == 0" >-</span>
                            <span v-if="scope.row.price!=0" style="color: #ff0000" >￥{{scope.row.price}}</span>
                        </template>
                    </el-table-column>
                    <el-table-column
                            label="状态">
                        <template scope="scope">
                            <el-tag type="success"  v-if="scope.row.busiStatus=='SUCCESS'">{{rechargeStatus[scope.row.busiStatus]}}</el-tag>
                            <el-tag type="warning" v-else>{{rechargeStatus[scope.row.busiStatus]}}</el-tag>
                        </template>
                    </el-table-column>
                    <el-table-column
                            width="170px"
                            label="操作">
                        <template scope="scope">
                            <span v-if="scope.row.busiStatus=='SUCCESS'" >--</span>
                            <el-button v-else type="primary" size="small" @click="goPay(scope.$index)">去支付</el-button>
                            <el-button v-if="scope.row.busiStatus!='SUCCESS'" type="primary" size="small" @click="cancel(scope.$index)">删除</el-button>
                        </template>
                    </el-table-column>
                </el-table>
                <div v-show="rechargeRecords&&rechargeRecords.length>0" align="right">
                    <el-pagination @size-change="rechargeHandleSizeChange" @current-change="rechargeHandleCurrentChange" :current-page.sync="rechargePageIndex" :page-sizes="[10,20,30,50]" :page-size="rechargePageSize" layout="total, sizes, prev, pager, next, jumper" :total="rechargeTotal">
                    </el-pagination>
                </div>
            </el-tab-pane>
            <el-tab-pane label="消费记录" name="second">
                <el-table :data="consumeRecords" border
                          style="width:100%">
                    <el-table-column
                            prop="consumeTime"
                            label="日期">
                    </el-table-column>
                    <el-table-column
                            prop="remark"
                            label="交易明细">
                    </el-table-column>
                    <el-table-column
                            prop="consumeAmount"
                            label="易废币支出">
                        <template scope="scope">
                            <span v-if="scope.row.consumeAmount == 0" >-</span>
                            <span v-if="scope.row.consumeAmount!=0" style="color: #1171d1" >{{scope.row.consumeAmount}}</span>
                        </template>
                    </el-table-column>
                    <el-table-column
                            prop="remainAmount"
                            label="易废币剩余">
                        <template scope="scope">
                            <span v-if="scope.row.remainAmount == 0" >-</span>
                            <span v-if="scope.row.remainAmount!=0" style="color: #1171d1" >{{scope.row.remainAmount}}</span>
                        </template>
                    </el-table-column>
                </el-table>
                <div v-show="consumeRecords&&consumeRecords.length>0" align="right">
                    <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page.sync="pageIndex" :page-sizes="[10,20,30,50]" :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper" :total="total">
                    </el-pagination>
                </div>
            </el-tab-pane>
        </el-tabs>
    </div>
</section>

<script src="<%=appPath %>/app/js/constants.js"></script>
<script src="<%=appPath %>/app/js/util.js"></script>
<!-- 先引入 Vue -->
<script src="<%=appPath %>/main/common/elementui/vue.min.js"></script>
<!-- 引入组件库 -->
<script src="<%=appPath %>/main/common/elementui/index.js"></script>
<script type="text/javascript">
    var vue = new Vue({
        el: '#app',
        data:{
            currentIndex:null,
            activeName:getParam('activeName')||'first',
            consumeRecords:[],
            rechargeRecords:[],
            pageIndex:1,
            pageSize:10,
            total:0,
            rechargePageIndex:1,
            rechargePageSize:10,
            rechargeTotal:0,
            rechargeStatus:{'SUBMIT':'待付款','SUCCESS':'已付款'}
        },
        created:function () {

        },
        mounted:function() {

        },
        methods:{
            recharge:function () {
                window.location='<%=appPath%>/personaluser/recharge.htm?ticketId=<%=ticketId%>';
            },
            getList:function () {
                ajax({
                    url:'/entBindServe/listBindServe.htm?ticketId=<%=ticketId%>&pageIndex='+this.pageIndex+'&pageSize='+this.pageSize,
                    data:JSON.stringify({bindEntId:globalInit.enterpriseId}),
                    contentType:'application/json',
                    success:function (result) {
                        console.log(result);
                        if(result.status==1){
                            vue.consumeRecords=result.data.entBindServeModels;
                            vue.total=result.data.pagingParameter.totalRecord;
                        }
                    }
                });
            },
            getListRecharge:function () {
                ajax({
                    url:'/entRecharge/listRecharge.htm?ticketId=<%=ticketId%>&current='+this.rechargePageIndex+'&size='+this.rechargePageSize,
                    data:JSON.stringify({entId:globalInit.enterpriseId}),
                    contentType:'application/json',
                    success:function (result) {
                        console.log(result);
                        if(result.status==1){
                            vue.rechargeRecords=result.data.records;
                            vue.rechargeTotal=result.data.total;
                        }
                    }
                });
            },
            handleClick:function () {

            },
            handleSizeChange:function (val) {
                this.pageIndex=1;
                this.pageSize = val;
                this.getList();
            },
            handleCurrentChange:function (val) {
                this.pageIndex = val;
                this.getList();
            },
            rechargeHandleSizeChange:function (val) {
                this.rechargePageIndex=1;
                this.rechargePageSize = val;
                this.getListRecharge();
            },
            rechargeHandleCurrentChange:function (val) {
                this.rechargePageIndex = val;
                this.getListRecharge();
            },
            goPay:function (index) {
                window.location='<%=appPath%>/common/pay.jsp?orderNo='+this.rechargeRecords[index]['orderNo'];
            },
            cancel:function (index) {
                vue.$confirm('确定删除该充值记录?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '我再想想',
                    type: 'warning'
                }).then(function(){
                    ajax({
                        url:'/entRecharge/delete.htm',
                        data:{
                            id:vue.rechargeRecords[index]['id']
                        },
                        type:'post',
                        success:function (result) {
                            if(result.status==1&&result.data){
                                $.notify('删除成功',{status:'success',timeout:1000});
                                vue.rechargePageIndex=1;
                                vue.getListRecharge();
                            }
                        }
                    })
                }).catch(function(){});
            }
        }
    });
</script>

<%------- 导入底部信息 -------%>
<jsp:include page="/common/bottom.jsp" flush="true"/>
<%------- 结束导入底部信息 -------%>
<script type="text/javascript">
    $(document).ready(function() {
        var rs = window.globalInit();
        rs.done(function () {
            vue.getList();
            vue.getListRecharge();
        });
    });
</script>
