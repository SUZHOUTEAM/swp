<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%
    String appPath = request.getContextPath();
    String ticketId = (String) request.getAttribute("ticketId");
%>
<%------- 导入页头信息 -------%>
<jsp:include page="/common/top.jsp" flush="true">
    <jsp:param name="title" value="合同备案管理"/>
</jsp:include>
<jsp:include page="/common/left.jsp" flush="true">
    <jsp:param name="menuId" value="#contractRecordManager"/>
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
            <span class="el-breadcrumb__item__inner">权限管理</span>
            <span class="el-breadcrumb__separator">/</span>
        </span>
        <span class="el-breadcrumb__item">
            <span class="el-breadcrumb__item__inner">合同备案</span>
            <span class="el-breadcrumb__separator">/</span>
        </span>
    </div>
    <div>
        <div id="app">
            <template>
                <div v-for="(item,index) in list">
                    <div class="buy_item panel panel-body">
                        <el-row :gutter="10" style="margin-bottom:10px">
                            <el-col :xs="16" :sm="16" :md="16" :lg="16">
                                <span class="status" :class="item.busiStatus">{{statusClass[item.busiStatus]}}</span>
                                <b style="margin-left: 14px">服务商：</b>
                                <em style="margin-right: 14px">{{item.facilitatorEntName}}</em>
                                <b>处置企业：</b>
                                <em style="margin-right: 14px">测试处置企业</em>
                            </el-col>
                            <el-col :xs="8" :sm="8" :md="8" :lg="8" align="right" style="font-size:12px;color: #6a7580;">
                                最后一次修改时间：{{item.editTime.substring(0,16)}}
                            </el-col>
                        </el-row>
                        <el-row :gutter="10" style="margin-bottom:10px">
                            <el-col :xs="24" :sm="24" :md="24" :lg="24">
                                <b>报价方式：</b><em>{{item.quotedType==0?'打包报价':'单独报价'}}</em><b>金额：</b><em><b style="color: #ff474c;font-weight:bold">￥{{item.totalPrice}}</b></em>
                                <b>总数：</b><em><b style="color: #1171d1">{{item.totalAmount}}</b></em>
                            </el-col>
                        </el-row>
                        <el-table :data="item.contractDetailModelList" border
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
                                    <span v-if="scope.row.price!=0">￥{{scope.row.price}}</span>
                                </template>
                            </el-table-column>
                            <el-table-column
                                    align="right"
                                    v-if="item.quotedType!=0"
                                    label="单项总价" >
                                <template scope="scope">
                                    <span v-if="scope.row.price == 0" >-</span>
                                    <span style="color: #ff474c;" class='wasteTotalPrice' v-if="scope.row.price!=0">￥{{scope.row.price*scope.row.amount}}</span>
                                </template>
                            </el-table-column>
                        </el-table>
                        <el-row :gutter="10" style="margin-top:10px">
                            <el-col :xs="24" :sm="24" :md="24" :lg="24"><b>备注：</b><em>{{item.remark||'--'}}</em></el-col>
                        </el-row>
                        <div style="margin-top: 9px;text-align: right;">
                            <el-button type="primary"  icon="el-icon-tickets"  size="small" style="margin-right: 4px" @click="approve(item,index)">审核通过</el-button>
                            <el-button type="primary" icon="el-icon-document"  size="small" style="margin-right: 4px" @click="reject(item,index)">审核退回</el-button>
                        </div>
                    </div>
                </div>
                <div style="padding: 50px 0;text-align: center" v-show="!list||list.length==0">
                    暂无相关数据
                </div>
                <div v-show="list&&list.length>0" align="right">
                    <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page.sync="pageIndex" :page-sizes="[5,10,20,30,50]" :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper" :total="total">
                    </el-pagination>
                </div>
            </template>
        </div>
        </body>
        <!-- 先引入 Vue -->
        <script src="<%=appPath%>/main/common/elementui/vue.min.js"></script>
        <!-- 引入组件库 -->
        <script src="<%=appPath%>/main/common/elementui/index.js"></script>
        <script src="<%=appPath %>/app/js/constants.js"></script>
        <script src="<%=appPath %>/app/js/util.js"></script>
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
                    statusClass:recordStatusClass,
                    pageIndex:1,
                    pageSize:5,
                },
                created: function () {
                    this.getList();
                },
                methods: {
                    getList: function () {
                        this.listLoading = true;
                        var _this = this;
                        var param={
                        }
                        ajax({
                            url: '/entRecordContract/listRecordContractList.htm?ticketId=<%=ticketId%>&pageIndex='+this.pageIndex+'&pageSize='+this.pageSize,
                            dataType: 'json',
                            contentType:'application/json',
                            data:JSON.stringify(param),
                            success: function (result) {
                                console.log(result);
                                _this.listLoading = false;
                                if (result.status == 1) {
                                    var recordContractList=result.data.recordContractList;
                                    _this.list=recordContractList;
                                    _this.total=result.data.paging.totalRecord;
                                }
                            }
                        })
                    },
                    approve:function (item,index) {
                        console.log(item,index);
                        vue.$confirm('此操作将把该合同备案状态改成审核通过, 是否确定?', '提示', {
                            confirmButtonText: '确定',
                            cancelButtonText: '取消',
                            type: 'warning'
                        }).then(function(){
                            ajax({
                                url:'/entRecordContract/approveRecordContract.htm?ticketId=<%=ticketId%>',
                                dataType: 'json',
                                contentType:'application/json',
                                data:JSON.stringify({id:item.id}),
                                success:function (result) {
                                    if(result.status==1&&result.data){
                                        $.notify('审核通过成功',{status:'success',timeout:2000});
                                        item.busiStatus='APPROVED';
                                        Vue.set(vue.list,index,item);
                                    }
                                }
                            })
                        }).catch(function(){});
                    },
                    reject:function (item,index) {
                        console.log(item,index);
                        vue.$confirm('此操作将把该合同备案状态改成审核退回, 是否确定?', '提示', {
                            confirmButtonText: '确定',
                            cancelButtonText: '取消',
                            type: 'warning'
                        }).then(function(){
                            ajax({
                                url:'/entRecordContract/rejectRecordContract.htm?ticketId=<%=ticketId%>',
                                dataType: 'json',
                                contentType:'application/json',
                                data:JSON.stringify({id:item.id}),
                                success:function (result) {
                                    if(result.status==1&&result.data){
                                        $.notify('审核退回成功',{status:'success',timeout:2000});
                                        item.busiStatus='REFUSED';
                                        Vue.set(vue.list,index,item);
                                    }
                                }
                            })
                        }).catch(function(){});
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