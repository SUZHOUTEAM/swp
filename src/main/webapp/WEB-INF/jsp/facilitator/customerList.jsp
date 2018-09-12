<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%
    String appPath=request.getContextPath();
    String ticketId = (String)request.getAttribute("ticketId");
%>
<%------- 导入页头信息 -------%>
<jsp:include page="/common/webCommon/user_top.jsp" flush="true">
    <jsp:param name="title" value="客户"/>
</jsp:include>
<jsp:include page="/common/webCommon/facilitatorMenu.jsp" flush="true">
    <jsp:param name="menuId" value="#myCustomer"/>
</jsp:include>
<link rel="stylesheet" href="<%=appPath%>/main/common/elementui/index.css">
<link rel="stylesheet" href="<%=appPath%>/css/myBusiness/business.css">
<section id="app">
    <template>
        <el-breadcrumb separator="/">
            <el-breadcrumb-item>我的易废圈</el-breadcrumb-item>
            <el-breadcrumb-item>客户</el-breadcrumb-item>
        </el-breadcrumb>
        <div>
            <el-row style="margin-bottom: 10px">
                <el-button type="primary" size="small" icon="el-icon-plus" @click="add">新增</el-button>
                <el-button type="primary" size="small" icon="el-icon-edit" @click="edit">编辑</el-button>
                <el-button type="primary" size="small" icon="el-icon-delete" @click="del">删除</el-button>
            </el-row>
            <div class="selectTable">
                <el-table  tooltip-effect="dark" @selection-change="handleSelectionChange"
                           :data="tableData"  v-loading.body="listLoading" ref="dataSelectTable" border
                           style="width: 100%">
                    <el-table-column
                            prop="id"  type="selection" align="center">
                    </el-table-column>
                    <el-table-column
                            label="客户名称"
                            prop="customerName">
                        <template scope="scope">
                            {{scope.row.customerName||'--'}}
                        </template>
                    </el-table-column>
                    <el-table-column
                            label="联系人"
                            prop="contacts">
                        <template scope="scope">
                            {{scope.row.contacts||'--'}}
                        </template>
                    </el-table-column>
                    <el-table-column
                            label="联系电话"
                            prop="contactsTel">
                        <template scope="scope">
                            {{scope.row.contactsTel||'--'}}
                        </template>
                    </el-table-column>
                    <el-table-column
                            label="客户地址"
                            prop="customerAdd">
                        <template scope="scope">
                            {{scope.row.customerAdd||'--'}}
                        </template>
                    </el-table-column>
                </el-table>
            </div>
            <div v-show="tableData&&tableData.length>0" align="right" style="margin-top: 10px">
                <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page.sync="pageIndex" :page-sizes="[10,20,30,50]" :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper" :total="total">
                </el-pagination>
            </div>
        </div>
    </template>
</section>
<!-- 先引入 Vue -->
<script src="<%=appPath%>/main/common/elementui/vue.min.js"></script>
<!-- 引入组件库 -->
<script src="<%=appPath%>/main/common/elementui/index.js"></script>
<script src="<%=appPath %>/app/js/constants.js"></script>
<script src="<%=appPath %>/app/js/util.js"></script>

<script>
    var vue=new Vue({
        el: '#app',
        data: {
            tableData: [],
            listLoading:true,
            pageIndex:1,
            pageSize:10,
            total:0,
            multipleSelection:[]
        },
        created:function() {
            this.getList();
        },
        methods:{
            handleSelectionChange:function(val){
                this.multipleSelection = val;
            },
            getList:function () {
                this.listLoading = true;
                var _this=this;
                var param={
                    pageIndex:this.pageIndex,
                    pageSize:this.pageSize
                }
                ajax({
                    url:'/facilitatorCustomer/listFacilitatorCustomer.htm',
                    dataType:'json',
                    data:param,
                    success:function (result) {
                        console.log(result);
                        _this.listLoading = false;
                        if(result.status==1&&result.data.customerList&&result.data.customerList.length>0){
                            _this.total=result.data.pagingParameter.totalRecord;
                            _this.tableData=result.data.customerList;
                        }else{
                            _this.tableData=[];
                            _this.total=0;
                        }
                    }
                })
            },
            search:function () {
              this.pageIndex=1;
              this.getList();
            },
            handleSizeChange:function(val) {
                this.pageIndex=1;
                this.pageSize = val;
                this.getList();
            },
            handleCurrentChange:function(val) {
                this.pageIndex = val;
                this.getList();
            },
            add:function () {
                window.location='<%=appPath%>/facilitator/addCustomer.htm?ticketId=<%=ticketId%>';
            },
            edit:function () {
                if(this.multipleSelection.length!=1){
                    $.notify('请选择一条进行编辑',{status:'info',timeout:2500});
                    return;
                }
                window.location='<%=appPath%>/facilitator/addCustomer.htm?ticketId=<%=ticketId%>&id='+vue.multipleSelection[0]['customerId'];
            },
            del:function () {
                if(this.multipleSelection.length==0){
                    $.notify('请至少选择一条进行删除',{status:'info',timeout:2500});
                    return;
                }
                var arr=[];
                for(var i in vue.multipleSelection){
                    var str=vue.multipleSelection[i]['id'];
                    arr.push(str);
                }
                vue.$confirm('此操作将解除该客户绑定, 是否确定?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(function(){
                    ajax({
                        url:'/facilitatorCustomer/deleteFacilitatorCustomer.htm?ticketId=<%=ticketId%>',
                        data:JSON.stringify(arr),
                        contentType:'application/json',
                        success:function (result) {
                            if(result.status&&result.data){
                                $.notify('客户删除成功',{status:'success',timeout:2500});
                                vue.pageIndex=1;
                                vue.getList();
                            }
                        }
                    })
                }).catch(function(){});
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